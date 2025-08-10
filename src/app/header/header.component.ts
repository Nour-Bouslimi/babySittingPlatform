import {AfterViewInit, Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import Swal from "sweetalert2";
import {UserService} from "../services/user.service";
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthentificationRequest} from "../models/AuthentificationRequest";
import {AuthenticationResponse} from "../models/authentication-response";
import {AuthService} from "../services/auth.service";
import {UserRole} from "../models/UserRole";
import {User} from "../models/user";
import {ImageModel} from "../models/ImageModel";
import {NotificationService} from "../services/notification.service";
import {Notification} from "../models/notification";
declare var bootstrap: any;
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements AfterViewInit,OnInit{



  authForm: FormGroup;
  authRequest:AuthentificationRequest={email: '',
    password: ''};
  authResponse: AuthenticationResponse = { accessToken: '',tokenType: ''};
  profileForm!: FormGroup;
  profileNounouForm!:FormGroup;
  userData:User |undefined;
  maxDate: string;
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
  //pour les notifs
  notifications: Notification[] = [];
  unreadCount: number = 0;
  userId!: number;
  constructor(private authService: AuthService, private router: Router, private formBuilder: FormBuilder,private userService: UserService,private notificationService: NotificationService) {
    // Calculer la date maximale pour la date de naissance (19 ans avant aujourd'hui)
    this.maxDate = new Date(new Date().setFullYear(new Date().getFullYear() - 19))
      .toISOString()
      .split('T')[0];
    // Initialiser le formulaire de connexion
    this.authForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.pattern('^(?=.*[A-Za-z])(?=.*\\d).{8,}$')]]
    });
    // Initialiser le formulaire de profil pour l'utilisateur parent
    this.profileForm = this.formBuilder.group({
      address: [''],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: [''],
      genre: [''],
      dateOfBirth: [''],
      ageChildren: [''],
      nbChildren: [''],
      firstName: [''],
      lastName: [''],
      password: ['']
    });
    // Initialiser le formulaire de profil pour l'utilisateur nounou
    this.profileNounouForm = this.formBuilder.group({

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
    });
  }

  error: string = '';
  message: string = '';
  //visualiser l'image de profil de parent
  imageModel:ImageModel = new ImageModel();
  imageToUpload: File | null = null;
  imageUrl: string | ArrayBuffer | null = null; // Pour stocker l'URL de l'image
 //visualiser l'image de profil de nounou
  imageUrlNounou: string | ArrayBuffer | null = null; // Pour stocker l'URL de l'image de nounou
  imageToUploadNounou: File | null = null; // image sélectionnée par l'utilisateur pour nounou
  imageModelNounou: ImageModel = new ImageModel(); // Modèle pour l'image de nounou
//Pour stocker l'image à uploader (imgEtude)
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


  user!: User; // ou User | null = null; si tu veux être plus prudent
  selectedImageFile!: File; // image sélectionnée par l'utilisateur
role:string = ''; // Pour stocker le rôle de l'utilisateur
  loggedIn: boolean = false;
// Méthode pour prévisualiser l'image sélectionnée de parent
  previewImage(file:File){
    const reader = new FileReader();
    reader.onload= ()=>{
      this.imageUrl = reader.result; // Stocker l'URL de l'image pour la prévisualisation
    }
    reader.readAsDataURL(file); // Lire le fichier comme une URL de données
  }

  //methode pour prévisualiser l'image sélectionnée de nounou
  previewImageNounou(file:File){
    const reader = new FileReader();
    reader.onload= ()=>{
      this.imageUrlNounou = reader.result; // Stocker l'URL de l'image pour la prévisualisation
    }
    reader.readAsDataURL(file); // Lire le fichier comme une URL de données
  }

  // Méthode pour prévisualiser l'image d'étude
  previewImageEtude(file:File){
    const reader = new FileReader();
    reader.onload= ()=>{
      this.imgEtudeUrl = reader.result; // Stocker l'URL de l'image pour la prévisualisation
    }
    reader.readAsDataURL(file); // Lire le fichier comme une URL de données
  }
  // Méthode pour prévisualiser l'image d'identité 1
  previewImageIdent1(file:File){
    const reader = new FileReader();
    reader.onload= ()=>{
      this.imgIdent1Url = reader.result; // Stocker l'URL de l'image pour la prévisualisation
    }
    reader.readAsDataURL(file); // Lire le fichier comme une URL de données
  }
  // Méthode pour prévisualiser l'image d'identité 2
  previewImageIdent2(file:File){
    const reader = new FileReader();
    reader.onload= ()=>{
      this.imgIdent2Url = reader.result; // Stocker l'URL de l'image pour la prévisualisation
    }
    reader.readAsDataURL(file); // Lire le fichier comme une URL de données
  }



  // Méthode pour gérer la sélection de fichier de parent
  onFileSelected(event:any){
    const element = event.currentTarget as HTMLInputElement;
    let fileList: FileList | null = element.files;
    if(fileList){
      this.imageToUpload = fileList[0]; // Prendre le premier fichier sélectionné
      this.imageModel=event.target?.files[0]; // Prendre le premier fichier sélectionné (2eme methode)
      this.previewImage(this.imageToUpload); // Prévisualiser l'image
    }

  }
  // Méthode pour gérer la sélection de fichier de nounou
  onFileSelectedNounou(event:any){
    const element = event.currentTarget as HTMLInputElement;
    let fileList: FileList | null = element.files;
    if(fileList){
      this.imageToUploadNounou = fileList[0]; // Prendre le premier fichier sélectionné
      this.imageModelNounou=event.target?.files[0]; // Prendre le premier fichier sélectionné (2eme methode)
      this.previewImageNounou(this.imageToUploadNounou); // Prévisualiser l'image
    }

  }
  // Méthode pour gérer la sélection de fichier d'étude
  onFileSelectedEtude(event:any){
    const element = event.currentTarget as HTMLInputElement;
    let fileList: FileList | null = element.files;
    if(fileList){
      this.imgEtudeToUpload = fileList[0]; // Prendre le premier fichier sélectionné
      this.imageModelEtude=event.target?.files[0]; // Prendre le premier fichier sélectionné (2eme methode)
      this.previewImageEtude(this.imgEtudeToUpload); // Prévisualiser l'image
    }

  }
  // Méthode pour gérer la sélection de fichier d'identité 1
  onFileSelectedIdent1(event:any){
    const element = event.currentTarget as HTMLInputElement;
    let fileList: FileList | null = element.files;
    if(fileList){
      this.imgIdent1ToUpload = fileList[0]; // Prendre le premier fichier sélectionné
      this.imageModelIdent1=event.target?.files[0]; // Prendre le premier fichier sélectionné (2eme methode)
      this.previewImageIdent1(this.imgIdent1ToUpload); // Prévisualiser l'image
    }

  }
  // Méthode pour gérer la sélection de fichier d'identité 2
  onFileSelectedIdent2(event:any){
    const element = event.currentTarget as HTMLInputElement;
    let fileList: FileList | null = element.files;
    if(fileList){
      this.imgIdent2ToUpload = fileList[0]; // Prendre le premier fichier sélectionné
      this.imageModelIdent2=event.target?.files[0]; // Prendre le premier fichier sélectionné (2eme methode)
      this.previewImageIdent2(this.imgIdent2ToUpload); // Prévisualiser l'image
    }

  }

  //pour ouvrir le modal de notif
  notifModalRef: any;

  openNotifModal() {
    const notifModalEl = document.getElementById('notifModal');
    this.notifModalRef = new bootstrap.Modal(notifModalEl, {
      backdrop: false, // important pour ne pas fermer les autres modals
      focus: false     // évite de déplacer le focus
    });
    this.notifModalRef.show();
  }

  // Méthode pour visualiser les informations de l'utilisateur connecté

  ngOnInit(): void {
     this.userId = this.authService.getUserId();
    //load notif
    this.loadNotifications();
    //extraire le role from le token pour personnaliser le navbar
    const tokenPayload = this.authService.decodedToken();
    console.log('Decoded token payload:', tokenPayload);
    const roles = tokenPayload?.roles || []; // Gestion si roles est absent
     this.role = Array.isArray(roles) ? roles[0] : roles;
   //verifier si user est connecté pour hide btn login
    this.loggedIn = this.authService.isLoggedIn();
    if (this.userId !== null) {
      this.userService.getUserById(this.userId).subscribe({
        next: (user: User) => {
          console.log('Utilisateur connecté :', user);
          this.user = user;

          const formattedDate = user.dateOfBirth ? user.dateOfBirth.substring(0, 10) : '';

          // Parsing des âges en tableau depuis la string
          const ageArray = user.ageChildren ? user.ageChildren.split(',') : [];

          // Options fixes correspondant aux checkbox
          const ageOptions = ['0-1', '1-3', '3-6', '6-10', '10+'];

          // Création du FormArray avec booléens (coché ou pas)
          const ageChildrenFormArray = this.formBuilder.array(
            ageOptions.map(age => this.formBuilder.control(ageArray.includes(age)))
          );

          // Initialisation du formulaire de profil avec les données de l'utilisateur parent
          this.profileForm = this.formBuilder.group({
            address: [user.address || ''],
            email: [user.email || ''],
            phoneNumber: [user.phoneNumber || ''],
            genre: [user.genre || ''],
            ageChildren: ageChildrenFormArray,
            nbChildren: [user.nbChildren ?? 0],
            dateOfBirth: [formattedDate],
            firstName: [user.firstName || ''],
            lastName: [user.lastName || ''],
            password: [''], // vide pour des raisons de sécurité
            photo: [user.photo || '']
          });
          console.log('Valeur formulaire:', this.profileForm.value);
// Initialisation du formulaire de profil avec les données de l'utilisateur nounou
          this.profileNounouForm = this.formBuilder.group({
            address: [user.address || '', Validators.required],
            email: [user.email || '', [Validators.required, Validators.email]],
            phoneNumber: [user.phoneNumber || '', [Validators.required, Validators.pattern('^\\+?\\d{8,20}$')]], // Format international
            genre: [user.genre || '', Validators.required],
            dateOfBirth: [formattedDate, [Validators.required, this.ageValidator]],
            firstName: [user.firstName || '', [Validators.required, Validators.minLength(2), Validators.maxLength(20), Validators.pattern('^[a-zA-Z]+$')]], // should contain only letters
            lastName: [user.lastName || '', [Validators.required, Validators.minLength(2), Validators.maxLength(20), Validators.pattern('^[a-zA-Z]+$')]], // should contain only letters
            cin: [user.cin || '', [Validators.required, Validators.pattern('^[0-9]{8}$')]],
            password: ['', [Validators.required, Validators.minLength(8), Validators.pattern('^(?=.*[A-Za-z])(?=.*\\d).{8,}$')]], // Au moins 8 caractères, une lettre et un chiffre
            etatCivil: [user.etatCivil || '', Validators.required],
            niveauEtude: [user.niveauEtude || '', Validators.required],
            domaineEtude: [user.domaineEtude || '', [Validators.required, Validators.pattern('^[a-zA-Z ]+$')]], // should contain only letters
            experience: [user.experience || '', Validators.required],
            langue: [user.langue || '', Validators.required],
            niveau: [user.niveau || '', Validators.required],
            centreInteret: [user.centreInteret || '', [Validators.required, Validators.pattern('^[a-zA-Z ]+$')]], // should contain only letters
            motorise: [user.motorise ||'', Validators.required],
            fumer: [user.fumer||'', Validators.required],
            imgIdent1: [user.imgIdent1 || '', Validators.required],
            imgIdent2: [user.imgIdent2 || '', Validators.required],
            tarifHoraire: [user.tarifHoraire || 0, [Validators.required, Validators.min(0)]], // Tarif horaire obligatoire et positif
            imgEtude: [''], // Champ vide pour l'image d'étude
            photo: [user.photo || ''] // Champ vide pour l'image de profil

          });
          console.log('Valeur formulaire nounou:', this.profileNounouForm.value);

          // Charger l'image existante de parent
          this.imageUrl = user.photo ? 'http://localhost:8081/user/displayImage/' + user.photo : null;
          // Charger l'image existante de nounou
          this.imageUrlNounou = user.photo ? 'http://localhost:8081/user/displayImage/' + user.photo : null;
          // Charger l'image d'étude si elle existe
          this.imgEtudeUrl = user.imgEtude ? 'http://localhost:8081/user/displayImage/' + user.imgEtude : null;
          // Charger l'image d'identité 1 si elle existe
          this.imgIdent1Url = user.imgIdent1 ? 'http://localhost:8081/user/displayImage/' + user.imgIdent1 : null;
          // Charger l'image d'identité 2 si elle existe
          this.imgIdent2Url = user.imgIdent2 ? 'http://localhost:8081/user/displayImage/' + user.imgIdent2 : null;
        },
        error: (err) => {
          console.error('Erreur lors de la récupération du user :', err);
        }
      });
    } else {
      console.error("Aucun ID utilisateur trouvé dans le token.");
    }

  }

// Méthode pour charger les notifications de l'utilisateur connecté
  loadNotifications(): void {
    this.notificationService.getNotificationsByIdUser(this.userId).subscribe({
      next: (res) => {
        this.notifications = res.sort((a, b) => (b.date! > a.date! ? 1 : -1));
        console.log('notifications: ' ,this.notifications);
        this.unreadCount = this.notifications.filter(n => !n.isRead).length;
      },
      error: (err) => console.error("❌ Failed to load notifications", err)
    });
  }

  markAllAsRead(): void {
    this.notificationService.markAllNotificationsAsRead(this.userId).subscribe({
      next: () => {
        this.notifications.forEach(n => n.isRead = true);
        this.unreadCount = 0;
      },
      error: (err) => console.error("❌ Failed to mark as read", err)
    });
  }
  deleteNotification(id: number): void {
    this.notificationService.deleteNotification(id).subscribe({
      next: () => {
        this.notifications = this.notifications.filter(n => n.idNotif !== id);
      },
      error: (err) => console.error("❌ Failed to delete notification", err)
    });
  }

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
          this.loadUserData(); // Charger les données pour PARENT
        } else if (role === 'ROLE_NOUNOU' || role === 'NOUNOU') {
          this.router.navigate(['home']);
          // this.loadProfileData(); // Charger les données pour NOUNOU
          //fermer le modal login
          const modal = document.getElementById('loginModal');
          if (modal) {
            const bsModal = bootstrap.Modal.getInstance(modal) || new bootstrap.Modal(modal);
            bsModal.hide();
          }
          this.loadUserData(); // Charger les données pour NOUNOU
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
  loadUserData() {
    const user = this.authService.decodedToken();
    //debug
    const token = this.authService.getToken();
    console.log('Token exists:', !!token);
    console.log('Token value:', token);
    /* Debug: vérifier les headers
    const headers = this.authService.createAuthorization();
    console.log('Authorization headers:', headers.get('Authorization'));
    this.userService.getUserById(2752).subscribe({
      next: (data) => {
        console.log('User data loaded successfully:', data);
      },
      error: (error) => {
        console.error('Error fetching user data:', error);
      }
    });*/

    if (user && user.user && user.user.id) {
      this.userService.getUserById(user.user.id).subscribe({
        next: (data) => {
          console.log('User data from backend:', data);
          this.userData = data;
          this.populateProfileForm(data);
          this.populateProfileFormNounou(data);
        },
        error: (err) => {
          console.error('Error fetching user data:', err);
        }
      });
    } else {
      console.error('No user ID found in token');
    }
  }

  //populateProfileForm parent
  populateProfileForm(userData: any) {
    this.profileForm.patchValue({
      address: userData.address || '',
      email: userData.email || '',
      phoneNumber: userData.phoneNumber || '',
      genre: userData.genre || '',
      dateOfBirth: userData.dateOfBirth ? new Date(userData.dateOfBirth).toISOString().split('T')[0] : '',
      nbChildren: userData.nbChildren || '',
      firstName: userData.firstName || '',
      lastName: userData.lastName || '',
      password: '' // Ne pas pré-remplir pour la sécurité
    });
    // Pour les cases à cocher (ageChildren), utiliser setValue ou gérer comme FormArray si multiple
    const ageChildrenControl = this.profileForm.get('ageChildren');
    if (ageChildrenControl && userData.ageChildren) {
      ageChildrenControl.setValue(userData.ageChildren);
    }
  }
  //populateProfileForm nounou
  populateProfileFormNounou(userData: any) {
    this.profileNounouForm.patchValue({
      address: userData.address || '',
      email: userData.email || '',
      phoneNumber: userData.phoneNumber || '',
      genre: userData.genre || '',
      dateOfBirth: userData.dateOfBirth ? new Date(userData.dateOfBirth).toISOString().split('T')[0] : '',
      firstName: userData.firstName || '',
      lastName: userData.lastName || '',
      cin: userData.cin || '',
      password: '', // Ne pas pré-remplir pour la sécurité
      etatCivil: userData.etatCivil || '',
      niveauEtude: userData.niveauEtude || '',
      domaineEtude: userData.domaineEtude || '',
      experience: userData.experience || '',
      langue: userData.langue || '',
      niveau: userData.niveau || '',
      centreInteret: userData.centreInteret || '',
      motorise: userData.motorise || '',
      fumer: userData.fumer || '',
      //imgIdent1: userData.imgIdent1 || '',
      //imgIdent2: userData.imgIdent2 || '',
      tarifHoraire: userData.tarifHoraire || 0,
      imgEtude: '', // Champ vide pour l'image d'étude
     // photo: userData.photo || '' // Champ vide pour l'image de profil
    });
  }
 /* ngOnInit(): void {
    const userId = this.authService.getUserId();

    if (userId !== null) {
      this.userService.getUserById(userId).subscribe({
        next: (user) => {
          console.log("User from backend:", user);

          this.profileForm = this.fb.group({
            address: [user.address || ''],
            email: [user.email || ''],
            phoneNumber: [user.phoneNumber || ''],
            genre: [user.genre || ''],
            ageChildren: [user.ageChildren || ''],
            nbChildren: [user.nbChildren || 0],
            dateOfBirth: [user.dateOfBirth || ''],
            firstName: [user.firstName || ''],
            lastName: [user.lastName || ''],
            password: [''] // champ vide pour des raisons de sécurité
          });
        },
        error: (err) => {
          console.error('Erreur lors de la récupération de l’utilisateur :', err);
        }
      });
    } else {
      console.error('Aucun ID utilisateur trouvé dans le token.');
    }
  }
*/


  //modifier le profil de l'utilisateur connecté parent
  onSubmit(): void {
    if (!this.profileForm || !this.user) {
      console.warn("⛔ Le formulaire ou l'utilisateur n'est pas prêt.");
      return;
    }

    const formValue = this.profileForm.value;
    const userId = this.authService.getUserId();

    if (!userId) {
      console.error("Aucun ID utilisateur trouvé");
      return;
    }

    const updatedUser: Partial<User> = {};

    // Comparaison champ par champ pour éviter les updates inutiles
    if (formValue.address && formValue.address !== this.user.address) {
      updatedUser.address = formValue.address;
    }
    if (formValue.email && formValue.email !== this.user.email) {
      updatedUser.email = formValue.email;
    }
    if (formValue.phoneNumber && formValue.phoneNumber !== this.user.phoneNumber) {
      updatedUser.phoneNumber = formValue.phoneNumber;
    }
    if (formValue.genre && formValue.genre !== this.user.genre) {
      updatedUser.genre = formValue.genre;
    }
    if (formValue.dateOfBirth && formValue.dateOfBirth !== this.user.dateOfBirth?.substring(0, 10)) {
      updatedUser.dateOfBirth = formValue.dateOfBirth;
    }
    if (formValue.firstName && formValue.firstName !== this.user.firstName) {
      updatedUser.firstName = formValue.firstName;
    }
    if (formValue.lastName && formValue.lastName !== this.user.lastName) {
      updatedUser.lastName = formValue.lastName;
    }
    if (formValue.nbChildren !== this.user.nbChildren) {
      updatedUser.nbChildren = formValue.nbChildren;
    }

    // Traitement des âges des enfants
    const ageOptions = ['0-1', '1-3', '3-6', '6-10', '10+'];
    const selectedAges = formValue.ageChildren
      .map((checked: boolean, i: number) => (checked ? ageOptions[i] : null))
      .filter((v: string | null) => v !== null)
      .join(',');

    if (selectedAges !== this.user.ageChildren) {
      updatedUser.ageChildren = selectedAges;
    }

    // Mise à jour de l'image si elle a changé
    if (this.imageToUpload) {
      this.userService.updateUserWithImage(userId, this.imageToUpload).subscribe({
        next: () => {
          console.log("📷 Image utilisateur mise à jour");
          Swal.fire({
            title: 'Success',
            text: 'Profile image updated successfully',
            icon: 'success',
            confirmButtonText: 'OK'
          });
          this.router.navigate(['/home']);
        },
        error: err => console.error("❌ Erreur lors de la mise à jour de l'image:", err)
      });
    }

    // Mise à jour du mot de passe si un nouveau est renseigné
    if (formValue.password && formValue.password.trim().length > 0) {
      const currentPassword = prompt("Enter your current password :");
      if (currentPassword) {
        this.userService.updateUserPassword(userId, currentPassword, formValue.password).subscribe({
          next: () =>{ console.log("🔑 Mot de passe mis à jour");
            Swal.fire({
              title: 'Success',
              text: 'Password updated successfully',
              icon: 'success',
              confirmButtonText: 'OK'
            });

            this.profileForm.patchValue({ password: '' }); // Réinitialiser le champ mot de passe
            this.router.navigate(['/home']); // Rediriger vers la page d'accueil

            },

          error: err =>{ console.error("❌ Erreur de mise à jour du mot de passe:", err);
          alert("Erreur: " + JSON.stringify(err));}
        });
      }
    }

    // Mise à jour des autres champs
    if (Object.keys(updatedUser).length > 0) {
      const userToUpdate: User = {
        ...this.user,
        ...updatedUser,
        idUser: userId
      };

      this.userService.updateUser(userId, userToUpdate).subscribe({
        next: updated => {
          console.log("✅ Informations utilisateur mises à jour :", updated);
          this.user = updated;
          this.message = 'Profile updated successfully';
          Swal.fire({
            title: 'Success',
            text: 'Profile updated successfully',
            icon: 'success',
            confirmButtonText: 'OK'
          });
          //rediriger vers la page d'accueil
          this.router.navigate(['/home']);
        },
        error: err => console.error("❌ Erreur mise à jour utilisateur:", err)
      });
    } else {
      // ✅ Vérification si une image ou un mot de passe a été modifié
      if (!this.imageToUpload && (!formValue.password || formValue.password.trim().length === 0)) {
        console.log("🔄 Aucun champ modifié.");
      }
    }
  }

//modifier le profil de l'utilisateur connecté nounou
  onSubmitNounou(): void {
    if (!this.profileNounouForm || !this.user) {
      console.warn("⛔ Le formulaire ou l'utilisateur n'est pas prêt.");
      return;
    }

    const formValue = this.profileNounouForm.value;
    const userId = this.authService.getUserId();

    if (!userId) {
      console.error("Aucun ID utilisateur trouvé");
      return;
    }

    const updatedUser: Partial<User> = {};

    // Comparaison champ par champ pour éviter les updates inutiles
    if (formValue.address && formValue.address !== this.user.address) {
      updatedUser.address = formValue.address;
    }
    if (formValue.email && formValue.email !== this.user.email) {
      updatedUser.email = formValue.email;
    }
    if (formValue.phoneNumber && formValue.phoneNumber !== this.user.phoneNumber) {
      updatedUser.phoneNumber = formValue.phoneNumber;
    }
    if (formValue.genre && formValue.genre !== this.user.genre) {
      updatedUser.genre = formValue.genre;
    }
    if (formValue.dateOfBirth && formValue.dateOfBirth !== this.user.dateOfBirth?.substring(0, 10)) {
      updatedUser.dateOfBirth = formValue.dateOfBirth;
    }
    if (formValue.firstName && formValue.firstName !== this.user.firstName) {
      updatedUser.firstName = formValue.firstName;
    }
    if (formValue.lastName && formValue.lastName !== this.user.lastName) {
      updatedUser.lastName = formValue.lastName;
    }
    if (formValue.cin && formValue.cin !== this.user.cin) {
      updatedUser.cin = formValue.cin;
    }
    if (formValue.etatCivil && formValue.etatCivil !== this.user.etatCivil) {
      updatedUser.etatCivil = formValue.etatCivil;
    }
    if (formValue.niveauEtude && formValue.niveauEtude !== this.user.niveauEtude) {
      updatedUser.niveauEtude = formValue.niveauEtude;
    }
    if (formValue.domaineEtude && formValue.domaineEtude !== this.user.domaineEtude) {
      updatedUser.domaineEtude = formValue.domaineEtude;
    }
    if (formValue.experience && formValue.experience !== this.user.experience) {
      updatedUser.experience = formValue.experience;
    }
    if (formValue.langue && formValue.langue !== this.user.langue) {
      updatedUser.langue = formValue.langue;
    }
    if (formValue.niveau && formValue.niveau !== this.user.niveau) {
      updatedUser.niveau = formValue.niveau;
    }
    if (formValue.centreInteret && formValue.centreInteret !== this.user.centreInteret) {
      updatedUser.centreInteret = formValue.centreInteret;
    }
    if (formValue.motorise && formValue.motorise !== this.user.motorise) {
      updatedUser.motorise = formValue.motorise;
    }
    if (formValue.fumer && formValue.fumer !== this.user.fumer) {
      updatedUser.fumer = formValue.fumer;
    }

    if (formValue.tarifHoraire && formValue.tarifHoraire !== this.user.tarifHoraire) {
      updatedUser.tarifHoraire = formValue.tarifHoraire;
    }
    // Traitement des images
    if (this.imageToUploadNounou) {
      this.userService.updateUserWithImage(userId, this.imageToUploadNounou).subscribe({
        next: () => console.log("📷 Image utilisateur nounou mise à jour"),
        error: err => console.error("❌ Erreur lors de la mise à jour de l'image de nounou:", err)
      });
    }



    // Mise à jour du mot de passe si un nouveau est renseigné
    if (formValue.password && formValue.password.trim().length > 0) {
      const currentPassword = prompt("Enter your current password :");
      if (currentPassword) {
        this.userService.updateUserPassword(userId, currentPassword, formValue.password).subscribe({
          next: () =>{ console.log("🔑 Mot de passe mis à jour");
            Swal.fire({
              title: 'Success',
              text: 'Password updated successfully',
              icon: 'success',
              confirmButtonText: 'OK'
            });

            this.profileNounouForm.patchValue({ password: '' }); // Réinitialiser le champ mot de passe
            this.router.navigate(['/home']); // Rediriger vers la page d'accueil

            },

          error: err =>{ console.error("❌ Erreur de mise à jour du mot de passe:", err);
          alert("Erreur: " + JSON.stringify(err));}
        });
      }
    }
    // Mise à jour des autres champs
    if (Object.keys(updatedUser).length > 0) {
      const userToUpdate: User = {
        ...this.user,
        ...updatedUser,
        idUser: userId
      };

      this.userService.updateUser(userId, userToUpdate).subscribe({
        next: updated => {
          console.log("✅ Informations utilisateur nounou mises à jour :", updated);
          this.user = updated;
          this.message = 'Profile updated successfully';
          Swal.fire({
            title: 'Success',
            text: 'Profile updated successfully',
            icon: 'success',
            confirmButtonText: 'OK'
          });
          //rediriger vers la page d'accueil
          this.router.navigate(['/home']);
        },
        error: err => console.error("❌ Erreur mise à jour utilisateur nounou:", err)
      });
    } else {
      console.log("🔄 Aucun champ modifié.");
    }
  }



//donnée de profil nounou
  /*
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
        this.updateProfilePhoto(userData.photo);
        const modal = new (window as any).bootstrap.Modal(document.getElementById('profileNannyModal'));
        modal.show();
      },
      error: (error) => {
        console.error('Error loading profile data:', error);
      }
    });
  }

  updateProfilePhoto(photoPath: string | undefined) {
    const photoPreviewId = this.authService.getLoggedUser()?.roles?.[0] === 'ROLE_NOUNOU' ? 'profileNannyPhotoPreview' : 'profilePhotoPreview';
    const previewImg = document.getElementById(photoPreviewId) as HTMLImageElement;
    if (previewImg && photoPath) {
      previewImg.src = this.getImageUrl(photoPath); // Utiliser une méthode pour construire l'URL
    } else {
      previewImg.src = '/assets/img/default-user.png'; // Image par défaut si aucune photo
    }
  }

  getImageUrl(photoPath: string): string {
    // Ajustez selon la structure de votre backend (ex. : 'uploads/img/filename.png' ou 'assets/filename')
    return photoPath.startsWith('http') ? photoPath : `/${photoPath}`; // Adaptez selon votre configuration
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
*/
  //update le profil de nounou
  /*submitProfile() {
    const form = document.getElementById('multiStepForm') as HTMLFormElement;
    if (form && this.userData) {
      const formData = new FormData(form);
      this.userService.updateUser(this.userData.idUser, formData).subscribe({ // Ajustez la méthode
        next: (response) => {
          console.log('Profile updated:', response);
          this.message = 'Profile updated successfully';
          this.error = '';
          const modal = (window as any).bootstrap.Modal.getInstance(document.getElementById('profileNannyModal'));
          modal.hide();
        },
        error: (error) => {
          console.error('Error updating profile:', error);
          this.error = 'Failed to update profile';
        }
      });
    }
  }*/




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

              // Rediriger vers la page d'accueil ou de connexion
              this.router.navigate(['/home']);

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
    localStorage.removeItem('token');
    sessionStorage.clear();
    window.location.href = '/home';
    console.log("User logged out");
  }

}


