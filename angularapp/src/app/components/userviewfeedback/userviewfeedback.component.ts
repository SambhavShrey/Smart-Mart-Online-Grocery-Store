import { Component, OnInit } from '@angular/core';
import { Feedback } from 'src/app/models/feedback.model';
import { FeedbackService } from 'src/app/services/feedback.service';

@Component({
  selector: 'app-userviewfeedback',
  templateUrl: './userviewfeedback.component.html',
  styleUrls: ['./userviewfeedback.component.css']
})
export class UserviewfeedbackComponent implements OnInit {
  feedbacks: Feedback[] = [];
  userId: any;
  errorMessage: any;
  showDeleteModal: boolean = false;
  selectedFeedbackId!: number;
  stars = [1, 2, 3, 4, 5];
  feedbackForm: any;

  constructor(private readonly feedbackService: FeedbackService) {}

  ngOnInit(): void {
    this.userId = localStorage.getItem('userId');
    this.loadFeedbacks();
  }

  loadFeedbacks(): void {
    this.feedbackService.getFeedbackByUserId(this.userId).subscribe({
      next: (data) => {
        this.feedbacks = data;
      },
      error: () => {
        this.errorMessage = "Error loading feedbacks.";
      }
    });
  }

  openDeleteModal(feedbackId: number): void {
    this.selectedFeedbackId = feedbackId;
    this.showDeleteModal = true;
  }

  closeDeleteModal(): void {
    this.showDeleteModal = false;
    this.selectedFeedbackId = 0;
  }

  confirmDelete(): void {
    if (this.selectedFeedbackId) {
      this.feedbackService.deleteFeedback(this.selectedFeedbackId).subscribe({
        next: () => {
          this.loadFeedbacks();
          this.closeDeleteModal();
        },
        error: () => {
          this.errorMessage = "Error deleting feedback.";
          this.closeDeleteModal();
        }
      });
    }
  }

  isStarActive(index: number, rating: number): string {
    if (index < rating) {
      switch (rating) {
        case 1:
          return 'selected one';
        case 2:
          return 'selected two';
        case 3:
          return 'selected three';
        case 4:
          return 'selected four';
        case 5:
          return 'selected five';
        default:
          return 'selected';
      }
    }
    return '';
  }
}
