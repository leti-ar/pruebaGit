package ar.com.nextel.sfa.client.operaciones;

import ar.com.nextel.sfa.client.OperacionesRpcService;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

/**
 * Esta página contiene el formulario de busquedas de Operaciones (OppFilterForm) y la tabla de resultados
 * (OppResultPanel)
 * 
 * @author eSalvador
 * 
 */
public class OperacionEnCursoUI extends ApplicationUI implements OperacionEnCursoUIController {

	// private OppFilterForm oppFilterForm;
	private OperacionEnCursoResultUI oppResultPanel;

	public OperacionEnCursoUI() {
		super();
	}

	public void firstLoad() {
		oppResultPanel = new OperacionEnCursoResultUI(this);

		mainPanel.add(oppResultPanel);
		mainPanel.addStyleName("gwt-central-panel");
	}

	public boolean load() {
		oppResultPanel.searchOperacionesYReservas();
		return true;
	}

	public boolean unload(String token) {
		return true;
	}

	public void cancelarOperacionEnCurso(String idOperacionEnCurso, DefaultWaitCallback callback) {
		OperacionesRpcService.Util.getInstance().cancelarOperacionEnCurso(idOperacionEnCurso, callback);
	}
}