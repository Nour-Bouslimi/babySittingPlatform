import {StatutEnum} from "./statutEnum";

export class Reservation{
  idReserv?: number;
  date?: string;
  heureDebut: number;
  heureFin: number;
  statut: StatutEnum;
  parent:number
  nounou:number;

  constructor() {
    //this.idReserv = 0;
    this.date = '';
    this.heureDebut = 0;
    this.heureFin = 0;
    this.statut = StatutEnum.PENDING; // Default status
    this.parent = 0;
    this.nounou = 0;
  }
}
