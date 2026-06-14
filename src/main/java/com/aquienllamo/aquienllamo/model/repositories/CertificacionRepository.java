package com.aquienllamo.aquienllamo.model.repositories;

import com.aquienllamo.aquienllamo.model.Enum.EstadoVerificacion;
import com.aquienllamo.aquienllamo.model.entities.CertificacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificacionRepository extends JpaRepository<CertificacionEntity, Integer>{
    Optional<CertificacionEntity> findByUuid(String uuid);
    List<CertificacionEntity> findByTecnico_Uuid(String uuidTecnico);
    List<CertificacionEntity> findByEstadoVerificacion(EstadoVerificacion estadoVerificacion);
}
