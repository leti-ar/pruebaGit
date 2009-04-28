package ar.com.nextel.sfa.client.infocom;

import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.widget.SimpleLabel;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ResumenEquipoPopUp extends NextelDialog {

	private Grid encabezadoTable;
	private FlexTable resumenTable;

	public ResumenEquipoPopUp(String title) {
		super(title);
		this.addStyleName("equipoDialog");
		initResumenEquipoPopUp();
	}

	private void initResumenEquipoPopUp() {
		encabezadoTable = new Grid(3, 4);
		encabezadoTable.setWidget(0, 0, new SimpleLabel("Razon Social:"));
		encabezadoTable.setWidget(1, 0, new SimpleLabel("N� de Cliente:"));
		encabezadoTable.setWidget(1, 2, new SimpleLabel("Factura N�:"));
		encabezadoTable.setWidget(2, 0, new SimpleLabel("Flota:"));
		encabezadoTable.setWidget(2, 2, new SimpleLabel("Emision:"));
		this.add(encabezadoTable);
		resumenTable = new FlexTable();
		initTable(resumenTable);

		SimpleLink cerrar = new SimpleLink("Cerrar");
		cerrar.addStyleName("cerrarLink");
		cerrar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				ResumenEquipoPopUp.this.hide();
			}
		});

		this.addFooter(cerrar);

	}

	private void initTable(FlexTable table) {
		SimplePanel contPanel = new SimplePanel();
		contPanel.addStyleName("contPanel");
		String[] widths = { "74px", "99px", "99px", "99px", "99px", "99px",
				"99px", "99px", "99px", "99px", "99px", "99px", "99px", "99px" };
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.getColumnFormatter().addStyleName(0, "alignCenter");
		table.getColumnFormatter().addStyleName(1, "alignCenter");
		table.getColumnFormatter().addStyleName(2, "alignCenter");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarCuentaResultTable equipoTable");
		table.getRowFormatter().addStyleName(0, "header");
		table.setHTML(0, 0, "Nro.Id");
		table.setHTML(0, 1, "Nro.Tel");
		table.setHTML(0, 2, "Abono");
		table.setHTML(0, 3, "Alq");
		table.setHTML(0, 4, "Gtia");
		table.setHTML(0, 5, "Serv");
		table.setHTML(0, 6, "Prop y Reint");
		table.setHTML(0, 7, "Exc. Radio");
		table.setHTML(0, 8, "Exc. Tel");
		table.setHTML(0, 9, "Red Fija");
		table.setHTML(0, 10, "DDN");
		table.setHTML(0, 11, "DDI y Roam");
		table.setHTML(0, 12, "Pagers");
		table.setHTML(0, 13, "Tot c/imp");
		for (int row = 1; row < 5; row++) {
			for (int col = 0; col < widths.length; col++) {
				table.setHTML(row, col, "Dato");
			}
		}
		contPanel.add(table);
		this.add(contPanel);
	}

}
