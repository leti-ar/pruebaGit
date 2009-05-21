package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchResultDto;
import ar.com.nextel.sfa.client.dto.DocumentoDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.initializer.AgregarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.BuscarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.VerazInitializer;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

public class CuentaRpcServiceDelegate {

	private CuentaRpcServiceAsync cuentaRpcService;

	public CuentaRpcServiceDelegate() {
	}
	
	public CuentaRpcServiceDelegate(CuentaRpcServiceAsync cuentaRpcService) {
		this.cuentaRpcService = cuentaRpcService;
	}

	public void searchCuenta(CuentaSearchDto cuentaSearchDto, DefaultWaitCallback<List<CuentaSearchResultDto>> callback) {
		WaitWindow.show();
		cuentaRpcService.searchCuenta(cuentaSearchDto, callback);
	}

	public void getBuscarCuentaInitializer(DefaultWaitCallback<BuscarCuentaInitializer> callback) {
		WaitWindow.show();
		cuentaRpcService.getBuscarCuentaInitializer(callback);
	}
	

	public void getAgregarCuentaInitializer(DefaultWaitCallback<AgregarCuentaInitializer> callback) {
		WaitWindow.show();
		cuentaRpcService.getAgregarCuentaInitializer(callback);
	}
	
	public void saveCuenta (CuentaDto cuentaDto, DefaultWaitCallback callback) {
		WaitWindow.show();
		cuentaRpcService.saveCuenta(cuentaDto, callback);
	}

	
	public void getVerazInitializer(DefaultWaitCallback<VerazInitializer> callback) {
		WaitWindow.show();
		cuentaRpcService.getVerazInitializer(callback);
	}
	
	
//	public void searchVeraz(VerazSearchDto verazSearchDto, DefaultWaitCallback callback) {
//		cuentaRpcService.searchVeraz(verazSearchDto, callback);
//	}
	
	public void consultarVeraz(PersonaDto personaDto, DefaultWaitCallback callback) {
		WaitWindow.show();
		cuentaRpcService.consultarVeraz(personaDto, callback);
	}
	
	public void selectCuenta(Long cuentaId,String cod_vantive,DefaultWaitCallback callback) {
		WaitWindow.show();
		cuentaRpcService.selectCuenta(cuentaId, cod_vantive,callback);
	}

	public void reservaCreacionCuenta(DocumentoDto docDto,DefaultWaitCallback callback) {
		WaitWindow.show();
		cuentaRpcService.reservaCreacionCuenta(docDto,callback);
	}
	
	public void validarTarjeta(String numeroTarjeta, Integer mesVto, Integer anoVto,DefaultWaitCallback callback) {
		WaitWindow.show();
		cuentaRpcService.validarTarjeta(numeroTarjeta, mesVto, anoVto, callback);
	}

	public void getDomicilioPorCPA(String cpa,DefaultWaitCallback callback) {
		WaitWindow.show();
		cuentaRpcService.getDomicilioPorCPA(cpa, callback);
	}
	
	public void normalizarDomicilio(DomiciliosCuentaDto domicilioANormalizar,DefaultWaitCallback callback) {
		WaitWindow.show();
		cuentaRpcService.normalizarDomicilio(domicilioANormalizar, callback);
	}
	
	public void getProvinciasInitializer(DefaultWaitCallback callback) {
		WaitWindow.show();
		cuentaRpcService.getProvinciasInitializer(callback);
	}	
}
