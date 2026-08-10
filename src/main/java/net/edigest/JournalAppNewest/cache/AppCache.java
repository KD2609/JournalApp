package net.edigest.JournalAppNewest.cache;

import net.edigest.JournalAppNewest.Api.response.WeatherResponse;
import net.edigest.JournalAppNewest.entity.ConfigJournalAppEntity;
import net.edigest.JournalAppNewest.repo.ConfigJournalAppRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {


    public enum keys{
        WEATHER_API;
    }
    public Map<String,String> appCache;

    @Autowired
    private ConfigJournalAppRepo configJournalAppRepo;

    @PostConstruct
    public void init() {
        appCache = new HashMap<>();
        List<ConfigJournalAppEntity> all = configJournalAppRepo.findAll();
        for (ConfigJournalAppEntity configJournalAppEntity : all) {
            appCache.put(configJournalAppEntity.getApiKey(), configJournalAppEntity.getValue());
        }

        System.out.println("CACHE = " + appCache);
    }
}
