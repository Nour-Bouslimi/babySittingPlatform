import { AfterViewInit,Component } from '@angular/core';
import { Router } from '@angular/router';
declare var bootstrap: any;
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements AfterViewInit{
  constructor(private router: Router) {}
  ngAfterViewInit() {
    // Afficher/masquer le mot de passe de login
    const passwordInput = document.getElementById('passwordInput') as HTMLInputElement;
    const togglePassword = document.getElementById('togglePassword');
    const eyeIcon = document.getElementById('eyeIcon');
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

    // Profile photo preview and upload
    const input = document.getElementById('profilePhotoInput') as HTMLInputElement;
    const preview = document.getElementById('profilePhotoPreview') as HTMLImageElement;
    if (input && preview) {
      input.addEventListener('change', (e: any) => {
        const file = e.target.files[0];
        if (file) {
          const reader = new FileReader();
          reader.onload = (evt: any) => {
            preview.src = evt.target.result;
          };
          reader.readAsDataURL(file);
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

    // Password show/hide for profile
    const passwordInputProfile = document.getElementById('passwordInputProfile') as HTMLInputElement;
    const togglePasswordProfile = document.getElementById('togglePasswordProfile');
    const eyeIconProfile = document.getElementById('eyeIconProfile');
    if (togglePasswordProfile && passwordInputProfile && eyeIconProfile) {
      togglePasswordProfile.addEventListener('click', () => {
        if (passwordInputProfile.type === 'password') {
          passwordInputProfile.type = 'text';
          eyeIconProfile.classList.remove('fa-eye-slash');
          eyeIconProfile.classList.add('fa-eye');
        } else {
          passwordInputProfile.type = 'password';
          eyeIconProfile.classList.remove('fa-eye');
          eyeIconProfile.classList.add('fa-eye-slash');
        }
      });
    }

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

    // Child's age dropdown
    const toggleBtn = document.getElementById('toggleAgeDropdown');
    const dropdown = document.getElementById('ageDropdownList');
    if (toggleBtn && dropdown) {
      toggleBtn.addEventListener('click', function (e) {
        e.preventDefault();
        dropdown.style.display = dropdown.style.display === 'none' ? 'block' : 'none';
      });
      document.addEventListener('click', function (e) {
        if (!toggleBtn.contains(e.target as Node) && !dropdown.contains(e.target as Node)) {
          dropdown.style.display = 'none';
        }
      });
    }

    // Modals notifications/messages (si les boutons existent)
    const notifBtn = document.getElementById('notifBtn');
    const msgBtn = document.getElementById('msgBtn');
    const notifModal = document.getElementById('notifModal');
    const msgModal = document.getElementById('msgModal');
    if (notifBtn && msgBtn && notifModal && msgModal) {
      notifBtn.addEventListener('click', () => {
        notifModal.style.display = notifModal.style.display === 'none' ? 'block' : 'none';
        msgModal.style.display = 'none';
      });
      msgBtn.addEventListener('click', () => {
        msgModal.style.display = msgModal.style.display === 'none' ? 'block' : 'none';
        notifModal.style.display = 'none';
      });
      document.addEventListener('click', function (e) {
        if (!notifBtn.contains(e.target as Node) && !notifModal.contains(e.target as Node)) {
          notifModal.style.display = 'none';
        }
        if (!msgBtn.contains(e.target as Node) && !msgModal.contains(e.target as Node)) {
          msgModal.style.display = 'none';
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

}


