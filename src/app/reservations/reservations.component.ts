import { Component } from '@angular/core';
import {ReservationService} from "../services/reservation.service";
import {UserService} from "../services/user.service";
import {AuthService} from "../services/auth.service";
import {forkJoin, map} from "rxjs";
import Swal from "sweetalert2";

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrls: ['./reservations.component.css']
})
export class ReservationsComponent {
  reservations: any[] = [];

  constructor(
    private reservationService: ReservationService,
    private userService: UserService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const idNounou = this.authService.getUserId(); // ID de la nounou connectée

    this.reservationService.getReservationsByNounouId(idNounou).subscribe({
      next: (res) => {
        console.log('📦 Réservations reçues :', res);

        const requests = res.map((reservation: any) => {
          const idParent = reservation.parent_idUser;
          return this.userService.getUserById(idParent).pipe(
            map((parent) => ({ ...reservation, parent }))
          );
        });

        forkJoin(requests).subscribe({
          next: (merged) => {
            this.reservations = merged;
            console.log('📦 Réservations avec infos parents :', merged);
          },
          error: (err) => {
            console.error('❌ Erreur lors du chargement des parents', err);
          }
        });
      },
      error: (err) => {
        console.error('❌ Erreur lors du chargement des réservations', err);
      }
    });
  }

  acceptReservation(id: number) {
    this.reservationService.markReservationAsAccepted(id).subscribe({
      next: () => {
        const res = this.reservations.find(r => r.idReserv === id);
        if (res) {
          res.statut = 'ACCEPTED';
          console.log(`✅ Réservation ${id} acceptée`);
          Swal.fire({
            title: 'Reservation accepted',
            text: `the reservation  has been accepted successfully.`,
            icon: 'success',
            confirmButtonText: 'OK'
          })
        }
      },
      error: (err) => {
        Swal.fire({
          title: 'Error',
          text: `An error occurred while accepting the reservation.`,
          icon: 'error',
          confirmButtonText: 'OK'
        });
        console.error('❌ Erreur lors de l’acceptation', err);
      }
    });
  }

  rejectReservation(id: number) {
    this.reservationService.markReservationAsRejected(id).subscribe({
      next: () => {
        const res = this.reservations.find(r => r.idReserv === id);
        if (res){ res.statut = 'REJECTED';
        console.log(`❌ Réservation ${id} refusée`);
        Swal.fire({
          title: 'Reservation rejected',
          text: `the reservation has been rejected successfully.`,
          icon: 'success',
          confirmButtonText: 'OK'
        })
        }
      },
      error: (err) => {
        Swal.fire({
          title: 'Error',
          text: `An error occurred while rejecting the reservation.`,
          icon: 'error',
          confirmButtonText: 'OK'
        })
        console.error('❌ Erreur lors du refus', err);
      }
    });
  }
}
