package ar.com.nextel.sfa.client.dto;

import java.util.Date;


import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author eSalvador
 */
public class OportunidadDto  implements IsSerializable{

	private String numeroCliente;
	private String razonSocial;
	private String nombre;
	private String apellido;
	private EstadoOportunidadDto estadoOportunidad;
	private Date fechaDesde;
	private Date fechaHasta;
	private int cantidadResultados;
	private int offset;
	private String numeroDocumento;
	private TipoDocumentoDto tipoDocumento;	
	
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
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public TipoDocumentoDto getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(TipoDocumentoDto tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getNumeroCliente() {
		return numeroCliente;
	}
	public void setNumeroCliente(String numeroCliente) {
		this.numeroCliente = numeroCliente;
	}
	public EstadoOportunidadDto getEstadoOportunidad() {
		return estadoOportunidad;
	}
	public void setEstadoOportunidad(EstadoOportunidadDto estadoOportunidad) {
		this.estadoOportunidad = estadoOportunidad;
	}
	public Date getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	public Date getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

}
