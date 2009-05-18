package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TipoTelefoniaDto extends EnumDto implements IsSerializable {

     public TipoTelefoniaDto() {}
     
     public TipoTelefoniaDto(long id, String desc) {
    	 super();
    	 this.id=id;
    	 this.descripcion=desc;
     }
	
}
