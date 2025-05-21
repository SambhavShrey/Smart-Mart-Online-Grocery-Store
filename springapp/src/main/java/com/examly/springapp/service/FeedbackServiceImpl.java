package com.examly.springapp.service;
 
import java.util.List;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
 
import com.examly.springapp.dto.FeedbackDTO;
import com.examly.springapp.exception.NotFoundException;
import com.examly.springapp.exception.ActionFailedException;
import com.examly.springapp.exception.BadRequestException;
import com.examly.springapp.mapper.FeedbackMapper;
import com.examly.springapp.model.Feedback;
import com.examly.springapp.repository.FeedbackRepo;

// This class defines the business logic for feedback operations.
// It handles tasks like adding feedback, fetching feedback, and deleting feedback.
@Service
public class FeedbackServiceImpl implements FeedbackService{
 
    // Used to log messages and track application flow for debugging purposes.
    Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);
 
    // A repository interface that interacts with the database for feedback-related operations.
    private final FeedbackRepo feedbackRepo;
 
    // Constructor to initialize the service class with the feedback repository dependency.
    public FeedbackServiceImpl(FeedbackRepo feedbackRepo) {
        this.feedbackRepo = feedbackRepo;
    }
 
    /**
     * Adds a new feedback entry into the database.
     * Ensures that feedback data is valid, maps it to a database entity, and saves it.
     */
    public FeedbackDTO addFeedback(FeedbackDTO feedbackDTO) {
        logger.info("Method createFeedback started...");
        
        // Validate feedback data and ensure the message field is not null or empty.
        if (feedbackDTO == null || feedbackDTO.getMessage() == null || feedbackDTO.getMessage().isEmpty()) {
            throw new BadRequestException("Invalid feedback data. Message cannot be null or empty.");
        }
        
        // Convert feedback data from DTO to entity format for database storage.
        Feedback feedback = FeedbackMapper.mapToFeedbackEntity(feedbackDTO);

        try {
            // Save the feedback entity in the database.
            Feedback saved = feedbackRepo.save(feedback);
            logger.info("Method createFeedback ended...");
            
            // Return the saved feedback entity in DTO format.
            return FeedbackMapper.mapToFeedbackDTO(saved);
        } catch (Exception e) {
            // Handle errors that occur during saving to the database.
            throw new ActionFailedException("Failed to save feedback to the database.");
        }
    }

    /**
     * Fetches all feedback entries from the database.
     * Throws an exception if no feedback is found.
     */
    public List<Feedback> getAllFeedback() {
        logger.info("Method getAllFeedback started...");
        
        // Retrieve all feedback entries from the database.
        List<Feedback> feedbackList = feedbackRepo.findAll();

        // Check if the feedback list is empty and throw an exception if necessary.
        if (feedbackList.isEmpty()) {
            throw new NotFoundException("No feedback found.");
        }

        return feedbackList;
    }

    /**
     * Fetches feedback submitted by a specific user.
     * Validates the user ID and retrieves feedback entries related to that user.
     */
    public List<Feedback> getFeedbackByUserId(Long userId) {
        logger.info("Method getFeedbackByUserId started...");

        // Validate the user ID to ensure it is not null or invalid.
        if (userId == null || userId <= 0) {
            throw new BadRequestException("Invalid user ID.");
        }

        // Fetch feedback entries for the given user ID from the database.
        List<Feedback> feedbackList = feedbackRepo.findByUserId(userId);

        // Throw an exception if no feedback is found for the user ID.
        if (feedbackList.isEmpty()) {
            throw new NotFoundException("No feedback found for the given user ID.");
        }

        return feedbackList;
    }

    /**
     * Fetches feedback details by its feedback ID.
     * Validates the feedback ID and retrieves the corresponding feedback from the database.
     */
    public Feedback getFeedbackById(Long feedbackId) {
        logger.info("Method getFeedbackById started...");

        // Validate the feedback ID to ensure it is valid.
        if (feedbackId == null || feedbackId <= 0) {
            throw new BadRequestException("Invalid feedback ID.");
        }

        // Fetch the feedback from the database based on the ID or throw an exception if not found.
        return feedbackRepo.findById(feedbackId)
                .orElseThrow(() -> new NotFoundException("Feedback not found for the given ID."));
    }

    /**
     * Deletes a feedback entry by its feedback ID.
     * Validates the ID and ensures the feedback exists before deletion.
     */
    public boolean deleteFeedback(Long feedbackId) {
        logger.info("Method deleteFeedback started...");

        // Validate the feedback ID to ensure it is not null or invalid.
        if (feedbackId == null || feedbackId <= 0) {
            throw new BadRequestException("Invalid feedback ID.");
        }

        // Retrieve the feedback from the database or throw an exception if it doesn't exist.
        Feedback feedback = feedbackRepo.findById(feedbackId)
                .orElseThrow(() -> new NotFoundException("Feedback not found for the given ID."));

        try {
            // Delete the feedback from the database.
            feedbackRepo.delete(feedback);
            logger.info("Method deleteFeedback ended...");
            return true;
        } catch (Exception e) {
            // Handle errors that occur during deletion.
            throw new ActionFailedException("Failed to delete feedback from the database.");
        }
    }

    /**
     * Dummy method to create and save feedback entries using the repository.
     */
    public Feedback createFeedback(Feedback feedback) {
        return feedbackRepo.save(feedback);
    }
}
