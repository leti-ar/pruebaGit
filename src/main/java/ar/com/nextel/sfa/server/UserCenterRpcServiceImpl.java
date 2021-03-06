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
import ar.com.nextel.components.knownInstances.KnownInstanceConfigItem;
import ar.com.nextel.framework.repository.Repository;
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
	//MGR - #1050
	private Repository repository;

	@Override
	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		mapper = (MapperExtended) context.getBean("dozerMapper");
		sessionContext = (SessionContextLoader) context.getBean("sessionContextLoader");
		registroVendedores = (RegistroVendedores) context.getBean("registroVendedores");
		solicitudServicioRepository = (SolicitudServicioRepository) context.getBean("solicitudServicioRepositoryBean");
		repository = (Repository) context.getBean("repository");
	}

	/**
	 * 
	 */
	public UserCenterDto getUserCenter() throws RpcExceptionMessages {
		
		HashMap<String, Boolean> mapaPermisosServer = (HashMap<String, Boolean>)sessionContext.getSessionContext().get(SessionContext.PERMISOS);
		HashMap<String, Boolean> mapaPermisosClient = new HashMap<String, Boolean>(); // se cargan solo los
		// usados
		// en el cliente.
		for (PermisosEnum permiso : PermisosEnum.values()) {
		 	if (permiso.isForBrowser())
				mapaPermisosClient.put(permiso.getValue(),	mapaPermisosServer.get(permiso.getValue()));
		}
		
		
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
	
	//MGR - #1050
	//Se encarga de cargar todas las instancias de la tabla SFA_KNOWNINSTANCE_ITEM
	public HashMap<String, Long> getKnownInstance() {
		HashMap<String, Long> knownInstance = new HashMap<String, Long>();
		List<KnownInstanceConfigItem> instancias = repository.find("from KnownInstanceConfigItem");
		
		for (KnownInstanceConfigItem inst : instancias) {
			knownInstance.put(inst.getKey(), inst.getInstanceId());
		}
		return knownInstance;
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
