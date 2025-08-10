import {UserRole} from "./UserRole";

export class User {
  idUser:number;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  phoneNumber: string;
  address: string;
  cin: string;
  genre: string;
  role: UserRole;
  isBlocked:number;
  photo: string;
  dateOfBirth?: string;
  nbChildren: number;
  ageChildren: string;
  etatCivil: string;
  niveauEtude: string;
  experience: string;
  domaineEtude: string;
  imgEtude: string;
  langue: string;
  niveau: string;
  centreInteret: string;
  motorise: string;
  fumer: string;
  imgIdent1: string;
  imgIdent2: string;
  tarifHoraire: number;
  latitude: number;
  longitude: number;


  constructor() {
    this.idUser = 0;
    this.firstName = '';
    this.lastName = '';
    this.email = '';
    this.password = '';
    this.phoneNumber = '';
    this.address = '';
    this.cin = '';
    this.genre = '';
    this.role = UserRole.PARENT; // Default role
    this.isBlocked = 0;
    this.photo = '';
    this.dateOfBirth = "";
    this.nbChildren = 0;
    this.ageChildren = '';
    this.etatCivil = '';
    this.niveauEtude = '';
    this.experience = '';
    this.domaineEtude = '';
    this.imgEtude = '';
    this.langue = '';
    this.niveau = '';
    this.centreInteret = '';
    this.motorise = '';
    this.fumer = '';
    this.imgIdent1 = '';
    this.imgIdent2 = '';
    this.tarifHoraire = 0;
    this.latitude = 0;
    this.longitude = 0;
  }

  [key: string]: any; // <-- autorise l'accès via string
}
