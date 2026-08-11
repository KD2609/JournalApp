package net.edigest.JournalAppNewest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class JournalAppNewestApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context= SpringApplication.run(JournalAppNewestApplication.class, args);
		ConfigurableEnvironment env= context.getEnvironment();
		System.out.println(env.getActiveProfiles()[0]);

	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}



}
