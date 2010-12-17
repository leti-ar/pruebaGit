package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LineaTransfSolicitudServicioDto implements IsSerializable, IdentifiableDto, Cloneable {
	
	private Long id;
	private Long idCabecera;
	private Long contrato;
	private Date fechaEstadoContrato;
	private String telefono;
    private String flotaId;
    private String modelo;
    private String contratacion;
    private PlanDto planNuevo;
    private Double precioVtaPlanNuevo;
    private ModalidadCobroDto modalidadCobro;
    private String customerNumber;
    private Long codigoBSCSPlanCedente;
    private Double precioPlanCedente;
    private String modalidadCobroPlanCedente;
    private String numeroIMEI;
    private String simCard;
    private String numeroSerie;
    private Long numeradorLinea;
    private Long idVantiveLinea;
    private Long idVantiveDetalle;
    private String descripcionPlanCedente;
    private Long idTipoTelefoniaCedente;
	
    private List<ServicioAdicionalLineaTransfSolicitudServicioDto> serviciosAdicionales = new ArrayList();
    
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
	
	public Long getContrato() {
		return contrato;
	}
	
	public void setContrato(Long contrato) {
		this.contrato = contrato;
	}
	
	public Date getFechaEstadoContrato() {
		return fechaEstadoContrato;
	}
	
	public void setFechaEstadoContrato(Date fechaEstadoContrato) {
		this.fechaEstadoContrato = fechaEstadoContrato;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getFlotaId() {
		return flotaId;
	}
	
	public void setFlotaId(String flotaId) {
		this.flotaId = flotaId;
	}
	
	public String getModelo() {
		return modelo;
	}
	
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	public String getContratacion() {
		return contratacion;
	}
	
	public void setContratacion(String contratacion) {
		this.contratacion = contratacion;
	}
	
	public PlanDto getPlanNuevo() {
		return planNuevo;
	}
	
	public void setPlanNuevo(PlanDto planNuevo) {
		this.planNuevo = planNuevo;
	}
	
	public Double getPrecioVtaPlanNuevo() {
		return precioVtaPlanNuevo;
	}
	
	public void setPrecioVtaPlanNuevo(Double precioVtaPlanNuevo) {
		this.precioVtaPlanNuevo = precioVtaPlanNuevo;
	}
	
	public ModalidadCobroDto getModalidadCobro() {
		return modalidadCobro;
	}
	
	public void setModalidadCobro(ModalidadCobroDto modalidadCobro) {
		this.modalidadCobro = modalidadCobro;
	}
	
	public String getCustomernumber() {
		return customerNumber;
	}
	
	public void setCustomernumber(String customernumber) {
		this.customerNumber = customernumber;
	}
	
	public Long getCodigoBSCSPlanCedente() {
		return codigoBSCSPlanCedente;
	}
	
	public void setCodigoBSCSPlanCedente(Long codigoBSCSPlanCedente) {
		this.codigoBSCSPlanCedente = codigoBSCSPlanCedente;
	}
	
	public Double getPrecioPlanCedente() {
		return precioPlanCedente;
	}
	
	public void setPrecioPlanCedente(Double precioPlanCedente) {
		this.precioPlanCedente = precioPlanCedente;
	}
	
	public String getModalidadCobroPlanCedente() {
		return modalidadCobroPlanCedente;
	}
	
	public void setModalidadCobroPlanCedente(String modalidadCobroPlanCedente) {
		this.modalidadCobroPlanCedente = modalidadCobroPlanCedente;
	}
	
	public String getNumeroIMEI() {
		return numeroIMEI;
	}
	
	public void setNumeroIMEI(String numeroIMEI) {
		this.numeroIMEI = numeroIMEI;
	}
	
	public String getSimCard() {
		return simCard;
	}
	
	public void setSimCard(String simCard) {
		this.simCard = simCard;
	}
	
	public String getNumeroSerie() {
		return numeroSerie;
	}
	
	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}
	
	public Long getNumeradorLinea() {
		return numeradorLinea;
	}
	
	public void setNumeradorLinea(Long numeradorLinea) {
		this.numeradorLinea = numeradorLinea;
	}
	
	public Long getIdVantiveLinea() {
		return idVantiveLinea;
	}
	
	public void setIdVantiveLinea(Long idVantiveLinea) {
		this.idVantiveLinea = idVantiveLinea;
	}
	
	public Long getIdVantiveDetalle() {
		return idVantiveDetalle;
	}
	
	public void setIdVantiveDetalle(Long idVantiveDetalle) {
		this.idVantiveDetalle = idVantiveDetalle;
	}
	
	public String getDescripcionPlanCedente() {
		return descripcionPlanCedente;
	}
	
	public void setDescripcionPlanCedente(String descripcionPlanCedente) {
		this.descripcionPlanCedente = descripcionPlanCedente;
	}
	
	public Long getIdTipoTelefoniaCedente() {
		return idTipoTelefoniaCedente;
	}

	public void setIdTipoTelefoniaCedente(Long idTipoTelefoniaCedente) {
		this.idTipoTelefoniaCedente = idTipoTelefoniaCedente;
	}

	public List<ServicioAdicionalLineaTransfSolicitudServicioDto> getServiciosAdicionales() {
		return serviciosAdicionales;
	}

	public void setServiciosAdicionales(
			List<ServicioAdicionalLineaTransfSolicitudServicioDto> serviciosAdicionales) {
		this.serviciosAdicionales = serviciosAdicionales;
	}
}
