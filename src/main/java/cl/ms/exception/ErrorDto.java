package cl.ms.exception;

import java.util.List;

import javax.validation.constraints.NotNull;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
@Setter
public class ErrorDto {
	@NotNull
	@NotEmpty
	private String mensaje;

}
