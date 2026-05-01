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
    @Column (name = "id_chat")
    private Integer idChat;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = true)
    private UsuarioEntity idUsuario;

    @ManyToOne
    @JoinColumn(name = "id_tecnico", nullable = true)
    private TecnicoEntity idTecnico;

}
