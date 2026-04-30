package com.aquienllamo.aquienllamo.model.entities;

import com.aquienllamo.aquienllamo.model.Enum.EstadoTrabajo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "Trabajo")
public class TrabajoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTrabajo;

    @OneToOne
    @JoinColumn(name = "id_presupuesto")
    private PresupuestoEntity idPresupuesto;

    @Column(name = "descripcion_trabajo", columnDefinition = "TEXT", nullable = false)
    private String descripcionTrabajo;

    @Column(name = "fecha_estimada_inicio", nullable = false)
    private LocalDate fechaEstimadaInicio;

    @Column(name = "fecha_estimada_fin", nullable = false)
    private LocalDate fechaEstimadaFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_trabajo", nullable = false)
    private EstadoTrabajo estadoTrabajo;
    
}
