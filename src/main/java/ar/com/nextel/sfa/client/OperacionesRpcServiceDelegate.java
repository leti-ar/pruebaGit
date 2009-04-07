package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.OperacionEnCursoDto;
import ar.com.nextel.sfa.client.dto.CuentaPotencialDto;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

/**
 * @author esalvador
 */

public class OperacionesRpcServiceDelegate {

	private OperacionesRpcServiceAsync opRpcService;

	public OperacionesRpcServiceDelegate() {
	}
	
	public OperacionesRpcServiceDelegate(OperacionesRpcServiceAsync opRpcService) {
		this.opRpcService = opRpcService;
	}

	public void searchOpEnCurso(DefaultWaitCallback<List<OperacionEnCursoDto>> callback) {
		WaitWindow.show();
		opRpcService.searchOpEnCurso(callback);
	}
	
	public void searchReservas(DefaultWaitCallback<List<CuentaPotencialDto>> callback) {
		WaitWindow.show();
		opRpcService.searchReservas(callback);
	}
}
