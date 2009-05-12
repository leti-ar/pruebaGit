package ar.com.nextel.web.download;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * Representa un dato a ser enviado para que descargue el cliente.
 * 
 * Creada: 30/10/2007
 * 
 * @author ldegiovannini
 * 
 */
public interface Data {

    int lenght();

    void sendTo(HttpServletResponse response);
}
