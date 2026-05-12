package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.dtos.Request.RubroDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.RubroDTOResponse;
import com.aquienllamo.aquienllamo.model.services.RubroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //esta clase maneja endpoints REST, puede devolver JSON automaticamente
@RequiredArgsConstructor //crea el constructor automaticamente para los final
@RequestMapping("/aquienllamo/rubros") //define la ruta base, entonces todos los endpoints empiezan con /rubros
public class RubroController {

    private final RubroService rubroService; //el controller usa el servicio para hacer la logica

    //crea un endpoint de rubro
    @PostMapping                              //convierte el JSON que manda el frontend en un DTO java
    public RubroDTOResponse crearRubro(@Valid @RequestBody RubroDTORequest dtoRequest){
        return rubroService.crearRurbo(dtoRequest);
    }

    //obtener todos los rubros
    @GetMapping
    public List<RubroDTOResponse> getAllRubros(){
        return rubroService.getAllRubros();
    }

    //busco rubro por uuid
    @GetMapping("/{uuid}")
    public RubroDTOResponse getRubroByUuid(@Valid @PathVariable String uuid){
        return rubroService.getRubroByUuid(uuid);
    }

    //buscar nombre exacto de rubro
    @GetMapping("/nombre/{nombre}")
    public RubroDTOResponse getRubroByNombre(@PathVariable String nombre){
        return rubroService.getRubroByNombre(nombre);
    }

    //buscar un rubro, puede ser por el nombre entero o por parte del nombre ej tec = tecnico
    @GetMapping("/buscar")
    public List<RubroDTOResponse> findRubros(@RequestParam String nombre){
        return rubroService.findRubroContaining(nombre);
    }

    //actualizar rubro
    @PutMapping("/{uuid}")
    public RubroDTOResponse updateRubro(@PathVariable String uuid, @RequestBody RubroDTORequest dto){
        return rubroService.updateRubroUuid(uuid,dto);
    }

    //eliminar rubro por uuid
    @DeleteMapping("/{uuid}")
    public void deleteRubroByUuid(@PathVariable String uuid){
        rubroService.deleteRubroByUuid(uuid);
    }


}
