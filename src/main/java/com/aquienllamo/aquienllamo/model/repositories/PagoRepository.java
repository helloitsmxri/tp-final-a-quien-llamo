package com.aquienllamo.aquienllamo.model.repositories;

import com.aquienllamo.aquienllamo.model.Enum.Estado;
import com.aquienllamo.aquienllamo.model.Enum.MetodoDePago;
import com.aquienllamo.aquienllamo.model.entities.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<PagoEntity,Integer> {

    //listar pagos segun un metodo de pago
    List<PagoEntity> findAllByMetodoDePago(MetodoDePago metodoDePago);

    //listar pagos segun el estado
    List<PagoEntity> findAllByEstadoPago(Estado estado);

    //listar pagos hechos por cliente
    List<PagoEntity> findAllByCliente (String uuid);

    //listar pagos recibidos por un tecnico
    List<PagoEntity> findAllByTecnico (String uuid);
}
