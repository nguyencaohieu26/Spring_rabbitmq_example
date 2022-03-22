package com.example.authentication.dto;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class KeyCloakUserInfoDto {
    private String sub;
    private String name;
    private String email;
    private String preferred_username;
}
