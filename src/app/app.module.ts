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
import {FormsModule} from "@angular/forms";
import { CreateAnnounceComponent } from './announcements/create-announce/create-announce.component';

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
    CreateAnnounceComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
