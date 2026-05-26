package com.aquienllamo.aquienllamo.model.repositories;


import com.aquienllamo.aquienllamo.model.entities.RatingEntity;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Integer> {

    //ver todas las reseñas que hizo un usuario
    List<RatingEntity> findAllByUsuarioRemitente(UsuarioEntity remitente);

    //ver todas las reseñas recibidas de un usuario
    List<RatingEntity> findAllByUsuarioDestinatario (UsuarioEntity destinatario);

}
