package cl.dani.ms.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.dani.ms.dto.relacion.crear.CrearUsuarioDTO;
import cl.dani.ms.service.UsuarioService;
import io.swagger.annotations.ApiOperation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/Prueba-servicio")
public class UsuarioController {
		
	@Autowired
	private UsuarioService serviceRelacion;

	@CrossOrigin(origins = "*")
	@ApiOperation("Realiza la creacion Usuario")
	@PostMapping(value = "/prueba", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void crearUsuario(@Valid @RequestBody CrearUsuarioDTO crearUsu){
		serviceRelacion.crearUsuario(crearUsu);
	}


}
