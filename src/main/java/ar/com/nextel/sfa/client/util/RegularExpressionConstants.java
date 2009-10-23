package ar.com.nextel.sfa.client.util;

public class RegularExpressionConstants {

	public static final String fecha = "^([0-9]{1,2}|[0-9]{1,2}\\/|[0-9]{1,2}\\/[0-9]{1,2}|[0-9]{1,2}\\/[0-9]{1,2}\\/|[0-9]{1,2}\\/[0-9]{1,2}\\/[0-9]{0,4})$";

	/** valida que sea del tipo ##-########-# */
	public static final String cuilCuit = "^([0-9]{0,2}|[0-9]{2,2}-|[0-9]{2,2}-[0-9]{0,8}|[0-9]{2,2}-[0-9]{8,8}-|[0-9]{2,2}-[0-9]{8,8}-[0-9]{0,1})$";

	public static final String dni = "[0-9]{0,8}";

	public static final String documentoOtros = "[0-9]{0,15}";

	/** importe de 8 digitos y 2 decimales */
	public static final String importe = "^([0-9]{0,7}|[0-9]{0,7}\\,|[0-9]{0,7}\\,[0-9]{0,2})$";

	public static final String caracter = "[^0-9]";

	public static final String letras = "[A-Za-z]+";
	
	/** Numeros enteros infinito */
	public static final String numeros = "[0-9]*";
	
	/** Numeros enteros infinito y caracter punto */
	public static final String numerosYPunto = "[0-9|\\.]*";

	/** Ingresos brutos*/
	public static final String iibb = "[0-9|\\-]*";
	
	/** Dirección de eMail */
	public static final String email = "^(?:[a-zA-Z0-9_\'^&amp;/+-])+(?:\\.(?:[a-zA-Z0-9_\'^&amp;/+-])+)*@(?:(?:\\[?(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))\\.){3}(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\]?)|(?:[a-zA-Z0-9-]+\\.)+(?:[a-zA-Z]){2,}\\.?)$";
	
	/** Validacion sencilla para controlar email mientras se escribe */
	public static final String lazyEmail = "(?:[\\.a-zA-Z0-9_\'^&amp;/+-]+)*@?(?:[\\.a-zA-Z0-9_\'^&amp;/+-]+)*";
	
	/** Permite ingresar una numeros hasta una cierta cantidad */
	public static final String getNumerosLimitado(int cant) {
		return "[0-9]{0," + cant + "}";
	}
	
	/** Permite ingresar una numeros y caracteres hasta una cierta cantidad */
	public static final String getNumerosYLetrasLimitado(int cant) {
		return "[0-9a-zA-Z]{0," + cant + "}";
	}

	public static final String getCantCaracteres(int cant) {
		return ".{0," + cant + "}";
	}
	/** true si el codigo vantive es vancuc */
	public static final boolean isVancuc(String codigo_vantive) {
		return codigo_vantive.contains("VANCUC");
	}
}
