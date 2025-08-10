import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "./auth.service";
import {Reservation} from "../models/reservation";

@Injectable(
  { providedIn:'root'}
)
export class ReservationService {

  constructor(private http:HttpClient,private authService:AuthService) { }
baseUrl = 'http://localhost:8081/reservations';

  // Create a new reservation
  createReservation(idParent:number,idNounou:number,reservation: Reservation) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.post<Reservation>(`${this.baseUrl}/addReservation/${idParent}/${idNounou}`, reservation, { headers });
  }
  //delete reservation
  deleteReservation(id: number) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.delete<void>(`${this.baseUrl}/deleteReservation/${id}`, { headers });
  }
  //update reservation
  updateReservation(id: number, reservation: Reservation) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.put<Reservation>(`${this.baseUrl}/updateReservation/${id}`, reservation, { headers });
  }
  //mark reservation as accepted
  markReservationAsAccepted(id: number) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.put<void>(`${this.baseUrl}/markReservationAsAccepted/${id}`, {},{ headers });
  }
  //mark reservation as refused
  markReservationAsRejected(id: number) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.put<void>(`${this.baseUrl}/markReservationAsRejected/${id}`, {},{ headers });
  }
  //get all reservations
  getAllReservations() {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.get<Reservation[]>(`${this.baseUrl}/getAllReservations`, { headers });
  }
  //get reservation by id
  getReservationById(id: number) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.get<Reservation>(`${this.baseUrl}/getReservationById/${id}`, { headers });
  }
  //get reservations by parent id
  getReservationByUserId(user_idUser: number) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.get<Reservation[]>(`${this.baseUrl}/getReservationByUserId/${user_idUser}`, { headers });
  }
  //get reservations by nounou id
  getReservationsByNounouId(idNounou: number) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.get<Reservation[]>(`${this.baseUrl}/getReservationByNounouId/${idNounou}`, { headers });
  }
  //get reservations by date
  getReservationsByDate(date: string) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.get<Reservation[]>(`${this.baseUrl}/getReservationByDate/${date}`, { headers });
  }
  //get reservation by time range
  getReservationsByTimeRange(heureDebut: string, heureFin: string) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.get<Reservation[]>(`${this.baseUrl}/getReservationByTimeRange`, { headers });
  }
  //get reservation by statut
  getReservationsByStatut(statut: string) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.get<Reservation[]>(`${this.baseUrl}/getReservationByStatut/${statut}`, { headers });
  }


}
