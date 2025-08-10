import {AfterViewInit, Component, EventEmitter, Inject, OnInit, Output} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AnnoncesService} from "../../services/annonces.service";
import Swal from "sweetalert2";

@Component({
  selector: 'app-update-announce',
  templateUrl: './update-announce.component.html',
  styleUrls: ['./update-announce.component.css']
})
export class UpdateAnnounceComponent implements OnInit  {
  announce: any;
  announceForm: FormGroup;
  error: string = '';
  message: string = '';

  @Output() update = new EventEmitter<any>();

  constructor(
    private announceService: AnnoncesService,
    private formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<UpdateAnnounceComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { annonce: any } // Typage explicite pour data
  ) {
    // Vérifier et extraire les données de manière explicite
    console.log('Raw data injected:', data); // Débogage
    if (!data || !data.annonce) {
      console.error('No valid announcement data provided:', data);
      this.announce = { idAnnonce: null, titre: '', description: '' }; // Valeurs par défaut
    } else {
      this.announce = data.annonce;
      console.log('Processed announce data:', this.announce); // Débogage
    }
    // Initialiser le formulaire avec les données ou des valeurs par défaut
    this.announceForm = this.formBuilder.group({
      idAnnonce: [this.announce.idAnnonce || null, Validators.required],
      titre: [this.announce.titre || '', [Validators.required]],
      description: [this.announce.description || '', [Validators.required, Validators.minLength(10)]]
    });
  }

  ngOnInit() {
    // Pas besoin de réinitialiser ici
  }

  onNoClick() {
    this.dialogRef.close();
  }

  onsubmit() {
    if (this.announceForm.valid) {


      const announceData = {
        idAnnonce: this.announceForm.value.idAnnonce,
        titre: this.announceForm.value.titre,
        description: this.announceForm.value.description,
        date:this.announce.date || new Date(), // Utiliser la date de l'annonce ou la date actuelle
        user_idUser:this.announce.user_idUser


      };
      console.log('Sending update data:', announceData); // Débogage
      this.updateAnnounce(announceData.idAnnonce, announceData);
    }
  }

  updateAnnounce(id: number, announceData: any) {
    this.announceService.updateAnnonce(id, announceData).subscribe({
      next: (a) => {
        this.error = '';
        this.message = 'Annonce updated successfully';
        const updatedAnnounce = this.announceForm.value;
        this.update.emit(updatedAnnounce);
        Swal.fire({
          icon: 'success',
          title: 'Success',
          text: this.message,
          confirmButtonText: 'OK'
        });
        this.dialogRef.close();
      },
      error: (error) => {
        this.error = 'Error updating annonce: ' + error.message;
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: this.error,
          confirmButtonText: 'OK'
        });
      }
    });
  }
}
