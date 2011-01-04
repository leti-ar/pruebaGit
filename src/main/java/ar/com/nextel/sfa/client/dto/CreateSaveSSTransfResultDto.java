package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CreateSaveSSTransfResultDto implements IsSerializable{

	private SolicitudServicioDto solicitud;
	private List<MessageDto> messages;
	private boolean error;
	
	public CreateSaveSSTransfResultDto(){
		messages = new ArrayList<MessageDto>();
		solicitud = null;
		error = false;
	}

	public SolicitudServicioDto getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(SolicitudServicioDto solicitud) {
		this.solicitud = solicitud;
	}

	public List<MessageDto> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageDto> messages) {
		this.messages = messages;
	}
	
	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
}
