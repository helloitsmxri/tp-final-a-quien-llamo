package com.aquienllamo.aquienllamo.model.repositories;


import com.aquienllamo.aquienllamo.model.entities.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface RatingRepository extends JpaRepository<RatingRepository, Integer> {

    //ver todas las reseñas de un tecnico/usuario
    List<RatingEntity> findByIdRecipientUser(Integer idUser);

    //ver todas las reseñas hechas por un usuario
    List<RatingEntity> findByIdSenderUser (Integer idUser);

    //promedio de valoracion total
    //SELECT AVG(r.valoracion) -> calcula promedio
    //FROM RatingEntity r -> se una la entidad no la tabla de la bdd
    //r.usuarioDestinatario.idUsuario -> el id del usuario que recibio la reseña
    //:idUsuario -> el valor que remplada spring
    @Query(""" 
            SELECT AVG(r.valoracion)
            FROM RatingEntity r
            WHERE r.usuarioDestinatario.idUsuario = :idUsuario""")
    Double averageRating(Integer idUser);
    
}
