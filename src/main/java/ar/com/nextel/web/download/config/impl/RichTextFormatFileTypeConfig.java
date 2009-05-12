package ar.com.nextel.web.download.config.impl;

import javax.servlet.http.HttpServletResponse;

import ar.com.nextel.web.download.config.FileTypeConfig;

/*
 * Configura la descarga de archivo con extensi�n Rich Text Format.
 * Ver el archivo de configuraci�n: spring-download-config.xml.
 */
public class RichTextFormatFileTypeConfig implements FileTypeConfig {

	public void config(HttpServletResponse response) {
		response.setContentType("application/rtf");
		response.setHeader("Content-Description", "Display database as Rich Text Format");
	}

	public String getFileExtension() {
		return ".rtf";
	}

}
