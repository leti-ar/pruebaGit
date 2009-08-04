package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ScoringDto implements IsSerializable {
	
    private String scoringData;
    private String cantidadCDW;
    private String cantidadTerminales;
    private String cantidadANIoTarjeta;
    private String mensajeAdicional;
    private String cantidadEquiposDDI;
	
    
    public String getScoringData() {
		return scoringData;
	}
	public void setScoringData(String scoringData) {
		this.scoringData = scoringData;
	}
	public String getCantidadCDW() {
		return cantidadCDW;
	}
	public void setCantidadCDW(String cantidadCDW) {
		this.cantidadCDW = cantidadCDW;
	}
	public String getCantidadTerminales() {
		return cantidadTerminales;
	}
	public void setCantidadTerminales(String cantidadTerminales) {
		this.cantidadTerminales = cantidadTerminales;
	}
	public String getCantidadANIoTarjeta() {
		return cantidadANIoTarjeta;
	}
	public void setCantidadANIoTarjeta(String cantidadANIoTarjeta) {
		this.cantidadANIoTarjeta = cantidadANIoTarjeta;
	}
	public String getMensajeAdicional() {
		return mensajeAdicional;
	}
	public void setMensajeAdicional(String mensajeAdicional) {
		this.mensajeAdicional = mensajeAdicional;
	}
	public String getCantidadEquiposDDI() {
		return cantidadEquiposDDI;
	}
	public void setCantidadEquiposDDI(String cantidadEquiposDDI) {
		this.cantidadEquiposDDI = cantidadEquiposDDI;
	}   
    
}
