package ar.com.nextel.sfa.client.enums;

public enum TipoTarjetaEnum {
	AMX("1"),
	CAB("2"),
	DIN("3"),
	MAS("4"),
	VIS("5");
	private String id;
	TipoTarjetaEnum(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
}
