package ar.com.nextel.sfa.client.infocom;

import ar.com.nextel.sfa.client.widget.TitledPanel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author mrial
 */
public class FidelizacionInfocomUI extends Composite {

	private InfocomUIData infocomUIData;
	private TitledPanel fidelizacionTitledPanel;
	private HorizontalPanel valoresPanel;
	private Label detalleLabel;
	private SimplePanel fidelizacionTableWrapper;


	public FidelizacionInfocomUI(InfocomUIData infocomUIData) {
		this.infocomUIData = infocomUIData;
		fidelizacionTitledPanel = new TitledPanel("Crédito de Fidelización");
		fidelizacionTitledPanel.addStyleName("contenedor");

		valoresPanel = new HorizontalPanel();
		valoresPanel.add(infocomUIData.getMontoPanel());
		valoresPanel.add(infocomUIData.getEstadoPanel());
		valoresPanel.add(infocomUIData.getVencimientoPanel());
		valoresPanel.setWidth("100%");

		detalleLabel = new Label("Detalle Bonif. Otorgadas - Facturación de Eq. y Acc.");
		detalleLabel.addStyleName("infocomTituloLabel");
		
		fidelizacionTableWrapper = new SimplePanel();
		fidelizacionTableWrapper.addStyleName("resultTableWrapper");
		fidelizacionTableWrapper.add(infocomUIData.initTableCreditoFidelizacion());

		fidelizacionTitledPanel.add(valoresPanel);
		fidelizacionTitledPanel.add(detalleLabel);
		fidelizacionTitledPanel.add(fidelizacionTableWrapper);
	}


	public Widget getFidelizacionTitledPanel() {
		return fidelizacionTitledPanel;
	}

}
