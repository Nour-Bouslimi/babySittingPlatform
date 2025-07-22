import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {User} from "../models/user";
import {Observable} from "rxjs";
import {UserRole} from "../models/UserRole";

@Injectable({providedIn: 'root'})

export class UserService {

  constructor(private http:HttpClient) {
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
    return this.http.put<User>(`${this.baseUrl}/updateUse/${idUser}`, user);
  }

  //update user with image
  updateUserWithImage(idUser: number, image: File) {
    const formData: FormData = new FormData();
    formData.append('image', image);
    return this.http.put<User>(`${this.baseUrl}/saveImageForUser/${idUser}`, formData);
  }

    // Get all users
    getAllUsers() {
    return this.http.get<User[]>(`${this.baseUrl}/getAllUser`);
    }

    // Get user by ID
    getUserById(id: number) {
    return this.http.get<User>(`${this.baseUrl}/getUserById/${id}`);
    }

    //get user by email
    getUserByEmail(email: string) {
    return this.http.get<User>(`${this.baseUrl}/getUserByEmail/${email}`);
    }

    //get user by cin
    getUserByCin(cin: string) {
    return this.http.get<User>(`${this.baseUrl}/getUserByCin/${cin}`);
    }

    //get user by role
  getUsersByRole(role: string) {
    return this.http.get<User[]>(`${this.baseUrl}/getUsersByRole/${role}`);
  }

    // Delete user by ID
    deleteUser(id: number) {
    return this.http.delete<void>(`${this.baseUrl}/deleteUser/${id}`);
    }

    //sending new password to user email
    sendNewPassword(email: string) {
    return this.http.get(`${this.baseUrl}/forgotPassword/${email}`, { responseType: 'text', observe: 'response' });
    }
    //update user password
    updateUserPassword(id: number,currentPassword:string, newPassword: string) {
      return this.http.put<{ data: User }>(`${this.baseUrl}/updateUserPassword/${id}`, {currentPassword, newPassword}, { observe: 'response' });
    }
    // Block user by ID
    blockUser(id: number) {
    return this.http.put(`${this.baseUrl}/blockUser/${id}`, { responseType: 'text', observe: 'response' });
    }
    // Unblock user by ID
    unblockUser(id: number) {
    return this.http.put(`${this.baseUrl}/unblockUser/${id}`, { responseType: 'text', observe: 'response' });
    }
    //methode de géolocalisation des nounou les plus proche d'une adresse de parent
   getNounousByLocation(parentCin:string):Observable<User[] | string> {
    return this.http.get<User[] |string>(`${this.baseUrl}/nearby-nannies/${parentCin}`);
    }







}
