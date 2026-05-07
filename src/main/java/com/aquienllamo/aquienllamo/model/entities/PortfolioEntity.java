package com.aquienllamo.aquienllamo.model.entities;

import com.aquienllamo.aquienllamo.model.Enum.EstadoVerificacion;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "Portfolio")
public class PortfolioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_portfolio")
    private Integer idPortfolio;

    @Column(name = "uuid",nullable = false,unique = true)
    private String uuid;

    @PrePersist
    public void generarUUID(){
        if(this.uuid == null){
            this.uuid = UUID.randomUUID().toString();
        }
    }

    @ManyToOne
    @JoinColumn(name = "id_tecnico", nullable = false)
    private TecnicoEntity tecnico;

    @ManyToOne
    @JoinColumn(name = "id_especialidad", nullable = false)
    private EspecialidadEntity especialidad;

    @Lob
    @Column(name = "notas_aspirante")
    private String notasAspirante;

    @Column(name = "enlace_externo", length = 255)
    private String enlaceExterno;

    @Column(name = "tipo_archivo", length = 100)
    private String tipoArchivo;

    @Lob
    @Column(name = "archivo_adjunto")
    private byte[] archivoAdjunto;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_verificacion")
    private EstadoVerificacion estadoVerificacion = EstadoVerificacion.Pendiente;

    @Lob
    @Column(name = "notas_admin")
    private String notasAdmin;

    @Column(name = "fecha_entrega", updatable = false, insertable = false)
    private LocalDateTime fechaEntrega;
    
}
