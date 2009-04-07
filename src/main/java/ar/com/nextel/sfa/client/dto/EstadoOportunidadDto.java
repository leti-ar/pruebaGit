package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author eSalvador 
 **/
public class EstadoOportunidadDto  implements ListBoxItem, IsSerializable {

	private String code;
	private String descripcion;
	
	public static EstadoOportunidadDto GRAN_CUENTA = new EstadoOportunidadDto("0", "Activa");
	public static EstadoOportunidadDto DIVISION = new EstadoOportunidadDto("1", "Inactiva");
	
	public EstadoOportunidadDto() {
	}

	public EstadoOportunidadDto(String code, String descripcion) {
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
