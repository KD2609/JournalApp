package net.edigest.JournalAppNewest.Api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@Data
public class WeatherResponse {

    private Current current;


    @Data
    public static class Current {

        private int temperature;

        @JsonProperty("weather_descriptions")
        private ArrayList<String> weatherDescriptions;


        private int feelslike;

    }
}