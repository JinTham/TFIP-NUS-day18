package tfip.ssf.day18.Services;

import java.io.StringReader;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class HttpBinService {
    
    public void get(String name, String email) {
        //Create the URL with the properly encoded query string
        //Get /get?name=<name>&email=<email>
        String url = UriComponentsBuilder.fromUriString("http://httpbin.org/get")
            .queryParam("name",name)
            .queryParam("email",email)
            .toUriString();

        System.out.printf("URL: %s\n", url);

        RequestEntity<Void> req = RequestEntity.get(url).build();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.exchange(req, String.class);

        System.out.printf("Status code: %d\n", resp.getStatusCodeValue());

        String payload = resp.getBody();

        System.out.printf("Payload: %s\n", payload);
    }

    public void get() {
        //Creating a GET /get request
        RequestEntity<Void> req = RequestEntity.get("http://httpbin.org/get").build(); 

        //Create a REST template
        RestTemplate template = new RestTemplate();

        //Make the request, the payload of the response will be a String
        ResponseEntity<String> resp = template.exchange(req, String.class);

        //Check the status code
        System.out.printf("Status code: %d\n", resp.getStatusCodeValue());

        //Get the payload
        String payload = resp.getBody();

        System.out.printf("Payload: %s\n", payload);

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject json = reader.readObject();
        JsonObject headers = json.getJsonObject("headers");
        String traceId = headers.getString("X-Amzn-Trace-Id");

        System.out.printf("X-Amzn-Trace-Id: %s\n",traceId);
    }

}
