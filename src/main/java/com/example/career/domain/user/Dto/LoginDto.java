package com.example.career.domain.user.Dto;

import lombok.*;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotNull
//    @Size(min = 3, max = 50)
    private String username;

    @NotNull
//    @Size(min = 3, max = 100)
    private String password;
}