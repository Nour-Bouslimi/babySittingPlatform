import {Component, OnInit} from '@angular/core';
import {ReservationService} from "../services/reservation.service";
import {AuthService} from "../services/auth.service";
import {UserService} from "../services/user.service";
import {forkJoin, map} from "rxjs";
import Swal from "sweetalert2";

@Component({
  selector: 'app-my-reservations',
  templateUrl: './my-reservations.component.html',
  styleUrls: ['./my-reservations.component.css']
})
export class MyReservationsComponent implements OnInit {
  reservations: any[] = [];
   nounou: any;
  constructor(private authService: AuthService, private reservationService: ReservationService,private userService:UserService) {
  }

  ngOnInit(): void {
    const idParent = this.authService.getUserId();

    this.reservationService.getReservationByUserId(idParent).subscribe({
      next: (res) => {
        console.log('📦 Réservations récupérées :', res);

        // Charger les nounous pour chaque réservation
        const reservationRequests = res.map((reservation: any) => {
          const idNounou = reservation.nounou_idUser;

          return this.userService.getUserById(idNounou).pipe(
            // Fusionner la nounou dans la réservation
            map(nounou => ({ ...reservation, nounou }))
          );
        });

        // Exécuter toutes les requêtes en parallèle
        forkJoin(reservationRequests).subscribe({
          next: (reservationsWithNounous) => {
            this.reservations = reservationsWithNounous;
            console.log('✅ Réservations avec nounous :', this.reservations);
          },
          error: (err) => {
            console.error('❌ Erreur lors du chargement des nounous', err);
          }
        });
      },
      error: (err) => {
        console.error('❌ Erreur lors du chargement des réservations', err);
      }
    });
  }

  deleteReservation(id: number) {
    Swal.fire({
      title: 'Delete Confirmation',
      text: `Are you sure you want to delete reservation ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, delete it',
      cancelButtonText: 'No, cancel'
    }).then((result) => {
      if (result.isConfirmed) {
        this.reservationService.deleteReservation(id).subscribe({
          next: () => {
            this.reservations = this.reservations.filter(r => r.idReserv !== id);
            console.log(`🗑️ Reservation ${id} deleted successfully`);
            Swal.fire(
              'Deleted!',
              `Reservation #${id} has been successfully deleted.`,
              'success'
            );
          },
          error: (err) => {
            console.error('❌ Error while deleting reservation', err);
            Swal.fire(
              'Error!',
              'An error occurred while trying to delete the reservation.',
              'error'
            );
          }
        });
      } else if (result.dismiss === Swal.DismissReason.cancel) {
        Swal.fire(
          'Cancelled',
          'The reservation was not deleted.',
          'info'
        );
      }
    });
  }


}



