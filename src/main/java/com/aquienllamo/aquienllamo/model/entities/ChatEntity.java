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
@Table(name = "Chat")
@ToString
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_chat")
    private Integer idChat;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "id_tecnico", nullable = false)
    private TecnicoEntity tecnico;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime fechaChat;

    @Column(name ="uuid", nullable = false)
    private String uuidChat;

    @PrePersist
    public void generarUUID()
    {
        if(this.uuidChat == null)
        {
            this.uuidChat = UUID.randomUUID().toString();
        }
    }

}
