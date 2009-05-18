package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TipoContribuyenteDto extends EnumDto implements ListBoxItem, IsSerializable {

	public TipoContribuyenteDto() {
	}
	
	public TipoContribuyenteDto(long id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}

	public String getItemText() {
		return descripcion;
	}
	public String getItemValue() {
		return id+"";
	}

}
