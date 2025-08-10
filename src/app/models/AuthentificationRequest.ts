export interface AuthentificationRequest{
  email:string;
  password:string;
  tokenType?: string; // Optional, used for token type in some cases
  User?:any; // Optional, used for user information in some cases
}
