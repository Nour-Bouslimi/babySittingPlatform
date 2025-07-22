import { Component } from '@angular/core';
import Swal from "sweetalert2";
import {UserService} from "../services/user.service";
import {Router} from "@angular/router";

declare var $: any;
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  constructor( private userService: UserService,
               private router: Router) {}

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
