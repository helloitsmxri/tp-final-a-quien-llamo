package com.aquienllamo.aquienllamo.entidades.trabajo.entity;

import com.aquienllamo.aquienllamo.common.BaseEntity;
import com.aquienllamo.aquienllamo.entidades.presupuesto.entity.PresupuestoEntity;
import com.aquienllamo.aquienllamo.entidades.trabajo.enums.EstadoTrabajo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "Trabajo")
public class TrabajoEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTrabajo;

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @PrePersist
    public void generarUUID() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
    }

    @OneToOne
    @JoinColumn(name = "id_presupuesto", nullable = false, unique = true)
    private PresupuestoEntity presupuesto;

    @Column(name = "descripcion_trabajo", nullable = false, columnDefinition = "TEXT")
    private String descripcionTrabajo;

    @Column(name = "fecha_estimada_inicio", nullable = false)
    private LocalDate fechaEstimadaInicio;

    @Column(name = "fecha_estimada_fin", nullable = false)
    private LocalDate fechaEstimadaFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_trabajo", nullable = false)
    private EstadoTrabajo estadoTrabajo;
}