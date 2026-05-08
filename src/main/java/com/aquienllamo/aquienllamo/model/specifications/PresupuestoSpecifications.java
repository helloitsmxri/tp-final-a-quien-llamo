package com.aquienllamo.aquienllamo.model.specifications;


import com.aquienllamo.aquienllamo.model.entities.PresupuestoEntity;
import org.springframework.data.jpa.domain.PredicateSpecification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PresupuestoSpecifications {

    // filtrar presupuestos mayores a ese precio:
    public static PredicateSpecification<PresupuestoEntity> presupuestoGreaterThan(BigDecimal price){
        return (entity, criteriaBuilder) -> price == null // ¿es el precio nulo?
                ? criteriaBuilder.conjunction() // si es nulo, no necesitamos filtro
                : criteriaBuilder.greaterThanOrEqualTo(entity.get("precioEstimado"), price); // si hay un num buscamos q sea igual o mayor

    }

    // filtrar presupuestos menores a ese precio:
    public static PredicateSpecification<PresupuestoEntity> presupuestoLesserThan(BigDecimal price){
        return (entity, criteriaBuilder) -> price == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.lessThanOrEqualTo(entity.get("precioEstimado"), price);
    }

//    public static PredicateSpecification<PresupuestoEntity> presupuestoDeTalPersona(String nombre){
//        return (entity, criteriaBuilder) -> nombre == null
//                ? criteriaBuilder.conjunction()
//                : criteriaBuilder.like(criteriaBuilder.lower(entity.get("usuario").get("nombre")), "%"+nombre+"%"); // like lower: nombre pasado a minusculas...
//    }

    public static PredicateSpecification<PresupuestoEntity> presupuestoDeTalPersonaNombre(String nombre){
        return (entity, criteriaBuilder) -> (nombre == null || nombre.isBlank())
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.lower(entity.get("usuario").get("nombre")), "%"+nombre.toLowerCase()+"%"), // acá termina UNA condición
                criteriaBuilder.like(criteriaBuilder.lower(entity.get("tecnico").get("usuario").get("nombre")), "%"+nombre.toLowerCase()+"%") // segunda condición
        ); // este paréntesis es del analisis d la palabra nombre

    }

    public static PredicateSpecification<PresupuestoEntity> presupuestoDeTalPersonaApellido(String apellido){
        return (entity, criteriaBuilder) -> (apellido == null || apellido.isBlank())
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.lower(entity.get("usuario").get("apellido")), "%"+apellido.toLowerCase()+"%"),
                criteriaBuilder.like(criteriaBuilder.lower(entity.get("tecnico").get("usuario").get("apellido")), "%"+apellido.toLowerCase()+"%"));
    }


    public static PredicateSpecification<PresupuestoEntity> presupuestoDeTalFecha(LocalDate fecha){
        return (entity, criteriaBuilder) -> fecha == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.greaterThanOrEqualTo(entity.get("fechaRealizado"), fecha);
    }
}
