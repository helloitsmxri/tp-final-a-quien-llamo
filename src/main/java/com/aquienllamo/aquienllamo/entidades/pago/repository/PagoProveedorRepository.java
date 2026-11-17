package com.aquienllamo.aquienllamo.entidades.pago.repository;

import com.aquienllamo.aquienllamo.entidades.pago.entity.PagoProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PagoProveedorRepository extends JpaRepository<PagoProveedorEntity, Long> {

    Optional<PagoProveedorEntity> findByPago_Uuid(String uuid);

}
