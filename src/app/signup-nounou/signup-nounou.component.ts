import { AfterViewInit,Component } from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {User} from "../models/user";
import {ImageModel} from "../models/ImageModel";
import {UserService} from "../services/user.service";
import {Router} from "@angular/router";
import {UserRole} from "../models/UserRole";
import Swal from "sweetalert2";

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
  // Pour stocker l'image à uploader (photo)
  imageModel:ImageModel = new ImageModel();
  imageToUpload: File | null = null;
  imageUrl: string | ArrayBuffer | null = null; // Pour stocker l'URL de l'image
// Pour stocker l'image à uploader (imgEtude)
  imageModelEtude: ImageModel = new ImageModel();
  imgEtudeToUpload: File | null = null;
  imgEtudeUrl: string | ArrayBuffer | null = null; // Pour stocker l'URL de l'image d'étude
  // Pour stocker l'image à uploader (imgIdent1)
  imageModelIdent1: ImageModel = new ImageModel();
  imgIdent1ToUpload: File | null = null;
  imgIdent1Url: string | ArrayBuffer | null = null; // Pour stocker l'URL de l'image d'identité 1
  // Pour stocker l'image à uploader (imgIdent2)
  imageModelIdent2: ImageModel = new ImageModel();
  imgIdent2ToUpload: File | null = null;
  imgIdent2Url: string | ArrayBuffer | null = null; // Pour stocker l'URL de l'image d'identité 2

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
      firstName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20), Validators.pattern('^[a-zA-Z]+$')]], // should contain only letters
      lastName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20), Validators.pattern('^[a-zA-Z]+$')]], // should contain only letters
      cin: ['', [Validators.required, Validators.pattern('^[0-9]{8}$')]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.pattern('^(?=.*[A-Za-z])(?=.*\\d).{8,}$')]], // Au moins 8 caractères, une lettre et un chiffre
      etatCivil: ['', [Validators.required]],
      niveauEtude: ['', [Validators.required]],
      domaineEtude: ['', [Validators.required, Validators.pattern('^[a-zA-Z ]+$')]], // should contain only letters
      experience: ['', [Validators.required]],
      langue: ['', [Validators.required]],
      niveau: ['', [Validators.required]],
      centreInteret: ['', [Validators.required, Validators.pattern('^[a-zA-Z ]+$')]], // should contain only letters
      motorise: ['', [Validators.required]],
      fumer: ['', [Validators.required]],
      imgIdent1: ['', [Validators.required]],
      imgIdent2: ['', [Validators.required]],
      tarifHoraire: ['', [Validators.required, Validators.min(0)]], // Tarif horaire obligatoire et positif
      imgEtude: ['', [Validators.required]],
      photo: ['', [Validators.required]]
    })
  }
// Méthode pour prévisualiser l'image sélectionnée (photo)
  previewImage(file:File){
    const reader = new FileReader();
    reader.onload= ()=>{
      this.imageUrl = reader.result; // Stocker l'URL de l'image pour la prévisualisation
    }
    reader.readAsDataURL(file); // Lire le fichier comme une URL de données
  }

// Méthode pour gérer la sélection de fichier (imgEtude)
  previewImageEtude(file:File){
    const reader = new FileReader();
    reader.onload= ()=>{
      this.imgEtudeUrl = reader.result; // Stocker l'URL de l'image pour la prévisualisation
    }
    reader.readAsDataURL(file); // Lire le fichier comme une URL de données
  }
// Méthode pour gérer la sélection de fichier (imgIdent1)
  previewImageIdent1(file:File){
    const reader = new FileReader();
    reader.onload= ()=>{
      this.imgIdent1Url = reader.result; // Stocker l'URL de l'image pour la prévisualisation
    }
    reader.readAsDataURL(file); // Lire le fichier comme une URL de données
  }
// Méthode pour gérer la sélection de fichier (imgIdent2)
  previewImageIdent2(file:File){
    const reader = new FileReader();
    reader.onload= ()=>{
      this.imgIdent2Url = reader.result; // Stocker l'URL de l'image pour la prévisualisation
    }
    reader.readAsDataURL(file); // Lire le fichier comme une URL de données
  }



  // Méthode pour gérer la sélection de fichier (photo)
  onFileSelected(event:any){
    const element = event.currentTarget as HTMLInputElement;
    let fileList: FileList | null = element.files;
    if(fileList){
      this.imageToUpload = fileList[0]; // Prendre le premier fichier sélectionné
      this.imageModel=event.target?.files[0]; // Prendre le premier fichier sélectionné (2eme methode)
      this.previewImage(this.imageToUpload); // Prévisualiser l'image
    }

  }
  // Méthode pour gérer la sélection de fichier (imgEtude)
  onFileSelectedEtude(event:any){
    const element = event.currentTarget as HTMLInputElement;
    let fileList: FileList | null = element.files;
    if(fileList){
      this.imgEtudeToUpload = fileList[0]; // Prendre le premier fichier sélectionné
      this.imageModelEtude=event.target?.files[0]; // Prendre le premier fichier sélectionné (2eme methode)
      this.previewImageEtude(this.imgEtudeToUpload); // Prévisualiser l'image
    }

  }
  // Méthode pour gérer la sélection de fichier (imgIdent1)
  onFileSelectedIdent1(event:any){
    const element = event.currentTarget as HTMLInputElement;
    let fileList: FileList | null = element.files;
    if(fileList){
      this.imgIdent1ToUpload = fileList[0]; // Prendre le premier fichier sélectionné
      this.imageModelIdent1=event.target?.files[0]; // Prendre le premier fichier sélectionné (2eme methode)
      this.previewImageIdent1(this.imgIdent1ToUpload); // Prévisualiser l'image
    }

  }
  // Méthode pour gérer la sélection de fichier (imgIdent2)
  onFileSelectedIdent2(event:any){
    const element = event.currentTarget as HTMLInputElement;
    let fileList: FileList | null = element.files;
    if(fileList){
      this.imgIdent2ToUpload = fileList[0]; // Prendre le premier fichier sélectionné
      this.imageModelIdent2=event.target?.files[0]; // Prendre le premier fichier sélectionné (2eme methode)
      this.previewImageIdent2(this.imgIdent2ToUpload); // Prévisualiser l'image
    }

  }



  // méthode onsubmit elly bch n3aytelha fyl form pour creer un nounou
 /* onsubmit(){
    if(this.signupForm.valid && this.imageToUpload&& this.imgEtudeToUpload && this.imgIdent1ToUpload && this.imgIdent2ToUpload) {
      this.user= this.signupForm.value;
      this.user.role = UserRole.NOUNOU; // Set default role to PARENT
      this.userService.createNounouWithImages(this.user,this.imageToUpload,this.imgIdent1ToUpload,this.imgIdent2ToUpload,this.imgEtudeToUpload).subscribe({
        next: () => {
          this.error = '';
          this.message = 'Your account created successfully';
          console.log('Your account created successfully');
          this.signupForm.reset();
          this.imageUrl = null; // Reset image preview after successful submission
          this.imgEtudeUrl = null; // Reset the image to upload
          this.imgIdent1Url = null; // Reset the image to upload
          this.imgIdent2Url = null; // Reset the image to upload


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
  }*/

  onsubmit() {
    if (this.signupForm.valid && this.imageToUpload && this.imgEtudeToUpload && this.imgIdent1ToUpload && this.imgIdent2ToUpload) {
      // Create a copy of signupForm.value excluding image-related fields
      const userData = { ...this.signupForm.value };
      delete userData.imgIdent1;
      delete userData.imgIdent2;
      delete userData.imgEtude;
      delete userData.photo;

      this.user = userData;
      this.user.role = UserRole.NOUNOU; // Set default role to NOUNOU

      this.userService.createNounouWithImages(this.user, this.imageToUpload, this.imgIdent1ToUpload, this.imgIdent2ToUpload, this.imgEtudeToUpload).subscribe({
        next: () => {
          this.error = '';
          this.message = 'Your account created successfully';
          console.log('Your account created successfully');
          this.signupForm.reset();
          this.imageUrl = null;
          this.imgEtudeUrl = null;
          this.imgIdent1Url = null;
          this.imgIdent2Url = null;

          Swal.fire({
            title: 'Success',
            text: 'Your account created successfully',
            icon: 'success',
            confirmButtonText: 'OK'
          });
          setTimeout(() => {
            this.router.navigate(['login']);
          }, 3000);
        },
        error: (error) => {
          this.error = 'Error creating parent account: ';
          console.log('Error creating parent account: ', error);
        }
      });
    } else {
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
