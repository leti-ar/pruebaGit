package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author eSalvador 
 **/
public class EstadoOportunidadDto extends EnumDto implements ListBoxItem, IsSerializable {

//	public static EstadoOportunidadDto GRAN_CUENTA = new EstadoOportunidadDto("0", "Activa");
//	public static EstadoOportunidadDto DIVISION = new EstadoOportunidadDto("1", "No Cerrada");
	
	public EstadoOportunidadDto() {
	}

	public EstadoOportunidadDto(long id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}
	
	public String getItemText() {
		return descripcion;
	}

	public String getItemValue() {
		return id + "";
	}
}
