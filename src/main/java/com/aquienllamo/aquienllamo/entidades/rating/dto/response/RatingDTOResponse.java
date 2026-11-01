package com.aquienllamo.aquienllamo.entidades.rating.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RatingDTOResponse {
    private String uuidRemitente; //para que el front pueda ir al perfil de quien escribió la reseña
    private String nombreRemitente; //el nombre del comentario
    private String nombreDestinatario; //el nombre del que recibe el comentario
    private Integer valoracion;
    private String descripcion;
    private String tipoFoto;
    private byte[] foto;
    private List<String> caracteristicas;

}
