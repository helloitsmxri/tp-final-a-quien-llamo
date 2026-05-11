package com.aquienllamo.aquienllamo.model.repositories;


import com.aquienllamo.aquienllamo.model.entities.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RatingRepository extends JpaRepository<RatingRepository, Integer> {

    //ver todas las reseñas de un tecnico/usuario
    List<RatingEntity> findByIdRecipientUser(Integer idUser);

    //ver todas las reseñas hechas por un usuario
    List<RatingEntity> findByIdSenderUser (Integer idUser);

}
