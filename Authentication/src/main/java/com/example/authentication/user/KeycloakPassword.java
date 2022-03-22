package com.example.authentication.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KeycloakPassword {
    private boolean temporary;
    private String type;
    private String value;
}
