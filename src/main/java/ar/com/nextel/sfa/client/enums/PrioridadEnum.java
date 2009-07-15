package ar.com.nextel.sfa.client.enums;

public enum PrioridadEnum {
	ALTA   (1), 
	MEDIA  (2),
	BAJA   (3);
	private long id;
	PrioridadEnum(long id) {
		this.id=id;
	}
	public long getId() {
		return id;
	}	
}