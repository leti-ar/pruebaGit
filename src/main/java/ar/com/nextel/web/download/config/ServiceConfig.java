package ar.com.nextel.web.download.config;

import java.io.File;

import javax.servlet.http.HttpServletResponse;

import ar.com.nextel.web.download.Request;

/**
 * 
 * Configuracion de servicios base.
 * 
 * Creada: 30/10/2007
 * 
 * @author ldegiovannini
 * 
 */
public abstract class ServiceConfig {

    private FileTypeConfig fileTypeConfig;

    private FileNameConfig fileNameConfig;
    
    private String serviceName;

    private String moduleName;

    public void configFileType(HttpServletResponse response) {
        fileTypeConfig.config(response);
    }

    public String getFileName(Request request) {
        return fileNameConfig.getFileName(request, fileTypeConfig.getFileExtension());
    }

    public boolean needFile() {
        return true;
    }

    public File getFile(String name) {
        throw new UnsupportedOperationException("No esta soportado este servicio para descarga de archivos fisicos");
    }

    /**
     * @param name Parámetros de la url. 
     */
    public byte[] getBytes(String name) {
        throw new UnsupportedOperationException("No esta soportado este servicio para descarga de datos");
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setFileNameConfig(FileNameConfig fileNameConfig) {
        this.fileNameConfig = fileNameConfig;
    }

    public void setFileTypeConfig(FileTypeConfig fileTypeConfig) {
        this.fileTypeConfig = fileTypeConfig;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

}
