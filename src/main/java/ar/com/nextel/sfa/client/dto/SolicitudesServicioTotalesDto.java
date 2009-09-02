package ar.com.nextel.sfa.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SolicitudesServicioTotalesDto implements IsSerializable {

	private List<SolicitudServicioCerradaDto> solicitudes;
	private Long totalEquipos;
	private Double totalPataconex;
	private Long totalEquiposFirmados;

	public List<SolicitudServicioCerradaDto> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(List<SolicitudServicioCerradaDto> solicitudes) {
		this.solicitudes = solicitudes;
	}

	public Long getTotalEquipos() {
		return totalEquipos;
	}

	public void setTotalEquipos(Long totalEquipos) {
		this.totalEquipos = totalEquipos;
	}

	public Double getTotalPataconex() {
		return totalPataconex;
	}

	public void setTotalPataconex(Double totalPataconex) {
		this.totalPataconex = totalPataconex;
	}

	public Long getTotalEquiposFirmados() {
		return totalEquiposFirmados;
	}

	public void setTotalEquiposFirmados(Long totalEquiposFirmados) {
		this.totalEquiposFirmados = totalEquiposFirmados;
	}

}
