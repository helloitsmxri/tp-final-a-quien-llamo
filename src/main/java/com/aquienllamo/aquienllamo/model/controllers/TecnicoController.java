package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.dtos.Request.TecnicoDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Request.TecnicoNuevoDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.TecnicoDTOResponse;
import com.aquienllamo.aquienllamo.model.services.TecnicoService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/aquienllamo/tecnicos")
@RequiredArgsConstructor
public class TecnicoController {
    private final TecnicoService tecnicoService;

    //registrar usuario como tecnico
    @PreAuthorize("hasRole('USUARIO')")
    @PostMapping("/registrar/{uuidUsuario}")
    public ResponseEntity<TecnicoDTOResponse> registrarTecnico(@PathVariable String uuidUsuario, @Valid @RequestBody TecnicoDTORequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(tecnicoService.registrarTecnico(dto, uuidUsuario));
    }

    //registrar un tecnico nuevo
    @PermitAll
    @PostMapping(value = "/registrar-nuevo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TecnicoDTOResponse> registrarTecnicoNuevo(@Valid @ModelAttribute TecnicoNuevoDTORequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(tecnicoService.registrarTecnicoNuevo(dto.getUsuario(), dto.getTecnico()));
    }

    //listar todos los tecnicos
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','USUARIO','TECNICO')")
    @GetMapping
    public ResponseEntity<List<TecnicoDTOResponse>> getAllTecnicos(){
        return ResponseEntity.ok(tecnicoService.getAllTecnicos());
    }

    //buscar tecnico por uuid
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','USUARIO','TECNICO')")
    @GetMapping("/perfil/{uuid}")
    public ResponseEntity<TecnicoDTOResponse> verPerfil(@PathVariable String uuid){
        return ResponseEntity.ok(tecnicoService.getTecnicoByUuid(uuid));
    }

    //filtrar por habilidad
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','USUARIO','TECNICO')")
    @GetMapping("/habilidad/{uuidHabilidad}")
    public ResponseEntity<List<TecnicoDTOResponse>> obtenerTecnicosPorHabilidad(@PathVariable String uuidHabilidad){
        return ResponseEntity.ok(tecnicoService.obtenerTecnicosPorHabilidad(uuidHabilidad));
    }

    //filtrar por especialidad
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','USUARIO','TECNICO')")
    @GetMapping("/especialidad/{uuidEspecialidad}")
    public ResponseEntity<List<TecnicoDTOResponse>> obtenerTecnicosPorEspecialidad(@PathVariable String uuidEspecialidad){
        return ResponseEntity.ok(tecnicoService.obtenerTecnicosPorEspecialidad(uuidEspecialidad));
    }

    //filtrar por nombre
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','USUARIO','TECNICO')")
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<TecnicoDTOResponse>> obtenerTecnicosPorNombre(@PathVariable String nombre){
        return ResponseEntity.ok(tecnicoService.obtenerTecnicosPorNombre(nombre));
    }

    //filtrar por rubros
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','USUARIO','TECNICO')")
    @GetMapping("/rubro/{uuidRubro}")
    public ResponseEntity<List<TecnicoDTOResponse>> obtenerTecnicosPorRubro(@PathVariable String uuidRubro){
        return ResponseEntity.ok(tecnicoService.getTecnicosByRubro(uuidRubro));
    }

    //buscar por filtros combinados
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','USUARIO','TECNICO')")
    @GetMapping("/buscar")
    public ResponseEntity<List<TecnicoDTOResponse>> buscarTecnicos(@RequestParam(required = false) String nombre, @RequestParam(required = false) String apellido, @RequestParam(required = false) String habilidad, @RequestParam(required = false) String especialidad, @RequestParam(required = false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha){
        return ResponseEntity.ok(tecnicoService.buscarTecnicos(nombre,apellido,habilidad,especialidad,fecha));

    }

    //ordenar por fecha mas reciente
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','USUARIO','TECNICO')")
    @GetMapping("/ordenar/recientes")
    public ResponseEntity<List<TecnicoDTOResponse>> getTecnicosOrderByFechaRegistroDesc() {
        return ResponseEntity.ok(tecnicoService.getTecnicosOrderByFechaRegistroDesc());
    }

    //ordenar por fecha mas antigua
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','USUARIO','TECNICO')")
    @GetMapping("/ordenar/antiguos")
    public ResponseEntity<List<TecnicoDTOResponse>> getTecnicosOrderByFechaRegistroAsc() {
        return ResponseEntity.ok(tecnicoService.getTecnicosOrderByFechaRegistroAsc());
    }

    //actualizar tecnico
    @PreAuthorize("hasRole('TECNICO')")
    @PutMapping("/actualizar-perfil/{uuid}")
    public ResponseEntity<TecnicoDTOResponse> updateTecnico(@PathVariable String uuid, @Valid @RequestBody TecnicoDTORequest dto) {
        return ResponseEntity.ok(tecnicoService.updateTecnico(uuid, dto));
    }

    //eliminar tecnico
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/eliminar/{uuid}")
    public ResponseEntity<Void> deleteTecnico(@PathVariable String uuid) {
        tecnicoService.deleteTecnico(uuid);
        return ResponseEntity.noContent().build();
    }
}
