package com.examly.springapp.mapper;

import com.examly.springapp.model.RegisterDTO;
import com.examly.springapp.model.User;


public final class UserMapper {
   // Private constructor prevents instantiation of this utility class
   private UserMapper() {
    throw new AssertionError("UserMapper is a utility class and should not be instantiated");
}

// Map User to RegisterDTO
public static RegisterDTO mapToRegisterDTO(User user) {
    RegisterDTO registerDTO = new RegisterDTO();
    registerDTO.setUserId(user.getUserId());
    registerDTO.setEmail(user.getEmail());
    registerDTO.setPassword(user.getPassword());
    registerDTO.setMobileNumber(user.getMobileNumber());
    registerDTO.setUserRole(user.getUserRole());
    return registerDTO;
}

// Convert RegisterDTO to User
public static User mapToUser(RegisterDTO registerDTO) {
    User user = new User();
    user.setUserId(registerDTO.getUserId());
    user.setEmail(registerDTO.getEmail());
    user.setPassword(registerDTO.getPassword());
    user.setUsername(registerDTO.getUsername());
    user.setMobileNumber(registerDTO.getMobileNumber());
    user.setUserRole(registerDTO.getUserRole());
    return user;
}
}
