package com.aquienllamo.aquienllamo.model.auth.securityDtos;

import lombok.*;


public record AuthResponse (String accessToken, String refreshToken){
}
