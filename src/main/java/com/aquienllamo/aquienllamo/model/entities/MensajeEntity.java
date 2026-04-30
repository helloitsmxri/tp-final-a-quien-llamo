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
    private Integer id_mensaje;

    @ManyToOne
    @JoinColumn(name= "id_chat", nullable = true)
    private ChatEntity id_chat;

    @Lob // el mensaje es de tipo TEXT, uso Lob (Large Object) para mapearlo
    @Column(nullable = false)
    private String mensaje;

}
