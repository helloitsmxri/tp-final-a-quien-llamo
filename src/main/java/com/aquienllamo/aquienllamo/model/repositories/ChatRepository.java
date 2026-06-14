package com.aquienllamo.aquienllamo.model.repositories;

import com.aquienllamo.aquienllamo.model.entities.ChatEntity;
import com.aquienllamo.aquienllamo.model.entities.HabilidadEntity;
import com.aquienllamo.aquienllamo.model.entities.TecnicoEntity;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

    Optional<ChatEntity> findByUuidChat(String uuidChat);
    List<ChatEntity> findByUsuario_Uuid(String uuidUsuario);
    List<ChatEntity> findByTecnico_Uuid(String uuidTecnico);
    boolean existsByUsuario_IdUsuarioAndTecnico_IdTecnico(Integer idUsuario, Integer idTecnico); // para evitar que se creen chats duplicados entre el mismo usuario y tecnico
}
