package ar.com.nextel.sfa.client.enums;

public enum TipoEmailEnum {
	PERSONAL(Long.valueOf(1),"Personal"), 
	LABORAL(Long.valueOf(2),"Laboral");
	
	private Long tipo;
	private String desc;

	TipoEmailEnum(Long t, String d) {
		tipo = t;
		desc = d;
	}

	public Long getTipo() {
		return tipo;
	}
	public String getDesc() {
		return desc;
	}
}
