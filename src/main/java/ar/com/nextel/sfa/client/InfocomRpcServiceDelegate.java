package ar.com.nextel.sfa.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import ar.com.nextel.sfa.client.dto.CreditoFidelizacionDto;
import ar.com.nextel.sfa.client.dto.TransaccionCCDto;
import ar.com.nextel.sfa.client.initializer.InfocomInitializer;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

public class InfocomRpcServiceDelegate {

	private InfocomRpcServiceAsync infocomRpcService;

	public InfocomRpcServiceDelegate() {
	}

	public InfocomRpcServiceDelegate(InfocomRpcServiceAsync infocomRpcSerive) {
		this.infocomRpcService = infocomRpcSerive;
	}

	public void getInfocomInitializer(String numeroCuenta, DefaultWaitCallback<InfocomInitializer> callback) {
		WaitWindow.show();
		infocomRpcService.getInfocomInitializer(numeroCuenta, callback);
	}

	public void getDetalleCreditoFidelizacion(String idCuenta,
			DefaultWaitCallback<CreditoFidelizacionDto> callback) {
		WaitWindow.show();
		infocomRpcService.getDetalleCreditoFidelizacion(idCuenta, callback);
	}

	public void getCuentaCorriente(DefaultWaitCallback<List<TransaccionCCDto>> callback) {
		WaitWindow.show();
		infocomRpcService.getCuentaCorriente(callback);
	}
	
//	public void getInfocomData(String numeroCuenta, String responsablePago, DefaultWaitCallback<InfocomInitializer> callback) {
//		WaitWindow.show();
//		infocomRpcService.getInfocomData(numeroCuenta, responsablePago, callback);
//	}
}
