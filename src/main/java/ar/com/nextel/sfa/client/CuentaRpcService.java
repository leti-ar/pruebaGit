package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.initializer.AgregarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.BuscarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.BuscarSSCerradasInitializer;

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

	public List<CuentaDto> searchCuenta(CuentaSearchDto cuentaSearchDto);
	
	public List<SolicitudServicioDto> searchSSCerrada(SolicitudServicioDto solicitudServicioDto);

	public BuscarCuentaInitializer getBuscarCuentaInitializer();
	
	public BuscarSSCerradasInitializer getBuscarSSCerradasInitializer();
	
	public AgregarCuentaInitializer getAgregarCuentaInitializer();
	
	public PersonaDto saveCuenta(PersonaDto personaDto);
	
}
