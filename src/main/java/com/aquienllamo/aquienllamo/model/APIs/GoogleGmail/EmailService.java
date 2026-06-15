package com.aquienllamo.aquienllamo.model.APIs.GoogleGmail;

import com.aquienllamo.aquienllamo.model.Enum.EstadoDenunciaE;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String from;

    private void enviarEmail(String para, String asunto, String mensaje){
        SimpleMailMessage email=new SimpleMailMessage();
        email.setFrom(from);
        email.setTo(para);
        email.setSubject(asunto);
        email.setText(mensaje);
        emailSender.send(email);
    }

    public void enviarBienvenida(String email, String nombre){
        enviarEmail(
                email,
                "Bienvenido a ¿A Quien LLamo?",
                """
                        Hola %s,
                        Gracias por registrarte en ¿A Quien Llamo?.
                        Ya podés comenzar a solicitar servicios o trabajar como técnico.
                        Saludos!
                        """.formatted(nombre)
        );
    }

    public void enviarRecuperacionClave(String email, String enlace){
        enviarEmail(
                email,
                "Recuperación de contraseña",
                """
                        Hemos recibido una solicitud para restablecer tu contraseña.
                        Utiliza el siguiente enlace:
                        %s
                        Si no realizaste esta solicitud, ignora este correo.
                        """.formatted(enlace)
        );
    }

    public void enviarPasswordActualizada(String email){
        enviarEmail(
                email,
                "Contraseña actualizada",
                """
                Tu contraseña fue actualizada correctamente.
    
                Si no realizaste este cambio, comunicate con soporte de inmediato.
                """
        );
    }

    public void enviarPresupuestoRecibido(String email, String nombreCliente){
        enviarEmail(
                email,
                "Nuevo presupuesto recibido",
                """
                        Hola %s,
                        Has recibido un nuevo presupuesto.
                        Ingresa a la plataforma para revisarlo.
                        Saludos.
                        """.formatted(nombreCliente)
        );
    }

    public void enviarPresupuestoAceptado(String email){
        enviarEmail(
                email,
                "Presupuesto aceptado",
                """
                        Tu presupuesto fue aceptado.
                        Ya podes coordinar el trabajo con el cliente.
                        """

        );
    }

    public void enviarPresupuestoRechazado(String email){
        enviarEmail(
                email,
                "Presupuesto rechazado",
                """
                        El cliente rechazó su presupuesto.
                        Podrás seguir recibiendo nuevas solicitudes.
                        """
        );
    }

    public void enviarTrabajoCreado(String email, String tituloTrabajo){
        enviarEmail(
                email,
                "Nuevo trabajo asignado.",
                """
                Se creó un nuevo trabajo asociado a tu solicitud.
                Trabajo: %s
                Ingresá a la plataforma para ver los detalles.
                """.formatted(tituloTrabajo)
        );
    }

    public void enviarPortafolioAprobado(String email){
        enviarEmail(
                email,
                "Portafolio aprobado.",
                """
                        Tu portafolio fue revisado y aprobado.
                        """
        );
    }

    public void enviarPortafolioRechazado(String email){
        enviarEmail(
                email,
                "Portafolio rechazado.",
                """
                        Tu portafolio fue revisado y rechazado.
                        Revisá las observaciones e intentá nuevamente.
                        """
        );
    }

    public void enviarCertificacionAprobada(String email){
        enviarEmail(
                email,
                "Certificación aprobada.",
                """
                        Tu certificación fue aprobada exitosamente.
                        Ya figura como validada en tu perfil.
                        """
        );
    }

    public void enviarCertificacionRechazada(String email, String motivo){
        enviarEmail(
                email,
                "Certificación rechazada.",
                """
                        Tu certificación fue rechazada.
                        Revisá la documentación enviada e intentá nuevamente.
                        """.formatted(motivo)
        );
    }

    public void enviarDenunciaAdmin(String emailAdmin, String uuidDenuncia){
        enviarEmail(
                emailAdmin,
                "Nueva denuncia recibida.",
                """
                        Se registró una nueva denuncia.
                        ID de denuncia: %s
                        Ingresá al panel de administración para revisarla.
                        """.formatted(uuidDenuncia)
        );
    }

    public void enviarDenunciaAprobadaDenunciante(String email) {
        enviarEmail(
                email,
                "Tu denuncia fue aprobada.",
                """
                        Tu denuncia fue revisada y aprobada por nuestro equipo.
                        Se tomarán las medidas correspondientes.
                        Gracias por ayudarnos a mantener la comunidad segura.
                        """
        );
    }

    public void enviarDenunciaAprobadaDenunciado(String email) {
        enviarEmail(
                email,
                "Notificación de denuncia.",
                """
                        Se ha procesado una denuncia asociada a tu cuenta.
                        Si considerás que se trata de un error, contactá al soporte.
                        """
        );
    }

    public void enviarDenunciaRechazadaDenunciante(String email) {
        enviarEmail(
                email,
                "Tu denuncia fue rechazada.",
                """
                        Tu denuncia fue revisada por nuestro equipo y no pudo ser aprobada.
                        Si tenés más información, podés volver a presentarla.
                        """
        );
    }

    public void enviarDenunciaRechazadaDenunciado(String email) {
        enviarEmail(
                email,
                "Tu denuncia fue rechazada.",
                """
                        Tu denuncia fue revisada por nuestro equipo y no pudo ser aprobada.
                        Si tenés más información, podés volver a presentarla.
                        """
        );
    }

    public void enviarSuspensionCuenta(String email, String motivo){
        enviarEmail(
                email,
                "Suspensión de cuenta",
                """
                        Tu cuenta ha sido suspendida.
                        Motivo:
                         %s
                         Si considerás que se trata de un error, contactá al soporte.
                        """.formatted(motivo)
        );
    }

}
