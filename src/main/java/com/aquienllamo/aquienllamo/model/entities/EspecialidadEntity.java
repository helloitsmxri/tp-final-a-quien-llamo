package com.aquienllamo.aquienllamo.model.entities;

import com.aquienllamo.aquienllamo.model.Enum.TipoValidacion;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
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

    //Rubro_especialidad
    @ManyToMany(mappedBy = "especialidades")
    private List<RubroEntity> rubros;

    // Habilidad_Especialidad mappedBy porque HabilidadEntity es la dueña
    @ManyToMany(mappedBy = "especialidades")
    private List<HabilidadEntity> habilidades;

    // Especialidad_Tecnico mappedBy porque TecnicoEntity es la dueña
    @ManyToMany(mappedBy = "especialidades")
    private List<TecnicoEntity> tecnicos;

}
