package ar.com.nextel.web.download;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * 
 * Servlet para descargar archivos fisicos o no.
 * 
 * Creada: 29/10/2007
 * 
 * @author ldegiovannini
 * 
 */
public class DownloadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private WebApplicationContext context;
    
    private FileServiceFactory factory;
    
    protected void doGet(HttpServletRequest servletRequest, HttpServletResponse response) throws ServletException,
            IOException {
    if(context == null){
    	context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    }
    if(factory == null){
	    factory = (FileServiceFactory)context.getBean("fileServiceFactory");
    }
    	Request request = this.factory.createRequest(servletRequest);
        factory.configureResponseType(response, request);
        factory.configureFileName(response, request);
        Data data = request.getData();
        configureLenght(response, data.lenght());
        data.sendTo(response);
    }

    private void configureLenght(HttpServletResponse response, int length) {
        response.setIntHeader("Content-Length", length);
        response.setContentLength(length);
    }
}
