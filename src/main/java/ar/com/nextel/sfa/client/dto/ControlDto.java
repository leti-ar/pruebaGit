package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ControlDto extends EnumDto implements ListBoxItem, IsSerializable {

	
	public String getItemText() {
		return descripcion;
	}

	public String getItemValue() {
		return "" + id;
	}

	
	
}
