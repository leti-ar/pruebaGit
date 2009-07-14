package ar.com.nextel.sfa.client.enums;

public enum TipoEmailEnum {
	PERSONAL(Long.valueOf(1)), 
	LABORAL(Long.valueOf(2));
	
	private Long tipo;

	TipoEmailEnum(Long t) {
		tipo = t;
	}

	public Long getTipo() {
		return tipo;
	}
}
