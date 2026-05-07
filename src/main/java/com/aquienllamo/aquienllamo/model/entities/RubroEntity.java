package com.aquienllamo.aquienllamo.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

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

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @PrePersist
    public void generarUUID(){
        if(this.uuid == null){
            this.uuid = UUID.randomUUID().toString();
        }
    }

    @Lob
    @Column(name = "nombre_rubro")
    private String nombreRubro;

    //union de la tabla rubro con la tabla especialidades
    @ManyToMany
    @JoinTable(name = "Rubro_Especialidad",
               joinColumns = @JoinColumn(name = "id_rubro"),
               inverseJoinColumns = @JoinColumn(name = "id_especialidad"))
    private List<EspecialidadEntity> especialidades;


}
