package ar.com.nextel.sfa.client.dto;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CambiosSolicitudServicioDto implements IsSerializable {

	private String fechaCambioEstado;
	private String estadoAprobacionSolicitud;
	private String comentario;



	public String getFechaCambioEstado() {
		return fechaCambioEstado;
	}

	public void setFechaCambioEstado(String fechaCambioEstado) {
		this.fechaCambioEstado = fechaCambioEstado;
	}

	public String getEstadoAprobacionSolicitud() {
		return estadoAprobacionSolicitud;
	}

	public void setEstadoAprobacionSolicitud(String estadoAprobacionSolicitud) {
		this.estadoAprobacionSolicitud = estadoAprobacionSolicitud;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

}
