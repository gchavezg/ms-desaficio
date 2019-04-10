package cl.dani.ms.service;


import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.dani.ms.dto.relacion.crear.CrearUsuarioDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService,InitializingBean {

	/**
	 * Herramienta para realizar mapeos variables entre instancias
	 */
	@Autowired
	private DozerBeanMapper dozerBeanMapper;
	
	@Override
	public void crearUsuario(CrearUsuarioDTO crearRelacion) {
		System.out.println("hola");
		
	}


	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() {
		// Agrega mapper
		BeanMappingBuilder builder = new BeanMappingBuilder() {
			protected void configure() {
				
			}
		};
		dozerBeanMapper.addMapping(builder);
	}	
}
