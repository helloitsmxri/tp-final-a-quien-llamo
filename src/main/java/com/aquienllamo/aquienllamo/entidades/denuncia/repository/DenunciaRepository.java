package com.aquienllamo.aquienllamo.entidades.denuncia.repository;

import com.aquienllamo.aquienllamo.entidades.denuncia.enums.EstadoDenunciaE;
import com.aquienllamo.aquienllamo.entidades.denuncia.entity.DenunciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DenunciaRepository extends JpaRepository<DenunciaEntity, Integer> {
    Optional<DenunciaEntity> findByUuid(String uuid);
    List<DenunciaEntity> findByEstadoDenuncia(EstadoDenunciaE estado);
    List<DenunciaEntity> findByAdministradorIsNull();
    List<DenunciaEntity> findAllByOrderByFechaDenunciaAsc();
    List<DenunciaEntity> findAllByOrderByFechaDenunciaDesc();
    List<DenunciaEntity> findByAdministradorUuid(String uuidAdmin);
    List<DenunciaEntity> findByAdministradorUuidAndEstadoDenuncia(String uuidAdmin, EstadoDenunciaE estado);
}
