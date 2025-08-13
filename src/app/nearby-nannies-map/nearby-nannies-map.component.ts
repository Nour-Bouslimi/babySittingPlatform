import { Component, OnInit } from '@angular/core';
import { UserService } from "../services/user.service";
import { AuthService } from "../services/auth.service";
import { User } from "../models/user";
import * as L from 'leaflet';

const DefaultIcon = L.icon({
  iconRetinaUrl: 'assets/leaflet/marker-icon-2x.png',
  iconUrl: 'assets/leaflet/marker-icon.png',
  shadowUrl: 'assets/leaflet/marker-shadow.png',
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowSize: [41, 41]
});
L.Marker.prototype.options.icon = DefaultIcon;

@Component({
  selector: 'app-nearby-nannies-map',
  templateUrl: './nearby-nannies-map.component.html',
  styleUrls: ['./nearby-nannies-map.component.css']
})
export class NearbyNanniesMapComponent implements OnInit {
  private map!: L.Map;

  constructor(private userService: UserService, private authService: AuthService) {}

  ngOnInit() {
    this.initMap();
    this.loadNanniesMarkers();
  }

  private initMap() {
    this.map = L.map('map').setView([36.8065, 10.1815], 8);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap contributors'
    }).addTo(this.map);
  }

  private loadNanniesMarkers() {
    const userId = this.authService.getUserId();
    this.userService.getUserById(userId).subscribe({
      next: (parent: User) => {
        if (!parent || !parent.cin) {
          console.error('Impossible de récupérer le CIN du parent');
          return;
        }

        this.userService.getNounousByLocation(parent.cin).subscribe({
          next: (nannies: User[] | string) => {
            if (typeof nannies === 'string') {
              console.warn('Message du backend:', nannies);
              return;
            }


            console.log('--- Nounous récupérées du backend ---');
            (nannies as User[]).forEach((nanny, index) => {
              console.log(`Nounou #${index + 1}`, {
                firstName: nanny.firstName,
                lastName: nanny.lastName,
                latitude: nanny.latitude,
                longitude: nanny.longitude,
                address: nanny.address
              });
            });

            const markers: L.Marker[] = [];
            const offset = 0.008; // Décalage pour les marqueurs au même endroit
            const positionCount: { [key: string]: number } = {};

            (nannies as User[]).forEach(nanny => {
              if (nanny.latitude && nanny.longitude) {
                const positionKey = `${nanny.latitude}_${nanny.longitude}`;
                if (!positionCount[positionKey]) positionCount[positionKey] = 0;

                const lat = Number(nanny.latitude) + positionCount[positionKey] * offset;
                const lng = Number(nanny.longitude) + positionCount[positionKey] * offset;

                positionCount[positionKey] += 1;

                const popupContent = `
                  <div style="text-align: center; min-width: 150px;">
                    <h4 style="margin: 5px 0; color: #2c3e50;">${nanny.firstName} ${nanny.lastName}</h4>
                    <p style="margin: 3px 0; font-size: 14px;">📞 ${nanny.phoneNumber || 'Non disponible'}</p>
                    ${nanny.email ? `<p style="margin: 3px 0; font-size: 12px; color: #7f8c8d;">✉️ ${nanny.email}</p>` : ''}
                  </div>
                `;

                const userImageUrl = nanny.photo
                  ? `http://localhost:8081/user/displayImage/${nanny.photo}`
                  : 'assets/images/default-avatar.png';

                const imageIcon = L.divIcon({
                  className: 'user-avatar-marker',
                  html: `<div class="avatar-container">
                           <img src="${userImageUrl}" alt="${nanny.firstName} ${nanny.lastName}" class="user-avatar-img"
                                onerror="this.src='assets/images/default-avatar.png'">
                         </div>`,
                  iconSize: [39, 39],
                  iconAnchor: [19.5, 39]
                });

                const marker = L.marker([lat, lng], { icon: imageIcon }).addTo(this.map);
                marker.bindPopup(popupContent);

                markers.push(marker);
              } else {
                console.warn('Nounou sans coordonnées:', nanny);
              }
            });

            // Ajuster la vue pour englober tous les marqueurs
            if (markers.length > 0) {
              const group = L.featureGroup(markers);
              this.map.fitBounds(group.getBounds(), { padding: [50, 50], maxZoom: 12 });
              console.log(`${markers.length} nounous ajoutées sur la carte`);
            } else {
              console.warn('Aucune nounou avec des coordonnées valides trouvée');
              this.map.setView([36.8065, 10.1815], 8);
            }
          },
          error: err => console.error('Erreur chargement nounous', err)
        });
      },
      error: err => console.error('Erreur récupération utilisateur', err)
    });
  }
}
