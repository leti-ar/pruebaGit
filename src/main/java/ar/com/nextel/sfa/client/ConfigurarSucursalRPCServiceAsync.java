package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.SucursalDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;

import com.google.gwt.user.client.rpc.AsyncCallback;



public interface ConfigurarSucursalRPCServiceAsync {
	
	
	
	void getSucursales(AsyncCallback<List<SucursalDto>> callback);
    void cambiarSucursal(Long idSucursal,Long idVendedor,AsyncCallback<VendedorDto> callback);

}
