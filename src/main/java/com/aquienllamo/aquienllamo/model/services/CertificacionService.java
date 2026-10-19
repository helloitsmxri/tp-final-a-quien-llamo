package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.APIs.GoogleGmail.EmailService;
import com.aquienllamo.aquienllamo.model.Enum.EstadoVerificacion;
import com.aquienllamo.aquienllamo.model.dtos.Request.CertificacionDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.CertificacionDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.CertificacionEntity;
import com.aquienllamo.aquienllamo.model.entities.TecnicoEntity;
import com.aquienllamo.aquienllamo.model.exceptions.*;
import com.aquienllamo.aquienllamo.model.mappers.CertificacionMapper;
import com.aquienllamo.aquienllamo.model.repositories.CertificacionRepository;
import com.aquienllamo.aquienllamo.model.repositories.TecnicoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CertificacionService {
    private final CertificacionRepository certificacionRepository;
    private final CertificacionMapper certificacionMapper;
    private final TecnicoRepository tecnicoRepository;
    private final EmailService emailService;

    //crear certificacion
    public CertificacionDTOResponse crearCertificacion(String uuidTecnico, CertificacionDTORequest certificacion){
        TecnicoEntity tecnico=tecnicoRepository.findByUuid(uuidTecnico)
                .orElseThrow(()-> new TecnicoNotFoundEx("el técnico con ese uuid no existe."));

        CertificacionEntity nueva=certificacionMapper.toEntity(certificacion);
        nueva.setEstadoVerificacion(EstadoVerificacion.Pendiente);
        nueva.setNotasAdmin(null);
        nueva.setTecnico(tecnico);
        nueva.setImagenCertificado(extraerBytes(certificacion.getImagenCertificado()));
        nueva.setTipoImagen(certificacion.getImagenCertificado().getContentType());
        return certificacionMapper.toResponse(certificacionRepository.save(nueva));
    }

    //metodo helper que convierte el MultipartFile a byte[]
    public byte[] extraerBytes(MultipartFile file){
        try {
            return file.getBytes();
        }catch (IOException e){
            throw new ImageDataTypeNotFoundEx("No se pudo procesar la imagen del certificado");
        }
    }

    public List<CertificacionDTOResponse> obtenerTecnicos(String uuidTecnico){
        return certificacionRepository.findByTecnico_Uuid(uuidTecnico)
                .stream()
                .map(certificacionMapper::toResponse)
                .toList();
    }

    //actualizar
    public CertificacionDTOResponse actualizarCertificacion(String uuid, CertificacionDTORequest certificacion){
        CertificacionEntity nueva=certificacionRepository.findByUuid(uuid)
                .orElseThrow(()-> new CertificacionNotFoundEx("la certificacion con ese uuid no se encontro."));
        nueva.setNumMatricula(certificacion.getNumMatricula());
        nueva.setEnteOtorgador(certificacion.getEnteOtorgador());
        nueva.setFechaVencimiento(certificacion.getFechaVencimiento());

        if (certificacion.getImagenCertificado()!=null && !certificacion.getImagenCertificado().isEmpty()){
            nueva.setImagenCertificado(extraerBytes(certificacion.getImagenCertificado()));
            nueva.setTipoImagen(certificacion.getImagenCertificado().getContentType());
        }
        return certificacionMapper.toResponse(certificacionRepository.save(nueva));
    }

    //eliminar
    public void eliminarCertificacion(String uuid){
        CertificacionEntity nueva=certificacionRepository.findByUuid(uuid)
                .orElseThrow(()-> new CertificacionNotFoundEx("la certificacion con ese uuid no se encontro."));
        certificacionRepository.delete(nueva);
    }

    //listar certificaciones
    public List<CertificacionDTOResponse> listarCertificaciones(){
        return certificacionRepository.findAll()
                .stream()
                .map(certificacionMapper::toResponse)
                .toList();
    }

    //buscar por uuid
    public CertificacionDTOResponse buscarPorUuid(String uuid){
        return certificacionRepository.findByUuid(uuid)
                .map(certificacionMapper::toResponse)
                .orElseThrow(()-> new CertificacionNotFoundEx("la certificacion con ese uuid no se encontro."));
    }

    //aprobar certificacion
    public CertificacionDTOResponse aprobarCertificacion(String uuid){
        CertificacionEntity certificacion=certificacionRepository.findByUuid(uuid)
                .orElseThrow(()-> new CertificacionNotFoundEx("La certificacion con ese uuid no se encontro"));

        if (certificacion.getEstadoVerificacion()!=EstadoVerificacion.Pendiente){
            throw new CertificacionEstadoInvalidoEx("Solo se pueden aprobar certificaciones pendientes. El estado de esta certificacion actual es: "+certificacion.getEstadoVerificacion());
        }
        certificacion.setEstadoVerificacion(EstadoVerificacion.Aprobado);
        certificacion.setNotasAdmin(null);

        certificacionRepository.save(certificacion);

        emailService.enviarCertificacionAprobada(certificacion.getTecnico().getUsuario().getEmail(), "http://localhost:8080/aquienllamo/certificaciones/" + certificacion.getUuid());
        return certificacionMapper.toResponse(certificacion);
    }

    //rechazar certificacion
    public CertificacionDTOResponse rechazarCertificacion(String uuid, String motivo){
        CertificacionEntity certificacion=certificacionRepository.findByUuid(uuid)
                .orElseThrow(()-> new  CertificacionNotFoundEx("Certificacion no encontrada"));

        if (certificacion.getEstadoVerificacion()!=EstadoVerificacion.Pendiente){
            throw new CertificacionEstadoInvalidoEx("Solo se pueden rechazar certificaciones pendientes. El estado de esta certificacion actual es: "+certificacion.getEstadoVerificacion());
        }

        certificacion.setEstadoVerificacion(EstadoVerificacion.Rechazado);
        certificacion.setNotasAdmin(motivo);
        certificacionRepository.save(certificacion);

        emailService.enviarCertificacionRechazada(certificacion.getTecnico().getUsuario().getEmail(), motivo);
        return certificacionMapper.toResponse(certificacion);
    }

    //filtrar por estado
    public List<CertificacionDTOResponse> filtrarPorEstado(EstadoVerificacion estado){
        return certificacionRepository
                .findByEstadoVerificacion(estado)
                .stream()
                .map(certificacionMapper::toResponse)
                .toList();
    }

}
