package br.com.inspectflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class InspectflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(InspectflowApplication.class, args);
	}

}
