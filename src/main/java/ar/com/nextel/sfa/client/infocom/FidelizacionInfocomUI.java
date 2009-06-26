package ar.com.nextel.sfa.client.infocom;

import ar.com.nextel.sfa.client.widget.TitledPanel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SimplePanel;

public class FidelizacionInfocomUI extends Composite {
	
	private TitledPanel mainPanel;
	private FlexTable resultTable;
	
	public FidelizacionInfocomUI(){
		mainPanel = new TitledPanel("Credito de Fidelizacion");
		mainPanel.addStyleName("contenedor");
		initWidget(mainPanel);
		initTableCreditoFidelizacion(resultTable);
	}

	private void initTableCreditoFidelizacion(FlexTable table) {
		table = new FlexTable();
		SimplePanel contTable = new SimplePanel();
		contTable.addStyleName("contTable");
		String[] widths = { "149px", "489px", "149px", "149px" };
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.getColumnFormatter().addStyleName(0, "alignCenter");
		table.getColumnFormatter().addStyleName(1, "alignCenter");
		table.getColumnFormatter().addStyleName(2, "alignCenter");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarCuentaResultTable fidelizacionTable");
		table.getRowFormatter().addStyleName(0, "header");
		table.setHTML(0, 0, "Fecha");
		table.setHTML(0, 1, "Descripcion");
		table.setHTML(0, 2, "Monto");
		table.setHTML(0, 3, "Factura aplicada");
		table.setHTML(0, 4, "");
		for (int row = 1; row < 5; row++) {
			for (int col = 0; col < widths.length; col++) {
				table.setHTML(row, col, "Dato");
			}
		}

		
//		SimplePanel subTitulo = new SimplePanel();
//		subTitulo.addStyleName("subTitulo");
//		subTitulo.add(new Label("Detalle Bonif. Otorgadas - Facturaci�n de Eq. y Acc."));
		
		contTable.add(table);
		mainPanel.add(contTable);
		
		
//		table = new FlexTable();
//		String[] widths = { "50px", "75px", "50px", "50px" };
//		for (int col = 0; col < widths.length; col++) {
//			table.getColumnFormatter().setWidth(col, widths[col]);
//		}
//		table.getColumnFormatter().addStyleName(0, "alignCenter");
//		table.getColumnFormatter().addStyleName(1, "alignCenter");
//		table.getColumnFormatter().addStyleName(2, "alignCenter");
//		table.getColumnFormatter().addStyleName(3, "alignCenter");
//		table.setCellPadding(0);
//		table.setCellSpacing(0);
//		table.addStyleName("gwt-BuscarCuentaResultTable");
//		table.getRowFormatter().addStyleName(0, "header");
//		//table.addStyleName("titulo");
//		table.setWidget(0, 0, new Label("Fecha:"));
//		table.setWidget(0, 1, new Label("Descripción:"));
//		table.setWidget(0, 2, new Label("Monto:"));
//		table.setWidget(0, 3, new Label("Factura aplicada:"));
//		mainPanel.add(table);
//		loadTable();
	}
	
	
//	private void loadTable() {
//		if (resultTable != null) {
//			resultTable.unsinkEvents(Event.getEventsSunk(resultTable.getElement()));
//			resultTable.removeFromParent();
//		}
//		//resultTable = new FlexTable();
//		//initCCTable(resultTable);
//		setVisible(true);
//	}
//
//	private void initCCTable(FlexTable table) {
//		SimplePanel contTable = new SimplePanel();
//		contTable.addStyleName("contTable");
//		String[] widths = { "149px", "489px", "149px", "149px" };
//		for (int col = 0; col < widths.length; col++) {
//			table.getColumnFormatter().setWidth(col, widths[col]);
//		}
//		table.getColumnFormatter().addStyleName(0, "alignCenter");
//		table.getColumnFormatter().addStyleName(1, "alignCenter");
//		table.getColumnFormatter().addStyleName(2, "alignCenter");
//		table.setCellPadding(0);
//		table.setCellSpacing(0);
//		table.addStyleName("gwt-BuscarCuentaResultTable fidelizacionTable");
//		table.getRowFormatter().addStyleName(0, "header");
//		table.setHTML(0, 0, "Fecha");
//		table.setHTML(0, 1, "Descripcion");
//		table.setHTML(0, 2, "Monto");
//		table.setHTML(0, 3, "Factura aplicada");
//		table.setHTML(0, 4, "");
//		for (int row = 1; row < 5; row++) {
//			for (int col = 0; col < widths.length; col++) {
//				table.setHTML(row, col, "Dato");
//			}
//		}
//		contTable.add(table);
//		mainPanel.add(contTable);
//	}
	
}
