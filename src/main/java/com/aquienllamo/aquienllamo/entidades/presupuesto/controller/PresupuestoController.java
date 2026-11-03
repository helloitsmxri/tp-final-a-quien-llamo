
package com.aquienllamo.aquienllamo.entidades.presupuesto.controller;

import com.aquienllamo.aquienllamo.entidades.presupuesto.dto.request.PresupuestoDTORequest;
import com.aquienllamo.aquienllamo.entidades.presupuesto.dto.response.PresupuestoDTOResponse;
import com.aquienllamo.aquienllamo.entidades.presupuesto.enums.EstadoPresupuestoE;
import com.aquienllamo.aquienllamo.entidades.presupuesto.service.PresupuestoService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aquienllamo/presupuestos")
public class PresupuestoController {

    private final PresupuestoService presupuestoService;


    // CREAR PRESUPUESTO DENTRO DE UN CHAT

    @PostMapping("/chat/{uuidChat}")
    @PreAuthorize("hasRole('TECNICO')")
    @ResponseStatus(HttpStatus.CREATED)
    public PresupuestoDTOResponse crearPresupuesto(@PathVariable String uuidChat, @Valid @RequestBody PresupuestoDTORequest dto, Authentication authentication) {
        return presupuestoService.createPresupuesto(dto, uuidChat, authentication.getName());
    }


    // VER PRESUPUESTO EN DETALLE

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAnyRole('USUARIO', 'TECNICO')")
    @ResponseStatus(HttpStatus.OK)
    public PresupuestoDTOResponse verPresupuesto(@PathVariable String uuid, Authentication authentication) {
        return presupuestoService.obtenerPorUuid(uuid, authentication.getName());
    }


    // ACEPTAR PRESUPUESTO

    @PatchMapping("/{uuid}/aceptar")
    @PreAuthorize("hasRole('USUARIO')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void aceptarPresupuesto(@PathVariable String uuid, Authentication authentication) {
        presupuestoService.aceptarPresupuesto(uuid, authentication.getName());
    }


    // RECHAZAR PRESUPUESTO

    @PatchMapping("/{uuid}/rechazar")
    @PreAuthorize("hasRole('USUARIO')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rechazarPresupuesto(@PathVariable String uuid, Authentication authentication) {
        presupuestoService.rechazarPresupuesto(uuid, authentication.getName());
    }


    // CANCELAR PRESUPUESTO COMO TÉCNICO

    @PatchMapping("/{uuid}/cancelar")
    @PreAuthorize("hasRole('TECNICO')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelarPresupuesto(@PathVariable String uuid, Authentication authentication) {

        presupuestoService.cancelarPresupuesto(uuid, authentication.getName());
    }


    // LISTAR TODOS LOS PRESUPUESTOS (ADMINISTRADOR)

    @GetMapping("/todos")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ResponseStatus(HttpStatus.OK)
    public List<PresupuestoDTOResponse> listarTodos() {
        return presupuestoService.getAllPresupuestos();
    }


    // BUSCAR PRESUPUESTOS CON FILTROS (ADMINISTRADOR)

    @GetMapping("/buscar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ResponseStatus(HttpStatus.OK)
    public List<PresupuestoDTOResponse> buscar(@RequestParam(required = false) String nombre, @RequestParam(required = false) String apellido,
                                               @RequestParam(required = false) BigDecimal min, @RequestParam(required = false) BigDecimal max,
                                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return presupuestoService.buscarPresupuestosCompleto(nombre, apellido, min, max, fecha);
    }


    // PRESUPUESTOS RECIBIDOS POR EL CLIENTE

    @GetMapping("/mis-presupuestos")
    @PreAuthorize("hasRole('USUARIO')")
    @ResponseStatus(HttpStatus.OK)
    public List<PresupuestoDTOResponse> misPresupuestos(Authentication authentication) {
        return presupuestoService.getPresupuestosUsuario(authentication.getName());
    }


    // PRESUPUESTOS DEL CLIENTE SEGÚN SU ESTADO

    @GetMapping("/mis-presupuestos/filtrar")
    @PreAuthorize("hasRole('USUARIO')")
    @ResponseStatus(HttpStatus.OK)
    public List<PresupuestoDTOResponse> misPresupuestosPorEstado(Authentication authentication, @RequestParam EstadoPresupuestoE estado) {
        return presupuestoService.getPresupuestosUsuarioPorEstado(authentication.getName(), estado);
    }


    // PRESUPUESTOS CREADOS POR EL TÉCNICO

    @GetMapping("/mis-presupuestos-tecnico")
    @PreAuthorize("hasRole('TECNICO')")
    @ResponseStatus(HttpStatus.OK)
    public List<PresupuestoDTOResponse> misPresupuestosTecnico(Authentication authentication) {
        return presupuestoService.getPresupuestosTecnico(authentication.getName());
    }


    // PRESUPUESTOS DEL TÉCNICO SEGÚN SU ESTADO

    @GetMapping("/mis-presupuestos-tecnico/filtrar")
    @PreAuthorize("hasRole('TECNICO')")
    @ResponseStatus(HttpStatus.OK)
    public List<PresupuestoDTOResponse> misPresupuestosTecnicoPorEstado(Authentication authentication, @RequestParam EstadoPresupuestoE estado) {
        return presupuestoService.getPresupuestosTecnicoPorEstado(authentication.getName(), estado);
    }


    // BUSCAR PRESUPUESTOS DEL CLIENTE CON FILTROS

    @GetMapping("/mis-presupuestos/buscar")
    @PreAuthorize("hasRole('USUARIO')")
    @ResponseStatus(HttpStatus.OK)
    public List<PresupuestoDTOResponse> buscarMisPresupuestos(Authentication authentication, @RequestParam(required = false) EstadoPresupuestoE estado, @RequestParam(required = false) BigDecimal min, @RequestParam(required = false) BigDecimal max, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return presupuestoService.buscarMisPresupuestos(authentication.getName(), estado, min, max, fecha);
    }


    // BUSCAR PRESUPUESTOS DEL TÉCNICO CON FILTROS

    @GetMapping("/mis-presupuestos-tecnico/buscar")
    @PreAuthorize("hasRole('TECNICO')")
    @ResponseStatus(HttpStatus.OK)
    public List<PresupuestoDTOResponse> buscarMisPresupuestosTecnico(Authentication authentication, @RequestParam(required = false) EstadoPresupuestoE estado, @RequestParam(required = false) BigDecimal min, @RequestParam(required = false) BigDecimal max, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return presupuestoService.buscarMisPresupuestosTecnico(authentication.getName(), estado, min, max, fecha);
    }


    // OBTENER PRESUPUESTOS DE UN CHAT

    @GetMapping("/chat/{uuidChat}")
    @PreAuthorize("hasAnyRole('USUARIO', 'TECNICO')")
    @ResponseStatus(HttpStatus.OK)
    public List<PresupuestoDTOResponse> obtenerPresupuestosChat(@PathVariable String uuidChat, Authentication authentication) {
        return presupuestoService.obtenerPresupuestosChat(uuidChat, authentication.getName());
    }


    // MODIFICAR PRESUPUESTO

    @PutMapping("/{uuid}")
    @PreAuthorize("hasRole('TECNICO')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modificarPresupuesto(@PathVariable String uuid, @Valid @RequestBody PresupuestoDTORequest dto, Authentication authentication) {
        presupuestoService.modificarPresupuesto(uuid, dto, authentication.getName());
    }

}
