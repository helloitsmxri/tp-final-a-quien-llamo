package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.dtos.Request.UsuarioDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.UsuarioDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.model.exceptions.*;
import com.aquienllamo.aquienllamo.model.mappers.UsuarioMapper;
import com.aquienllamo.aquienllamo.model.repositories.UsuarioRepository;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

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

        UsuarioEntity user = usuarioMapper.toEntity(newUser);
        // tengo q encriptar la clave... se hace dsps porq supuestamente bloquea el programa
        user.setClave(newUser.getClave());

        // validar fotito

        processImage(user, newUser);

        // guardar user
        usuarioRepository.save(user);
        return usuarioMapper.toResponse(user);
    }

    // eliminar
    public String deleteUser(String correo){
        UsuarioEntity user = null;
        if (usuarioRepository.existsByEmail(correo)){
            user = usuarioRepository.findByDni(correo).get();
            usuarioRepository.delete(user);
            return "Se ha eliminado con éxito";
        }else {
            throw new UserNotFoundEx("No se pudo eliminar el usuario ya que no se encontró el dni del mismo.");
        }
    }

    // procesar foto
    private void processImage(UsuarioEntity user, UsuarioDTORequest request){
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
    public UsuarioDTOResponse update(String uuid, UsuarioDTORequest request){
        UsuarioEntity user = usuarioRepository.findByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundEx("No se encontró dicho usuario"));

        user.setNombre(request.getNombre());
        user.setApellido(request.getApellido());

        String nuevoCorreo = request.getEmail();
        // si el nuevo correo es diferente al actual
        if (!user.getEmail().equals(nuevoCorreo)){
            if (usuarioRepository.existsByEmail(nuevoCorreo)){
                throw new UserFoundEx("Ese correo ya existe");
            }
            // y sino, lo seteo
            user.setEmail(nuevoCorreo);
        }

        user.setTelefono(request.getTelefono());
        user.setSobreMi(request.getSobreMi());

        // y si el user sube nueva foto:
        if (!request.getFoto().isEmpty() && request.getFoto() != null){
            processImage(user, request);
        }

        // guardar y mapear:
        return usuarioMapper.toResponse(usuarioRepository.save(user));

    }

    // mostrar todos los usuarios
    public List<UsuarioDTOResponse> getAllUsers() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponse)
                .collect(Collectors.toList());
    }

    // mostrar x algo específico -> dni o por email (tamb puede ser el id)
    public UsuarioDTOResponse getByDni(String dni){
        return usuarioRepository.findByDni(dni)
                .map(usuarioMapper::toResponse)
                .orElseThrow(() -> new UserNotFoundEx("No se encontró un usuario asociado a ese dni"));
    }

    public UsuarioDTOResponse getByEmail (String email){
        return usuarioRepository.findByEmail(email)
                .map(usuarioMapper::toResponse)
                .orElseThrow(() -> new UserNotFoundEx("No se encontró un usuario asociado a ese correo."));
    }

    public UsuarioDTOResponse login(String email, String passwordSinEncriptar){
        // validar correo:
        UsuarioEntity user = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundEx("El correo ingresado no existe en nuestro sistema."));

        // validar contraseña:
        if (!user.getClave().equals(passwordSinEncriptar)){
            throw new InvalidPasswordEx("Clave incorrecta");
        }

    }
}
