package ar.com.nextel.sfa.client.ss;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;

public class BusqClienteCedenteUI extends Composite{
		private FlowPanel mainPanel;
	private Grid busqLayout;
	
	private BusqClienteCedenteUIData busqClienteCedenteUIData;

	public BusqClienteCedenteUI(BusqClienteCedenteUIData busqClienteCedenteUIData){
		this.busqClienteCedenteUIData = busqClienteCedenteUIData;
		
		mainPanel = new FlowPanel();
		initWidget(mainPanel);
		initGenericPanel();
	}
	
	private void initGenericPanel(){
		busqLayout = new Grid(1,2);
		busqLayout.addStyleName("layout");
		busqLayout.getCellFormatter().setWidth(0, 0, "100px");
		busqLayout.setWidget(0, 0, busqClienteCedenteUIData.getCriterioBusqCedente());
		busqLayout.setWidget(0, 1, busqClienteCedenteUIData.getParametroBusqCedente());
		mainPanel.add(busqLayout);
	}
	
}
