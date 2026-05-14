package com.aquienllamo.aquienllamo.model.entities;

import com.aquienllamo.aquienllamo.model.Enum.EstadoTrabajo;
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
public class TrabajoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTrabajo;

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @PrePersist
    public void generarUUID(){
        if(this.uuid == null){
            this.uuid = UUID.randomUUID().toString();
        }
    }

    @OneToOne
    @JoinColumn(name = "id_presupuesto")
    private PresupuestoEntity Presupuesto;
    //presupuestoRepository.findByUuid(dto.getUuidPresupuesto())

    @Lob
    @Column(name = "descripcion_trabajo", nullable = false)
    private String descripcionTrabajo;

    @Column(name = "fecha_estimada_inicio", nullable = false)
    private LocalDate fechaEstimadaInicio;

    @Column(name = "fecha_estimada_fin", nullable = false)
    private LocalDate fechaEstimadaFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_trabajo", nullable = false)
    private EstadoTrabajo estadoTrabajo;
    
}
