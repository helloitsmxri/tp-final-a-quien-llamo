package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.Enum.EstadoTrabajo;
import com.aquienllamo.aquienllamo.model.auth.credentials.CredentialsEntity;
import com.aquienllamo.aquienllamo.model.auth.repositories.CredentialsRepository;
import com.aquienllamo.aquienllamo.model.auth.securityServices.AuthService;
import com.aquienllamo.aquienllamo.model.auth.utils.SecurityUtils;
import com.aquienllamo.aquienllamo.model.dtos.Request.RatingDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.RatingDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.*;
import com.aquienllamo.aquienllamo.model.exceptions.*;
import com.aquienllamo.aquienllamo.model.mappers.RatingMapper;
import com.aquienllamo.aquienllamo.model.repositories.CaracteristicaRepository;
import com.aquienllamo.aquienllamo.model.repositories.RatingRepository;
import com.aquienllamo.aquienllamo.model.repositories.TrabajoRepository;
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
    private final TrabajoRepository trabajoRepository;
    private final CredentialsRepository credentialsRepository;

    public RatingDTOResponse crearRating(RatingDTORequest request) {

        // 1. Obtener el remitente autenticado
        UsuarioEntity remitente = SecurityUtils.getCurrentUser();

        // 2. Buscar el trabajo
        TrabajoEntity trabajo = trabajoRepository
                .findByUuid(request.getUuidTrabajo())
                .orElseThrow(() ->
                        new RuntimeException("El trabajo no existe."));

        // 3. Verificar que el trabajo esté finalizado
        if (trabajo.getEstadoTrabajo() != EstadoTrabajo.Finalizado) {
            throw new CompletedWorkRequiredException(
                    "Solo se puede reseñar un trabajo finalizado.");
        }

        // 4. Verificar que el trabajo tenga un presupuesto
        PresupuestoEntity presupuesto = trabajo.getPresupuesto();

        if (presupuesto == null) {
            throw new RuntimeException(
                    "El trabajo no tiene un presupuesto asociado.");
        }

        // 5. Obtener al cliente y al técnico del trabajo
        UsuarioEntity cliente = presupuesto.getUsuario();

        if (presupuesto.getTecnico() == null) {
            throw new RuntimeException(
                    "El trabajo no tiene un técnico asociado.");
        }

        UsuarioEntity tecnico = presupuesto.getTecnico().getUsuario();

        if (cliente == null || tecnico == null) {
            throw new RuntimeException(
                    "No se encontraron los participantes del trabajo.");
        }

        // 6. Determinar el destinatario según quién realiza la reseña
        UsuarioEntity destinatario;

        if (remitente.getUuid().equals(cliente.getUuid())) {

            // El cliente reseña al técnico
            destinatario = tecnico;

        } else if (remitente.getUuid().equals(tecnico.getUuid())) {

            // El técnico reseña al cliente
            destinatario = cliente;

        } else {

            // El usuario autenticado no participa del trabajo
            throw new RuntimeException(
                    "Solo los participantes del trabajo pueden realizar reseñas.");
        }

        // 7. Verificar que no exista una reseña previa
        // del mismo remitente para este trabajo
        if (ratingRepository.existsByUsuarioRemitenteAndTrabajo(
                remitente, trabajo)) {

            throw new RatingAlreadyExistsEx(
                    "Ya has realizado una reseña para este trabajo.");
        }

        // 8. Buscar las características por UUID
        List<CaracteristicaEntity> caracteristicas =
                request.getUuidCaracteristicas()
                        .stream()
                        .map(uuid -> caracteristicaRepository.findByUuid(uuid)
                                .orElseThrow(() ->
                                        new RuntimeException(
                                                "La característica no existe: "
                                                        + uuid)))
                        .toList();

        // 9. Crear la entidad Rating
        RatingEntity rating = RatingMapper.toEntity(
                request,
                caracteristicas,
                remitente,
                destinatario
        );

        // 10. Asociar la reseña al trabajo
        rating.setTrabajo(trabajo);

        // 11. Guardar y devolver la respuesta
        return RatingMapper.toResponse(
                ratingRepository.save(rating)
        );
    }


    //listar reseñas que realizo un usuario
    public List<RatingDTOResponse> listarRatingRemitente (String uuid){
        UsuarioEntity remitente = usuarioRepository.findByUuid(uuid)
                .orElseThrow(()-> new UserNotFoundEx("El usuario no existe."));
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
                .orElseThrow(()-> new UserNotFoundEx("El usuario no existe."));
        return ratingRepository.findAllByUsuarioDestinatario(destinatario)
                .stream()
                .map(RatingMapper::toResponse)
                .toList();
    }
}
