package cl.dani.ms.service;


import cl.dani.ms.dto.relacion.crear.CrearUsuarioDTO;
import cl.dani.ms.dto.relacion.crear.CrearUsuarioResponseDTO;

public interface UsuarioService {

	public CrearUsuarioResponseDTO crearUsuario(CrearUsuarioDTO crearRelacion);
}
