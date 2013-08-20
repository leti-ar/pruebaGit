package ar.com.nextel.web.download;

import java.io.File;
import java.util.List;

import ar.com.nextel.web.download.config.ServiceConfig;

public class DownloadServiceImpl implements DownloadService {

    private List<ServiceConfig> serviceConfigs;
    
    public ServiceConfig getServiceConfig(String service) {
        for (ServiceConfig config : serviceConfigs) {
            if (config.getServiceName().equals(service)) {
                return config;
            }
        }
        return null;
    }
    
    
    public void setServiceConfigs(List<ServiceConfig> serviceConfigs) {
        this.serviceConfigs = serviceConfigs;
    }

	public File downloadFile(String service, String fileName) {
		return this.getServiceConfig(service).getFile(fileName);
	}

}
