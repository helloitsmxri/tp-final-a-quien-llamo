package com.aquienllamo.aquienllamo.model.auth.securityDtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDTORequest {
    private String token;
    private String newPassword;
}
