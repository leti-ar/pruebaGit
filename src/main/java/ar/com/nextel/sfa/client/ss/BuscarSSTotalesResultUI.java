package ar.com.nextel.sfa.client.ss;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * Muestra el panel correspondiente a los totales de la busqueda de SS cerradas.
 * 
 * @author juliovesco
 * 
 */
public class BuscarSSTotalesResultUI extends FlowPanel {

	private Label labelTotales = new Label("Totales");
	private Label labelEquipos = new Label();
	private Label labelPataconex = new Label();
	private Label labelFirmados = new Label();
	private FlexTable resultadosTable = new FlexTable();
	
	private final NumberFormat numberFormat = NumberFormat.getCurrencyFormat();

	public BuscarSSTotalesResultUI() {
		resultadosTable.setWidget(0, 0, labelTotales);
		labelTotales.addStyleName("mt5");
		labelTotales.addStyleName("oppEnCursoTitulosLabel");
		resultadosTable.setWidget(1, 0, labelEquipos);
		labelEquipos.addStyleName("mr50");
		labelEquipos.addStyleName("mt5");
		labelEquipos.addStyleName("mb3");
		resultadosTable.setWidget(1, 1, labelPataconex);
		labelPataconex.addStyleName("mlr40");
		labelPataconex.addStyleName("mt5");
		labelPataconex.addStyleName("mb3");
		resultadosTable.setWidget(1, 2, labelFirmados);
		labelFirmados.addStyleName("mlr40");
		labelFirmados.addStyleName("mt5");
		labelFirmados.addStyleName("mb3");
		add(resultadosTable);
	}

	public void setValues(String equipos, double pataconex,
			String equiposFirmados) {//LF#3, boolean analistaCredito) {
		labelEquipos.setText("Equipos: " + equipos);
		//LF#3if(!analistaCredito) {
			labelPataconex.setText("Pataconex: " + numberFormat.format(pataconex));
			labelFirmados.setText("Eq. Firmados: " + equiposFirmados);
	//LF#3	}
	}

}
