export class Message  {

  idMsg?:number;
  content:string;
  date?:string;
  receiver_id:number;
  sender_id:number;
  constructor() {
    this.content='';
    this.date='';
    this.receiver_id=0;
    this.sender_id=0;
  }

}
