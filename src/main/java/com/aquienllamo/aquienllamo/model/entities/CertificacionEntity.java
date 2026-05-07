package com.aquienllamo.aquienllamo.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

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
    @Column(name = "id_certificacion")
    private Integer idCertificacion;

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @PrePersist
    public void generarUUID() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tecnico", referencedColumnName = "id_tecnico")
    private TecnicoEntity tecnico;

    @Column(nullable = false, length = 100, name = "num_matricula")
    private String numMatricula;

    @Column(nullable = false, length = 100, name = "ente_otorgador")
    private String enteOtorgador;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(nullable = false, length = 50, name = "tipo_imagen")
    private String tipoImagen;

    @Lob
    @Column(nullable = false, name = "imagen_certificado")
    private byte[] imagenCertificado;
}
