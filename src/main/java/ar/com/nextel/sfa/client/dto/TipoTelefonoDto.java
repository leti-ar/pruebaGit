package ar.com.nextel.sfa.client.dto;

public class TipoTelefonoDto {

	private String code;
	private String descripcion;
	
	public TipoTelefonoDto(String code, String descripcion) {
		super();
		this.code = code;
		this.descripcion = descripcion;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
}
