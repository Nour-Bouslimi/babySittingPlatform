import { AfterViewInit,Component } from '@angular/core';

@Component({
  selector: 'app-signup-parent',
  templateUrl: './signup-parent.component.html',
  styleUrls: ['./signup-parent.component.css']
})
export class SignupParentComponent implements AfterViewInit{
  ngAfterViewInit() {
    // Afficher/masquer le mot de passe
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

    // Affiche/masque la liste des âges enfants
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

    // Prévisualisation de l'image utilisateur (optionnel)
    const userImageInput = document.getElementById('userImage') as HTMLInputElement;
    const previewImage = document.getElementById('previewImage') as HTMLImageElement;
    const userImageBase64 = document.getElementById('userImageBase64') as HTMLInputElement;
    if (userImageInput && previewImage && userImageBase64) {
      userImageInput.addEventListener('change', (event: any) => {
        const file = event.target.files[0];
        if (file) {
          const reader = new FileReader();
          reader.onload = (evt: any) => {
            previewImage.src = evt.target.result;
            previewImage.style.display = 'block';
            userImageBase64.value = evt.target.result;
          };
          reader.readAsDataURL(file);
        } else {
          previewImage.src = '';
          previewImage.style.display = 'none';
          userImageBase64.value = '';
        }
      });
    }
  }

}
