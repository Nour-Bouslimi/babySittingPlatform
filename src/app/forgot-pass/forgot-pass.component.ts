import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "../services/user.service";
import Swal from "sweetalert2";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
declare var bootstrap: any; // Import Bootstrap JS
@Component({
  selector: 'app-forgot-pass',
  templateUrl: './forgot-pass.component.html',
  styleUrls: ['./forgot-pass.component.css']
})
export class ForgotPassComponent implements OnInit{

  email:string='';
  forgotForm!: FormGroup;
  message: string = '';
  error: string = '';
  constructor(private router: Router,private userService:UserService,private  fb:FormBuilder) {}

  ngOnInit(): void {
    this.forgotForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  goToForgotPass() {
    // Fermer le modal login
    const modal = document.getElementById('loginModal');
    if (modal) {
      const bsModal = bootstrap.Modal.getInstance(modal) || new bootstrap.Modal(modal);
      bsModal.hide();
    }
    // Naviguer vers la page forgot password
    this.router.navigate(['/forgotPass']);
  }


  //forgot pass
  onSubmit() {
    if (this.forgotForm.invalid) {
      this.forgotForm.markAllAsTouched();
      return;
    }

    const email = this.forgotForm.value.email;

    this.userService.sendNewPassword(email).subscribe({
      next: (response) => {
        this.message = response.body ?? 'Temporary password sent successfully!';
        this.error = '';
        // After 3 seconds, redirect to the login page
        setTimeout(() => {
          this.router.navigate(['login']);
        }, 3000);
      },
      error: (err) => {
        if (err.status === 404) {
          this.error = 'User not found';
        } else {
          this.error = 'Something went wrong';
        }
        this.message = '';
      }
    });
  }

}
