package ar.com.nextel.sfa.client.event;

import com.google.gwt.event.shared.HandlerManager;

public class EventBusUtil {
	private final static HandlerManager eventBus = new HandlerManager(null);
	
	public static HandlerManager getEventBus(){
		return eventBus;
	}

}
