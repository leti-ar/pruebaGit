package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StockRpcServiceAsync {

	
	void obtenerTiposDeSolicitudParaVendedor(VendedorDto vendedorDto,
			AsyncCallback<List<TipoSolicitudDto>> asyncCallback);

	void validarStock(Long idItem, Long idVendedor, AsyncCallback<String> callback);
	void validarStockDesdeSS(Long idItem, Long idVendedor, AsyncCallback<String> callback);
	
}
