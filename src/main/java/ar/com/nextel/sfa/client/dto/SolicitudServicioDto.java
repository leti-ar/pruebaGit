package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author jlgperez
 * 
 */
public class SolicitudServicioDto implements IsSerializable {

	private Long id;
	private VendedorDto vendedor;
	private GrupoSolicitudDto grupoSolicitud;
	private CuentaSSDto cuenta;
	private String numero;
	private String numeroFlota;
	private Long nroTriptico;
    private String estado;
    private EstadoSolicitudDto estados;
	// private CuentaPotencial cuentaPotencial;
	private OrigenSolicitudDto origen;
	// private Vendedor vendedorDae;

	private Long idDomicilioFacturacion;
	private Long idDomicilioEnvio;
	private String aclaracionEntrega;
	private String email;
	private String emailLicencias;

	private Double montoCreditoFidelizacion;
	private Date fechaVencimientoCreditoFidelizacion;
	private Double montoDisponible; // cuenta.estadoCreditoFidelizacion.monto
	private Long channelCode;
	private Double pataconex;
    private Boolean retiraEnSucursal= Boolean.FALSE;
	private Boolean firmar = Boolean.FALSE;
	// private EstadoAprobacionSolicitud estadoActual;
	private String observaciones;

	private TipoAnticipoDto tipoAnticipo;

	private List<LineaSolicitudServicioDto> lineas = new ArrayList<LineaSolicitudServicioDto>();
	
	private List<EstadoPorSolicitudDto> historialEstados = new ArrayList<EstadoPorSolicitudDto>();

	private Boolean pinValidationSuccess;
	private Boolean scoringValidationSuccess;
	private Long idVantive;
	private Boolean vencimientoProcesado;
	private Boolean generada;
	private Boolean enCarga;
	private Date fechaCreacion;
	private double precioListaTotal = 0;
	private double precioVentaTotal = 0;
	private double precioItemTotal = 0;
    private ControlDto control;
	private long tripticoNumber;
	
	private SolicitudServicioGeneracionDto solicitudServicioGeneracion;
	
	//MGR - #1027
    private String ordenCompra;
    
    //MGR - #1013
    private String validacionPin;
    
    private CuentaDto cuentaCedente;
    private VendedorDto usuarioCreacion;
    private Long incidenteCedente;
    private List<ContratoViewDto> contratosCedidos = new ArrayList<ContratoViewDto>();
    
    private Long idSucursal;
    private Long tipoCanalVentas;
    
  //larce - Datos del histórico
    private Long cantidadEquiposH;
	private Date fechaFirma;
	private EstadoHistoricoDto estadoH;
	private Date fechaEstado;
	private String clienteHistorico;
    private Boolean passCreditos;
	private Long idConsultaVeraz;
	private Long idConsultaScoring;
    	//GB - si es customer o no
	private boolean customer;
	private VendedorDto vendedorLogueado;
	
	private Long cantLineasPortabilidad;

//	MGR - Facturacion
	private FacturaDto factura;
	private String customerNumberFactura;
	
    public SolicitudServicioDto() {
		solicitudServicioGeneracion = new SolicitudServicioGeneracionDto();
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

	public Long getIdDomicilioFacturacion() {
		return idDomicilioFacturacion;
	}

	public void setIdDomicilioFacturacion(Long idDomicilioFacturacion) {
		this.idDomicilioFacturacion = idDomicilioFacturacion;
	}

	public Long getIdDomicilioEnvio() {
		return idDomicilioEnvio;
	}

	public void setIdDomicilioEnvio(Long idDomicilioEnvio) {
		this.idDomicilioEnvio = idDomicilioEnvio;
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

	public List<LineaSolicitudServicioDto> getLineas() {
		return lineas;
	}

	public void setLineas(List<LineaSolicitudServicioDto> lineas) {
		this.lineas = lineas;
	}

	public CuentaSSDto getCuenta() {
		return cuenta;
	}

	public void setCuenta(CuentaSSDto cuenta) {
		this.cuenta = cuenta;
	}

	public SolicitudServicioGeneracionDto getSolicitudServicioGeneracion() {
		return solicitudServicioGeneracion;
	}

	public void setSolicitudServicioGeneracion(SolicitudServicioGeneracionDto solicitudServicioGeneracion) {
		this.solicitudServicioGeneracion = solicitudServicioGeneracion;
	}

	/** Calcula precioListaTotal y precioVentaTotal */
	public void refreshPreciosTotales() {
		precioListaTotal = 0;
		precioVentaTotal = 0;
		precioItemTotal = 0;
		for (LineaSolicitudServicioDto linea : getLineas()) {
			linea.refreshPrecioServiciosAdicionales();
			precioListaTotal = precioListaTotal + linea.getPrecioListaTotal();
			precioVentaTotal = precioVentaTotal + linea.getPrecioVentaTotal();
			precioItemTotal = precioItemTotal + linea.getPrecioVenta() + linea.getPrecioAlquilerVenta();
		}
	}

	/** Retorna precioListaTotal. Se debe llamar antes a refreshPreciosTotales() */
	public double getPrecioListaTotal() {
		return precioListaTotal;
	}

	/** Retorna precioVentaTotal. Se debe llamar antes a refreshPreciosTotales() */
	public double getPrecioVentaTotal() {
		return precioVentaTotal;
	}

	/**
	 * Retorna la suma de los precios de todos los items y AUVs. Se debe llamar antes a
	 * refreshPreciosTotales()
	 */
	public double getPrecioItemTotal() {
		return precioItemTotal;
	}
	
	public void setTripticoNumber(long tripticoNumber) {
		this.tripticoNumber = tripticoNumber;
	}

	public long getTripticoNumber() {
		// TODO Auto-generated method stub
		return this.tripticoNumber;
	}
	
	public String getOrdenCompra() {
		return ordenCompra;
	}

	public void setOrdenCompra(String ordenCompra) {
		this.ordenCompra = ordenCompra;
	}
	
	public Long getNroTriptico() {
		return nroTriptico;
	}

	public void setNroTriptico(Long nroTriptico) {
		this.nroTriptico = nroTriptico;
	}
	
	public String getValidacionPin() {
		return validacionPin;
	}

	public void setValidacionPin(String validacionPin) {
		this.validacionPin = validacionPin;
	}
	
	public CuentaDto getCuentaCedente() {
		return cuentaCedente;
	}

	public void setCuentaCedente(CuentaDto cuentaCedente) {
		this.cuentaCedente = cuentaCedente;
	}

	public VendedorDto getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(VendedorDto usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Long getIncidenteCedente() {
		return incidenteCedente;
	}

	public void setIncidenteCedente(Long incidenteCedente) {
		this.incidenteCedente = incidenteCedente;
	}

	public List<ContratoViewDto> getContratosCedidos() {
		return contratosCedidos;
	}

	public void setContratosCedidos(List<ContratoViewDto> contratosCedidos) {
		this.contratosCedidos = contratosCedidos;
	}

	public Long getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(Long idSucursal) {
		this.idSucursal = idSucursal;
	}
	
	public Long getTipoCanalVentas() {
		return tipoCanalVentas;
	}

	public void setTipoCanalVentas(Long tipoCanalVentas) {
		this.tipoCanalVentas = tipoCanalVentas;
	}
	
	public void setCantLineasPortabilidad(Long cantLineasPortabilidad){
		this.cantLineasPortabilidad = cantLineasPortabilidad;
	}
	
	public Long getCantLineasPortabilidad(){
		return cantLineasPortabilidad;
	}

	public Long getCantidadEquiposH() {
		return cantidadEquiposH;
	}

	public void setCantidadEquiposH(Long cantidadEquiposH) {
		this.cantidadEquiposH = cantidadEquiposH;
	}

	public Date getFechaFirma() {
		return fechaFirma;
	}

	public void setFechaFirma(Date fechaFirma) {
		this.fechaFirma = fechaFirma;
	}

	public EstadoHistoricoDto getEstadoH() {
		return estadoH;
	}

	public void setEstadoH(EstadoHistoricoDto estadoH) {
		this.estadoH = estadoH;
	}

	public Date getFechaEstado() {
		return fechaEstado;
	}

	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}
    
	public String getClienteHistorico() {
		return clienteHistorico;
	}
	
	public void setClienteHistorico(String clienteHistorico) {
		this.clienteHistorico = clienteHistorico;
	}
	

	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public EstadoSolicitudDto getEstados() {
		return estados;
	}

	public void setEstados(EstadoSolicitudDto estados) {
		this.estados = estados;
	}

	public void addHistorialEstados(EstadoPorSolicitudDto nuevoEstado) {
		this.historialEstados.add(nuevoEstado);
	}

	public List<EstadoPorSolicitudDto> getHistorialEstados() {
		return historialEstados;
	}

	public void setHistorialEstados(List<EstadoPorSolicitudDto> historialEstados) {
		this.historialEstados = historialEstados;
	}

	public ControlDto getControl() {
		return control;
	}

	public void setControl(ControlDto control) {
		this.control = control;
	}

	public void setPassCreditos(Boolean passCreditos) {
		this.passCreditos = passCreditos;
	}

	public Boolean getPassCreditos() {
		return passCreditos;
	}
	
	public boolean isCustomer() {
		return customer;
	}

	public void setCustomer(boolean customer) {
		this.customer = customer;
	}

	public Long getIdConsultaVeraz() {
		return idConsultaVeraz;
	}

	public void setIdConsultaVeraz(Long idConsultaVeraz) {
		this.idConsultaVeraz = idConsultaVeraz;
	}

	public Long getIdConsultaScoring() {
		return idConsultaScoring;
	}

	public void setIdConsultaScoring(Long idConsultaScoring) {
		this.idConsultaScoring = idConsultaScoring;
	}

	public VendedorDto getVendedorLogueado() {
		return vendedorLogueado;
	}

	public void setVendedorLogueado(VendedorDto vendedorLogueado) {
		this.vendedorLogueado = vendedorLogueado;
	}

	public Boolean getRetiraEnSucursal() {
		return retiraEnSucursal;
	}

	public void setRetiraEnSucursal(Boolean retiraEnSucursal) {
		this.retiraEnSucursal = retiraEnSucursal;
	}

	public FacturaDto getFactura() {
		return factura;
	}

	public void setFactura(FacturaDto factura) {
		this.factura = factura;
	}
	
	public String getCustomerNumberFactura() {
		return customerNumberFactura;
	}

	public void setCustomerNumberFactura(String customerNumberFactura) {
		this.customerNumberFactura = customerNumberFactura;
	}

	//	MGR - Facturacion	
	public Boolean isCuentaCorriene(){
		if(this.lineas.isEmpty())
        	return false;
        
        LineaSolicitudServicioDto linea = this.lineas.get(0);
        return linea.getTerminoPago().isCuentaCorriente();
	}
}