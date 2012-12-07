package ar.com.nextel.sfa.client.event;

import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Evento para refrescar la pantalla de Solicitud de Servicio. Se utiliza para saber
 * que opciones tiene la solicitud segun los items que se cargan
 * @author mrotger
 *
 */
public class RefrescarPantallaSSEvent extends GwtEvent<RefrescarPantallaSSEventHandler> {

	public static Type<RefrescarPantallaSSEventHandler> TYPE = new Type<RefrescarPantallaSSEventHandler>();
	
	private SolicitudServicioDto solicitud;
	private boolean retiraEnSucursal = false;
	
	public RefrescarPantallaSSEvent(SolicitudServicioDto solicitud, boolean retiraEnSucursal){
		this.solicitud = solicitud;
		this.retiraEnSucursal = retiraEnSucursal;
	}
	
	public SolicitudServicioDto getSolicitud() {
		return solicitud;
	}

	public boolean isRetiraEnSucursal() {
		return retiraEnSucursal;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<RefrescarPantallaSSEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RefrescarPantallaSSEventHandler handler) {
		handler.refrescarPantallaSS(this);
	}
	
}
