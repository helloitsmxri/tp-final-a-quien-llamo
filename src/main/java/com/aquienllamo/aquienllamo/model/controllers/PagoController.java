package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.Enum.Estado;
import com.aquienllamo.aquienllamo.model.Enum.MetodoDePago;
import com.aquienllamo.aquienllamo.model.dtos.Request.PagoDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.PagoDTOResponse;
import com.aquienllamo.aquienllamo.model.services.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aquienllamo/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    //crear pago
    @PostMapping
    public ResponseEntity<PagoDTOResponse> crearPago (@Valid @RequestBody PagoDTORequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.crearPago(request));
    }

    //listar pagos por metodo de pago
    @GetMapping("/metodoPago")
    public ResponseEntity<List<PagoDTOResponse>> listarPorMetodoDePago (@RequestParam MetodoDePago metodoDePago){
        return ResponseEntity.ok().body(pagoService.listarPorMetodoDePago(metodoDePago));
    }

    //listar pagos por estado
    @GetMapping("/estado")
    public ResponseEntity<List<PagoDTOResponse>> listarPorEstado (@RequestParam Estado estado) {
        return ResponseEntity.ok().body(pagoService.listarPorEstado(estado));
    }

    //listar todos los pagos
    @GetMapping
    public ResponseEntity<List<PagoDTOResponse>> listarPagos (@RequestParam String uuid){
        return ResponseEntity.ok().body(pagoService.listarPagos());
    }

    //listar pagos hechos por cliente
    @GetMapping("/{uuid}")
    public ResponseEntity<List<PagoDTOResponse>> listarPagoCliente (@PathVariable String uuid){
        return ResponseEntity.ok().body(pagoService.listarPagosPorClienteUuid(uuid));
    }

    //listar pagos recibidos por tecnico
    @GetMapping("/{uuid}")
    public ResponseEntity<List<PagoDTOResponse>> listarPagoTecnico (@PathVariable String uuid){
        return ResponseEntity.ok().body(pagoService.listarPagosRecibidosPorTecnico(uuid));
    }
}
