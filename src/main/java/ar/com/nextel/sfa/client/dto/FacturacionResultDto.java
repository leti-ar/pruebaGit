package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
/**
 * Clase que representa el resultado de realizar la facturacion de una
 * Solicitud de servicio
 * 
 * @author mrotger
 */
public class FacturacionResultDto implements IsSerializable{

	private List<MessageDto> messages;
	private boolean error;
	private SolicitudServicioDto solicitud;
	
	public FacturacionResultDto(){
		this.error = false;
		messages = new ArrayList<MessageDto>();
		solicitud = null;
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

	public SolicitudServicioDto getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(SolicitudServicioDto solicitud) {
		this.solicitud = solicitud;
	}
	
	public void addMessage(String mensaje){
		MessageDto mens = new MessageDto();
		mens.setDescription(mensaje);
		messages.add(mens);
	}
}
