import {HttpClient} from "@angular/common/http";
import {AuthService} from "./auth.service";
import {Message} from "../models/message";
import {MessageRequest} from "../models/messageRequest";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {ConversationDTO} from "../models/conversation.dto";

@Injectable({
  providedIn: 'root'
})
export class MessageService{

  constructor(private http:HttpClient,private authService:AuthService) {
  }
  baseUrl = 'http://localhost:8081/messages';

  //create a new message

  sendMessage(messageRequest: MessageRequest): Observable<Message> {
    const headers = this.authService.createAuthorization();
    return this.http.post<Message>(`${this.baseUrl}/sendMessage`, messageRequest, { headers });
  }

  // Récupérer la conversation entre 2 utilisateurs
  getConversation(userId1: number, userId2: number): Observable<Message[]> {
    const headers = this.authService.createAuthorization();
    return this.http.get<Message[]>(`${this.baseUrl}/getConversation/${userId1}/${userId2}`, { headers });
  }

//add message
  addMessage(message: Message): Observable<Message> {
    const headers = this.authService.createAuthorization();
    return this.http.post<Message>(`${this.baseUrl}/addMessage`, message, { headers });
  }

  //Ajouter une liste de messages
  addListMessages(messages: Message[]): Observable<Message[]> {
    const headers = this.authService.createAuthorization();
    return this.http.post<Message[]>(`${this.baseUrl}/addListMessages`, messages, { headers });
  }

  // Supprimer un message
  deleteMessage(id: number): Observable<void> {
    const headers = this.authService.createAuthorization();
    return this.http.delete<void>(`${this.baseUrl}/deleteMessage/${id}`, { headers });
  }

  // Modifier un message
  updateMessage(id: number, message: Message): Observable<Message> {
    const headers = this.authService.createAuthorization();
    return this.http.put<Message>(`${this.baseUrl}/updateMessage/${id}`, message, { headers });
  }

  // Récupérer tous les messages
  getAllMessages(): Observable<Message[]> {
    const headers = this.authService.createAuthorization();
    return this.http.get<Message[]>(`${this.baseUrl}/getAllMessages`, { headers });
  }

  //get all messages for a user
  getAllConversationsForUser(userId:number){
    const headers = this.authService.createAuthorization();
    return this.http.get<ConversationDTO[]>(`${this.baseUrl}/getAllConversationForUser/${userId}`, { headers });
  }

  // Récupérer un message par ID
  getMessageById(id: number): Observable<Message> {
    const headers = this.authService.createAuthorization();
    return this.http.get<Message>(`${this.baseUrl}/getMessageById/${id}`, { headers });
  }

//get messages by date
  getMessagesByDate(date: string): Observable<Message[]> {
    const headers = this.authService.createAuthorization();
    return this.http.get<Message[]>(`${this.baseUrl}/getMessagesByDate/${date}`, { headers });
  }



}
