package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ItemSolicitudDto extends EnumDto implements IsSerializable {

	private Boolean sinModelo;
	private String segment1;
	  
	public Boolean getSinModelo() {
		return sinModelo;
	}

	public void setSinModelo(Boolean sinModelo) {
		this.sinModelo = sinModelo;
	}
	
	public String getSegment1() {
		return segment1;
	}
	
	public void setSegment1(String segment1) {
		this.segment1 = segment1;
	}

}
