package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MotivoNoCierreDto implements IsSerializable {
	private Long id;
	private String codigoVantive;
	private String descripcion;

	public MotivoNoCierreDto() {}
	public MotivoNoCierreDto(Long id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCodigoVantive() {
		return codigoVantive;
	}
	public void setCodigoVantive(String codigoVantive) {
		this.codigoVantive = codigoVantive;
	}
}
