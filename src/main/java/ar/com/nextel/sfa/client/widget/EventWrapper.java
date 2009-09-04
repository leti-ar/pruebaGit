package ar.com.nextel.sfa.client.widget;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.SimplePanel;

public abstract class EventWrapper extends SimplePanel implements EventListener {

	public EventWrapper() {
		super();
		sinkEvents(Event.ONKEYPRESS);
	}

	public void onBrowserEvent(Event event) {
		switch (DOM.eventGetType(event)) {
		case Event.ONKEYPRESS:
			if (event.getKeyCode() == KeyCodes.KEY_ENTER)
				doEnter();
			break;
		default:
			break;
		}
		super.onBrowserEvent(event);
	}

	abstract public void doEnter();
}
