package com.aquienllamo.aquienllamo.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "Administrador")
public class AdministradorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_admin")
    private Integer idAdmin;

    @Column(nullable = false, length = 50, name = "nombre_usuario")
    private String nombreUsuario;

    @Column(nullable = false, length = 20)
    private String clave;

    @Column(name ="uuid", nullable = false, unique = true)
    private String uuid;

    @PrePersist
    public void generarUUID()
    {
        if(this.uuid == null)
        {
            this.uuid = UUID.randomUUID().toString();
        }
    }

}
