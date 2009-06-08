package ar.com.nextel.sfa.client.oportunidad;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.nextel.sfa.client.widget.UILoader;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

public class BuscarOportunidadUI extends ApplicationUI {

	private BuscarOportunidadFilterUI buscarOportunidadFilterForm;
	private BuscarOportunidadResultUI buscarOportunidadResultPanel;

	public BuscarOportunidadUI() {
		super();
	}

	public void firstLoad() {
		buscarOportunidadFilterForm = new BuscarOportunidadFilterUI();
		buscarOportunidadResultPanel = new BuscarOportunidadResultUI();
		buscarOportunidadFilterForm.setBuscarOportunidadResultPanel(buscarOportunidadResultPanel);

		mainPanel.add(buscarOportunidadFilterForm);
		mainPanel.add(buscarOportunidadResultPanel);
		mainPanel.addStyleName("gwt-central-panel");

		Button aceptar = new Button(Sfa.constant().aceptar(), new ClickListener() {
			public void onClick(Widget sender) {
				UILoader.getInstance().setPage(UILoader.BUSCAR_OPP);
			}
		});
		mainPanel.add(aceptar);
	}

	public boolean load() {
		return true;
	}

	public boolean unload() {
		return true;
	}
}
