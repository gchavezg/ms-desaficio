package cl.ms.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@ControllerAdvice
public class ExceptionManager {

    @ExceptionHandler(UnsupportedOperationException.class)
    private void manageUnsupportedOperationException(){
        //To do: Create some object to encapsulate data about the errors to add to the Microservice response
    }
    
}
