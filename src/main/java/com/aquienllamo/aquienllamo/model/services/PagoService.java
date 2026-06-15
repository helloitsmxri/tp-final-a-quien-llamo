package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.APIs.MercadoPago.MercadoPagoService;
import com.aquienllamo.aquienllamo.model.APIs.PayU.PayUResponseDTO;
import com.aquienllamo.aquienllamo.model.APIs.PayU.PayUService;
import com.aquienllamo.aquienllamo.model.Enum.Estado;
import com.aquienllamo.aquienllamo.model.Enum.EstadoPresupuestoE;
import com.aquienllamo.aquienllamo.model.Enum.MetodoDePago;
import com.aquienllamo.aquienllamo.model.dtos.Request.PagoDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.PagoDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.PagoEntity;
import com.aquienllamo.aquienllamo.model.entities.TrabajoEntity;
import com.aquienllamo.aquienllamo.model.exceptions.*;
import com.aquienllamo.aquienllamo.model.mappers.PagoMapper;
import com.aquienllamo.aquienllamo.model.repositories.PagoRepository;
import com.aquienllamo.aquienllamo.model.repositories.TecnicoRepository;
import com.aquienllamo.aquienllamo.model.repositories.TrabajoRepository;
import com.aquienllamo.aquienllamo.model.repositories.UsuarioRepository;
import com.mercadopago.resources.payment.Payment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;
    private final TrabajoRepository trabajoRepository;
    private final UsuarioRepository usuarioRepository;
    private final TecnicoRepository tecnicoRepository;
    private final PayUService payUService;
    private final MercadoPagoService mercadoPagoService;

    //crear un pago
    public PagoDTOResponse crearPago (PagoDTORequest request){

        TrabajoEntity trabajo = trabajoRepository.findByUuid(request.getUuidTrabajo())
                .orElseThrow(()-> new TrabajoNotFoundEx("ERROR: El trabajo ingresado no existe."));

        if(!trabajo.getPresupuesto().getEstado().equals(EstadoPresupuestoE.Aceptado)){
            throw new TrabajoNotAcceptedEx("ERROR: El trabajo que intenta pagar no esta aprobado.");
        }

        PagoEntity pago = PagoMapper.toEntity(request, trabajo);

        switch (request.getMetodoDePago()){
            case Credito, Debito ->{
                PayUResponseDTO respuesta = payUService.procesarPago(request, pago.getUuid());
                if("APPROVED".equals(respuesta.getState())){
                    pago.setEstadoPago(Estado.Confirmado);
                }else{
                    pago.setEstadoPago(Estado.Rechazado);
                }
            }
            case Transferencia -> {
                Payment respuesta = mercadoPagoService.procesarTransferencia(request);

                if ("approved".equals(respuesta.getStatus())){
                    pago.setEstadoPago(Estado.Confirmado);
                }else {
                    pago.setEstadoPago(Estado.Pendiente_de_revision);
                }
            }

            case Efectivo -> {
                pago.setEstadoPago(Estado.Pendiente_de_revision);
            }
        }

        return PagoMapper.toResponse(pagoRepository.save(pago));

    }

    //listar pagos segun un metodo de pago
    public List<PagoDTOResponse> listarPorMetodoDePago(MetodoDePago metodoDePago){
        return pagoRepository.findAllByMetodoDePago(metodoDePago)
                .stream()
                .map(PagoMapper::toResponse)
                .toList();
    }

    //listar pagos segun un estado
    public List<PagoDTOResponse> listarPorEstado (Estado estado){
        return pagoRepository.findAllByEstadoPago(estado)
                .stream()
                .map(PagoMapper::toResponse)
                .toList();
    }

    //listar todos los pagos
    public List<PagoDTOResponse> listarPagos (){
        return pagoRepository.findAll()
                .stream()
                .map(PagoMapper::toResponse)
                .toList();
    }

    //listar todos los pagos hechos por un cliente
    public List<PagoDTOResponse> listarPagosPorClienteUuid (String uuid){
        usuarioRepository.findByUuid(uuid)
                .orElseThrow(()-> new UserNotFoundEx("ERROR: El usuario ingresado no existe."));
        return pagoRepository.findAllByCliente(uuid)
                .stream()
                .map(PagoMapper::toResponse)
                .toList();
    }

    //listar pagos recibidos de un tecnico
    public List<PagoDTOResponse> listarPagosRecibidosPorTecnico (String uuid){
        tecnicoRepository.findByUuid(uuid)
                .orElseThrow(()-> new TecnicoNotFoundEx("ERROR: El tecnico ingresado no existe."));
        return pagoRepository.findAllByTecnico(uuid)
                .stream()
                .map(PagoMapper::toResponse)
                .toList();
    }

    //buscar un pago por uuid
    public PagoDTOResponse buscarPagoPorUuidParaTecnico (String uuid){
        PagoEntity pago = pagoRepository.findByUuid(uuid)
                .orElseThrow(()-> new PagoNotFoundEx("ERROR: El pago ingresado no existe."));
        return PagoMapper.toResponse(pago);
    }

    //cancelar pago
    public PagoDTOResponse cancelarPago (String uuid){
        PagoEntity pago = pagoRepository.findByUuid(uuid)
                .orElseThrow(()-> new PagoNotFoundEx("ERROR: El pago ingresado no existe."));
        if(!pago.getEstadoPago().equals(Estado.Pendiente_de_revision)){
            throw new PaymentCannotBeCancelledException("ERROR: Solo se pueden cancelar pagos pendientes.");
        }

        pago.setEstadoPago(Estado.Rechazado);
        return PagoMapper.toResponse(pagoRepository.save(pago));

    }

    //metodo que corrobora que el usuario ingresado del uuid es el mismo logueado con ese mail
    //busca al usuario por el uuid que viene en la url, si lo encontro compara el email con el del token, sino existe ese uuid devuelve false
    public boolean perteneceAlUsuario(String uuid, String email){
        return usuarioRepository.findByUuid(uuid)
                .map(usuario -> usuario.getEmail().equals(email))
                .orElse(false);
    }

    //buscar un pago por uuid, busca al usuario dueño del presupuesto asociado
    //y compara el email con el del usuario logueado, si son iguales devuelve true, sino existe false
    public boolean pagoPerteneceAlUsuario(String uuid, String email){
        return pagoRepository.findByUuid(uuid)
                .map(pago -> pago.getTrabajo().getPresupuesto().getUsuario().getEmail().equals(email))
                .orElse(false);
    }

}
