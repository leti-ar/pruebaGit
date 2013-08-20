package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ItemSolicitudDto extends EnumDto implements IsSerializable {

	private Boolean sinModelo;
	private String segment1;
	public String classIndicator;
	public String esSim;
	  
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

	public String getClassIndicator() {
		return classIndicator;
	}

	public void setClassIndicator(String classIndicator) {
		this.classIndicator = classIndicator;
	}

	public String getEsSim() {
		return esSim;
	}

	public void setEsSim(String esSim) {
		this.esSim = esSim;
	}
	

	
}
