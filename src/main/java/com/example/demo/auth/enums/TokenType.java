package com.example.demo.auth.enums;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public enum TokenType {
    ACCESS,
    REFRESH;
}
