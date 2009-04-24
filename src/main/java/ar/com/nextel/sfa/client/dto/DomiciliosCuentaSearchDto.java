package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DomiciliosCuentaSearchDto implements IsSerializable {

	
	private String calle;
	private Long numero;
	private Long piso;
	private String codigo_postal;
	private String departamento;
	private String entre_calle;
	private String manzana;
	private String puerta;
	private String y_calle;
	private String observaciones;
	private String localidad;
	private String partido;
	private String provincia;
	private String cpa;
	private String torre;
	private String unidad_funcional;
	private String no_normalizar;
	private String validado;
	private String id_facturacion;
	private String id_entrega;
	private String principal;
	private String en_carga;
	private String nombre_usuario_ultima_modificacion;
	private String fecha_ultima_modificacion;
	private String activo;
	
	//
	private FacturacionDto facturacion;
	private EntregaDto entrega;
	
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

	public Long getPiso() {
		return piso;
	}

	public void setPiso(Long piso) {
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

	public FacturacionDto getFacturacion() {
		return facturacion;
	}

	public void setFacturacion(FacturacionDto facturacion) {
		this.facturacion = facturacion;
	}

	public EntregaDto getEntrega() {
		return entrega;
	}

	public void setEntrega(EntregaDto entrega) {
		this.entrega = entrega;
	}
	
	public String getPartido() {
		return partido;
	}

	public void setPartido(String partido) {
		this.partido = partido;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
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

	public String getId_facturacion() {
		return id_facturacion;
	}

	public void setId_facturacion(String id_facturacion) {
		this.id_facturacion = id_facturacion;
	}

	public String getId_entrega() {
		return id_entrega;
	}

	public void setId_entrega(String id_entrega) {
		this.id_entrega = id_entrega;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
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

	public String toString() {
//		StringBuilder string = new StringBuilder();
//		string.append("Razón Social:" + razonSocial);
//		string.append("\nTipo Doc:" + tipoDocumento);
//		string.append("\nNºDocumento:" + numeroDocumento);
//		string.append("\nNº Cuenta:" + numeroCuenta);
//		string.append("\nNº Nextel:" + numeroNextel);
//		string.append("\nId de flota:" + flotaId);
//		string.append("\nNº Solicitud:" + numeroSolicitudServicio);
//		string.append("\nResponsable:" + responsable);
//		string.append("\nBusqueda Pred.:" + busquedaPredefinida);
//		return string.toString();
		return null;
	}
}