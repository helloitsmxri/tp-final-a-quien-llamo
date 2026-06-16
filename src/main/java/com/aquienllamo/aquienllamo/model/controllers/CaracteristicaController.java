package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.Enum.TipoCaracteristicaE;
import com.aquienllamo.aquienllamo.model.dtos.Request.CaracteristicaDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.CaracteristicaDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.CaracteristicaEntity;
import com.aquienllamo.aquienllamo.model.services.CaracteristicaService;
import com.aquienllamo.aquienllamo.model.specifications.CaracteristicaSpecification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/caracteristica")
public class CaracteristicaController {

    private final CaracteristicaService  caracteristicaService;

    // crear característica:
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ResponseStatus(HttpStatus.CREATED)
    public CaracteristicaDTOResponse createCaracteristica(@Valid @RequestBody CaracteristicaDTORequest dto){
        return caracteristicaService.crearNuevaCaracteristica(dto);
    }

    // eliminar característica:
    @DeleteMapping("/delete/{uuid}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCaracteristica(@PathVariable String uuid){
        caracteristicaService.eliminarCaracteristica(uuid);
    }

    // listar todas las caracteristicas:
    @GetMapping("/caracteristicas")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ResponseStatus(HttpStatus.OK)
    public List<CaracteristicaDTOResponse> findAllCaracteristicas(){
        return caracteristicaService.findAll();
    }

    // actualizar caracteristica
    @PatchMapping("/update/{uuid}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ResponseStatus(HttpStatus.OK)
    public CaracteristicaDTOResponse updateCaracteristica(@PathVariable String uuid, @RequestBody CaracteristicaDTORequest dto){
        return caracteristicaService.modificar(uuid, dto);
    }

    // encontrar por uuid
    @GetMapping("/{uuid}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ResponseStatus(HttpStatus.OK)
    public CaracteristicaDTOResponse findByUuid(@PathVariable String uuid){
        return caracteristicaService.findByUuid(uuid);
    }

    // buscar por filtrado
    @GetMapping("/buscar")
    @ResponseStatus(HttpStatus.OK)
    public List<CaracteristicaDTOResponse> buscar(@RequestParam(required = false) String palabra, @RequestParam(required = false) TipoCaracteristicaE tipo){
        return caracteristicaService.buscar(palabra, tipo);
    }
}

