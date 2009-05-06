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

	private long id;
	private String code;
	private String descripcion;
	
	public static CategoriaCuentaDto GRAN_CUENTA = new CategoriaCuentaDto(1, "GRAN CUENTA","GRAN CUENTA");
	public static CategoriaCuentaDto DIVISION    = new CategoriaCuentaDto(2, "DIVISION","DIVISION");
	public static CategoriaCuentaDto SUSCRIPTOR  = new CategoriaCuentaDto(3, "SUSCRIPTOR","SUSCRIPTOR");

	public CategoriaCuentaDto() {
	}

	public CategoriaCuentaDto(long id,String code, String descripcion) {
		super();
		this.id = id;
		this.code = code;
		this.descripcion = descripcion;
	}

	public String getItemText() {
		return descripcion;
	}

	public String getItemValue() {
		return id + "";
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
