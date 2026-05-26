package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.dtos.Request.RatingDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.RatingDTOResponse;
import com.aquienllamo.aquienllamo.model.services.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aquienllamo/Ratings")
public class RatingController {

    private final RatingService service;

    //crear reseña
    @PostMapping
    public ResponseEntity<RatingDTOResponse> crearRating (@Valid @RequestBody RatingDTORequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearRating(request));
    }

    //listar reseñas realizadas por un usuario
    @GetMapping("/remitidas/{uuid}")
    public ResponseEntity<List<RatingDTOResponse>> listarRatingRemitente (@PathVariable String uuid){
        return ResponseEntity.ok().body(service.listarRatingRemitente(uuid));
    }

    //listar reseñas que recibio un usuario
    @GetMapping("/recibidas/{uuid}")
    public ResponseEntity<List<RatingDTOResponse>> listarRatingRecibidas(@PathVariable String uuid){
        return ResponseEntity.ok().body(service.listarRatingDestinatario(uuid));
    }

    

}
