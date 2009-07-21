package ar.com.nextel.sfa.client.validator;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class GwtValidationUtils {

	public static final String DEFAULT_DATE_SEPARATOR = "/";
	private static final String SOME_NON_USASCII_CHARS = " 'áéíóúÁÉÍÓÚñÑàèìòùÀÈÌÒÙâêîôûÂÊÎÔÛäëïöüÄËÏÖÜÃãÕõÇç";
	private static final String[] BOOLEAN_VALUES = { "0", "1", "si", "no", "true", "false" };
	private static List<String> booleanValue;

	static {
		booleanValue = Arrays.asList(BOOLEAN_VALUES);
	}

	public static boolean validateRequired(String text) {
		return text != null && !text.equals("");
	}

	public static boolean validateRequired(Object obj) {
		return obj != null;
	}

	public static boolean validateEmpty(String text) {
		return !validateRequired(text);
	}

	public static boolean validateEquals(String text, Object value) {
		return text.equals(value.toString());
	}

	public static boolean validateNotEquals(String text, Object value) {
		return !validateEquals(text, value);
	}

	public static boolean validateMinLength(String text, int length) {
		return !(text.length() < length);
	}

	public static boolean validateMaxLength(String text, int length) {
		return !(text.length() > length);
	}

	public static boolean validateLength(String text, int length) {
		return text.length() == length;
	}

	public static boolean validateInteger(String text) {
		try {
			Integer.parseInt(text);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean validateLong(String text) {
		try {
			Long.parseLong(text);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean validateFloat(String text) {
		try {
			Float.parseFloat(text);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean validateBoolean(String text) {
		text = text.trim().toLowerCase();
		if (booleanValue.contains(text)) {
			return true;
		}
		return false;
	}

	public static boolean validateNumeric(String text) {
		if (validateInteger(text)) {
			return true;
		} else if (validateLong(text)) {
			return true;
		} else if (validateFloat(text)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validateNumeric(char c) {
		return (c >= 48 && c <= 57) || (c >= 96 && c <= 122);
	}

	public static boolean validateAlphabetic(char c) {
		return (c >= 65 && c <= 90) /* ||/( c >= 97 && c <= 122 ) */;
	}

	public static boolean validateAlphabetic(String text) {
		char[] chars = text.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (!Character.isLetter(chars[i]) && !SOME_NON_USASCII_CHARS.contains("" + chars[i])) {
				return false;
			}
		}
		return true;
	}

	public static boolean validateAlphaNumeric(String text) {
		char[] chars = text.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (!Character.isLetter(chars[i]) && !Character.isDigit(chars[i])
					&& !SOME_NON_USASCII_CHARS.contains("" + chars[i])) {
				return false;
			}
		}
		return true;
	}

	public static int getInt(String text) {
		try {
			return Integer.parseInt(text);
		} catch (Exception e) {
			return -1;
		}
	}

	public static long getLong(String text) {
		try {
			return Long.parseLong(text);
		} catch (Exception e) {
			return -1;
		}
	}

	public static double getDouble(String text) {
		try {
			return Double.parseDouble(text);
		} catch (Exception e) {
			return -1;
		}
	}

	public static boolean validateSmaller(String text, int value) {
		return getInt(text) < value;
	}

	public static boolean validateSmallerOrEqual(String text, int value) {
		return getInt(text) <= value;
	}

	public static boolean validateGreater(String text, int value) {
		return getInt(text) > value;
	}

	public static boolean validateGreaterOrEqual(String text, int value) {
		return getInt(text) >= value;
	}

	public static boolean validateSmaller(String text, double value) {
		return getDouble(text) < value;
	}

	public static boolean validateSmallerOrEqual(String text, double value) {
		return getDouble(text) <= value;
	}

	public static boolean validateGreater(String text, double value) {
		return getDouble(text) > value;
	}

	public static boolean validateGreaterOrEqual(String text, double value) {
		return getDouble(text) >= value;
	}

	public static boolean validateDay(String text) {
		int n = getInt(text);
		return n > 0 && n < 32;
	}

	public static boolean validateMonth(String text) {
		int n = getInt(text);
		return n > 0 && n < 13;
	}

	public static boolean validateYear(String text) {
		return getInt(text) > 1899;
	}

	public static boolean validateYearToday(String text) {
		return getInt(text) == new Date().getYear() + 1900;
	}

	public static boolean validateYearSmallerOrEqual(String text, int year) {
		return getInt(text) <= year;
	}

	public static boolean validateYearTodaySmallerOrEqual(String text) {
		return getInt(text) <= new Date().getYear() + 1900;
	}

	private static String[] getDateArray(String text, String separator) {
		if (text.indexOf(separator) == -1) {
			return null;
		}
		return text.split(separator);
	}

	public static Date getDate(String text, String separator) {
		String[] splitArray = getDateArray(text, separator);
		if (splitArray == null) {
			return null;
		}

		return new Date(getInt(splitArray[2]) - 1900, getInt(splitArray[1]) - 1, getInt(splitArray[0]));
	}

	public static String getDate(Date date, String separator) {
		int dayInt = date.getDate();
		int monthInt = date.getMonth() + 1;
		String day;
		String month;

		// format day
		if (dayInt < 10) {
			day = "0" + dayInt;
		} else {
			day = dayInt + "";
		}

		// format month
		if (monthInt < 10) {
			month = "0" + monthInt;
		} else {
			month = monthInt + "";
		}

		return new String(day + separator + month + separator + (date.getYear() + 1900));
	}

	public static String getDate(Date date) {
		return GwtValidationUtils.getDate(date, DEFAULT_DATE_SEPARATOR);
	}

	public static boolean validateDate(String text) {
		return validateDate(text, DEFAULT_DATE_SEPARATOR);
	}

	public static boolean validateDate(String text, String separator) {
		String[] splitArray = getDateArray(text, separator);

		if (splitArray == null || splitArray.length != 3) {
			return false;
		}

		return validateDate(splitArray);
	}

	private static boolean validateDate(String[] array) {
		return validateNumeric(array[0]) && validateNumeric(array[1]) && validateNumeric(array[2])
				&& validateDay(array[0]) && validateMonth(array[1]) && validateYear(array[2])
				&& isCorrectDate(getInt(array[0]), getInt(array[1]), getInt(array[2]));
	}

	private static boolean isCorrectDate(int day, int month, int year) {
		boolean bis;
		// vemos a�o bisiesto
		if (month == 2) {
			// 28 o 29 dias, segun anio bisiesto
			if ((year % 4) == 0) {
				if ((year % 100) == 0 && (year % 400) != 0) {
					bis = false;
				} else {
					bis = true;
				}

				// no bisiesto
			} else {
				bis = false;
			}
			if (!bis) {
				if (day == 29) {
					return false;
				} else {
					if (day > 29) {
						return false;
					}
				}
			} else {
				if (day > 29) {
					return false;
				}
			}
		}

		if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10)
				|| (month == 12)) {
			if (day > 31) {
				return false;
			}
		}

		if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
			if (day > 30) {
				return false;
			}
		}

		return true;
	}

	public static boolean validateDateSmaller(String text, String separator, Date date) {
		if (!validateDate(text, separator)) {
			return false;
		}
		Date inputDate = getDate(text, separator);
		if (inputDate == null) {
			return false;
		}

		if (inputDate.compareTo(date) < 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validateDateSmallerOrEquals(String text, String separator, Date date) {
		if (!validateDate(text, separator)) {
			return false;
		}
		Date inputDate = getDate(text, separator);
		if (inputDate == null) {
			return false;
		}

		if (inputDate.compareTo(date) <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validateDateGreater(String text, String separator, Date date) {
		if (!validateDate(text, separator)) {
			return false;
		}
		Date inputDate = getDate(text, separator);
		if (inputDate == null) {
			return false;
		}

		if (inputDate.compareTo(date) > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validateDateGreaterOrEquals(String text, String separator, Date date) {
		if (!validateDate(text, separator)) {
			return false;
		}
		Date inputDate = getDate(text, separator);
		if (inputDate == null) {
			return false;
		}

		if (inputDate.compareTo(date) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validateDateSmallerToday(String text, String separator) {
		if (!validateDate(text, separator)) {
			return false;
		}
		Date inputDate = getDate(text, separator);
		if (inputDate == null) {
			return false;
		}

		Date today = new Date();

		return compare(inputDate, today, SMALLER);
	}

	public static boolean validateDateSmallerOrEqualToday(String text, String separator) {
		if (!validateDate(text, separator)) {
			return false;
		}
		Date inputDate = getDate(text, separator);
		if (inputDate == null) {
			return false;
		}

		Date today = new Date();

		return compare(inputDate, today, SMALLER_EQUAL);
	}

	public static boolean validateDateGreaterToday(String text, String separator) {
		if (!validateDate(text, separator)) {
			return false;
		}
		Date inputDate = getDate(text, separator);
		if (inputDate == null) {
			return false;
		}

		Date today = new Date();

		return compare(inputDate, today, GREATER);
	}

	public static boolean validateDateGreaterOrEqualsToday(String text, String separator) {
		if (!validateDate(text, separator)) {
			return false;
		}
		Date inputDate = getDate(text, separator);
		if (inputDate == null) {
			return false;
		}

		Date today = new Date();

		return compare(inputDate, today, GREATER_EQUAL);
	}

	public static final int EQUAL = 0;
	public static final int GREATER = 1;
	public static final int GREATER_EQUAL = 2;
	public static final int SMALLER = 3;
	public static final int SMALLER_EQUAL = 4;

	/**
	 * Permite comparar 2 fechas usando el tipo de operador indicado.
	 * 
	 * @param date1
	 *            Fecha usanda como LHS.
	 * @param date2
	 *            Fecha usanda como RHS.
	 * @param operator
	 *            Tipo de operacion a realizar entre las 2 fechas. Si no se corresponde con los tipos
	 *            especificados se tira una Exception.
	 * 
	 * @return Boolean que indica el resultado de la comparacion de las 2 fechas.
	 */
	public static boolean compare(Date date1, Date date2, int operator) {
		clearTime(date1);
		clearTime(date2);

		switch (operator) {
		case EQUAL:
			return (date1.compareTo(date2) == 0);
		case GREATER:
			return (date1.compareTo(date2) > 0);
		case GREATER_EQUAL:
			return (date1.compareTo(date2) >= 0);
		case SMALLER:
			return (date1.compareTo(date2) < 0);
		case SMALLER_EQUAL:
			return (date1.compareTo(date2) <= 0);
		default:
			throw new RuntimeException(
					"No se reconoce el tipo de operador para realizar la comparacion. Tipo: " + operator);
		}
	}

	/**
	 * Dada una fecha setea la hora en 00:00:00.000
	 * 
	 * @return La misma instancia de la fecha pasada por parametro pero con la hora en 00:00:00.000
	 */
	public static Date clearTime(Date date) {
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);

		// Lo siguiente es para dejar los millisecons en 0 dado que Date no
		// tiene una forma de setear solo el campo de milliseconds.
		long aux = date.getTime();
		aux /= 1000;
		aux *= 1000;
		date.setTime(aux);

		return date;
	}

	public static boolean validateMail(String text) {
		return text.indexOf(".") != -1 && text.indexOf("@") != -1;
		// return text.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
	}

	public static boolean validateNumericPositive(String text) {
		if (validateIntegerPositive(text)) {
			return true;
		} else if (validateLongPositive(text)) {
			return true;
		} else if (validateFloatPositive(text)) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean validateIntegerPositive(String text) {
		if (validateInteger(text) && validateIntegerGreaterThanZero(text)) {
			return true;
		}
		return false;
	}

	private static boolean validateLongPositive(String text) {
		if (validateLong(text) && validateLongGreaterThanZero(text)) {
			return true;
		}
		return false;
	}

	private static boolean validateFloatPositive(String text) {
		if (validateFloat(text) && validateFloatGreaterThanZero(text)) {
			return true;
		}
		return false;
	}

	private static boolean validateIntegerGreaterThanZero(String text) {
		if (Integer.parseInt(text) > 0) {
			return true;
		}
		return false;
	}

	private static boolean validateLongGreaterThanZero(String text) {
		if (Long.parseLong(text) > 0) {
			return true;
		}
		return false;
	}

	private static boolean validateFloatGreaterThanZero(String text) {
		if (Float.parseFloat(text) > 0) {
			return true;
		}
		return false;
	}

	public static boolean validateNumericPositiveOrZero(String text) {
		if (validateIntegerPositiveOrZero(text)) {
			return true;
		} else if (validateLongPositiveOrZero(text)) {
			return true;
		} else if (validateFloatPositiveOrZero(text)) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean validateIntegerPositiveOrZero(String text) {
		if (validateInteger(text) && validateIntegerGreaterOrEqualThanZero(text)) {
			return true;
		}
		return false;
	}

	private static boolean validateLongPositiveOrZero(String text) {
		if (validateLong(text) && validateLongGreaterOrEqualThanZero(text)) {
			return true;
		}
		return false;
	}

	private static boolean validateFloatPositiveOrZero(String text) {
		if (validateFloat(text) && validateFloatGreaterOrEqualThanZero(text)) {
			return true;
		}
		return false;
	}

	private static boolean validateIntegerGreaterOrEqualThanZero(String text) {
		if (Integer.parseInt(text) >= 0) {
			return true;
		}
		return false;
	}

	private static boolean validateLongGreaterOrEqualThanZero(String text) {
		if (Long.parseLong(text) >= 0) {
			return true;
		}
		return false;
	}

	private static boolean validateFloatGreaterOrEqualThanZero(String text) {
		if (Float.parseFloat(text) >= 0) {
			return true;
		}
		return false;
	}

	public static boolean validateRegEx(String text, String regex) {
		return text.matches(regex);
	}

	/**
	 * Valida un CUIL/CUIT.
	 * 
	 * @param text
	 *            El CUIL/CUIT sin guiones. Ej: 201234567890
	 */
	public static boolean validateCuil(String text) {
		return validarSinGuion(text);
	}

	private static boolean validarSinGuion(String cuit) {
		if (cuit == null || "".equals(cuit) || cuit.length() <= 3) {
			return false;
		}

		String xyStr, dniStr, digitoStr;
		int digitoTmp;
		int n = cuit.length();

		xyStr = cuit.substring(0, 2);
		dniStr = cuit.substring(2, n - 1);
		digitoStr = cuit.substring(n - 1);

		dniStr = dniStr.replaceAll("-", "");
		
		if (xyStr.length() != 2 || dniStr.length() > 8 || digitoStr.length() != 1)
			return false;

		int xyStc;
		int dniStc;

		try {
			xyStc = Integer.parseInt(xyStr);
			dniStc = Integer.parseInt(dniStr);
			digitoTmp = Integer.parseInt(digitoStr);
		} catch (NumberFormatException e) {
			return false;
		}

		if (xyStc != 20 && xyStc != 23 && xyStc != 24 && xyStc != 27 && xyStc != 30 && xyStc != 33
				&& xyStc != 34)
			return false;

		int digitoStc = calcular(xyStc, dniStc);

		if (digitoStc == digitoTmp && xyStc == Integer.parseInt(xyStr))
			return true;

		return false;
	}

	private static int calcular(int xyStc, int dniStc) {
		long tmp1, tmp2;
		long acum = 0;
		int n = 2;
		tmp1 = xyStc * 100000000L + dniStc;

		for (int i = 0; i < 10; i++) {
			tmp2 = tmp1 / 10;
			acum += (tmp1 - tmp2 * 10L) * n;
			tmp1 = tmp2;
			if (n < 7)
				n++;
			else
				n = 2;
		}

		n = (int) (11 - acum % 11);

		if (n == 10) {
			if (xyStc == 20 || xyStc == 27 || xyStc == 24)
				xyStc = 23;
			else
				xyStc = 33;

			/*
			 * No es necesario hacer la llamada recursiva a calcular(), se puede poner el digito en 9 si el
			 * prefijo original era 23 o 33 o poner el dijito en 4 si el prefijo era 27
			 */
			return calcular(xyStc, dniStc);
		} else {
			if (n == 11)
				return 0;
			else
				return n;
		}
	}

	public static boolean validateDateEquals(Date date1, Date date2) {
		return date1.compareTo(date2) == 0;
	}

	public static boolean validateDateFirstLowerThanSecond(Date date1, Date date2) {
		return date1.compareTo(date2) < 0;
	}

	public static boolean validateDateFirstLowerOrEqualThanSecond(Date date1, Date date2) {
		return date1.compareTo(date2) <= 0;
	}

	public static boolean validateDateFirstGreaterThanSecond(Date date1, Date date2) {
		return date1.compareTo(date2) > 0;
	}

	public static boolean validateDateFirstGreaterOrEqualThanSecond(Date date1, Date date2) {
		return date1.compareTo(date2) >= 0;
	}

	/**
	 * Valida que un string tenga al menos numbersCount numeros
	 */
	public static boolean validateWithNumbers(String text, int numbersCount) {
		char[] charArray = text.toLowerCase().toCharArray();
		char c;
		for (int i = 0; i < charArray.length; i++) {
			c = charArray[i];
			if (c >= 48 && c <= 57) {
				numbersCount--;
			}
		}
		return numbersCount <= 0;
	}

	/**
	 * Valida que el texto no tenga espacios en blanco por ningún lado
	 */
	public static boolean validateWithoutSpaces(String text) {
		return text.indexOf(" ") == -1;
	}

}
