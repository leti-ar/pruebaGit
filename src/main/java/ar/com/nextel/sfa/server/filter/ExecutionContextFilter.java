package ar.com.nextel.sfa.server.filter;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import winit.uc.exception.UCException;
import winit.uc.facade.UCFacade;
import ar.com.nextel.business.vendedores.RegistroVendedores;
import ar.com.nextel.framework.repository.hibernate.HibernateRepository;
import ar.com.nextel.framework.security.Usuario;
import ar.com.nextel.services.components.sessionContext.SessionContext;
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.services.usercenter.SFAUserCenter;
import ar.com.nextel.services.usercenter.factory.SFAUserCenterFactory;
import ar.com.nextel.sfa.client.enums.PermisosEnum;
import ar.com.nextel.util.AppLogger;
import ar.com.nextel.util.ApplicationContextUtil;

/**
 * Este filtro prepara el contexto de ejecución de la aplicación
 * 
 * @author <a href="mailto:elorenzano@snoop.com.ar">Esteban Lorenzano</a>
 */
public class ExecutionContextFilter implements Filter {
	private static final Log log = LogFactory
			.getLog(ExecutionContextFilter.class);
	private RegistroVendedores registroVendedores;
	private SessionContextLoader sessionContext;
	private SFAUserCenterFactory sfaUserCenterFactory;
	private SFAUserCenter sfaUserCenter;
	//MGR - #873
	private HibernateRepository hibernateRepository;
	private boolean usarUserCenter = true;
	private UCFacade ucFacade;

	public void init(FilterConfig config) throws ServletException {
		ServletContext sc = config.getServletContext();
		ApplicationContext springContext = WebApplicationContextUtils
				.getWebApplicationContext(sc);
		// Carga el contexto en el Utils para que no levante nuevamente si se
		// utiliza.
		ApplicationContextUtil.getInstance(springContext);
		registroVendedores = (RegistroVendedores) springContext
				.getBean("registroVendedores");
		sessionContext = (SessionContextLoader) springContext
				.getBean("sessionContextLoader");
		sfaUserCenterFactory = (SFAUserCenterFactory) springContext
				.getBean("userCenterFactory");
		// MGR - #873
		hibernateRepository = (HibernateRepository) springContext
				.getBean("repository");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		prepareExecutionContext(request);
		chain.doFilter(request, response);
		diposeExecutionContext();
	}

	private void prepareExecutionContext(ServletRequest request) {

		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			ucFacade = (UCFacade) httpRequest.getSession().getAttribute(
					"UCFacade");
			HashMap permisos = (HashMap) httpRequest.getSession().getAttribute(
					SessionContext.PERMISOS);
			AppLogger.infoLog("---el mapa de permisos es: " + permisos, this);
			if (permisos == null) {
				permisos = loadPermisos(httpRequest);
				httpRequest.getSession().setAttribute(SessionContext.PERMISOS, permisos);
			}
			sessionContext.getSessionContext().put(SessionContext.PERMISOS,
					permisos);

		}

		if (usarUserCenter) {
			try {
				sfaUserCenter = sfaUserCenterFactory
						.createUserCenter((HttpServletRequest) request);
				sessionContext.getSessionContext().put(SessionContext.USUARIO,
						sfaUserCenter.getUsuario());
				sessionContext.getSessionContext().setVendedor(
						registroVendedores.getVendedor(sfaUserCenter
								.getUsuario()));

			} catch (UCException e) {
				log.error(e);
			}
		} else {
			Usuario usuario = new Usuario();
			usuario.setUserName("acsa1");
			if (sessionContext.getSessionContext().get(SessionContext.USUARIO) == null) {
				sessionContext.getSessionContext().put(SessionContext.USUARIO,
						usuario);
			}
			if (sessionContext.getSessionContext().getVendedor() == null) {
				sessionContext.getSessionContext().setVendedor(
						registroVendedores.getVendedor(usuario));
			}
		}
		//MGR - #873 - Se agrega el filtro
		hibernateRepository.enableTipoVendedorFilter();
	}

	/**
	 * Libera el contexto de ejecución
	 */
	private void diposeExecutionContext() {
		sessionContext.getSessionContext().setVendedor(null);
	}

	public void destroy() {
		sessionContext.getSessionContext().setVendedor(null);
	}

	private HashMap loadPermisos(HttpServletRequest request) {
		HashMap<String, Boolean> mapaPermisosServer = new HashMap<String, Boolean>(); // se
																						// cargan
																						// todos
		// usados
		// en el cliente.
		AppLogger.infoLog("!!!cargo los permisos:",this);
		
		for (PermisosEnum permiso : PermisosEnum.values()) {
			String tag = permiso.getValue();
			Boolean result = checkPermiso(tag, permiso.getAccion(),request);
			AppLogger.infoLog("!!!el check para : " +tag + " me retorna: "+ result,this);
			mapaPermisosServer.put(tag, result);
		}

//		sessionContext.getSessionContext().put(SessionContext.PERMISOS,
//				mapaPermisosServer);
		return mapaPermisosServer;

	}

	/**
	 * 
	 * @param permiso
	 * @param accion
	 * @return
	 */
	private Boolean checkPermiso(String permiso, String accion, HttpServletRequest request) {
		Boolean ret = false;
		try {
			if (ucFacade != null) {
				// FIXME PATCH Esto esta porque hay un BUG en User Center
				Object[] params = new Object[] { permiso, accion,	request };
				Object invokeRet = MethodUtils.invokeMethod(ucFacade,
						"checkPermission", params);
				ret = ((Boolean) invokeRet).booleanValue();
			} else {
				log
						.warn("Se intentó obtener permisos sin tener una UCFacade asignada.");
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

}
