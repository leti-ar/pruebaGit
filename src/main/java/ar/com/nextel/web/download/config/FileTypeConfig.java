package ar.com.nextel.web.download.config;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * Configura el response para un determinado tipo de archivo.
 *
 * Creada: 30/10/2007
 *
 * @author ldegiovannini
 *
 */
public interface FileTypeConfig {

    void config(HttpServletResponse response);

    String getFileExtension();

}
