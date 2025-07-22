import {AfterViewInit, Component } from '@angular/core';
import Swal from "sweetalert2";
import {Router} from "@angular/router";
import {UserService} from "../services/user.service";
declare var $: any;
@Component({
  selector: 'app-profile-nounou',
  templateUrl: './profile-nounou.component.html',
  styleUrls: ['./profile-nounou.component.css']
})
export class ProfileNounouComponent implements AfterViewInit{

  constructor( private userService: UserService,
               private router: Router) {}
  profileData: any = {
    lastname: '',
    firstname: '',
    address: '',
    phone: '',
    gender: '',
    email: '',
    password: '',
    birthdate: '',
    maritalStatus: '',
    educationLevel: '',
    fieldOfStudy: '',
    diplomaImageBase64: '',
    language: '',
    otherLanguage: '',
    languageLevel: '',
    interests: '',
    motorized: '',
    smoker: '',
    idImage1Base64: '',
    idImage2Base64: '',
    photoBase64: '',
    hourlyRate: ''
  };

 /* ngAfterViewInit() {
    // Afficher/masquer le mot de passe
    const passwordInput = document.getElementById('passwordInputProfile') as HTMLInputElement;
    const togglePassword = document.getElementById('togglePasswordProfile');
    const eyeIcon = document.getElementById('eyeIconProfile');
    if (togglePassword && passwordInput && eyeIcon) {
      togglePassword.addEventListener('click', () => {
        if (passwordInput.type === 'password') {
          passwordInput.type = 'text';
          eyeIcon.classList.remove('fa-eye-slash');
          eyeIcon.classList.add('fa-eye');
        } else {
          passwordInput.type = 'password';
          eyeIcon.classList.remove('fa-eye');
          eyeIcon.classList.add('fa-eye-slash');
        }
      });
    }
  }

  nextStep(step: number) {
    document.querySelectorAll('.form-step').forEach(div => {
      div.classList.add('d-none');
    });
    const stepDiv = document.getElementById('step' + step);
    if (stepDiv) stepDiv.classList.remove('d-none');
  }

  toggleOtherLanguage(event: any) {
    const select = event.target;
    const otherInput = document.getElementById('otherLanguageInput') as HTMLInputElement;
    if (select.value === 'other') {
      otherInput.classList.remove('d-none');
      otherInput.required = true;
    } else {
      otherInput.classList.add('d-none');
      otherInput.required = false;
    }
  }

  previewAndBase64(event: any, previewId: string, hiddenId: string) {
    const file = event.target.files[0];
    const preview = document.getElementById(previewId) as HTMLImageElement;
    const hidden = document.getElementById(hiddenId) as HTMLInputElement;
    if (file) {
      const reader = new FileReader();
      reader.onload = (evt: any) => {
        preview.src = evt.target.result;
        preview.style.display = 'block';
        hidden.value = evt.target.result;
        this.profileData[hiddenId] = evt.target.result;
      };
      reader.readAsDataURL(file);
    } else {
      preview.src = '';
      preview.style.display = 'none';
      hidden.value = '';
      this.profileData[hiddenId] = '';
    }
  }

  saveProfile() {
    // Appel API pour sauvegarder le profil
    console.log(this.profileData);
  }*/
  ngAfterViewInit() {
    // Password show/hide for profile nounou
    const passwordInputP = document.getElementById('passwordInputP') as HTMLInputElement;
    const togglePasswordP = document.getElementById('togglePasswordP');
    const eyeIconP = document.getElementById('eyeIconP');
    if (togglePasswordP && passwordInputP && eyeIconP) {
      togglePasswordP.addEventListener('click', () => {
        if (passwordInputP.type === 'password') {
          passwordInputP.type = 'text';
          eyeIconP.classList.remove('fa-eye-slash');
          eyeIconP.classList.add('fa-eye');
        } else {
          passwordInputP.type = 'password';
          eyeIconP.classList.remove('fa-eye');
          eyeIconP.classList.add('fa-eye-slash');
        }
      });
    }

    // Profile photo preview and upload for nounou
    const inputNunny = document.getElementById('profileNannyPhotoInput') as HTMLInputElement;
    const previewNunny = document.getElementById('profileNannyPhotoPreview') as HTMLImageElement;
    if (inputNunny && previewNunny) {
      inputNunny.addEventListener('change', (e: any) => {
        const file = e.target.files[0];
        if (file) {
          const reader = new FileReader();
          reader.onload = (evt: any) => {
            previewNunny.src = evt.target.result;
          };
          reader.readAsDataURL(file);
        }
      });
    }
  }

  nextStep(step: number) {
    document.querySelectorAll('.form-step').forEach(div => {
      div.classList.add('d-none');
    });
    const stepDiv = document.getElementById('step' + step);
    if (stepDiv) stepDiv.classList.remove('d-none');
  }

  toggleOtherLanguage(event: any) {
    const select = event.target;
    const otherInput = document.getElementById('otherLanguageInput') as HTMLInputElement;
    if (select.value === 'other') {
      otherInput.classList.remove('d-none');
      otherInput.required = true;
    } else {
      otherInput.classList.add('d-none');
      otherInput.required = false;
    }
  }

  previewAndBase64(event: any, previewId: string, hiddenId: string) {
    const file = event.target.files[0];
    const preview = document.getElementById(previewId) as HTMLImageElement;
    const hidden = document.getElementById(hiddenId) as HTMLInputElement;
    if (file) {
      const reader = new FileReader();
      reader.onload = (evt: any) => {
        preview.src = evt.target.result;
        preview.style.display = 'block';
        hidden.value = evt.target.result;
      };
      reader.readAsDataURL(file);
    } else {
      preview.src = '';
      preview.style.display = 'none';
      hidden.value = '';
    }
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

}
