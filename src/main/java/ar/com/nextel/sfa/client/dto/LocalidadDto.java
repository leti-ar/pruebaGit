package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LocalidadDto extends EnumDto implements IsSerializable, ListBoxItem {

	public String getItemText() {
		return descripcion;
	}

	public String getItemValue() {
		return "" + id;
	}

}
