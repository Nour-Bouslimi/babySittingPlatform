
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { AuthentificationRequest } from "../models/AuthentificationRequest";
import { AuthenticationResponse } from '../models/authentication-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private userPayload: any;
  private jwtHelper: JwtHelperService = new JwtHelperService();
  private baseUrl: string = 'http://localhost:8081/api/auth/login';

  constructor(private http: HttpClient) {
    // Ne pas initialiser userPayload ici
    this.loadUserPayload(); // Charger le payload si un token existe déjà
  }

  private loadUserPayload() {
    const token = this.getToken();
    if (token) {
      try {
        this.userPayload = this.decodedToken();
      } catch (error) {
        console.error('Error decoding token on load:', error);
        this.userPayload = null;
      }
    }
  }

  // Consommer l'API de login
  login(authRequest: AuthentificationRequest) {
    return this.http.post<AuthenticationResponse>(`${this.baseUrl}`, authRequest);
  }

  // Get token from localStorage
  getToken() {
    return localStorage.getItem('token')!;
  }

  // Decoder le token
  decodedToken() {
    const token = this.getToken();
    console.log('Token to decode:', token);
    if (!token) {
      console.warn('No token available for decoding');
      return null;
    }
    try {
      return this.jwtHelper.decodeToken(token);
    } catch (error) {
      console.error('Invalid JWT token:', token, error);
      throw new Error('The inspected token doesn\'t appear to be a JWT. Check to make sure it has three parts and see https://jwt.io for more.');
    }
  }
  createAuthorization() {
    console.log('🔍 createAuthorization() called');

    let authHeader = new HttpHeaders();
    const token = this.getToken();

    console.log('Token from getToken():', !!token);

    if (token) {
      authHeader = authHeader.set('Authorization', 'Bearer ' + token);
      console.log('✅ Authorization header created');
      console.log('Header value preview:', authHeader.get('Authorization')?.substring(0, 30) + '...');
    } else {
      console.log('❌ No token in createAuthorization');
    }
console.log('Final authHeader:', authHeader);
    return authHeader;
  }
  /*createAuthorization() {
    let authHeader = new HttpHeaders();
    const token = this.getToken();
    if (token) {
      authHeader = authHeader.set('Authorization', 'Bearer ' + token);
    }
    return authHeader;
  }*/

  getLoggedUser() {
    return this.userPayload;
  }

  //recuperer id de l'utilisateur connecté
  getUserId(): number {
    const decoded = this.decodedToken();
    if (!decoded?.user?.id) {
      throw new Error("L'utilisateur n'est pas connecté ou le token est invalide.");
    }
    return decoded.user.id;
  }
  isLoggedIn(): boolean {
    const token = localStorage.getItem('token');
    // Tu peux aussi ajouter une vérification de validité si besoin
    return !!token;
  }



}
