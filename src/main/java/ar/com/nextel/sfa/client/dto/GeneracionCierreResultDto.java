package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GeneracionCierreResultDto implements IsSerializable {

	private List<MessageDto> messages;
	private boolean error;
	private String rtfFileName;
	private List<String> rtfFileNamePortabilidad;
	private List<String> rtfFileNamePortabilidad_adj;
	private List<String> reportes = new ArrayList<String>();
	
	public GeneracionCierreResultDto() {
		error = false;
		messages = new ArrayList<MessageDto>();
		rtfFileNamePortabilidad = new ArrayList<String>();
		rtfFileNamePortabilidad_adj = new ArrayList<String>();
	}

	public List<MessageDto> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageDto> messages) {
//		this.messages = messages;
//		MGR - Refactorizacion del cierre
		this.messages.addAll(messages);
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getRtfFileName() {
		return rtfFileName;
	}

	public void setRtfFileName(String rtfFileName) {
		this.rtfFileName = rtfFileName;
	}

	public List<String> getRtfFileNamePortabilidad() {
		return rtfFileNamePortabilidad;
	}

	public void setRtfFileNamePortabilidad(List<String> rtfFileNamePortabilidad) {
		this.rtfFileNamePortabilidad = rtfFileNamePortabilidad;
	}

	public List<String> getRtfFileNamePortabilidad_adj() {
		return rtfFileNamePortabilidad_adj;
	}

	public void setRtfFileNamePortabilidad_adj(List<String> rtfFileNamePortabilidad_adj) {
		this.rtfFileNamePortabilidad_adj = rtfFileNamePortabilidad_adj;
	}

	public List<String> getReportes() {
		return reportes;
	}

	public void setReportes(List<String> reportes) {
		this.reportes = reportes;
	}
	
//	MGR - Refactorizacion del cierre
	public void addMessage(String msg){
		MessageDto mens = new MessageDto();
		mens.setDescription(msg);
		this.messages.add(mens);
	}
}
