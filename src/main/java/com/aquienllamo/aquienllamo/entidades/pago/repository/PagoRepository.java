package com.aquienllamo.aquienllamo.entidades.pago.repository;

import com.aquienllamo.aquienllamo.entidades.pago.enums.EstadoPago;
import com.aquienllamo.aquienllamo.entidades.pago.enums.MetodoDePago;
import com.aquienllamo.aquienllamo.entidades.pago.entity.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<PagoEntity,Integer> {

        Optional<PagoEntity> findByUuid(String uuid);
        Optional<PagoEntity> findByDatosProveedor_ExternalPaymentId(String externalPaymentId);
        boolean existsByTrabajo_Uuid(String uuidTrabajo);

    //listar pagos segun un metodo de pago
    List<PagoEntity> findAllByMetodoDePago(MetodoDePago metodoDePago);

    //listar pagos segun el estado
    List<PagoEntity> findAllByEstadoPago(EstadoPago estado);

    //para navegar en pago a trabajo a presupuesto a usuario al uuid de usuario
    @Query("SELECT p FROM PagoEntity p WHERE p.trabajo.presupuesto.usuario.uuid = :uuid")
    List<PagoEntity> findAllByCliente(@Param("uuid") String uuid);

    //lo mismo pero para tecnico
    @Query("SELECT p FROM PagoEntity p WHERE p.trabajo.presupuesto.tecnico.uuid = :uuid")
    List<PagoEntity> findAllByTecnico(@Param("uuid") String uuid);

}
