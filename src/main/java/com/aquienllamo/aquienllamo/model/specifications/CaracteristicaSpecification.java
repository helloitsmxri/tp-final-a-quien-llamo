package com.aquienllamo.aquienllamo.model.specifications;

import com.aquienllamo.aquienllamo.model.Enum.TipoCaracteristicaE;
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



    // filtrar características x positivas o negativas? ¿capaz? -> creo q no sería necesario
    public static PredicateSpecification<CaracteristicaEntity> tipoEs(TipoCaracteristicaE tipo){
        return (root, cb) -> (tipo == null)
                ? cb.conjunction()
                : cb.equal(root.get("tipo"),tipo);
    }
}
