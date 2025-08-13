import {Component, OnInit} from '@angular/core';
import {User} from "../models/user";
import {UserService} from "../services/user.service";
import {Annonce} from "../models/annonces";
import {AnnoncesService} from "../services/annonces.service";
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  topNannies: User[] = [];
  announcements: Annonce[] = [];

  constructor(private userService: UserService,private authService:AuthService,private AnnouncementService:AnnoncesService) {}

  ngOnInit(): void {
    if(this.authService.isLoggedIn()) {
      this.userService.getUsersByRole('NOUNOU').subscribe((users: User[]) => {
        this.topNannies = users.slice(0, 3); // Prendre seulement les 3 premiers
      });
      this.AnnouncementService.getAllAnnonces().subscribe((announcements: Annonce[]) => {
        this.announcements = announcements.slice(0, 4); // Prendre seulement les 3 premiers
      });
    }
  }

}
