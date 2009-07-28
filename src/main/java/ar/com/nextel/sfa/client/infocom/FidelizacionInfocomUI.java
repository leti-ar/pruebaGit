package ar.com.nextel.sfa.client.infocom;

import ar.com.nextel.sfa.client.widget.TitledPanel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author mrial
 */
public class FidelizacionInfocomUI extends Composite {

	private InfocomUIData infocomUIData;
	private TitledPanel fidelizacionTitledPanel;
	private FlexTable montoFlexTable;
	private Label detalleLabel;
	private Label montoInfo;
	private Label estadoInfo;
	private Label vencimientoInfo;
	private SimplePanel fidelizacionTableWrapper;
	private FlexTable creditoFidelizacionTable;


	public FidelizacionInfocomUI(InfocomUIData infocomUIData) {
		this.infocomUIData = infocomUIData;
		fidelizacionTitledPanel = new TitledPanel("Crédito de Fidelización");
		fidelizacionTitledPanel.addStyleName("contenedor");

		montoFlexTable = new FlexTable();
		montoFlexTable.setWidget(0, 0, infocomUIData.getMontoLabel());
		montoFlexTable.setWidget(0, 1, infocomUIData.getMonto());
		montoFlexTable.setWidget(0, 2, infocomUIData.getEstadoLabel());
		montoFlexTable.setWidget(0, 3, infocomUIData.getEstado());
		montoFlexTable.setWidget(0, 4, infocomUIData.getVencimientoLabel());
		montoFlexTable.setWidget(0, 5, infocomUIData.getVencimiento());

		detalleLabel = new Label("Detalle Bonif. Otorgadas - Facturación de Eq. y Acc.");
		detalleLabel.addStyleName("infocomTituloLabel");
		initTableCreditoFidelizacion();

		fidelizacionTableWrapper = new SimplePanel();
		fidelizacionTableWrapper.addStyleName("contTable");
		fidelizacionTableWrapper.add(creditoFidelizacionTable);

		fidelizacionTitledPanel.add(montoFlexTable);
		fidelizacionTitledPanel.add(detalleLabel);
		fidelizacionTitledPanel.add(fidelizacionTableWrapper);
	}


	private void initTableCreditoFidelizacion() {
		creditoFidelizacionTable = new FlexTable();
		String[] widths = { "149px", "489px", "149px", "149px", };
		String[] titles = { "Fecha", "Descripcion", "Monto", "Factura aplicada" };
		for (int col = 0; col < widths.length; col++) {
			creditoFidelizacionTable.setHTML(0, col, titles[col]);
			creditoFidelizacionTable.getColumnFormatter().setWidth(col, widths[col]);
		}
		creditoFidelizacionTable.getColumnFormatter().addStyleName(0, "alignCenter");
		creditoFidelizacionTable.getColumnFormatter().addStyleName(1, "alignCenter");
		creditoFidelizacionTable.getColumnFormatter().addStyleName(2, "alignCenter");
		creditoFidelizacionTable.setCellPadding(0);
		creditoFidelizacionTable.setCellSpacing(0);
		creditoFidelizacionTable.addStyleName("gwt-BuscarCuentaResultTable");
		creditoFidelizacionTable.getRowFormatter().addStyleName(0, "header");
	}


	public Widget getFidelizacionTitledPanel() {
		return fidelizacionTitledPanel;
	}

}
