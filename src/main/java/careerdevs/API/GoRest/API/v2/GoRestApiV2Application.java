package careerdevs.API.GoRest.API.v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GoRestApiV2Application {

	public static void main(String[] args) {
		SpringApplication.run(GoRestApiV2Application.class, args);
	}

	@Bean
	public RestTemplate restTemplate (RestTemplateBuilder builder){


		return builder.build();
	}

}
