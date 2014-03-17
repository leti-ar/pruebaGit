package ar.com.nextel.sfa.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import ar.com.nextel.sfa.client.dto.OperacionEnCursoDto;
import ar.com.nextel.sfa.client.dto.VentaPotencialVistaResultDto;
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

	public void searchOpEnCurso(DefaultWaitCallback<List<OperacionEnCursoDto>> defaultWaitCallback) {
		WaitWindow.show();
		opRpcService.searchOpEnCurso(defaultWaitCallback);
	}

	public void searchReservas(DefaultWaitCallback<VentaPotencialVistaResultDto> callback) {
		WaitWindow.show();
		opRpcService.searchReservas(callback);
	}

	public void cancelarOperacionEnCurso(String idOperacionEnCurso,
			DefaultWaitCallback<VentaPotencialVistaResultDto> callback) {
		WaitWindow.show();
		opRpcService.cancelarOperacionEnCurso(idOperacionEnCurso, callback);
	}

	public void cancelarOperacionEnCurso(Long idCuenta,
			DefaultWaitCallback<VentaPotencialVistaResultDto> callback) {
		WaitWindow.show();
		opRpcService.cancelarOperacionEnCurso(idCuenta, callback);
	}
	
//	#6637
	public void vendedorIsGeneraTriptico(Long idTipoVendedor,
			AsyncCallback<Boolean> callback){
		WaitWindow.show();
		opRpcService.vendedorIsGeneraTriptico(idTipoVendedor, callback);
	}
}
