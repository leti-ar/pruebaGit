package ar.com.nextel.web.download.config.impl;

import java.io.File;

import ar.com.nextel.components.report.ReportPathBuilder;
import ar.com.nextel.web.download.config.ServiceConfig;

/**
 * Implementaci�n del servicio para la configuraci�n de la descarga de archivos
 * de tipo Rich Text Format.
 * 
 * @author cacciaresi
 */
public class SolicitudRichTextFormatService extends ServiceConfig {

    private ReportPathBuilder reportPathBuilder;
    
    public void setReportPathBuilder(ReportPathBuilder reportPathBuilder) {
        this.reportPathBuilder = reportPathBuilder;
    }
    
	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.com.nextel.web.download.config.ServiceConfig#getFile(java.lang.String)
	 */
	@Override
	public File getFile(String name) {
		/*
		 * El path del archivo debe coincidir con el path especificado por el
		 * siguiente par�metro: GlobalParameterIdentifier.REPORTS_DIR
		 */
        return new File(this.reportPathBuilder.buildReportPath(), name);	    
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.com.nextel.web.download.config.ServiceConfig#needFile()
	 */
	@Override
	public boolean needFile() {
		return true;
	}
	
}
