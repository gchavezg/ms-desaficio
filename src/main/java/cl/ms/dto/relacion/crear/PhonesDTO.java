package cl.ms.dto.relacion.crear;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PhonesDTO {

	@NotBlank
	private String number;

	@NotBlank
	private String citycode;
	
	@NotBlank
	private String contrycode;
}
