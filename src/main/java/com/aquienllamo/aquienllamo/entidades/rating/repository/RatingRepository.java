package com.aquienllamo.aquienllamo.entidades.rating.repository;

import com.aquienllamo.aquienllamo.entidades.rating.entity.RatingEntity;
import com.aquienllamo.aquienllamo.entidades.trabajo.entity.TrabajoEntity;
import com.aquienllamo.aquienllamo.entidades.usuario.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Integer> {

    //ver todas las reseñas que hizo un usuario
    List<RatingEntity> findAllByUsuarioRemitente(UsuarioEntity remitente);

    //ver todas las reseñas recibidas de un usuario
    List<RatingEntity> findAllByUsuarioDestinatario (UsuarioEntity destinatario);

    boolean existsByUsuarioRemitenteAndTrabajo(
            UsuarioEntity remitente,
            TrabajoEntity trabajo
    );

}
