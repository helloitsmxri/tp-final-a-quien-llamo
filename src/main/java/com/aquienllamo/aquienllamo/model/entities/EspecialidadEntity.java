package com.aquienllamo.aquienllamo.model.entities;

import com.aquienllamo.aquienllamo.model.Enum.TipoValidacion;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Table(name = "Especialidad")
public class EspecialidadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_especialidad")
    private Integer idEspecialidad;

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @PrePersist
    public void generarUUID() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
    }

    @Column(length = 50, name = "nombre_especialidad")
    private String nombreEspecialidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_validacion")
    private TipoValidacion tipoValidacion;
}
