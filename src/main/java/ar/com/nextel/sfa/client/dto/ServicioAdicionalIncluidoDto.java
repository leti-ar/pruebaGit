package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ServicioAdicionalIncluidoDto implements IsSerializable, IdentifiableDto{
	
	//para ServicioAdicionalLineaTransfSolicitudServicio mapea a servicioAdicional.id
	//para ServicioAdicionalIncluido mapea a id
	private Long id;
	
	private Double precioFinal;
	private Boolean obligatorio;
	private ServicioAdicionalDto servicioAdicional;
	private PlanDto plan;
	private Boolean visible;
	private Boolean servicioAdicionalXDefault;
	private boolean checked;

	private Long idContrato; //Id del contrato al que pertenece (en SS Transferencias)
	private Long idSALineaTransf; //Id de ServicioAdicionalLineaTransfSolicitudServicio
	
	public Double getPrecioFinal() {
		return precioFinal;
	}

	public void setPrecioFinal(Double precioFinal) {
		this.precioFinal = precioFinal;
	}

	public Boolean getObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(Boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

	public ServicioAdicionalDto getServicioAdicional() {
		return servicioAdicional;
	}

	public void setServicioAdicional(ServicioAdicionalDto servicioAdicional) {
		this.servicioAdicional = servicioAdicional;
	}

	public PlanDto getPlan() {
		return plan;
	}

	public void setPlan(PlanDto plan) {
		this.plan = plan;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Boolean getServicioAdicionalXDefault() {
		return servicioAdicionalXDefault;
	}

	public void setServicioAdicionalXDefault(Boolean servicioAdicionalXDefault) {
		this.servicioAdicionalXDefault = servicioAdicionalXDefault;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		Long id = servicioAdicional != null ? servicioAdicional.getId() : null;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		ServicioAdicionalIncluidoDto other = (ServicioAdicionalIncluidoDto) obj;
		if (servicioAdicional.getId() == null) {
			if (other.servicioAdicional.getId() != null)
				return false;
		} else if (!servicioAdicional.getId().equals(other.servicioAdicional.getId()))
			return false;
		return true;
	}
	
	//MGR****
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public Long getIdSALineaTransf() {
		return idSALineaTransf;
	}

	public void setIdSALineaTransf(Long idSALineaTransf) {
		this.idSALineaTransf = idSALineaTransf;
	}
}
