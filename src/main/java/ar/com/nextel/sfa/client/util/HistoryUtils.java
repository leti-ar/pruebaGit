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
	 *            (url?param1=value1&param2=value2#token)
	 * @return solo el token
	 */
	public static String getToken(String token) {
		int begin = token.indexOf('#') + 1;
		return token.substring(begin, token.length());
	}

	/**
	 * @return Map con los parametros como clave y sus correspondientes valores
	 */
	public static Map getParams() {
		return getParams(History.getToken());
	}

	/**
	 * @param historyToken
	 *            (url?param1=value1&param2=value2#token)
	 * @return Map con los parametros como clave y sus correspondientes valores
	 */
	public static Map getParams(String token) {
		Map<String, String> paramMap = new HashMap<String, String>();
		String params = getParamsString(token);
		if (!"".equals(params.trim())) {
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
	 *            (url?param1=value1&param2=value2#token)
	 * @param paramName
	 * @return valor del parametro. Null en caso de no encontrarlo.
	 */
	public static String getParam(String token, String paramName) {
		String params = getParamsString(token);
		if (!"".equals(params.trim())) {
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
	 *            (url?param1=value1&param2=value2#token)
	 * @return String contenido entre '?' y '#'
	 */
	private static String getParamsString(String token) {
		int begin = token.indexOf('?') + 1;
		int end = token.indexOf('#');
		String params = "";
		if (begin > 0) {
			if (end > 0) {
				params = token.substring(begin, end);
			} else {
				params = token.substring(begin, token.length());
			}
		}
		return params;
	}
}
