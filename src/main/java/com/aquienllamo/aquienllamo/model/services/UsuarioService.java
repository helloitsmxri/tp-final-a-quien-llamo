package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.dtos.UsuarioDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.UsuarioDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.model.exceptions.MinorFoundEx;
import com.aquienllamo.aquienllamo.model.exceptions.UserFoundEx;
import com.aquienllamo.aquienllamo.model.exceptions.UserNotFoundEx;
import com.aquienllamo.aquienllamo.model.mappers.UsuarioMapper;
import com.aquienllamo.aquienllamo.model.repositories.UsuarioRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

@Service // aclaración es servicio
@RequiredArgsConstructor
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


        UsuarioEntity user = UsuarioMapper.toEntity(newUser);

        if (Period.between(newUser.getFechaNacimiento(), LocalDate.now()).getYears() < 18){
            throw new MinorFoundEx("No se pueden registrar menores de 18 años.");
        }

        if (newUser.getFoto() != null && !newUser.getFoto().isEmpty()){
            try{
                String tipoImagen = newUser.getFoto().getContentType();
                if (tipoImagen == null || !tipoImagen.startsWith("image/")){
                    throw new RuntimeException("No es un formato válido de imagen");
                }

                user.setTipoImagen(tipoImagen);
                user.setFoto(newUser.getFoto().getBytes());

            }catch (IOException e){
             throw new RuntimeException("Hubo un error con la imagen");
            }
        }else {
            user.setTipoImagen("none");
            user.setFoto(null);
        }

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

    // actualizar


    // mostrar todos los usuarios


    // mostrar x algo específico -> dni o por email (tamb puede ser el id)
}
