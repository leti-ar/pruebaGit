package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EquipoDto implements IsSerializable {

    private String cliente;
    private String numeroID;    
    private String telefono;    
    private Double abono;    
    private Double alquiler;    
    private Double garantia;    
    private Double servicios;    
    private String facturaNumero;    
    private String fechaEmision;    
    private Double proporcionalYReintegros;    
    private Double excedenteRadio;    
    private Double excedenteTelefonia;    
    private Double redFija;    
    private Double ddn;    
    private Double ddiYRoaming;    
    private Double pagers;    
    private Double totalConImpuestos;
    
    
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getNumeroID() {
		return numeroID;
	}
	public void setNumeroID(String numeroID) {
		this.numeroID = numeroID;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getFacturaNumero() {
		return facturaNumero;
	}
	public void setFacturaNumero(String facturaNumero) {
		this.facturaNumero = facturaNumero;
	}
	public String getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public Double getAbono() {
		return abono;
	}
	public void setAbono(Double abono) {
		this.abono = abono;
	}
	public Double getAlquiler() {
		return alquiler;
	}
	public void setAlquiler(Double alquiler) {
		this.alquiler = alquiler;
	}
	public Double getGarantia() {
		return garantia;
	}
	public void setGarantia(Double garantia) {
		this.garantia = garantia;
	}
	public Double getServicios() {
		return servicios;
	}
	public void setServicios(Double servicios) {
		this.servicios = servicios;
	}
	public Double getProporcionalYReintegros() {
		return proporcionalYReintegros;
	}
	public void setProporcionalYReintegros(Double proporcionalYReintegros) {
		this.proporcionalYReintegros = proporcionalYReintegros;
	}
	public Double getExcedenteRadio() {
		return excedenteRadio;
	}
	public void setExcedenteRadio(Double excedenteRadio) {
		this.excedenteRadio = excedenteRadio;
	}
	public Double getExcedenteTelefonia() {
		return excedenteTelefonia;
	}
	public void setExcedenteTelefonia(Double excedenteTelefonia) {
		this.excedenteTelefonia = excedenteTelefonia;
	}
	public Double getRedFija() {
		return redFija;
	}
	public void setRedFija(Double redFija) {
		this.redFija = redFija;
	}
	public Double getDdn() {
		return ddn;
	}
	public void setDdn(Double ddn) {
		this.ddn = ddn;
	}
	public Double getDdiYRoaming() {
		return ddiYRoaming;
	}
	public void setDdiYRoaming(Double ddiYRoaming) {
		this.ddiYRoaming = ddiYRoaming;
	}
	public Double getPagers() {
		return pagers;
	}
	public void setPagers(Double pagers) {
		this.pagers = pagers;
	}
	public Double getTotalConImpuestos() {
		return totalConImpuestos;
	}
	public void setTotalConImpuestos(Double totalConImpuestos) {
		this.totalConImpuestos = totalConImpuestos;
	}
	    
}
