package cl.ms.service;

import cl.ms.dto.LoginRequestDTO;
import cl.ms.dto.UsuarioDTO;
import cl.ms.dto.UsuarioResponseDTO;

/**
 * Interfas del servicio de gestion de informacion del usuario
 * @author Everis S.A. 
 */
public interface UsuService {

	public UsuarioResponseDTO login(LoginRequestDTO loginReq);

	public UsuarioResponseDTO addRegistro(UsuarioDTO usuReq);
	
	
}
