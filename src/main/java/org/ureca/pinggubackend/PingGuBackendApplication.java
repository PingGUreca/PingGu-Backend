package org.ureca.pinggubackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PingGuBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PingGuBackendApplication.class, args);
	}

}
