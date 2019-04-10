package cl.dani.ms.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class AspectLogger {
	
	@Around("@within(Loggeable) || @annotation(Loggeable)")
	public Object loggeable(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature methodSignature  = (MethodSignature) proceedingJoinPoint.getSignature();
		Method method = methodSignature.getMethod();
		
		Map<String, String> values = ContextService.headers.get();
		String trackingId = (values == null) ? UUID.randomUUID().toString() : values.get(ContextService.TRACKING_ID);
		String className = method.getDeclaringClass().getCanonicalName();
		String methodName = method.getName();
		
		String arguments = this.getArguments(proceedingJoinPoint, method);
		
		log.info("[{}] [{}] [{}] [---] [BCI_INI] inicio {}", trackingId, className, methodName, arguments);
		
		Object result = null;
		try {
			result = proceedingJoinPoint.proceed();
		}
		catch (Exception e) {
			Throwable t = e;
			if (e instanceof InvocationTargetException) {
				t = e.getCause();
			}
			log.error("[{}] [{}] [{}] [---] [BCI_FINEX] [{}] error con mensaje: {}", trackingId, className, methodName, t.getClass().getSimpleName(), t.getMessage(), t);
			throw t;
		}
		
		if (method.getReturnType().equals(Void.TYPE)) {
			log.info("[{}] [{}] [{}] [---] [BCI_FINOK]", trackingId, className, methodName);
		} else {
			log.info("[{}] [{}] [{}] [---] [BCI_FINOK] retornando objeto {}", trackingId, className, methodName, result);
		}

		return result;
	}
	
	private String getArguments(ProceedingJoinPoint proceedingJoinPoint, Method signMethod) 
			throws NoSuchMethodException, SecurityException {
		
		Object[] args = proceedingJoinPoint.getArgs();
		
		Class<?>[] signParameterTypes = signMethod.getParameterTypes();
		Method method = proceedingJoinPoint.getTarget().getClass().getMethod(signMethod.getName(), 
				signParameterTypes);
		
		// Esto obtiene los nombres de los parametros de un metodo
		ParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
		String[] names = discoverer.getParameterNames(method);
		
		return IntStream.range(0, args.length)
				.mapToObj((i) -> {
					Parameter param = method.getParameters()[i];
					String paramValue = checkAnnotationByType(param) ? "": "[" + args[i] + "]";
					return String.format("%s%s", (names != null ? names[i] : param.getName()), paramValue);
				}).collect(Collectors.joining(", "));
	}	
	
	private boolean checkAnnotationByType(Parameter parameter) {
		return parameter.isAnnotationPresent(NotLog.class);
	}
	
}
