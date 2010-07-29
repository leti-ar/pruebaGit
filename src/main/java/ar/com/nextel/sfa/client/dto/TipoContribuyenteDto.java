package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TipoContribuyenteDto extends EnumDto implements ListBoxItem, IsSerializable {

	//MGR - 26-07-2010 - Incidente #0000703
	private static final Long COD_COSUMIDOR_FINAL = new Long(1); 
	
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

	//MGR - 26-07-2010 - Incidente #0000703
	public boolean isConsumidorFinal(){
		
		if(id.compareTo(COD_COSUMIDOR_FINAL) == 0)
			return true;
		else
			return false;
	}
}
