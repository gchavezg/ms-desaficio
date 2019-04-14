package cl.ms.dto.login;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginRequestDTO {

	private String correo;
	
	private String contraseña;
}
