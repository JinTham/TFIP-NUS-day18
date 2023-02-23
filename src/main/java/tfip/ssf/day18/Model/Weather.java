package tfip.ssf.day18.Model;

import java.util.LinkedList;
import java.util.List;

public class Weather {
    private List<String> description = new LinkedList<>();
    private String country;
    private String city;
    private Float latitude;
    private Float longtitude;

    //Constructors
    public Weather(List<String> description, String country, String city, Float latitude, Float longtitude) {
        this.description = description;
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }
    public Weather() {
    }

    //Getters & Setters
    public List<String> getDescription() {
        return description;
    }
    public void setDescription(List<String> description) {
        this.description = description;
    }
    public void addDescription(String description) {
        this.description.add(description);
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public Float getLatitude() {
        return latitude;
    }
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }
    public Float getLongtitude() {
        return longtitude;
    }
    public void setLongtitude(Float longtitude) {
        this.longtitude = longtitude;
    }

}
