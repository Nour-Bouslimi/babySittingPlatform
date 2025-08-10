import {Component, OnInit} from '@angular/core';
import {AnnoncesService} from "../services/annonces.service";
import {PageEvent} from "@angular/material/paginator";
import Swal from "sweetalert2";
import {timeout} from "rxjs";
import {UpdateAnnounceComponent} from "./update-announce/update-announce.component";
import {MatDialog} from "@angular/material/dialog";
import {AuthService} from "../services/auth.service";
import {UserService} from "../services/user.service";

@Component({
  selector: 'app-announcements',
  templateUrl: './announcements.component.html',
  styleUrls: ['./announcements.component.css']
})
export class AnnouncementsComponent implements OnInit{
constructor(private annoncesService: AnnoncesService,public dialog:MatDialog,private authService:AuthService,private userService:UserService) {

}
message:string='';
public annonces:any=[];
filteredAnnonces:any[] = [];
  isShowingAll: boolean = false; // Track if all announcements are shown
  loggedIn: boolean = false;
  // Pagination properties
  currentPage: number = 1;
  itemsPerPage: number = 3; // Number of announcements per page
  totalPages: number = 1;
  currentUserId: number | null = null;

  getAnnonces() {
    this.annoncesService.getAllAnnonces().subscribe({
      next: (response) => {
        this.message = "success";
        this.annonces = response;
        // Pour chaque annonce, récupérer l'utilisateur et ajouter son adresse
        this.annonces.forEach((a: any) => {
          this.userService.getUserById(a.user_idUser).subscribe({
            next: (user: any) => {
              a.userAdresse = user.address ? user.address : "Adresse indisponible"; // Check if address exists
            },
            error: (err) => {
              console.error(`Erreur lors de la récupération de l'utilisateur ${a.user_idUser}`, err);
              a.userAdresse = "Adresse indisponible";
            }
          });
        });
        this.updatePagination();
      },
      error: (err) => {
        console.error('Erreur lors du chargement des annonces', err);
      }
    });
  }

  // Update paginated announcements
  updatePagination() {
    this.totalPages = Math.ceil(this.annonces.length / this.itemsPerPage);
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    this.filteredAnnonces = this.annonces.slice(startIndex, endIndex);
  }
  // Navigate to a specific page
  goToPage(page: number) {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      this.isShowingAll = false;
      this.updatePagination();
    }
  }
// Method to show all announcements
  viewAllAnnonces() {
    this.filteredAnnonces = [...this.annonces]; // Show all announcements
    this.isShowingAll = true; // Update the state
    this.currentPage = 1; // Reset to the first page
  }
// Navigate to the previous page
  previousPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.isShowingAll = false;
      this.updatePagination();
    }
  }
  // Navigate to the next page
  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.isShowingAll = false;
      this.updatePagination();
    }
  }

  // Generate array of page numbers
  getPageNumbers(): number[] {
    return Array.from({ length: this.totalPages }, (_, i) => i + 1);
  }
//delete annonce
  deleteAnnonce(id: number | undefined) {
    if (id === undefined || id === null) {
      console.error('Invalid ID provided for deletion');
      return;
    }

    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#dd3333',
      confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
      if (result.isConfirmed) {
        this.annoncesService.deleteAnnonce(id).subscribe({
          next: (data) => {
            Swal.fire('Deleted!', 'Your announcement has been deleted.', 'success');
            // Remove the deleted item from the annonces array
            this.annonces = this.annonces.filter((annonce: any) => annonce.idAnnonce !== id);
            // Update pagination without refetching
            this.updatePagination();
            this.ngOnInit();

          },
          error: (err) => {
            console.error('Error deleting announcement:', err);
            Swal.fire('Error!', 'Failed to delete the announcement.', 'error');
            timeout(3000);
          }
        });
      }
    });
  }
  //initially, show only the first 3 announcements
  ngOnInit() {
    this.getAnnonces();
    //verifier si user est connecté pour hide btn update et delete
    this.loggedIn = this.authService.isLoggedIn();
    this.currentUserId = this.authService.getUserId();
  }

  // dialog for update annonce
  selectedAnnonce: any;
  openDialog(annonce: any) {
    if (!annonce || !annonce.idAnnonce) {
      console.error('Invalid annonce object passed to dialog:', annonce);
      return; // Arrêter si les données sont invalides
    }
    console.log('Opening dialog with annonce:', annonce); // Déjà présent
    this.selectedAnnonce = annonce;
    const dialogRef = this.dialog.open(UpdateAnnounceComponent, {
     width: 'auto',

      panelClass: 'custom-dialog-container', // Custom class for styling
      disableClose: true, // Prevent closing the dialog by clicking outside

      data: { annonce: annonce },


    });

    dialogRef.componentInstance.update.subscribe((updateAnnounce: any) => {
      console.log('Updated announce received:', updateAnnounce);
      const index = this.annonces.findIndex((item: any) => item.idAnnonce === updateAnnounce.idAnnonce);
      if (index !== -1) {
        this.annonces[index].titre = updateAnnounce.titre;
        this.annonces[index].description = updateAnnounce.description;
        this.filteredAnnonces = [...this.annonces];
      }
    });
    this.updatePagination();
  }

  /*Method to check if the user is logged in
  isLoggedIn(): boolean {
    return !!localStorage.getItem('token'); // Check if token exists in local storage
  }*/
}
