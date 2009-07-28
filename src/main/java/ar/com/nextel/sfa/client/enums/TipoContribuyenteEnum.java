package ar.com.nextel.sfa.client.enums;

public enum TipoContribuyenteEnum {
	CONS_FINAL   (1L,"CONSUMIDOR FINAL");
	Long id;
	String descripcion;
	TipoContribuyenteEnum(Long id, String desc) {
		this.id=id;
		this.descripcion=desc;
	}
	public Long getId() {
		return id;
	}
	public String getDescripcion() {
		return descripcion;
	}
}
