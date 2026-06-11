package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.Enum.Estado;
import com.aquienllamo.aquienllamo.model.Enum.MetodoDePago;
import com.aquienllamo.aquienllamo.model.dtos.Request.PagoDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.PagoDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.PagoEntity;
import com.aquienllamo.aquienllamo.model.entities.TecnicoEntity;
import com.aquienllamo.aquienllamo.model.entities.TrabajoEntity;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.model.exceptions.PagoNotFoundEx;
import com.aquienllamo.aquienllamo.model.exceptions.TecnicoNotFoundEx;
import com.aquienllamo.aquienllamo.model.exceptions.TrabajoNotFoundEx;
import com.aquienllamo.aquienllamo.model.exceptions.UserNotFoundEx;
import com.aquienllamo.aquienllamo.model.mappers.PagoMapper;
import com.aquienllamo.aquienllamo.model.repositories.PagoRepository;
import com.aquienllamo.aquienllamo.model.repositories.TecnicoRepository;
import com.aquienllamo.aquienllamo.model.repositories.TrabajoRepository;
import com.aquienllamo.aquienllamo.model.repositories.UsuarioRepository;
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

    //crear un pago
    public PagoDTOResponse crearPago (PagoDTORequest request){

        TrabajoEntity trabajo = trabajoRepository.findByUuid(request.getUuidTrabajo())
                .orElseThrow(()-> new TrabajoNotFoundEx("ERROR: El trabajo ingresado no existe."));

        return PagoMapper.toResponse(pagoRepository.save(PagoMapper.toEntity(request,trabajo)));

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

}
