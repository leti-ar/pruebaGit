package ar.com.nextel.sfa.client.widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class FormButtonsBar extends Composite {

	protected FlowPanel formButtons;

	public FormButtonsBar() {
		formButtons = new FlowPanel();
		formButtons.addStyleName("popFormButtons");
		formButtons.setWidth("100%");
		initWidget(formButtons);
	}

	public void setWidth(String width) {
		formButtons.setWidth(width);
	}

	/** Agrega un link a la barra de comandos del formulario. */
	public void addLink(Widget link) {
		link.addStyleName("popFormButton");
		formButtons.add(link);
	}

	/**
	 * Agrega un link a la barra de comandos del formulario en la posicion dada. En el caso que no exista (la
	 * barra), la crea. <br>
	 * FIXME: cambiar la lógica cuando se resuelva el <a
	 * href="http://0-code.google.com.millennium.unicatt.it/p/google-web-toolkit/issues/detail?id=341">issue
	 * 341</a> de GWT
	 */
	public void addLink(Widget link, int index) {
		link.addStyleName("popFormButton");
		List widgets = new ArrayList();
		for (int i = 0; i < formButtons.getWidgetCount(); i++) {
			widgets.add(formButtons.getWidget(i));
		}
		formButtons.clear();
		widgets.add(index, link);
		for (Iterator iterator = widgets.iterator(); iterator.hasNext();) {
			formButtons.add((Widget) iterator.next());

		}
	}
}
