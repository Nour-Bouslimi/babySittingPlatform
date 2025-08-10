export class Annonce{
  idAnnonce: number;
  user_idUser: number|undefined; // Assuming this is the ID of the user who created the announcement
  titre: string;
  description: string;
  date:Date;


  constructor() {
    this.idAnnonce = 0;
    this.titre = '';
    this.description = '';
    this.date = new Date();
  }
}
