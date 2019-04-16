package cl.ms.service;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;

import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ms.dto.LoginRequestDTO;
import cl.ms.dto.UsuarioDTO;
import cl.ms.dto.UsuarioResponseDTO;
import cl.ms.exception.ErrorCredencialesException;
import cl.ms.exception.ErrorNegocioException;
import cl.ms.model.PhonesModel;
import cl.ms.model.UsuarioModel;
import cl.ms.repository.UsuarioRepository;
import cl.ms.security.JwtGenerator;

/**
 * Servicio de gestion de informacion del usuario
 * @author Gerald
 */
@Service
public class UsuServiceImpl implements UsuService,InitializingBean {
	
	/**
	 * Herramienta para realizar mapeos de variables entre instancias
	 */
	@Autowired
	private org.dozer.DozerBeanMapper dozerBeanMapper;

	@Autowired
    private JwtGenerator jwtGenerator;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UsuarioResponseDTO login(LoginRequestDTO loginReq) {
		UsuarioModel usuModelExist = usuarioRepository.findByEmail(loginReq.getCorreo());
		if(usuModelExist == null) {
			throw new ErrorNegocioException("Usuario o contraseña inválidos");
		}
		if (!usuModelExist.getPassword().equals(loginReq.getContrasena())) {
			throw new ErrorCredencialesException(" Usuario o contraseña inválidos ");
		}
		
		String token =jwtGenerator.generateToken(usuModelExist);
		usuModelExist.setLast_login(LocalDateTime.now());
		usuarioRepository.save(usuModelExist);
		
		UsuarioResponseDTO res= UsuarioResponseDTO.builder().build();
		res.setId(usuModelExist.getUsuCod().toString());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		res.setCreated(usuModelExist.getCreated().format(formatter));
		res.setToken(token);
		res.setLast_login(usuModelExist.getLast_login().format(formatter));
		
		res.setModified(usuModelExist.getModified().format(formatter));
		return res;
	}
	@Override
	public UsuarioResponseDTO addRegistro(UsuarioDTO usuReq) {
		UsuarioModel usuModelExist = usuarioRepository.findByEmail(usuReq.getEmail());

		if(usuModelExist != null) {
			throw new ErrorNegocioException("El correo ya registrado");
		}

		UsuarioModel usuarioMod = new UsuarioModel();

		dozerBeanMapper.map(usuReq, usuarioMod);
		usuarioMod.setCreated(LocalDateTime.now());
		usuarioMod.setLast_login(LocalDateTime.now());
		usuarioMod.setModified(LocalDateTime.now());
		usuarioRepository.save(usuarioMod);
		
		UsuarioModel usuModel = usuarioRepository.findByEmail(usuReq.getEmail());

		String token =jwtGenerator.generateToken(usuModel);
				
		UsuarioResponseDTO res= UsuarioResponseDTO.builder().build();
		res.setId(usuModel.getUsuCod().toString());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		res.setCreated(usuModel.getCreated().format(formatter));
		res.setLast_login(usuModel.getLast_login().format(formatter));
		res.setToken(token);
		res.setModified(usuModel.getModified().format(formatter));
		return res;
	}

	/**
	 * see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() {
		// Agrega mapper
		BeanMappingBuilder builder = new BeanMappingBuilder() {
			protected void configure() {
				mapping(UsuarioDTO.class,UsuarioModel.class);
			}
		};
		dozerBeanMapper.addMapping(builder);
	} 
	
	
}
