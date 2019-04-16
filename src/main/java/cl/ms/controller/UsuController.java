package cl.ms.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ms.dto.UsuarioDTO;
import cl.ms.dto.UsuarioResponseDTO;
import cl.ms.model.PhonesModel;
import cl.ms.model.UsuarioModel;
import cl.ms.repository.UsuarioRepository;
import cl.ms.service.UsuService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/** 
 * 
 * Clase controlador de endpoints de Microservicio 
 * 
 *@author: BCI
 */
@Slf4j
@RestController
@RequestMapping("/ms-desafio")
public class UsuController{

	/**
	 * Interface de la logica de negocio 
	 */
	@Autowired
    private UsuService service;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	public UsuController(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
		UsuarioModel usuarioMod = new UsuarioModel();
		usuarioMod.setEmail("gerald@gmail.com");
		usuarioMod.setName("gerald chavez");
		usuarioMod.setPassword("gerald");
		List<PhonesModel> phones = new ArrayList<PhonesModel>();
		PhonesModel phone = new PhonesModel();
		phone.setCitycode("1");
		phone.setContrycode("57");
		phone.setNumber("1234567");
		phones.add(phone);
		usuarioMod.setPhones(phones);
		usuarioMod.setCreated(LocalDateTime.now());
		usuarioMod.setLast_login(LocalDateTime.now());
		usuarioMod.setModified(LocalDateTime.now());
		this.usuarioRepository.save(usuarioMod);
	}
	
	@ApiOperation("")
	@PostMapping(value = "/registro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public UsuarioResponseDTO addRegistro(@Valid @RequestBody UsuarioDTO usuReq) {
		return service.addRegistro(usuReq);
	}
}
