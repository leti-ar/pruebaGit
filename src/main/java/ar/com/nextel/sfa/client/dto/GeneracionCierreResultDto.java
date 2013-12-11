package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GeneracionCierreResultDto implements IsSerializable {

	private List<MessageDto> messages;
	private boolean error;
	private String reportSSFileName;
	private List<String> fileNamePortabilidad;
	private List<String> fileNamePortabilidad_adj;
	private String remitoRtfFileName;
	private List<NumeroReservaDto> numerosReservados;
	
	public GeneracionCierreResultDto() {
		error = false;
		messages = new ArrayList<MessageDto>();
		fileNamePortabilidad = new ArrayList<String>();
		fileNamePortabilidad_adj = new ArrayList<String>();
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

	public String getReportSSFileName() {
		return reportSSFileName;
	}

	public void setReportSSFileName(String reportSSFileName) {
		this.reportSSFileName = reportSSFileName;
	}

	public List<String> getFileNamePortabilidad() {
		return fileNamePortabilidad;
	}

	public void setFileNamePortabilidad(List<String> fileNamePortabilidad) {
		this.fileNamePortabilidad = fileNamePortabilidad;
	}

	public List<String> getFileNamePortabilidad_adj() {
		return fileNamePortabilidad_adj;
	}

	public void setFileNamePortabilidad_adj(List<String> fileNamePortabilidad_adj) {
		this.fileNamePortabilidad_adj = fileNamePortabilidad_adj;
	}

	public String getRemitoRtfFileName() {
		return remitoRtfFileName;
	}

	public void setRemitoRtfFileName(String remitoRtfFileName) {
		this.remitoRtfFileName = remitoRtfFileName;
	}

//	MGR - Refactorizacion del cierre
	public void addMessage(String msg){
		MessageDto mens = new MessageDto();
		mens.setDescription(msg);
		this.messages.add(mens);
	}

	public void setNumerosReservados(List<NumeroReservaDto> numerosReservados) {
		this.numerosReservados = numerosReservados;
	}

	public List<NumeroReservaDto> getNumerosReservados() {
		return numerosReservados;
	}
}
