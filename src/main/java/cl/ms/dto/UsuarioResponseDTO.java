package cl.ms.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UsuarioResponseDTO {

	private String id;
	
	private String created;
	
	private String modified;
	
	private String last_login;
	
	private String token;
}
