package com.aquienllamo.aquienllamo.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "Caracteristica")
public class CaracteristicaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caracteristica")
    private Integer idCaracteristica;

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @PrePersist
    public void generarUUID(){
        if (this.uuid == null){
            this.uuid = UUID.randomUUID().toString();
        }
    }

    @Column(name = "valor_adjetivo", length = 50)
    private String valorAdjetivo;

    // relaciones q están en la bdd
    @ManyToMany(mappedBy = "caracteristicas")
    private List<RatingEntity> ratings;
}
