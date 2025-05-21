import { Component, OnInit } from '@angular/core';
import { Feedback } from 'src/app/models/feedback.model';
import { FeedbackService } from 'src/app/services/feedback.service';

@Component({
  selector: 'app-adminviewfeedback',
  templateUrl: './adminviewfeedback.component.html',
  styleUrls: ['./adminviewfeedback.component.css']
})
export class AdminviewfeedbackComponent implements OnInit {
  errorMessage: string = ''; // For handling errors
  feedbacks: Feedback[] = [];

  // Pagination Variables
  currentPage: number = 1;
  itemsPerPage: number = 4; // Show 4 items per page

  constructor(private readonly feedbackService: FeedbackService) {}

  ngOnInit(): void {
    this.loadFeedbacks();
  }

  private loadFeedbacks(): void {
    this.feedbackService.getAllFeedbacks().subscribe({
      next: (data) => this.handleFeedbackSuccess(data),
      error: (error) => this.handleFeedbackError(error)
    });
  }

  private handleFeedbackSuccess(data: Feedback[]): void {
    this.feedbacks = data;
    this.errorMessage = ''; // Clear any previous error message
  }

  private handleFeedbackError(error: any): void {
    if (this.isFeedbackNotFoundError(error)) {
      this.feedbacks = [];
      this.errorMessage = "No feedback found.";
    } else {
      this.errorMessage = "Error loading feedbacks.";
    }
  }

  private isFeedbackNotFoundError(error: any): boolean {
    return (
      error.status === 404 ||
      (error.error &&
        (error.error.data === "No feedback found." ||
         error.error.message === "Not Found"))
    );
  }

  // Calculate total number of pages
  getTotalPages(): number {
    return Math.ceil(this.feedbacks.length / this.itemsPerPage);
  }

  // Get feedbacks for the current page
  getPaginatedFeedbacks(): Feedback[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.feedbacks.slice(startIndex, endIndex);
  }

  // Navigate to previous page
  previousPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }

  // Navigate to next page
  nextPage(): void {
    if (this.currentPage < this.getTotalPages()) {
      this.currentPage++;
    }
  }

  // Determine the class for star colors based on rating
  getStarClass(index: number, rating: number): string {
    return index < rating ? `selected rating-${rating}` : '';
  }
}
