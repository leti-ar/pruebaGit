package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class FormaPagoDto implements IsSerializable, ListBoxItem {
    
	private long id;
	private String codigoVantive;
    private String descripcion;
    
    public FormaPagoDto() { }
    
	public FormaPagoDto(long id, String codigoVantive, String descripcion) {
		super();
		this.codigoVantive = codigoVantive;
		this.descripcion = descripcion;
		this.id = id;
	}
	
	public String getCodigoVantive() {
		return codigoVantive;
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
	public void setCodigoVantive(String codigoVantive) {
		this.codigoVantive = codigoVantive;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
