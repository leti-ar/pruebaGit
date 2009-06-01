package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.dto.EnumDto;
import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

/**
 *@author eSalvador
 **/
public class TipoDomicilioItem extends EnumDto implements ListBoxItem{
	
	public String getItemText() {
		return descripcion;
	}

	public String getItemValue() {
		return id.toString();
	}
}
