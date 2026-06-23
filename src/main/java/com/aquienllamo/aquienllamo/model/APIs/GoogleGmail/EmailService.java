package com.aquienllamo.aquienllamo.model.APIs.GoogleGmail;

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
        enviarEmail(email,"¡Te damos la bienvenida a ¿A Quién Llamo?!",
                """
                Hola, %s:
                
                ¡Gracias por registrarte en "¿A Quién Llamo?"!
                Ya podés comenzar a solicitar servicios u ofrecerlos.
                Acordate que si sos un usuario común, siempre podés pasarte al Plan Dúo y empezar a ofrecer tus servicios.
                
                Saludos,
                El equipo de ¿A Quién Llamo?
                
                AVISO DE SEGURIDAD IMPORTANTE
                Recordá que desde ¿A Quién Llamo? JAMÁS te vamos a solicitar contraseñas, datos de tarjetas de crédito o códigos por este medio.
                Para proteger tu cuenta, revisá siempre que el correo del cual recibís nuestros avisos provenga de una dirección oficial terminada en @aquienllamo.com. Si desconfiás de la legitimidad de un mensaje, no hagas clic en ningún enlace y comunicate con soporte.
                """.formatted(nombre)
        );
    }

    public void enviarBienvenida2(String email, String nombre){
        enviarEmail(email,"¿List@ para empezar a trabajar juntos?",
                """
                Hola, %s:
                
                ¡Gracias por registrarte en "¿A Quién Llamo?"!
                Ya podés comenzar a ofrecer tus servicios.
                Acordate, que además, podés solicitar servicios en cualquier momento.

                Saludos,
                El equipo de ¿A Quién Llamo?
                
                AVISO DE SEGURIDAD IMPORTANTE
                Recordá que desde ¿A Quién Llamo? JAMÁS te vamos a solicitar contraseñas, datos de tarjetas de crédito o códigos por este medio.
                Para proteger tu cuenta, revisá siempre que el correo del cual recibís nuestros avisos provenga de una dirección oficial terminada en @aquienllamo.com. Si desconfiás de la legitimidad de un mensaje, no hagas clic en ningún enlace y comunicate con soporte.
                """.formatted(nombre)
        );
    }

    public void enviarRecuperacionClave(String email, String enlace){
        enviarEmail(
                email, "Recuperá tu clave.",
                """
                        ¡Hola!
                        Recibimos una solicitud para restablecer la contraseña de tu cuenta.
                        Podés hacerlo ingresando al siguiente enlace:
                        %s
                        
                        Si no realizaste esta solicitud, podés desestimar este correo.
                        
                        Saludos,
                        El equipo de ¿A Quién Llamo?
                        
                        AVISO DE SEGURIDAD IMPORTANTE
                        Recordá que desde ¿A Quién Llamo? JAMÁS te vamos a solicitar contraseñas, datos de tarjetas de crédito o códigos por este medio.
                        Para proteger tu cuenta, revisá siempre que el correo del cual recibís nuestros avisos provenga de una dirección oficial terminada en @aquienllamo.com. Si desconfiás de la legitimidad de un mensaje, no hagas clic en ningún enlace y comunicate con soporte.
                        """.formatted(enlace)
        );
    }

    public void enviarPasswordActualizada(String email){
        enviarEmail(
                email, "Contraseña actualizada exitosamente",
                """
                        ¡Hola!
                        Te informamos que tu contraseña fue actualizada correctamente.
                        Si no realizaste este cambio, por favor comunícate con nuestro soporte de manera inmediata.
                        
                        Saludos,
                        El equipo de ¿A Quién Llamo?
                        
                        AVISO DE SEGURIDAD IMPORTANTE
                        Recordá que desde ¿A Quién Llamo? JAMÁS te vamos a solicitar contraseñas, datos de tarjetas de crédito o códigos por este medio.
                        Para proteger tu cuenta, revisá siempre que el correo del cual recibís nuestros avisos provenga de una dirección oficial terminada en @aquienllamo.com. Si desconfiás de la legitimidad de un mensaje, no hagas clic en ningún enlace y comunicate con soporte.
                        """
        );
    }

    public void enviarPresupuestoRecibido(String email, String nombreCliente){
        enviarEmail(email, "Nuevo presupuesto recibido",
                """
                        ¡Hola, %s!
                        Recibiste un nuevo presupuesto para tu solicitud.
                        Ingresá a la plataforma para revisar los detalles y los costos.
                        Recordá que no pueden obligarte a aceptar un presupuesto, si eso ocurre, contáctate con soporte.
                        
                        Saludos,
                        El equipo de ¿A Quién Llamo?
                        
                        AVISO DE SEGURIDAD IMPORTANTE
                        Recordá que desde ¿A Quién Llamo? JAMÁS te vamos a solicitar contraseñas, datos de tarjetas de crédito o códigos por este medio.
                        Para proteger tu cuenta, revisá siempre que el correo del cual recibís nuestros avisos provenga de una dirección oficial terminada en @aquienllamo.com. Si desconfiás de la legitimidad de un mensaje, no hagas clic en ningún enlace y comunicate con soporte.
                        """.formatted(nombreCliente)
                );
    }

    public void enviarPresupuestoAceptado(String email){
        enviarEmail(
                email, "¡Tu presupuesto fue aceptado!",
                """
                        ¡Buenas noticias!
                        El cliente aceptó tu presupuesto. Ya podés ponerte en contacto.
                        
                        Ingresá a la plataforma, y revisá los detalles.
                        ¡Muchos éxitos!
                        
                        Saludos,
                        El equipo de ¿A Quién Llamo?
                        
                        AVISO DE SEGURIDAD IMPORTANTE
                        Recordá que desde ¿A Quién Llamo? JAMÁS te vamos a solicitar contraseñas, datos de tarjetas de crédito o códigos por este medio.
                        Para proteger tu cuenta, revisá siempre que el correo del cual recibís nuestros avisos provenga de una dirección oficial terminada en @aquienllamo.com. Si desconfiás de la legitimidad de un mensaje, no hagas clic en ningún enlace y comunicate con soporte.
                        """

        );
    }

    public void enviarPresupuestoRechazado(String email){
        enviarEmail(
                email,"Actualización sobre tu presupuesto",
                """
                        Hola:
                        Te informamos que el cliente rechazó el presupuesto enviado.
                        No te preocupes, vas a seguir recibiendo nuevas solicitudes.
                        Revisá nuevamente el chat con tu cliente, en ocasiones, pueden discutir un nuevo presupuesto.
                        
                        Saludos,
                        El equipo de ¿A Quién Llamo?
                        
                        AVISO DE SEGURIDAD IMPORTANTE
                        Recordá que desde ¿A Quién Llamo? JAMÁS te vamos a solicitar contraseñas, datos de tarjetas de crédito o códigos por este medio.
                        Para proteger tu cuenta, revisá siempre que el correo del cual recibís nuestros avisos provenga de una dirección oficial terminada en @aquienllamo.com. Si desconfiás de la legitimidad de un mensaje, no hagas clic en ningún enlace y comunicate con soporte.
                        """
        );
    }

    public void enviarTrabajoCreado(String email, String tituloTrabajo){
        enviarEmail(
                email, "¿List@ para empezar a trabajar juntos?",
                """
                ¡Hola!
                Se generó un nuevo trabajo asociado a tu solicitud.
                Detalle: %s
                
                Ingresa a la plataforma para ver el estado y los próximos pasos a seguir.
                
                Saludos,
                El equipo de ¿A Quién Llamo?
                
                AVISO DE SEGURIDAD IMPORTANTE
                Recordá que desde ¿A Quién Llamo? JAMÁS te vamos a solicitar contraseñas, datos de tarjetas de crédito o códigos por este medio.
                Para proteger tu cuenta, revisá siempre que el correo del cual recibís nuestros avisos provenga de una dirección oficial terminada en @aquienllamo.com. Si desconfiás de la legitimidad de un mensaje, no hagas clic en ningún enlace y comunicate con soporte.
                """.formatted(tituloTrabajo)
        );
    }

    public void enviarPortafolioAprobado(String email, String enlace){
        enviarEmail(
                email, "Portafolio aprobado.",
                """
                        ¡Buenas noticias!
                        
                        Tu portfolio de trabajos fue revisado y le dimos el visto bueno.
                        Podés además, agregarlo en la plataforma.
                        
                        Ingresá acá para ver acá los detalles de la decisión:
                        %s
                        
                        ¡Muchos éxitos!
                        Saludos,
                        El equipo de ¿A Quién Llamo?
                        
                        AVISO DE SEGURIDAD IMPORTANTE
                        Recordá que desde ¿A Quién Llamo? JAMÁS te vamos a solicitar contraseñas, datos de tarjetas de crédito o códigos por este medio.
                        Para proteger tu cuenta, revisá siempre que el correo del cual recibís nuestros avisos provenga de una dirección oficial terminada en @aquienllamo.com. Si desconfiás de la legitimidad de un mensaje, no hagas clic en ningún enlace y comunicate con soporte.
                        """.formatted(enlace)
        );
    }

    public void enviarPortafolioRechazado(String email, String nombre, String enlace){
        enviarEmail(
                email, "Tu portfolio requiere correcciones",
                        """
                        Hola %s:
                        Revisamos tu portfolio, y por el momento, no puede ser aprobado.
                        
                        Ingresá a este enlace para ver las notas que dejó el administrador que supervisó tu portfolio:
                        %s
                        Si creés que se trata de un error, podés comunicarte con soporte.
                        Por otro lado, si considerás que la nota es válida, podés editar el portfolio y volverlo a enviar.
                        
                        Saludos,
                        El equipo de ¿A Quién Llamo?
                        
                        AVISO DE SEGURIDAD IMPORTANTE
                        Recordá que desde ¿A Quién Llamo? JAMÁS te vamos a solicitar contraseñas, datos de tarjetas de crédito o códigos por este medio.
                        Para proteger tu cuenta, revisá siempre que el correo del cual recibís nuestros avisos provenga de una dirección oficial terminada en @aquienllamo.com. Si desconfiás de la legitimidad de un mensaje, no hagas clic en ningún enlace y comunicate con soporte.
                        """.formatted(nombre, enlace)
        );
    }

    public void enviarCertificacionAprobada(String email, String enlace){
        enviarEmail(
                email, "Certificación validada con éxito",
                """
                        ¡Buenas noticias!
                        La certificación que enviaste fue aprobada exitosamente y ya podés comenzar a trabajar.
                        
                        Ingresá acá si querés saber más sobre la decisión que tomamos:
                        %s
                        
                        Saludos,
                        El equipo de ¿A Quién Llamo?
                        
                        AVISO DE SEGURIDAD IMPORTANTE
                        Recordá que desde ¿A Quién Llamo? JAMÁS te vamos a solicitar contraseñas, datos de tarjetas de crédito o códigos por este medio.
                        Para proteger tu cuenta, revisá siempre que el correo del cual recibís nuestros avisos provenga de una dirección oficial terminada en @aquienllamo.com. Si desconfiás de la legitimidad de un mensaje, no hagas clic en ningún enlace y comunicate con soporte.
                        """.formatted(enlace)
        );
    }

    public void enviarCertificacionRechazada(String email, String motivo){
        enviarEmail(
                email, "Tu certificación no pudo ser aprobada",
                        """
                        Hola:
                        Lamentamos informarte que la documentación que nos proporcionaste fue rechazada.
                        Motivo: %s
                        
                        Podés revisar los requisitos y volver a adjuntarlo si lo ves necesario.
                        Si considerás que se trata de un error, siempre podés comunicarte con el soporte.
                        
                        Saludos,
                        El equipo de ¿A Quién Llamo?
                        
                        AVISO DE SEGURIDAD IMPORTANTE
                        Recordá que desde ¿A Quién Llamo? JAMÁS te vamos a solicitar contraseñas, datos de tarjetas de crédito o códigos por este medio.
                        Para proteger tu cuenta, revisá siempre que el correo del cual recibís nuestros avisos provenga de una dirección oficial terminada en @aquienllamo.com. Si desconfiás de la legitimidad de un mensaje, no hagas clic en ningún enlace y comunicate con soporte.
                        """.formatted(motivo)
        );
    }

    // en este caso no veo necesario el aviso de seguridad, pero por las dudas, sea lo q sea, lo dejamos
    public void enviarDenunciaAdmin(String emailAdmin, String uuidDenuncia){
        enviarEmail(emailAdmin,
                "[IMPORTANTE] ¡NUEVA DENUNCIA REGISTRADA!",
                        """
                        Atención:
                        Se registró una nueva denuncia en la plataforma.
                        ID de denuncia: %s
                        
                        Ingresá al panel de administración para revisarla.
                        
                        AVISO DE SEGURIDAD IMPORTANTE
                        Recordá que desde ¿A Quién Llamo? JAMÁS te vamos a solicitar contraseñas, datos de tarjetas de crédito o códigos por este medio.
                        Para proteger tu cuenta, revisá siempre que el correo del cual recibís nuestros avisos provenga de una dirección oficial terminada en @aquienllamo.com. Si desconfiás de la legitimidad de un mensaje, no hagas clic en ningún enlace y comunicate con soporte.
                        """.formatted(uuidDenuncia)
        );
    }

    public void enviarDenunciaAprobadaDenunciante(String email, String nombreDenunciante, String enlace) {
        enviarEmail(
                email, "Actualización sobre tu denuncia",
                """
                        Hola, %s.
                        Te informamos que la denuncia que presentaste fue revisada por nuestros administradores.
                        Se tomaron las medidas correspondientes según nuestros términos.
                        
                        Si querés ver más información al respecto, ingresá acá:
                        %s
                        
                        Gracias por ayudarnos a mantener la comunidad segura.
                        Saludos,
                        El equipo de ¿A Quién Llamo?
                        
                        AVISO DE SEGURIDAD IMPORTANTE
                        Recordá que desde ¿A Quién Llamo? JAMÁS te vamos a solicitar contraseñas, datos de tarjetas de crédito o códigos por este medio.
                        Para proteger tu cuenta, revisá siempre que el correo del cual recibís nuestros avisos provenga de una dirección oficial terminada en @aquienllamo.com. Si desconfiás de la legitimidad de un mensaje, no hagas clic en ningún enlace y comunicate con soporte.
                        """.formatted(nombreDenunciante, enlace)
        );
    }

    public void enviarDenunciaAprobadaDenunciado(String email, String nombreDenunciado, String enlace) {
        enviarEmail(
                email, "Notificación de sanción",
                """
                        Hola %s:
                        Se ha procesado una denuncia asociada a tu cuenta, ésta fue aprobada.
                        Las acciones correspondientes ya fueron aplicadas.
                        
                        Ingresá acá para consultar los motivos:
                        %s
                        
                        Si considerás que se trata de un error o querés presentar un descargo al respecto, contactá al soporte.
                        Saludos,
                        El equipo de ¿A Quién Llamo?
                        
                        AVISO DE SEGURIDAD IMPORTANTE
                        Recordá que desde ¿A Quién Llamo? JAMÁS te vamos a solicitar contraseñas, datos de tarjetas de crédito o códigos por este medio.
                        Para proteger tu cuenta, revisá siempre que el correo del cual recibís nuestros avisos provenga de una dirección oficial terminada en @aquienllamo.com. Si desconfiás de la legitimidad de un mensaje, no hagas clic en ningún enlace y comunicate con soporte.
                        """.formatted(nombreDenunciado, enlace)
        );
    }

    public void enviarDenunciaRechazadaDenunciante(String email, String nombreDenunciante, String enlace) {
        enviarEmail(email, "Resolución de tu denuncia",
                """
                        Hola %s:
                        
                        Revisamos la denuncia que presentaste, pero no encontramos pruebas suficientes para aplicar una sanción en este momento.
                        Por lo tanto, fue rechazada.
                        
                        Si querés saber más sobre los motivos para tomar esta decisión, ingresá acá:
                        %s
                        
                        Si tenés más información o nuevas pruebas, podés volver a contactarte con el centro de denuncias.
                        Saludos,
                        El equipo de ¿A Quién Llamo?
                        
                        AVISO DE SEGURIDAD IMPORTANTE
                        Recordá que desde ¿A Quién Llamo? JAMÁS te vamos a solicitar contraseñas, datos de tarjetas de crédito o códigos por este medio.
                        Para proteger tu cuenta, revisá siempre que el correo del cual recibís nuestros avisos provenga de una dirección oficial terminada en @aquienllamo.com. Si desconfiás de la legitimidad de un mensaje, no hagas clic en ningún enlace y comunicate con soporte.
                        """.formatted(nombreDenunciante, enlace)
        );
    }

    public void enviarDenunciaRechazadaDenunciado(String email, String nombreDenunciado, String enlace) {
        enviarEmail(
                email, "Resolución de denuncia asociada a tu cuenta",
                """
                        Hola %s:
                        Te informamos que la denuncia que había sido registrada en relación a tu cuenta ha sido desestimada.
                        
                        No es necesario que realices ninguna acción.
                        Si querés saber qué nos llevó a tomar esta decisión, ingresá al siguiente enlace para verlo:
                        %s
                        
                        Saludos,
                        El equipo de ¿A Quién Llamo?
                        
                        AVISO DE SEGURIDAD IMPORTANTE
                        Recordá que desde ¿A Quién Llamo? JAMÁS te vamos a solicitar contraseñas, datos de tarjetas de crédito o códigos por este medio.
                        Para proteger tu cuenta, revisá siempre que el correo del cual recibís nuestros avisos provenga de una dirección oficial terminada en @aquienllamo.com. Si desconfiás de la legitimidad de un mensaje, no hagas clic en ningún enlace y comunicate con soporte.
                        """.formatted(nombreDenunciado, enlace)
        );
    }

    public void enviarAmonestacionCuenta(String email,String nombreAmonestado, String motivo){
        enviarEmail(
                email, "Tu cuenta ha sido suspendida",
                        """
                        Hola %s:
                        Te informamos que tu cuenta en ¿A Quién Llamo? fue suspendida por el siguiente motivo:
                        > %s
                        Por esta razón, no podrás acceder al sitio por un mes.
                        
                        Si considerás que esta medida es un error, por favor contactá de manera inmediata al soporte.
                        Saludos,
                        El equipo de ¿A Quién Llamo?
                        
                        AVISO DE SEGURIDAD IMPORTANTE
                        Recordá que desde ¿A Quién Llamo? JAMÁS te vamos a solicitar contraseñas, datos de tarjetas de crédito o códigos por este medio.
                        Para proteger tu cuenta, revisá siempre que el correo del cual recibís nuestros avisos provenga de una dirección oficial terminada en @aquienllamo.com. Si desconfiás de la legitimidad de un mensaje, no hagas clic en ningún enlace y comunicate con soporte.
                        """.formatted(nombreAmonestado, motivo)
        );
    }

    public void enviarSeCerroLaCuenta(String email, String nombreCuenta, String motivo){
        enviarEmail(email, "Tu cuenta ha sido dada de baja",
                """
                Hola %s:
                
                Te informamos que tu cuenta en ¿A Quién Llamo? ha sido dada de baja.
                La razón por la cual hemos tomado esta medida es la siguiente:
                %s
                Recordá que el tiempo por el cual tu cuenta permanecerá dada de baja es indefinido.
                
                Si considerás que se trata de un error, comunícate con nuestro soporte.
                Saludos,
                El equipo de ¿A Quién Llamo?
                
                AVISO DE SEGURIDAD IMPORTANTE
                Recordá que desde ¿A Quién Llamo? JAMÁS te vamos a solicitar contraseñas, datos de tarjetas de crédito o códigos por este medio.
                Para proteger tu cuenta, revisá siempre que el correo del cual recibís nuestros avisos provenga de una dirección oficial terminada en @aquienllamo.com. Si desconfiás de la legitimidad de un mensaje, no hagas clic en ningún enlace y comunicate con soporte.
                """
        );
    }

    public void enviarSeAsignoUnAdmin(String email, String nombreDenunciante, String enlace){
        enviarEmail(email, "Tu denuncia cambió de estado",
                        """
                        Hola %s:
                        
                        Hubo un cambio en el estado de tu denuncia. Se asignó un nuevo administrador para que se encargue del problema.
                        Si estás interesad@, podés seguir el avance del proceso haciendo clic en el siguiente enlace:
                        %s
                        
                        Saludos,
                        El equipo de ¿A Quién Llamo?
                        
                        AVISO DE SEGURIDAD IMPORTANTE
                        Recordá que desde ¿A Quién Llamo? JAMÁS te vamos a solicitar contraseñas, datos de tarjetas de crédito o códigos por este medio.
                        Para proteger tu cuenta, revisá siempre que el correo del cual recibís nuestros avisos provenga de una dirección oficial terminada en @aquienllamo.com. Si desconfiás de la legitimidad de un mensaje, no hagas clic en ningún enlace y comunicate con soporte.
                        """.formatted(nombreDenunciante, enlace)
        );
    }

    public void enviarSeAsignoUnAdmin2(String email, String nombreDenunciado, String enlace){
        enviarEmail(email, "La denuncia asociada a tu cuenta cambió de estado",
                        """
                        Hola %s:
                        Hubo un cambio de estado en la denuncia asociada a tu cuenta. Se asignó un nuevo administrador para que se encargue del problema.
                        Si estás interesad@, podés seguir el avance del proceso haciendo clic en el siguiente enlace:
                        %s
                        
                        Saludos,
                        El equipo de ¿A Quién Llamo?
                        
                        AVISO DE SEGURIDAD IMPORTANTE
                        Recordá que desde ¿A Quién Llamo? JAMÁS te vamos a solicitar contraseñas, datos de tarjetas de crédito o códigos por este medio.
                        Para proteger tu cuenta, revisá siempre que el correo del cual recibís nuestros avisos provenga de una dirección oficial terminada en @aquienllamo.com. Si desconfiás de la legitimidad de un mensaje, no hagas clic en ningún enlace y comunicate con soporte.
                        """.formatted(nombreDenunciado, enlace)
        );
    }

}
