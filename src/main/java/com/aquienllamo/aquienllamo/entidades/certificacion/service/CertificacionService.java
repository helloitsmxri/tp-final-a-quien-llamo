package com.aquienllamo.aquienllamo.entidades.certificacion.service;

import com.aquienllamo.aquienllamo.APIs.GoogleGmail.EmailService;
import com.aquienllamo.aquienllamo.administrador.enums.EstadoVerificacion;
import com.aquienllamo.aquienllamo.entidades.certificacion.mapper.CertificacionMapper;
import com.aquienllamo.aquienllamo.entidades.certificacion.repository.CertificacionRepository;
import com.aquienllamo.aquienllamo.entidades.certificacion.dtos.request.CertificacionDTORequest;
import com.aquienllamo.aquienllamo.entidades.certificacion.dtos.response.CertificacionDTOResponse;
import com.aquienllamo.aquienllamo.entidades.certificacion.entity.CertificacionEntity;
import com.aquienllamo.aquienllamo.entidades.certificacion.exceptions.CertificacionEstadoInvalidoEx;
import com.aquienllamo.aquienllamo.entidades.certificacion.exceptions.CertificacionNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.especialidad.entity.EspecialidadEntity;
import com.aquienllamo.aquienllamo.entidades.especialidad.exceptions.EspecialidadNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.especialidad.repository.EspecialidadRepository;
import com.aquienllamo.aquienllamo.exception.generales.ImageDataTypeNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.tecnico.entity.TecnicoEntity;
import com.aquienllamo.aquienllamo.entidades.tecnico.repository.TecnicoRepository;
import com.aquienllamo.aquienllamo.entidades.tecnico.exceptions.TecnicoNotFoundEx;
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
    private final EspecialidadRepository especialidadRepository;
    private final EmailService emailService;

    //crear certificacion
    public CertificacionDTOResponse crearCertificacion(String uuidTecnico, CertificacionDTORequest certificacion){
        TecnicoEntity tecnico=tecnicoRepository.findByUuid(uuidTecnico)
                .orElseThrow(()-> new TecnicoNotFoundEx("el técnico con ese uuid no existe."));

        if (certificacion.getTitulo() == null || certificacion.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El título de la certificación es obligatorio.");
        }

        CertificacionEntity nueva=certificacionMapper.toEntity(certificacion);
        nueva.setEstadoVerificacion(EstadoVerificacion.Pendiente);
        nueva.setNotasAdmin(null);
        nueva.setTecnico(tecnico);
        nueva.setEspecialidad(buscarEspecialidad(certificacion.getUuidEspecialidad(), true));
        nueva.setImagenCertificado(extraerBytes(certificacion.getImagenCertificado()));
        nueva.setTipoImagen(certificacion.getImagenCertificado().getContentType());
        return certificacionMapper.toResponse(certificacionRepository.save(nueva));
    }

    //busca la especialidad por uuid; si es obligatoria y no viene, o no existe, falla
    private EspecialidadEntity buscarEspecialidad(String uuidEspecialidad, boolean obligatoria){
        if (uuidEspecialidad == null || uuidEspecialidad.isBlank()){
            if (obligatoria){
                throw new EspecialidadNotFoundEx("La especialidad de la certificación es obligatoria.");
            }
            return null;
        }
        return especialidadRepository.findByUuid(uuidEspecialidad)
                .orElseThrow(() -> new EspecialidadNotFoundEx("La especialidad indicada no existe."));
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
        if (certificacion.getTitulo() != null && !certificacion.getTitulo().isBlank()) {
            nueva.setTitulo(certificacion.getTitulo());
        }
        if (certificacion.getNumMatricula() != null) {
            nueva.setNumMatricula(certificacion.getNumMatricula());
        }

        if (certificacion.getEnteOtorgador() != null) {
            nueva.setEnteOtorgador(certificacion.getEnteOtorgador());
        }

        if (certificacion.getFechaVencimiento() != null) {
            nueva.setFechaVencimiento(certificacion.getFechaVencimiento());
        }

        // si no viene especialidad se conserva la que ya tenía
        EspecialidadEntity especialidad = buscarEspecialidad(certificacion.getUuidEspecialidad(), false);
        if (especialidad != null) {
            nueva.setEspecialidad(especialidad);
        }

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