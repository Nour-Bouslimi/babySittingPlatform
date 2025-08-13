import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {User} from "../models/user";
import {Observable} from "rxjs";
import {UserRole} from "../models/UserRole";
import {AuthService} from "./auth.service";

@Injectable({providedIn: 'root'})

export class UserService {

  constructor(private http:HttpClient,private authService:AuthService) {

  }

  private baseUrl = 'http://localhost:8081/user';

  // Create a new user without image
  createUser(user: User) {

    return this.http.post<User>(`${this.baseUrl}/addUser`, user);
  }

  //create a new Parent with image
  createParentWithImage(user: User, image: File) {
    const formData: FormData = new FormData();
    formData.append('user', JSON.stringify(user));
    formData.append('file', image); // Match backend @RequestPart("file")
    return this.http.post<{ data: User }>(`${this.baseUrl}/addUserWithImage`, formData, { observe: 'response' });
  }
  //create a new Nounou with his 4 images
  /*createNounouWithImages(user: User, photo: File, imgIdent1: File, imgIdent2: File, imgEtude: File) {
    console.log('user:', user);
    console.log('photo:', photo);
    console.log('imgIdent1:', imgIdent1);
    console.log('imgIdent2:', imgIdent2);
    console.log('imgEtude:', imgEtude);
    const formData:FormData=new FormData();
    formData.append('user', JSON.stringify(user));
    formData.append('photo', photo);
    formData.append('imgIdent1', imgIdent1);
    formData.append('imgIdent2', imgIdent2);
    formData.append('imgEtude', imgEtude);
    return this.http.post<User>(`${this.baseUrl}/addNounouWithImages`, formData, { observe: 'body' });

  }*/

  createNounouWithImages(user: User, photo: File, imgIdent1: File, imgIdent2: File, imgEtude: File) {
    console.log('user:', user);
    console.log('photo:', photo);
    console.log('imgIdent1:', imgIdent1);
    console.log('imgIdent2:', imgIdent2);
    console.log('imgEtude:', imgEtude);
    const formData: FormData = new FormData();
    formData.append('user', new Blob([JSON.stringify(user)], { type: 'application/json' })); // Use Blob for better compatibility
    formData.append('photo', photo);
    formData.append('imgIdent1', imgIdent1);
    formData.append('imgIdent2', imgIdent2);
    formData.append('imgEtude', imgEtude);


    return this.http.post<User>(`${this.baseUrl}/addNounouWithImages`, formData, { observe: 'body' });
  }
  // Update user without image
  updateUser(idUser: number,user: User) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.put<User>(`${this.baseUrl}/updateUser/${idUser}`, user, { headers });
  }

  //update user with image
  updateUserWithImage(idUser: number, file: File) {
    const formData: FormData = new FormData();
    formData.append('file', file);
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.put<String>(`${this.baseUrl}/saveImageForUser/${idUser}`, formData, { headers });
  }

    // Get all users
    getAllUsers() {
      // Add authorization header if needed
      const headers = this.authService.createAuthorization();
    return this.http.get<User[]>(`${this.baseUrl}/getAllUser`, { headers });
    }

    // Get user by ID
  getUserById(id: number) {
    console.log('📞 getUserById called with id:', id);

    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    console.log('Headers object:', headers);
    console.log('Authorization header exists:', headers.has('Authorization'));
    console.log('Authorization header value:', headers.get('Authorization')?.substring(0, 30) + '...');

    return this.http.get<User>(`${this.baseUrl}/getUserById/${id}`, { headers });
  }

    //get user by email
    getUserByEmail(email: string) {
      // Add authorization header if needed
      const headers = this.authService.createAuthorization();
    return this.http.get<User>(`${this.baseUrl}/getUserByEmail/${email}`, { headers });
    }

    //get user by cin
    getUserByCin(cin: string) {
      // Add authorization header if needed
      const headers = this.authService.createAuthorization();
    return this.http.get<User>(`${this.baseUrl}/getUserByCin/${cin}`, { headers });
    }

    //get user by role
  getUsersByRole(role: string) {
    // Add authorization header if needed
    const headers = this.authService.createAuthorization();
    return this.http.get<User[]>(`${this.baseUrl}/getUsersByRole/${role}`, { headers });
  }

    // Delete user by ID
    deleteUser(id: number) {
      // Add authorization header if needed
      const headers = this.authService.createAuthorization();
    return this.http.delete<void>(`${this.baseUrl}/deleteUser/${id}`, { headers });
    }

    //sending new password to user email
    sendNewPassword(email: string) {

    return this.http.get(`${this.baseUrl}/forgotPassword/${email}`, { responseType: 'text', observe: 'response' });
    }
    //update user password
    updateUserPassword(id: number,currentPassword:string, newPassword: string) {
    const body = {
      currentPassword,
      newPassword
    };
      // Add authorization header if needed
      const headers = this.authService.createAuthorization();
      return this.http.put< User >(`${this.baseUrl}/updateUserPassword/${id}`, body,  { headers });
    }
    // Block user by ID
    blockUser(id: number) {
      // Add authorization header if needed
      const headers = this.authService.createAuthorization();
    return this.http.put(`${this.baseUrl}/blockUser/${id}`, { responseType: 'text', observe: 'response' }, { headers });
    }
    // Unblock user by ID
    unblockUser(id: number) {
      // Add authorization header if needed
      const headers = this.authService.createAuthorization();
    return this.http.put(`${this.baseUrl}/unblockUser/${id}`, { responseType: 'text', observe: 'response' }, { headers });
    }
    //methode de géolocalisation des nounou les plus proche d'une adresse de parent
   getNounousByLocation(parentCin:string):Observable<User[] | string> {
     // Add authorization header if needed
     const headers = this.authService.createAuthorization();
    return this.http.get<User[] |string>(`${this.baseUrl}/nearby-nannies/${parentCin}`, { headers });
    }







}
