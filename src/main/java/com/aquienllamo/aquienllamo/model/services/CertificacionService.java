package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.dtos.Request.CertificacionDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.CertificacionDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.CertificacionEntity;
import com.aquienllamo.aquienllamo.model.entities.TecnicoEntity;
import com.aquienllamo.aquienllamo.model.exceptions.CertificacionNotFoundEx;
import com.aquienllamo.aquienllamo.model.exceptions.ImageDataTypeNotFoundEx;
import com.aquienllamo.aquienllamo.model.exceptions.TecnicoNotFoundEx;
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

    //crear certificacion
    public CertificacionDTOResponse crearCertificacion(String uuidTecnico, CertificacionDTORequest certificacion){
        TecnicoEntity tecnico=tecnicoRepository.findByUuid(uuidTecnico)
                .orElseThrow(()-> new TecnicoNotFoundEx("el técnico con ese uuid no existe."));

        CertificacionEntity nueva=certificacionMapper.toEntity(certificacion);
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
                .orElseThrow(()-> new CertificacionNotFoundEx("la certificacion con ese uuid no se encuentra."));
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
                .orElseThrow(()-> new CertificacionNotFoundEx("la certificacion con ese uuid no existe."));
        certificacionRepository.delete(nueva);
    }



}
