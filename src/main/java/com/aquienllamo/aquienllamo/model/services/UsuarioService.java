package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.auth.Credentials.CredentialsEntity;
import com.aquienllamo.aquienllamo.model.auth.permissions.RoleEntity;
import com.aquienllamo.aquienllamo.model.auth.permissions.RolesUser;
import com.aquienllamo.aquienllamo.model.auth.repositories.RoleRepository;
import com.aquienllamo.aquienllamo.model.dtos.Request.UsuarioDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.UsuarioDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.model.exceptions.*;
import com.aquienllamo.aquienllamo.model.mappers.UsuarioMapper;
import com.aquienllamo.aquienllamo.model.auth.repositories.CredentialsRepository;
import com.aquienllamo.aquienllamo.model.repositories.TecnicoRepository;
import com.aquienllamo.aquienllamo.model.repositories.UsuarioRepository;
import lombok.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private final TecnicoRepository tecnicoRepository;
    private final CredentialsRepository credentialsRepository;
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public String determinarTipoUsuario(String uuid) {
        return tecnicoRepository.existsByUsuarioUuid(uuid) ? "TECNICO" : "CLIENTE";
    }

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
        // tengo q encriptar la clave...
        user.setClave(passwordEncoder.encode(newUser.getClave()));

        // validar fotito

        processImage(user, newUser);

        // guardar user y devolverlo
        usuarioRepository.save(user);

        // buscar ROLE_USUARIO
        RoleEntity roleUsuario = roleRepository.findByRole(RolesUser.ROLE_USUARIO)
                                .orElseThrow(() -> new RuntimeException("No existe ROLE_USUARIO"));

        // crear credenciales
        CredentialsEntity credencial =
                CredentialsEntity.builder()
                        .username(user.getEmail())
                        .clave(user.getClave())
                        .enabled(true)
                        .usuario(user)
                        .build();

        // asignar rol
        credencial.getRoles().add(roleUsuario);

        // guardar credenciales
        credentialsRepository.save(credencial);

        // acá genero la response
        UsuarioDTOResponse response = usuarioMapper.toResponse(user);
        response.setTipoUsuario(determinarTipoUsuario(user.getUuid()));

        return response;
    }

    // eliminar: revisar si con el email como USERNAME hay q buscar x uuid o x correo.
    public String deleteUser(String uuid, String password){
        UsuarioEntity user = usuarioRepository.findByUuid(uuid)
                .orElseThrow(()->new UserNotFoundEx("No se encontró el usuario"));

        CredentialsEntity cred = credentialsRepository.findByUsuario(user)
                .orElseThrow(() -> new UserNotFoundEx("No se encontró el usuario"));

            if (!passwordEncoder.matches(password, cred.getPassword())){ // acá tamb puede ser get clave están los 2 métodos
                throw new InvalidPasswordEx("¡Clave incorrecta!");
            }

            credentialsRepository.delete(cred);
            usuarioRepository.delete(user);
            return "Se ha eliminado con éxito";

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

            // como credencial tiene de username el correo:
            CredentialsEntity credencial = credentialsRepository.findByUsuario(user)
                            .orElseThrow(() -> new UserNotFoundEx("El usuario no existe."));

            credencial.setUsername(nuevoCorreo);
            credentialsRepository.save(credencial);
        }

        user.setTelefono(request.getTelefono());
        user.setSobreMi(request.getSobreMi());

        // y si el user sube nueva foto:
        if (request.getFoto() != null && !request.getFoto().isEmpty()){
            processImage(user, request);
        }

        // guardar y mapear:

        return usuarioMapper.toResponse(usuarioRepository.save(user));

    }

    // mostrar todos los usuarios admin
    public List<UsuarioDTOResponse> getAllUsers() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponse)
                .collect(Collectors.toList());
    }


    // mostrar x algo específico -> dni o por email (tamb puede ser el id)
    // estas son más p/los admins je
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

    // para el usuario común:
    public UsuarioDTOResponse getByUuid(String uuid){
        UsuarioEntity user = usuarioRepository.findByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundEx("No se encontró el usuario"));

        UsuarioDTOResponse response = usuarioMapper.toResponse(user);
        response.setTipoUsuario(determinarTipoUsuario(uuid));

        return response;
    }

    // VER MI PERFIL, este es xq es más fácil ver un perfil sin uuid.
    public UsuarioDTOResponse getMyProfile(String username){
        CredentialsEntity cred = credentialsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No existe ese usuario"));

        UsuarioEntity user = cred.getUsuario();

        UsuarioDTOResponse res = usuarioMapper.toResponse(user);
        res.setTipoUsuario(determinarTipoUsuario(user.getUuid()));
        return res;
    }

    // se me ocurrió q para amonestar sea un mes de baja
    public String amonestarUsuario(String uuid){
        UsuarioEntity user = usuarioRepository.findByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundEx("No se encontró el usuario"));

        user.setFechaFinSuspension(LocalDate.now().plusMonths(1));
        usuarioRepository.save(user);

        return "Se ha suspendido al usuario por un mes";
    }

    public String darDeBajaUsuario(String uuid){
        UsuarioEntity user = usuarioRepository.findByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundEx("No se encontró el usuario"));

        user.setActivo(false);
        usuarioRepository.save(user);
        return "Usuario dado de baja correctamente";
    }

}
