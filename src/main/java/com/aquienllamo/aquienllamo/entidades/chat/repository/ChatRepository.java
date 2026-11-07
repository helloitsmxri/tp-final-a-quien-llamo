package com.aquienllamo.aquienllamo.entidades.chat.repository;

import com.aquienllamo.aquienllamo.entidades.chat.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

    Optional<ChatEntity> findByUuidChat(String uuidChat);
    List<ChatEntity> findByUsuario_Uuid(String uuidUsuario);
    List<ChatEntity> findByTecnico_Uuid(String uuidTecnico);

    // Para no crear chats duplicados entre el mismo cliente y técnico
    Optional<ChatEntity> findByUsuario_UuidAndTecnico_Uuid(String uuidUsuario, String uuidTecnico);

    // Mis chats (como cliente o como técnico), solo los que ya tienen al menos un mensaje
    @Query("""
           SELECT c FROM ChatEntity c
           WHERE (c.usuario.email = :email OR c.tecnico.usuario.email = :email)
             AND EXISTS (SELECT m FROM MensajeEntity m WHERE m.chat = c)
           ORDER BY c.fechaChat DESC
           """)
    List<ChatEntity> findMisChats(@Param("email") String email);
}