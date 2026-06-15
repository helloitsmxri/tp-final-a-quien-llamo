package com.aquienllamo.aquienllamo.model.repositories;

import com.aquienllamo.aquienllamo.model.Enum.Estado;
import com.aquienllamo.aquienllamo.model.Enum.MetodoDePago;
import com.aquienllamo.aquienllamo.model.entities.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<PagoEntity,Integer> {

    //listar pagos segun un metodo de pago
    List<PagoEntity> findAllByMetodoDePago(MetodoDePago metodoDePago);

    //listar pagos segun el estado
    List<PagoEntity> findAllByEstadoPago(Estado estado);

    Optional<PagoEntity> findByUuid(String uuid);

    //para navegar en pago a trabajo a presupuesto a usuario al uuid de usuario
    @Query("SELECT p FROM PagoEntity p WHERE p.trabajo.presupuesto.usuario.uuid = :uuid")
    List<PagoEntity> findAllByCliente(@Param("uuid") String uuid);

    //lo mismo pero para tecnico
    @Query("SELECT p FROM PagoEntity p WHERE p.trabajo.presupuesto.tecnico.uuid = :uuid")
    List<PagoEntity> findAllByTecnico(@Param("uuid") String uuid);
}
