package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ServicioAdicionalIncluidoDto implements IsSerializable {
	private Double precioFinal;
	private Boolean obligatorio;
	private ServicioAdicionalDto servicioAdicional;
	private PlanDto plan;
	private Boolean visible;
	private Boolean servicioAdicionalXDefault;
	private boolean checked;

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

}
