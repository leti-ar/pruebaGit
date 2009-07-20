package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.CreditoFidelizacionDto;
import ar.com.nextel.sfa.client.dto.TransaccionCCDto;
import ar.com.nextel.sfa.client.initializer.InfocomInitializer;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface InfocomRpcServiceAsync {

	public void getInfocomInitializer(AsyncCallback<InfocomInitializer> callback);

	public void getDetalleCreditoFidelizacion(Long idCuenta, AsyncCallback<CreditoFidelizacionDto> callback);

	public void getCuentaCorriente(AsyncCallback<List<TransaccionCCDto>> callback);

}
