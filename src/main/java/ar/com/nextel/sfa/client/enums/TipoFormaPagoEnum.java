package ar.com.nextel.sfa.client.enums;

public enum TipoFormaPagoEnum {
	EFECTIVO       ("1"), 
	CUENTA_BANCARIA("2"), 
	TARJETA_CREDITO("3");
	
	private String tipo;
	TipoFormaPagoEnum(String t) {
		tipo=t;
	}
	public String getTipo() {
		return tipo;
	}
}
