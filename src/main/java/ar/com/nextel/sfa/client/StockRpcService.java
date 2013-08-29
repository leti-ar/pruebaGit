package ar.com.nextel.sfa.client; 

import java.util.List;

import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("stock.rpc")
public interface StockRpcService extends RemoteService {
	
	public static class Util {

		private static StockRpcServiceAsync stockRpcServiceAsync = null;
		private static StockRpcServiceDelegate stockRpcServiceDelegate = null;

		public static StockRpcServiceDelegate getInstance() {
			if (stockRpcServiceDelegate == null) {
				stockRpcServiceAsync = GWT.create(StockRpcService.class);
				stockRpcServiceDelegate = new StockRpcServiceDelegate(stockRpcServiceAsync);
			}
			return stockRpcServiceDelegate;
		}
	}

	List<TipoSolicitudDto> obtenerTiposDeSolicitudParaVendedor(
			VendedorDto vendedorDto) throws RpcExceptionMessages;

	String validarStock(Long idItem, Long idVendedor) throws RpcExceptionMessages;
	
	String validarStockDesdeSS(Long idItem, Long idVendedor) throws RpcExceptionMessages;
	
}
