package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.OperacionEnCursoDto;
import ar.com.nextel.sfa.client.dto.CuentaPotencialDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author esalvador
 */

	public interface OperacionesRpcServiceAsync {

		public void searchOpEnCurso(AsyncCallback<List<OperacionEnCursoDto>> callback);
		
		public void searchReservas(AsyncCallback<List<CuentaPotencialDto>> callback);
	}