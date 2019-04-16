package cl.ms.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;;

/** 
 * Configuracion utilitaria transversal 
 *@author: BCI
 */
@Configuration
public class UtilConfiguration {

	/**
	 * Instancia de dozer
	 * @return Dozer
	 */
	@Bean
	public DozerBeanMapper dozerBeanMapper() {
		return new DozerBeanMapper();
	}
}
