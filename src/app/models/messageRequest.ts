export class MessageRequest{
  senderId:number;
  receiverId:number;
  content:string;
  date?:string;

  constructor() {
    this.senderId=0;
    this.receiverId=0;
    this.content='';
    this.date='';
  }
}
