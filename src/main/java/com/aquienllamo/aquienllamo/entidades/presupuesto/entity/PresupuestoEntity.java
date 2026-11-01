package com.aquienllamo.aquienllamo.entidades.presupuesto.entity;

import com.aquienllamo.aquienllamo.entidades.chat.entity.ChatEntity;
import com.aquienllamo.aquienllamo.common.BaseEntity;
import com.aquienllamo.aquienllamo.entidades.presupuesto.enums.EstadoPresupuestoE;
import com.aquienllamo.aquienllamo.entidades.tecnico.entity.TecnicoEntity;
import com.aquienllamo.aquienllamo.entidades.usuario.entities.UsuarioEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "Presupuesto")
public class PresupuestoEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_presupuesto;

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;
    @PrePersist
    public void generarUUID(){
        if(this.uuid == null){
            this.uuid = UUID.randomUUID().toString();
        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @ToString.Exclude // buscar info ¿?
    private UsuarioEntity usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chat", nullable = false)
    private ChatEntity chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tecnico", nullable = false)
    @ToString.Exclude
    private TecnicoEntity tecnico;

    @Column(name = "precio_estimado", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioEstimado;

    @Column(name = "descripcion_presupuesto", columnDefinition = "TEXT", nullable = false)
    private String descripcionPresupuesto;


    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoPresupuestoE estado;

}
