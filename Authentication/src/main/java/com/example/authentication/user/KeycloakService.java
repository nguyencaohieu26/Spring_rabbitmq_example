package com.example.authentication.user;

import com.example.authentication.credential.KeycloakAccessToken;
import com.example.authentication.dto.KeyCloakUserInfoDto;
import com.example.authentication.util.Peggable;
import com.example.authentication.util.Peggy;
import com.example.authentication.util.Specifearcation;

import java.io.IOException;
import java.util.Optional;

public interface KeycloakService {
    KeycloakAccessToken login(String username,String password) throws IOException;
    void prepareAdminToken() throws  IOException;
    boolean save(KeycloakUser keycloakUser) throws IOException;
    Peggy<KeycloakUser> findAll(Specifearcation specifearcation, Peggable pageable) throws  IOException;
    Optional<KeycloakUser> findById(String id) throws IOException;
    boolean update(String id,KeycloakUser updateKeycloakUser) throws IOException;
    boolean delete(String id) throws IOException;
    Optional<KeyCloakUserInfoDto> userInfo() throws IOException;
}
