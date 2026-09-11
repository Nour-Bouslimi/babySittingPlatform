import {Component, OnInit} from '@angular/core';
import {DisponibiliteService} from "../services/disponibilite.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../services/auth.service";
import Swal from "sweetalert2";
import {Disponibilitie} from "../models/disponibilitie";

@Component({
  selector: 'app-disponibilities',
  templateUrl: './disponibilities.component.html',
  styleUrls: ['./disponibilities.component.css']
})
export class DisponibilitiesComponent implements OnInit{
  disponibiliteForm!: FormGroup;
  constructor(private fb: FormBuilder,private disponibiliteService:DisponibiliteService,private authService:AuthService) { }

  ngOnInit(): void {
    this.disponibiliteForm = this.fb.group({
      date: ['', Validators.required],
      heureDebut: ['', [Validators.required, Validators.min(0), Validators.max(23)]],
      heureFin: ['', [Validators.required, Validators.min(1), Validators.max(24)]],
    });
  }

  onSubmit(): void {
    if (this.disponibiliteForm.invalid) {
      return;
    }

    const userId = this.authService.getUserId(); // ID du nounou connecté
    const { date, heureDebut, heureFin } = this.disponibiliteForm.value;

    if (heureDebut >= heureFin) {
      Swal.fire('Erreur', 'L’heure de début doit être inférieure à l’heure de fin.', 'warning');
      return;
    }

    const disponibilite = new Disponibilitie();
    disponibilite.user_idUser = userId;
    disponibilite.date = date;
    disponibilite.heureDebut = heureDebut;
    disponibilite.heureFin = heureFin;

    this.disponibiliteService.createDisponibility(disponibilite).subscribe({
      next: () => {
        Swal.fire('Success', 'Disponibility added successfully.', 'success');
        this.disponibiliteForm.reset();
      },
      error: () => {
        Swal.fire('Error', 'Can Not add disponibility.', 'error');
      },
    });
  }

}
