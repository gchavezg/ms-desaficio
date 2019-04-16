package cl.ms.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cl.ms.dto.LoginRequestDTO;
import cl.ms.dto.UsuarioResponseDTO;
import cl.ms.service.UsuService;
import io.swagger.annotations.ApiOperation;

@RestController
public class TokenController {

	/**
	 * Interface de la logica de negocio 
	 */
	@Autowired
    private UsuService service;
    @ApiOperation("")
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public UsuarioResponseDTO login(@Valid @RequestBody LoginRequestDTO loginReq) {
		return service.login(loginReq);
	}
}
