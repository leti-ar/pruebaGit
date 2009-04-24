package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ModalidadCobroDto implements IsSerializable, ListBoxItem  {
    private String descripcion;
    private String codigoBSCS;
	public String getDescripcion() {
		return descripcion;
	}
	public String getItemText() {
		return descripcion;
	}

	public String getItemValue() {
		return codigoBSCS + "";
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCodigoBSCS() {
		return codigoBSCS;
	}
	public void setCodigoBSCS(String codigoBSCS) {
		this.codigoBSCS = codigoBSCS;
	}
}
