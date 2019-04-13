package cl.ms.unit.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(	basePackages={
	"cl.dani.ms.service", 
	"cl.dani.ms.client", 
	"cl.dani.ms.repository"
})
public class ServiceTestConfiguration {

}
