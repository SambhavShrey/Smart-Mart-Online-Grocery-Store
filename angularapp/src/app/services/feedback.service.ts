import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Api } from 'src/api';
import { Feedback } from '../models/feedback.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {

  apiUrl:string=Api.apiUrlUser
  constructor(private readonly http:HttpClient) { }
  createFeedback(feedback: Feedback): Observable<Feedback> {
    return this.http.post<Feedback>(`${this.apiUrl}/feedback/add-feedback`, feedback);
  }

  // Get all feedbacks
  getAllFeedbacks(): Observable<Feedback[]> {
    return this.http.get<Feedback[]>(`${this.apiUrl}/feedback`);
  }

  // Get feedbacks by user ID
  getFeedbackByUserId(userId: number): Observable<Feedback[]> {
    return this.http.get<Feedback[]>(`${this.apiUrl}/feedback/user/${userId}`);
  }

  // Get feedback by feedback ID
  getFeedbackById(feedbackId: number): Observable<Feedback> {
    return this.http.get<Feedback>(`${this.apiUrl}/feedback/${feedbackId}`);
  }

  // Delete feedback by ID
  deleteFeedback(feedbackId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/feedback/${feedbackId}`);
  }

  // Update feedback by ID
  updateFeedback(feedback: Feedback, feedbackId: number): Observable<Feedback> {
    return this.http.put<Feedback>(`${this.apiUrl}/feedback/${feedbackId}`, feedback);
  }

}

