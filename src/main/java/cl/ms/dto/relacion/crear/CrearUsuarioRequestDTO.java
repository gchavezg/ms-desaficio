package cl.ms.dto.relacion.crear;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CrearUsuarioRequestDTO {

	@NotBlank
	private String name;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
	
	@NotEmpty
	@Valid
	private List<PhonesDTO> phones;
	
}
	
