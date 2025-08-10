import {Component, OnInit} from '@angular/core';
import {UserService} from "../services/user.service";
import {User} from "../models/user";
import {Reservation} from "../models/reservation";
import {ReservationService} from "../services/reservation.service";
import {AuthService} from "../services/auth.service";
import Swal from "sweetalert2";
import {Router} from "@angular/router";
import {DisponibiliteService} from "../services/disponibilite.service";
import {Disponibilitie} from "../models/disponibilitie";

declare var bootstrap: any; // Import Bootstrap for modal functionality
@Component({
  selector: 'app-nannies',
  templateUrl: './nannies.component.html',
  styleUrls: ['./nannies.component.css']
})


export class NanniesComponent implements OnInit{

  //reservation
  reservationData: Reservation = new Reservation();
  selectedNounouId: number = 0;
  modalRef: any;
  todayDate: string = '';
  //pour savoir si nounou est dispo ou pas
  nounou:User[]=[];
  today:string='';
  currentHour:number= new Date().getHours();
  //disponibilités nounous
  nounousDisponibilites: { [key: number]: Disponibilitie[] } = {};

  constructor(private userService: UserService,private reservationService:ReservationService,private authService:AuthService,private router:Router,private disponibiliteService:DisponibiliteService) {
    // Initialize today's date in the format YYYY-MM-DD
    const today = new Date();
    this.todayDate = today.toISOString().split('T')[0]; // Format YYYY-MM-DD

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
  searchQuery: string = '';
  selectedSortOption: string = 'firstName';
//disponibilités nounous
  loadDisponibilites(idUser: number) {
    this.disponibiliteService.getDisponibilitiesByUserId(idUser).subscribe(dispos => {
      console.log(`Disponibilités de nounou ${idUser}:`, dispos);
      this.nounousDisponibilites[idUser] = dispos;
    });
  }
  getDisponibilitesFor(idUser: number): Disponibilitie[] {
    return this.nounousDisponibilites[idUser] || [];
  }

  getNannies() {
    this.userService.getUsersByRole('NOUNOU').subscribe({
      next: (users) => {
        this.nannies = this.filteredNannies = users;

        this.nannies.forEach(n => {
          this.loadDisponibilites(n.idUser);
          this.reservationService.getReservationsByNounouId(n.idUser).subscribe(reservations => {
            const todayReservations = reservations.filter(r =>
              r.date === this.todayDate && r.statut === 'ACCEPTED'
            );

            // Détermine s’il y a une réservation actuelle
            const hasCurrentReservation = todayReservations.some(r =>
              this.currentHour >= r.heureDebut && this.currentHour < r.heureFin


          );
            console.log('Current hour:', this.currentHour);
            // ✅ Propriété pour savoir si le nounou est dispo maintenant
            n['isAvailableNow'] = !hasCurrentReservation;

            // ✅ Liste des créneaux réservés pour aujourd'hui
            n['bookedSlotsToday'] = todayReservations.map(r =>
              `${r.heureDebut}h - ${r.heureFin}h`
            );
            console.log(`Nounou ${n.firstName} - Disponible maintenant : ${n['isAvailableNow']}, Créneaux réservés : ${n['bookedSlotsToday'].join(', ')}`);
          });
        });

      },
      error: (error) => {
        console.error('Error fetching nannies:', error);
      }
    });
  }

ngOnInit() {
    // Initialize today's date in the format YYYY-MM-DD
  const today = new Date();
  this.todayDate = today.toISOString().split('T')[0]; // Format YYYY-MM-DD
  this.currentHour = new Date().getHours();
  this.getNannies();

}


  // 🔎 Filtrage dynamique
  filterNannies() {
    const query = this.searchQuery.toLowerCase();
    this.filteredNannies = this.nannies.filter(nanny =>
      nanny.firstName?.toLowerCase().includes(query) ||
      nanny.address?.toLowerCase().includes(query) ||
      nanny.etatCivil?.toLowerCase().includes(query)
    );
  }



  // 🔁 Tri
  sortNannies() {
    const field = this.selectedSortOption as keyof User;
    this.filteredNannies.sort((a, b) => {
      const aValue = a[field]?.toString().toLowerCase() || '';
      const bValue = b[field]?.toString().toLowerCase() || '';
      return aValue.localeCompare(bValue);
    });
  }

// 📝 Réservation
  openReservationModal(nounouId: number) {
    this.selectedNounouId = nounouId;
    this.reservationData = new Reservation();

    const modalElement = document.getElementById('reservationModal');
    this.modalRef = new bootstrap.Modal(modalElement);
    this.modalRef.show();
  }

  submitReservation() {
    const parentId = this.authService.getUserId();
    const { date, heureDebut, heureFin } = this.reservationData;

    // ⚠️ Validation de base
    if (heureDebut >= heureFin) {
      Swal.fire('Invalid Time', 'Start time must be before end time.', 'warning');
      return;
    }
console.log('heureDebut:', heureDebut, 'heureFin:', heureFin);
    // ⚠️ Vérifier que la date est bien sélectionnée
    if (!date) {
      Swal.fire('Invalid Date', 'Please select a valid date for the reservation.', 'warning');
      return;
    }

    // ✅ Formatage de la date (obligatoire pour l’API backend)
    const formattedDate = date.split('T')[0]; // "2025-08-06"
console.log('formattedDate:', formattedDate);
    // Étape 1 : Vérifier que le créneau demandé est dans la DISPONIBILITÉ du nounou
    this.disponibiliteService.getDisponibilitiesByUserIdAndDate(this.selectedNounouId, formattedDate)
      .subscribe(dispos => {
        const isWithinAvailability = dispos.some(d =>
          heureDebut >= d.heureDebut && heureFin <= d.heureFin
        );
console.log('availability:', dispos);
        if (!isWithinAvailability) {
          Swal.fire({
            icon: 'error',
            title: 'Unavailable Time',
            text: 'This nanny is not available during the selected time.',
            confirmButtonText: 'OK'
          });
          return;
        }

        // Étape 2 : Vérifier qu’il n’y a pas déjà une réservation ACCEPTÉE sur ce créneau
        this.reservationService.getReservationsByNounouId(this.selectedNounouId)
          .subscribe(reservations => {
            const hasConflict = reservations.some(r =>
              r.date?.startsWith(formattedDate) &&
              r.statut === 'ACCEPTED' &&
              heureDebut < r.heureFin &&
              heureFin > r.heureDebut
            );

            if (hasConflict) {
              Swal.fire({
                icon: 'error',
                title: 'Already Reserved',
                text: 'This time slot is already booked by another parent.',
                confirmButtonText: 'OK'
              });
              return;
            }

            // ✅ Étape 3 : Créer la réservation si tout est OK
            this.reservationService.createReservation(parentId, this.selectedNounouId, this.reservationData)
              .subscribe({
                next: () => {
                  Swal.fire({
                    icon: 'success',
                    title: 'Reservation Successful',
                    text: 'Your reservation has been successfully submitted. You will be notified when the nanny accepts or rejects your request.',
                    confirmButtonText: 'OK'
                  });
                  this.modalRef.hide();
                  this.router.navigate(['/home/myReservations']);
                },
                error: (err) => {
                  console.error(err);
                  Swal.fire({
                    icon: 'error',
                    title: 'Reservation Failed',
                    text: 'An error occurred while creating your reservation.',
                    confirmButtonText: 'OK'
                  });
                }
              });
          });
      });
  }




  closeModal() {
    if (this.modalRef) {
      this.modalRef.hide();
    }
  }

  //pour ouvrir le chat
  goToChat(nounouId: number) {
    this.router.navigate(['/home/chat', nounouId]);
  }

}
