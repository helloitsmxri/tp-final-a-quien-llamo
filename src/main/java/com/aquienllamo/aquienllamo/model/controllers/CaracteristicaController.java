package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.dtos.Request.CaracteristicaDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.CaracteristicaDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.CaracteristicaEntity;
import com.aquienllamo.aquienllamo.model.services.CaracteristicaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/caracteristica")
public class CaracteristicaController {
    private final CaracteristicaService  caracteristicaService;

    // crear característica:
    @PostMapping("/create")
    public ResponseEntity<CaracteristicaDTOResponse> createCaracteristica(@Valid @RequestBody CaracteristicaDTORequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(caracteristicaService.crearNuevaCaracteristica(dto));
    }

    // eliminar característica:
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCaracteristica(String uuid){
        caracteristicaService.eliminarCaracteristica(uuid);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Caracteristica no encontrada");
    }

    // listar todas las caracteristicas:
    @GetMapping("/caracteristicas")
    public ResponseEntity<List<CaracteristicaDTOResponse>> findAllCaracteristicas(){
        return ResponseEntity.ok(caracteristicaService.findAll());
    }

    // actualizar caracteristica
    @PatchMapping("/update")
    public ResponseEntity<CaracteristicaDTOResponse> updateCaracteristica(String uuid, CaracteristicaDTORequest dto){
        return ResponseEntity.ok(caracteristicaService.modificar(uuid, dto));
    }
}

