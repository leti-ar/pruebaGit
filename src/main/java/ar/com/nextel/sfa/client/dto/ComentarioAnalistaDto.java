package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ComentarioAnalistaDto implements ListBoxItem, IsSerializable {

	private long estadoSolicitud;
	private String comentario;

	public ComentarioAnalistaDto() {
	}

	public ComentarioAnalistaDto(long estadoSolicitud, String comentario) {
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

	public long getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(long estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
}