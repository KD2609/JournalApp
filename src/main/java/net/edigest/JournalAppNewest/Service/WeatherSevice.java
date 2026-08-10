package net.edigest.JournalAppNewest.Service;

import net.edigest.JournalAppNewest.Api.response.WeatherResponse;
import net.edigest.JournalAppNewest.Constants.Placeholders;
import net.edigest.JournalAppNewest.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherSevice {

        @Value("${weather.api.key}")
        private String apiKey;

        @Autowired
        private AppCache appCache;

        @Autowired
        private RestTemplate restTemplate;

        public WeatherResponse getWeather(String city){
            String replace = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.CITY, city).replace(Placeholders.API_KEY, apiKey);


            ResponseEntity<WeatherResponse> response =
                    restTemplate.exchange(replace, HttpMethod.GET, null, WeatherResponse.class);


            WeatherResponse weatherResponse = response.getBody();


            System.out.println("URL = " + replace);
            System.out.println("RESPONSE = " + response.getBody());

            return weatherResponse;

        }
}
