package ar.com.nextel.web.download.config.impl;

import java.io.File;

import ar.com.nextel.business.constants.GlobalParameterIdentifier;
import ar.com.nextel.components.knownInstances.GlobalParameter;
import ar.com.nextel.components.knownInstances.retrievers.DefaultRetriever;
import ar.com.nextel.web.download.Request;
import ar.com.nextel.web.download.config.ServiceConfig;

/**
 * 
 * Servicio de archivo TIF del Veraz.
 *
 * Creada: 14/03/2012
 *
 * @author mrotger
 *
 */
public class DownloadVerazRichTextFormatService extends ServiceConfig{

	private static final String VERAZ = "VERAZ";
	private DefaultRetriever globalParameterRetriever;
	
	public void setGlobalParameterRetriever(
			DefaultRetriever globalParameterRetriever) {
		this.globalParameterRetriever = globalParameterRetriever;
	}

	//Este metodo define el nombre con el cual se va a bajar el archivo
    public String getFileName(Request request) {
        return request.getName();
    }
    
	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.com.nextel.web.download.config.ServiceConfig#getFile(java.lang.String)
	 */
	@Override
	public File getFile(String name) {
		GlobalParameter pathGlobalParameter = (GlobalParameter) globalParameterRetriever.
        		getObject(GlobalParameterIdentifier.SAMBA_PATH_RTF);
	      
		String path = pathGlobalParameter.getValue() + 
      			String.valueOf(File.separatorChar) + VERAZ;
		
		return new File(path, name);
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
