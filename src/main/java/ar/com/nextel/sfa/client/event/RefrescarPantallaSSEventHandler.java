package ar.com.nextel.sfa.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Evento para refrescar la pantalla de Solicitud de Servicio.
 * @author mrotger
 *
 */
public interface RefrescarPantallaSSEventHandler extends EventHandler {

	public void refrescarPantallaSS(RefrescarPantallaSSEvent event);
}
