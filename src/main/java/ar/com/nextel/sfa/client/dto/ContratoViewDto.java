package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ContratoViewDto implements IsSerializable, IdentifiableDto{
	
	private Long id;
	private Long contrato;
	private Date fechaEstado;
	private String telefono;
	private String flotaId;
	private String modelo;
	private String contratacion;
	private String os;
	private String modalidad;
	private String suscriptor;
	private String numeroIMEI;
	private String numeroSimCard;
	private String numeroSerie;
	private String codVantiveCesionario;
	
	private Long idPlanCedente; //TMCODE //**
	private String planCedente;
	//Cuando el Plan Cedente no existe es SFA, aqui se guarda el Id del Plan Cedente
	private Long codigoBSCSPlanCedente;
	private Double precioPlanCedente;
	private Long gamaPlanCedente;
	
	private PlanDto planCesionario; //Plan Cesionario
	private boolean isPinchado = false;
	private List<ServicioAdicionalIncluidoDto> serviciosAdicionalesInc = new ArrayList<ServicioAdicionalIncluidoDto>();
	
	private Long idTipoTelefoniaCedente;
	private Double cargosPermanencia;
	private int mesesPermanencia;
	
	public Long getContrato() {
		return contrato;
	}

	public void setContrato(Long contrato) {
		this.contrato = contrato;
	}

	public Date getFechaEstado() {
		return fechaEstado;
	}

	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
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

	public Long getIdPlanCedente() {
		return idPlanCedente;
	}

	public void setIdPlanCedente(Long idPlanCedente) {
		this.idPlanCedente = idPlanCedente;
	}
	
	public String getPlanCedente() {
		return planCedente;
	}

	public void setPlanCedente(String planCedente) {
		this.planCedente = planCedente;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getSuscriptor() {
		return suscriptor;
	}

	public void setSuscriptor(String suscriptor) {
		this.suscriptor = suscriptor;
	}
	
	public String getNumeroIMEI() {
		return numeroIMEI;
	}

	public void setNumeroIMEI(String numeroIMEI) {
		this.numeroIMEI = numeroIMEI;
	}

	public String getNumeroSimCard() {
		return numeroSimCard;
	}

	public void setNumeroSimCard(String numeroSimCard) {
		this.numeroSimCard = numeroSimCard;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}
	
	public String getCodVantiveCesionario() {
		return codVantiveCesionario;
	}

	public void setCodVantiveCesionario(String codVantiveCesionario) {
		this.codVantiveCesionario = codVantiveCesionario;
	}

	public PlanDto getPlanCesionario() {
		return planCesionario;
	}

	public void setPlanCesionario(PlanDto planCesionario) {
		this.planCesionario = planCesionario;
	}
	
	public boolean isPinchado() {
		return isPinchado;
	}

	public void setPinchado(boolean isPinchado) {
		this.isPinchado = isPinchado;
	}
	
	public List<ServicioAdicionalIncluidoDto> getServiciosAdicionalesInc() {
		return serviciosAdicionalesInc;
	}

	public void setServiciosAdicionalesInc(
			List<ServicioAdicionalIncluidoDto> serviciosAdicionalesInc) {
		this.serviciosAdicionalesInc = serviciosAdicionalesInc;
	}

	@Override
	public int hashCode() {
		if (this.contrato!=null) {
			return this.contrato.intValue();
		}
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean equals = false;
		if (obj instanceof ContratoViewDto){
			ContratoViewDto dto2 = (ContratoViewDto)obj;
			if(this.contrato == null)
				return false;
			if(dto2.getContrato() == null)
				return false;
			
			if(dto2.getContrato().equals(this.contrato)){
				equals = true;
			}
		}
		return equals;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void agregarServicioAdicional(
			ServicioAdicionalIncluidoDto servicioSelected) {
		servicioSelected.setIdContrato(id);
		serviciosAdicionalesInc.add(servicioSelected);
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

	public void setIdTipoTelefoniaCedente(Long idTipoTelefoniaCedente) {
		this.idTipoTelefoniaCedente = idTipoTelefoniaCedente;
	}

	public Long getIdTipoTelefoniaCedente() {
		return idTipoTelefoniaCedente;
	}

	public void setCargosPermanencia(Double cargosPermanencia) {
		this.cargosPermanencia = cargosPermanencia;
	}

	public Double getCargosPermanencia() {
		return cargosPermanencia;
	}

	public void setMesesPermanencia(int mesesPermanencia) {
		this.mesesPermanencia = mesesPermanencia;
	}

	public int getMesesPermanencia() {
		return mesesPermanencia;
	}

	public void setGamaPlanCedente(Long gamaPlanCedente) {
		this.gamaPlanCedente = gamaPlanCedente;
	}

	public Long getGamaPlanCedente() {
		return gamaPlanCedente;
	}
	
}
