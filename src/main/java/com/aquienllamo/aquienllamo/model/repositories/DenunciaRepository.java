package com.aquienllamo.aquienllamo.model.repositories;

import com.aquienllamo.aquienllamo.model.Enum.EstadoDenunciaE;
import com.aquienllamo.aquienllamo.model.entities.DenunciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DenunciaRepository extends JpaRepository<DenunciaEntity, Integer> {
    Optional<DenunciaEntity> findByUuid(String uuid);
    List<DenunciaEntity> findByEstadoDenuncia(EstadoDenunciaE estado);
    List<DenunciaEntity> findByAdministradorIsNull();
}
