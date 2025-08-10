import {Injectable} from "@angular/core";
import {AuthService} from "./auth.service";
import {HttpClient} from "@angular/common/http";
import {Disponibilitie} from "../models/disponibilitie";


@Injectable({ providedIn: 'root' })
export class DisponibiliteService{

  constructor(private http:HttpClient,private authService:AuthService) { }
  baseUrl = 'http://localhost:8081/disponibility';

  //create a new disponibility
  createDisponibility(disponibility: Disponibilitie) {
    const headers = this.authService.createAuthorization();
    return this.http.post<Disponibilitie>(`${this.baseUrl}/addDisponibility`, disponibility, { headers });
  }
  //delete a disponibility
  deleteDisponibility(id: number) {
    const headers = this.authService.createAuthorization();
    return this.http.delete<void>(`${this.baseUrl}/deleteDisponibility/${id}`, { headers });
  }
  //update a disponibility
  updateDisponibility(id: number, disponibility: Disponibilitie) {
    const headers = this.authService.createAuthorization();
    return this.http.put<Disponibilitie>(`${this.baseUrl}/updateDisponibility/${id}`, disponibility, { headers });
  }
  //get all disponibilities
  getAllDisponibilities() {
    const headers = this.authService.createAuthorization();
    return this.http.get<Disponibilitie[]>(`${this.baseUrl}/getAllDisponibilities`, { headers });
  }
  //get disponibility by id
  getDisponibilityById(id: number) {
    const headers = this.authService.createAuthorization();
    return this.http.get<Disponibilitie>(`${this.baseUrl}/getDisponibilityById/${id}`, { headers });
  }
  //get disponibilities by user id
  getDisponibilitiesByUserId(idUser: number) {
    const headers = this.authService.createAuthorization();
    return this.http.get<Disponibilitie[]>(`${this.baseUrl}/getDisponibilityByUserId/${idUser}`, { headers });
  }
  //get disponibilities by date
  getDisponibilitiesByDate(date: string) {
    const headers = this.authService.createAuthorization();
    return this.http.get<Disponibilitie[]>(`${this.baseUrl}/getDisponibilityByDate/${date}`, { headers });
  }
  //get disponibilities by user id and date
  getDisponibilitiesByUserIdAndDate(userId: number, date: string) {
    const headers = this.authService.createAuthorization();
    return this.http.get<Disponibilitie[]>(`${this.baseUrl}/getDisponibilityByUserIdAndDate/${userId}/${date}`, { headers });
  }
  //get disponibility by time range
  getDisponibilitiesByTimeRange(date: string, heureDebut: number, heureFin: number) {
    const headers = this.authService.createAuthorization();
    return this.http.get<Disponibilitie[]>(`${this.baseUrl}/getDisponibilityByTimeRange/${date}/${heureDebut}/${heureFin}`, { headers });
  }
}
