package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.CrearCuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaPotencialDto;
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
	public CuentaDto selectCuenta(Long cuentaId, String cod_vantiveu) throws RpcExceptionMessages;
	public GranCuentaDto reservaCreacionCuenta(CrearCuentaDto crearCuentaDto) throws RpcExceptionMessages;
	public TarjetaCreditoValidatorResultDto validarTarjeta(String numeroTarjeta, Integer mesVto,Integer anoVto) throws RpcExceptionMessages;
	public NormalizarCPAResultDto getDomicilioPorCPA(String cpa) throws RpcExceptionMessages;
	public NormalizarDomicilioResultDto normalizarDomicilio(DomiciliosCuentaDto domicilioANormalizar)throws RpcExceptionMessages;
	public CrearContactoInitializer getCrearContactoInitializer() throws RpcExceptionMessages;
	public List<ProvinciaDto> getProvinciasInitializer() throws RpcExceptionMessages;
	//public OportunidadNegocioDto getOportunidadNegocio(Long cuenta_id) throws RpcExceptionMessages;
	public CuentaPotencialDto getOportunidadNegocio(Long cuenta_id) throws RpcExceptionMessages;
	public OportunidadNegocioDto updateEstadoOportunidad(OportunidadNegocioDto oportunidadDto) throws RpcExceptionMessages;
	public List <CuentaDto> getCuentasAsociadasAVentaPotencial(Long idVentaPotencial) throws RpcExceptionMessages;
	
}
