package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CuentaResultsDto implements IsSerializable{

	private long id;
	private long categoria;
	private String numero;
	private String razonSocial;
	private String apellidoContacto;
	private String numeroTelefono;
	private long lockingState;
	private String ejecutivoLockeo;
	private String ejecutivo;
	private long condicionCuenta;
	private boolean responsablePago;
	private long claseCuenta;
	private String supervisor;
	private String montoCreditoFidelizacion;
	private boolean sincronizada;
	private boolean puedeVerInfocom = true;
	private String numeroAux;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCategoria() {
		return categoria;
	}

	public void setCategoria(long categoria) {
		this.categoria = categoria;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getApellidoContacto() {
		return apellidoContacto;
	}

	public void setApellidoContacto(String apellidoContacto) {
		this.apellidoContacto = apellidoContacto;
	}

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	public long getLockingState() {
		return lockingState;
	}

	public void setLockingState(long lockingState) {
		this.lockingState = lockingState;
	}

	public String getEjecutivoLockeo() {
		return ejecutivoLockeo;
	}

	public void setEjecutivoLockeo(String ejecutivoLockeo) {
		this.ejecutivoLockeo = ejecutivoLockeo;
	}

	public String getEjecutivo() {
		return ejecutivo;
	}

	public void setEjecutivo(String ejecutivo) {
		this.ejecutivo = ejecutivo;
	}

	public long getCondicionCuenta() {
		return condicionCuenta;
	}

	public void setCondicionCuenta(long condicionCuenta) {
		this.condicionCuenta = condicionCuenta;
	}

	public boolean isResponsablePago() {
		return responsablePago;
	}

	public void setResponsablePago(boolean responsablePago) {
		this.responsablePago = responsablePago;
	}

	public long getClaseCuenta() {
		return claseCuenta;
	}

	public void setClaseCuenta(long claseCuenta) {
		this.claseCuenta = claseCuenta;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getMontoCreditoFidelizacion() {
		return montoCreditoFidelizacion;
	}

	public void setMontoCreditoFidelizacion(String montoCreditoFidelizacion) {
		this.montoCreditoFidelizacion = montoCreditoFidelizacion;
	}

	public boolean isSincronizada() {
		return sincronizada;
	}

	public void setSincronizada(boolean sincronizada) {
		this.sincronizada = sincronizada;
	}

	public boolean isPuedeVerInfocom() {
		return puedeVerInfocom;
	}

	public void setPuedeVerInfocom(boolean puedeVerInfocom) {
		this.puedeVerInfocom = puedeVerInfocom;
	}

	public String getNumeroAux() {
		return numeroAux;
	}

	public void setNumeroAux(String numeroAux) {
		this.numeroAux = numeroAux;
	}

}
