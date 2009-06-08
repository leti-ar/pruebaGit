package ar.com.nextel.sfa.client.operaciones;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.nextel.sfa.client.widget.UILoader;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

/**
 * Esta página contiene el formulario de busquedas de Operaciones (OppFilterForm) y la tabla de resultados
 * (OppResultPanel)
 * 
 * @author eSalvador
 * 
 */
public class OperacionEnCursoUI extends ApplicationUI {

	// private OppFilterForm oppFilterForm;
	private OperacionEnCursoResultUI oppResultPanel;

	public OperacionEnCursoUI() {
		super();
	}

	public void firstLoad() {
		oppResultPanel = new OperacionEnCursoResultUI();

		mainPanel.add(oppResultPanel);
		mainPanel.addStyleName("gwt-central-panel");
		Button aceptar = new Button(Sfa.constant().aceptar(), new ClickListener() {
			public void onClick(Widget sender) {
				UILoader.getInstance().setPage(UILoader.OP_EN_CURSO);
			}
		});
		mainPanel.add(aceptar);
	}

	public boolean load() {
		oppResultPanel.searchOperaciones();
		return true;
	}

	public boolean unload() {
		return true;
	}
}