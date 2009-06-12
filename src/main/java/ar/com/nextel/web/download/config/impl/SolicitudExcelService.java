package ar.com.nextel.web.download.config.impl;

import java.io.File;

import ar.com.nextel.web.download.config.ServiceConfig;

/**
 * 
 * Servicio de archivo Excel de Solicitud de Servicio.
 *
 * Creada: 30/10/2007
 *
 * @author ldegiovannini
 *
 */
public class SolicitudExcelService extends ServiceConfig {

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.com.nextel.web.download.config.ServiceConfig#getFile(java.lang.String)
	 */
	@Override
	public File getFile(String name) {
		/*
		 * 
		 * El path del archivo debe coincidir con el path especificado por el
		 * siguiente parámetro: GlobalParameterIdentifier.REPORTS_DIR
		 * 
		 * TODO Revisar este parche, que puse porque ponerle "/tmp/" no es multiplataforma, ni prolijo! rgm
		 */
		String tmp = System.getenv("TMP");
		if ((tmp == null) || ( tmp.length()==0)) {
			tmp = "/tmp";
		}
		return new File(tmp, name);
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
