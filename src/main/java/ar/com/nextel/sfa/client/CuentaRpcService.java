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
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("cuenta.rpc")
public interface CuentaRpcService extends RemoteService {

	public static class Util {
		private static CuentaRpcServiceAsync cuentaRpcService = null;
		private static CuentaRpcServiceDelegate cuentaRpcServiceDelegate = null;

		public static CuentaRpcServiceDelegate getInstance() {
			if (cuentaRpcServiceDelegate == null) {
				cuentaRpcService = GWT.create(CuentaRpcService.class);
				cuentaRpcServiceDelegate = new CuentaRpcServiceDelegate(cuentaRpcService);
			}
			return cuentaRpcServiceDelegate;
		}
	}
	public List<CuentaSearchResultDto> searchCuenta(CuentaSearchDto cuentaSearchDto) throws RpcExceptionMessages;
	public BuscarCuentaInitializer getBuscarCuentaInitializer() throws RpcExceptionMessages;
	public AgregarCuentaInitializer getAgregarCuentaInitializer() throws RpcExceptionMessages;
	public CuentaDto saveCuenta(CuentaDto cuentaDto) throws RpcExceptionMessages;
	public CuentaDto crearDivision(Long id_cuentaPadre) throws RpcExceptionMessages;
	public CuentaDto crearSuscriptor(Long id_cuentaPadre) throws RpcExceptionMessages;
	public VerazInitializer getVerazInitializer() throws RpcExceptionMessages;
	public VerazResponseDto consultarVeraz(PersonaDto personaDto) throws RpcExceptionMessages;
	//MGR - #960
	public VerazResponseDto consultarVeraz(String customerId) throws RpcExceptionMessages;
	public VerazResponseDto consultarDetalleVeraz(Long cuentaId) throws RpcExceptionMessages;
	public String leerConsultaDetalleVeraz(String verazFileName) throws RpcExceptionMessages;
	//MGR - #1466
	public CuentaDto selectCuenta(Long cuentaId, String cod_vantiveu,boolean filtradoPorDni, boolean deberiaLockear) throws RpcExceptionMessages;
	public CuentaDto reservaCreacionCuenta(CrearCuentaDto crearCuentaDto) throws RpcExceptionMessages;
	public TarjetaCreditoValidatorResultDto validarTarjeta(String numeroTarjeta, Integer mesVto,Integer anoVto) throws RpcExceptionMessages;
	public NormalizarCPAResultDto getDomicilioPorCPA(String cpa) throws RpcExceptionMessages;
	public NormalizarDomicilioResultDto normalizarDomicilio(DomiciliosCuentaDto domicilioANormalizar)throws RpcExceptionMessages;
	public CrearContactoInitializer getCrearContactoInitializer() throws RpcExceptionMessages;
	public List<ProvinciaDto> getProvinciasInitializer() throws RpcExceptionMessages;
	//public OportunidadNegocioDto getOportunidadNegocio(Long cuenta_id) throws RpcExceptionMessages;
	public CuentaPotencialDto getOportunidadNegocio(Long cuenta_id) throws RpcExceptionMessages;
	public OportunidadNegocioDto updateEstadoOportunidad(OportunidadNegocioDto oportunidadDto) throws RpcExceptionMessages;
	public List <CuentaDto> getCuentasAsociadasAVentaPotencial(Long idVentaPotencial) throws RpcExceptionMessages;
	//MGR - Dado un codigo vantive, devuelve el numero de cuenta que le corresponde en SFA
	public Long selectCuenta(String codigoVantive) throws RpcExceptionMessages;
	//MGR - #1466
	public List<CuentaDto> searchCuentasDto(CuentaSearchDto cuentaSearchDto, boolean deberiaLockear) throws RpcExceptionMessages;
	public List<ContratoViewDto> searchContratosActivos(CuentaDto ctaDto) throws RpcExceptionMessages;
	public List<ServicioContratoDto> searchServiciosContratados(Long numeroContrato) throws RpcExceptionMessages;
	
	public CaratulaInitializer getCaratulaInicializarte() throws RpcExceptionMessages;
	public CaratulaDto crearCaratula(CuentaDto cuentaDto) throws RpcExceptionMessages;
	public CaratulaDto confirmarCaratula(CaratulaDto caratulaDto) throws RpcExceptionMessages;
	public boolean validarExistenciaTriptico(String nro) throws RpcExceptionMessages;
	public List<DocDigitalizadosDto> getDocDigitalizados(String customerCode) throws RpcExceptionMessages;
//	MGR - #3010 - Se pide el id de la cuenta para tenerlo en cuenta en la consulta
	public Boolean isDomicilioValidadoPorEECC(Long idCuenta,String nro_ss) throws RpcExceptionMessages;
	// #LF
	public ScoreVerazDto autocompletarValoresVeraz(String score, int cantEquipos) throws RpcExceptionMessages;
	
	public String obtenerPahtArchivoVeraz(String verazFileName);
}
