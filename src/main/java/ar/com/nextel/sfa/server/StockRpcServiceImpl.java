package ar.com.nextel.sfa.server;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.nextel.business.constants.MessageIdentifier;
import ar.com.nextel.business.stock.ResultadoValidarStock;
import ar.com.nextel.business.stock.StockService;
import ar.com.nextel.components.knownInstances.retrievers.DefaultRetriever;
import ar.com.nextel.components.message.Message;
import ar.com.nextel.model.solicitudes.beans.TipoSolicitud;
import ar.com.nextel.sfa.client.StockRpcService;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;
import ar.com.nextel.sfa.server.util.MapperExtended;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;
import ar.com.snoop.gwt.commons.server.RemoteService;

@SuppressWarnings("serial")
public class StockRpcServiceImpl extends RemoteService implements StockRpcService {

	private MapperExtended mapper;
	private WebApplicationContext context;
	private StockService stockService;
	private DefaultRetriever messageRetriever;
	
	public List<TipoSolicitudDto> obtenerTiposDeSolicitudParaVendedor(VendedorDto vendedorDto)
			throws RpcExceptionMessages {
		List<TipoSolicitudDto> listBoxItems = null;
		List<TipoSolicitud> tiposDeSolicitud = stockService.getTiposSolicitudesServicio(vendedorDto.getId(),vendedorDto.getIdSucursal());
		listBoxItems = mapper.convertList(tiposDeSolicitud, TipoSolicitudDto.class);
		return listBoxItems;
	}
	
	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		mapper = (MapperExtended) context.getBean("dozerMapper");
		stockService = (StockService) context.getBean("stockServiceBean");
		messageRetriever = (DefaultRetriever)context.getBean("messageRetriever");
	}

	/**
	 * Llama al servicio de validación de stock y en base al resutado selecciona 
	 * el mensaje adecuado para informar al usuario
	 */
	public String validarStock(Long idItem, Long idVendedor) throws RpcExceptionMessages {
		String respuesta = new String();
		ResultadoValidarStock resultado = new ResultadoValidarStock();
		resultado = stockService.validarStock(idItem, idVendedor);

		if (resultado.isVSalon()){
			// Hay stock disponible (%)
			respuesta = getMessage(resultado.getStock(), MessageIdentifier.SFA_VAL_STOCK_VS);
			}
		 if ((resultado.isEquipos() || resultado.isAccesorios()) && resultado.getStock() > 0){
			 // Retirar del pañol (%)
			respuesta = getMessage(resultado.getStock(), MessageIdentifier.SFA_VAL_STOCK_PA);			 
		 }
		 if (resultado.getStock() == 0 ){
			 // No hay stock disponible en esta sucursal. Optar por envío a domicilio.
 			respuesta = getMessage(resultado.getStock(), MessageIdentifier.SFA_VAL_STOCK_SU);			 
		 }

		 return respuesta;
	}
	
	public String validarStockDesdeSS(Long idItem, Long idVendedor) throws RpcExceptionMessages {
		String respuesta = new String();
		ResultadoValidarStock resultado = new ResultadoValidarStock();
		resultado = stockService.validarStock(idItem, idVendedor);

		 if ((resultado.isEquipos() || resultado.isAccesorios()) && resultado.getStock() > 0){
			 // Retirar del pañol (%)
			respuesta = getMessage(resultado.getStock(), MessageIdentifier.SFA_VAL_STOCK_PA);			 
		 }
		 if (resultado.getStock() == 0 ){
			 // No hay stock disponible en esta sucursal. Optar por envío a domicilio.
 			respuesta = getMessage(resultado.getStock(), MessageIdentifier.SFA_VAL_STOCK_SU);			 
		 }

		 return respuesta;
	}
	
	
	
	
	

	private String getMessage(int stock,MessageIdentifier keyMsj) {
		String respuesta;
		Message message;
		message = (Message)this.messageRetriever.getObject(keyMsj);
		message.addParameters(new Object[] {stock});
		respuesta = message.getDescription();
		return respuesta;
	}


}
