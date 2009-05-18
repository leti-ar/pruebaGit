package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ClaseCuentaDto extends EnumDto implements IsSerializable, ListBoxItem  {

	public ClaseCuentaDto() {
	}
	public ClaseCuentaDto(long id, String descripcion) {
		super();
		this.id=id;
		this.descripcion=descripcion;
	}
	public String getItemText() {
		return descripcion;
	}
	public String getItemValue() {
		return id + "";
	}
}
