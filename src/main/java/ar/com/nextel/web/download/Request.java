package ar.com.nextel.web.download;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 * Request del pedido de descarga de archivo.
 * 
 * Creada: 30/10/2007
 * 
 * @author ldegiovannini
 * 
 */
public class Request {

    private String moduleName;
    
    private String serviceName;
    
    private String name;
    
    private FileServiceFactory factory;

    public Request(HttpServletRequest servletRequest, FileServiceFactory factory) {
        moduleName = servletRequest.getParameter("module");
        serviceName  = servletRequest.getParameter("service");
        name = servletRequest.getParameter("name");
        this.factory = factory;
    }

    public Data getData() {
        if (needFile()) {
            return new FileData(factory.getFile(this));
        } else {
            return new SimpleData(factory.getBytes(this));
        }
    }

    private boolean needFile() {
        return factory.needFile(this);
    }
    
    public String getModuleName() {
        return moduleName;
    }
    
    
    public String getName() {
        return name;
    }
    
    
    public String getServiceName() {
        return serviceName;
    }
}
