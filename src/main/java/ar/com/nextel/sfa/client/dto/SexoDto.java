package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

public class SexoDto extends EnumDto implements ListBoxItem, IsSerializable {

    private String codigoVantive;
    
    
	public SexoDto() {
	}
	
	public SexoDto(long id, String descripcion) {
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

	public String getCodigoVantive() {
		return codigoVantive;
	}

	public void setCodigoVantive(String codigoVantive) {
		this.codigoVantive = codigoVantive;
	}

}
