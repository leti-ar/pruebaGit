package ar.com.nextel.sfa.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ClickPermanenciaEvent extends GwtEvent<ClickPermanenciaEventHandler>{
	public static Type<ClickPermanenciaEventHandler> TYPE = new Type<ClickPermanenciaEventHandler>();
	private boolean isFactura;
	
	public ClickPermanenciaEvent(boolean isFactura) {
		this.isFactura = isFactura;
	}
	
	public boolean isFactura() {
		return isFactura;
	}

	@Override
	protected void dispatch(ClickPermanenciaEventHandler handler) {
		handler.onClickPermanencia(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ClickPermanenciaEventHandler> getAssociatedType() {
		return TYPE;
	}
}