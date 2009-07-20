package ar.com.nextel.sfa.server;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.nextel.business.legacy.financial.FinancialSystem;
import ar.com.nextel.business.legacy.financial.dto.DetalleCreditoDTO;
import ar.com.nextel.business.legacy.financial.dto.EncabezadoCreditoDTO;
import ar.com.nextel.business.legacy.financial.exception.FinancialSystemException;
import ar.com.nextel.sfa.client.InfocomRpcService;
import ar.com.nextel.sfa.client.dto.CreditoFidelizacionDto;
import ar.com.nextel.sfa.client.dto.DetalleFidelizacionDto;
import ar.com.nextel.sfa.client.dto.TransaccionCCDto;
import ar.com.nextel.sfa.client.initializer.InfocomInitializer;
import ar.com.nextel.sfa.server.util.MapperExtended;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;
import ar.com.snoop.gwt.commons.server.util.ExceptionUtil;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class InfocomRpcServiceImpl extends RemoteServiceServlet implements InfocomRpcService {

	private WebApplicationContext context;
	private FinancialSystem financialSystem;
	private MapperExtended mapper;

	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		mapper = (MapperExtended) context.getBean("dozerMapper");
		financialSystem = (FinancialSystem) context.getBean("financialSystemBean");
	}

	public InfocomInitializer getInfocomInitializer() throws RpcExceptionMessages {
		// TODO Auto-generated method stub
		return null;
	}

	public CreditoFidelizacionDto getDetalleCreditoFidelizacion(Long idCuenta) throws RpcExceptionMessages {
		CreditoFidelizacionDto creditoFidelizacion = new CreditoFidelizacionDto();
		String custCode = null; // obtener del idCuenta
		List<DetalleCreditoDTO> detalleCreditoFidelizacion = null;
		EncabezadoCreditoDTO encabezadoCreditoFidelizacion = null;
		try {
			encabezadoCreditoFidelizacion = financialSystem.retrieveEncabezadoCreditoFidelizacion(custCode);
			detalleCreditoFidelizacion = financialSystem.retrieveDetalleCreditoFidelizacion(custCode);
		} catch (FinancialSystemException e) {
			throw ExceptionUtil.wrap(e);
		}
		creditoFidelizacion.setMonto(encabezadoCreditoFidelizacion.getMonto());
		creditoFidelizacion.setEstado(encabezadoCreditoFidelizacion.getEstado());
		creditoFidelizacion.setVencimiento(encabezadoCreditoFidelizacion.getFechaVencimiento());
		creditoFidelizacion.setDetalles(mapper.convertList(detalleCreditoFidelizacion,
				DetalleFidelizacionDto.class));
		return creditoFidelizacion;
	}

	public List<TransaccionCCDto> getCuentaCorriente() throws RpcExceptionMessages {
		// TODO Auto-generated method stub
		return null;
	}
}
