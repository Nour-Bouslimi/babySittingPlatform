import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HeaderfooterComponent} from "./headerfooter/headerfooter.component";
import {HomeComponent} from "./home/home.component";
import {LoginComponent} from "./login/login.component";
import {AboutComponent} from "./about/about.component";
import {DisponibilitiesComponent} from "./disponibilities/disponibilities.component";
import {ReservationsComponent} from "./reservations/reservations.component";
import {AnnouncementsComponent} from "./announcements/announcements.component";
import {NanniesComponent} from "./nannies/nannies.component";
import {ProfileComponent} from "./profile/profile.component";
import {SignupComponent} from "./signup/signup.component";
import {SignupParentComponent} from "./signup-parent/signup-parent.component";
import {SignupNounouComponent} from "./signup-nounou/signup-nounou.component";
import {ForgotPassComponent} from "./forgot-pass/forgot-pass.component";
import {ProfileNounouComponent} from "./profile-nounou/profile-nounou.component";
import {CreateAnnounceComponent} from "./announcements/create-announce/create-announce.component";
import {UpdateAnnounceComponent} from "./announcements/update-announce/update-announce.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {ForbidenComponent} from "./forbiden/forbiden.component";
import {MyReservationsComponent} from "./my-reservations/my-reservations.component";
import {ChatComponent} from "./chat/chat.component";
import {ChatListComponent} from "./chat-list/chat-list.component";
import {NearbyNanniesMapComponent} from "./nearby-nannies-map/nearby-nannies-map.component";
import {CallComponent} from "./call/call.component";

const routes: Routes = [
  {path:'home',component:HeaderfooterComponent,
  children:[
    {path:'',component:HomeComponent},
    {path:'about',component:AboutComponent},
    {path:'disponibilities',component:DisponibilitiesComponent},
    {path:'reservations',component:ReservationsComponent},
    {path:'myReservations',component:MyReservationsComponent},
    {path:'announcements',component:AnnouncementsComponent},
    {path:'createAnnounce',component:CreateAnnounceComponent},
    {path:'updateAnnounce',component:UpdateAnnounceComponent},
    {path:'nannies',component:NanniesComponent},
    {path:'nearestNannies',component:NearbyNanniesMapComponent},
    {path:'profile',component:ProfileComponent},
    {path:'profileNounou',component:ProfileNounouComponent},

    {
      path: 'chat/:receiverId',
      component: ChatComponent
    },
    {
      path: 'chat-list',
      component: ChatListComponent
    },
    {
      path: 'call/:id',
      component: CallComponent
    }



  ]},
  {path:'login',component:LoginComponent},
  {path:'signup',component:SignupComponent},
  {path:'signupParent',component:SignupParentComponent},
  {path:'signupNounou',component:SignupNounouComponent},
  {path:'forgotPass',component:ForgotPassComponent},
  {path:'dashboard',component:DashboardComponent},
  {path:'forbiden',component:ForbidenComponent},
  {path:'',redirectTo:'/home',pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
