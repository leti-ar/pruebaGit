package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.SucursalDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("configurarsucursal.rpc")
public interface ConfigurarSucursalRPCService extends RemoteService {
	
	
	
	public static class Util{
	private static ConfigurarSucursalRPCServiceAsync configurarSucursalRPCServiceAsync = null;
	private static ConfigurarSucursalRpcServiceDelegate configurarSucursalRpcServiceDelegate = null;

	public static ConfigurarSucursalRpcServiceDelegate getInstance() {
	if (configurarSucursalRpcServiceDelegate == null) {
		configurarSucursalRPCServiceAsync = GWT.create(ConfigurarSucursalRPCService.class);
		configurarSucursalRpcServiceDelegate = new ConfigurarSucursalRpcServiceDelegate(configurarSucursalRPCServiceAsync);
	}
	return configurarSucursalRpcServiceDelegate;
	}
	}
	
	
	public List<SucursalDto> getSucursales() throws RpcExceptionMessages;
	
	
	public VendedorDto cambiarSucursal(Long idSucursal,Long idVendedor)throws RpcExceptionMessages;
}
