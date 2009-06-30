package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.business.oportunidades.search.result.OportunidadNegocioSearchResult;
import ar.com.nextel.sfa.client.dto.OportunidadDto;
import ar.com.nextel.sfa.client.dto.OportunidadNegocioSearchResultDto;
import ar.com.nextel.sfa.client.initializer.BuscarOportunidadNegocioInitializer;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

public class OportunidadNegocioRpcServiceDelegate {

	private OportunidadNegocioRpcServiceAsync oportunidadNegocioRpcService;

	public OportunidadNegocioRpcServiceDelegate() {
	}

	public OportunidadNegocioRpcServiceDelegate(OportunidadNegocioRpcServiceAsync oportunidadNegocioRpcService) {
		this.oportunidadNegocioRpcService = oportunidadNegocioRpcService;
	}

	public void searchOportunidad(OportunidadDto oportunidadDto, DefaultWaitCallback<List<OportunidadNegocioSearchResultDto>> callback) {
		WaitWindow.show();
		oportunidadNegocioRpcService.searchOportunidad(oportunidadDto, callback);
	}
	
	public void getBuscarOportunidadInitializer(DefaultWaitCallback<BuscarOportunidadNegocioInitializer> callback) {
		WaitWindow.show();
		oportunidadNegocioRpcService.getBuscarOportunidadInitializer(callback);
	}

//	public void getAgregarOportunidadInitializer(DefaultWaitCallback<AgregarCuentaInitializer> callback) {
//		WaitWindow.show();
//		oportunidadNegocioRpcService.getAgregarOportunidadInitializer(callback);
//	}
//	
//	public void saveOportunidad(OportunidadSearchDto oportunidadSearchDto, DefaultWaitCallback callback) {
//		WaitWindow.show();
//		oportunidadNegocioRpcService.saveOportunidad(oportunidadSearchDto, callback);
//	}
}
