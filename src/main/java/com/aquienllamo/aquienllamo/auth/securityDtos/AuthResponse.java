package com.aquienllamo.aquienllamo.auth.securityDtos;

import lombok.*;


public record AuthResponse (String accessToken, String refreshToken){
}
