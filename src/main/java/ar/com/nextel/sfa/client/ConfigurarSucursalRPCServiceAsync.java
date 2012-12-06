package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.SucursalDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;

import com.google.gwt.user.client.rpc.AsyncCallback;



public interface ConfigurarSucursalRPCServiceAsync {
	
	public void getSucursales(AsyncCallback<List<SucursalDto>> callback);
    public void cambiarSucursal(Long idSucursal,AsyncCallback<Void> callback);

}
