package com.portalnoticias.auth.service;

import com.portalnoticias.auth.dto.LoginRequest;
import com.portalnoticias.auth.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

}