package ar.com.nextel.sfa.client.widget;

/**
 * Estructura que sirve para la verificación de una expresion regular y
 * el mensaje que se envía en caso de no cumplir dicha condicion
 * @see VerificacionRegexTextBox
 * @author monczer
 */
public class MensajeRegex {
	private String mensaje;
	private String regexPattern;

	public MensajeRegex(String regexPattern, String mensaje) {
		this.regexPattern = regexPattern;
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

	public String getRegexPattern() {
		return regexPattern;
	}
	
}
