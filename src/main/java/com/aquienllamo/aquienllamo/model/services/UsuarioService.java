package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.dtos.Request.UsuarioDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.UsuarioDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.model.exceptions.ImageDataTypeNotFoundEx;
import com.aquienllamo.aquienllamo.model.exceptions.MinorFoundEx;
import com.aquienllamo.aquienllamo.model.exceptions.UserFoundEx;
import com.aquienllamo.aquienllamo.model.exceptions.UserNotFoundEx;
import com.aquienllamo.aquienllamo.model.mappers.UsuarioMapper;
import com.aquienllamo.aquienllamo.model.repositories.UsuarioRepository;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

@Service // aclaración es servicio
@RequiredArgsConstructor
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    // Registrar un nuevo usuario en el sistema.
    public UsuarioDTOResponse createUser(UsuarioDTORequest newUser){
        // si queremos que alguien se registre, verificamos q antes no exista su correo, o su dni
        if (usuarioRepository.existsByDni(newUser.getDni())){
            throw new UserFoundEx("El documento:"+newUser.getDni()+"ya se encuentra asociado.");
        }

        if (usuarioRepository.existsByEmail(newUser.getEmail())){
            throw new UserFoundEx("El correo: "+newUser.getEmail()+"ya está en uso.");
        }

        // validar edad

        if (Period.between(newUser.getFechaNacimiento(), LocalDate.now()).getYears() < 18){
            throw new MinorFoundEx("No se pueden registrar menores de 18 años.");
        }

        // el correo no existe, el dni tampoco y es > edad:

        UsuarioEntity user = UsuarioMapper.toEntity(newUser);
        // validar fotito


        // guardar user
        usuarioRepository.save(user);
        return usuarioMapper.toResponse(user);
    }

    // eliminar
    public String deleteUser(String dni){
        UsuarioEntity user = null;
        if (usuarioRepository.existsByDni(dni)){
            user = usuarioRepository.findByDni(dni).get();
            usuarioRepository.delete(user);
            return "Se ha eliminado con éxito";
        }else {
            throw new UserNotFoundEx("No se pudo eliminar el usuario ya que no se encontró el dni del mismo.");
        }
    }

    // procesar foto
    private void procesarImagen(UsuarioEntity user, UsuarioDTORequest request){
        if (request.getFoto() != null && !request.getFoto().isEmpty()){
            try{
                String tipoImagen = request.getFoto().getContentType();
                if (tipoImagen == null || !tipoImagen.startsWith("image/")){
                    throw new ImageDataTypeNotFoundEx("Formato no válido de imagen");
                }
                user.setTipoImagen(tipoImagen);
                user.setFoto(request.getFoto().getBytes());
            }catch (IOException errorImagen){
                throw new RuntimeException("Hubo un error con la imagen");
            }
        }else{
            user.setTipoImagen("None");
            user.setFoto(null);
        }
    }

    // actualizar


    // mostrar todos los usuarios


    // mostrar x algo específico -> dni o por email (tamb puede ser el id)
}
