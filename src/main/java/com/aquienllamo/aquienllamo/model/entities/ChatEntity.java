package com.aquienllamo.aquienllamo.model.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "Chat")
@ToString
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_chat;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = true)
    private UsuarioEntity id_usuario;

    @ManyToOne
    @JoinColumn(name = "id_tecnico", nullable = true)
    private TecnicoEntity id_tecnico;

}
