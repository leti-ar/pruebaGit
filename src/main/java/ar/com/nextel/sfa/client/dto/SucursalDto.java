package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SucursalDto implements IsSerializable, ListBoxItem, Comparable {
	
	private Long id;
	private String descripcion;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getItemText() {
		return descripcion;
	}

	public String getItemValue() {
		return id + "";
	}
	
	public int compareTo(Object o) {
		SucursalDto sucursal = (SucursalDto) o;
		return this.descripcion.compareToIgnoreCase(sucursal.getDescripcion());
	}
	
}
