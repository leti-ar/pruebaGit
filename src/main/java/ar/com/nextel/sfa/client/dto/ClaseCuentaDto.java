package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ClaseCuentaDto implements IsSerializable, ListBoxItem  {

	private String code;
	private String descripcion;

	public ClaseCuentaDto() {
	}
	public ClaseCuentaDto(String code, String descripcion) {
		super();
		this.code=code;
		this.descripcion=descripcion;
	}

	
	public String getItemText() {
		return descripcion;
	}

	public String getItemValue() {
		return code + "";
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
