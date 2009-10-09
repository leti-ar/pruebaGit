package ar.com.nextel.sfa.client.widget;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;

public class LoadingPopup extends PopupPanel {

	public LoadingPopup() {
		addStyleName("gwt-LoadingPopup");
		
		FlowPanel mainPanel = new FlowPanel();
		mainPanel.addStyleName("gwt-LoadingPopup-panel");
		

		FlowPanel blackPanel = new FlowPanel();
		blackPanel.addStyleName("gwt-LoadingPopup-blackPanel");
		
		HTML text = new HTML("Espere un momento");
		text.addStyleName("gwt-LoadingPopup-text");
		
		FlowPanel tableWrapper = new FlowPanel();
		tableWrapper.addStyleName("gwt-LoadingPopup-tableWrapper");
		
		Grid layout = new Grid(1, 2);
		layout.setWidget(0, 0, new Image("images/loader.gif"));
		layout.setWidget(0, 1, text);
		tableWrapper.add(layout);
		
		mainPanel.add(blackPanel);
		mainPanel.add(tableWrapper);
		
		setWidget(mainPanel);
	}
}
