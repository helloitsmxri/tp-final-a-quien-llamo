package com.aquienllamo.aquienllamo.model.entities;

import jakarta.persistence.*;
import lombok.*;

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
    private Integer id_rating;

    @ManyToOne
    @JoinColumn(name = "id_usuario_remitente")
    private UsuarioEntity id_usuario_remitente;

    @ManyToOne
    @JoinColumn(name = "id_usuario_destinatario")
    private UsuarioEntity id_usuario_destinatario;

    @Column(name = "valoracion",nullable = false)
    private Integer valoracion;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
}
