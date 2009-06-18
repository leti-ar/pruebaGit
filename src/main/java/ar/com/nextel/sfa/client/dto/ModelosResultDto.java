package ar.com.nextel.sfa.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ModelosResultDto implements IsSerializable {

	List<ModeloDto> modelos;
	// Resultado de consulta en NegativeFile
	private boolean result;
	private String message;

	public List<ModeloDto> getModelos() {
		return modelos;
	}

	public void setModelos(List<ModeloDto> modelos) {
		this.modelos = modelos;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
