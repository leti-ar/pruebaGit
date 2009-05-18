package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ProveedorDto extends EnumDto implements ListBoxItem,  IsSerializable {
    
    public ProveedorDto() {}
    
	public ProveedorDto( long id, String descripcion) {
		super();
		this.descripcion = descripcion;
		this.id = id;
	}
	public String getItemText() {
		return descripcion;
	}
	public String getItemValue() {
		return id + "";
	}
}
