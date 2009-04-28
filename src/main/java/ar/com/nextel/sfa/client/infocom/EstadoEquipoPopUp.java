package ar.com.nextel.sfa.client.infocom;

import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class EstadoEquipoPopUp extends NextelDialog {
	private FlexTable table;

	public EstadoEquipoPopUp(String title) {
		super(title);
		this.addStyleName("estadoDialog");
		table = new FlexTable();
		initTable(table);
		SimpleLink cerrar = new SimpleLink("Cerrar");
		cerrar.addStyleName("cerrarLink");
		this.addFooter(cerrar);
		cerrar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				EstadoEquipoPopUp.this.hide();
			}
		});
	}

	private void initTable(FlexTable table) {
		SimplePanel contTable = new SimplePanel();
		contTable.addStyleName("contPanel");
		String[] widths = { "164px", "164px", "149px", "149px", "149px",
				"149px", "149px", "149px", "149px" };
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarCuentaResultTable estadoTable");
		table.getRowFormatter().addStyleName(0, "header");
		table.setHTML(0, 0, "Numero cuenta");
		table.setHTML(0, 1, "Plan");
		table.setHTML(0, 2, "Modelo");
		table.setHTML(0, 3, "Tipo telefonia");
		table.setHTML(0, 4, "Forma contratacion");
		table.setHTML(0, 5, "Fecha");
		table.setHTML(0, 6, "Numero contrato");
		table.setHTML(0, 7, "Motivo");
		table.setHTML(0, 8, "Numero solicitud");
		for (int col = 0; col < widths.length; col++) {
			for (int row = 1; row < 3; row++) {
				table.setHTML(row, col, "Dato");
			}
		}
		contTable.add(table);
		this.add(contTable);
	}

}
