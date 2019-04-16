package cl.ms.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UsuarioDTO {

	
	private String name;
	
	private String email;
	
	private String password;
	
	private List<PhonesDTO> phones;

}
