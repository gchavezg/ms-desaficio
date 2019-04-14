package cl.ms.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ms.dto.login.LoginRequestDTO;
import cl.ms.dto.relacion.crear.CrearUsuarioRequestDTO;
import cl.ms.dto.relacion.crear.CrearUsuarioResponseDTO;
import cl.ms.service.UsuarioService;
import io.swagger.annotations.ApiOperation;

import lombok.extern.slf4j.Slf4j;

/**
 * Clase controlador de endpoints de Microservicio 
 * @author Gerald
 *
 */
@Slf4j
@RestController
@RequestMapping("/ms-desafio")
public class UsuarioController {
		
	/**
	 * Interface de la logica de negocio 
	 */
	@Autowired
	private UsuarioService serviceRelacion;
	
	/**
	 * Login
	 * @param crearUsu credenciales 
	 * @return perfilamiento
	 */
	@CrossOrigin(origins = "*")
	@ApiOperation("Login")
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public CrearUsuarioResponseDTO newLogin(@Valid @RequestBody LoginRequestDTO login){
		return serviceRelacion.crearLogin(login);
	}


}
