package cl.dani.ms.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfiguration {
	
	/**
	 * Crea el bean de rest client para ser usado en el microservicio para invocar otros microservicios
	 * @return Nueva instancia del rest client
	 */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
