package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.dtos.Request.RubroDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.RubroDTOResponse;
import com.aquienllamo.aquienllamo.model.services.RubroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //esta clase maneja endpoints REST, puede devolver JSON automaticamente
@RequiredArgsConstructor //crea el constructor automaticamente para los final
@RequestMapping("/aquienllamo/rubros") //define la ruta base, entonces todos los endpoints empiezan con /rubros
public class RubroController {

    private final RubroService rubroService; //el controller usa el servicio para hacer la logica

    //crear un rubro (solo lo puede hacer admin)
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<RubroDTOResponse> crearRubro(@Valid @RequestBody RubroDTORequest rubroDTORequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(rubroService.crearRubro(rubroDTORequest));
    }

    //modificar un rubro (solo lo puede hacer el admin)
    @PutMapping("/{uuid}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<RubroDTOResponse> modificarRubro (@PathVariable String uuid, @Valid @RequestBody RubroDTORequest rubroDTORequest){
        return ResponseEntity.ok(rubroService.modificarRubro(uuid, rubroDTORequest));
    }

    //eliminar un rubro (solo el admin)
    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> eliminarRubro(@PathVariable String uuid){
        rubroService.eliminarRubro(uuid);
        return ResponseEntity.noContent().build(); //204 No Content, operación exitosa, pero no tengo nada para devolverte
    }

    //obtener todos los rubros
    @GetMapping
    @PreAuthorize("isAuthenticated()") //cualquier usuario logueado puede ver los rubros disponibles
    public ResponseEntity<List<RubroDTOResponse>> getAllRubros(){
        return ResponseEntity.ok().body(rubroService.getAllRubros());
    }

    //busco rubro por uuid
    @GetMapping("/{uuid}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RubroDTOResponse> getRubroByUuid(@Valid @PathVariable String uuid){
        return ResponseEntity.ok().body(rubroService.getRubroByUuid(uuid));
    }

    //buscar nombre exacto de rubro
    @GetMapping("/nombre/{nombre}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RubroDTOResponse> getRubroByNombre(@PathVariable String nombre){
        return ResponseEntity.ok().body(rubroService.getRubroByNombre(nombre));
    }

    //buscar un rubro, puede ser por el nombre entero o por parte del nombre ej tec = tecnico
    @GetMapping("/buscar")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<RubroDTOResponse>> findRubros(@RequestParam String nombre){
        return ResponseEntity.ok().body(rubroService.findRubroContaining(nombre));
    }
}
