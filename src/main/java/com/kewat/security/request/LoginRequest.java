package com.kewat.security.request;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginRequest {
    String username;
    String password;
}
