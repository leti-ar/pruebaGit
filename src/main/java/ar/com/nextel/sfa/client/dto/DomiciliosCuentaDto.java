package ar.com.nextel.sfa.client.dto;

import ar.com.nextel.sfa.client.enums.EstadoDomicilioEnum;
import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author eSalvador
 **/
public class DomiciliosCuentaDto implements IsSerializable, ListBoxItem, IdentifiableDto, Cloneable {

	private Long id;
	private String calle;
	private String numero;
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
	private Boolean no_normalizar;
	private Boolean validado = false;
//	private String en_carga;
	private String nombre_usuario_ultima_modificacion;
	private String fecha_ultima_modificacion;
	private String activo;
	private Long vantiveId;
	private EstadoDomicilioDto estado = new EstadoDomicilioDto(new Long(EstadoDomicilioEnum.ACTIVO.getId()),
			EstadoDomicilioEnum.ACTIVO.getDesc());
	private Long idEntrega;
	private Long idFacturacion;

	// private List<TipoDomicilioAsociadoDto> tiposDomicilioAsociado = new
	// ArrayList<TipoDomicilioAsociadoDto>();

	// Lo seteo en True por defecto, porque esto no se guarda en la base,
	// y cuando venga el dato, siempre estara deshabilitado, a menos que sea un domicilio Nuevo.
	// private boolean locked = true;

	private boolean locked;

//	public String getDomicilios() {
//	/** Muestra la concatenación de Calle, Número, Piso, Dpto., UF, Torre, Localidad, Partido, Provincia, Código Postal y CPA para cada domicilio habilitado.*/
//	String domicilios = this.calle +  
//	                    " " + this.numero +  
//	                    (this.piso!=null ? " " + this.piso: "") + 
//	                    (this.departamento!=null ? " " + this.departamento:"") + 
//	                    (this.getUnidad_funcional()!=null ? " " + this.getUnidad_funcional():"") + 
//	                    (this.torre!=null ? " " + this.torre: "") + 
//	                    ". " +
//	                    this.localidad + 
//	                    (this.partido!=null && !this.partido.equals("") ? "-" + this.partido : "") +  
//	                    ", " + 
//	                    this.provincia.getDescripcion() +  
//	                    (this.cpa!=null ? " ("+this.cpa+")" : this.codigo_postal!=null ? " ("+this.codigo_postal+")" : "");
//	
//	return domicilios;
//	}
	
	public String getDomicilios() {
		/** Muestra la concatenación de Calle, Número, Piso, Dpto., UF, Torre, Localidad, Partido, Provincia, Código Postal y CPA para cada domicilio habilitado.*/
		String domicilios = this.calle +  
		                    " " + this.numero +  
		                    (!"".equals(this.piso) ? " " + this.piso: "") + 
		                    (!"".equals(this.departamento) ? " " + this.departamento:"") + 
		                    (!"".equals(this.getUnidad_funcional()) ? " " + this.getUnidad_funcional():"") + 
		                    (!"".equals(this.torre) ? " " + this.torre: "") + 
		                    ". " +
		                    this.localidad + 
		                    (this.partido!=null && !this.partido.equals("") ? "-" + this.partido : "") +  
		                    ", " + 
		                    this.provincia.getDescripcion() +  
		                    (this.cpa!=null ? " ("+this.cpa+")" : this.codigo_postal!=null ? " ("+this.codigo_postal+")" : "");
		
		return domicilios;
		}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
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

	public Boolean getNo_normalizar() {
		return no_normalizar;
	}

	public void setNo_normalizar(Boolean no_normalizar) {
		this.no_normalizar = no_normalizar;
	}

	public boolean getValidado() {
		return validado;
	}

	public void setValidado(boolean validado) {
		this.validado = validado;
	}

//	public String getEn_carga() {
//		return en_carga;
//	}
//
//	public void setEn_carga(String en_carga) {
//		this.en_carga = en_carga;
//	}

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

	public DomiciliosCuentaDto clone() {
		DomiciliosCuentaDto domicilioCopiado = new DomiciliosCuentaDto();
		domicilioCopiado.setCalle(calle);
		domicilioCopiado.setEntre_calle(entre_calle);
		domicilioCopiado.setY_calle(y_calle);
		domicilioCopiado.setCodigo_postal(codigo_postal);
		domicilioCopiado.setLocalidad(localidad);
		domicilioCopiado.setPartido(partido);
		domicilioCopiado.setCpa(cpa);
		domicilioCopiado.setDepartamento(departamento);
		domicilioCopiado.setManzana(manzana);
		if (!"".equals(numero)) {
			domicilioCopiado.setNumero(numero);
		}
		domicilioCopiado.setObservaciones(observaciones);
		domicilioCopiado.setPiso(piso);
		domicilioCopiado.setProvincia(provincia);
		domicilioCopiado.setPuerta(puerta);
		domicilioCopiado.setTorre(torre);
		domicilioCopiado.setValidado(validado);
		domicilioCopiado.setNombre_usuario_ultima_modificacion(nombre_usuario_ultima_modificacion);
		domicilioCopiado.setFecha_ultima_modificacion(fecha_ultima_modificacion);
		domicilioCopiado.setIdEntrega(idEntrega);
		domicilioCopiado.setIdFacturacion(idFacturacion);
		return domicilioCopiado;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EstadoDomicilioDto getEstado() {
		// return new
		// EstadoDomicilioDto(EstadoDomicilioEnum.ACTIVO.getId(),EstadoDomicilioEnum.ACTIVO.toString());
		return estado;
	}

	public void setEstado(EstadoDomicilioDto estado) {
		this.estado = estado;
	}

	public Long getVantiveId() {
		return vantiveId;
	}

	public void setVantiveId(Long vantiveId) {
		this.vantiveId = vantiveId;
	}

	public String getItemText() {
		return getDomicilios();
	}

	public String getItemValue() {
		return "" + id;
	}

	public Long getIdEntrega() {
		return idEntrega;
	}

	public void setIdEntrega(Long idEntrega) {
		this.idEntrega = idEntrega;
	}

	public Long getIdFacturacion() {
		return idFacturacion;
	}

	public void setIdFacturacion(Long idFacturacion) {
		this.idFacturacion = idFacturacion;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DomiciliosCuentaDto other = (DomiciliosCuentaDto) obj;
		if (id == null) {
			return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
