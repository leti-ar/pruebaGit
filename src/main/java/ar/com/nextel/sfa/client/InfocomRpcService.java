package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.CreditoFidelizacionDto;
import ar.com.nextel.sfa.client.dto.TransaccionCCDto;
import ar.com.nextel.sfa.client.initializer.InfocomInitializer;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("infocom.rpc")
public interface InfocomRpcService extends RemoteService {

	public class Util {

		private static InfocomRpcServiceAsync infocomRpcService = null;
		private static InfocomRpcServiceDelegate infocomRpcServiceDelegate = null;

		public static InfocomRpcServiceDelegate getInstance() {
			if (infocomRpcServiceDelegate == null) {
				infocomRpcService = GWT.create(InfocomRpcService.class);
				infocomRpcServiceDelegate = GWT.create(InfocomRpcServiceDelegate.class);
			}
			return infocomRpcServiceDelegate;
		}
	}

	public InfocomInitializer getInfocomInitializer() throws RpcExceptionMessages;

	public CreditoFidelizacionDto getDetalleCreditoFidelizacion(Long idCuenta) throws RpcExceptionMessages;

	public List<TransaccionCCDto> getCuentaCorriente() throws RpcExceptionMessages;

}
