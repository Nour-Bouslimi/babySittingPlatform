import { AfterViewInit,Component } from '@angular/core';

@Component({
  selector: 'app-signup-nounou',
  templateUrl: './signup-nounou.component.html',
  styleUrls: ['./signup-nounou.component.css']
})
export class SignupNounouComponent implements AfterViewInit{
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
