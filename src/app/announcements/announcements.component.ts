import {Component, OnInit} from '@angular/core';
import {AnnoncesService} from "../services/annonces.service";
import {PageEvent} from "@angular/material/paginator";
import Swal from "sweetalert2";
import {timeout} from "rxjs";

@Component({
  selector: 'app-announcements',
  templateUrl: './announcements.component.html',
  styleUrls: ['./announcements.component.css']
})
export class AnnouncementsComponent implements OnInit{
constructor(private annoncesService: AnnoncesService) {

}
message:string='';
public annonces:any=[];
filteredAnnonces:any[] = [];
  isShowingAll: boolean = false; // Track if all announcements are shown

  // Pagination properties
  currentPage: number = 1;
  itemsPerPage: number = 3; // Number of announcements per page
  totalPages: number = 1;

  getAnnonces() {
    this.annoncesService.getAllAnnonces().subscribe({
      next: (response) => {
        this.message = "success";
        this.annonces = response;
        this.updatePagination();
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
  }

  /*Method to check if the user is logged in
  isLoggedIn(): boolean {
    return !!localStorage.getItem('token'); // Check if token exists in local storage
  }*/
}
