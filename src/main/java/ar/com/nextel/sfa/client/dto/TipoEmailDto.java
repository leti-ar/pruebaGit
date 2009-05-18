package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TipoEmailDto extends EnumDto implements IsSerializable {

	public TipoEmailDto() {}
    
    public TipoEmailDto(long id, String desc) {
    	super();
    	this.id=id;
    	this.descripcion=desc;
    }
    
}
