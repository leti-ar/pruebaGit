package ar.com.nextel.sfa.client.enums;

public enum EstadoDomicilioEnum {
	ACTIVO   (1L,"Activo"), 
	INACTIVO (2L,"Inactivo");
	private Long id;
	private String desc;
	EstadoDomicilioEnum(Long id,String d) {
		this.id=id;
		this.desc=d;
	}
	public Long getId() {
		return id;
	}
	public String getDesc() {
		return desc;
	}
}
