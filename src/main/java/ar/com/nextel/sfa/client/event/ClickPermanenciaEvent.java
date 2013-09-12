package ar.com.nextel.sfa.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ClickPermanenciaEvent extends GwtEvent<ClickPermanenciaEventHandler>{
	public static Type<ClickPermanenciaEventHandler> TYPE = new Type<ClickPermanenciaEventHandler>();
	
	public ClickPermanenciaEvent() {}

	@Override
	protected void dispatch(ClickPermanenciaEventHandler handler) {
		handler.onClickPermanencia(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ClickPermanenciaEventHandler> getAssociatedType() {
		return TYPE;
	}
}