import {AfterViewInit, Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
declare var bootstrap: any; // Import Bootstrap JS
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, AfterViewInit {
  constructor(private router: Router) {}
  ngOnInit() {
    setTimeout(()=>{
      const modalElement = document.getElementById('loginModal');
      if(modalElement) {
        const modal = new bootstrap.Modal(modalElement);
        modal.show();
      }
    },0);
  }
  ngAfterViewInit() {
    // Afficher/masquer le mot de passe de login
    const passwordInputL = document.getElementById('passwordInputL') as HTMLInputElement;
    const togglePasswordL = document.getElementById('togglePasswordL');
    const eyeIcon = document.getElementById('eyeIcon');
    if (togglePasswordL && passwordInputL && eyeIcon) {
      togglePasswordL.addEventListener('click', () => {
        if (passwordInputL.type === 'password') {
          passwordInputL.type = 'text';
          eyeIcon.classList.remove('fa-eye-slash');
          eyeIcon.classList.add('fa-eye');
        } else {
          passwordInputL.type = 'password';
          eyeIcon.classList.remove('fa-eye');
          eyeIcon.classList.add('fa-eye-slash');
        }
      });
    }

  }

  goToForgotPass() {
    // Fermer le modal login
    const modal = document.getElementById('loginModal');
    if (modal) {
      const bsModal = bootstrap.Modal.getInstance(modal) || new bootstrap.Modal(modal);
      bsModal.hide();
    }
    // Naviguer vers la page forgot password
    this.router.navigate(['/home/forgotPass']);
  }

}
