package com.examly.springapp.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.config.JwtUtils;
import com.examly.springapp.model.LoginDTO;
import com.examly.springapp.model.RegisterDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.service.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserServiceImpl service;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtlis;
   
    // Constructor injection makes dependencies explicit and final.
    public UserController(UserServiceImpl service,AuthenticationManager authenticationManager,JwtUtils jwtUtlis) {
        this.service = service;
        this.authenticationManager=authenticationManager;
        this.jwtUtlis=jwtUtlis;
    }
    //Dummy Api
   @PostMapping("/register")
   public ResponseEntity<String> addUser(@RequestBody User user){
       service.createUser(user);
       return ResponseEntity.status(201).body("registered");
    }
    @PostMapping("/login")
    public ResponseEntity<LoginDTO> loginUser(@RequestBody User user){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtlis.genrateToken(authentication);
        LoginDTO login = service.loginUsers(user);
        login.setToken(token);
        return ResponseEntity.status(200).body(login);
    }
 
    //Real Api
    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> user = service.findAllUsers();
        return ResponseEntity.status(200).body(user);
    }


 //Real API
 @PostMapping("/registers")
   public ResponseEntity<User> addNewUser(@Valid @RequestBody RegisterDTO registerUser){
       User user = service.addNewUser(registerUser);
       return ResponseEntity.status(201).body(user);
    }
 @PostMapping("/logins")
    public ResponseEntity<LoginDTO> userLogins(@RequestBody User user){
        LoginDTO userLogin = service.loginUsers(user);
        return ResponseEntity.status(200).body(userLogin);
    }

}
