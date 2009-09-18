package ar.com.nextel.web.download;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.com.nextel.web.download.config.ModuleConfig;
import ar.com.nextel.web.download.config.ServiceConfig;

/**
 * 
 * Factory de los servicios de descarga
 * 
 * Creada: 30/10/2007
 * 
 * @author ldegiovannini
 * 
 */
public class FileServiceFactory {

    private List<ModuleConfig> moduleConfigs;
    
    protected FileServiceFactory() {
        moduleConfigs = new ArrayList<ModuleConfig>();
    }

    public boolean needFile(Request request) {
        return getServiceConfig(request).needFile();
    }

    public void configureResponseType(HttpServletResponse response, Request request) {
        getServiceConfig(request).configFileType(response);
    }
    
    public void configureFileName(HttpServletResponse response, Request request) {
        String fileName = getServiceConfig(request).getFileName(request);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
    }

    public File getFile(Request request) {
        return getServiceConfig(request).getFile(request.getName());
    }

    public byte[] getBytes(Request request) {
        return getServiceConfig(request).getBytes(request.getName());
    }
    
    private ServiceConfig getServiceConfig(Request request) {
        return getModule(request.getModuleName()).getServiceConfig(request.getServiceName());
    }
    
    private ModuleConfig getModule(String moduleName) {
        for (ModuleConfig config : moduleConfigs) {
            if (config.getModuleName().equals(moduleName)) {
                return config;
            }
        }
        return null;
    }

    public Request createRequest(HttpServletRequest servletRequest) {
        return new Request(servletRequest, this);
    }
    
    
    public void setModuleConfigs(List<ModuleConfig> moduleConfigs) {
        this.moduleConfigs = moduleConfigs;
    }
}