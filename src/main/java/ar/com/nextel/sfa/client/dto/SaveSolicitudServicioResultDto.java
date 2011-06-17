package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

//MGR - ISDN 1824 - Creada para poder manejar mensajes
public class SaveSolicitudServicioResultDto  implements IsSerializable{

	private SolicitudServicioDto solicitud;
	private List<MessageDto> messages;
	private boolean error;
	
	public SaveSolicitudServicioResultDto(){
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
	
	public void addMessage(String mensaje){
		MessageDto mens = new MessageDto();
		mens.setDescription(mensaje);
		messages.add(mens);
	}
}
