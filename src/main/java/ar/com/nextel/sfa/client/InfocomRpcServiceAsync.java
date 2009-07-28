package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.CreditoFidelizacionDto;
import ar.com.nextel.sfa.client.dto.TransaccionCCDto;
import ar.com.nextel.sfa.client.initializer.InfocomInitializer;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface InfocomRpcServiceAsync {

	public void getInfocomInitializer(String numeroCuenta, AsyncCallback<InfocomInitializer> callback);

	public void getDetalleCreditoFidelizacion(String idCuenta, AsyncCallback<CreditoFidelizacionDto> callback);

	public void getCuentaCorriente(AsyncCallback<List<TransaccionCCDto>> callback);
	
	//public void getInfocomData(String numeroCuenta, String responsablePago, AsyncCallback<InfocomInitializer> callback);

}
