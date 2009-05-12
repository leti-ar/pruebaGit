package ar.com.nextel.web.download.config;

import java.util.List;

/**
 * 
 * Configuracion de modulos.
 *
 * Creada: 30/10/2007
 *
 * @author ldegiovannini
 *
 */
public class ModuleConfig {
    
    private String moduleName;
    
    private List<ServiceConfig> serviceConfigs;
    
    public ServiceConfig getServiceConfig(String service) {
        for (ServiceConfig config : serviceConfigs) {
            if (config.getServiceName().equals(service)) {
                return config;
            }
        }
        return null;
    }
    
    public String getModuleName() {
        return moduleName;
    }
    
    public void setServiceConfigs(List<ServiceConfig> serviceConfigs) {
        this.serviceConfigs = serviceConfigs;
    }

    
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
