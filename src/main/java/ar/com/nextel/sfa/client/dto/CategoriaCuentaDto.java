package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Mapea contra CategoriaCuenta
 * 
 * @author jlgperez
 * 
 */
public class CategoriaCuentaDto implements ListBoxItem, IsSerializable {

	private String code;
	private String descripcion;
	
	public static CategoriaCuentaDto GRAN_CUENTA = new CategoriaCuentaDto("0", "GRAN CUENTA");
	public static CategoriaCuentaDto DIVISION = new CategoriaCuentaDto("1", "DIVISION");
	public static CategoriaCuentaDto SUSCRIPTOR = new CategoriaCuentaDto("2", "SUSCRIPTOR");

	public CategoriaCuentaDto() {
	}

	public CategoriaCuentaDto(String code, String descripcion) {
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
