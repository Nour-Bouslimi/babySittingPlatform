import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Annonce} from "../models/annonces";


@Injectable({
  providedIn: 'root'
})
//consommer les api de backend here
// This service can be used to manage annonces, such as fetching, creating, updating, and deleting annonces.

export class AnnoncesService {

  constructor(private http:HttpClient) { }
private baseUrl = 'http://localhost:8081/annonces';

  //create annonce
  createAnnonce(annonce:Annonce){
    return this.http.post<Annonce>(`${this.baseUrl}/addAnnonce`, annonce);
  }

  //get all annonces
  getAllAnnonces() {
    return this.http.get<Annonce[]>(`${this.baseUrl}/getAllAnnonces`);
  }
  //get annonce by id
  getAnnonceById(id: number) {
    return this.http.get<Annonce>(`${this.baseUrl}/getAnnonceById/${id}`);
  }
  //update annonce
  updateAnnonce(annonce: Annonce) {
    return this.http.put<Annonce>(`${this.baseUrl}/updateAnnonce`, annonce);
  }
  //delete annonce
  deleteAnnonce(id:number){
    return this.http.delete(`${this.baseUrl}/deleteAnnonce/${id}`);
  }

}
