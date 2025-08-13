import {AfterViewInit, Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthentificationRequest} from "../models/AuthentificationRequest";
import {AuthenticationResponse} from "../models/authentication-response";
import {AuthService} from "../services/auth.service";
import {UserRole} from "../models/UserRole";
import {User} from "../models/user";
import {UserService} from "../services/user.service";
declare var bootstrap: any; // Import Bootstrap JS
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, AfterViewInit {
  authForm: FormGroup;
  userData:User |undefined;
  authRequest:AuthentificationRequest={email: '',
    password: ''};
  authResponse: AuthenticationResponse = { accessToken: '',tokenType: '' };

  constructor(private authService: AuthService, private router: Router, private formBuilder: FormBuilder,private userService: UserService) {
    // Initialiser le formulaire de connexion
    this.authForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.pattern('^(?=.*[A-Za-z])(?=.*\\d).{8,}$')]]
    });
  }

  error: string = '';
  message: string = '';

  // Méthode pour soumettre le formulaire de connexion
  authenticate() {
    this.authRequest.email = this.authForm.get('email')?.value;
    this.authRequest.password = this.authForm.get('password')?.value;

    this.authService.login(this.authRequest).subscribe({
      next: (response) => {
        this.authResponse = response;
        const token = response.accessToken; // Corrigez ici
        if (!token || typeof token !== 'string' || !token.includes('.')) {
          console.error('Invalid token format:', token);
          this.error = 'Invalid token received';
          return;
        }
        localStorage.setItem('token', token);
        this.error = '';
        this.message = 'Login successful';

        const tokenPayload = this.authService.decodedToken();
        console.log('Decoded token payload:', tokenPayload);
        const roles = tokenPayload?.roles || []; // Gestion si roles est absent
        const role = Array.isArray(roles) ? roles[0] : roles;
        console.log('User role:', role);

        if (role === 'ROLE_ADMIN' || role === 'ADMIN') { // Ajustez pour "ROLE_ADMIN"
          this.router.navigate(['dashboard']);
          //fermer le modal login
          const modal = document.getElementById('loginModal');
          if (modal) {
            const bsModal = bootstrap.Modal.getInstance(modal) || new bootstrap.Modal(modal);
            bsModal.hide();
          }
        } else if (role === 'ROLE_PARENT' || role === 'PARENT') {
          this.router.navigate(['home']);
          //fermer le modal login
          const modal = document.getElementById('loginModal');
          if (modal) {
            const bsModal = bootstrap.Modal.getInstance(modal) || new bootstrap.Modal(modal);
            bsModal.hide();
          }
        } else if (role === 'ROLE_NOUNOU' || role === 'NOUNOU') {
          this.router.navigate(['home']);
          this.loadProfileData(); // Charger les données pour NOUNOU
          //fermer le modal login
          const modal = document.getElementById('loginModal');
          if (modal) {
            const bsModal = bootstrap.Modal.getInstance(modal) || new bootstrap.Modal(modal);
            bsModal.hide();
          }

        } else {
          this.error = 'Rôle utilisateur non reconnu';
          localStorage.removeItem('token');
        }
      },
      error: (error) => {
        console.error('Erreur de login :', error);
        if (error.status === 404) {
          this.error = error.error;
          this.message = '';
        } else if (error.status === 403) {
          if (error.error === 'User disabled and token expired') {
            this.error = 'User disabled and token expired';
            this.message = '';
          } else if (error.error === 'User disabled') {
            this.error = 'User disabled';
            this.message = '';
          } else {
            this.error = 'Access denied';
            this.message = '';
          }
        } else {
          this.error = 'Bad credentials';
          this.message = '';
        }
      }
    });
  }

//donnée de profil nounou
  loadProfileData() {
    const userId = this.authService.decodedToken()?.user?.id;
    if (!userId) {
      console.error('No user ID found in token');
      return;
    }
    this.userService.getUserById(userId).subscribe({
      next: (userData) => {
        console.log('User profile data:', userData);
        this.userData = userData;
        this.populateForm(userData);
        const modal = new (window as any).bootstrap.Modal(document.getElementById('profileNannyModal'));
        modal.show();
      },
      error: (error) => {
        console.error('Error loading profile data:', error);
      }
    });
  }

  //mettre les données de l'utilisateur dans le formulaire
  populateForm(userData: User) {
    const form = document.getElementById('multiStepForm') as HTMLFormElement;
    if (form) {
      form.querySelector('input[name="lastname"]')?.setAttribute('value', userData.lastName || '');
      form.querySelector('input[name="firstname"]')?.setAttribute('value', userData.firstName || '');
      form.querySelector('input[name="address"]')?.setAttribute('value', userData.address || '');
      form.querySelector('input[name="phone"]')?.setAttribute('value', userData.phoneNumber || '');
      form.querySelector(`input[name="gender"][value="${userData.genre}"]`)?.setAttribute('checked', 'true');
      form.querySelector('input[name="email"]')?.setAttribute('value', userData.email || '');
      form.querySelector('input[name="password"]')?.setAttribute('value', ''); // Sécurité
      form.querySelector('input[name="birthdate"]')?.setAttribute('value', userData.dateOfBirth ? new Date(userData.dateOfBirth).toISOString().split('T')[0] : '');
      form.querySelector(`input[name="maritalStatus"][value="${userData.etatCivil}"]`)?.setAttribute('checked', 'true');
      form.querySelector(`select[name="educationLevel"]`)?.setAttribute('value', userData.niveauEtude || '');
      form.querySelector('input[name="fieldOfStudy"]')?.setAttribute('value', userData.domaineEtude || '');
      form.querySelector(`select[name="language"]`)?.setAttribute('value', userData.langue || '');
      form.querySelector(`select[name="languageLevel"]`)?.setAttribute('value', userData.niveau || '');
      form.querySelector('input[name="interests"]')?.setAttribute('value', userData.centreInteret || '');
      form.querySelector(`input[name="motorized"][value="${userData.motorise}"]`)?.setAttribute('checked', 'true');
      form.querySelector(`input[name="smoker"][value="${userData.fumer}"]`)?.setAttribute('checked', 'true');
      form.querySelector('input[name="hourlyRate"]')?.setAttribute('value', userData.tarifHoraire.toString() || '');
      // Gérer les images si elles sont incluses dans userData
    }
  }

  ngOnInit() {
    setTimeout(() => {
      const modalElement = document.getElementById('loginModal');
      if (modalElement) {
        const modal = new bootstrap.Modal(modalElement);
        modal.show();
      }
    }, 0);
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
    this.router.navigate(['/forgotPass']);
  }


}
