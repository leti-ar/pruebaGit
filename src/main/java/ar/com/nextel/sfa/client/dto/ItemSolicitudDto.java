package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ItemSolicitudDto implements IsSerializable {

	private Long id;
	private String descripcion;
	private Boolean sinModelo;

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

	public Boolean getSinModelo() {
		return sinModelo;
	}

	public void setSinModelo(Boolean sinModelo) {
		this.sinModelo = sinModelo;
	}

}
