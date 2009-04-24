package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class FormaPagoDto implements IsSerializable, ListBoxItem {
    private String codigoVantive;
    private String descripcion;
	public String getCodigoVantive() {
		return codigoVantive;
	}
	public String getItemText() {
		return descripcion;
	}
	public String getItemValue() {
		return codigoVantive + "";
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
