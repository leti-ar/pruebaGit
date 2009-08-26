package ar.com.nextel.sfa.client.enums;

public enum SexoEnum {
	INDEFINIDO   (1,"Indefinido"), 
	FEMENINO     (2,"Femenino"),
	MASCULINO    (3,"Masculino"),
	ORGANIZACION (4,"Organización");
	
	private long id;
	private String descripcion;
	SexoEnum(long id,String desc) {
		this.id=id;
		this.descripcion=desc;
	}
	
	public long getId() {
		return id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public static long getId(String nroDoc) {
		return (nroDoc.startsWith("30") || nroDoc.startsWith("33")) ? SexoEnum.ORGANIZACION.getId() : SexoEnum.MASCULINO.getId();
	}
}
