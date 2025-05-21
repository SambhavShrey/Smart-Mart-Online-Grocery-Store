package com.examly.springapp.mapper;

import com.examly.springapp.dto.FeedbackDTO;
import com.examly.springapp.model.Feedback;
import com.examly.springapp.model.User;

public class FeedbackMapper {
    FeedbackMapper() {}
  //  This is a method to convert data from FeedbackDTO (used in the API) to Feedback (used for database operations).
    public static Feedback mapToFeedbackEntity(FeedbackDTO feedbackDTO) {
        Feedback feedback = new Feedback();
        feedback.setMessage(feedbackDTO.getMessage());
        feedback.setRating(feedbackDTO.getRating());
        // Map userId to User entity
      //  Checks if the User object in Feedback is not null.
        if (feedbackDTO.getUserId() != null) {
            User user = new User(); // Create a User entity and set its ID
            user.setUserId(feedbackDTO.getUserId());  //If the User object exists, extracts its userId and sets it in the FeedbackDTO.
            feedback.setUser(user);
        }
        return feedback;
    }

    // Maps Feedback to FeedbackDTO
    public static FeedbackDTO mapToFeedbackDTO(Feedback feedback) {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setFeedbackId(feedback.getFeedbackId());
        feedbackDTO.setMessage(feedback.getMessage());
        feedbackDTO.setRating(feedback.getRating());
        // Map User entity to userId
        if (feedback.getUser() != null) {
            feedbackDTO.setUserId(feedback.getUser().getUserId());
        }
        return feedbackDTO;
    }
}
