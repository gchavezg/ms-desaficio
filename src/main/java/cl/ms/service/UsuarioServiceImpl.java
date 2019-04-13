package cl.ms.service;


import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ms.dto.relacion.crear.CrearUsuarioRequestDTO;
import cl.ms.dto.relacion.crear.CrearUsuarioResponseDTO;
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
	public CrearUsuarioResponseDTO crearUsuario(CrearUsuarioRequestDTO crearRelacion) {
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
