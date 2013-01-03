package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

//MGR - #934 - Se agrega que implemente Comparable
public class LocalidadDto extends EnumDto implements IsSerializable, ListBoxItem, Comparable {

	public String getItemText() {
		return descripcion;
	}

	public String getItemValue() {
		return "" + id;
	}
	
	//MGR - #934 - La lista de localidades debe estar ordenada por orden alfabetico
	public int compareTo(Object o) {
		LocalidadDto loc1 = (LocalidadDto)o;
		if (this.descripcion == null) {
			return -1;
		}
		if (loc1 == null) {
			return 1;
		}
		return this.descripcion.compareToIgnoreCase(loc1.descripcion);
	}

}
