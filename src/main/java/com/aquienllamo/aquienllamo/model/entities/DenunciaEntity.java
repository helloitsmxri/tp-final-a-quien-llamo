package com.aquienllamo.aquienllamo.model.entities;

import com.aquienllamo.aquienllamo.model.Enum.EstadoDenunciaE;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Denuncia")
public class DenunciaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_denuncia")
    private Integer idDenuncia;

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @PrePersist
    public void generarUUID() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrador_encargado")
    private AdministradorEntity administrador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_denunciante", nullable = false)
    private UsuarioEntity denunciante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_denunciado", nullable = false)
    private UsuarioEntity denunciado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_denuncia", nullable = false)
    private EstadoDenunciaE estadoDenuncia;

    @Column(name = "motivo_denuncia", nullable = false, columnDefinition = "TEXT")
    private String motivoDenuncia;

    @Column(name = "nota_del_admin", columnDefinition = "TEXT")
    private String notaDelAdmin;

    @Column(name = "fecha_denuncia", nullable = false, insertable = false, updatable = false)
    private LocalDateTime fechaDenuncia;

    @Column(name = "tipo_foto", length = 50)
    private String tipoFoto;

    @Lob
    @Column(name = "foto", columnDefinition = "MEDIUMBLOB")
    private byte[] foto;
}
