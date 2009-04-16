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
	private VerazResultUI verazResultUI;
	
	public VerazUI() {
		super();
	}
		
	
	public void load() {	
		if (firstLoad) {
			firstLoad = false;
			verazForm = new VerazFilterUI();
			verazResultUI = new VerazResultUI();
			verazForm.setVerazResultUI(verazResultUI);						
			mainPanel.add(verazForm);
			mainPanel.add(verazResultUI);
			mainPanel.add(verazForm.getFooter());
			mainPanel.addStyleName("gwt-central-panel");
		}
	}
	

	public void unload() {
	
	}
	
}
