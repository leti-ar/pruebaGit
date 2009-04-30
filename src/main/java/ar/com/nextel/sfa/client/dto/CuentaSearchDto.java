package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CuentaSearchDto implements IsSerializable{

	private CategoriaCuentaDto categoria;
	private String numeroCuenta;
	private String razonSocial;
	private Long numeroNextel;
	private String flotaId;
	private Long numeroSolicitudServicio;
	private String responsable;
	private BusquedaPredefinidaDto busquedaPredefinida;
	private int cantidadResultados;
	private int offset;
	private String numeroDocumento;
	private GrupoDocumentoDto grupoDocumentoId;
	
	public CategoriaCuentaDto getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaCuentaDto categoria) {
		this.categoria = categoria;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Long getNumeroNextel() {
		return numeroNextel;
	}

	public void setNumeroNextel(Long numeroNextel) {
		this.numeroNextel = numeroNextel;
	}

	public String getFlotaId() {
		return flotaId;
	}

	public void setFlotaId(String flotaId) {
		this.flotaId = flotaId;
	}

	public Long getNumeroSolicitudServicio() {
		return numeroSolicitudServicio;
	}

	public void setNumeroSolicitudServicio(Long numeroSolicitudServicio) {
		this.numeroSolicitudServicio = numeroSolicitudServicio;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public GrupoDocumentoDto getGrupoDocumentoId() {
		return grupoDocumentoId;
	}

	public void setGrupoDocumentoId(GrupoDocumentoDto grupoDocumentoId) {
		this.grupoDocumentoId = grupoDocumentoId;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public BusquedaPredefinidaDto getBusquedaPredefinida() {
		return busquedaPredefinida;
	}

	public void setBusquedaPredefinida(BusquedaPredefinidaDto busquedaPredefinida) {
		this.busquedaPredefinida = busquedaPredefinida;
	}

	public int getCantidadResultados() {
		return cantidadResultados;
	}

	public void setCantidadResultados(int cantidadResultados) {
		this.cantidadResultados = cantidadResultados;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

//	
	public String toString(){
		StringBuilder string = new StringBuilder();
		string.append("Razón Social:" + razonSocial);
		string.append("\nTipo Doc:"+grupoDocumentoId);
		string.append("\nNºDocumento:"+numeroDocumento);
		string.append("\nCategoría:"+categoria);
		string.append("\nNº Cuenta:"+numeroCuenta);
		string.append("\nNº Nextel:"+numeroNextel);
		string.append("\nId de flota:"+flotaId);
		string.append("\nNº Solicitud:"+numeroSolicitudServicio);
		string.append("\nResponsable:"+responsable);
		string.append("\nBusqueda Pred.:"+busquedaPredefinida);
		return string.toString();
	}
}
