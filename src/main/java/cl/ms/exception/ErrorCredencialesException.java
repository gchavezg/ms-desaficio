package cl.ms.exception;

/**
 * Excepcion lanzada cuando sucede un error de negocio
 * 
 * @author everis
 *
 */
public class ErrorCredencialesException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	

    public ErrorCredencialesException(String mensaje){
    	super(mensaje);
    }
    
    public String getMensaje() {
    	return getMessage();
    }
}
