package ar.com.nextel.sfa.client.enums;

public enum TipoCuentaEnum {
	CTA("GRAN CUENTA"), 
	DIV("DIVISION"),
	SUS("SUSCRIPTOR");
	private String tipo;
	TipoCuentaEnum(String t) {
		tipo=t;
	}
	public String getTipo() {
		return tipo;
	}
}
