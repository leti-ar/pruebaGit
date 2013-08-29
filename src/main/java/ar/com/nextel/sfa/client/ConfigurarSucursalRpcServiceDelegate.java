package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.SucursalDto;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ConfigurarSucursalRpcServiceDelegate {
	
	private ConfigurarSucursalRPCServiceAsync confSucursalRpcServiceAsync;

	public ConfigurarSucursalRpcServiceDelegate(ConfigurarSucursalRPCServiceAsync configurarSucursalRPCServiceAsync){
	this.confSucursalRpcServiceAsync = configurarSucursalRPCServiceAsync;
	}

	public ConfigurarSucursalRPCServiceAsync configurarSucursalRPCServiceAsync() {
	return confSucursalRpcServiceAsync;
	}

	public void setconfigurarSucursalRPCServiceAsync(ConfigurarSucursalRPCServiceAsync confSucursalRpcServiceAsync) {
	this.confSucursalRpcServiceAsync = confSucursalRpcServiceAsync;
	}

	public void getSucursales(AsyncCallback<List<SucursalDto>> callback ){
	WaitWindow.show();
	confSucursalRpcServiceAsync.getSucursales(callback);
	}
	
	public void cambiarSucursal(Long idSucursal,AsyncCallback<Void> callback){
		WaitWindow.show();
		confSucursalRpcServiceAsync.cambiarSucursal(idSucursal,callback);
	}
	
	
	
	
	
	
	

}
