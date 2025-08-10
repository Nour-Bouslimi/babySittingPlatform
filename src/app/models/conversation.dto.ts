export interface ConversationDTO {
  otherUserId: number;
  lastMessage: string;
  date: string;
  firstName: string;
  lastName: string;
  photo: string;
  nom?: string;      //pour concaténation du nom complet
}
