package cl.dani.ms.service;


import cl.dani.ms.dto.relacion.crear.CrearUsuarioRequestDTO;
import cl.dani.ms.dto.relacion.crear.CrearUsuarioResponseDTO;

public interface UsuarioService {

	public CrearUsuarioResponseDTO crearUsuario(CrearUsuarioRequestDTO crearRelacion);
}
