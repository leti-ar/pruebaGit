package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GeneracionCierreResultDto implements IsSerializable {

	private List<MessageDto> messages;
	private boolean error;
	private String rtfFileName;

	public GeneracionCierreResultDto() {
		messages = new ArrayList<MessageDto>();
		error = false;
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

	public String getRtfFileName() {
		return rtfFileName;
	}

	public void setRtfFileName(String rtfFileName) {
		this.rtfFileName = rtfFileName;
	}

}
