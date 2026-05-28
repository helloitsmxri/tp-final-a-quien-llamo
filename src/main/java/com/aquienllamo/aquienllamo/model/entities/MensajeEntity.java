package com.aquienllamo.aquienllamo.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

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
    @JoinColumn(name ="id_sender", nullable = false) // SENDER: para saber quién envió el mensaje a la hora de chattear.
    private UsuarioEntity sender;

    @ManyToOne
    @JoinColumn(name= "id_chat", nullable = false)
    private ChatEntity chat;

    @Column(nullable = false)
    private String mensaje;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime fechaMensaje;

    @Column(name ="uuid", nullable = false)
    private String uuidMensaje;

    @PrePersist
    public void generarUUID()
    {
        if(this.uuidMensaje == null)
        {
            this.uuidMensaje = UUID.randomUUID().toString();
        }
    }

}
