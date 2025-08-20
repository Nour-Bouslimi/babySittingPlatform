import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {faPhoneSlash} from "@fortawesome/free-solid-svg-icons";
import {AuthService} from "../services/auth.service";
import {User} from "../models/user";
import {UserService} from "../services/user.service";

@Component({
  selector: 'app-call',
  templateUrl: './call.component.html',
  styleUrls: ['./call.component.css']
})
export class CallComponent implements OnInit{
  userId!:string;
  liveURL!: string;
  audioURL!: string;
  user!:any; // Pour stocker les informations de l'utilisateur
  fullName!: string; // Pour stocker le nom complet de l'utilisateur
 userConnecte!:number;
  constructor(private route: ActivatedRoute,private router:Router,private authService:AuthService,private userService:UserService) {}



  ngOnInit(): void {
    this.userId = this.route.snapshot.paramMap.get('id')!;
    if (!this.userId) {
      console.error('User ID is not provided.');
      return;
    }
    console.log('User ID:', this.userId);
    this.userService.getUserById(Number(this.userId)).subscribe(user => {
      this.user = user;
      this.fullName = user.firstName + ' ' + user.lastName;
    });

    const ip = '192.168.1.11'; //  l'IP de mon téléphone
    const port = 8080;

    this.liveURL = `http://${ip}:${port}/video`; // flux vidéo MJPEG
    this.audioURL = `http://${ip}:${port}/audio`; // flux audio WAV

    // Vérifier si l'utilisateur est connecté
    //this.userConnecte= this.authService.getUserId();

  }

  protected readonly faPhoneSlash = faPhoneSlash;

  endCall() {
    this.router.navigate(['home/chat', this.userId]);
  }






}

