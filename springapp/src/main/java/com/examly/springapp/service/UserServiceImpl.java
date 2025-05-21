package com.examly.springapp.service;
 
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
 
import com.examly.springapp.config.UserPrinciple;
import com.examly.springapp.exception.NoUserFoundException;
import com.examly.springapp.mapper.UserMapper;
import com.examly.springapp.model.LoginDTO;
import com.examly.springapp.model.RegisterDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;
 
import jakarta.validation.Valid;
@Service
public class UserServiceImpl implements UserService,UserDetailsService{
    // Logger for tracking the application's flow
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    // Constructor injection makes dependencies explicit and immutable.
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }
 
 
    //Dummy Service Methods
    public User createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        logger.info("Method createUser ended...");
        return userRepo.save(user);
    }
    //Dummy Service Methods
    public LoginDTO loginUser(User user) {
        User checkUser= userRepo.findByUsername(user.getUsername());
        logger.info("Method loginUser ended...");
        return new LoginDTO("token",checkUser.getUsername(), checkUser.getUserRole(), checkUser.getUserId());
    }
 
 
 
    public UserDetails loadUserByUsername(String username){
        logger.info("Method loadUserByUsername started...");

      User user = userRepo.findByUsername(username);
      if(user==null){
        logger.error("Error occures");
         throw new NoUserFoundException("User not found");
      }
      logger.info("Method loadUserByUsername ended...");
      return UserPrinciple.build(user);
    }
 
   
    public List<User> findAllUsers() {
        logger.info("Method findAllUsers started...");
       List<User> user = userRepo.findAll();
       if(user.isEmpty())
          throw new NoUserFoundException("No user found!!");
        logger.info("Method findAllUsers ended...");
        return user;
    }
 
    public LoginDTO loginUsers(User user) {
        logger.info("Method loginUsers started...");
        // Find user by username
        User checkUser= userRepo.findByUsername(user.getUsername());
        if(checkUser==null)
            throw new BadCredentialsException("User Not Found!!");
        if(!encoder.matches(user.getPassword(),checkUser.getPassword()))
            throw new BadCredentialsException("Password is Incorrect!!");
        logger.info("Method loginUsers ended...");
        return new LoginDTO("Token",checkUser.getUsername(), checkUser.getUserRole(), checkUser.getUserId());
 
    }
    public @Valid User addNewUser(RegisterDTO registerDTO) {
        logger.info("Method addNewUser started...");
        registerDTO.setPassword(encoder.encode(registerDTO.getPassword()));
        registerDTO.setUserRole("USER");
        User user = UserMapper.mapToUser(registerDTO);
        logger.info("Method addNewUser ended...");
        return userRepo.save(user);
    }
}
 
 