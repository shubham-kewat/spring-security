package com.kewat.security.response;

import lombok.Builder;
import lombok.Getter;

@Builder
public record LoginResponse(String token,String refreshToken) {

}
