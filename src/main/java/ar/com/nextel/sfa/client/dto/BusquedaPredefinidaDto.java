package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author eSalvador
 */
public class BusquedaPredefinidaDto implements ListBoxItem, IsSerializable {
	
	private long id;
	private String descripcion;
	
	public static BusquedaPredefinidaDto TIPO_PREDEFINIDA_NULL = new BusquedaPredefinidaDto(0,"null");
	public static BusquedaPredefinidaDto TIPO_PREDEFINIDA_PROPIAS    = new BusquedaPredefinidaDto(1,"Ctas. propias");
	public static BusquedaPredefinidaDto TIPO_PREDEFINIDA_ULTIMA_CONSULTADA  = new BusquedaPredefinidaDto(3,"Últimas consultadas");

	public BusquedaPredefinidaDto() {
	}

	public BusquedaPredefinidaDto(long id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}

	public String getItemValue() {
		return id + "";
	}
	
	public long getId() {
		return id;
	}
	
	public String getItemText() {
		return descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
