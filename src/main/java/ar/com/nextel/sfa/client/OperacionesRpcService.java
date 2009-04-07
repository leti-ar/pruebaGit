package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.OperacionEnCursoDto;
import ar.com.nextel.sfa.client.dto.CuentaPotencialDto;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

	@RemoteServiceRelativePath("operaciones.rpc")
	public interface OperacionesRpcService extends RemoteService {

		public static class Util {

			private static OperacionesRpcServiceAsync opRpcService = null;
			private static OperacionesRpcServiceDelegate opRpcServiceDelegate = null;

			public static OperacionesRpcServiceDelegate getInstance() {
				if (opRpcServiceDelegate == null) {
					opRpcService = GWT.create(OperacionesRpcService.class);
					opRpcServiceDelegate = new OperacionesRpcServiceDelegate(opRpcService);
				}
				return opRpcServiceDelegate;
			}
		}

		public List<OperacionEnCursoDto> searchOpEnCurso() throws RpcExceptionMessages;
		
		public List<CuentaPotencialDto> searchReservas() throws RpcExceptionMessages;
	}

