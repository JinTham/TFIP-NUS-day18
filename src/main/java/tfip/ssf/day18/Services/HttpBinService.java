package tfip.ssf.day18.Services;

import java.io.StringReader;

import javax.print.attribute.standard.Media;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;

@Service
public class HttpBinService {

    //Normal GET request
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

    //Get request with param
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

    //Post request as form
    public void post(String name, String email) {
        //Create the <form>
        MultiValueMap<String,String> form = new LinkedMultiValueMap<>();
        form.set("name",name);
        form.set("email",email);

        //POST /post
        //Content-Type: application/x-www-form-urlencoded
        //Accept: application/json
        RequestEntity<MultiValueMap<String,String>> req = RequestEntity.post("http://httpbin.org/post")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            //.header("Content-Type","application/x-www-form-urlencoded")
            .accept(MediaType.APPLICATION_JSON)
            //.header("Accept","application/json")
            .body(form, MultiValueMap.class);
        
        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.exchange(req, String.class);

        System.out.printf("Status code: %d\n", resp.getStatusCodeValue());

        String payload = resp.getBody();

        System.out.printf("Payload: %s\n", payload);
    }

    //Post request as Json object
    public void postAsJson(String name, String email) {
        //Json object
        JsonObject json = Json.createObjectBuilder()
            .add("name",name)
            .add("email",email)
            .build();

        RequestEntity<String> req = RequestEntity.post("http://httpbin.org/post")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(json.toString(), String.class);

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.exchange(req, String.class);
    
        System.out.printf("Status code: %d\n", resp.getStatusCodeValue());
    
        String payload = resp.getBody();
    
        System.out.printf("Payload: %s\n", payload);
    }

}
