package cl.dani.ms.configuration;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@Configuration
@SpringBootApplication
@EnableAutoConfiguration
@PropertySource("file:./config/server-config.properties")
@ComponentScan(	basePackages={"cl.dani.ms"})
@Import({
	
	})
@EnableSwagger2
public class Main {
	
	/**
	 * Metodo main del microservicio
	 * @param args argumentos para inicial el microservicio
	 */
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
