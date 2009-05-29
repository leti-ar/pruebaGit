package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LineaSolicitudServicioDto implements IsSerializable, IdentifiableDto {

	private Long id;
	private Long idCabecera;

	private TipoSolicitudDto tipoSolicitud;
	private ItemSolicitudDto item;
	private PlanDto plan;

	private List<ServicioAdicionalLineaSolicitudServicioDto> serviciosAdicionales = new ArrayList();

	private ListaPreciosDto listaPrecios;
	private TerminoPagoDto terminoPago;
	// private List<DescuentoLinea> descuentos;

	private Long numeradorLinea;
	private Double precioLista;
	private Double precioVenta;
	private Double precioListaPlan;
	private Double precioListaAjustado;
	private Double precioVentaPlan;

	private double precioServiciosAdicionalesLista = 0;
	private double precioServiciosAdicionalesVenta = 0;
	private ServicioAdicionalLineaSolicitudServicioDto garantia = null;

	private ModalidadCobroDto modalidadCobro;

	private String numeroReserva;
	private String numeroReservaArea;
	private String numeroIMEI;
	private String numeroSimcard;
	private String numeroSerie;
	private List<NumeroANIDto> numerosANI;
	private String alias;
	private Integer cantidad;

	private LocalidadDto localidad;
	private ModeloDto modelo;

	private EstadoVerificacionNegativeFilesDto estadoVerificacionNegativeFilesIMEI;
	private EstadoVerificacionNegativeFilesDto estadoVerificacionNegativeFilesSIM;

	private Boolean ddn = Boolean.FALSE;
	private Boolean ddi = Boolean.FALSE;
	private Boolean roaming = Boolean.FALSE;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdCabecera() {
		return idCabecera;
	}

	public void setIdCabecera(Long idCabecera) {
		this.idCabecera = idCabecera;
	}

	public TipoSolicitudDto getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(TipoSolicitudDto tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	public ItemSolicitudDto getItem() {
		return item;
	}

	public void setItem(ItemSolicitudDto item) {
		this.item = item;
	}

	public PlanDto getPlan() {
		return plan;
	}

	public void setPlan(PlanDto plan) {
		this.plan = plan;
	}

	public List<ServicioAdicionalLineaSolicitudServicioDto> getServiciosAdicionales() {
		return serviciosAdicionales;
	}

	public void setServiciosAdicionales(List<ServicioAdicionalLineaSolicitudServicioDto> serviciosAdicionales) {
		this.serviciosAdicionales = serviciosAdicionales;
	}

	public ListaPreciosDto getListaPrecios() {
		return listaPrecios;
	}

	public void setListaPrecios(ListaPreciosDto listaPrecios) {
		this.listaPrecios = listaPrecios;
	}

	public TerminoPagoDto getTerminoPago() {
		return terminoPago;
	}

	public void setTerminoPago(TerminoPagoDto terminoPago) {
		this.terminoPago = terminoPago;
	}

	public Long getNumeradorLinea() {
		return numeradorLinea;
	}

	public void setNumeradorLinea(Long numeradorLinea) {
		this.numeradorLinea = numeradorLinea;
	}

	public Double getPrecioLista() {
		return precioLista;
	}

	public void setPrecioLista(Double precioLista) {
		this.precioLista = precioLista;
	}

	public Double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(Double precioVenta) {
		this.precioVenta = precioVenta;
	}

	public Double getPrecioListaPlan() {
		return precioListaPlan;
	}

	public void setPrecioListaPlan(Double precioListaPlan) {
		this.precioListaPlan = precioListaPlan;
	}

	public Double getPrecioListaAjustado() {
		return precioListaAjustado;
	}

	public void setPrecioListaAjustado(Double precioListaAjustado) {
		this.precioListaAjustado = precioListaAjustado;
	}

	public Double getPrecioVentaPlan() {
		return precioVentaPlan;
	}

	public void setPrecioVentaPlan(Double precioVentaPlan) {
		this.precioVentaPlan = precioVentaPlan;
	}

	public ModalidadCobroDto getModalidadCobro() {
		return modalidadCobro;
	}

	public void setModalidadCobro(ModalidadCobroDto modalidadCobro) {
		this.modalidadCobro = modalidadCobro;
	}

	public String getNumeroReserva() {
		return numeroReserva;
	}

	public void setNumeroReserva(String numeroReserva) {
		this.numeroReserva = numeroReserva;
	}

	public String getNumeroReservaArea() {
		return numeroReservaArea;
	}

	public void setNumeroReservaArea(String numeroReservaArea) {
		this.numeroReservaArea = numeroReservaArea;
	}

	public String getNumeroIMEI() {
		return numeroIMEI;
	}

	public void setNumeroIMEI(String numeroIMEI) {
		this.numeroIMEI = numeroIMEI;
	}

	public String getNumeroSimcard() {
		return numeroSimcard;
	}

	public void setNumeroSimcard(String numeroSimcard) {
		this.numeroSimcard = numeroSimcard;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	// public List<NumeroANIDto> getNumerosANI() {
	// return numerosANI;
	// }
	//
	// public void setNumerosANI(List<NumeroANIDto> numerosANI) {
	// this.numerosANI = numerosANI;
	// }

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public EstadoVerificacionNegativeFilesDto getEstadoVerificacionNegativeFilesIMEI() {
		return estadoVerificacionNegativeFilesIMEI;
	}

	public void setEstadoVerificacionNegativeFilesIMEI(
			EstadoVerificacionNegativeFilesDto estadoVerificacionNegativeFilesIMEI) {
		this.estadoVerificacionNegativeFilesIMEI = estadoVerificacionNegativeFilesIMEI;
	}

	public EstadoVerificacionNegativeFilesDto getEstadoVerificacionNegativeFilesSIM() {
		return estadoVerificacionNegativeFilesSIM;
	}

	public void setEstadoVerificacionNegativeFilesSIM(
			EstadoVerificacionNegativeFilesDto estadoVerificacionNegativeFilesSIM) {
		this.estadoVerificacionNegativeFilesSIM = estadoVerificacionNegativeFilesSIM;
	}

	public Boolean getDdn() {
		return ddn;
	}

	public void setDdn(Boolean ddn) {
		this.ddn = ddn;
	}

	public Boolean getDdi() {
		return ddi;
	}

	public void setDdi(Boolean ddi) {
		this.ddi = ddi;
	}

	public Boolean getRoaming() {
		return roaming;
	}

	public void setRoaming(Boolean roaming) {
		this.roaming = roaming;
	}

	public LocalidadDto getLocalidad() {
		return localidad;
	}

	public void setLocalidad(LocalidadDto localidad) {
		this.localidad = localidad;
	}

	public ModeloDto getModelo() {
		return modelo;
	}

	public void setModelo(ModeloDto modelo) {
		this.modelo = modelo;
	}

	/** Actualiza precioServiciosAdicionalesLista, precioServiciosAdicionalesVenta y precioGarantia */
	public void refreshPrecioServiciosAdicionales() {
		precioServiciosAdicionalesLista = 0;
		precioServiciosAdicionalesVenta = 0;

		for (ServicioAdicionalLineaSolicitudServicioDto servicioAd : serviciosAdicionales) {
			if (servicioAd.isChecked()) {
				if (!servicioAd.isEsGarantia()) {
					precioServiciosAdicionalesLista = precioServiciosAdicionalesLista
							+ servicioAd.getPrecioLista();
					precioServiciosAdicionalesVenta = precioServiciosAdicionalesVenta
							+ servicioAd.getPrecioVenta();
				} else {
					garantia = servicioAd;
				}
			}
		}
	}
	
	// XXX: "Mirar el valor de los servicios adicionales"

	/** Obtiene precioServiciosAdicionalesLista. Llamar primero a refreshPrecioServiciosAdicionales */
	public double getPrecioServiciosAdicionalesLista() {
		return precioServiciosAdicionalesLista;
	}

	/** Obtiene precioServiciosAdicionalesVenta. Llamar primero a refreshPrecioServiciosAdicionales */
	public double getPrecioServiciosAdicionalesVenta() {
		return precioServiciosAdicionalesVenta;
	}

	/** Obtiene precioGarantiaLista. Llamar primero a refreshPrecioServiciosAdicionales */
	public double getPrecioGarantiaLista() {
		if (garantia != null)
			return garantia.getPrecioLista();
		return 0;
	}

	/** Obtiene precioGarantiaVenta. Llamar primero a refreshPrecioServiciosAdicionales */
	public double getPrecioGarantiaVenta() {
		if (garantia != null)
			return garantia.getPrecioVenta();
		return 0;
	}

}
