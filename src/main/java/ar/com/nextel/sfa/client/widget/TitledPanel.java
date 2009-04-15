package ar.com.nextel.sfa.client.widget;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

/**
 * Este componente se basa en un FlowPanel. Al mismo se agrega un título y otros estilos.
 * 
 * @author jlgperez
 * 
 */
public class TitledPanel extends FlowPanel {

	public TitledPanel(String title) {
		addStyleName("gwt-TitledPanel");
		HTML titleWidget = new HTML(title);
		titleWidget.setStyleName("title");
		add(titleWidget);
	}
}
