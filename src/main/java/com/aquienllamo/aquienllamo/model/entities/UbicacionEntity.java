package com.aquienllamo.aquienllamo.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "Ubicacion")
public class UbicacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_ubicacion;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = true)
    private UsuarioEntity id_usuario;//mapeo la foreign key porque una ubicacion pertenece a un usuario.
    //se pone como la entidad UsuarioEntity directamente.

    @Column(nullable = false, length = 50)
    private String codigo_postal;

    @Column(nullable = false, length = 50)
    private String provincia;

    @Column(nullable = false, length = 50)
    private String ciudad;

    @Column(nullable = false, length = 50)
    private String calle;

    @Column
    private Integer numero;

    @Column
    private Integer piso;

    @Column
    private Integer numero_piso;

}
