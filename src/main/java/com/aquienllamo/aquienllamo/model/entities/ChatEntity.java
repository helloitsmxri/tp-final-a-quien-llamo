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

    @Override
    public String toString() {
        return "ChatEntity{" +
                "id_chat=" + id_chat +
                ", id_usuario=" + id_usuario +
                ", id_tecnico=" + id_tecnico +
                '}';
    }
}
