package cl.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableAutoConfiguration
@ComponentScan( basePackages={"cl.ms"})
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
		
	}
}
