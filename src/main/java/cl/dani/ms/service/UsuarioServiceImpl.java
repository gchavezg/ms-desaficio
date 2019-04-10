package cl.dani.ms.service;


import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.dani.ms.dto.relacion.crear.CrearUsuarioDTO;
import cl.dani.ms.dto.relacion.crear.CrearUsuarioResponseDTO;
import cl.dani.ms.util.Loggeable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Loggeable
public class UsuarioServiceImpl implements UsuarioService,InitializingBean {

	/**
	 * Herramienta para realizar mapeos variables entre instancias
	 */
	@Autowired
	private DozerBeanMapper dozerBeanMapper;
	
	
	@Override
	@Loggeable
	public CrearUsuarioResponseDTO crearUsuario(CrearUsuarioDTO crearRelacion) {
		System.out.println("hola");
		
		CrearUsuarioResponseDTO creado = CrearUsuarioResponseDTO.builder().build();
		creado.setMensaje("OK");
		return creado;
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
