package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EquiposServiciosDto implements IsSerializable {
	
    public String descripcion = "descripcion";    
    public String aVencer = "aVencer";    
    public String vencida = "vencida";
	
    
    public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getAVencer() {
		return aVencer;
	}
	public void setAVencer(String vencer) {
		aVencer = vencer;
	}
	public String getVencida() {
		return vencida;
	}
	public void setVencida(String vencida) {
		this.vencida = vencida;
	}   
    
}
