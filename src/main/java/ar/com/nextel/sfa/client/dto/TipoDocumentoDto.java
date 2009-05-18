package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

public class TipoDocumentoDto extends EnumDto implements ListBoxItem, IsSerializable {


	public TipoDocumentoDto() {
	}
	
	public TipoDocumentoDto(long id, String descripcion) {
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
