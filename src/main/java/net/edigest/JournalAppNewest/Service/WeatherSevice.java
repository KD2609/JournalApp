package net.edigest.JournalAppNewest.Service;

import net.edigest.JournalAppNewest.Api.response.WeatherResponse;
import net.edigest.JournalAppNewest.Constants.Placeholders;
import net.edigest.JournalAppNewest.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) {

        String key = "Weather_of_" + city;

        // First check Redis
        WeatherResponse weatherResponse =
                redisService.get(key, WeatherResponse.class);

        if (weatherResponse != null) {
            return weatherResponse;
        }

        // If not found in Redis, call Weather API
        String url = appCache.appCache
                .get(AppCache.keys.WEATHER_API.toString())
                .replace(Placeholders.CITY, city)
                .replace(Placeholders.API_KEY, apiKey);

        ResponseEntity<WeatherResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        WeatherResponse.class
                );

        WeatherResponse actualResponse = response.getBody();

        // Save response in Redis
        if (actualResponse != null) {
            redisService.set(key, actualResponse, 3000L);
        }

        return actualResponse;
    }
}