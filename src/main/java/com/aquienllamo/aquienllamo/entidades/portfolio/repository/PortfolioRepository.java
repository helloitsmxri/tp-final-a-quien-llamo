package com.aquienllamo.aquienllamo.entidades.portfolio.repository;

import com.aquienllamo.aquienllamo.administrador.EstadoVerificacion;
import com.aquienllamo.aquienllamo.entidades.especialidad.entity.EspecialidadEntity;
import com.aquienllamo.aquienllamo.entidades.portfolio.entity.PortfolioEntity;
import com.aquienllamo.aquienllamo.entidades.tecnico.entity.TecnicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Integer> {

    //listar portafolios por el estado de verificacion
    List<PortfolioEntity> findAllByEstadoVerificacion (EstadoVerificacion estadoVerificacion);

    //listar por fecha de entrega
    List<PortfolioEntity> findAllByFechaEntregaBetween(LocalDateTime desde, LocalDateTime hasta);

    //listar todos los portfolios de un tecnico
    List<PortfolioEntity> findAllByTecnicoUuid (String uuidTecnico);

    //listar los portafolios por especialidad
    List<PortfolioEntity> findAllByEspecialidadNombreEspecialidad (String nombreEspecialidad);

    //buscar un portfolio
    Optional<PortfolioEntity> findByUuid(String uuid);

    //verificar si existe un portfolio segun un tecnico y especialidad
    //porque no debe de haber dos portfolios con una misma especialidad y tecnico
    Boolean existsByTecnicoAndEspecialidad(TecnicoEntity tecnico, EspecialidadEntity especialidad);
}
