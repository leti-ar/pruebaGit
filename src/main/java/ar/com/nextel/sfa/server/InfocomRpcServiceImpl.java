package ar.com.nextel.sfa.server;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.nextel.business.cuentas.scoring.CuentaScoringBusinessOperator;
import ar.com.nextel.business.cuentas.scoring.result.CuentaScoringBusinessResult;
import ar.com.nextel.business.legacy.avalon.AvalonSystem;
import ar.com.nextel.business.legacy.avalon.dto.CantidadEquiposDTO;
import ar.com.nextel.business.legacy.avalon.dto.CuentaCorrienteArbolDTO;
import ar.com.nextel.business.legacy.avalon.dto.CuentaCorrienteResponsablePagoDTO;
import ar.com.nextel.business.legacy.avalon.dto.DatosEquipoPorEstadoDTO;
import ar.com.nextel.business.legacy.avalon.dto.DeudaDTO;
import ar.com.nextel.business.legacy.avalon.dto.ResumenPorEquipoArbolDTO;
import ar.com.nextel.business.legacy.avalon.dto.ResumenPorEquipoResponsablePagoDTO;
import ar.com.nextel.business.legacy.avalon.exception.AvalonSystemException;
import ar.com.nextel.business.legacy.dao.VantiveLegacyDAO;
import ar.com.nextel.business.legacy.financial.FinancialSystem;
import ar.com.nextel.business.legacy.financial.dto.DetalleCreditoDTO;
import ar.com.nextel.business.legacy.financial.dto.EncabezadoCreditoDTO;
import ar.com.nextel.business.legacy.financial.exception.FinancialSystemException;
import ar.com.nextel.exception.LegacyDAOException;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.model.cuentas.beans.CicloFacturacion;
import ar.com.nextel.model.cuentas.beans.Cuenta;
import ar.com.nextel.model.cuentas.beans.GranCuenta;
import ar.com.nextel.sfa.client.InfocomRpcService;
import ar.com.nextel.sfa.client.dto.CreditoFidelizacionDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DatosEquipoPorEstadoDto;
import ar.com.nextel.sfa.client.dto.DetalleFidelizacionDto;
import ar.com.nextel.sfa.client.dto.EquipoDto;
import ar.com.nextel.sfa.client.dto.EquiposServiciosDto;
import ar.com.nextel.sfa.client.dto.ResumenEquipoDto;
import ar.com.nextel.sfa.client.dto.ScoringDto;
import ar.com.nextel.sfa.client.dto.TransaccionCCDto;
import ar.com.nextel.sfa.client.initializer.InfocomInitializer;
import ar.com.nextel.sfa.server.businessservice.CuentaBusinessService;
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
public class InfocomRpcServiceImpl extends RemoteServiceServlet implements
		InfocomRpcService {

	private WebApplicationContext context;
	private FinancialSystem financialSystem;
	private AvalonSystem avalonSystem;
	private MapperExtended mapper;
	private VantiveLegacyDAO vantiveLegacyDAO;
	private Repository repository;

	// private SelectCuentaBusinessOperator selectCuentaBusinessOperator;
	private CuentaScoringBusinessOperator cuentaScoringBusinessOperator;
	private CuentaBusinessService cuentaBusinessService;

	private String codigoVantiveRP;

	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());
		mapper = (MapperExtended) context.getBean("dozerMapper");
		financialSystem = (FinancialSystem) context
				.getBean("financialSystemBean");
		avalonSystem = (AvalonSystem) context.getBean("avalonSystemBean");
		vantiveLegacyDAO = (VantiveLegacyDAO) context
				.getBean("vantiveLegacyDAOBean");
		cuentaScoringBusinessOperator = (CuentaScoringBusinessOperator) context
				.getBean("cuentaScoringBusinessOperator");
		cuentaBusinessService = (CuentaBusinessService) context
				.getBean("cuentaBusinessService");
		repository = (Repository) context.getBean("repository");
	}

	/**
	 * Obtiene los datos del Header de infocom
	 * 
	 * @throws Exception
	 */
	public InfocomInitializer getInfocomInitializer(String numeroCuenta,
			String codigoVantive, String responsablePago)
	throws RpcExceptionMessages {
		AppLogger.info("Iniciando retrieve infocom-header...");
		InfocomInitializer infocomInitializer = new InfocomInitializer();
		try {
			if (codigoVantive != null) {
				Cuenta cuenta = cuentaBusinessService
				.getCuentaSinLockear(codigoVantive);
				numeroCuenta = cuenta.getId().toString();
			} else {
				Cuenta cuenta = repository.retrieve(Cuenta.class, Long
						.valueOf(numeroCuenta));
				codigoVantive = cuenta.getCodigoVantive();
			}
			//Verificar si está bien suponer la segunda condición
			if (responsablePago.equals(numeroCuenta) || "0".equals(responsablePago)) {
				codigoVantiveRP = codigoVantive;
			} else if (responsablePago == null) {
				codigoVantiveRP = null;
			} else {
				codigoVantiveRP = responsablePago;
			}
			infocomInitializer.setNumeroCuenta(codigoVantive);
			try {
				getEncabezadoInfocom(numeroCuenta, codigoVantive, codigoVantiveRP,
						infocomInitializer);
			} catch (Exception e) {
				throw ExceptionUtil.wrap(e);
			}

			getCantidadEquipos(numeroCuenta, getCuentaRaiz(numeroCuenta, codigoVantive).getCodigoVantive(),
					infocomInitializer);
			getResponsablesPago(numeroCuenta, codigoVantive, infocomInitializer);
			AppLogger.info("Retrieve infocom-header finalizado.");
		} catch (Exception e) {
			throw ExceptionUtil.wrap(e);
		}
		return infocomInitializer;
	}

	private void getLimiteCredito(Cuenta cuenta, String responsablePago,
			InfocomInitializer infocomInitializer) {
		if (responsablePago == null) {
			infocomInitializer.setLimiteCredito(Double.parseDouble(""));
		} else {
			try {
				infocomInitializer.setLimiteCredito(vantiveLegacyDAO
						.obtenerLimiteCredito(cuenta.getCuentaRaiz()
								.getCodigoVantive()));
			} catch (LegacyDAOException e) {
				infocomInitializer.setLimiteCredito(Double.parseDouble(""));
			}
		}
	}

	/**
	 * Obtiene una lista de cuentas de los responsables de pago dado un número
	 * de cuenta
	 */
	private void getResponsablesPago(String numeroCuenta, String codigoVantive, 
			InfocomInitializer infocomInitializer) throws RpcExceptionMessages {
		Set<Cuenta> cuentasRP = new HashSet<Cuenta>();
		try {
			if (codigoVantive != null) {
				Cuenta cuenta = cuentaBusinessService.getCuentaSinLockear(codigoVantive);
				cuentasRP = cuenta.cuentasResponsablesPago();
			} else {
				Cuenta cuenta = cuentaBusinessService.getCuentaSinLockear(numeroCuenta);
				cuentasRP = cuenta.cuentasResponsablesPago();
			}
		} catch (Exception e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
		infocomInitializer.setResponsablePago(mapper.convertList(cuentasRP,
				CuentaDto.class));
	}

	/**
	 * Carga en el initializer la cantidad de equipos en estado A,S,D dado un
	 * número de cuenta
	 */
	private void getCantidadEquipos(String numeroCuenta, String codigoVantive, InfocomInitializer infocomInitializer) throws RpcExceptionMessages {
		CantidadEquiposDTO resultDTO = null;
		try {
			if (codigoVantive != null) {
				resultDTO = avalonSystem.retreiveEquiposPorEstado(codigoVantive);
			} else {
				Cuenta cuenta = cuentaBusinessService.getCuentaSinLockear(numeroCuenta);
				codigoVantive = cuenta.getId().toString();
				resultDTO = avalonSystem.retreiveEquiposPorEstado(codigoVantive);
			}						
		} catch (Exception e) {
			throw ExceptionUtil.wrap(e);
		}
		infocomInitializer.setTerminalesActivas(resultDTO.getCantidadActivos());
		infocomInitializer.setTerminalesSuspendidas(resultDTO
				.getCantidadSuspendidos());
		infocomInitializer.setTerminalesDesactivadas(resultDTO
				.getCantidadDesactivados());
	}
	
	
	private void getEncabezadoInfocom(String numeroCuenta, String codigoVantive, String responsablePago,
			InfocomInitializer infocomInitializer) throws RpcExceptionMessages {
		Cuenta cuenta = null;
		try {
			Long idCuenta = Long.parseLong(numeroCuenta);
			cuenta = cuentaBusinessService.getCuentaSinLockear(idCuenta);
		} catch (Exception e) {
			throw ExceptionUtil.wrap(e);
		}
		CicloFacturacion cicloFacturacion = cuenta.getCicloFacturacion();
		if (cicloFacturacion != null) {
			infocomInitializer.setCiclo(cicloFacturacion.getDescripcion());
		}
		//MGR - #887
		String flota = "";
		try {
			flota = vantiveLegacyDAO.searchFlotaByCodigoVantive(cuenta.getCodigoVantive());
		} catch (LegacyDAOException e) {
			throw ExceptionUtil.wrap(e);
		}
		
		//infocomInitializer.setFlota(cuenta.getFlota());
		infocomInitializer.setFlota(flota);
		infocomInitializer.setRazonSocial(cuenta.getPersona().getRazonSocial());
		getLimiteCredito(cuenta, responsablePago, infocomInitializer);
		getEquiposServiciosTable(numeroCuenta, codigoVantive, responsablePago,
		infocomInitializer);
	}

	/**
	 * Obtiene los datos necesarios para completar la tabla de Descripción de
	 * Equipos y Servicios
	 */
	private void getEquiposServiciosTable(String numeroCuenta, String codigoVantive,
			String responsablePago, InfocomInitializer infocomInitializer)
	throws RpcExceptionMessages {
		DeudaDTO resultDTO = null;
		try {
			if (codigoVantive != null) {
				Cuenta cuenta = cuentaBusinessService.getCuentaSinLockear(codigoVantive);
				numeroCuenta = cuenta.getId().toString();
			} else {
				Cuenta cuenta = repository.retrieve(Cuenta.class, Long.valueOf(numeroCuenta));
				codigoVantive = cuenta.getCodigoVantive();
			}
		} catch (Exception e) {
			throw ExceptionUtil.wrap(e);
		}	
		try {
			if (responsablePago == null) {
				GranCuenta cuentaRaiz = getCuentaRaiz(numeroCuenta, codigoVantive);
				resultDTO = this.avalonSystem.retrieveDeudaArbol(cuentaRaiz
						.getCodigoVantive());
			} else {
				resultDTO = this.avalonSystem
				.retrieveDeudaRespPago(responsablePago);
			}
		} catch (AvalonSystemException e) {
			throw ExceptionUtil.wrap(e);
		}
		EquiposServiciosDto equipo = new EquiposServiciosDto();
		equipo.setDeudaEquiposAVencer(resultDTO.getDeudaEquiposAVencer());
		equipo.setDeudaEquiposVencida(resultDTO.getDeudaEquiposVencida());
		equipo.setDeudaServiciosAVencer(resultDTO.getDeudaServiciosAVencer());
		equipo.setDeudaServiciosVencida(resultDTO.getDeudaServiciosVencida());
		infocomInitializer.setEquiposServicios(equipo);
	}

	/** Obtiene los datos necesarios para cargar la tabla de Fidelización */
	public CreditoFidelizacionDto getDetalleCreditoFidelizacion(String custCode, String codigoVantive)
			throws RpcExceptionMessages {
		try {
			if (codigoVantive != null) {
				Cuenta cuenta = cuentaBusinessService.getCuentaSinLockear(codigoVantive);
				custCode = cuenta.getId().toString();
			} else {
				Cuenta cuenta = repository.retrieve(Cuenta.class, Long.valueOf(custCode));
				codigoVantive = cuenta.getCodigoVantive();
			}
		} catch (Exception e) {
			throw ExceptionUtil.wrap(e);
		}		
//		Cuenta cuenta = repository.retrieve(Cuenta.class, Long
//				.valueOf(custCode));
//		if ("0".equals(codigoVantive)) {
//			codigoVantive = cuenta.getCodigoVantive();
//		}		
		CreditoFidelizacionDto creditoFidelizacion = new CreditoFidelizacionDto();
		List<DetalleCreditoDTO> detalleCreditoFidelizacion = null;
		EncabezadoCreditoDTO encabezadoCreditoFidelizacion = null;
		try {
			encabezadoCreditoFidelizacion = financialSystem
					.retrieveEncabezadoCreditoFidelizacion(codigoVantive);
			detalleCreditoFidelizacion = financialSystem
					.retrieveDetalleCreditoFidelizacion(codigoVantive);
		} catch (FinancialSystemException e) {
			throw ExceptionUtil.wrap(e);
		}
		creditoFidelizacion.setMonto(encabezadoCreditoFidelizacion.getMonto());
		creditoFidelizacion
				.setEstado(encabezadoCreditoFidelizacion.getEstado());
		creditoFidelizacion.setVencimiento(encabezadoCreditoFidelizacion
				.getFechaVencimiento());
		creditoFidelizacion.setDetalles(mapper.convertList(
				detalleCreditoFidelizacion, DetalleFidelizacionDto.class));
		return creditoFidelizacion;
	}

	/** Obtiene los datos necesarios para cargar la tabla de Cuenta Corriente */
	public List<TransaccionCCDto> getCuentaCorriente(String numeroCuenta, String codigoVantive, String responsablePago) throws RpcExceptionMessages {
		List<TransaccionCCDto> listTransaccion = new ArrayList<TransaccionCCDto>();
		try {
			if (codigoVantive != null) {
				Cuenta cuenta = cuentaBusinessService.getCuentaSinLockear(codigoVantive);
				numeroCuenta = cuenta.getId().toString();
				
			} else {
				Cuenta cuenta = repository.retrieve(Cuenta.class, Long.valueOf(numeroCuenta));
				codigoVantive = cuenta.getCodigoVantive();
			}
		} catch (Exception e) {
			throw ExceptionUtil.wrap(e);
		}			
		if (responsablePago.equals(numeroCuenta)) {
			codigoVantiveRP = codigoVantive;
		} else if (responsablePago == null) {
			codigoVantiveRP = null;
		} else {
			codigoVantiveRP = responsablePago;
		}
		try {
			if (responsablePago == null) {
				List<CuentaCorrienteArbolDTO> resultDTO = null;
				GranCuenta cuentaRaiz = getCuentaRaiz(numeroCuenta, codigoVantive);
				// resultDTO es una lista
				resultDTO = avalonSystem
						.retrieveCuentaCorrienteArbol(cuentaRaiz
								.getCodigoVantive());
				for (Iterator iterator = resultDTO.iterator(); iterator
						.hasNext();) {
					CuentaCorrienteArbolDTO result = (CuentaCorrienteArbolDTO) iterator
							.next();
					TransaccionCCDto transaccion = new TransaccionCCDto();
					transaccion.setClase(result.getClase());
					transaccion.setFechaVenc(result.getVencimiento());
					transaccion.setDescripcion(result.getDescripcion());
					transaccion.setNumero(result.getNumeroComprobante());
					transaccion
							.setImporte(NumberFormat.getCurrencyInstance(
									new Locale("es", "AR")).format(
									result.getImporte()));
					transaccion.setSaldo(NumberFormat.getCurrencyInstance(
							new Locale("es", "AR")).format(result.getSaldo()));
					listTransaccion.add(transaccion);
				}

			} else {
				List<CuentaCorrienteResponsablePagoDTO> resultDTO = null;
				resultDTO = avalonSystem
						.retrieveCuentaCorrienteRespPago(codigoVantiveRP);
				for (Iterator iterator = resultDTO.iterator(); iterator
						.hasNext();) {
					CuentaCorrienteResponsablePagoDTO result = (CuentaCorrienteResponsablePagoDTO) iterator
							.next();
					TransaccionCCDto transaccion = new TransaccionCCDto();
					transaccion.setNumeroCuenta(result.getNumeroCuenta());
					transaccion.setClase(result.getClase());
					transaccion.setFechaVenc(result.getVencimiento());
					transaccion.setDescripcion(result.getDescripcion());
					transaccion.setNumero(result.getNumeroComprobante());
					transaccion
							.setImporte(NumberFormat.getCurrencyInstance(
									new Locale("es", "AR")).format(
									result.getImporte()));
					transaccion.setSaldo(NumberFormat.getCurrencyInstance(
							new Locale("es", "AR")).format(result.getSaldo()));
					listTransaccion.add(transaccion);
				}
			}
		} catch (AvalonSystemException e) {
			throw ExceptionUtil.wrap(e);
		}
		return listTransaccion;
	}

	/** Obtiene la información de los equipos según el estado */
	public List<DatosEquipoPorEstadoDto> getInformacionEquipos(String numeroCuenta, String codigoVantive, String estado) throws RpcExceptionMessages {
		AppLogger
				.info("Iniciando retrieve de información de equipos-Infocom...");
		List<DatosEquipoPorEstadoDTO> resultDTO = null;
		try {
			if (codigoVantive != null) {
				Cuenta cuenta = cuentaBusinessService.getCuentaSinLockear(codigoVantive);
				numeroCuenta = cuenta.getId().toString();
				
			} else {
				Cuenta cuenta = repository.retrieve(Cuenta.class, Long.valueOf(numeroCuenta));
				codigoVantive = cuenta.getCodigoVantive();
			}
		} catch (Exception e) {
			throw ExceptionUtil.wrap(e);
		}					
		try {
			resultDTO = this.avalonSystem.retrieveDatosEquipoPorEstado(getCuentaRaiz(numeroCuenta, codigoVantive).getCodigoVantive(), estado);
		} catch (AvalonSystemException e) {
			throw ExceptionUtil.wrap(e);
		}
		AppLogger
				.info("Retrieve de información de equipos-Infocom finalizado.");
		List listaEquiposPorEstado = new ArrayList<DatosEquipoPorEstadoDto>();
		listaEquiposPorEstado = mapper.convertList(resultDTO,
				DatosEquipoPorEstadoDto.class);
		return listaEquiposPorEstado;
	}

	private GranCuenta getCuentaRaiz(String numeroCuenta, String codigoVantive)
	throws RpcExceptionMessages {
		Cuenta cuenta;
		try {
			if (codigoVantive != null) {
				cuenta = cuentaBusinessService.getCuentaSinLockear(codigoVantive);

			} else {
				Long idCuenta = Long.parseLong(numeroCuenta);
				cuenta = cuentaBusinessService.getCuentaSinLockear(idCuenta);
			}
		} catch (Exception e) {
			throw ExceptionUtil.wrap(e);
		}	
		return cuenta.getCuentaRaiz();
	}

	/** Obtiene la información del Resumen por Equipo */
	public ResumenEquipoDto getResumenEquipos(String numeroCuenta, String codigoVantive,
			String responsablePago) throws RpcExceptionMessages {
		AppLogger.info("Iniciando retrieve resumen equipos-Infocom...");
		try {
			if (codigoVantive != null) {
				Cuenta cuenta = cuentaBusinessService.getCuentaSinLockear(codigoVantive);
				numeroCuenta = cuenta.getId().toString();	
			} else {
				Cuenta cuenta = repository.retrieve(Cuenta.class, Long.valueOf(numeroCuenta));
				codigoVantive = cuenta.getCodigoVantive();
			}
		} catch (Exception e) {
			throw ExceptionUtil.wrap(e);
		} 
		ResumenEquipoDto resumenEquipoDto = new ResumenEquipoDto();
		try {
			if (responsablePago == null) {
				getResumenEncabezadoCuenta(numeroCuenta, getCuentaRaiz(numeroCuenta, codigoVantive).getCodigoVantive(), resumenEquipoDto);
				GranCuenta cuentaRaiz = getCuentaRaiz(numeroCuenta, codigoVantive);
				List<ResumenPorEquipoArbolDTO> retrieveResumenPorEquipoArbol = avalonSystem
				.retrieveResumenPorEquipoArbol(cuentaRaiz
						.getCodigoVantive());
				transformResumenEquipoArbolDTOToWCTO(
						retrieveResumenPorEquipoArbol, resumenEquipoDto);
			} else {
				getResumenEncabezadoCuenta(responsablePago, codigoVantive, resumenEquipoDto);
				List<ResumenPorEquipoResponsablePagoDTO> retrieveResumenPorEquipoResponsablePago = avalonSystem
				.retrieveResumenPorEquipoResponsablePago(responsablePago);
				transformResumenEquipoRespPagoDTOToWCTO(
						retrieveResumenPorEquipoResponsablePago,
						resumenEquipoDto);
			}
		} catch (Exception e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
		AppLogger.info("Retrieve resumen equipos-Infocom finalizado.");
		return resumenEquipoDto;
	}
	
	
	
		private void getResumenEncabezadoCuenta(String numeroCuenta, String codigoVantive,
				ResumenEquipoDto resumenEquipoDto) throws RpcExceptionMessages {
			try {
				if (codigoVantive != null) {
					Cuenta cuenta = cuentaBusinessService.getCuentaSinLockear(codigoVantive);
					numeroCuenta = cuenta.getId().toString();
				} else {
					Cuenta cuenta = repository.retrieve(Cuenta.class, Long.valueOf(numeroCuenta));
					codigoVantive = cuenta.getCodigoVantive();
				}		
				Cuenta cuenta = cuentaBusinessService
				.getCuentaSinLockear(Long.parseLong(numeroCuenta));
				resumenEquipoDto.setRazonSocial(cuenta.getPersona()
						.getRazonSocial());
				resumenEquipoDto.setNumeroCliente(cuenta.getCodigoVantive());
				//resumenEquipoDto.setFlota(cuenta.getFlota());
				//MGR - #896
				String flota = "";
				try {
					flota = vantiveLegacyDAO.searchFlotaByCodigoVantive(cuenta.getCodigoVantive());
				} catch (LegacyDAOException e) {
					throw ExceptionUtil.wrap(e);
				}
				resumenEquipoDto.setFlota(flota);
				
			} catch (Exception e) {
				throw ExceptionUtil.wrap(e);
			}
		}

	private void transformResumenEquipoArbolDTOToWCTO(
			List<ResumenPorEquipoArbolDTO> retrieveResumenPorEquipoArbol,
			ResumenEquipoDto resumenEquipoDto) {
		List<EquipoDto> listaEquipos = new ArrayList<EquipoDto>();
		for (ResumenPorEquipoArbolDTO resumenPorEquipoArbolDTO : retrieveResumenPorEquipoArbol) {
			EquipoDto equipoDto = new EquipoDto();
			equipoDto.setAbono(resumenPorEquipoArbolDTO.getAbono());
			equipoDto.setAlquiler(resumenPorEquipoArbolDTO.getAlquiler());
			equipoDto.setCliente(resumenPorEquipoArbolDTO.getNumeroCuenta());
			equipoDto.setDdiYRoaming(resumenPorEquipoArbolDTO.getDdiRoam());
			equipoDto.setDdn(resumenPorEquipoArbolDTO.getDdn());
			equipoDto.setExcedenteRadio(resumenPorEquipoArbolDTO.getExcRadio());
			equipoDto.setExcedenteTelefonia(resumenPorEquipoArbolDTO
					.getExcTel());
			equipoDto.setFacturaNumero(resumenPorEquipoArbolDTO.getFactura());
			equipoDto.setFechaEmision(resumenPorEquipoArbolDTO
					.getFechaEmision());
			equipoDto.setGarantia(resumenPorEquipoArbolDTO.getGarantia());
			equipoDto.setNumeroID(resumenPorEquipoArbolDTO.getNId());
			equipoDto.setPagers(resumenPorEquipoArbolDTO.getPagers());
			equipoDto.setProporcionalYReintegros(resumenPorEquipoArbolDTO
					.getPropYReint());
			equipoDto.setRedFija(resumenPorEquipoArbolDTO.getRedFija());
			equipoDto.setServicios(resumenPorEquipoArbolDTO.getServ());
			equipoDto.setTelefono(resumenPorEquipoArbolDTO.getTelefono());
			equipoDto.setTotalConImpuestos(resumenPorEquipoArbolDTO
					.getTotCImp());

			listaEquipos.add(equipoDto);
		}
		resumenEquipoDto.setEquipos(listaEquipos);
	}

	private void transformResumenEquipoRespPagoDTOToWCTO(
			List<ResumenPorEquipoResponsablePagoDTO> retrieveResumenPorEquipoResponsablePago,
			ResumenEquipoDto resumenEquipoDto) {
		List<EquipoDto> listaEquipos = new ArrayList<EquipoDto>();
		for (ResumenPorEquipoResponsablePagoDTO resumenRespPagolDTO : retrieveResumenPorEquipoResponsablePago) {
			EquipoDto equipoDto = new EquipoDto();
			equipoDto.setAbono(resumenRespPagolDTO.getAbono());
			equipoDto.setAlquiler(resumenRespPagolDTO.getAlquiler());
			equipoDto.setDdiYRoaming(resumenRespPagolDTO.getDdiRoam());
			equipoDto.setDdn(resumenRespPagolDTO.getDdn());
			equipoDto.setExcedenteRadio(resumenRespPagolDTO.getExcRadio());
			equipoDto.setExcedenteTelefonia(resumenRespPagolDTO.getExcTel());
			equipoDto.setGarantia(resumenRespPagolDTO.getGarantia());
			equipoDto.setNumeroID(resumenRespPagolDTO.getNId());
			equipoDto.setPagers(resumenRespPagolDTO.getPagers());
			equipoDto.setProporcionalYReintegros(resumenRespPagolDTO
					.getPropYReint());
			equipoDto.setRedFija(resumenRespPagolDTO.getRedFija());
			equipoDto.setServicios(resumenRespPagolDTO.getServ());
			equipoDto.setTelefono(resumenRespPagolDTO.getTelefono());
			equipoDto.setTotalConImpuestos(resumenRespPagolDTO.getTotCImp());
			resumenEquipoDto.setEmision(resumenRespPagolDTO
					.getFechaEmisionUltimaFactura());
			resumenEquipoDto.setFacturaNumero(resumenRespPagolDTO
					.getNumeroFactura());
			listaEquipos.add(equipoDto);
		}
		resumenEquipoDto.setEquipos(listaEquipos);
	}

	/**
	 * Obtiene los datos del scoring para mostrar en el pop up de Scoring de
	 * Infocom
	 */
	public ScoringDto consultarScoring(Long numeroCuenta, String codigoVantive)
	throws RpcExceptionMessages {
		try {
			if (codigoVantive != null) {
				Cuenta cuenta = cuentaBusinessService.getCuentaSinLockear(codigoVantive);
				numeroCuenta = cuenta.getId();
			} else {
				Cuenta cuenta = repository.retrieve(Cuenta.class, numeroCuenta);
				codigoVantive = cuenta.getCodigoVantive();
			}	
		} catch (Exception e) {
			throw ExceptionUtil.wrap(e);
		}
		AppLogger.info("Iniciando consulta de scoring para " + codigoVantive);
		CuentaScoringBusinessResult result;
		try {
			Cuenta cuentaSinLockear = cuentaBusinessService
			.getCuentaSinLockear(codigoVantive);
			result = cuentaScoringBusinessOperator
			.obtenerCuentaScoring(cuentaSinLockear.getCuentaRaiz()
					.getCodigoVantive());
		} catch (Exception e) {
			throw ExceptionUtil.wrap(e);
			// AppLogger.info("Error al consultar el scoring: " +
			// e.getMessage());
		}
		ScoringDto scoringDto = mapper.map(result.getScoringCuentaLegacyDTO(),
				ScoringDto.class);
		AppLogger.info("Consulta de scoring para " + codigoVantive
				+ " finalizado.");
		return scoringDto;
	}
}
