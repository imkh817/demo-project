package com.example.demo.security.exception;

import com.example.demo.auth.enums.ErrorCode;
import lombok.Getter;

import static com.example.demo.auth.enums.ErrorCode.UNSUPPORTED_TOKEN;

@Getter
public class UnsupportedJwtException extends RuntimeException{
   public final ErrorCode errorCode;

   public UnsupportedJwtException() {
       super(UNSUPPORTED_TOKEN.getMessage());
       this.errorCode = UNSUPPORTED_TOKEN;
   }
}
