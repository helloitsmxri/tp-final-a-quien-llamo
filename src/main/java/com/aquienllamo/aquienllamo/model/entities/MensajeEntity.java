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
@Table(name = "Mensaje")

public class MensajeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_mensaje")
    private Integer idMensaje;

    @ManyToOne
    @JoinColumn(name= "id_chat", nullable = true)
    private ChatEntity idChat;

    @Lob // el mensaje es de tipo TEXT, uso Lob (Large Object) para mapearlo
    @Column(nullable = false)
    private String mensaje;

}
