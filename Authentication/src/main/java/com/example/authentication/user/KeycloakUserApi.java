package com.example.authentication.user;

import com.example.authentication.credential.KeycloakAccessToken;
import com.example.authentication.dto.KeyCloakUserDto;
import com.example.authentication.util.Peggy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/users")
public class KeycloakUserApi {

    @Autowired
    KeycloakService keycloakService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Peggy<KeycloakUser>> index() throws IOException{
        return new ResponseEntity<Peggy<KeycloakUser>>(
                keycloakService.findAll(null,null),
                HttpStatus.OK
        );
    }

    @RequestMapping(method = RequestMethod.GET,path = "login")
    public ResponseEntity<KeycloakAccessToken> login(@RequestBody KeyCloakUserDto keyCloakUserDto) throws  IOException{
        return new ResponseEntity<>(keycloakService.login(keyCloakUserDto.getUsername(), keyCloakUserDto.getPassword()),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<KeycloakUser> register(@RequestBody KeycloakUser keycloakUser) throws IOException {
        if (keycloakService.save(keycloakUser)) {
            return new ResponseEntity<KeycloakUser>(keycloakUser, HttpStatus.CREATED);
        } else {
            throw new IOException();
        }
    }

    @RequestMapping(method = RequestMethod.GET,path = "detail")
    public ResponseEntity<KeycloakUser> detail(
            @RequestParam(name = "id") String id) throws IOException{
        Optional<KeycloakUser> keycloakUser = keycloakService.findById(id);
        if(keycloakUser.isPresent()){
            return new ResponseEntity<KeycloakUser>(keycloakUser.get(),HttpStatus.OK);
        }
        return new ResponseEntity<KeycloakUser>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET,path = "/info")
    public ResponseEntity<Object> getInfo() throws IOException {
        return  new ResponseEntity<Object>(keycloakService.userInfo(),HttpStatus.OK);
    }
}
