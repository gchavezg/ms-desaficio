package cl.ms.configuration;


import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfiguration {

	/**
	 * Crea el bean de dozer mapper para ser usado en el microservicio
	 * @return Nueva instancia del Dozer mapper
	 */
	@Bean
	public DozerBeanMapper dozerBeanMapper() {
		return new DozerBeanMapper();
	}
	
}
