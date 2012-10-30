package ar.com.nextel.sfa.server;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.nextel.business.constants.MessageIdentifier;
import ar.com.nextel.business.stock.StockService;
import ar.com.nextel.components.knownInstances.retrievers.DefaultRetriever;
import ar.com.nextel.components.message.Message;
import ar.com.nextel.components.message.Resultado;
import ar.com.nextel.model.cuentas.beans.Vendedor;
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
		Vendedor vendedor = mapper.map(vendedorDto, Vendedor.class);
		List<TipoSolicitud> tiposDeSolicitud = stockService.getTiposSolicitudesServicio(vendedor,vendedorDto.getIdSucursal());
		listBoxItems = mapper.convertList(tiposDeSolicitud, TipoSolicitudDto.class);
		return listBoxItems;
	}
	
	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		mapper = (MapperExtended) context.getBean("dozerMapper");
		stockService = (StockService) context.getBean("stockServicioRepositoryBean");
		messageRetriever = (DefaultRetriever)context.getBean("messageRetriever");
	}

	/**
	 * Llama al servicio de validación de stock y en base al resutado selecciona 
	 * el mensaje adecuado para informar al usuario
	 */
	public String validarStock(Long idItem, Long idVendedor) throws RpcExceptionMessages {
		String respuesta = new String();
		Resultado resultado = new Resultado();
		resultado = stockService.validarStock(idItem, idVendedor);

		Message message = null;
		
		if (resultado.isVSalon()){
			// Hay stock disponible (%)
			message = (Message)this.messageRetriever.getObject(MessageIdentifier.SFA_VAL_STOCK_VS);
			message.addParameters(new Object[] {resultado.getValor()});
			respuesta = message.getDescription();
			}
		 if (resultado.isEquipos() || resultado.isAccesorios()){
			 // Retirar del pañol (%)
			 message = (Message)this.messageRetriever.getObject(MessageIdentifier.SFA_VAL_STOCK_PA);
			 message.addParameters(new Object[] {resultado.getValor()});
			 respuesta = message.getDescription();
		 }
		 if (resultado.getValor() == -1 ){
			 // No hay stock disponible en esta sucursal. Optar por envío a domicilio.
			 message = (Message)this.messageRetriever.getObject(MessageIdentifier.SFA_VAL_STOCK_SU);
			 respuesta = message.getDescription();
		 }

		 return respuesta;
	}


}
