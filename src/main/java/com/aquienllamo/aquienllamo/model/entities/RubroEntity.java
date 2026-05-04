package com.aquienllamo.aquienllamo.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "Rubro")
public class RubroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rubro")
    private Integer idRubro;

    @Lob
    @Column(name = "nombre_rubro")
    private String nombreRubro;



}
