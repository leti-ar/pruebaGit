package ar.com.nextel.sfa.client.veraz;

import ar.com.nextel.sfa.client.widget.ApplicationUI;

/**
 * 
 * @author mrial
 *
 */

public class VerazUI extends ApplicationUI {
	
	protected boolean firstLoad = true;
	private VerazFilterUI verazForm;
	
	public VerazUI() {
		super();
	}
		
	
	public void load() {	
		if (firstLoad) {
			firstLoad = false;
			VerazFilterUI verazForm = new VerazFilterUI();
			
			mainPanel.add(verazForm);
			mainPanel.addStyleName("gwt-central-panel");
		}
	}
	

	public void unload() {
	
	}
	
}
