export class Notification{
  idNotif?:number;
  message:string;
  isRead:boolean;
  date?: string;
  user_idUser: number;
  constructor() {
    //this.idNotif = 0;
    this.message = '';
    this.isRead = false;
    this.date = ''
    this.user_idUser = 0; // Default user ID
  }
}
