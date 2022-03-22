package com.example.authentication.user;

import com.example.authentication.credential.KeycloakAccessToken;
import com.example.authentication.dto.KeyCloakUserDto;
import com.example.authentication.dto.KeyCloakUserInfoDto;
import com.example.authentication.retrofit.RetrofitServiceGenerator;
import com.example.authentication.retrofit.RetrofitUserService;
import com.example.authentication.util.KeycloakConstant;
import com.example.authentication.util.Peggable;
import com.example.authentication.util.Peggy;
import com.example.authentication.util.Specifearcation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Log4j2
public class KeycloakServiceImpl implements KeycloakService{

    public static final String LOGIN_FORM_CLIENT_ID_KEY  = "client_id";
    public static final String LOGIN_FORM_USERNAME_KEY   = "username";
    public static final String LOGIN_FORM_PASSWORD_KEY   = "password";
    public static final String LOGIN_FORM_GRANT_TYPE_KEY = "grant_type";
    private static String adminToken;


    @Override
    public KeycloakAccessToken login(String username, String password) throws IOException {
        Map<String,String> params = new HashMap<>();
        params.put(LOGIN_FORM_CLIENT_ID_KEY, KeycloakConstant.KEYCLOAK_CLIENT_ID);
        params.put(LOGIN_FORM_USERNAME_KEY,username);
        params.put(LOGIN_FORM_PASSWORD_KEY,password);
        params.put(LOGIN_FORM_GRANT_TYPE_KEY,KeycloakConstant.KEYCLOAK_CREDENTIAL_GRANT_TYPE);
        RetrofitUserService service = RetrofitServiceGenerator.createService(RetrofitUserService.class);
        Response<KeycloakAccessToken> response = service.login(params).execute();
        KeycloakAccessToken accessToken = response.body();
        adminToken = accessToken.getAccess_token();
        log.info("Token: "+adminToken);

        if(response.isSuccessful()){
            return response.body();
        }
        throw new IOException();
    }

    @Override
    public void prepareAdminToken() throws IOException {
        log.info("Token: "+adminToken);
        if(adminToken != null && adminToken.length() > 0){
            return;
        }
        KeycloakAccessToken token = login(KeycloakConstant.KEYCLOAK_ADMIN_USERNAME,KeycloakConstant.KEYCLOAK_ADMIN_PASSWORD);
        if(token == null){
            throw new IOException();
        }
        adminToken = token.getAccess_token();
    }

    @Override
    public boolean save(KeycloakUser keycloakUser) throws IOException {
        prepareAdminToken();
        RetrofitUserService service =
                RetrofitServiceGenerator.createService(RetrofitUserService.class,adminToken);
        Response<Void> response =
                service.save(keycloakUser).execute();
        if(!response.isSuccessful()){
            if(response.code() == HttpStatus.UNAUTHORIZED.value() ||
                response.code() == HttpStatus.FORBIDDEN.value()
            ){
                adminToken = null;
            }
        }
        return true;
    }

    @Override
    public Peggy<KeycloakUser> findAll(Specifearcation specifearcation, Peggable pageable) throws IOException {
        prepareAdminToken();
        log.info("Token FindAll: "+adminToken);
        RetrofitUserService service =
                RetrofitServiceGenerator.createService(RetrofitUserService.class,adminToken);
        Response<List<KeycloakUser>> response =
                service.findAll().execute();
        if(!response.isSuccessful()){
            if(response.code() == HttpStatus.UNAUTHORIZED.value() || response.code() == HttpStatus.FORBIDDEN.value()){
                adminToken = null;
            }
            throw new IOException(response.message());
        }
        return Peggy.<KeycloakUser>builder().content(response.body()).limit(10).page(1).build();
    }

    @Override
    public Optional<KeycloakUser> findById(String id) throws IOException {
        prepareAdminToken();
        RetrofitUserService service =
                RetrofitServiceGenerator.createService(RetrofitUserService.class,adminToken);
        Response<KeycloakUser> response = service.findById(id).execute();
        if(!response.isSuccessful()){
            if(response.code() == HttpStatus.UNAUTHORIZED.value() || response.code() == HttpStatus.FORBIDDEN.value()){
                adminToken = null;
            }
            throw new IOException(response.message());
        }
        return Optional.ofNullable(response.body());
    }

    @Override
    public boolean update(String id, KeycloakUser updateKeycloakUser) throws IOException {
        prepareAdminToken();
        RetrofitUserService service =
                RetrofitServiceGenerator.createService(RetrofitUserService.class,adminToken);
        Response<Void> response =
                service.update(id,updateKeycloakUser).execute();
        if(!response.isSuccessful()){
            if(response.code() == HttpStatus.UNAUTHORIZED.value()
                || response.code() == HttpStatus.FORBIDDEN.value()
            ){
                adminToken = null;
            }
            throw new IOException(response.message());
        }
        return true;
    }

    @Override
    public boolean delete(String id) throws IOException {
        prepareAdminToken();
        RetrofitUserService service =
                RetrofitServiceGenerator.createService(RetrofitUserService.class,adminToken);
        Response<Void> response = service.delete(id).execute();
        if(!response.isSuccessful()){
            if(response.code() == HttpStatus.UNAUTHORIZED.value() || response.code() == HttpStatus.FORBIDDEN.value()){
                adminToken = null;
            }
            throw new IOException(response.message());
        }
        return true;
    }

    @Override
    public Optional<KeyCloakUserInfoDto> userInfo() throws IOException {
        RetrofitUserService service =
                RetrofitServiceGenerator.createService(RetrofitUserService.class,adminToken);
        Response<KeyCloakUserInfoDto> response = service.getUserInfo().execute();
        log.info(response);
        if(!response.isSuccessful()){
            if(response.code() == HttpStatus.UNAUTHORIZED.value()
                    || response.code() == HttpStatus.FORBIDDEN.value()
            ){
                adminToken = null;
            }
            throw new IOException(response.message());
        }
        return Optional.ofNullable(response.body());
    }
}
