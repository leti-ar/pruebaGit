package ar.com.nextel.web.download;

import java.io.IOException;
import java.io.OutputStream;


/**
 * 
 * Dato basado en un byte[]
 * 
 * Creada: 30/10/2007
 * 
 * @author ldegiovannini
 * 
 */
public class SimpleData extends BaseData {

    private byte[] data;

    public SimpleData(byte[] data) {
        this.data = data;
    }

    public int lenght() {
        return data.length;
    }

    public void doSend(OutputStream outputStream) throws IOException {
            outputStream.write(data);
    }

}
