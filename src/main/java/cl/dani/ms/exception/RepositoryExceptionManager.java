package cl.dani.ms.exception;

import java.lang.reflect.InvocationTargetException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RepositoryExceptionManager {

	/**
	 * Codigo de error para cuando
	 */
	public static final String GENERAL_CODE = "1000";
	
	/**
	 * Mensaje de error en caso de problemas al acceder a los repositorios.
	 */
	public static final String GENERAL_ERROR_MESSAGE = "Se ha producido un problema interno. Reintente más tarde.";
	
	@Around("execution (* cl.dani.ms.repository.*.*(..))")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		
        Object ret = null;
        try {
            ret = joinPoint.proceed();
        } catch (InvocationTargetException e) {
        	Throwable t = e.getCause();
            throw new GeneralException(GENERAL_CODE, GENERAL_ERROR_MESSAGE, t);
        } catch (Exception e) {
        	Throwable t = e;
            throw new GeneralException(GENERAL_CODE, GENERAL_ERROR_MESSAGE, t);
        }
        return ret;
		
	}
	
}
