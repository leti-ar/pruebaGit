package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

public class TipoDocumentoDto implements ListBoxItem {

	private String code;
	private String descripcion;

	public TipoDocumentoDto() {
	}
	
	public TipoDocumentoDto(String code, String descripcion) {
		super();
		this.code = code;
		this.descripcion = descripcion;
	}

	public String getItemText() {
		return descripcion;
	}

	public String getItemValue() {
		return code;
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
