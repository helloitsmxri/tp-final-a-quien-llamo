package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.Enum.EstadoVerificacion;
import com.aquienllamo.aquienllamo.model.dtos.Request.PortfolioAdminDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.PortfolioAdminDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.PortfolioEntity;
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

    //actualizar estado de portfolio
    public PortfolioAdminDTOResponse revisionPortfolio(String uuid, PortfolioAdminDTORequest request){
        PortfolioEntity portfolio = repository.findByUuid(uuid)
                .orElseThrow(() -> new PortfolioNotFoundEx("ERROR: No se ha encontrado el portfolio ingresado."));

        portfolio.setEstadoVerificacion(request.getEstadoVerificacion());
        portfolio.setNotasAdmin(request.getNotasAdmin());

        return PortfolioMapper.toResponseAdmin(repository.save(portfolio));

    }


}
