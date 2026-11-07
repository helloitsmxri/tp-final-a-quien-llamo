package com.aquienllamo.aquienllamo.entidades.mensaje.repository;

import com.aquienllamo.aquienllamo.entidades.mensaje.entity.MensajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MensajeRepository extends JpaRepository<MensajeEntity, Integer> {

    List<MensajeEntity> findByChat_UuidChatOrderByFechaMensajeAsc(String uuidChat);
    Optional<MensajeEntity> findByUuidMensaje(String uuidMensaje);
}