package ar.com.nextel.sfa.client.veraz;

import ar.com.nextel.sfa.client.widget.ApplicationUI;

/**
 * 
 * @author mrial
 * 
 */

public class VerazUI extends ApplicationUI {

	private VerazFilterUI verazForm;
	private VerazResultUI verazResultUI;

	public VerazUI() {
		super();
	}

	public void firstLoad() {
		verazForm = new VerazFilterUI();
		verazResultUI = new VerazResultUI();
		verazForm.setVerazResultUI(verazResultUI);
		mainPanel.add(verazForm);
		mainPanel.add(verazResultUI);
		mainPanel.add(verazForm.getFooter());
		mainPanel.addStyleName("gwt-central-panel");
	}

	public boolean load() {
		return true;
	}

	public boolean unload() {
		return true;
	}

}
