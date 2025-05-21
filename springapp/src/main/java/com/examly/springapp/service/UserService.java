package com.examly.springapp.service;

import java.util.List;


import org.springframework.security.core.userdetails.UserDetails;

import com.examly.springapp.model.LoginDTO;
import com.examly.springapp.model.User;

public interface UserService {
  public User createUser(User user);
  public UserDetails loadUserByUsername(String userName);
  public List<User> findAllUsers();
  public LoginDTO loginUser(User user);
}
