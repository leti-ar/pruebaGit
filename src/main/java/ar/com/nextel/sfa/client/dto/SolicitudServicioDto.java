package ar.com.nextel.sfa.client.dto;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SolicitudServicioDto implements IsSerializable {

	private Long id;
	private VendedorDto vendedor;
	private GrupoSolicitudDto grupoSolicitud;
	private CuentaDto cuenta;
	private String numero;
	private String numeroFlota;

	// private CuentaPotencial cuentaPotencial;
	// private OrigenSolicitud origen;
	// private Vendedor vendedorDAE;

	private DomicilioDto domicilioFacturacion;
	private DomicilioDto domicilioEnvio;
	private String aclaracionEntrega;
	private String email;
	private String emailLicencias;

	private Double montoCreditoFideliacion;
	private String fechaVencimientoCreditoFidelizacion;
	private String montoDisponible;
	private Long channelCode;
	private Double pataconex;

	private Boolean firmar = Boolean.FALSE;
	// private EstadoAprobacionSolicitud estadoActual;
	private String observaciones;

	// private TipoAnticipo tipoAnticipo;

	// private Set<LineaSolicitudServicio> lineas = new HashSet<LineaSolicitudServicio>();

	private Boolean pinValidationSuccess;
	private Boolean scoringValidationSuccess;
	private Long idVantive;

	private Boolean vencimientoProcesado;

	private Boolean generada;

	private Boolean enCarga;

	private Date fechaCreacion;

	public SolicitudServicioDto() {

	}

	public Long getId() {
		return id;
	}

	public VendedorDto getVendedor() {
		return vendedor;
	}

	public GrupoSolicitudDto getGrupoSolicitud() {
		return grupoSolicitud;
	}

	public CuentaDto getCuenta() {
		return cuenta;
	}

	public String getNumero() {
		return numero;
	}

	public String getNumeroFlota() {
		return numeroFlota;
	}

	public DomicilioDto getDomicilioFacturacion() {
		return domicilioFacturacion;
	}

	public DomicilioDto getDomicilioEnvio() {
		return domicilioEnvio;
	}

	public String getAclaracionEntrega() {
		return aclaracionEntrega;
	}

	public String getEmail() {
		return email;
	}

	public String getEmailLicencias() {
		return emailLicencias;
	}

	public Double getMontoCreditoFideliacion() {
		return montoCreditoFideliacion;
	}

	public String getFechaVencimientoCreditoFidelizacion() {
		return fechaVencimientoCreditoFidelizacion;
	}

	public String getMontoDisponible() {
		return montoDisponible;
	}

	public Long getChannelCode() {
		return channelCode;
	}

	public Double getPataconex() {
		return pataconex;
	}

	public Boolean getFirmar() {
		return firmar;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public Boolean getPinValidationSuccess() {
		return pinValidationSuccess;
	}

	public Boolean getScoringValidationSuccess() {
		return scoringValidationSuccess;
	}

	public Long getIdVantive() {
		return idVantive;
	}

	public Boolean getVencimientoProcesado() {
		return vencimientoProcesado;
	}

	public Boolean getGenerada() {
		return generada;
	}

	public Boolean getEnCarga() {
		return enCarga;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setVendedor(VendedorDto vendedor) {
		this.vendedor = vendedor;
	}

	public void setGrupoSolicitud(GrupoSolicitudDto grupoSolicitud) {
		this.grupoSolicitud = grupoSolicitud;
	}

	public void setCuenta(CuentaDto cuenta) {
		this.cuenta = cuenta;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setNumeroFlota(String numeroFlota) {
		this.numeroFlota = numeroFlota;
	}

	public void setDomicilioFacturacion(DomicilioDto domicilioFacturacion) {
		this.domicilioFacturacion = domicilioFacturacion;
	}

	public void setDomicilioEnvio(DomicilioDto domicilioEnvio) {
		this.domicilioEnvio = domicilioEnvio;
	}

	public void setAclaracionEntrega(String aclaracionEntrega) {
		this.aclaracionEntrega = aclaracionEntrega;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEmailLicencias(String emailLicencias) {
		this.emailLicencias = emailLicencias;
	}

	public void setMontoCreditoFideliacion(Double montoCreditoFideliacion) {
		this.montoCreditoFideliacion = montoCreditoFideliacion;
	}

	public void setFechaVencimientoCreditoFidelizacion(String fechaVencimientoCreditoFidelizacion) {
		this.fechaVencimientoCreditoFidelizacion = fechaVencimientoCreditoFidelizacion;
	}

	public void setMontoDisponible(String montoDisponible) {
		this.montoDisponible = montoDisponible;
	}

	public void setChannelCode(Long channelCode) {
		this.channelCode = channelCode;
	}

	public void setPataconex(Double pataconex) {
		this.pataconex = pataconex;
	}

	public void setFirmar(Boolean firmar) {
		this.firmar = firmar;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public void setPinValidationSuccess(Boolean pinValidationSuccess) {
		this.pinValidationSuccess = pinValidationSuccess;
	}

	public void setScoringValidationSuccess(Boolean scoringValidationSuccess) {
		this.scoringValidationSuccess = scoringValidationSuccess;
	}

	public void setIdVantive(Long idVantive) {
		this.idVantive = idVantive;
	}

	public void setVencimientoProcesado(Boolean vencimientoProcesado) {
		this.vencimientoProcesado = vencimientoProcesado;
	}

	public void setGenerada(Boolean generada) {
		this.generada = generada;
	}

	public void setEnCarga(Boolean enCarga) {
		this.enCarga = enCarga;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}
