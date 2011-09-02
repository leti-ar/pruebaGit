package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import ar.com.nextel.model.solicitudes.beans.SolicitudServicio;
import ar.com.nextel.sfa.client.enums.CondicionCuentaEnum;
import ar.com.nextel.sfa.client.enums.TipoDocumentoEnum;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CuentaDto implements IsSerializable {

	private Long id;
	private ClaseCuentaDto claseCuenta;
	private Long idVantive;
	private CategoriaCuentaDto categoriaCuenta;
	private String codigoVantive;
	private CondicionCuentaDto condicionCuenta;
	private VendedorDto vendedor;
	private EstadoCuentaDto estadoCuenta;
	private FormaPagoDto formaPago;
	private DatosPagoDto datosPago;
	private String iibb;
	private Boolean responsablePago;
	private List<CuentaPotencialDto> cuentasPotenciales;
	private CuentaPotencialDto cuentaPotencialOrigen;
	private PersonaDto persona;
	private ProveedorDto proveedorInicial;
	private RubroDto rubro;
	private boolean solicitudesServicioEmpty;
	private TipoCanalVentasDto tipoCanalVentas;
	private TipoContribuyenteDto tipoContribuyente;
	private String use;
	private CicloFacturacionDto cicloFacturacion;
	private String observacionesTelMail;
	private String nombreUsuarioCreacion;
	private Date fechaCreacion;
	private FacturaElectronicaDto facturaElectronica;
	
	//MGR**** Adm_Vtas R2
	private List<CaratulaDto> caratulas = new ArrayList<CaratulaDto>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ClaseCuentaDto getClaseCuenta() {
		return claseCuenta;
	}

	public void setClaseCuenta(ClaseCuentaDto claseCuenta) {
		this.claseCuenta = claseCuenta;
	}

	public Long getIdVantive() {
		return idVantive;
	}

	public void setIdVantive(Long idVantive) {
		this.idVantive = idVantive;
	}

	public CategoriaCuentaDto getCategoriaCuenta() {
		return categoriaCuenta;
	}

	public void setCategoriaCuenta(CategoriaCuentaDto categoriaCuenta) {
		this.categoriaCuenta = categoriaCuenta;
	}

	public String getCodigoVantive() {
		return codigoVantive;
	}

	public void setCodigoVantive(String codigoVantive) {
		this.codigoVantive = codigoVantive;
	}

	public CondicionCuentaDto getCondicionCuenta() {
		return condicionCuenta;
	}

	public void setCondicionCuenta(CondicionCuentaDto condicionCuenta) {
		this.condicionCuenta = condicionCuenta;
	}

	// public Set<CuentaCorrienteDto> getCuentasCorrientes() {
	// return cuentasCorrientes;
	// }
	// public void setCuentasCorrientes(Set<CuentaCorrienteDto> cuentasCorrientes) {
	// this.cuentasCorrientes = cuentasCorrientes;
	// }
	public VendedorDto getVendedor() {
		return vendedor;
	}

	public void setVendedor(VendedorDto vendedor) {
		this.vendedor = vendedor;
	}

	public EstadoCuentaDto getEstadoCuenta() {
		return estadoCuenta;
	}

	public void setEstadoCuenta(EstadoCuentaDto estadoCuenta) {
		this.estadoCuenta = estadoCuenta;
	}

	public FormaPagoDto getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(FormaPagoDto formaPago) {
		this.formaPago = formaPago;
	}

	public DatosPagoDto getDatosPago() {
		return datosPago;
	}

	public void setDatosPago(DatosPagoDto datosPago) {
		this.datosPago = datosPago;
	}

	public String getIibb() {
		return iibb;
	}

	public void setIibb(String iibb) {
		this.iibb = iibb;
	}

	public Boolean getResponsablePago() {
		return responsablePago;
	}

	public void setResponsablePago(Boolean responsablePago) {
		this.responsablePago = responsablePago;
	}

	// public Set<PlanDto> getPlanesPropios() {
	// return planesPropios;
	// }
	// public void setPlanesPropios(Set<PlanDto> planesPropios) {
	// this.planesPropios = planesPropios;
	// }
	public List<CuentaPotencialDto> getCuentasPotenciales() {
		return cuentasPotenciales;
	}

	public void setCuentasPotenciales(List<CuentaPotencialDto> cuentasPotenciales) {
		this.cuentasPotenciales = cuentasPotenciales;
	}

	public CuentaPotencialDto getCuentaPotencialOrigen() {
		return cuentaPotencialOrigen;
	}

	public void setCuentaPotencialOrigen(CuentaPotencialDto cuentaPotencialOrigen) {
		this.cuentaPotencialOrigen = cuentaPotencialOrigen;
	}

	public PersonaDto getPersona() {
		return persona;
	}

	public void setPersona(PersonaDto persona) {
		this.persona = persona;
	}

	public ProveedorDto getProveedorInicial() {
		return proveedorInicial;
	}

	public void setProveedorInicial(ProveedorDto proveedorInicial) {
		this.proveedorInicial = proveedorInicial;
	}

	public RubroDto getRubro() {
		return rubro;
	}

	public void setRubro(RubroDto rubro) {
		this.rubro = rubro;
	}

	public boolean isSolicitudesServicioEmpty() {
		return solicitudesServicioEmpty;
	}

	public void setSolicitudesServicioEmpty(boolean solicitudesServicioEmpty) {
		this.solicitudesServicioEmpty = solicitudesServicioEmpty;
	}

	public TipoCanalVentasDto getTipoCanalVentas() {
		return tipoCanalVentas;
	}

	public void setTipoCanalVentas(TipoCanalVentasDto tipoCanalVentas) {
		this.tipoCanalVentas = tipoCanalVentas;
	}

	public TipoContribuyenteDto getTipoContribuyente() {
		return tipoContribuyente;
	}

	public void setTipoContribuyente(TipoContribuyenteDto tipoContribuyente) {
		this.tipoContribuyente = tipoContribuyente;
	}

	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public CicloFacturacionDto getCicloFacturacion() {
		return cicloFacturacion;
	}

	public void setCicloFacturacion(CicloFacturacionDto cicloFacturacion) {
		this.cicloFacturacion = cicloFacturacion;
	}

	// public String getPinMaestro() {
	// return pinMaestro;
	// }
	// public void setPinMaestro(String pinMaestro) {
	// this.pinMaestro = pinMaestro;
	// }
	// public EstadoCreditoFidelizacionDto getEstadoCreditoFidelizacion() {
	// return estadoCreditoFidelizacion;
	// }
	// public void setEstadoCreditoFidelizacion(
	// EstadoCreditoFidelizacionDto estadoCreditoFidelizacion) {
	// this.estadoCreditoFidelizacion = estadoCreditoFidelizacion;
	// }
	// public Boolean getEsPropia() {
	// return esPropia;
	// }
	// public void setEsPropia(Boolean esPropia) {
	// this.esPropia = esPropia;
	// }
	// public EstadoLockeoDto getEstadoLockeo() {
	// return estadoLockeo;
	// }
	// public void setEstadoLockeo(EstadoLockeoDto estadoLockeo) {
	// this.estadoLockeo = estadoLockeo;
	// }
	// public String getFlota() {
	// return flota;
	// }
	// public void setFlota(String flota) {
	// this.flota = flota;
	// }
	public String getObservacionesTelMail() {
		return observacionesTelMail;
	}

	public void setObservacionesTelMail(String observacionesTelMail) {
		this.observacionesTelMail = observacionesTelMail;
	}

	// public Double getLimiteCredito() {
	// return limiteCredito;
	// }
	// public void setLimiteCredito(Double limiteCredito) {
	// this.limiteCredito = limiteCredito;
	// }
	// public Long getFirmas() {
	// return firmas;
	// }
	// public void setFirmas(Long firmas) {
	// this.firmas = firmas;
	// }
	public String getNombreUsuarioCreacion() {
		return nombreUsuarioCreacion;
	}

	public void setNombreUsuarioCreacion(String nombreUsuarioCreacion) {
		this.nombreUsuarioCreacion = nombreUsuarioCreacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public boolean isProspect() {
		return getCondicionCuenta().getId() == CondicionCuentaEnum.PROSPECT.getId()
				|| getCondicionCuenta().getId() == CondicionCuentaEnum.PROSPECT_EN_CARGA.getId();
	}

	public boolean isCustomer() {
		return getCondicionCuenta().getId() == CondicionCuentaEnum.CUSTOMER.getId();
	}

	public boolean isEmpresa() {
		return TipoDocumentoEnum.CUIT.getCodigoVantive().equals(
				persona.getDocumento().getTipoDocumento().getCodigoVantive())
				&& persona.getDocumento().getNumero().startsWith("3");
	}

	public FacturaElectronicaDto getFacturaElectronica() {
		return facturaElectronica;
	}

	public void setFacturaElectronica(FacturaElectronicaDto facturaElectronica) {
		this.facturaElectronica = facturaElectronica;
	}

	public boolean isProspectEnCarga() {
		return getCondicionCuenta().getId() == CondicionCuentaEnum.PROSPECT_EN_CARGA.getId();
	}

	public List<CaratulaDto> getCaratulas() {
		return caratulas;
	}

	public void setCaratulas(List<CaratulaDto> caratulas) {
		this.caratulas = caratulas;
	}
}
