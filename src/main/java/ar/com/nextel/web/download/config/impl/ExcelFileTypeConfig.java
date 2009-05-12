package ar.com.nextel.web.download.config.impl;

import javax.servlet.http.HttpServletResponse;

import ar.com.nextel.web.download.config.FileTypeConfig;

/**
 * 
 * Configura la descarga de archivo xls.
 *
 * Creada: 30/10/2007
 *
 * @author ldegiovannini
 *
 */
public class ExcelFileTypeConfig implements FileTypeConfig {

    /**
     * @see ar.com.nextel.web.download.config.FileTypeConfig#config(javax.servlet.http.HttpServletResponse)
     */
    public void config(HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Description", "Display database as EXCEL");
    }
    
    /**
     * @see ar.com.nextel.web.download.config.FileTypeConfig#getFileExtension()
     */
    public String getFileExtension() {
        return ".xls";
    }

}
