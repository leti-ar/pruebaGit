package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.widget.TitledPanel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class VariosSSUI extends Composite{

	private FlowPanel mainpanel;
	private CrearSSUIData crearSSUIData;
	
	public VariosSSUI(CrearSSUIData crearSSUIData) {
		mainpanel = new FlowPanel();
		initWidget(mainpanel);
		this.crearSSUIData = crearSSUIData;
		
	}
	
	private Widget getPataconex(){
		TitledPanel pataconexPanel = new TitledPanel(Sfa.constant().pataconexTitle());
		return pataconexPanel;
	}
	
	private Widget getCreditoFidelizacion(){
		TitledPanel creditoPanel = new TitledPanel(Sfa.constant().creditoFidelizacionTitle());
		return creditoPanel;	
	}
	
	private Widget getFirmas(){
		TitledPanel firmasPanel = new TitledPanel(Sfa.constant().firmaTitle());
		return firmasPanel;		
	}
	
	private Widget getResumen(){
		TitledPanel resumenPanel = new TitledPanel(Sfa.constant().firmaTitle());
		return resumenPanel;	
	}
	
	private Widget getScoring(){
		TitledPanel scoringPanel = new TitledPanel(Sfa.constant().firmaTitle());
		return scoringPanel;	
	}
}
