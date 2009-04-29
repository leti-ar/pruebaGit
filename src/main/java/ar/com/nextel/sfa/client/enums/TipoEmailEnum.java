package ar.com.nextel.sfa.client.enums;

public enum TipoEmailEnum {
	PERSONAL(1), 
	LABORAL (2);
	private long tipo;
	TipoEmailEnum(long t) {
		tipo=t;
	}
	public long getTipo() {
		return tipo;
	}
}
