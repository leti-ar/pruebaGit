package ar.com.nextel.sfa.client.widget;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;

/**
 * Esta clase es una tabla de 2 columnas, la segunda con alineacion a la derecha. Se creo para evitarponer
 * divs con float=right que son medios inestables. Necesita un estilo llamado "alignRight".
 */
public class DualPanel extends Composite {

	private Grid mainPanel;

	public DualPanel() {
		mainPanel = new Grid(1, 2);
		mainPanel.addStyleName("gwt-DualPanel");
		mainPanel.setCellPadding(0);
		mainPanel.setCellSpacing(0);
		mainPanel.getCellFormatter().addStyleName(0, 1, "alignRight");
		mainPanel.setWidth("98%");
		mainPanel.getRowFormatter().setVerticalAlign(0, HasVerticalAlignment.ALIGN_TOP);
		initWidget(mainPanel);
	}

	public void setLeft(Widget widget) {
		mainPanel.setWidget(0, 0, widget);
	}

	public Widget getLeft() {
		return mainPanel.getWidget(0, 0);
	}

	public void setRight(Widget widget) {
		mainPanel.setWidget(0, 1, widget);
	}

	public Widget getRight() {
		return mainPanel.getWidget(0, 1);
	}

	public void setWidth(String width) {
		mainPanel.setWidth(width);
	}

	public void setVerticalAligment(int column, VerticalAlignmentConstant alignment) {
		if (column == 0 || column == 1) {
			mainPanel.getCellFormatter().setVerticalAlignment(0, column, alignment);
		}
	}
}
