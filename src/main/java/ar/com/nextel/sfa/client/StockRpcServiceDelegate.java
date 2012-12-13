package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class StockRpcServiceDelegate {
	
	private StockRpcServiceAsync stockRpcServiceAsync;
	
	public StockRpcServiceDelegate(StockRpcServiceAsync stockRpcServiceAsync){
		this.stockRpcServiceAsync = stockRpcServiceAsync;
	}

	public StockRpcServiceAsync getStockRpcServiceAsync() {
		return stockRpcServiceAsync;
	}

	public void setStockRpcServiceAsync(StockRpcServiceAsync stockRpcServiceAsync) {
		this.stockRpcServiceAsync = stockRpcServiceAsync;
	}
	
	public void obtenerTiposDeSolicitudParaVendedor(VendedorDto vendedorDto, AsyncCallback<List<TipoSolicitudDto>> callback ){
	WaitWindow.show();
	stockRpcServiceAsync.obtenerTiposDeSolicitudParaVendedor(vendedorDto, callback);
	}
	
	public void validarStock(Long idItem, Long idVendedor,AsyncCallback<String> callback){
		WaitWindow.show();
		stockRpcServiceAsync.validarStock(idItem, idVendedor,callback);
	}
	
	public void validarStockDesdeSS(Long idItem, Long idVendedor,AsyncCallback<String> callback){
		WaitWindow.show();
		stockRpcServiceAsync.validarStockDesdeSS(idItem, idVendedor,callback);
	}
	
	
}
