package cl.ms.service;

import cl.ms.dto.relacion.crear.CrearUsuarioRequestDTO;
import cl.ms.dto.relacion.crear.CrearUsuarioResponseDTO;

public interface UsuarioService {

	public CrearUsuarioResponseDTO crearUsuario(CrearUsuarioRequestDTO crearRelacion);
}
