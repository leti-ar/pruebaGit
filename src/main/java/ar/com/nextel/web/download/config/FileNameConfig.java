package ar.com.nextel.web.download.config;

import ar.com.nextel.web.download.Request;

/**
 * 
 * Configuracion del nombre dado al archivo
 *
 * Creada: 30/10/2007
 *
 * @author ldegiovannini
 *
 */
public class FileNameConfig {

    public String getFileName(Request request, String extension) {
        String fileName = request.getModuleName() + "-" + request.getServiceName() + 
        "-" + request.getName();
        return fileName;
    }

}
