package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import ar.com.nextel.model.solicitudes.beans.LineaSolicitudServicio;
import ar.com.nextel.model.support.owner.CollectionOwnerCreator;

import com.google.gwt.dev.util.collect.HashSet;
import com.google.gwt.user.client.rpc.IsSerializable;

public class LineaSolicitudServicioDto implements IsSerializable, IdentifiableDto, Cloneable {

	private Long id;
	private Long idCabecera;

	private TipoSolicitudDto tipoSolicitud;
	private ItemSolicitudDto item;
	private PlanDto plan;

	private List<ServicioAdicionalLineaSolicitudServicioDto> serviciosAdicionales = new ArrayList();

	private ListaPreciosDto listaPrecios;
	private TerminoPagoDto terminoPago;

	private Long numeradorLinea;
	private double precioLista = 0d;
	private double precioVenta = 0d;
	private double precioListaPlan = 0d;
	private double precioListaAjustado = 0d;
	private double precioVentaPlan = 0d;

	private double precioServiciosAdicionalesLista = 0;
	private double precioServiciosAdicionalesVenta = 0;
	private ServicioAdicionalLineaSolicitudServicioDto garantia = null;
	private ServicioAdicionalLineaSolicitudServicioDto alquiler = null;

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
	private Boolean fullPrice = Boolean.FALSE;
	
	private Double precioConDescuento;
	private Double monto;
	private Double porcentaje;
	private List<DescuentoLineaDto> descuentosLinea = new ArrayList<DescuentoLineaDto>();

	private List<SolicitudPortabilidadDto> solicitudPortabilidad = new ArrayList<SolicitudPortabilidadDto>();
	

	public List<SolicitudPortabilidadDto> getSolicitudPortabilidad() {
		return solicitudPortabilidad;
	}

	public void setSolicitudPortabilidad(List<SolicitudPortabilidadDto> solicitudPortabilidad) {
		this.solicitudPortabilidad = solicitudPortabilidad;
	}
	
	public SolicitudPortabilidadDto getPortabilidad() {
		if(this.solicitudPortabilidad.size() > 0) return solicitudPortabilidad.get(0);
		return null;
	}

	public void setPortabilidad(SolicitudPortabilidadDto solicitudPortabilidad) {
		if(this.solicitudPortabilidad.size() > 0) this.solicitudPortabilidad.clear();
		this.solicitudPortabilidad.add(solicitudPortabilidad);
	}

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

	public double getPrecioLista() {
		return precioLista;
	}

	public void setPrecioLista(double precioLista) {
		this.precioLista = precioLista;
	}

	public double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(double precioVenta) {
		this.precioVenta = precioVenta;
	}

	public double getPrecioListaPlan() {
		return precioListaPlan;
	}

	public void setPrecioListaPlan(double precioListaPlan) {
		this.precioListaPlan = precioListaPlan;
	}

	public double getPrecioListaAjustado() {
		return precioListaAjustado;
	}

	public void setPrecioListaAjustado(double precioListaAjustado) {
		this.precioListaAjustado = precioListaAjustado;
	}

	public double getPrecioVentaPlan() {
		return precioVentaPlan;
	}

	public void setPrecioVentaPlan(double precioVentaPlan) {
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
		garantia = null;
		alquiler = null;

		for (ServicioAdicionalLineaSolicitudServicioDto servicioAd : serviciosAdicionales) {
			if (servicioAd.isChecked()) {
				if (!servicioAd.isEsGarantia() && !servicioAd.isEsAlquiler()) {
					precioServiciosAdicionalesLista = precioServiciosAdicionalesLista
							+ servicioAd.getPrecioLista();
					precioServiciosAdicionalesVenta = precioServiciosAdicionalesVenta
							+ servicioAd.getPrecioVenta();
				} else if (servicioAd.isEsGarantia()) {
					garantia = servicioAd;
				} else if (servicioAd.isEsAlquiler()) {
					alquiler = servicioAd;
				}
			}
		}
	}

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

	/** Obtiene precioAlquilerLista. Llamar primero a refreshPrecioServiciosAdicionales */
	public double getPrecioAlquilerLista() {
		if (alquiler != null)
			return alquiler.getPrecioLista();
		return 0;
	}

	/** Obtiene precioAlquilerVenta. Llamar primero a refreshPrecioServiciosAdicionales */
	public double getPrecioAlquilerVenta() {
		if (alquiler != null)
			return alquiler.getPrecioVenta();
		return 0;
	}

	public double getPrecioListaTotal() {
		//MGR - #1135
		double total = getPrecioLista() + getPrecioListaPlan() + getPrecioServiciosAdicionalesLista()
				+ getPrecioGarantiaLista() + getPrecioAlquilerLista();
		
		return (total * this.getCantidad());
	}

	public double getPrecioVentaTotal() {
		//MGR - #1135
		double total = getPrecioVenta() + getPrecioVentaPlan() + getPrecioServiciosAdicionalesVenta()
				+ getPrecioGarantiaVenta() + getPrecioAlquilerVenta();
		return (total * this.getCantidad());
	}

	public LineaSolicitudServicioDto clone() {
		LineaSolicitudServicioDto linea = new LineaSolicitudServicioDto();

		linea.tipoSolicitud = tipoSolicitud;
		linea.item = item;
		linea.plan = plan;

		for (ServicioAdicionalLineaSolicitudServicioDto servicio : serviciosAdicionales) {
			linea.getServiciosAdicionales().add(servicio.clone());
		}

		linea.listaPrecios = listaPrecios;
		linea.terminoPago = terminoPago;
		linea.numeradorLinea = numeradorLinea;
		linea.precioLista = precioLista;
		linea.precioVenta = precioVenta;
		linea.precioListaPlan = precioListaPlan;
		linea.precioListaAjustado = precioListaAjustado;
		linea.precioVentaPlan = precioVentaPlan;
		linea.precioServiciosAdicionalesLista = precioServiciosAdicionalesLista;
		linea.precioServiciosAdicionalesVenta = precioServiciosAdicionalesVenta;
		linea.garantia = garantia;
		linea.modalidadCobro = modalidadCobro;
		linea.numeroReserva = numeroReserva;
		linea.numeroReservaArea = numeroReservaArea;
		linea.numeroIMEI = numeroIMEI;
		linea.numeroSimcard = numeroSimcard;
		linea.numeroSerie = numeroSerie;
		linea.numerosANI = numerosANI;
		linea.alias = alias;
		linea.cantidad = cantidad;
		linea.localidad = localidad;
		linea.modelo = modelo;
		linea.estadoVerificacionNegativeFilesIMEI = estadoVerificacionNegativeFilesIMEI;
		linea.estadoVerificacionNegativeFilesSIM = estadoVerificacionNegativeFilesSIM;
		linea.ddn = ddn;
		linea.ddi = ddi;
		linea.roaming = roaming;
		linea.fullPrice = fullPrice;
		linea.precioConDescuento = precioConDescuento;
		return linea;
	}
	
	public Double getPrecioConDescuento() {
		return precioConDescuento;
	}
	
	public void setPrecioConDescuento(Double precioConDescuento) {
		this.precioConDescuento = precioConDescuento;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}
	
	public List<DescuentoLineaDto> getDescuentosLinea() {
		return descuentosLinea;
	}
	
	public void setDescuentosLinea(List<DescuentoLineaDto> descuentosLinea) {
		this.descuentosLinea = descuentosLinea;
	}

    public void addDescuentoLinea(DescuentoLineaDto descuento) {
        this.descuentosLinea.add(descuento);
    }

	public void setFullPrice(Boolean fullPrice) {
		this.fullPrice = fullPrice;
	}

	public Boolean getFullPrice() {
		return fullPrice;
	}
    
}
