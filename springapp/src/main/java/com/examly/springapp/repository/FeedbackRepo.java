package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Feedback;

// Marks this interface as a Repository to interact with the database
@Repository
public interface FeedbackRepo extends JpaRepository<Feedback,Long>{
    //Custom query to fetch all feedbacks by userId
    @Query("select feedback from Feedback feedback where feedback.user.userId=:userId")
    List<Feedback> findByUserId(Long userId);
}
