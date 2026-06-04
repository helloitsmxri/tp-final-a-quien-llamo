package com.aquienllamo.aquienllamo.model.auth.permissions;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Permit")
@Builder
public class PermitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permit")
    private Integer idPermit;

    @Enumerated(EnumType.STRING)
    @Column(name = "permiso", nullable = false, unique = true)
    private RolePermits permits;
}
