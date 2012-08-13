package ar.com.nextel.web.download.config.impl;

import javax.servlet.http.HttpServletResponse;

import ar.com.nextel.web.download.config.FileTypeConfig;

/**
 * Configura la descarga de archivo con extensión Tagged Image File.
 * Ver el archivo de configuración: spring-download-config.xml.
 * 
 * @author mrotger
 */

public class TaggedImageFileFormatFileTypeConfig implements FileTypeConfig{

	public void config(HttpServletResponse response) {
		response.setContentType("application/tif");
		response.setHeader("Content-Description", "Display database as Tagged Image File");
	}

	public String getFileExtension() {
		return ".tif";
	}
}
