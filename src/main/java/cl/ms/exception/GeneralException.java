package cl.ms.exception;

public class GeneralException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Código de la excepción genérica
	 */
	private final String codigo;

	public GeneralException(String codigo) {
		super();
		this.codigo = codigo;
	}

	public GeneralException(String codigo, String message, Throwable cause) {
		super(message, cause);
		this.codigo = codigo;
	}

	public GeneralException(String codigo, String message) {
		super(message);
		this.codigo = codigo;
	}

	public GeneralException(String codigo, Throwable cause) {
		super(cause);
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}
	
}
