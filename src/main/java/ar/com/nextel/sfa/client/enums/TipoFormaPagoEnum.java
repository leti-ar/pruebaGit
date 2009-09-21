package ar.com.nextel.sfa.client.enums;

public enum TipoFormaPagoEnum {
	EFECTIVO       ("1","Efectivo"), 
	CUENTA_BANCARIA("2","Débito directo-Cuenta Bancaria"), 
	TARJETA_CREDITO("3","Débito directo-Tarjeta de Créd.");
	
	private String tipo;
	private String desc;
	TipoFormaPagoEnum(String t, String  d) {
		tipo=t;
		desc=d;
	}
	public String getTipo() {
		return tipo;
	}
	public String getDesc() {
		return desc;
	}
}
