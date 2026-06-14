package com.aquienllamo.aquienllamo.model.auth.securityDtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordDTORequest {
    private String email;
}
