package ar.com.nextel.sfa.client.dto;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author eSalvador
 */
public class OportunidadSearchDto  implements IsSerializable{

	private String numeroCuenta;
	private String razonSocial;
	private String nombre;
	private String apellido;
	private String estadoOPP;
	private Date desde;
	private Date hasta;
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
	public String getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
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
	public String getEstadoOPP() {
		return estadoOPP;
	}
	public void setEstadoOPP(String estadoOPP) {
		this.estadoOPP = estadoOPP;
	}
	public Date getDesde() {
		return desde;
	}
	public void setDesde(Date desde) {
		this.desde = desde;
	}
	public Date getHasta() {
		return hasta;
	}
	public void setHasta(Date hasta) {
		this.hasta = hasta;
	}
}
