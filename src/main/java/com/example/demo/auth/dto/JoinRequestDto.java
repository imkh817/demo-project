package com.example.demo.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class JoinRequestDto {

    @NotBlank(message = "아이디는 필수 값입니다.")
    private String userId;
    @Size(min = 8 , message = "비밀번호는 8자리 이상이여야 합니다.")
    private String password;
    @NotBlank(message = "이름은 필수 값입니다.")
    private String name;
    private LocalDateTime birthDate;


}
