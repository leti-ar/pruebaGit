package ar.com.nextel.sfa.client.util;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.History;

public class HistoryUtils {

	/**
	 * @return solo el token
	 */
	public static String getToken() {
		return getToken(History.getToken());
	}

	/**
	 * @param historyToken
	 *            (token?param1=value1&param2=value2)
	 * @return solo el token. Null si no tiene;
	 */
	public static String getToken(String historyToken) {
		int end = historyToken.indexOf('?');
		String token = historyToken;
		if (end > 0) {
			token = historyToken.substring(0, end);
		}
		return "".equals(token.trim()) ? token : null;

	}

	/**
	 * @return Map con los parametros como clave y sus correspondientes valores
	 */
	public static Map getParams() {
		return getParams(History.getToken());
	}

	/**
	 * @param historyToken
	 *            (token?param1=value1&param2=value2)
	 * @return Map con los parametros como clave y sus correspondientes valores
	 */
	public static Map getParams(String token) {
		Map<String, String> paramMap = new HashMap<String, String>();
		String params = getParamsString(token);
		if (params != null) {
			String[] splited = params.split("&|=");
			for (int i = 0; i < splited.length; i = i + 2) {
				paramMap.put(splited[i], splited[i + 1]);
			}
		}
		return paramMap;
	}

	/**
	 * @param paramName
	 * @return valor del parametro. Null en caso de no encontrarlo.
	 */
	public static String getParam(String paramName) {
		return getParam(History.getToken(), paramName);
	}

	/**
	 * @param historyToken
	 *            (token?param1=value1&param2=value2)
	 * @param paramName
	 * @return valor del parametro. Null en caso de no encontrarlo.
	 */
	public static String getParam(String token, String paramName) {
		String params = getParamsString(token);
		if (params != null) {
			String[] splited = params.split("&|=");
			for (int i = 0; i < splited.length; i = i + 2) {
				if (splited[i].equals(paramName)) {
					return splited[i + 1];
				}
			}
		}
		return null;
	}

	/**
	 * @param historyToken
	 *            (token?param1=value1&param2=value2)
	 * @return String contenido depues de '?'
	 */
	private static String getParamsString(String token) {
		int begin = token.indexOf('?') + 1;
		String params = "";
		if (begin > 0) {
			params = token.substring(begin, token.length());
		}
		return "".equals(params.trim()) ? null : params;
	}
}
