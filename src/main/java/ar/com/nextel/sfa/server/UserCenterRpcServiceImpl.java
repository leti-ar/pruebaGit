package ar.com.nextel.sfa.server;

import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.commons.beanutils.MethodUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import winit.uc.facade.UCFacade;
import ar.com.nextel.business.solicitudes.repository.SolicitudServicioRepository;
import ar.com.nextel.business.vendedores.RegistroVendedores;
import ar.com.nextel.framework.security.Usuario;
import ar.com.nextel.model.solicitudes.beans.GrupoSolicitud;
import ar.com.nextel.services.components.sessionContext.SessionContext;
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.sfa.client.UserCenterRpcService;
import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.dto.UserCenterDto;
import ar.com.nextel.sfa.client.dto.UsuarioDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;
import ar.com.nextel.sfa.client.enums.PermisosEnum;
import ar.com.nextel.sfa.server.util.MapperExtended;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;
import ar.com.snoop.gwt.commons.server.RemoteService;

public class UserCenterRpcServiceImpl extends RemoteService implements UserCenterRpcService {

	private static final long serialVersionUID = 1L;
	private WebApplicationContext context;
	private MapperExtended mapper;
	private SessionContextLoader sessionContext;
	private UCFacade ucFacade;
	private RegistroVendedores registroVendedores;
	private SolicitudServicioRepository solicitudServicioRepository; 

	@Override
	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		mapper = (MapperExtended) context.getBean("dozerMapper");
		sessionContext = (SessionContextLoader) context.getBean("sessionContextLoader");
		registroVendedores = (RegistroVendedores) context.getBean("registroVendedores");
		solicitudServicioRepository = (SolicitudServicioRepository) context.getBean("solicitudServicioRepositoryBean");
	}

	/**
	 * 
	 */
	public UserCenterDto getUserCenter() throws RpcExceptionMessages {
		
		HashMap<String, Boolean> mapaPermisosServer = new HashMap<String, Boolean>(); // se cargan todos
		HashMap<String, Boolean> mapaPermisosClient = new HashMap<String, Boolean>(); // se cargan solo los
		// usados
		// en el cliente.
		for (PermisosEnum permiso : PermisosEnum.values()) {
			String tag = permiso.getValue();
			Boolean result = checkPermiso(tag, permiso.getAccion());
			mapaPermisosServer.put(tag, result);
			if (permiso.isForBrowser())
				mapaPermisosClient.put(tag, result);
		}
		
		//MGR - Integracion
		//Ambos deben poder editar
		mapaPermisosClient.put("rootsMenuPanel.cuentasEditar", true);
		//Para que se vea Infocom si no es telemarketing
		mapaPermisosClient.put("verInfocom", true);
		//Para ver la seccion "Reservas" de "Op. en Curso"
		mapaPermisosClient.put("opEnCursoSeccionReservas", true);
		//Para ver todas las secciones en la pestaña "Varios"
		mapaPermisosClient.put("variosCreditoFidelizacion",true);
		mapaPermisosClient.put("variosPataconex",true);
		mapaPermisosClient.put("variosFirmas",true);
		mapaPermisosClient.put("variosAnticipos",true);
		//Solo para que funcione por ahora
		if(sessionContext.getVendedor().getTipoVendedor().getCodigoVantive().equals("TELE")){
			mapaPermisosClient.put("rootsMenuPanel.verazButton", true);
			mapaPermisosClient.put("verInfocom", false);
			mapaPermisosClient.put("opEnCursoSeccionReservas", false);
			mapaPermisosClient.put("variosCreditoFidelizacion",false);
			mapaPermisosClient.put("variosPataconex",false);
			mapaPermisosClient.put("variosFirmas",false);
			mapaPermisosClient.put("variosAnticipos",false);
			
			
			
			mapaPermisosClient.put("rootsMenuPanel.cuentasButtonMenu",true);
			mapaPermisosClient.put("rootsMenuPanel.cuentasBuscarMenu",false);
			mapaPermisosClient.put("rootsMenuPanel.cuentasAgregarMenu",false);
			mapaPermisosClient.put("rootsMenuPanel.agregarProspectButton", true);
			mapaPermisosClient.put("crearNuevaSS",true);
			mapaPermisosClient.put("rootsMenuPanel.ssButton",false);
			
			mapaPermisosClient.put("rootsMenuPanel.verazButton",true);
			mapaPermisosClient.put("rootsMenuPanel.busquedaOportunidadesButton",false);
			mapaPermisosClient.put("rootsMenuPanel.operacionesEnCursoButton", true);
		}
		

		
		sessionContext.getSessionContext().put(SessionContext.PERMISOS, mapaPermisosServer);
		UserCenterDto userCenter = new UserCenterDto();
		
		Usuario usuario = (Usuario) sessionContext.getSessionContext().get(SessionContext.USUARIO);
		userCenter.setUsuario(mapper.map(usuario, UsuarioDto.class));
		userCenter.getUsuario().setId(registroVendedores.getVendedor(usuario).getId());
		userCenter.setMapaPermisos(mapaPermisosClient);
		
		userCenter.setVendedor(mapper.map(sessionContext.getVendedor(), VendedorDto.class));

		//MGR - #873 - Se buscan los grupos que le corresponden. El filtro ya esta activado y por lo tanto
		//tiene en cuenta el TipoVendedor
		List<GrupoSolicitud> grupos =  solicitudServicioRepository.getGruposSolicitudesServicio();
		userCenter.getVendedor().getTipoVendedor().setGrupos(mapper.convertList(grupos, GrupoSolicitudDto.class));
		return userCenter;
	}

	/**
	 * Permisos para desarrollo
	 */
	public UserCenterDto getDevUserData() throws RpcExceptionMessages {
		HashMap<String, Boolean> mapaPermisos = new HashMap<String, Boolean>();
		for (PermisosEnum permiso : PermisosEnum.values()) {
			String tag = permiso.getValue();
			if (tag.equals("MI_DEV_TAG") || tag.equals("MI_OTRO_DEV_TAG"))
				mapaPermisos.put(tag, Boolean.FALSE);
			else
				mapaPermisos.put(tag, Boolean.TRUE);
		}
		sessionContext.getSessionContext().put(SessionContext.PERMISOS, mapaPermisos);
		UserCenterDto userCenter = new UserCenterDto();
		userCenter.setUsuario(mapper.map((Usuario) sessionContext.getSessionContext().get(
				SessionContext.USUARIO), UsuarioDto.class));
		userCenter.getUsuario().setId(57L);
		userCenter.setMapaPermisos(mapaPermisos);
		return userCenter;
	}

	/**
	 * 
	 * @param permiso
	 * @param accion
	 * @return
	 */
	public Boolean checkPermiso(String permiso, String accion) {
		Boolean ret = false;
		initUCFacade();
		try {
			if (ucFacade != null) {
				// FIXME PATCH Esto esta porque hay un BUG en User Center
				Object[] params = new Object[] { permiso, accion, getThreadLocalRequest() };
				Object invokeRet = MethodUtils.invokeMethod(ucFacade, "checkPermission", params);
				ret = ((Boolean) invokeRet).booleanValue();
			} else {
				log.warn("Se intentó obtener permisos sin tener una UCFacade asignada.");
			}
		} catch (NullPointerException e) {
			// Es false
			log.warn("La UCFacade tiró un NPE.");
			// } catch (UCAuthorizationException e) {
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			// throw new GeneralException(e.getMessage(), e);
		}
		return ret;
	}

	/**
	 * 
	 */
	private void initUCFacade() {
		try {
			ucFacade = (UCFacade) getThreadLocalRequest().getSession().getAttribute("UCFacade");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
