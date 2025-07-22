export class Annonce{
  idAnnonce: number;
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
