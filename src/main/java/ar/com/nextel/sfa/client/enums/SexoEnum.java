package ar.com.nextel.sfa.client.enums;

public enum SexoEnum {
	INDEFINIDO   (1), 
	FEMENINO     (2),
	MASCULINO    (3),
	ORGANIZACION (4);
	
	private long id;
	
	SexoEnum(long id) {
		this.id=id;
	}
	
	public long getId() {
		return id;
	}
	
	public static long getId(String nroDoc) {
		return (nroDoc.startsWith("30") || nroDoc.startsWith("33")) ? SexoEnum.ORGANIZACION.getId() : SexoEnum.MASCULINO.getId();
	}
}
