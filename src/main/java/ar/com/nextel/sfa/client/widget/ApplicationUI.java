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

	protected boolean firstLoad = true;
	private SimplePanel page;
	protected FlowPanel mainPanel;

	public ApplicationUI() {
		mainPanel = new FlowPanel();
		page = new SimplePanel();
		page.setWidget(mainPanel);
		initWidget(page);
		addStyleName("Gwt-ApplicationUI");
	}

	/**
	 * Lanza la carga de la pagina. Se utiliza solo en el UILoader
	 * 
	 * @return true si se pudo cargar
	 */
	public boolean loadApplication() {
		if (firstLoad) {
			firstLoad = false;
			firstLoad();
		}
		return load();
	}

	/** Se ejecuta la primera vez que se carga el módulo */
	public abstract void firstLoad();

	/**
	 * Se ejecuta antes de cargar el módulo
	 * 
	 * @return true si se pudo descargar
	 */
	public abstract boolean load();

	/**
	 * Se ejecuta antes de descargar el módulo
	 * 
	 * @return true si se pudo descargar
	 */
	public abstract boolean unload();

}
