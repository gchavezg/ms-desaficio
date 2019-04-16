package cl.ms.exception;

import java.util.List;

import javax.validation.constraints.NotNull;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {
	@NotNull
	@NotEmpty
	private String mensaje;

}
