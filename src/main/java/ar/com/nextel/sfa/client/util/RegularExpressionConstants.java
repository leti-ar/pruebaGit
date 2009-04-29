package ar.com.nextel.sfa.client.util;

public class RegularExpressionConstants {

		public static final String fecha = "^([0-9]{1,2}|[0-9]{1,2}\\/|[0-9]{1,2}\\/[0-9]{1,2}|[0-9]{1,2}\\/[0-9]{1,2}\\/|[0-9]{1,2}\\/[0-9]{1,2}\\/[0-9]{0,4})$";

		// valida que sea del tipo ##-########-#
		public static final String cuilCuit = "^([0-9]{0,2}|[0-9]{2,2}-|[0-9]{2,2}-[0-9]{0,8}|[0-9]{2,2}-[0-9]{8,8}-|[0-9]{2,2}-[0-9]{8,8}-[0-9]{0,1})$";

		public static final String dni = "[0-9]{0,8}";

		public static final String documentoOtros = "[0-9]{0,15}";

		// importe de 8 digitos y 2 decimales
		public static final String importe = "^([0-9]{0,7}|[0-9]{0,7}\\,|[0-9]{0,7}\\,[0-9]{0,2})$";

		public static final String caracter = "[^0-9]";
		
		/** Numeros infinito */
		public static final String numeros = "[0-9]*";

		/** Permite ingresar una numeros hasta una cierta cantidad */
		public static final String getNumerosLimitado(int cant) {
			return "[0-9]{0," + cant + "}";
		}

		public static final String getCantCaracteres(int cant) {
			return ".{0," + cant + "}";
		}
}
