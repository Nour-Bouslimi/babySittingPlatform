import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Annonce} from "../models/annonces";
import {AuthService} from "./auth.service";


@Injectable({
  providedIn: 'root'
})
//consommer les api de backend here
// This service can be used to manage annonces, such as fetching, creating, updating, and deleting annonces.

export class AnnoncesService {

  constructor(private http:HttpClient,private authService:AuthService) { }
private baseUrl = 'http://localhost:8081/annonces';

  //create annonce
  createAnnonce(annonce:Annonce){
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.post<Annonce>(`${this.baseUrl}/addAnnonce`, annonce, { headers });
  }

  //get all annonces
  getAllAnnonces() {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.get<Annonce[]>(`${this.baseUrl}/getAllAnnonces`, { headers });
  }
  //get annonce by id
  getAnnonceById(id: number) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.get<Annonce>(`${this.baseUrl}/getAnnonceById/${id}`, { headers });
  }
  //update annonce
  updateAnnonce(id:number,annonce: Annonce) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.put<Annonce>(`${this.baseUrl}/updateAnnonce/${id}`, annonce, { headers });
  }
  //delete annonce
  deleteAnnonce(id:number){
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.delete(`${this.baseUrl}/deleteAnnonce/${id}`, { headers });
  }

}
