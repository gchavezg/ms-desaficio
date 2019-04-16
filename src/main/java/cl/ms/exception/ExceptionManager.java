package cl.ms.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Clase de definicion ExceptionManager, que maneja excepciones centralizadas
 *
 * @author Everis S.A.
 */
@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionManager {



	@ExceptionHandler(ErrorNegocioException.class)
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public ResponseEntity<ErrorDto> manageErrorNegocioException(ErrorNegocioException errorNegocioException){
		ErrorDto errorDto= ErrorDto.builder()
				.mensaje(errorNegocioException.getMensaje()).build();
		ResponseEntity.BodyBuilder builder = ResponseEntity.status(460);
		return builder.body(errorDto);
	}
	@ExceptionHandler(ErrorCredencialesException.class)
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public ResponseEntity<ErrorDto> manageErrorCredencialesException(ErrorCredencialesException errorNegocioException){
		ErrorDto errorDto= ErrorDto.builder()
				.mensaje(errorNegocioException.getMensaje()).build();
		ResponseEntity.BodyBuilder builder = ResponseEntity.status(401);
		return builder.body(errorDto);
	}

}
