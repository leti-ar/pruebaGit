package ar.com.nextel.sfa.client.infocom;

import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.nextel.sfa.client.widget.RazonSocialClienteBar;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

public class InfocomUI extends ApplicationUI {
	
	private InfocomIUData infocomIUData;
	private FlowPanel contenedorPanel;
	
	private ScoringInfocomUI scoringInfocomUI;

	public InfocomUI() {
		super();
	}
	
	public void firstLoad() {
		infocomIUData = new InfocomIUData();
	}

	public boolean load() {
		contenedorPanel = new FlowPanel();
		contenedorPanel.addStyleName("gwt-ContendorPanel");
		scoringInfocomUI = new ScoringInfocomUI();
		RazonSocialClienteBar razonSocialClienteBar = new RazonSocialClienteBar();
		mainPanel.add(razonSocialClienteBar);

		HTML html = new HTML();
		html.setHTML("<br clear='all' />");
		contenedorPanel.add(html);
		contenedorPanel.add(scoringInfocomUI);
		mainPanel.add(contenedorPanel);
		return true;
	}


	public boolean unload(String token) {
		return true;
	}

}
