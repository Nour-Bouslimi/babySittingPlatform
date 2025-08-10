import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "./auth.service";
import {Notification} from "../models/notification";


@Injectable({
  providedIn: 'root'
})

export  class NotificationService{
  constructor(private http:HttpClient, private authService: AuthService) {}
  baseUrl = 'http://localhost:8081/notifications';

  // Create a new notification
  createNotification(notification: Notification) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.post<Notification>(`${this.baseUrl}/addNotification`, notification, { headers });
  }

  // Delete a notification by ID
  deleteNotification(id: number) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.delete<void>(`${this.baseUrl}/deleteNotification/${id}`, { headers });
  }
  //delete all notifications for a user
  deleteAllNotificationsByUserId(id: number) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.delete<void>(`${this.baseUrl}/deleteAllNotifications/${id}`, { headers });
  }

  //get all notifications
  getAllNotifications() {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.get<Notification[]>(`${this.baseUrl}/getAllNotifications`, { headers });
  }
  //get notifications by id
  getNotificationById(id: number) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.get<Notification>(`${this.baseUrl}/getNotificationById/${id}`, { headers });
  }

  //get notifications by user id
  getNotificationsByIdUser(idUser: number) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.get<Notification[]>(`${this.baseUrl}/getNotificationsByIdUser/${idUser}`, { headers });
  }
  //get unread notifications by id user

  getUnreadNotificationsByIdUser(idUser: number) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.get<Notification[]>(`${this.baseUrl}/getUnreadNotificationsByIdUser/${idUser}`, { headers });
  }
  //mark notification as read
  markNotificationAsRead(id: number) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.put<void>(`${this.baseUrl}/markNotificationAsRead/${id}`, {}, { headers });
  }

  //mark all notifications as read for a user
  markAllNotificationsAsRead(idUser: number) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.put<void>(`${this.baseUrl}/markAllNotificationsAsRead/${idUser}`, {}, { headers });
  }

  //get notifications by date
  getNotificationsByDate(date: string) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.get<Notification[]>(`${this.baseUrl}/getNotificationsByDate/${date}`, { headers });
  }









}
