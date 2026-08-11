package net.edigest.JournalAppNewest.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testSendMail() {
        redisTemplate.opsForValue().set("email", "vipul@email.com");

        Object email = redisTemplate.opsForValue().get("salary");

        System.out.println(email);
    }
}
