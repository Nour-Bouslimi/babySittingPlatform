import { AfterViewInit,Component } from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {User} from "../models/user";
import {UserService} from "../services/user.service";
import {Router} from "@angular/router";
import Swal from "sweetalert2";
import {ImageModel} from "../models/ImageModel";
import {UserRole} from "../models/UserRole";

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


  //controle de saisie
  // Validation de l'âge : au moins 19 ans
  ageValidator(control: AbstractControl): { [key: string]: boolean } | null {
    const birthDate = new Date(control.value);
    const today = new Date();

    // Vérifie si la date est dans le futur
    if (birthDate > today) {
      return { invalidAge: true };
    }

    // Calcule l'âge réel
    let age = today.getFullYear() - birthDate.getFullYear();
    const monthDiff = today.getMonth() - birthDate.getMonth();

    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
      age--;
    }

    return age < 19 ? { invalidAge: true } : null;
  }

  signupForm: FormGroup;
  user:User = new User();
  message: string = '';
  error: string = '';
  maxDate: string;
  imageModel:ImageModel = new ImageModel();
imageToUpload: File | null = null;
imageUrl: string | ArrayBuffer | null = null; // Pour stocker l'URL de l'image

  constructor(private fb: FormBuilder,
  private userService: UserService,
  private router: Router) {
    // Calculer la date maximale pour la date de naissance (19 ans avant aujourd'hui)
    this.maxDate = new Date(new Date().setFullYear(new Date().getFullYear() - 19))
      .toISOString()
      .split('T')[0];

    this.signupForm= this.fb.group({
      address:['',[Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', [Validators.required, Validators.pattern('^\\+?\\d{8,20}$')]], // Format international
      genre: ['', [Validators.required]],
      //la date n'accepte que ceux on 19 ans ou plus
      dateOfBirth: ['', [Validators.required, this.ageValidator]],
      ageChildren: ['', [Validators.required]],
      nbChildren: ['', [Validators.required, Validators.min(1), Validators.max(4)]],
      firstName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20), Validators.pattern('^[a-zA-Z]+$')]], // should contain only letters
      lastName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20), Validators.pattern('^[a-zA-Z]+$')]], // should contain only letters
      cin: ['', [Validators.required, Validators.pattern('^[0-9]{8}$')]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.pattern('^(?=.*[A-Za-z])(?=.*\\d).{8,}$')]], // Au moins 8 caractères, une lettre et un chiffre
     photo: ['', [Validators.required]]
    })
  }
// Méthode pour prévisualiser l'image sélectionnée
  previewImage(file:File){
    const reader = new FileReader();
    reader.onload= ()=>{
      this.imageUrl = reader.result; // Stocker l'URL de l'image pour la prévisualisation
    }
    reader.readAsDataURL(file); // Lire le fichier comme une URL de données
  }


  // Méthode pour gérer la sélection de fichier
  onFileSelected(event:any){
    const element = event.currentTarget as HTMLInputElement;
    let fileList: FileList | null = element.files;
    if(fileList){
      this.imageToUpload = fileList[0]; // Prendre le premier fichier sélectionné
      this.imageModel=event.target?.files[0]; // Prendre le premier fichier sélectionné (2eme methode)
      this.previewImage(this.imageToUpload); // Prévisualiser l'image
    }

  }


  // méthode onsubmit elly bch n3aytelha fyl form pour creer un parent
  onsubmit(){
    if(this.signupForm.valid && this.imageToUpload) {
      this.user= this.signupForm.value;
      this.user.role = UserRole.PARENT; // Set default role to PARENT
       this.userService.createParentWithImage(this.user,this.imageToUpload).subscribe({
        next: () => {
          this.error = '';
          this.message = 'Your account created successfully';
          console.log('Your account created successfully');
          this.signupForm.reset();
          this.imageUrl = null; // Reset image preview after successful submission

          // Show success message using SweetAlert2 (popUp)
          Swal.fire({
            title: 'Success',
            text: "Your account created successfully",
            icon: 'success',
            confirmButtonText: 'OK'
          });
          // After 3 seconds, redirect to the login page
          setTimeout(() => {
            this.router.navigate(['login']);
          }, 3000);
        },
        error: (error) => {
          this.error = 'Error creating parent account: ';
          console.log('Error creating parent account: ', error);
        }
      })
    }else {
      console.log('Form is invalid or no image uploaded. Form valid:', this.signupForm.valid, 'Image:', this.imageToUpload);
      Object.keys(this.signupForm.controls).forEach(key => {
        const controlErrors = this.signupForm.get(key)?.errors;
        if (controlErrors) {
          console.log(`Control: ${key}, Errors:`, controlErrors);
        }
      });
    }
  }







}
