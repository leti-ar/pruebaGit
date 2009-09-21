package ar.com.nextel.sfa.client.enums;

public enum TipoTelefonoEnum {
	PARTICULAR(5,"Particular"), 
	PRINCIPAL (1,"Principal"), 
	FAX       (3,"Fax"), 
	CELULAR   (4,"Celular"), 
	ADICIONAL (6,"Adicional");
	private long tipo;
	private String desc;
	TipoTelefonoEnum(long t,String d) {
		tipo=t;
		desc=d;
	}
	public long getTipo() {
		return tipo;
	}
	public String getDesc() {
		return desc;
	}
}
