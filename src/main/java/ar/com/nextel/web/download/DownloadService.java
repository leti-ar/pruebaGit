package ar.com.nextel.web.download;

import java.io.File;

public interface DownloadService {
	
	public File downloadFile(String service, String fileName);
}
