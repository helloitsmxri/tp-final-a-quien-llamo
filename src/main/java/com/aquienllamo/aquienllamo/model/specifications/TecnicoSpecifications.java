package com.aquienllamo.aquienllamo.model.specifications;

import com.aquienllamo.aquienllamo.model.entities.TecnicoEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TecnicoSpecifications {

    //filtrar por nombre
    public static Specification<TecnicoEntity> porNombre(String nombre) {
        return (entity, query, criteriaBuilder) -> (nombre == null || nombre.isBlank())
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.like(criteriaBuilder.lower(entity.get("usuario").get("nombre")), "%"+nombre.toLowerCase()+"%");
    }

    //filtrar por apellido
    public static Specification<TecnicoEntity> porApellido(String apellido) {
        return (entity, query, criteriaBuilder) -> (apellido == null || apellido.isBlank())
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.like(criteriaBuilder.lower(entity.get("usuario").get("apellido")), "%"+apellido.toLowerCase()+"%");
    }

    //filtrar por habilidad
    public static Specification<TecnicoEntity> porHabilidad(String habilidad) {
        return (entity, query, criteriaBuilder) -> (habilidad == null || habilidad.isBlank())
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.like(criteriaBuilder.lower(entity.join("habilidades").get("nombreHabilidad")), "%"+habilidad.toLowerCase()+"%");
    }

    //filtrar por especialidad
    public static Specification<TecnicoEntity> porEspecialidad(String especialidad) {
        return (entity, query, criteriaBuilder) -> (especialidad == null || especialidad.isBlank())
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.like(criteriaBuilder.lower(entity.join("especialidades").get("nombreEspecialidad")), "%"+especialidad.toLowerCase()+"%");
    }

    //filtrar por fecha de registro
    public static Specification<TecnicoEntity> porFechaRegistro(LocalDateTime fecha) {
        return (entity, query, criteriaBuilder) -> fecha == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.greaterThanOrEqualTo(entity.get("usuario").get("fechaRegistro"), fecha);
    }
}
