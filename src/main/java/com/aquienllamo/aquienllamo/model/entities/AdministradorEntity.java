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

}
