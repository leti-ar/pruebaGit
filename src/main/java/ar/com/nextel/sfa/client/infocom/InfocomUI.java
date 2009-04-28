package ar.com.nextel.sfa.client.infocom;

import ar.com.nextel.sfa.client.widget.ApplicationUI;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

public class InfocomUI extends ApplicationUI  {
	private FlowPanel contenedorPanel;
	private ScoringInfocomUI scoringInfocomUI;
	
	
	public InfocomUI() {
		super();
	}


	@Override
	public void load() {
		contenedorPanel = new FlowPanel();
		contenedorPanel.addStyleName("gwt-ContendorPanel");
		scoringInfocomUI = new ScoringInfocomUI();
		Grid header = new Grid(1,2);
		FlowPanel razonSocial = new FlowPanel();
		razonSocial.addStyleName("razonSocial");
		razonSocial.add(new Label("Razon Social"));
		Label razonLabel = new Label("ABAD, ANTONIO L");
		razonSocial.add(razonLabel);
		header.setWidget(0, 0, razonSocial);
		
		FlowPanel numeroCliente = new FlowPanel();
		numeroCliente.addStyleName("numeroCliente");
		numeroCliente.add(new Label("Cliente:"));
		Label clienteLabel = new Label("Algoo");
		numeroCliente.add(clienteLabel);
		header.setWidget(0, 1, numeroCliente);
		mainPanel.add(header);
		
		HTML html = new HTML();
		html.setHTML("<br clear='all' />");
		contenedorPanel.add(html);
		contenedorPanel.add(scoringInfocomUI);
		mainPanel.add(contenedorPanel);
	}

	@Override
	public void unload() {
		// TODO Auto-generated method stub
		
	}
	

}
