package com.aquienllamo.aquienllamo.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "Presupuesto")
public class PresupuestoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_presupuesto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @ToString.Exclude // buscar info ¿?
    private UsuarioEntity usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tecnico", nullable = false)
    @ToString.Exclude
    private TecnicoEntity tecnico;

    @Column(name = "precio_estimado", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioEstimado;

    @Column(name = "descripcion_presupuesto", columnDefinition = "TEXT", nullable = false)
    private String descripcionPresupuesto;

}
