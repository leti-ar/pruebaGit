package ar.com.nextel.web.download;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import ar.com.nextel.util.AppLogger;

/**
 * 
 * Dato basico que maneja los errores del envio.
 *
 * Creada: 30/10/2007
 *
 * @author ldegiovannini
 *
 */
public abstract class BaseData implements Data {
    
    public final void sendTo(HttpServletResponse response) {
        OutputStream outputStream;
        try {
            outputStream = response.getOutputStream();
            doSend(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            AppLogger.info("Error in outputStream...", e);
        }
    }

    protected abstract void doSend(OutputStream outputStream) throws IOException;
}
