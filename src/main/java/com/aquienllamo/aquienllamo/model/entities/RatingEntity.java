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
@Table(name = "Rating")
public class RatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRating;

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @PrePersist
    public void generarUUID(){
        if(this.uuid == null){
            this.uuid = UUID.randomUUID().toString();
        }
    }

    @ManyToOne
    @JoinColumn(name = "id_usuario_remitente", nullable = false)
    private UsuarioEntity usuarioRemitente;

    @ManyToOne
    @JoinColumn(name = "id_usuario_destinatario", nullable = false)
    private UsuarioEntity usuarioDestinatario;

    @Column(name = "valoracion", nullable = false)
    private Integer valoracion;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "tipo_foto", length = 50)
    private String tipoFoto;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    //tabla intermedia de RATING Y CARACTERISTICAS
    @ManyToMany
    @JoinTable(name = "Rating_Caracteristica",
    joinColumns = @JoinColumn(name = "id_rating"),
    inverseJoinColumns = @JoinColumn(name = "id_caracteristica"))
    private List<CaracteristicaEntity> caracteristicas;
    //List<CaracteristicaEntity> caracteristicas =
    //    caracteristicaRepository.findAllById(dto.getIdCaracteristicas());
}
