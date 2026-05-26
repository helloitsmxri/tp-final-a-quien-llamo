package com.aquienllamo.aquienllamo.model.repositories;

import com.aquienllamo.aquienllamo.model.entities.ChatEntity;
import com.aquienllamo.aquienllamo.model.entities.HabilidadEntity;
import com.aquienllamo.aquienllamo.model.entities.TecnicoEntity;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

    Optional<ChatEntity> findById(Long idChat);
    List<ChatEntity> findAllByUsuario(UsuarioEntity usuario);
    List<ChatEntity> findByTecnico(TecnicoEntity tecnico);
}
