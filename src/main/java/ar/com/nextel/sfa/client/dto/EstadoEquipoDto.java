package ar.com.nextel.sfa.client.dto;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EstadoEquipoDto implements IsSerializable {
	
	private int cuenta;
	private String plan;
	private String modelo;
	private String tipoTel;
	private String contratacion;
	private Date fecha;
	private int contrato;
	private String motivo;
	private int solicitud;

	public int getCuenta() {
		return cuenta;
	}

	public void setCuenta(int cuenta) {
		this.cuenta = cuenta;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getTipoTel() {
		return tipoTel;
	}

	public void setTipoTel(String tipoTel) {
		this.tipoTel = tipoTel;
	}

	public String getContratacion() {
		return contratacion;
	}

	public void setContratacion(String contratacion) {
		this.contratacion = contratacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getContrato() {
		return contrato;
	}

	public void setContrato(int contrato) {
		this.contrato = contrato;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public int getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(int solicitud) {
		this.solicitud = solicitud;
	}
}
