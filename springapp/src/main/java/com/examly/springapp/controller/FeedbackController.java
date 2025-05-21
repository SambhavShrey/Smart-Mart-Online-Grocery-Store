package com.examly.springapp.controller;
 
import java.util.List;
 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.examly.springapp.dto.FeedbackDTO;
import com.examly.springapp.model.Feedback;
import com.examly.springapp.service.FeedbackServiceImpl;
 
import jakarta.validation.Valid;

// Defines a REST API controller that handles feedback-related HTTP requests.
@RestController
// Specifies the base URL for all API endpoints in this controller.
@RequestMapping("/api/feedback")
public class FeedbackController {

    // The service layer that contains the business logic for feedback operations.
    private final FeedbackServiceImpl service;

    // Constructor to initialize the feedback service using dependency injection.
    public FeedbackController(FeedbackServiceImpl service) {
        this.service = service;
    }

    /**
     * Handles POST requests to create new feedback.
     * Takes a FeedbackDTO object from the request body, validates it,
     * and calls the service layer to add the feedback.
     * Returns the newly created feedback with a 201 status.
     */
    @PostMapping("/add-feedback")
    public ResponseEntity<FeedbackDTO> addFeedback(@Valid @RequestBody FeedbackDTO feedbackDTO) {
        feedbackDTO = service.addFeedback(feedbackDTO);
        return ResponseEntity.status(201).body(feedbackDTO);
    }

    /**
     * Handles GET requests to retrieve all feedback entries.
     * Calls the service layer to fetch the list of feedback.
     * Returns the feedback list with a 200 status.
     */
    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        List<Feedback> list = service.getAllFeedback();
        return ResponseEntity.status(200).body(list);
    }

    /**
     * Handles GET requests to retrieve feedback submitted by a specific user.
     * The user ID is provided as a path variable.
     * Calls the service layer to fetch feedback entries for the user.
     * Returns the feedback list with a 200 status.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Feedback>> getFeedbackByUserId(@PathVariable Long userId) {
        List<Feedback> list = service.getFeedbackByUserId(userId);
        return ResponseEntity.status(200).body(list);
    }

    /**
     * Handles GET requests to retrieve feedback by its unique feedback ID.
     * The feedback ID is provided as a path variable.
     * Calls the service layer to fetch the specific feedback entry.
     * Returns the feedback with a 200 status.
     */
    @GetMapping("/{feedbackId}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long feedbackId) {
        Feedback feedback = service.getFeedbackById(feedbackId);
        return ResponseEntity.status(200).body(feedback);
    }

    /**
     * Handles DELETE requests to delete a feedback entry by its ID.
     * The feedback ID is provided as a path variable.
     * Calls the service layer to delete the specific feedback entry.
     * Returns a success message with a 200 status if the deletion is successful,
     * otherwise returns an error message with a 404 status.
     */
    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<String> deleteFeedback(@PathVariable Long feedbackId) {
        boolean result = service.deleteFeedback(feedbackId);
        if (result)
            return ResponseEntity.status(200).body(null);
        else
            return ResponseEntity.status(404).body("Not Deleted");
    }

    /**
     * A dummy endpoint for testing feedback creation.
     * Takes a Feedback object from the request body and saves it using the service layer.
     * Returns the saved feedback with a 201 status.
     */
    @PostMapping
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) {
        return ResponseEntity.status(201).body(service.createFeedback(feedback));
    }
}
