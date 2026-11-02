package com.aquienllamo.aquienllamo.entidades.certificacion.repository;

import com.aquienllamo.aquienllamo.administrador.enums.EstadoVerificacion;
import com.aquienllamo.aquienllamo.entidades.certificacion.entity.CertificacionEntity;
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
