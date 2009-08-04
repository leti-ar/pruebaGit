package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EquiposServiciosDto implements IsSerializable {
	
    private Float deudaEquiposAVencer;    
    private Float deudaEquiposVencida;    
    private Float deudaServiciosAVencer;    
    private Float deudaServiciosVencida;
    
    
	public Float getDeudaEquiposAVencer() {
		return deudaEquiposAVencer;
	}
	public void setDeudaEquiposAVencer(Float deudaEquiposAVencer) {
		this.deudaEquiposAVencer = deudaEquiposAVencer;
	}
	public Float getDeudaEquiposVencida() {
		return deudaEquiposVencida;
	}
	public void setDeudaEquiposVencida(Float deudaEquiposVencida) {
		this.deudaEquiposVencida = deudaEquiposVencida;
	}
	public Float getDeudaServiciosAVencer() {
		return deudaServiciosAVencer;
	}
	public void setDeudaServiciosAVencer(Float deudaServiciosAVencer) {
		this.deudaServiciosAVencer = deudaServiciosAVencer;
	}
	public Float getDeudaServiciosVencida() {
		return deudaServiciosVencida;
	}
	public void setDeudaServiciosVencida(Float deudaServiciosVencida) {
		this.deudaServiciosVencida = deudaServiciosVencida;
	}
    
    
    
}
