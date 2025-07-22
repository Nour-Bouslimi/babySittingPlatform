import {Component, OnInit} from '@angular/core';
import {UserService} from "../services/user.service";
import {User} from "../models/user";

@Component({
  selector: 'app-nannies',
  templateUrl: './nannies.component.html',
  styleUrls: ['./nannies.component.css']
})
export class NanniesComponent implements OnInit{
constructor(private userService: UserService) {

  }

  // Set to keep track of flipped card indexes
  flippedCardIndexes: Set<number> = new Set();

  flipCard(index: number) {
    this.flippedCardIndexes.add(index);
  }

  unflipCard(index: number) {
    this.flippedCardIndexes.delete(index);
  }
  toggleFlip(index: number): void {
    if (this.flippedCardIndexes.has(index)) {
      this.flippedCardIndexes.delete(index); // remettre la carte à l'endroit
    } else {
      this.flippedCardIndexes.add(index); // retourner la carte
    }
  }

  // Message to display in the component
message: string = '';
  public nannies: User[] = [];
  filteredNannies: User[] = [];

  getNannies() {
    this.userService.getUsersByRole('NOUNOU').subscribe({
      next: (users) => {
        this.nannies = this.filteredNannies = users;
        console.log('Nannies:', this.nannies);
      },
      error: (error) => {
        console.error('Error fetching nannies:', error);
      }
    });
  }

ngOnInit() {
  this.getNannies();
}
  onImgError(event: any) {
    event.target.src = '/assets/img/default-image.jpg'; // Fallback image
    console.log('Image load failed for:', event.target.src);
  }

}
