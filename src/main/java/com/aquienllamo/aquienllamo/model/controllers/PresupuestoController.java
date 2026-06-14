package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.Enum.EstadoPresupuestoE;
import com.aquienllamo.aquienllamo.model.dtos.Request.PresupuestoDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.PresupuestoDTOResponse;
import com.aquienllamo.aquienllamo.model.services.PresupuestoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/presupuestos")
public class PresupuestoController {

    private final PresupuestoService presupuestoService;

    // Crear presupuesto dentro de un chat
    @PostMapping("/chat/{uuidChat}")
    @ResponseStatus(HttpStatus.CREATED)
    public PresupuestoDTOResponse crearPresupuesto(
            @PathVariable String uuidChat,
            @Valid @RequestBody PresupuestoDTORequest dto,
            Authentication authentication) {

        String emailAutenticado = authentication.getName();

        return presupuestoService.createPresupuesto(
                dto,
                uuidChat,
                emailAutenticado
        );
    }

    // Ver presupuesto en detalle
    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public PresupuestoDTOResponse verPresupuesto(
            @PathVariable String uuid) {

        return presupuestoService.obtenerPorUuid(uuid);
    }

    // Aceptar presupuesto
    @PatchMapping("/{uuid}/aceptar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void aceptarPresupuesto(
            @PathVariable String uuid,
            Authentication authentication) {

        String emailUsuario = authentication.getName();

        presupuestoService.aceptarPresupuesto(uuid, emailUsuario);
    }

    // Rechazar presupuesto
    @PatchMapping("/{uuid}/rechazar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rechazarPresupuesto(
            @PathVariable String uuid,
            Authentication authentication) {

        String emailUsuario = authentication.getName();

        presupuestoService.rechazarPresupuesto(uuid, emailUsuario);
    }

    // Cancelar presupuesto como técnico
    @PatchMapping("/{uuid}/cancelar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelarPresupuesto(
            @PathVariable String uuid,
            Authentication authentication) {

        String emailTech = authentication.getName();

        presupuestoService.cancelarPresupuesto(uuid, emailTech);
    }

    // Listar todos
    @GetMapping("/todos")
    @ResponseStatus(HttpStatus.OK)
    public List<PresupuestoDTOResponse> listarTodos() {
        return presupuestoService.getAllPresupuestos();
    }

    // Búsqueda por filtros
    @GetMapping("/buscar")
    @ResponseStatus(HttpStatus.OK)
    public List<PresupuestoDTOResponse> buscar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellido,
            @RequestParam(required = false) BigDecimal min,
            @RequestParam(required = false) BigDecimal max,
            @RequestParam(required = false) LocalDate fecha) {

        return presupuestoService.buscarPresupuestosCompleto(nombre, apellido, min, max, fecha);
    }

    @GetMapping("/mis-presupuestos")
    @ResponseStatus(HttpStatus.OK)
    public List<PresupuestoDTOResponse> misPresupuestos(
            Authentication auth){

        return presupuestoService.getPresupuestosUsuario(auth.getName());
    }

    @GetMapping("/mis-presupuestos/filtrar")
    @ResponseStatus(HttpStatus.OK)
    public List<PresupuestoDTOResponse> misPresupuestosPorEstado(
            Authentication auth,
            @RequestParam EstadoPresupuestoE estado){

        return presupuestoService.getPresupuestosUsuarioPorEstado(auth.getName(),estado);
    }

    @GetMapping("/mis-presupuestos-tecnico")
    @ResponseStatus(HttpStatus.OK)
    public List<PresupuestoDTOResponse> misPresupuestosTecnico(
            Authentication auth){

        return presupuestoService.getPresupuestosTecnico(
                auth.getName()
        );
    }

    @GetMapping("/mis-presupuestos-tecnico/filtrar")
    @ResponseStatus(HttpStatus.OK)
    public List<PresupuestoDTOResponse> misPresupuestosTecnicoPorEstado(
            Authentication auth,
            @RequestParam EstadoPresupuestoE estado){

        return presupuestoService.getPresupuestosTecnicoPorEstado(
                auth.getName(),
                estado
        );
    }

}
