package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchResultDto;
import ar.com.nextel.sfa.client.dto.DocumentoDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.NormalizarCPAResultDto;
import ar.com.nextel.sfa.client.dto.NormalizarDomicilioResultDto;
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

	public List<CuentaSearchResultDto> searchCuenta(CuentaSearchDto cuentaSearchDto);
	
	public BuscarCuentaInitializer getBuscarCuentaInitializer();
	
	public AgregarCuentaInitializer getAgregarCuentaInitializer();
		
	public void saveCuenta(CuentaDto cuentaDto);
	
	public VerazInitializer getVerazInitializer();
	
	public VerazResponseDto consultarVeraz(PersonaDto personaDto);
	
	public CuentaDto selectCuenta(Long cuentaId, String cod_vantiveu);
	
	public CuentaDto reservaCreacionCuenta(DocumentoDto documentoDto);
	
	public TarjetaCreditoValidatorResultDto validarTarjeta(String numeroTarjeta, Integer mesVto, Integer anoVto);
	
	public NormalizarCPAResultDto getDomicilioPorCPA(String cpa) throws RpcExceptionMessages;
	
	public NormalizarDomicilioResultDto normalizarDomicilio(DomiciliosCuentaDto domicilioANormalizar) throws RpcExceptionMessages;
	
	public CrearContactoInitializer getCrearContactoInitializer(); 
	
	public List<ProvinciaDto> getProvinciasInitializer();
}
