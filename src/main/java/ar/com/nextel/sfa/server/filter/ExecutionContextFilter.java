package ar.com.nextel.sfa.server.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import winit.uc.exception.UCException;
import ar.com.nextel.business.vendedores.RegistroVendedores;
import ar.com.nextel.framework.repository.hibernate.HibernateRepository;
import ar.com.nextel.framework.security.Usuario;
import ar.com.nextel.services.components.sessionContext.SessionContext;
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.services.usercenter.SFAUserCenter;
import ar.com.nextel.services.usercenter.factory.SFAUserCenterFactory;
import ar.com.nextel.util.ApplicationContextUtil;

/**
 * Este filtro prepara el contexto de ejecución de la aplicación
 * 
 * @author <a href="mailto:elorenzano@snoop.com.ar">Esteban Lorenzano</a>
 */
public class ExecutionContextFilter implements Filter {
	private static final Log log = LogFactory.getLog(ExecutionContextFilter.class);
	private RegistroVendedores registroVendedores;
	private SessionContextLoader sessionContext;
 	private SFAUserCenterFactory sfaUserCenterFactory;
 	private SFAUserCenter sfaUserCenter;
 	//MGR - #873
 	private HibernateRepository hibernateRepository;
    private boolean usarUserCenter = true;

	public void init(FilterConfig config) throws ServletException {
		ServletContext sc = config.getServletContext();
		ApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(sc);
		// Carga el contexto en el Utils para que no levante nuevamente si se utiliza.
		ApplicationContextUtil.getInstance(springContext);
		registroVendedores = (RegistroVendedores) springContext.getBean("registroVendedores");
		sessionContext = (SessionContextLoader) springContext.getBean("sessionContextLoader");
		sfaUserCenterFactory = (SFAUserCenterFactory) springContext.getBean("userCenterFactory");
		//MGR - #873
		hibernateRepository = (HibernateRepository) springContext.getBean("repository");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		prepareExecutionContext(request);
		chain.doFilter(request, response);
		diposeExecutionContext();
	}

	private void prepareExecutionContext(ServletRequest request) {

		if (usarUserCenter) {
			try {
				sfaUserCenter =  sfaUserCenterFactory.createUserCenter((HttpServletRequest) request);
				sessionContext.getSessionContext().put(SessionContext.USUARIO, sfaUserCenter.getUsuario());
				sessionContext.getSessionContext().setVendedor(registroVendedores.getVendedor(sfaUserCenter.getUsuario()));
				
			} catch (UCException e) {
				log.error(e);
			} 
		} else {
			Usuario usuario = new Usuario();
			usuario.setUserName("acsa1");
			if (sessionContext.getSessionContext().get(SessionContext.USUARIO) == null) {
				sessionContext.getSessionContext().put(SessionContext.USUARIO, usuario);
			}
			if (sessionContext.getSessionContext().getVendedor() == null) {
				sessionContext.getSessionContext().setVendedor(registroVendedores.getVendedor(usuario));
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
}
