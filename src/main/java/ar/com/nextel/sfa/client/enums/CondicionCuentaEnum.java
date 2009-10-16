package ar.com.nextel.sfa.client.enums;

public enum CondicionCuentaEnum {
	PROSPECT          (1,"PROSPECT"), 
	CUSTOMER          (2,"CUSTOMER"),
	PROSPECT_EN_CARGA (3,"PROSPECT EN CARGA");
	
	private long id;
	private String descripcion;
	CondicionCuentaEnum(long id,String desc) {
		this.id=id;
		this.descripcion=desc;
	}
	
	public long getId() {
		return id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	
}
