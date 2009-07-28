package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.CrearCuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchResultDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.GranCuentaDto;
import ar.com.nextel.sfa.client.dto.NormalizarCPAResultDto;
import ar.com.nextel.sfa.client.dto.NormalizarDomicilioResultDto;
import ar.com.nextel.sfa.client.dto.OportunidadNegocioDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.ProvinciaDto;
import ar.com.nextel.sfa.client.dto.TarjetaCreditoValidatorResultDto;
import ar.com.nextel.sfa.client.dto.VerazResponseDto;
import ar.com.nextel.sfa.client.initializer.AgregarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.BuscarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.CrearContactoInitializer;
import ar.com.nextel.sfa.client.initializer.VerazInitializer;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

public class CuentaRpcServiceDelegate {

	private CuentaRpcServiceAsync cuentaRpcService;

	public CuentaRpcServiceDelegate() {	}
	
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
	public void saveCuenta(CuentaDto cuentaDto,DefaultWaitCallback<CuentaDto> callback) {
		WaitWindow.show();
		cuentaRpcService.saveCuenta(cuentaDto, callback);
	}
	public void crearDivision(Long id_cuentaPadre, DefaultWaitCallback<CuentaDto> callback) {
		WaitWindow.show();
		cuentaRpcService.crearDivision(id_cuentaPadre, callback);
	}
	public void crearSuscriptor(Long id_cuentaPadre, DefaultWaitCallback<CuentaDto> callback) {
		WaitWindow.show();
		cuentaRpcService.crearSuscriptor(id_cuentaPadre, callback);
	}
	public void getVerazInitializer(DefaultWaitCallback<VerazInitializer> callback) {
		WaitWindow.show();
		cuentaRpcService.getVerazInitializer(callback);
	}
	public void consultarVeraz(PersonaDto personaDto, DefaultWaitCallback<VerazResponseDto> callback) {
		WaitWindow.show();
		cuentaRpcService.consultarVeraz(personaDto, callback);
	}
	public void selectCuenta(Long cuentaId,String cod_vantive,DefaultWaitCallback<CuentaDto> callback) {
		WaitWindow.show();
		cuentaRpcService.selectCuenta(cuentaId, cod_vantive,callback);
	}
	public void reservaCreacionCuenta(CrearCuentaDto crearCuentaDto,DefaultWaitCallback<GranCuentaDto>  callback) {
		WaitWindow.show();
		cuentaRpcService.reservaCreacionCuenta(crearCuentaDto,callback);
	}
	public void validarTarjeta(String numeroTarjeta, Integer mesVto, Integer anoVto,DefaultWaitCallback<TarjetaCreditoValidatorResultDto> callback) {
		WaitWindow.show();
		cuentaRpcService.validarTarjeta(numeroTarjeta, mesVto, anoVto, callback);
	}
	public void getDomicilioPorCPA(String cpa,DefaultWaitCallback<NormalizarCPAResultDto> callback) {
		WaitWindow.show();
		cuentaRpcService.getDomicilioPorCPA(cpa, callback);
	}
	public void normalizarDomicilio(DomiciliosCuentaDto domicilioANormalizar,DefaultWaitCallback<NormalizarDomicilioResultDto> callback) {
		WaitWindow.show();
		cuentaRpcService.normalizarDomicilio(domicilioANormalizar, callback);
	}
	public void CrearContactoInitializer(DefaultWaitCallback<CrearContactoInitializer> callback) {
		WaitWindow.show();
		cuentaRpcService.getCrearContactoInitializer(callback);
	}
	public void getProvinciasInitializer(DefaultWaitCallback<List<ProvinciaDto>> callback) {
		WaitWindow.show();
		cuentaRpcService.getProvinciasInitializer(callback);
	}	
	public void getOportunidadNegocio(Long cuenta_id,DefaultWaitCallback<OportunidadNegocioDto> callback) {
		WaitWindow.show();
		cuentaRpcService.getOportunidadNegocio(cuenta_id,callback);
	}
	public void updateEstadoOportunidad(OportunidadNegocioDto oportunidadDto,DefaultWaitCallback<OportunidadNegocioDto> callback) {
		WaitWindow.show();
		cuentaRpcService.updateEstadoOportunidad(oportunidadDto,callback);
	}
}
