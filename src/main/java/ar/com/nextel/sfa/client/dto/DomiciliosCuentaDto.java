package ar.com.nextel.sfa.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DomiciliosCuentaDto implements IsSerializable{
	
	private String calle;
	private Long numero;
	private String piso;
	private String codigo_postal;
	private String departamento;
	private String entre_calle;
	private String manzana;
	private String puerta;
	private String y_calle;
	private String observaciones;
	private String localidad;
	private String partido;
	private ProvinciaDto provincia;
	private String cpa;
	private String torre;
	private String unidad_funcional;
	private String no_normalizar;
	private String validado;
	private String en_carga;
	private String nombre_usuario_ultima_modificacion;
	private String fecha_ultima_modificacion;
	private String activo;
	private List<TipoDomicilioAsociadoDto> tiposDomicilioAsociado;
	private boolean locked;
	
	public String getDomicilios() {
	/** Muestra la concatenación de Calle, Número, Piso, Dpto., UF, Torre, Localidad, Partido, Provincia, Código Postal y CPA para cada domicilio habilitado.*/
	//String domicilios = this.calle + " " + (this.numero!=null? this.numero:"") + " " + (this.piso!=null? this.piso:"") + " " + (this.departamento!=null? this.departamento:"") + " " + (this.torre!=null? this.torre:"") + " " + this.localidad + " " + this.partido + " " + this.provincia.getDescripcion() + " " + (this.codigo_postal!=null? this.codigo_postal:"") + " " + this.cpa;	
	
	//TODO: Va el de arriba!!!
	String domicilios = this.calle + " " + (this.numero!=null? this.numero:"") + " " + (this.piso!=null? this.piso:"") + " " + (this.departamento!=null? this.departamento:"") + " " + (this.torre!=null? this.torre:"") + " " + this.localidad + " " + (this.codigo_postal!=null? this.codigo_postal:"") + " " + this.cpa;
		
	return domicilios;
	}
	
	public List<TipoDomicilioAsociadoDto> getTiposDomicilioAsociado() {
		return tiposDomicilioAsociado;
	}

	public void setTiposDomicilioAsociado(List<TipoDomicilioAsociadoDto> tipoDomicilioAsociado) {
		this.tiposDomicilioAsociado = tipoDomicilioAsociado;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	public String getPiso() {
		return piso;
	}
	public void setPiso(String piso) {
		this.piso = piso;
	}
	public String getCodigo_postal() {
		return codigo_postal;
	}
	public void setCodigo_postal(String codigo_postal) {
		this.codigo_postal = codigo_postal;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getEntre_calle() {
		return entre_calle;
	}
	public void setEntre_calle(String entre_calle) {
		this.entre_calle = entre_calle;
	}
	public String getManzana() {
		return manzana;
	}
	public void setManzana(String manzana) {
		this.manzana = manzana;
	}
	public String getPuerta() {
		return puerta;
	}
	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}
	public String getY_calle() {
		return y_calle;
	}
	public void setY_calle(String y_calle) {
		this.y_calle = y_calle;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getPartido() {
		return partido;
	}
	public void setPartido(String partido) {
		this.partido = partido;
	}
	public ProvinciaDto getProvincia() {
		return provincia;
	}
	public void setProvincia(ProvinciaDto provincia) {
		this.provincia = provincia;
	}
	public String getCpa() {
		return cpa;
	}
	public void setCpa(String cpa) {
		this.cpa = cpa;
	}
	public String getTorre() {
		return torre;
	}
	public void setTorre(String torre) {
		this.torre = torre;
	}
	public String getUnidad_funcional() {
		return unidad_funcional;
	}
	public void setUnidad_funcional(String unidad_funcional) {
		this.unidad_funcional = unidad_funcional;
	}
	public String getNo_normalizar() {
		return no_normalizar;
	}
	public void setNo_normalizar(String no_normalizar) {
		this.no_normalizar = no_normalizar;
	}
	public String getValidado() {
		return validado;
	}
	public void setValidado(String validado) {
		this.validado = validado;
	}
	public String getEn_carga() {
		return en_carga;
	}
	public void setEn_carga(String en_carga) {
		this.en_carga = en_carga;
	}
	public String getNombre_usuario_ultima_modificacion() {
		return nombre_usuario_ultima_modificacion;
	}
	public void setNombre_usuario_ultima_modificacion(String nombre_usuario_ultima_modificacion) {
		this.nombre_usuario_ultima_modificacion = nombre_usuario_ultima_modificacion;
	}
	public String getFecha_ultima_modificacion() {
		return fecha_ultima_modificacion;
	}
	public void setFecha_ultima_modificacion(String fecha_ultima_modificacion) {
		this.fecha_ultima_modificacion = fecha_ultima_modificacion;
	}
	public String getActivo() {
		return activo;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
}
