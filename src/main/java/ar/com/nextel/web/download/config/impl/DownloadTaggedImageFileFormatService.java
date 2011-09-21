package ar.com.nextel.web.download.config.impl;

import java.io.File;

import ar.com.nextel.web.download.Request;
import ar.com.nextel.web.download.config.ServiceConfig;

/**
 * 
 * Servicio de archivo TIF de una Cuenta.
 *
 * Creada: 20/09/2011
 *
 * @author mrotger
 *
 */
public class DownloadTaggedImageFileFormatService extends ServiceConfig{
	
	//Este metodo define el nombre con el cual se va a bajar el archivo
    public String getFileName(Request request) {
    	String fileName = request.getModuleName() + "-" + request.getServiceName() + 
        	 "-" + request.getName().substring(request.getName().lastIndexOf(File.separatorChar)+1);
        return fileName;
    }
    
	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.com.nextel.web.download.config.ServiceConfig#getFile(java.lang.String)
	 */
	@Override
	public File getFile(String name) {
		//Llega algo como '\\arpalfls02\imaging\imagenes_general\orden_servicio\2002_06\5.66559-1-0300110.tif'
		name = name.replace('\\', File.separatorChar);
		name = name.replace('/', File.separatorChar);
		
		//Separo en path y nombre del archivo
		String path = name.substring(0,name.lastIndexOf(File.separatorChar)+1);
		String file = name.substring(name.lastIndexOf(File.separatorChar)+1);

		return new File(path, file);
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
