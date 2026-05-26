package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.Enum.Estado;
import com.aquienllamo.aquienllamo.model.Enum.MetodoDePago;
import com.aquienllamo.aquienllamo.model.dtos.Request.PagoDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.PagoDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.TrabajoEntity;
import com.aquienllamo.aquienllamo.model.exceptions.TrabajoNotFoundEx;
import com.aquienllamo.aquienllamo.model.mappers.PagoMapper;
import com.aquienllamo.aquienllamo.model.repositories.PagoRepository;
import com.aquienllamo.aquienllamo.model.repositories.TrabajoRepository;
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
        return pagoRepository.findAllByEstado(estado)
                .stream()
                .map(PagoMapper::toResponse)
                .toList();
    }


}
