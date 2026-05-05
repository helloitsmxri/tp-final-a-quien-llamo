package com.aquienllamo.aquienllamo.model.entities;

import com.aquienllamo.aquienllamo.model.Enum.EstadoDenunciaE;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Denuncia")
public class DenunciaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_denuncia")
    private Integer idDenuncia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrador_encargado")
    private AdministradorEntity administrador;

    @Column(name = "nombre_denunciante", length = 50)
    private String nombreDenunciante;

    @Column(name = "apellido_denunciante", length = 50)
    private String apellidoDenunciante;

    @Column(name = "dni_denunciante", length = 8)
    private String dniDenunciante;

    @Column(name = "telefono_denunciante", length = 20)
    private String telefonoDenunciante;

    //denunciado
    @Column(name = "nombre_denunciado", length = 50)
    private String nombreDenunciado;

    @Column(name = "apellido_denunciado", length = 50)
    private String apellidoDenunciado;

    @Column(name = "dni_denunciado", length = 8)
    private String dniDenunciado;

    @Column(name = "telefono_denunciado", length = 20)
    private String telefonoDenunciado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_denuncia", nullable = false)
    private EstadoDenunciaE estadoDenuncia;

    @Column(name = "motivo_denuncia", nullable = false, columnDefinition = "TEXT")
    private String motivoDenuncia;

    @Column(name = "fecha_denuncia", nullable = false, insertable = false, updatable = false)
    private LocalDateTime fechaDenuncia;

    @Column(name = "tipo_foto", length = 50)
    private String tipoFoto;

    @Lob
    @Column(name = "foto", columnDefinition = "MEDIUMBLOB")
    private byte[] foto;
}
