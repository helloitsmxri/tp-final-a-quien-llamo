package com.aquienllamo.aquienllamo.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "Certificacion")
public class CertificacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_certificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tecnico", referencedColumnName = "id_tecnico")
    private TecnicoEntity id_tecnico;

    @Column(nullable = false, length = 100)
    private String num_matricula;

    @Column(nullable = false, length = 100)
    private String ente_otorgador;

    @Column
    private LocalDate fecha_vencimiento;

    @Column(nullable = false, length = 50)
    private String tipo_imagen;

    @Lob
    @Column(nullable = false)
    private byte[] imagen_certificado;
}
