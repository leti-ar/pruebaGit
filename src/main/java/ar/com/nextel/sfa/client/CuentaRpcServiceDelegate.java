package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioSearchDto;
import ar.com.nextel.sfa.client.dto.SolicitudesServicioTotalesDto;
import ar.com.nextel.sfa.client.initializer.AgregarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.BuscarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.BuscarSSCerradasInitializer;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

public class CuentaRpcServiceDelegate {

	private CuentaRpcServiceAsync cuentaRpcService;

	public CuentaRpcServiceDelegate() {
	}
	
	public CuentaRpcServiceDelegate(CuentaRpcServiceAsync cuentaRpcService) {
		this.cuentaRpcService = cuentaRpcService;
	}

	public void searchCuenta(CuentaSearchDto cuentaSearchDto, DefaultWaitCallback<List<CuentaDto>> callback) {
		WaitWindow.show();
		cuentaRpcService.searchCuenta(cuentaSearchDto, callback);
	}

	public void getBuscarCuentaInitializer(DefaultWaitCallback<BuscarCuentaInitializer> callback) {
		WaitWindow.show();
		cuentaRpcService.getBuscarCuentaInitializer(callback);
	}
	
	public void getBuscarSSCerradasInitializer(DefaultWaitCallback<BuscarSSCerradasInitializer> callback) {
		WaitWindow.show();
		cuentaRpcService.getBuscarSSCerradasInitializer(callback);
	}

	public void getAgregarCuentaInitializer(DefaultWaitCallback<AgregarCuentaInitializer> callback) {
		WaitWindow.show();
		cuentaRpcService.getAgregarCuentaInitializer(callback);
	}
	
	public void saveCuenta (PersonaDto personaDto, DefaultWaitCallback callback) {
		WaitWindow.show();
		cuentaRpcService.saveCuenta(personaDto, callback);
	}

	public void searchSSCerrada(SolicitudServicioSearchDto solicitudServicioSearchDto,
			DefaultWaitCallback<SolicitudesServicioTotalesDto> callback) {
		cuentaRpcService.searchSSCerrada(solicitudServicioSearchDto, callback);
	}
}
