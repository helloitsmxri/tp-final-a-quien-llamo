package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.dtos.Response.RubroDTOResponse;
import com.aquienllamo.aquienllamo.model.services.RubroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //esta clase maneja endpoints REST, puede devolver JSON automaticamente
@RequiredArgsConstructor //crea el constructor automaticamente para los final
@RequestMapping("/aquienllamo/rubros") //define la ruta base, entonces todos los endpoints empiezan con /rubros
public class RubroController {

    private final RubroService rubroService; //el controller usa el servicio para hacer la logica

    //obtener todos los rubros
    @GetMapping
    public ResponseEntity<List<RubroDTOResponse>> getAllRubros(){
        return ResponseEntity.ok().body(rubroService.getAllRubros());
    }

    //busco rubro por uuid
    @GetMapping("/{uuid}")
    public ResponseEntity<RubroDTOResponse> getRubroByUuid(@Valid @PathVariable String uuid){
        return ResponseEntity.ok().body(rubroService.getRubroByUuid(uuid));
    }

    //buscar nombre exacto de rubro
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<RubroDTOResponse> getRubroByNombre(@PathVariable String nombre){
        return ResponseEntity.ok().body(rubroService.getRubroByNombre(nombre));
    }

    //buscar un rubro, puede ser por el nombre entero o por parte del nombre ej tec = tecnico
    @GetMapping("/buscar")
    public ResponseEntity<List<RubroDTOResponse>> findRubros(@RequestParam String nombre){
        return ResponseEntity.ok().body(rubroService.findRubroContaining(nombre));
    }




}
