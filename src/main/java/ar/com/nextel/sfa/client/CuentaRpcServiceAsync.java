package ar.com.nextel.sfa.client;

import java.util.List;
import java.util.Set;

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

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CuentaRpcServiceAsync {
	public void getAgregarCuentaInitializer(AsyncCallback<AgregarCuentaInitializer> callback);
	public void getBuscarCuentaInitializer(AsyncCallback<BuscarCuentaInitializer> callback);
	public void saveCuenta(CuentaDto cuentaDto, AsyncCallback<CuentaDto> callback);
	public void crearDivision(Long id_cuentaPadre, AsyncCallback<CuentaDto> callback);
	public void crearSuscriptor(Long id_cuentaPadre, AsyncCallback<CuentaDto> callback);
	public void searchCuenta(CuentaSearchDto cuentaSearchDto, AsyncCallback<List<CuentaSearchResultDto>> callback);
	public void getVerazInitializer(AsyncCallback<VerazInitializer> callback);
	public void consultarVeraz(PersonaDto personaDto, AsyncCallback<VerazResponseDto> callback);
	//MGR - #960
	public void consultarVeraz(String customerCode, AsyncCallback<VerazResponseDto> callback);
	public void consultarDetalleVeraz(Long cuentaId, AsyncCallback<VerazResponseDto> callback);
	public void leerConsultaDetalleVeraz(String verazFileName, AsyncCallback<String> callback);
	//MGR - #1466
	public void selectCuenta(Long cuentaId, String cod_vantive,boolean filtradoPorDni, boolean deberiaLockear,AsyncCallback<CuentaDto> callback);
	public void reservaCreacionCuenta(CrearCuentaDto crearCuentaDto, AsyncCallback<CuentaDto> callback);
	public void validarTarjeta(String numeroTarjeta, Integer mesVto, Integer anoVto,AsyncCallback<TarjetaCreditoValidatorResultDto> callback);
	public void getDomicilioPorCPA(String cpa, AsyncCallback<NormalizarCPAResultDto> callback);
    public void normalizarDomicilio(DomiciliosCuentaDto domicilioANormalizar, AsyncCallback<NormalizarDomicilioResultDto> callback);
	public void getCrearContactoInitializer(AsyncCallback<CrearContactoInitializer> callback); 
	public void getProvinciasInitializer(AsyncCallback<List<ProvinciaDto>> callback);
	//public void getOportunidadNegocio(Long cuenta_id, AsyncCallback<OportunidadNegocioDto> callback);
	public void getOportunidadNegocio(Long cuenta_id, AsyncCallback<CuentaPotencialDto> callback);
	public void updateEstadoOportunidad(OportunidadNegocioDto oportunidadDto, AsyncCallback<OportunidadNegocioDto> callback);
	public void getCuentasAsociadasAVentaPotencial(Long idVentaPotencial, AsyncCallback<List<CuentaDto>> callback);
	//MGR - Dado un codigo vantive, devuelve el numero de cuenta que le corresponde en SFA
	public void selectCuenta(String codigoVantive,AsyncCallback<Long> callback);
	public void estaChequeadoPinEnNexus(String idRegistroAtencion, String customerId,AsyncCallback<Boolean> callback);
	
	//MGR - #1466
	public void searchCuentasDto(CuentaSearchDto cuentaSearchDto, boolean deberiaLockear, AsyncCallback<List<CuentaDto>> callback);
	public void searchContratosActivos(CuentaDto ctaDto, AsyncCallback<List<ContratoViewDto>> callback);
	public void searchServiciosContratados(Long numeroContrato, AsyncCallback<List<ServicioContratoDto>> callback);
	
	public void getCaratulaInicializarte(AsyncCallback<CaratulaInitializer> callback);
	public void crearCaratula(CuentaDto cuentaDto, AsyncCallback<CaratulaDto> callback);	
	public void confirmarCaratula(CaratulaDto caratulaDto, AsyncCallback<CaratulaDto> callback);
	public void validarExistenciaTriptico(String nro, AsyncCallback<Boolean> callback);
	public void getDocDigitalizados(String customerCode, AsyncCallback<List<DocDigitalizadosDto>> callback);
//	MGR - #3010 - Se pide el id de la cuenta para tenerlo en cuenta en la consulta
	public void isDomicilioValidadoPorEECC(Long idCuenta, String nro_ss, AsyncCallback<Boolean> callback);
	//LF
	public void autocompletarValoresVeraz(String score, int cantEquipos, AsyncCallback<ScoreVerazDto> callback);

	public void obtenerPahtArchivoVeraz(String verazFileName, AsyncCallback<String> callback);
}
