package net.edigest.JournalAppNewest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalAppNewestApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context= SpringApplication.run(JournalAppNewestApplication.class, args);
		ConfigurableEnvironment env= context.getEnvironment();
		System.out.println(env.getActiveProfiles()[0]);

	}

}
