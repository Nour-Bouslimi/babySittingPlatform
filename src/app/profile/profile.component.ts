import {Component, OnInit} from '@angular/core';
import Swal from "sweetalert2";
import {UserService} from "../services/user.service";
import {Router} from "@angular/router";
import {AuthService} from "../services/auth.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Notification} from "../models/notification";
import {NotificationService} from "../services/notification.service";

declare var $: any;
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  profileForm!: FormGroup;
  //pour les notifs
  notifications: Notification[] = [];
  unreadCount: number = 0;
  userId!: number;
  constructor( private userService: UserService,
               private router: Router,private fb: FormBuilder, private authService: AuthService,private notificationService:NotificationService) {}


// Méthode pour charger les notifications de l'utilisateur connecté
  loadNotifications(): void {
    this.notificationService.getNotificationsByIdUser(this.userId).subscribe({
      next: (res) => {
        this.notifications = res.sort((a, b) => (b.date! > a.date! ? 1 : -1));
        console.log('notifications: ' ,this.notifications);
        this.unreadCount = this.notifications.filter(n => !n.isRead).length;
      },
      error: (err) => console.error("❌ Failed to load notifications", err)
    });
  }

  markAllAsRead(): void {
    this.notificationService.markAllNotificationsAsRead(this.userId).subscribe({
      next: () => {
        this.notifications.forEach(n => n.isRead = true);
        this.unreadCount = 0;
      },
      error: (err) => console.error("❌ Failed to mark as read", err)
    });
  }
  deleteNotification(id: number): void {
    this.notificationService.deleteNotification(id).subscribe({
      next: () => {
        this.notifications = this.notifications.filter(n => n.idNotif !== id);
      },
      error: (err) => console.error("❌ Failed to delete notification", err)
    });
  }
  ngOnInit(): void {
    const user = this.authService.decodedToken(); // récupérer les infos du token (user connecté)
console.log("User from token:", user);
//load notif
    this.loadNotifications();
    this.profileForm = this.fb.group({
      address: [user?.address || ''],
      email: [user?.sub || ''], // ou user?.email
      phoneNumber: [user?.phoneNumber || ''],
      genre: [user?.genre || ''],
      ageChildren: [user?.ageChildren || ''],
      nbChildren: [user?.nbChildren || 0],
      dateOfBirth: [user?.dateOfBirth || ''],
      firstName: [user?.firstName || ''],
      lastName: [user?.lastName || ''],
      password: [''] // jamais pré-rempli pour des raisons de sécurité
    });
  }

  //modifier le profil de l'utilisateur connecté
  onSubmit(): void {
    console.log(this.profileForm.value);
    // ici tu peux envoyer les données pour les modifier si tu veux
  }






  // Confirmation de suppression de compte pour l'utilisateur connecté
  confirmDelete() {
    console.log("Confirm delete called");
    Swal.fire({
      title: 'Are you sure?',
      text: "This action cannot be undone!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Delete',
      cancelButtonText: 'Cancel'
    }).then((result) => {
      if (result.isConfirmed) {
        const idUserStr = sessionStorage.getItem('idUser');
        if (idUserStr) {
          const idUser = Number(idUserStr);
          this.userService.deleteUser(idUser).subscribe({
            next: () => {
              this.deleteAccount();
            },
            error: (err) => {
              Swal.fire('Error', 'Account deletion failed.', 'error');
              console.error(err);
            }
          });
        } else {
          Swal.fire('Error', 'User not found.', 'error');
        }
      }
    });
  }

  deleteAccount() {
    // Your account deletion logic here
    console.log("Account deleted");
    Swal.fire('Deleted!', 'Your account has been deleted.', 'success');
  }

  confirmLogout() {
    Swal.fire({
      title: 'Logout',
      text: "Do you really want to logout?",
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Logout',
      cancelButtonText: 'Cancel'
    }).then((result) => {
      if (result.isConfirmed) {
        this.logout();
      }
    });
  }

  logout() {
    sessionStorage.clear();
    window.location.href = '/home';
    console.log("User logged out");
  }




  /*confirmDelete(){
    $('#deleteModal').modal('show');

  }

  closeDelete(){
    $('#deleteModal').modal('hide');
  }
  deleteAccount(){
    // Logic to delete the account goes here
    console.log("Account deleted");
    this.closeDelete();
  }

  confirmLogout(){
    $('#logoutModal').modal('show');
  }
  closeLogout(){
    $('#logoutModal').modal('hide');
  }
  logout(){
    // Logic to log out the user goes here
    //redirect to home page and clear session
    //clear session
    sessionStorage.clear();
    //redirect to home page
    window.location.href = '/home';
    console.log("User logged out");
    this.closeLogout();
  }*/

}
