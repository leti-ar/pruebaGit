package ar.com.nextel.sfa.client.dto;

import java.sql.Date;

import ar.com.nextel.sfa.client.widget.ContratoConChinche;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ContratoViewDto implements IsSerializable{
	
	private Long id;
	private Date fechaEstado;
	private String telefono;
	private String flotaId;
	private String modelo;
	private String contratacion;
	private Long idPlanCedente; //TMCODE
	private String planCedente;
	private Long planCesionario;
	private String precioPlanCesionario;
	private String Os;
	private String modalidad;
	private String suscriptor;
	private String numeroIMEI;
	private String numeroSimCard;
	private String numeroSerie;	
	private PlanDto plan;
	private boolean isPinchado = false;
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getPlanCesionario() {
		return planCesionario;
	}

	public void setPlanCesionario(Long planCesionario) {
		this.planCesionario = planCesionario;
	}

	public String getPrecioPlanCesionario() {
		return precioPlanCesionario;
	}

	public void setPrecioPlanCesionario(String precioPlanCesionario) {
		this.precioPlanCesionario = precioPlanCesionario;
	}

	public String getOs() {
		return Os;
	}

	public void setOs(String os) {
		Os = os;
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
	
	public PlanDto getPlan() {
		return plan;
	}

	public void setPlan(PlanDto plan) {
		this.plan = plan;
	}

	public boolean isPinchado() {
		return isPinchado;
	}

	public void setPinchado(boolean isPinchado) {
		this.isPinchado = isPinchado;
	}

	@Override
	public int hashCode() {
		return this.id.intValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean equals = false;
		if (obj instanceof ContratoViewDto){
			ContratoViewDto dto2 = (ContratoViewDto)obj;
			if(dto2.getId().equals(this.id)){
				equals = true;
			}
		}
		return equals;
	}

}
