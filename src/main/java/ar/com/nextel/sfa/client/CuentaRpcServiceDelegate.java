package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.CaratulaDto;
import ar.com.nextel.sfa.client.dto.ContratoViewDto;
import ar.com.nextel.sfa.client.dto.CrearCuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaPotencialDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchResultDto;
import ar.com.nextel.sfa.client.dto.DocDigitalizadosDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.NormalizarCPAResultDto;
import ar.com.nextel.sfa.client.dto.NormalizarDomicilioResultDto;
import ar.com.nextel.sfa.client.dto.OportunidadNegocioDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.ProvinciaDto;
import ar.com.nextel.sfa.client.dto.ScoreVerazDto;
import ar.com.nextel.sfa.client.dto.ServicioContratoDto;
import ar.com.nextel.sfa.client.dto.TarjetaCreditoValidatorResultDto;
import ar.com.nextel.sfa.client.dto.VerazResponseDto;
import ar.com.nextel.sfa.client.initializer.AgregarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.BuscarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.CaratulaInitializer;
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
		WaitWindow.hide();
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
	
	//MGR - #960
	public void consultarVeraz(String customerCode, DefaultWaitCallback<VerazResponseDto> callback) {
		WaitWindow.show();
		cuentaRpcService.consultarVeraz(customerCode, callback);
	}
	
	public void consultarDetalleVeraz(Long cuentaId, DefaultWaitCallback<VerazResponseDto> callback) {
		WaitWindow.show();
		cuentaRpcService.consultarDetalleVeraz(cuentaId, callback);
	}

	public void leerConsultaDetalleVeraz(String verazFileName, DefaultWaitCallback<String> callback) {
		WaitWindow.show();
		cuentaRpcService.leerConsultaDetalleVeraz(verazFileName, callback);
	}

	public void selectCuenta(Long cuentaId,String cod_vantive,boolean filtradoPorDni,
			DefaultWaitCallback<CuentaDto> callback) {
		WaitWindow.show();
		//MGR - #1466 - Esta llamada sigue funcionando como antes, entonces lockea
		cuentaRpcService.selectCuenta(cuentaId, cod_vantive,filtradoPorDni, true,callback);
		WaitWindow.hide();
	}
	public void reservaCreacionCuenta(CrearCuentaDto crearCuentaDto,DefaultWaitCallback<CuentaDto>  callback) {
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
//	public void getOportunidadNegocio(Long cuenta_id,DefaultWaitCallback<OportunidadNegocioDto> callback) {
//		WaitWindow.show();
//		cuentaRpcService.getOportunidadNegocio(cuenta_id,callback);
//	}
	public void getOportunidadNegocio(Long cuenta_id,DefaultWaitCallback<CuentaPotencialDto> callback) {
	WaitWindow.show();
	cuentaRpcService.getOportunidadNegocio(cuenta_id,callback);
}
	public void updateEstadoOportunidad(OportunidadNegocioDto oportunidadDto,DefaultWaitCallback<OportunidadNegocioDto> callback) {
		WaitWindow.show();
		cuentaRpcService.updateEstadoOportunidad(oportunidadDto,callback);
	}
	public void getCuentasAsociadasAVentaPotencial(Long idVentaPotencial, DefaultWaitCallback<List<CuentaDto>> callback) {
		WaitWindow.show();
		cuentaRpcService.getCuentasAsociadasAVentaPotencial(idVentaPotencial, callback);
	}

	//MGR - Dado un codigo vantive, devuelve el numero de cuenta que le corresponde en SFA
	public void selectCuenta(String codigoVantive,
			DefaultWaitCallback<Long> callback) {
		WaitWindow.show();
		cuentaRpcService.selectCuenta(codigoVantive, callback);
		
	}
	
	public void estaChequeadoPinEnNexus(String idRegistroAtencion, String customerId, DefaultWaitCallback<Boolean> callback) {
		WaitWindow.show();
		cuentaRpcService.estaChequeadoPinEnNexus(idRegistroAtencion, customerId, callback);
	}
	
	
	//MGR - #1466
	public void searchCuentaDto(CuentaSearchDto cuentaSearchDto, boolean deberiaLockear, DefaultWaitCallback<List<CuentaDto>> callback) {
		WaitWindow.show();
		cuentaRpcService.searchCuentasDto(cuentaSearchDto, deberiaLockear, callback);
	}
	
	public void searchContratosActivos(CuentaDto ctaDto, DefaultWaitCallback<List<ContratoViewDto>> callback) {
		WaitWindow.show();
		
		cuentaRpcService.searchContratosActivos(ctaDto, callback);
	}
	
	public void searchServiciosContratados(Long numeroContrato, DefaultWaitCallback<List<ServicioContratoDto>> callback){
		WaitWindow.show();
		cuentaRpcService.searchServiciosContratados(numeroContrato, callback);
	}

	public void selectCuentaParaInfocom(Long cuentaId, String codVantive, boolean filtradoPorDni,
			DefaultWaitCallback<CuentaDto> callback) {
		WaitWindow.show();
		//Al abrir Infocom no debe lockear la cuenta
		cuentaRpcService.selectCuenta(cuentaId, codVantive,filtradoPorDni, false, callback);
	}
	
	public void getCaratulaInicializarte(DefaultWaitCallback<CaratulaInitializer> callback) {
		WaitWindow.show();
		cuentaRpcService.getCaratulaInicializarte(callback);
	}
	
	public void confirmarCaratula(CaratulaDto caratulaDto, DefaultWaitCallback<CaratulaDto> callback){
		WaitWindow.show();
		cuentaRpcService.confirmarCaratula(caratulaDto, callback);
	}
	
	public void crearCaratula(CuentaDto cuentaDto, DefaultWaitCallback<CaratulaDto> callback){
		WaitWindow.show();
		cuentaRpcService.crearCaratula(cuentaDto, callback);
	}
	
	public void validarExistenciaTriptico(String nro, DefaultWaitCallback<Boolean> callback){
		WaitWindow.show();
		cuentaRpcService.validarExistenciaTriptico(nro, callback);
	}
	
	public void getDocDigitalizados(String customerCode, DefaultWaitCallback<List<DocDigitalizadosDto>> callback){
		WaitWindow.show();
		cuentaRpcService.getDocDigitalizados(customerCode, callback);
	}
	
//	MGR - #3010 - Se pide el id de la cuenta para tenerlo en cuenta en la consulta
	public void isDomicilioValidadoPorEECC(Long idCuenta, String nro_ss, DefaultWaitCallback<Boolean> callback){
		WaitWindow.show();
		cuentaRpcService.isDomicilioValidadoPorEECC(idCuenta, nro_ss, callback);
	}
	// LF
	public void autocompletarValoresVeraz(String score, int cantEquipos, DefaultWaitCallback<ScoreVerazDto> callback) {
		WaitWindow.show();
		cuentaRpcService.autocompletarValoresVeraz(score, cantEquipos, callback);		
	}
	
	public void obtenerPahtArchivoVeraz(String verazFileName, DefaultWaitCallback<String> callback){
		WaitWindow.show();
		cuentaRpcService.obtenerPahtArchivoVeraz(verazFileName, callback);
	}
}
