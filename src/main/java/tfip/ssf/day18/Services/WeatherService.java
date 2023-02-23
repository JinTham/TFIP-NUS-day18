package tfip.ssf.day18.Services;

import java.io.StringReader;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.JsonArray;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import tfip.ssf.day18.Model.Weather;

@Service
public class WeatherService {
    @Value("${weathermap.key}")
    private String apiKey;

    public Optional<Weather> getWeather(String city) {
        //https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
        String url = UriComponentsBuilder.fromUriString("https://api.openweathermap.org/data/2.5/weather")
            .queryParam("q",city)
            .queryParam("appid",apiKey)
            .toUriString();

        System.out.printf("URL: %s\n", url);

        RequestEntity<Void> req = RequestEntity.get(url).accept(MediaType.APPLICATION_JSON).build();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = null;
        String payload = "";
        int statusCode = 500;
        try {
            resp = template.exchange(req, String.class);
            payload = resp.getBody();
            statusCode = resp.getStatusCode().value();
        } catch (HttpClientErrorException ex) {
            payload = ex.getResponseBodyAsString();
            statusCode = ex.getStatusCode().value();
            return Optional.empty();
        } finally {
            System.out.printf("Status code: %d\n", statusCode);
            System.out.printf("Payload: %s\n", payload);
        }

        //Parse the result to Weather class
        Weather weather = new Weather();
        weather.setCity(city);
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject json = reader.readObject();
        JsonObject jo = json.getJsonObject("coord");

        //Get and set longtitude and latitude data
        Float lonFloat = (float) jo.getJsonNumber("lon").doubleValue(); //Get longitude
        weather.setLongtitude(lonFloat);
        Float latFloat = (float) jo.getJsonNumber("lat").doubleValue(); //Get latitude
        weather.setLatitude(latFloat);

        //Read weather data
        JsonArray weatherArray = json.getJsonArray("weather"); // Get weather info
        for (int i=0;i<weatherArray.size();i++) {
            jo = weatherArray.getJsonObject(i);
            String desc = "%s - %s".formatted(jo.getString("main"),jo.getString("description"));
            weather.addDescription(desc);
        }

        //Get and set country data
        jo = json.getJsonObject("sys");
        weather.setCountry(jo.getString("country").toLowerCase());

        return Optional.of(weather);
    }
}
