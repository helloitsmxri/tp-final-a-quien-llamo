package com.aquienllamo.aquienllamo.model.auth.permissions;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Rol")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol",nullable = false, unique = true)
    private RolesUser role;

}