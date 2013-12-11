package ar.com.nextel.sfa.server;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.nextel.business.sucursal.ConfigurarSucursalService;
import ar.com.nextel.sfa.client.ConfigurarSucursalRPCService;
import ar.com.nextel.sfa.client.dto.SucursalDto;
import ar.com.nextel.sfa.server.util.MapperExtended;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;
import ar.com.snoop.gwt.commons.server.RemoteService;

public class ConfigurarSucursalRPCServiceImpl extends RemoteService implements ConfigurarSucursalRPCService {
	private static final long serialVersionUID = 1L;
	
	private WebApplicationContext context;
	private  ConfigurarSucursalService configurarSucursalService;
	private MapperExtended mapper;
	
	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		configurarSucursalService = (ConfigurarSucursalService) context.getBean("configurarSucursalServicioRepositoryBean");
        mapper= (MapperExtended) context.getBean("dozerMapper");
		
	}
	

	public List<SucursalDto> getSucursales() throws RpcExceptionMessages {
	
		List<SucursalDto> listaSucursales= mapper.convertList(configurarSucursalService.getSucursales(),
				SucursalDto.class);
		return listaSucursales;
	}
	

	public void cambiarSucursal(Long idSucursal)throws RpcExceptionMessages{
		configurarSucursalService.cambiarSucursal(idSucursal);
	}
		
		
		
	
	
	
}