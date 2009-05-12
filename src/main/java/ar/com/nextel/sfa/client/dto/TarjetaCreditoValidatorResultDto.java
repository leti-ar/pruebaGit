package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TarjetaCreditoValidatorResultDto implements IsSerializable {
	private static String SERVICE_ERROR;
	private static String TARJETA_OK;
	private static String TARJETA_ERR;
    private String message;
    private Boolean isValid;
	public static String getSERVICE_ERROR() {
		return SERVICE_ERROR;
	}
	public static void setSERVICE_ERROR(String service_error) {
		SERVICE_ERROR = service_error;
	}
	public static String getTARJETA_OK() {
		return TARJETA_OK;
	}
	public static void setTARJETA_OK(String tarjeta_ok) {
		TARJETA_OK = tarjeta_ok;
	}
	public static String getTARJETA_ERR() {
		return TARJETA_ERR;
	}
	public static void setTARJETA_ERR(String tarjeta_err) {
		TARJETA_ERR = tarjeta_err;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean getIsValid() {
		return isValid;
	}
	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
 }

