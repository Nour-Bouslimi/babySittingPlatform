import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Annonce} from "../../models/annonces";
import {AnnoncesService} from "../../services/annonces.service";
import {Route, Router} from "@angular/router";
import Swal from 'sweetalert2';
@Component({
  selector: 'app-create-announce',
  templateUrl: './create-announce.component.html',
  styleUrls: ['./create-announce.component.css']
})
export class CreateAnnounceComponent {

  //controle de saisie
  annonceForm:FormGroup;
  annonce:Annonce= new Annonce();
  message: string = '';
  error: string = '';

  constructor(
    private fb:FormBuilder,
    private annonceService:AnnoncesService,
    private router:Router
  ) {
    this.annonceForm= this.fb.group({
      titre:['',[Validators.required]],
      description: ['', [Validators.required, Validators.minLength(10)]],
      date:[new Date()]
    })
  }




//methode onsubmit elly bch n3aytelha fyl form pour creer une annonce
  onsubmit(){
      if(this.annonceForm.valid){
        this.annonce= this.annonceForm.value;
        this.annonceService.createAnnonce(this.annonce).subscribe(
          {
            next: () => {
              this.error = '';
              this.message = 'Annonce created successfully';
              console.log('Annonce created successfully');
              this.annonceForm.reset();

              // Show success message using SweetAlert2 (popUp)
              Swal.fire({
                title: 'Success',
                text: "Announce added successfully",
                icon: 'success',
                confirmButtonText: 'OK'
              });
              //aprés 3 secondes, rediriger vers la page des annonces
              setTimeout(()=>{
                this.router.navigate(['home/announcements']);
              },3000);
            },
            error:(error) => {
              this.error = 'Error creating annonce: ' ;
              console.log('Error creating annonce: ', error);
              //console.log(error.error.error());
            }
          }
        )
      }
  }





}
