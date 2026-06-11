package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.dtos.Request.RatingDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.RatingDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.CaracteristicaEntity;
import com.aquienllamo.aquienllamo.model.entities.RatingEntity;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.model.exceptions.UserNotFoundEx;
import com.aquienllamo.aquienllamo.model.mappers.RatingMapper;
import com.aquienllamo.aquienllamo.model.repositories.CaracteristicaRepository;
import com.aquienllamo.aquienllamo.model.repositories.RatingRepository;
import com.aquienllamo.aquienllamo.model.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final UsuarioRepository usuarioRepository;
    private final CaracteristicaRepository caracteristicaRepository;

    //crear una rating
    public RatingDTOResponse crearRating (RatingDTORequest request){
        UsuarioEntity remitente = usuarioRepository.findByUuid(request.getUuidRemitente())
                .orElseThrow(()-> new UserNotFoundEx("ERROR: El remitente no existe."));

        UsuarioEntity destinatario = usuarioRepository.findByUuid(request.getUuidDestinatario())
                .orElseThrow(()-> new UserNotFoundEx("ERROR: El destinatario no existe."));

        //que no se reseñe a si mismo
        if(remitente.getUuid().equals(destinatario.getUuid())){
            throw new IllegalArgumentException("ERROR: No podes reseñarte a vos mismo.");
        }

        if(ratingRepository.existsByUsuarioRemitenteAndUsuarioDestinatario(remitente, destinatario)){
            throw new IllegalArgumentException("ERROR: Ya has realizado una reseña a este usuario.");
        }

        //Recorre cada UUID de la lista y busca la característica correspondiente con el findByUuid
        List<CaracteristicaEntity> caracteristicas = request.getUuidCaracteristicas()
                .stream()
                .map(uuid -> caracteristicaRepository.findByUuid(uuid)
                        .orElseThrow(()-> new UserNotFoundEx("ERROR: La caracteristica no existe.")))
                .toList();

        return RatingMapper.toResponse(ratingRepository.save(
                RatingMapper.toEntity(request,caracteristicas,remitente,destinatario)));

    }

    //listar reseñas que realizo un usuario
    public List<RatingDTOResponse> listarRatingRemitente (String uuid){
        UsuarioEntity remitente = usuarioRepository.findByUuid(uuid)
                .orElseThrow(()-> new UserNotFoundEx("ERROR: El usuario no existe."));
        return ratingRepository.findAllByUsuarioRemitente(remitente)
                .stream()
                .map(RatingMapper::toResponse)
                .toList();
    }

    //estos metodos reciben UUID del usuario y devuelven la lista de reseñas correspondiente.
    //Desde el perfil de cualquier usuario se puede mostrar las dos listas.

    //listar reseñas que recibio un usuario
    public List<RatingDTOResponse> listarRatingDestinatario(String uuid){
        UsuarioEntity destinatario = usuarioRepository.findByUuid(uuid)
                .orElseThrow(()-> new UserNotFoundEx("ERROR: El usuario no existe."));
        return ratingRepository.findAllByUsuarioDestinatario(destinatario)
                .stream()
                .map(RatingMapper::toResponse)
                .toList();
    }
}
