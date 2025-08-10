import {Component, OnInit} from '@angular/core';
import {UserService} from "../services/user.service";
import {AuthService} from "../services/auth.service";
import {MessageService} from "../services/message.service";
import {lastValueFrom} from "rxjs";
import {ConversationDTO} from "../models/conversation.dto";

@Component({
  selector: 'app-chat-list',
  templateUrl: './chat-list.component.html',
  styleUrls: ['./chat-list.component.css']
})
export class ChatListComponent  implements  OnInit{
  conversations: ConversationDTO[] = [];
  nounouId!: number;

  constructor(
    private messageService: MessageService,
    private authService: AuthService,
    private userService: UserService
  ) {}
  async ngOnInit() {
    this.nounouId =Number( this.authService.getUserId());
    // Vérification pour éviter d'appeler l'API avec undefined
    if (!this.nounouId || isNaN(this.nounouId)) {
      console.error('❌ ID de l’utilisateur connecté introuvable');
      return;
    }
    await this.loadConversations();
  }


  async loadConversations() {
    try {
      const convs: ConversationDTO[] = await lastValueFrom(
        this.messageService.getAllConversationsForUser(this.nounouId)
      );


      const requests = convs.map(async conv => {
        const otherUserId = conv.otherUserId;


        if (!otherUserId) {
          return {
            ...conv,
            photo: 'assets/default.png',
            nom: 'Utilisateur inconnu'
          };
        }

        try {
          const user = await lastValueFrom(this.userService.getUserById(otherUserId));
          return {
            ...conv,
            photo: user?.photo
              ? `http://localhost:8081/user/displayImage/${user.photo}`
              : 'assets/default.png',
            nom: `${user?.firstName || ''} ${user?.lastName || ''}`.trim()
          };
        } catch (e) {
          console.error(`❌ Erreur récupération user ${otherUserId}`, e);
          return {
            ...conv,
            photo: 'assets/default.png',
            nom: 'Utilisateur inconnu'
          };
        }
      });

      this.conversations = await Promise.all(requests);
    } catch (error) {
      console.error('❌ Erreur récupération conversations', error);
    }
  }










}
