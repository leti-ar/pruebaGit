package ar.com.nextel.sfa.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.nextel.business.cuentas.select.SelectCuentaBusinessOperator;
import ar.com.nextel.business.legacy.avalon.AvalonSystem;
import ar.com.nextel.business.legacy.avalon.dto.CantidadEquiposDTO;
import ar.com.nextel.business.legacy.dao.VantiveLegacyDAO;
import ar.com.nextel.business.legacy.financial.FinancialSystem;
import ar.com.nextel.business.legacy.financial.dto.DetalleCreditoDTO;
import ar.com.nextel.business.legacy.financial.dto.EncabezadoCreditoDTO;
import ar.com.nextel.business.legacy.financial.exception.FinancialSystemException;
import ar.com.nextel.exception.LegacyDAOException;
import ar.com.nextel.model.cuentas.beans.CicloFacturacion;
import ar.com.nextel.model.cuentas.beans.Cuenta;
import ar.com.nextel.model.cuentas.beans.GranCuenta;
import ar.com.nextel.model.cuentas.beans.TipoCanalVentas;
import ar.com.nextel.sfa.client.InfocomRpcService;
import ar.com.nextel.sfa.client.dto.CreditoFidelizacionDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DetalleFidelizacionDto;
import ar.com.nextel.sfa.client.dto.TipoCanalVentasDto;
import ar.com.nextel.sfa.client.dto.TransaccionCCDto;
import ar.com.nextel.sfa.client.initializer.InfocomInitializer;
import ar.com.nextel.sfa.server.util.MapperExtended;
import ar.com.nextel.util.AppLogger;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;
import ar.com.snoop.gwt.commons.server.util.ExceptionUtil;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * 
 * @author mrial
 *
 */
public class InfocomRpcServiceImpl extends RemoteServiceServlet implements InfocomRpcService {

	private WebApplicationContext context;
	private FinancialSystem financialSystem;
	private AvalonSystem avalonSystem;
	private MapperExtended mapper;
	private VantiveLegacyDAO vantiveLegacyDAO;
	
    private SelectCuentaBusinessOperator selectCuentaBusinessOperator;

	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		mapper = (MapperExtended) context.getBean("dozerMapper");
		financialSystem = (FinancialSystem) context.getBean("financialSystemBean");
		avalonSystem = (AvalonSystem) context.getBean("avalonSystemBean");
		selectCuentaBusinessOperator = (SelectCuentaBusinessOperator) context.getBean("selectCuentaBusinessOperator");
		vantiveLegacyDAO = (VantiveLegacyDAO) context.getBean("vantiveLegacyDAOBean");
	}
	
//	public InfocomInitializer getInfocomData(String numeroCuenta, String responsablePago) throws RpcExceptionMessages {
//		AppLogger.info("Iniciando retrieve infocom data...");
//		InfocomInitializer infocomInitializer = new InfocomInitializer();
//		Cuenta cuenta;
//		try {
//			cuenta = selectCuentaBusinessOperator.getCuentaSinLockear(numeroCuenta);
//			numeroCuenta = cuenta.getCuentaRaiz().getCodigoVantive();
//		} catch (Exception e) {
//			AppLogger.error("Error obteniendo datos de Infocom: ");
//			throw ExceptionUtil.wrap(e);
//		}
//		getLimiteCredito(cuenta, responsablePago, infocomInitializer);
//		//getCuentaCorriente(numeroCuenta, responsablePago, infocomInitializer);
//		//getEquiposServiciosTable(numeroCuenta, responsablePago, infocomInitializer);
//		//getCreditoFidelizacion(numeroCuenta, responsablePago, infocomInitializer);
//		AppLogger.info("Retrieve infocom data finalizado.");
//		return infocomInitializer;
//	}
	
	/** Obtiene los datos del Header de infocom */
	public InfocomInitializer getInfocomInitializer(String numeroCuenta) throws RpcExceptionMessages {
		AppLogger.info("Iniciando retrieve infocom-header...");
		InfocomInitializer infocomInitializer = new InfocomInitializer();
		infocomInitializer.setNumeroCuenta(numeroCuenta);
        try {
            getEncabezadoInfocom(numeroCuenta, infocomInitializer);
        } catch (Exception e) {
            throw ExceptionUtil.wrap(e);
        }
        getCantidadEquipos(getCuentaRaiz(numeroCuenta).getCodigoVantive(), infocomInitializer);
        getResponsablesPago(numeroCuenta, infocomInitializer);
        AppLogger.info("Retrieve infocom-header finalizado.");
        return infocomInitializer;
	}
	
    private void getLimiteCredito(Cuenta cuenta, String responsablePago, InfocomInitializer infocomInitializer) {
        if ("Todos".equals(responsablePago)) {
        	infocomInitializer.setLimiteCredito("");
        } else {
        	try {
        		infocomInitializer.setLimiteCredito(vantiveLegacyDAO.obtenerLimiteCredito(cuenta.getCuentaRaiz().getCodigoVantive()).toString());
        	} catch (LegacyDAOException e) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
        		infocomInitializer.setLimiteCredito("");
        	}
        }
    }
    
//    private void getEquiposServiciosTable(String numeroCuenta, String responsablePago, InfocomInitializer infocomInitializer)
//    throws RpcExceptionMessages {
//    	DeudaDTO resultDTO = null;
//    	try {
//    		if ("Todos".equals(responsablePago)) {
//    			GranCuenta cuentaRaiz = getCuentaRaiz(numeroCuenta);
//    			resultDTO = avalonSystem.retrieveDeudaArbol(cuentaRaiz.getCodigoVantive());
//    		} else {
//    			resultDTO = avalonSystem.retrieveDeudaRespPago(responsablePago);
//    		}
//    	} catch (AvalonSystemException e) {
//    		throw ExceptionUtil.wrap(e);
//    	}
//    	//pasar el DeudaDTO a InfocomInitializer
//        EquiposServiciosDto equipos = new EquiposServiciosDto();
//        equipos.setAVencer(resultDTO.getDeudaEquiposAVencer().toString());
//        equipos.setVencida(resultDTO.getDeudaEquiposVencida().toString());
//        EquiposServiciosWCTO servicios = new EquiposServiciosWCTO();
//        servicios.setDescripcion(DESCRIPCION_SERVICIOS);
//        servicios.setAVencer(resultDTO.getDeudaServiciosAVencer().toString());
//        servicios.setVencida(resultDTO.getDeudaServiciosVencida().toString());
//        equiposServiciosCollection.add(equipos);
//        equiposServiciosCollection.add(servicios);
//        resultWCTO.setEquiposServiciosCollection(equiposServiciosCollection);
//    	
//    	this.addTotalRow(resultWCTO);
//    }

    
	/** Obtiene una lista de cuentas de los responsables de pago dado un número de cuenta */
	private void getResponsablesPago(String numeroCuenta, InfocomInitializer infocomInitializer) throws RpcExceptionMessages {
		Set<Cuenta> cuentasRP = new HashSet<Cuenta>();
		try {
			Cuenta cuenta = selectCuentaBusinessOperator.getCuentaSinLockear(numeroCuenta);
			cuentasRP = cuenta.cuentasResponsablesPago();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<CuentaDto> cuentasDto = new ArrayList<CuentaDto>();
		infocomInitializer.setResponsablePago(mapper.convertList(cuentasRP, CuentaDto.class));		
	}
	
	/** Carga en el initializer la cantidad de equipos en estado A,S,D dado un número de cuenta */
    private void getCantidadEquipos(String numeroCuenta, InfocomInitializer infocomInitializer) throws RpcExceptionMessages {
    	CantidadEquiposDTO resultDTO = null;
    	try {
    		resultDTO = avalonSystem.retreiveEquiposPorEstado(numeroCuenta);
        } catch (Exception e) {
            throw ExceptionUtil.wrap(e);
        }
        infocomInitializer.setTerminalesActivas(resultDTO.getCantidadActivos());
        infocomInitializer.setTerminalesSuspendidas(resultDTO.getCantidadSuspendidos());
        infocomInitializer.setTerminalesDesactivadas(resultDTO.getCantidadDesactivados());
    }
    
    private void getEncabezadoInfocom(String numeroCuenta, InfocomInitializer infocomInitializer) throws RpcExceptionMessages {
		Cuenta cuenta = null;
		try {
			cuenta = selectCuentaBusinessOperator.getCuentaSinLockear(numeroCuenta);
		} catch (Exception e) {
			throw ExceptionUtil.wrap(e);
		}
		CicloFacturacion cicloFacturacion = cuenta.getCicloFacturacion();
		if (cicloFacturacion != null) {
			infocomInitializer.setCiclo(cicloFacturacion.getDescripcion());
		}
		infocomInitializer.setFlota(cuenta.getFlota());
		infocomInitializer.setRazonSocial(cuenta.getPersona().getRazonSocial());
		getLimiteCredito(cuenta, "6.356172", infocomInitializer);
	}

	
	/** Obtiene los datos necesarios para cargar la tabla de Fidelización */
    public CreditoFidelizacionDto getDetalleCreditoFidelizacion(String custCode) throws RpcExceptionMessages {
		CreditoFidelizacionDto creditoFidelizacion = new CreditoFidelizacionDto();
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
		creditoFidelizacion.setDetalles(mapper.convertList(detalleCreditoFidelizacion, DetalleFidelizacionDto.class));
		return creditoFidelizacion;
	}

//	/** Obtiene los datos necesarios para cargar la tabla de Cuenta Corriente */
//    public List<TransaccionCCDto> getCuentaCorriente(String numeroCuenta, String responsablePago, InfocomInitializer infocomInitializer) throws RpcExceptionMessages {
//		TransaccionCCDto transaccionCCDto = new TransaccionCCDto();
//		try {
//			if ("Todos".equals(responsablePago)) {
//				List<CuentaCorrienteArbolDTO> resultDTO = null;
//				GranCuenta cuentaRaiz = getCuentaRaiz(numeroCuenta);
//				//resultDTO es una lista
//				resultDTO = avalonSystem.retrieveCuentaCorrienteArbol(cuentaRaiz.getCodigoVantive());
//			} else {
//				List<CuentaCorrienteResponsablePagoDTO> resultDTO = null;
//				resultDTO = avalonSystem.retrieveCuentaCorrienteRespPago(responsablePago);
//			}
//			CuentaCorrienteDto cuentaCorrienteDto = new CuentaCorrienteDto();
//			cuentaCorrienteDto = (CuentaCorrienteDto) mapper.convertList(resultDTO, CuentaCorrienteDto.class);
//		} catch (AvalonSystemException e) {
//			throw ExceptionUtil.wrap(e);
//		}
//		return List<TransaccionCCDto>;
//	}
	
    private GranCuenta getCuentaRaiz(String numeroCuenta) throws RpcExceptionMessages {
        try {
            Cuenta cuenta = this.selectCuentaBusinessOperator.getCuentaSinLockear(numeroCuenta);
            return cuenta.getCuentaRaiz();
        } catch (Exception e) {
            throw ExceptionUtil.wrap(e);
        }
    }

	public List<TransaccionCCDto> getCuentaCorriente()
			throws RpcExceptionMessages {
		// TODO Auto-generated method stub
		return null;
	}

	public InfocomInitializer getInfocomInitializer()
			throws RpcExceptionMessages {
		// TODO Auto-generated method stub
		return null;
	}

}
