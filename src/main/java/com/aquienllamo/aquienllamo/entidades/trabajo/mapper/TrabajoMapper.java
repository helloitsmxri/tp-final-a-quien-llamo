package com.aquienllamo.aquienllamo.entidades.trabajo.mapper;

import com.aquienllamo.aquienllamo.entidades.presupuesto.entity.PresupuestoEntity;
import com.aquienllamo.aquienllamo.entidades.trabajo.dto.request.TrabajoDTORequest;
import com.aquienllamo.aquienllamo.entidades.trabajo.dto.response.TrabajoDTOResponse;
import com.aquienllamo.aquienllamo.entidades.trabajo.entity.TrabajoEntity;
import com.aquienllamo.aquienllamo.entidades.usuario.entities.UsuarioEntity;
import org.springframework.stereotype.Component;

public class TrabajoMapper {

    public static TrabajoDTOResponse toResponse(TrabajoEntity entity) {

        PresupuestoEntity presupuesto = entity.getPresupuesto();

        UsuarioEntity cliente = presupuesto.getUsuario();

        UsuarioEntity tecnico = presupuesto.getTecnico() != null
                ? presupuesto.getTecnico().getUsuario()
                : null;

        return TrabajoDTOResponse.builder()
                .uuid(entity.getUuid())

                // Datos del presupuesto
                .uuidPresupuesto(presupuesto.getUuid())
                .nombreProyecto(presupuesto.getNombreProyecto())
                .precioEstimado(presupuesto.getPrecioEstimado())
                .uuidChat(presupuesto.getChat() != null
                        ? presupuesto.getChat().getUuidChat()
                        : null)

                // Datos del cliente
                .uuidCliente(cliente != null ? cliente.getUuid() : null)
                .nombreCliente(cliente != null ? cliente.getNombre() : null)
                .apellidoCliente(cliente != null ? cliente.getApellido() : null)

                // Datos del técnico
                .uuidUsuarioTecnico(tecnico != null ? tecnico.getUuid() : null)
                .nombreTecnico(tecnico != null ? tecnico.getNombre() : null)
                .apellidoTecnico(tecnico != null ? tecnico.getApellido() : null)

                // Datos del trabajo
                .descripcionTrabajo(entity.getDescripcionTrabajo())
                .fechaEstimadaInicio(entity.getFechaEstimadaInicio())
                .fechaEstimadaFin(entity.getFechaEstimadaFin())
                .estadoTrabajo(entity.getEstadoTrabajo())

                .build();
    }

    public static TrabajoEntity toEntity(
            TrabajoDTORequest request,
            PresupuestoEntity presupuesto) {

        return TrabajoEntity.builder()
                .presupuesto(presupuesto)
                .descripcionTrabajo(request.getDescripcionTrabajo())
                .fechaEstimadaInicio(request.getFechaEstimadaInicio())
                .fechaEstimadaFin(request.getFechaEstimadaFin())
                .build();
    }
}