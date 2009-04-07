package ar.com.nextel.sfa.client.widget;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Representa un modulo principal de la aplicación. Permite implementar los metodos de load() y unload() para
 * poder manejar la inicialización. @see UILoader
 * 
 * @author jlgperez
 * 
 */
public abstract class ApplicationUI extends Composite {

	private SimplePanel page;
	protected FlowPanel mainPanel;

	public ApplicationUI() {
		mainPanel = new FlowPanel();
		page = new SimplePanel();
		page.setWidget(mainPanel);
		initWidget(page);
	}

	public abstract void load();

	public abstract void unload();

}
