package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DatosEquipoPorEstadoDto implements IsSerializable{

	private String numeroCuenta;    
    private String plan;    
    private String modelo;    
    private String tipoTelefonia;    
    private String formaContratacion;    
    private String fecha;    
    private Integer numeroContrato;    
    private String motivo;   
    private String numeroSolicitudServicio;
	
    
    public String getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getTipoTelefonia() {
		return tipoTelefonia;
	}
	public void setTipoTelefonia(String tipoTelefonia) {
		this.tipoTelefonia = tipoTelefonia;
	}
	public String getFormaContratacion() {
		return formaContratacion;
	}
	public void setFormaContratacion(String formaContratacion) {
		this.formaContratacion = formaContratacion;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public Integer getNumeroContrato() {
		return numeroContrato;
	}
	public void setNumeroContrato(Integer numeroContrato) {
		this.numeroContrato = numeroContrato;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getNumeroSolicitudServicio() {
		return numeroSolicitudServicio;
	}
	public void setNumeroSolicitudServicio(String numeroSolicitudServicio) {
		this.numeroSolicitudServicio = numeroSolicitudServicio;
	}    
    
}
