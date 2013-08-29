package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ItemSolicitudDto extends EnumDto implements IsSerializable {

	private Boolean sinModelo;
	private String segment1;
//	public String classIndicator;
	public Boolean esSim;
	public Boolean equipo;
	public Boolean accesorio;
	public Boolean servicio;
	  
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

//	public String getClassIndicator() {
//		return classIndicator;
//	}
//
//	public void setClassIndicator(String classIndicator) {
//		this.classIndicator = classIndicator;
//	}

	public Boolean getEsSim() {
		return esSim;
	}

	public void setEsSim(Boolean esSim) {
		this.esSim = esSim;
	}
	
	public Boolean isEquipo() {
		return this.equipo;
	}
	
	public void setEquipo(Boolean b) {
		this.equipo = b;
	}
	
	public Boolean isAccesorio() {
		return this.accesorio;
	}

	public void setAccesorio(Boolean b) {
		this.accesorio = b;
	}
	
	public Boolean isServicio() {
		return this.servicio;
	}

	public void setServicio(Boolean b) {
		this.servicio = b;
	}
}
