package com.aquienllamo.aquienllamo.entidades.caracteristica.controller;

import com.aquienllamo.aquienllamo.entidades.caracteristica.service.CaracteristicaService;
import com.aquienllamo.aquienllamo.entidades.caracteristica.enums.TipoCaracteristicaE;
import com.aquienllamo.aquienllamo.entidades.caracteristica.dtos.request.CaracteristicaDTORequest;
import com.aquienllamo.aquienllamo.entidades.caracteristica.dtos.response.CaracteristicaDTOResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/caracteristicas")
public class CaracteristicaController {

    private final CaracteristicaService caracteristicaService;

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
    @GetMapping("")
    @PreAuthorize("hasRole('USUARIO')")
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
    @PreAuthorize("hasRole('USUARIO')")
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

