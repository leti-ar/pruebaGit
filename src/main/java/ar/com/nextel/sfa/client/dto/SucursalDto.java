package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SucursalDto implements IsSerializable, ListBoxItem {
	
	private Long id;
	private String descripcion;
//  MGR - Facturacion
    private Long idVantive;
	
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
	
	public Long getIdVantive() {
		return idVantive;
	}

	public void setIdVantive(Long idVantive) {
		this.idVantive = idVantive;
	}
	
	public int compareTo(Object o) {
		SucursalDto suc1 = (SucursalDto)o;
		return this.id.compareTo(suc1.id); 
	}
}
