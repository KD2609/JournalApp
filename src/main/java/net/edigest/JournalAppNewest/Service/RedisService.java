package net.edigest.JournalAppNewest.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T get(String key, Class<T> clazz) {

        try {

            String json = redisTemplate.opsForValue().get(key);

            if (json == null) {
                return null;
            }

            return mapper.readValue(json, clazz);

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    public void set(String key, Object value, Long time) {

        try {

            String json = mapper.writeValueAsString(value);

            redisTemplate.opsForValue()
                    .set(key, json, time, TimeUnit.SECONDS);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}