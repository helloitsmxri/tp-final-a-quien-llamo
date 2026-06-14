package com.aquienllamo.aquienllamo.model.entities;

import com.aquienllamo.aquienllamo.model.Enum.EstadoVerificacion;
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

    @Column(name = "ente_otorgador", nullable = false, length = 255)
    private String enteOtorgador;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(nullable = false, length = 50, name = "tipo_imagen")
    private String tipoImagen;

    @Lob
    @Column(nullable = false, name = "imagen_certificado", columnDefinition = "MEDIUMBLOB")
    private byte[] imagenCertificado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_admin_revisor", referencedColumnName = "id_admin")
    private AdministradorEntity adminRevisor;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_verificacion")
    private EstadoVerificacion estadoVerificacion;

    @Column(name = "notas_admin", columnDefinition = "TEXT")
    private String notasAdmin;
}
