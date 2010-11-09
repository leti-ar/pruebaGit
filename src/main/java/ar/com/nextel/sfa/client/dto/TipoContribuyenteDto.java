package ar.com.nextel.sfa.client.dto;

import java.util.HashMap;

import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TipoContribuyenteDto extends EnumDto implements ListBoxItem, IsSerializable {

	//MGR - 26-07-2010 - Incidente #0000703
	private static final String COD_COSUMIDOR_FINAL = "CONSUMIDOR_FINAL"; 
	
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
		HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
		
		if(instancias != null){
			if(id.compareTo(instancias.get(COD_COSUMIDOR_FINAL)) == 0)
				return true;
		}
		return false;
	}
}
