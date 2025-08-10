export class Disponibilitie{
  idDispo?: number;
  date?: string;
  heureDebut: number;
  heureFin: number;
  user_idUser: number;

  constructor() {
    //this.idDispo = 0;
    this.date = '';
    this.heureDebut = 0;
    this.heureFin = 0;
    this.user_idUser = 0; // Default user ID
  }
}
