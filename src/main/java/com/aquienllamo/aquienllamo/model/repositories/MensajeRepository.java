package com.aquienllamo.aquienllamo.model.repositories;

import com.aquienllamo.aquienllamo.model.entities.MensajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MensajeRepository extends JpaRepository<MensajeEntity, Integer> {

    List<MensajeEntity> containsMensaje(String mensaje);
    Optional<MensajeEntity> findByChat_UuidChat(String uuidChat);
}
