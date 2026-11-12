
package com.aquienllamo.aquienllamo.entidades.denuncia.controller;

import com.aquienllamo.aquienllamo.entidades.denuncia.dto.request.DenunciaDTORequest;
import com.aquienllamo.aquienllamo.entidades.denuncia.dto.response.DenunciaDTOResponse;
import com.aquienllamo.aquienllamo.entidades.denuncia.dto.response.DenunciaFotoDTO;
import com.aquienllamo.aquienllamo.entidades.denuncia.service.DenunciaService;
import com.aquienllamo.aquienllamo.entidades.denuncia.enums.EstadoDenunciaE;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/aquienllamo/denuncias")
@RestController
@RequiredArgsConstructor
public class DenunciaController {

    private final DenunciaService denunciaService;

    // DENUNCIAR DESDE UN CHAT

    @PostMapping(
            value = {
                    "/crear/{uuidChat}",
                    "/crear/chat/{uuidChat}"
            },
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @PreAuthorize("hasAnyRole('USUARIO', 'TECNICO')")
    public ResponseEntity<DenunciaDTOResponse> crearDenunciaDesdeChat(
            @PathVariable String uuidChat,
            @Valid @ModelAttribute DenunciaDTORequest denuncia
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        denunciaService.crearDenuncia(
                                denuncia,
                                uuidChat
                        )
                );
    }


    // DENUNCIAR DESDE UN PERFIL

    @PostMapping(
            value = "/crear/perfil/{uuidDenunciado}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @PreAuthorize("hasAnyRole('USUARIO', 'TECNICO')")
    public ResponseEntity<DenunciaDTOResponse> crearDenunciaDesdePerfil(
            @PathVariable String uuidDenunciado,
            @Valid @ModelAttribute DenunciaDTORequest denuncia
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        denunciaService.crearDenunciaDesdePerfil(
                                denuncia,
                                uuidDenunciado
                        )
                );
    }


    // LISTAR TODAS: ADMINISTRACIÓN

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERADMINISTRADOR')")
    public ResponseEntity<List<DenunciaDTOResponse>> listarDenuncias() {

        return ResponseEntity.ok(
                denunciaService.listarDenuncias()
        );
    }


    // BUSCAR UNA DENUNCIA

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAnyRole('USUARIO', 'TECNICO', 'ADMINISTRADOR', 'SUPERADMINISTRADOR')")
    public ResponseEntity<DenunciaDTOResponse> buscarPorUuid(
            @PathVariable String uuid
    ) {

        return ResponseEntity.ok(
                denunciaService.buscarPorUuid(uuid)
        );
    }


    // VISUALIZAR FOTO ADJUNTA A UNA DENUNCIA

    @GetMapping("/{uuid}/foto")
    @PreAuthorize("hasAnyRole('USUARIO', 'TECNICO', 'ADMINISTRADOR', 'SUPERADMINISTRADOR')")
    public ResponseEntity<byte[]> obtenerFoto(
            @PathVariable String uuid
    ) {

        DenunciaFotoDTO foto = denunciaService.obtenerFoto(uuid);

        MediaType tipoContenido = MediaType.parseMediaType(
                foto.tipoContenido()
        );

        return ResponseEntity.ok()
                .contentType(tipoContenido)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(foto.contenido().length))
                .header("X-Content-Type-Options", "nosniff")
                .body(foto.contenido());
    }


    // FILTRAR POR ESTADO: ADMINISTRACIÓN

    @GetMapping("/estado/{estado}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERADMINISTRADOR')")
    public ResponseEntity<List<DenunciaDTOResponse>> listarPorEstado(
            @PathVariable EstadoDenunciaE estado
    ) {

        return ResponseEntity.ok(
                denunciaService.listarPorEstado(estado)
        );
    }


    // DENUNCIAS SIN ASIGNAR

    @GetMapping("/sin-asignar")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERADMINISTRADOR')")
    public ResponseEntity<List<DenunciaDTOResponse>> obtenerDenunciasSinAsignar() {

        return ResponseEntity.ok(
                denunciaService.obtenerDenunciasSinAsignar()
        );
    }


    // ASIGNAR ADMINISTRADOR

    @PatchMapping("/{uuidDenuncia}/asignar-administrador/{uuidAdmin}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERADMINISTRADOR')")
    public ResponseEntity<DenunciaDTOResponse> asignarAdministrador(
            @PathVariable String uuidDenuncia,
            @PathVariable String uuidAdmin
    ) {

        return ResponseEntity.ok(
                denunciaService.asignarAdministrador(
                        uuidDenuncia,
                        uuidAdmin
                )
        );
    }


    // APROBAR DENUNCIA

    @PatchMapping("/{uuid}/aprobar")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERADMINISTRADOR')")
    public ResponseEntity<DenunciaDTOResponse> aprobar(
            @PathVariable String uuid,
            @RequestParam String mensaje
    ) {

        return ResponseEntity.ok(
                denunciaService.aprobarDenuncia(
                        uuid,
                        mensaje
                )
        );
    }


    // RECHAZAR DENUNCIA

    @PatchMapping("/{uuid}/rechazar")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERADMINISTRADOR')")
    public ResponseEntity<DenunciaDTOResponse> rechazar(
            @PathVariable String uuid,
            @RequestParam String mensaje
    ) {

        return ResponseEntity.ok(
                denunciaService.rechazarDenuncia(
                        uuid,
                        mensaje
                )
        );
    }


    // DENUNCIAS QUE REALIZÓ EL USUARIO

    @GetMapping("/mis-denuncias/creadas")
    @PreAuthorize("hasAnyRole('USUARIO', 'TECNICO')")
    public ResponseEntity<List<DenunciaDTOResponse>> verDenunciasCreadas() {

        return ResponseEntity.ok(
                denunciaService.misDenunciasCreadas()
        );
    }


    // DENUNCIAS QUE RECIBIÓ EL USUARIO

    @GetMapping("/mis-denuncias/recibidas")
    @PreAuthorize("hasAnyRole('USUARIO', 'TECNICO')")
    public ResponseEntity<List<DenunciaDTOResponse>> verDenunciasRecibidas() {

        return ResponseEntity.ok(
                denunciaService.misDenunciasRecibidas()
        );
    }


    // DENUNCIAS ASIGNADAS AL ADMINISTRADOR

    @GetMapping("/mis-denuncias")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERADMINISTRADOR')")
    public ResponseEntity<List<DenunciaDTOResponse>> verMisDenuncias(
            @RequestParam(required = false) EstadoDenunciaE estado
    ) {

        return ResponseEntity.ok(
                denunciaService.misDenuncias(estado)
        );
    }


    // ORDENAR DENUNCIAS: ADMINISTRACIÓN

    @GetMapping("/ordenar")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERADMINISTRADOR')")
    public ResponseEntity<List<DenunciaDTOResponse>> ordenar(
            @RequestParam(defaultValue = "desc") String orden
    ) {

        if ("asc".equalsIgnoreCase(orden)) {

            return ResponseEntity.ok(
                    denunciaService.denunciasMasViejasPrimero()
            );
        }

        if ("desc".equalsIgnoreCase(orden)) {

            return ResponseEntity.ok(
                    denunciaService.denunciasMasNuevasPrimero()
            );
        }

        return ResponseEntity.badRequest().build();
    }
}
