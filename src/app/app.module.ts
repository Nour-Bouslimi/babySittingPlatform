import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderfooterComponent } from './headerfooter/headerfooter.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { AboutComponent } from './about/about.component';
import { DisponibilitiesComponent } from './disponibilities/disponibilities.component';
import { ReservationsComponent } from './reservations/reservations.component';
import { AnnouncementsComponent } from './announcements/announcements.component';
import { NanniesComponent } from './nannies/nannies.component';
import { ProfileComponent } from './profile/profile.component';
import { SignupComponent } from './signup/signup.component';
import { SignupParentComponent } from './signup-parent/signup-parent.component';
import { SignupNounouComponent } from './signup-nounou/signup-nounou.component';
import { ForgotPassComponent } from './forgot-pass/forgot-pass.component';
import { ProfileNounouComponent } from './profile-nounou/profile-nounou.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { CreateAnnounceComponent } from './announcements/create-announce/create-announce.component';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import {MatStepperModule} from "@angular/material/stepper";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatButtonModule} from "@angular/material/button";
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatCardModule} from "@angular/material/card";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatTableModule} from "@angular/material/table";
import {MatDialogModule} from "@angular/material/dialog";
import { UpdateAnnounceComponent } from './announcements/update-announce/update-announce.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ForbidenComponent } from './forbiden/forbiden.component';
import { MyReservationsComponent } from './my-reservations/my-reservations.component';
import { ChatComponent } from './chat/chat.component';
import { ChatListComponent } from './chat-list/chat-list.component';
import { NearbyNanniesMapComponent } from './nearby-nannies-map/nearby-nannies-map.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CallComponent } from './call/call.component';
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HeaderfooterComponent,
    HomeComponent,
    LoginComponent,
    AboutComponent,
    DisponibilitiesComponent,
    ReservationsComponent,
    AnnouncementsComponent,
    NanniesComponent,
    ProfileComponent,
    SignupComponent,
    SignupParentComponent,
    SignupNounouComponent,
    ForgotPassComponent,
    ProfileNounouComponent,
    CreateAnnounceComponent,
    UpdateAnnounceComponent,
    DashboardComponent,
    ForbidenComponent,
    MyReservationsComponent,
    ChatComponent,
    ChatListComponent,
    NearbyNanniesMapComponent,
    CallComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    FontAwesomeModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatSlideToggleModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
    MatStepperModule,
    MatCheckboxModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatProgressSpinnerModule,
    MatCardModule,
    MatPaginatorModule,
    MatTableModule,
    MatDialogModule,

  ],
  providers: [
    HttpClient

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
