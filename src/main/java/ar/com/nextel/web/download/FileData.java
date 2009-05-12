package ar.com.nextel.web.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * 
 * Dato basado en un archivo fisico.
 * 
 * Creada: 30/10/2007
 * 
 * @author ldegiovannini
 * 
 */
public class FileData extends BaseData {

    private static final int BUFFER_SIZE = 8096;

    private File data;
    
    public FileData(File data) {
        this.data = data;
    }

    public int lenght() {
        return (int) data.length();
    }

    public void doSend(OutputStream outputStream) throws IOException {
        InputStream inputStream = new FileInputStream(data);
        byte[] buffer = new byte[BUFFER_SIZE];
        int read = 0;
        do {
            outputStream.write(buffer, 0, read);
            read = inputStream.read(buffer);
        } while (read > 0);
    }

}
