package cl.dani.ms.dto.relacion.crear;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CrearUsuarioDTO {
	
	/**
	 * Identificador del usuario
	 */
	@NotBlank
	private String nombre;
}
	
