package com.jatin.smart_appointment_scheduler;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import java.util.TimeZone;
import jakarta.annotation.PostConstruct;

@EnableJpaAuditing
@SpringBootApplication
public class SmartAppointmentSchedulerApplication {

	@PostConstruct
	void setUTCTimeZone() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();

		// Set env vars as system properties
		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

		SpringApplication.run(SmartAppointmentSchedulerApplication.class, args);
	}

}
