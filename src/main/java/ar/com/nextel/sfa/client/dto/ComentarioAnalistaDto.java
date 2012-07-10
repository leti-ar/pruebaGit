package ar.com.nextel.sfa.client.dto;


import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ComentarioAnalistaDto implements ListBoxItem, IsSerializable {

	private EstadoSolicitudDto estadoSolicitud;
	private String comentario;

	public ComentarioAnalistaDto() {
	}

	public ComentarioAnalistaDto(EstadoSolicitudDto estadoSolicitud, String comentario) {
		super();
		this.estadoSolicitud = estadoSolicitud;
		this.comentario = comentario;
	}

	public String getItemText() {
		return comentario;
	}

	public String getItemValue() {
		return estadoSolicitud + "";
	}

	
	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public EstadoSolicitudDto getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(EstadoSolicitudDto estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	
}