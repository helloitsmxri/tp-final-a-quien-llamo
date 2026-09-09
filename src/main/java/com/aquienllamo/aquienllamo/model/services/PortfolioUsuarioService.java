package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.dtos.Request.PortfolioUsuarioDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.portfolio.PortfolioUsuarioDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.EspecialidadEntity;
import com.aquienllamo.aquienllamo.model.entities.PortfolioEntity;
import com.aquienllamo.aquienllamo.model.entities.TecnicoEntity;
import com.aquienllamo.aquienllamo.model.exceptions.PortfolioAlreadyExistsEx;
import com.aquienllamo.aquienllamo.model.exceptions.PortfolioNotFoundEx;
import com.aquienllamo.aquienllamo.model.mappers.PortfolioMapper;
import com.aquienllamo.aquienllamo.model.repositories.EspecialidadRepository;
import com.aquienllamo.aquienllamo.model.repositories.PortfolioRepository;
import com.aquienllamo.aquienllamo.model.repositories.TecnicoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PortfolioUsuarioService {

    private final PortfolioRepository portfolioRepository;
    private final TecnicoRepository tecnicoRepository;
    private final EspecialidadRepository especialidadRepository;

    //crear portfolio
    public PortfolioUsuarioDTOResponse crearPortafolio (String uuidTecnico ,PortfolioUsuarioDTORequest request){

        TecnicoEntity tecnico = tecnicoRepository.findByUuid(uuidTecnico).get();
        EspecialidadEntity especialidad = especialidadRepository.findByUuid(request.getUuidEspecialidad()).get();

        if(portfolioRepository.existsByTecnicoAndEspecialidad(tecnico,especialidad)){
            throw new PortfolioAlreadyExistsEx("ERROR: Ya existe un portfolio con esas caracteristicas.");
        }

        return PortfolioMapper.toResponseUsuario(portfolioRepository.save(PortfolioMapper.toEntity(request,tecnico,especialidad)));

    }

    //ver estado de portafolio
    public PortfolioUsuarioDTOResponse verPortfolio (String uuidPortfolio){
        PortfolioEntity portfolio = portfolioRepository.findByUuid(uuidPortfolio)
                .orElseThrow(()-> new PortfolioNotFoundEx("ERROR: No se ha encontrado el portfolio ingresado."));
        return PortfolioMapper.toResponseUsuario(portfolio);
    }

    //ver listado de portfolios enviados por el usuario
    public List<PortfolioUsuarioDTOResponse> verListadoPortfolios (String uuidTecnico){

        List<PortfolioEntity> portfolio = portfolioRepository.findAllByTecnicoUuid(uuidTecnico);

        if(portfolio.isEmpty()){
            throw new PortfolioNotFoundEx("ERROR: No posee portfolios cargados.");
        }

        return portfolio.stream()
                .map(PortfolioMapper::toResponseUsuario)
                .toList();
    }

}
