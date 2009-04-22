package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

public class TipoDocumentoDto implements ListBoxItem, IsSerializable {

	private long code;
	private String descripcion;

	public TipoDocumentoDto() {
	}
	
	public TipoDocumentoDto(long code, String descripcion) {
		super();
		this.code = code;
		this.descripcion = descripcion;
	}

	public String getItemText() {
		return descripcion;
	}

	public String getItemValue() {
		return code + "";
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
