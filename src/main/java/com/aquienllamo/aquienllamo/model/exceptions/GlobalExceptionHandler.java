package com.aquienllamo.aquienllamo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundEx.class)
    public ResponseEntity<ErrorResponseDTO> userNotFoundEx(UserNotFoundEx ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDTO.builder()
                .status(404)
                .mensaje(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(UserFoundEx.class)
    public ResponseEntity<ErrorResponseDTO> userFoundEx(UserFoundEx ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponseDTO.builder()
                .status(409)
                .mensaje(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(UbicacionNotFoundEx.class)
    public ResponseEntity<ErrorResponseDTO> ubicacionNotFoundEx(UbicacionNotFoundEx ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDTO.builder()
                .status(404)
                .mensaje(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(TrabajoNotFoundEx.class)
    public ResponseEntity<ErrorResponseDTO> trabajoNotFoundEx(TrabajoNotFoundEx ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDTO.builder()
                .status(404)
                .mensaje(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(TrabajoAlreadyExistsEx.class)
    public ResponseEntity<ErrorResponseDTO> trabajoAlreadyExistsEx(TrabajoAlreadyExistsEx ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponseDTO.builder()
                .status(409)
                .mensaje(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(TecnicoAlreadyExistsEx.class)
    public ResponseEntity<ErrorResponseDTO> tecnicoAlreadyExistsEx(TecnicoAlreadyExistsEx ex)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponseDTO.builder()
                .status(409)
                .mensaje(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(TecnicoNotFoundEx.class)
    public ResponseEntity<ErrorResponseDTO> tecnicoNotFoundEx(TecnicoNotFoundEx ex)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDTO.builder()
                .status(404)
                .mensaje(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }
    @ExceptionHandler(RubroAlreadyExistsEx.class)
    public ResponseEntity<ErrorResponseDTO> rubroAlreadyExistsEx(RubroAlreadyExistsEx ex)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponseDTO.builder()
                .status(409)
                .mensaje(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(PresupuestoNotFoundEx.class)
    public ResponseEntity<ErrorResponseDTO> presupuestoNotFoundEx(PresupuestoNotFoundEx ex)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDTO.builder()
                .status(404)
                .mensaje(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(MinorFoundEx.class)
    public ResponseEntity<ErrorResponseDTO> minorFoundEx(MinorFoundEx ex)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponseDTO.builder()
                .status(409)
                .mensaje(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(InvalidPasswordEx.class)
    public ResponseEntity<ErrorResponseDTO> invalidPasswordEx(InvalidPasswordEx ex)
    {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponseDTO.builder()
                .status(401)
                .mensaje(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(ImageDataTypeNotFoundEx.class)
    public ResponseEntity<ErrorResponseDTO> imageDataTypeNotFoundEx(ImageDataTypeNotFoundEx ex)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDTO.builder()
                .status(404)
                .mensaje(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }
    @ExceptionHandler(HabilidadNotFoundEx.class)
    public ResponseEntity<ErrorResponseDTO> habilidadNotFoundEx(HabilidadNotFoundEx ex)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDTO.builder()
                .status(404)
                .mensaje(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(EspecialidadNotFoundEx.class)
    public ResponseEntity<ErrorResponseDTO> especialidadNotFoundEx(EspecialidadNotFoundEx ex)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDTO.builder()
                .status(404)
                .mensaje(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(EspecialidadAlreadyExistsEx.class)
    public ResponseEntity<ErrorResponseDTO> especialidadAlreadyExistsEx(EspecialidadAlreadyExistsEx ex)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponseDTO.builder()
                .status(409)
                .mensaje(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(DuplicateCuitEx.class)
    public ResponseEntity<ErrorResponseDTO> duplicateCuitEx(DuplicateCuitEx ex)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponseDTO.builder()
                .status(409)
                .mensaje(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(ChatNotFoundEx.class)
    public ResponseEntity<ErrorResponseDTO> chatNotFoundEx(ChatNotFoundEx ex)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDTO.builder()
                .status(404)
                .mensaje(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(CertificacionNotFoundEx.class)
    public ResponseEntity<ErrorResponseDTO> certificacionNotFoundEx(CertificacionNotFoundEx ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDTO.builder()
                .status(404)
                .mensaje(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(AdministradorNotFoundEx.class)
    public ResponseEntity<ErrorResponseDTO> administradorNotFoundEx(AdministradorNotFoundEx ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDTO.builder()
                .status(404)
                .mensaje(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(AdminAsignadoDenunciaEx.class)
    public ResponseEntity<ErrorResponseDTO> adminAsignadoDenunciaEx(AdminAsignadoDenunciaEx ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponseDTO.builder()
                .status(400)
                .mensaje(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(DenunciaResueltaEx.class)
    public ResponseEntity<ErrorResponseDTO> denunciaResueltaEx(DenunciaResueltaEx ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponseDTO.builder()
                        .status(400)
                        .mensaje(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(CertificacionEstadoInvalidoEx.class)
    public ResponseEntity<ErrorResponseDTO> certificacionEstadoInvalidoEx(CertificacionEstadoInvalidoEx ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponseDTO.builder()
                        .status(400)
                        .mensaje(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(RoleNotFoundEx.class)
    public ResponseEntity<ErrorResponseDTO> roleNotFoundEx(RoleNotFoundEx ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDTO.builder()
                        .status(404)
                        .mensaje(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(CredentialsNotFoundEx.class)
    public ResponseEntity<ErrorResponseDTO> credentialsNotFoundEx(CredentialsNotFoundEx ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDTO.builder()
                        .status(404)
                        .mensaje(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    //500 INTERNAL SERVER ERROR - catch-all de seguridad
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleUnexpected(Exception ex)
    {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponseDTO.builder()
                .status(500)
                .timestamp(LocalDateTime.now())
                .mensaje(ex.getMessage())
                .build());

    }
}
