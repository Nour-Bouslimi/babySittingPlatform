import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "../services/auth.service";
import {MessageService} from "../services/message.service";
import {MessageRequest} from "../models/messageRequest";
import {UserService} from "../services/user.service";
import Swal from "sweetalert2";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit{
  receiverId!: number;
  senderId!: number;
  messages: any[] = [];

  senderPhoto: string = 'default.png';
  receiverPhoto: string = 'default.png';
  openedMessageId: number | null = null;  // message dont le menu est ouvert
  editingMessageId: number | null = null; // id message en édition
  newMessage: string = '';                 // contenu input message
  constructor(
    private route: ActivatedRoute,
    private authService: AuthService,
    private messageService: MessageService,
    private userService:UserService
  ) {}

  ngOnInit() {
    this.receiverId = Number(this.route.snapshot.paramMap.get('receiverId'));
    this.senderId = this.authService.getUserId();

    if (!this.senderId || isNaN(this.receiverId)) {
      console.error('SenderId ou receiverId invalide.');
      return;
    }

    // Récupérer photo du sender
    this.userService.getUserById(this.senderId).subscribe({
      next: user => {
        this.senderPhoto = user.photo || 'default.png';
        console.log('Photo sender:', this.senderPhoto);
      },
      error: err => {
        console.error('Erreur récupération photo sender:', err);
        this.senderPhoto = 'default.png';
      }
    });

    // Récupérer photo du receiver
    this.userService.getUserById(this.receiverId).subscribe({
      next: user => {
        this.receiverPhoto = user.photo || 'default.png';
        console.log('Photo receiver:', this.receiverPhoto);
      },
      error: err => {
        console.error('Erreur récupération photo receiver:', err);
        this.receiverPhoto = 'default.png';
      }
    });

    this.loadConversation();
  }

  loadConversation() {
    this.messageService.getConversation(this.senderId, this.receiverId)
      .subscribe(data => this.messages = data);
    console.log('senderId:', this.senderId, 'receiverId:', this.receiverId);
    this.messages.forEach(m => console.log('msg sender:', m.sender_id, 'msg receiver:', m.receiver_id));

  }

  sendMessage() {
    if (!this.newMessage.trim()) return;

    if (this.editingMessageId) {
      // Modifier un message existant
      const updatedMsg = {
        ...this.messages.find(m => m.idMsg === this.editingMessageId),
        content: this.newMessage
      };

      this.messageService.updateMessage(this.editingMessageId, updatedMsg).subscribe({
        next: updated => {
          const index = this.messages.findIndex(m => m.idMsg === updated.idMsg);
          if (index !== -1) {
            this.messages[index] = updated;
          }
          this.editingMessageId = null;
          this.newMessage = '';
        },
        error: err => {
          console.error('Erreur mise à jour message', err);
          Swal.fire('Error', 'Can not update message.', 'error');
        }
      });

    } else {
      // Envoyer un nouveau message
      const msg: MessageRequest = {
        senderId: this.senderId,
        receiverId: this.receiverId,
        content: this.newMessage
      };

      this.messageService.sendMessage(msg).subscribe({
        next: sent => {
          this.messages.push(sent);
          this.newMessage = '';
        },
        error: err => {
          console.error('Erreur envoi message', err);
          Swal.fire('Error', 'Can not send message.', 'error');
        }
      });
    }
  }


  toggleOptions(id: number) {
    if (this.openedMessageId === id) {
      this.openedMessageId = null;
    } else {
      this.openedMessageId = id;
    }
  }

// Modifier le message
  onEditMessage(msg: any) {
    this.openedMessageId = null;
    this.editingMessageId = msg.idMsg;
    this.newMessage = msg.content;
  }

// Supprimer le message
  onDeleteMessage(id: number) {
    this.openedMessageId = null;
    Swal.fire({
      title: 'Confirm deletion',
      text: 'Are you sure you want to delete the message ?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, Delete it',
      cancelButtonText: 'Cancel'
    }).then(result => {
      if (result.isConfirmed) {
        this.messageService.deleteMessage(id).subscribe({
          next: () => {
            this.messages = this.messages.filter(m => m.idMsg !== id);
            Swal.fire('Deleted', 'Your message successfully deleted.', 'success');
          },
          error: err => {
            console.error('Erreur suppression message', err);
            Swal.fire('Error', 'Can not delete the message.', 'error');
          }
        });
      }
    });
  }



}
