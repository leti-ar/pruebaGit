package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

public class BusquedaPredefinidaDto implements ListBoxItem {

	public static final long TIPO_PREDEFINIDA_PROPIAS = 1;
	public static final long TIPO_PREDEFINIDA_CON_CREDITO_FIDEL = 2;
	public static final long TIPO_PREDEFINIDA_ULTIMA_CONSULTADA = 3;

	private String code;
	private String descripcion;

	public BusquedaPredefinidaDto() {
	}

	public BusquedaPredefinidaDto(String code, String descripcion) {
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
