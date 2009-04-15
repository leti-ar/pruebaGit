package ar.com.nextel.sfa.client.ss;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * Muestra el panel correspondiente a los totales de la busqueda de SS cerradas.
 * 
 * @author juliovesco
 * 
 */
public class BuscarSSTotalesResultUI extends FlowPanel {

	private Label labelEquipos = new Label("Equipos: ");
	private Label labelPataconex = new Label("Pataconex: ");
	private Label labelFirmados = new Label("Eq. Firmados: ");

	public BuscarSSTotalesResultUI() {
		add(labelEquipos);
		add(labelPataconex);
		add(labelFirmados);
	}

	public void setValues(String equipos, String pataconex,
			String equiposFirmados) {
		labelEquipos.setText("Equipos: " + equipos);
		labelPataconex.setText("Pataconex: " + pataconex);
		labelFirmados.setText("Eq. Firmados: " + equiposFirmados);
	}

}
