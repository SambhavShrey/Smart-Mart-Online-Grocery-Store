package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.User;

@Repository
public interface UserRepo extends JpaRepository<User,Long>{
    //finds user by username
    public User findByUsername(String userName);
    //finds user by email
    public User findByEmail(String email);
    //finds by username or email
    public User findByEmailOrUsername(String email, String username);

}
