package ar.com.nextel.sfa.server;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.model.cuentas.beans.Cuenta;
import ar.com.nextel.model.cuentas.beans.Vendedor;
import ar.com.nextel.model.oportunidades.beans.OperacionEnCurso;
import ar.com.nextel.model.solicitudes.beans.SolicitudServicio;
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.sfa.client.OperacionesRpcService;
import ar.com.nextel.sfa.client.dto.OperacionEnCursoDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;
import ar.com.nextel.sfa.client.dto.VentaPotencialVistaDto;
import ar.com.nextel.sfa.client.dto.VentaPotencialVistaResultDto;
import ar.com.nextel.sfa.server.businessservice.SolicitudBusinessService;
import ar.com.nextel.sfa.server.util.MapperExtended;
import ar.com.nextel.util.AppLogger;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;
import ar.com.snoop.gwt.commons.server.RemoteService;
import ar.com.snoop.gwt.commons.server.util.ExceptionUtil;

/**
 * @author esalvador
 */

public class OperacionesRpcServiceImpl extends RemoteService implements OperacionesRpcService {

	private WebApplicationContext context;
	private SessionContextLoader sessionContextLoader;
	private SolicitudBusinessService solicitudBusinessService;
	private MapperExtended mapper;
	private Repository repository;
	
	//MGR - #1359
	private static String oppEnCursoAdmCredito= "OPP_CURSO_ADM_CREDITO";
	private static String oppEnCursoNoTLMNoDAE= "OPP_CURSO_NO_TLM_NO_DAE";
	private static String oppEnCursoACancelarAdmCredito = "OPP_CURSO_A_CANCELAR_ADM_CREDITO";

	@Override
	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		sessionContextLoader = (SessionContextLoader) context.getBean("sessionContextLoader");
		solicitudBusinessService = (SolicitudBusinessService) context.getBean("solicitudBusinessService");
		mapper = (MapperExtended) context.getBean("dozerMapper");
		repository = (Repository) context.getBean("repository");
	}

	public List<OperacionEnCursoDto> searchOpEnCurso() {
		Vendedor vendedor = sessionContextLoader.getVendedor();
		
		//MGR - #1359
		if(!vendedor.isADMCreditos()){

			AppLogger.info("Obteniendo operaciones en curso para vendedor: " + vendedor.getUserName(), this);
			if(vendedor.isTelemarketing() || vendedor.isDAE()){
				List<OperacionEnCursoDto> operacionesEnCursoDto = mapper.convertList(
						vendedor.getOperacionesEnCurso(), OperacionEnCursoDto.class);
				return operacionesEnCursoDto;
			
			}else{
				//#3427 - Busco operaciones en curso por sucursal para los tipos de vendedor AP
				List<OperacionEnCurso> oppEnCurso = null;
				if (vendedor.isAP()) {
					oppEnCurso = this.repository.executeCustomQuery("OPP_CURSO_AP", vendedor.getSucursal().getId(), vendedor.getTipoVendedor());
				} else {
					oppEnCurso = this.repository.executeCustomQuery(oppEnCursoNoTLMNoDAE,vendedor.getId());
				}
				List<OperacionEnCursoDto> operacionesEnCursoDto = mapper.convertList(
						oppEnCurso, OperacionEnCursoDto.class);
				return operacionesEnCursoDto;
			}
		}
		else{
			AppLogger.info("Obteniendo operaciones en curso para vendedores del tipo Adm. de creditos.", this);
			//MGR - ISDN 1824 - Se modifico la consulta de las operaciones en curso para los administradores
			List<OperacionEnCurso> oppEnCurso = this.repository.executeCustomQuery
					(oppEnCursoAdmCredito, VendedorDto.TIPO_VENDEDOR_ADM_CREDITOS, vendedor.getId());
			
			List<OperacionEnCursoDto> operacionesEnCursoDto = mapper.convertList(
					oppEnCurso, OperacionEnCursoDto.class);
			return operacionesEnCursoDto;
		}
	}

	public VentaPotencialVistaResultDto searchReservas() {
		Vendedor vendedor = sessionContextLoader.getVendedor();
		AppLogger.info("Obteniendo reservas para vendedor: " + vendedor.getUserName(), this);
		List<VentaPotencialVistaDto> ventasPotencialesEnCursoDto = mapper.convertList(vendedor.getVentasPotencialesVistaEnCurso(), VentaPotencialVistaDto.class);
		VentaPotencialVistaResultDto ventaPotencialVistaResultDto = new VentaPotencialVistaResultDto(ventasPotencialesEnCursoDto, vendedor.getCantidadCuentasPotencialesNoConsultadasActivasYVigentes().toString());
		return ventaPotencialVistaResultDto;
	}

	public void cancelarOperacionEnCurso(String idOperacionEnCurso) throws RpcExceptionMessages {
		try {
			//MGR - #1761
			Vendedor vendedor = sessionContextLoader.getVendedor();
			
			OperacionEnCurso operacionEnCurso = null;
			if(vendedor.isADMCreditos()){
				List<OperacionEnCurso> oppEnCurso =  this.repository.executeCustomQuery
						(oppEnCursoACancelarAdmCredito, idOperacionEnCurso, vendedor.getId());
				
				if(oppEnCurso.isEmpty() || oppEnCurso.size() > 1){
					String error = "No se pudo identificar una operaci�n en curso con id: " + idOperacionEnCurso;
					AppLogger.error(error);
					throw ExceptionUtil.wrap(new Exception(error));
				}else{
					operacionEnCurso = oppEnCurso.get(0);
				}
			}else{
				operacionEnCurso = repository.retrieve(OperacionEnCurso.class,	idOperacionEnCurso);
			}
			
			solicitudBusinessService.cancelarOperacionEnCurso(operacionEnCurso);
		} catch (Exception e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
	}
	
	public void cancelarOperacionEnCurso(Long idCuenta) throws RpcExceptionMessages {
		try {
			Cuenta cuenta = repository.retrieve(Cuenta.class, idCuenta);
			List <SolicitudServicio> listaSolicitudes = (List <SolicitudServicio>)cuenta.getSolicitudesEnCarga();
			for (SolicitudServicio solicitud : listaSolicitudes) {
				OperacionEnCurso operacionEnCurso = new OperacionEnCurso(sessionContextLoader.getSessionContext().getVendedor(), cuenta, solicitud);
				solicitudBusinessService.cancelarOperacionEnCurso(operacionEnCurso);
			}
		} catch (Exception e) {
			AppLogger.error(e);
			throw ExceptionUtil.wrap(e);
		}
	}
	
}
