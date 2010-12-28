package ar.com.nextel.sfa.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ClickPincheEvent extends GwtEvent<ClickPincheEventHandler>{
	public static Type<ClickPincheEventHandler> TYPE = new Type<ClickPincheEventHandler>();
	public boolean isClicked;
	private String contrato;
	
	public ClickPincheEvent(String contrato, boolean isClicked) {
		this.isClicked = isClicked;
		this.contrato = contrato;
	}
	
	public boolean isClicked() {
		return isClicked;
	}
	
	public String getContrato() {
		return contrato;
	}
	
	@Override
	protected void dispatch(ClickPincheEventHandler handler) {
		handler.onClickPinche(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ClickPincheEventHandler> getAssociatedType() {
		return TYPE;
	}
}
