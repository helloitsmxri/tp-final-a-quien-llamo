package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.Enum.EstadoVerificacion;
import com.aquienllamo.aquienllamo.model.dtos.Request.PortfolioAdminDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.PortfolioAdminDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.PortfolioEntity;
import com.aquienllamo.aquienllamo.model.exceptions.PortfolioAlreadyReviewedEx;
import com.aquienllamo.aquienllamo.model.exceptions.PortfolioNotFoundEx;
import com.aquienllamo.aquienllamo.model.mappers.PortfolioMapper;
import com.aquienllamo.aquienllamo.model.repositories.PortfolioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PortfolioAdminService {

    private final PortfolioRepository repository;

    //buscar un portfolio por uuid
    public PortfolioAdminDTOResponse buscarPorfolioPorUuid(String uuid){
        PortfolioEntity portfolio = repository.findByUuid(uuid)
                .orElseThrow(() -> new PortfolioNotFoundEx("ERROR: No se encontro el portfolio que busca."));
        return PortfolioMapper.toResponseAdmin(portfolio);
    }

    //ver todos los portfolios
    public List<PortfolioAdminDTOResponse> listarPortfolios(){
        return repository.findAll()
                .stream()
                .map(PortfolioMapper::toResponseAdmin)
                .toList();
    }

    //ver listado de portafolios por estado
    public List<PortfolioAdminDTOResponse> listarPorEstado(EstadoVerificacion estadoVerificacion){
        return repository.findAllByEstadoVerificacion(estadoVerificacion)
                .stream()
                .map(PortfolioMapper::toResponseAdmin)
                .toList();
    }

    //ver listado de portafolios por fecha
    public List<PortfolioAdminDTOResponse> listarPorFecha(LocalDateTime desde, LocalDateTime hasta){
        return repository.findAllByFechaEntregaBetween(desde, hasta)
                .stream()
                .map(PortfolioMapper::toResponseAdmin)
                .toList();
    }

    //ver listado de portafolios por especialidad
    public List<PortfolioAdminDTOResponse> listarPorEspecialidad(String especialidad){
        return repository.findAllByEspecialidadNombreEspecialidad(especialidad)
                .stream()
                .map(PortfolioMapper::toResponseAdmin)
                .toList();
    }

    //obtener tecnico por uuid
    public List<PortfolioAdminDTOResponse> obtenerTecnicoPorUuid (String uuidTecnico){
        List<PortfolioEntity> portfolio = repository.findAllByTecnicoUuid(uuidTecnico);

        if(portfolio.isEmpty()){
            throw new PortfolioNotFoundEx("ERROR: El tecnico ingresado no posee portfolios.");
        }

        return portfolio.stream()
                .map(PortfolioMapper::toResponseAdmin)
                .toList();
    }

    //aceptar portfolio
    public PortfolioAdminDTOResponse aprobarPortfolio(String uuid, String notas){
        PortfolioEntity portfolio = repository.findByUuid(uuid)
                .orElseThrow(() -> new PortfolioNotFoundEx("ERROR: No se encontro el portfolio."));
        if(portfolio.getEstadoVerificacion() != EstadoVerificacion.Pendiente){
            throw new PortfolioAlreadyReviewedEx("ERROR: Solo se pueden revisar los portfolios pendientes.");
        }
        portfolio.setEstadoVerificacion(EstadoVerificacion.Aprobado);
        portfolio.setNotasAdmin(notas);
        return PortfolioMapper.toResponseAdmin(repository.save(portfolio));

    }

    //rechazar portfolio
    public PortfolioAdminDTOResponse rechazarPortfolio(String uuid, String notas){
        PortfolioEntity portfolio = repository.findByUuid(uuid)
                .orElseThrow(()-> new PortfolioNotFoundEx("ERROR: No se encontro el portfolio."));

        if(portfolio.getEstadoVerificacion() != EstadoVerificacion.Pendiente){
            throw new PortfolioAlreadyReviewedEx("ERROR: Solo se pueden revisar los portfolios pendientes.");
        }
        portfolio.setEstadoVerificacion(EstadoVerificacion.Rechazado);
        portfolio.setNotasAdmin(notas);
        return PortfolioMapper.toResponseAdmin(repository.save(portfolio));
    }

}
