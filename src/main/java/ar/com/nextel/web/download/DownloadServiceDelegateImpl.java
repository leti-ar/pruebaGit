package ar.com.nextel.web.download;

import java.io.File;

import ar.com.nextel.services.nextelServices.DownloadServiceDelegate;

/**
 * @see ar.com.nextel.services.nextelServices.DownloadServiceDelegate
 * @author jmonczer
 */
public class DownloadServiceDelegateImpl implements DownloadServiceDelegate {
	private DownloadService downloadService;
	
	public File downloadFile(String service, String fileName) {
		return this.downloadService.downloadFile(service, fileName);
	}

	public void setDownloadService(DownloadService downloadService) {
		this.downloadService = downloadService;
	}
}
