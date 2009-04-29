package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

public class TipoContribuyenteDto implements ListBoxItem, IsSerializable {

	private long id;
	private String code;
	private String descripcion;
	
	public TipoContribuyenteDto() {
	}
	
	public TipoContribuyenteDto(long id, String code, String descripcion) {
		super();
		this.id = id;
		this.code = code;
		this.descripcion = descripcion;
	}

	public String getItemText() {
		return descripcion;
	}
	public String getItemValue() {
		return id+"";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
