package com.aquienllamo.aquienllamo.entidades.pago.entity;

import com.aquienllamo.aquienllamo.entidades.pago.enums.EProveedorPago;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pago_proveedor")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagoProveedorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_pago", nullable = false, unique = true)
    private PagoEntity pago;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EProveedorPago proveedor;

    private String externalPaymentId;

    @Column(length = 1000)
    private String checkoutUrl;

    private String estadoExterno;

    private LocalDateTime fechaActualizacion;

}