package ar.com.nextel.sfa.client.dto;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author jlgperez
 * 
 */
public class SolicitudServicioDto implements IsSerializable {

	private Long id;
	private VendedorDto vendedor;
	private GrupoSolicitudDto grupoSolicitud;
	// private CuentaDto cuenta;
	private String numero;
	private String numeroFlota;

	// private CuentaPotencial cuentaPotencial;
	private OrigenSolicitudDto origen;
	// private Vendedor vendedorDae;

	private DomicilioDto domicilioFacturacion;
	private DomicilioDto domicilioEnvio;
	private String aclaracionEntrega;
	private String email;
	private String emailLicencias;

	private Double montoCreditoFidelizacion;
	private Date fechaVencimientoCreditoFidelizacion;
	private Double montoDisponible; // cuenta.estadoCreditoFidelizacion.monto
	private Long channelCode;
	private Double pataconex;

	private Boolean firmar = Boolean.FALSE;
	// private EstadoAprobacionSolicitud estadoActual;
	private String observaciones;

	private TipoAnticipoDto tipoAnticipo;

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

	public void setId(Long id) {
		this.id = id;
	}

	public VendedorDto getVendedor() {
		return vendedor;
	}

	public void setVendedor(VendedorDto vendedor) {
		this.vendedor = vendedor;
	}

	public GrupoSolicitudDto getGrupoSolicitud() {
		return grupoSolicitud;
	}

	public void setGrupoSolicitud(GrupoSolicitudDto grupoSolicitud) {
		this.grupoSolicitud = grupoSolicitud;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNumeroFlota() {
		return numeroFlota;
	}

	public void setNumeroFlota(String numeroFlota) {
		this.numeroFlota = numeroFlota;
	}

	public OrigenSolicitudDto getOrigen() {
		return origen;
	}

	public void setOrigen(OrigenSolicitudDto origen) {
		this.origen = origen;
	}

	public DomicilioDto getDomicilioFacturacion() {
		return domicilioFacturacion;
	}

	public void setDomicilioFacturacion(DomicilioDto domicilioFacturacion) {
		this.domicilioFacturacion = domicilioFacturacion;
	}

	public DomicilioDto getDomicilioEnvio() {
		return domicilioEnvio;
	}

	public void setDomicilioEnvio(DomicilioDto domicilioEnvio) {
		this.domicilioEnvio = domicilioEnvio;
	}

	public String getAclaracionEntrega() {
		return aclaracionEntrega;
	}

	public void setAclaracionEntrega(String aclaracionEntrega) {
		this.aclaracionEntrega = aclaracionEntrega;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailLicencias() {
		return emailLicencias;
	}

	public void setEmailLicencias(String emailLicencias) {
		this.emailLicencias = emailLicencias;
	}

	public Double getMontoCreditoFidelizacion() {
		return montoCreditoFidelizacion;
	}

	public void setMontoCreditoFidelizacion(Double montoCreditoFidelizacion) {
		this.montoCreditoFidelizacion = montoCreditoFidelizacion;
	}

	public Date getFechaVencimientoCreditoFidelizacion() {
		return fechaVencimientoCreditoFidelizacion;
	}

	public void setFechaVencimientoCreditoFidelizacion(Date fechaVencimientoCreditoFidelizacion) {
		this.fechaVencimientoCreditoFidelizacion = fechaVencimientoCreditoFidelizacion;
	}

	public Double getMontoDisponible() {
		return montoDisponible;
	}

	public void setMontoDisponible(Double montoDisponible) {
		this.montoDisponible = montoDisponible;
	}

	public Long getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(Long channelCode) {
		this.channelCode = channelCode;
	}

	public Double getPataconex() {
		return pataconex;
	}

	public void setPataconex(Double pataconex) {
		this.pataconex = pataconex;
	}

	public Boolean getFirmar() {
		return firmar;
	}

	public void setFirmar(Boolean firmar) {
		this.firmar = firmar;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public TipoAnticipoDto getTipoAnticipo() {
		return tipoAnticipo;
	}

	public void setTipoAnticipo(TipoAnticipoDto tipoAnticipo) {
		this.tipoAnticipo = tipoAnticipo;
	}

	public Boolean getPinValidationSuccess() {
		return pinValidationSuccess;
	}

	public void setPinValidationSuccess(Boolean pinValidationSuccess) {
		this.pinValidationSuccess = pinValidationSuccess;
	}

	public Boolean getScoringValidationSuccess() {
		return scoringValidationSuccess;
	}

	public void setScoringValidationSuccess(Boolean scoringValidationSuccess) {
		this.scoringValidationSuccess = scoringValidationSuccess;
	}

	public Long getIdVantive() {
		return idVantive;
	}

	public void setIdVantive(Long idVantive) {
		this.idVantive = idVantive;
	}

	public Boolean getVencimientoProcesado() {
		return vencimientoProcesado;
	}

	public void setVencimientoProcesado(Boolean vencimientoProcesado) {
		this.vencimientoProcesado = vencimientoProcesado;
	}

	public Boolean getGenerada() {
		return generada;
	}

	public void setGenerada(Boolean generada) {
		this.generada = generada;
	}

	public Boolean getEnCarga() {
		return enCarga;
	}

	public void setEnCarga(Boolean enCarga) {
		this.enCarga = enCarga;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}
