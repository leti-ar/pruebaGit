package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.CreditoFidelizacionDto;
import ar.com.nextel.sfa.client.dto.TransaccionCCDto;
import ar.com.nextel.sfa.client.initializer.InfocomInitializer;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

public class InfocomRpcServiceDelegate {

	private InfocomRpcServiceAsync infocomRpcService;

	public InfocomRpcServiceDelegate() {
	}

	public InfocomRpcServiceDelegate(InfocomRpcServiceAsync infocomRpcSerive) {
		this.infocomRpcService = infocomRpcSerive;
	}

	public void getInfocomInitializer(DefaultWaitCallback<InfocomInitializer> callback) {
		WaitWindow.show();
		infocomRpcService.getInfocomInitializer(callback);
	}

	public void getDetalleCreditoFidelizacion(Long idCuenta,
			DefaultWaitCallback<CreditoFidelizacionDto> callback) {
		WaitWindow.show();
		infocomRpcService.getDetalleCreditoFidelizacion(idCuenta, callback);
	}

	public void getCuentaCorriente(DefaultWaitCallback<List<TransaccionCCDto>> callback) {
		WaitWindow.show();
		infocomRpcService.getCuentaCorriente(callback);
	}
}
