package com.aquienllamo.aquienllamo.model.specifications;

import com.aquienllamo.aquienllamo.model.entities.CaracteristicaEntity;
import com.aquienllamo.aquienllamo.model.entities.RatingEntity;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.PredicateSpecification;

public class CaracteristicaSpecification {
    // métodos estáticos p/ejecutar en el service

    // ¿podría ser filtrar reseñas x una característica en particular? tipo, parte del nombre 'puntual' = 'pun'...??
    public static PredicateSpecification<CaracteristicaEntity> buscarPorPalabra(String palabraParcial) {
        return (entity, criteriaBuilder) -> (palabraParcial == null || palabraParcial.isBlank())
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.like(criteriaBuilder.lower(entity.get("valorAdjetivo")), "%" + palabraParcial.toLowerCase() + "%");
    }

    // podría ser filtrar caracteristicas x valoración de la reseña? -> tengo q analizarlo bien porq capaz no nos sirve

    public static PredicateSpecification<CaracteristicaEntity> asociadasAPuntaje(Integer estrellas) {
        return (entity, criteriaBuilder) -> {
            Join<CaracteristicaEntity, RatingEntity> ratingsJoin = entity.join("ratings");
            return criteriaBuilder.equal(ratingsJoin.get("puntuacion"), estrellas);
        };

    }

    // filtrar características x positivas o negativas? ¿capaz? -> creo q no sería necesario
}
