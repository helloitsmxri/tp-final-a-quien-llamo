package com.aquienllamo.aquienllamo.entidades.pago.entity;

import com.aquienllamo.aquienllamo.common.BaseEntity;
import com.aquienllamo.aquienllamo.entidades.pago.enums.EProveedorPago;
import com.aquienllamo.aquienllamo.entidades.pago.enums.EstadoPago;
import com.aquienllamo.aquienllamo.entidades.pago.enums.MetodoDePago;
import com.aquienllamo.aquienllamo.entidades.trabajo.entity.TrabajoEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"trabajo", "datosProveedor"})
@Builder
@Table(name = "Pago")
public class PagoEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPago;

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @PrePersist
    public void generarUUID() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
    }

    @OneToOne
    @JoinColumn(name = "id_trabajo")
    private TrabajoEntity trabajo;

    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    private MetodoDePago metodoDePago;

    @Enumerated(EnumType.STRING)
    @Column(name = "proveedor_pago", nullable = false)
    private EProveedorPago proveedorDePago;

    @Builder.Default
    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago = LocalDateTime.now();

    @OneToOne(
            mappedBy = "pago",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private PagoProveedorEntity datosProveedor;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoPago estadoPago;
}