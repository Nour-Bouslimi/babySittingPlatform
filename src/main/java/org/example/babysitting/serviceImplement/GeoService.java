package org.example.babysitting.serviceImplement;

import org.example.babysitting.entities.User;
import org.example.babysitting.repository.UserRepo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GeoService {


    public void enrichUserWithCoordinates(User user) throws Exception {
        String address = user.getAddress();
        if (address == null || address.isBlank()) return;

        System.out.println("🔎 Adresse à géocoder: " + address);

        String url = "https://nominatim.openstreetmap.org/search?q=" + URLEncoder.encode(address, "UTF-8") + "&format=json";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "babySittingApp")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONArray results = new JSONArray(response.body());

        if (!results.isEmpty()) {
            JSONObject location = results.getJSONObject(0);
            double lat = location.getDouble("lat");
            double lon = location.getDouble("lon");

            System.out.println("📍 Coordonnées reçues: lat=" + lat + " / lon=" + lon);

            user.setLatitude(lat);
            user.setLongitude(lon);
        } else {
            System.out.println("❌ Aucune localisation trouvée pour: " + address);
        }
    }

}
