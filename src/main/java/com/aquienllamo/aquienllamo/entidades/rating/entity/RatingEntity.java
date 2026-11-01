
package com.aquienllamo.aquienllamo.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(
        name = "Rating",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_rating_remitente_trabajo",
                        columnNames = {
                                "id_usuario_remitente",
                                "id_trabajo"
                        }
                )
        }
)
public class RatingEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRating;

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @PrePersist
    public void generarUUID() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
    }

    @ManyToOne
    @JoinColumn(name = "id_usuario_remitente", nullable = false)
    private UsuarioEntity usuarioRemitente;

    @ManyToOne
    @JoinColumn(name = "id_usuario_destinatario", nullable = false)
    private UsuarioEntity usuarioDestinatario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_trabajo", nullable = false)
    private TrabajoEntity trabajo;

    @Column(name = "valoracion", nullable = false)
    private Integer valoracion;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "tipo_foto", length = 50)
    private String tipoFoto;

    @Lob
    @Column(name = "foto", columnDefinition = "MEDIUMBLOB")
    private byte[] foto;

    // Tabla intermedia de Rating y Caracteristicas
    @ManyToMany
    @JoinTable(
            name = "Rating_Caracteristica",
            joinColumns = @JoinColumn(name = "id_rating"),
            inverseJoinColumns = @JoinColumn(name = "id_caracteristica")
    )
    private List<CaracteristicaEntity> caracteristicas;
}
