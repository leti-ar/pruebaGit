package ar.com.nextel.sfa.client.infocom;

import ar.com.nextel.sfa.client.widget.TitledPanel;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SimplePanel;

public class CCInfocomUI extends Composite {
	private TitledPanel mainPanel;
	private FlexTable cuentaCorriente;
	private FlexTable descrpTable;
	
	public CCInfocomUI(){
		mainPanel = new TitledPanel("Cuenta Corriente");
		mainPanel.addStyleName("contenedor");
		initWidget(mainPanel);
		initCtaCteInfocomUI();
	}

	private void initCtaCteInfocomUI() {
		loadCCTable();
		loadDescrpTable();
		
	}
	
	private void loadCCTable() {
		if (cuentaCorriente != null) {
			cuentaCorriente.unsinkEvents(Event.getEventsSunk(cuentaCorriente.getElement()));
			cuentaCorriente.removeFromParent();
		}
		cuentaCorriente = new FlexTable();
		initCCTable(cuentaCorriente);
		setVisible(true);
	}
	
	private void loadDescrpTable() {
		if (descrpTable != null) {
			descrpTable.unsinkEvents(Event.getEventsSunk(descrpTable.getElement()));
			descrpTable.removeFromParent();
		}
		descrpTable = new FlexTable();
		initdescrpTable(descrpTable);
		setVisible(true);
		
	}

	private void initCCTable(FlexTable table) {
		SimplePanel contTable = new SimplePanel();
		contTable.addStyleName("contTable");
		String[] widths = { "164px", "74px", "99px", "149px", "149px", "149px", "149px" };
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.getColumnFormatter().addStyleName(5, "alignRight");
		table.getColumnFormatter().addStyleName(6, "alignRight");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarCuentaResultTable cuenta");
		table.getRowFormatter().addStyleName(0, "header");
		table.setHTML(0, 0, "Numero Cuenta");
		table.setHTML(0, 1, "Clase");
		table.setHTML(0, 2, "Vencimiento");
		table.setHTML(0, 3, "Descripcion");
		table.setHTML(0, 4, "Numero comprobante");
		table.setHTML(0, 5, "Importe");
		table.setHTML(0, 6, "Saldo");
		table.setHTML(0, 7, "");
		for (int row = 1; row < 5; row++) {
			for (int col = 0; col < widths.length; col++) {
				table.setHTML(row, col, "Dato");
			}
		}
		for (int row = 0; row < table.getRowCount(); row++) {
			if ((row % 2) == 0) {
				table.getRowFormatter().addStyleName(row, "bgrRow");
			}
		}
		
		contTable.add(table);
		mainPanel.add(contTable);
	}
	
	private void initdescrpTable(FlexTable table) {
		SimplePanel contTable = new SimplePanel();
		contTable.addStyleName("contTable");
		String[] widths = { "639px", "149px", "149px" };
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.getColumnFormatter().addStyleName(1, "alingRight");
		table.getColumnFormatter().addStyleName(2, "alingRight");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarCuentaResultTable ccTable");
		table.getRowFormatter().addStyleName(0, "header");
		table.setHTML(0, 0, "Descripcion");
		table.setHTML(1, 0, "EQUIPOS");
		table.setHTML(2, 0, "SERVICIOS");
		table.setHTML(3, 0, "Total");
		table.setHTML(0, 1, "A vencer");
		table.setHTML(0, 2, "Vencida");
		table.setHTML(0, 3, "");
		for (int row = 1; row < 4; row++) {
			for (int col = 1; col < widths.length; col++) {
				table.setHTML(row, col, "Dato");
			}
		}
		contTable.add(table);
		mainPanel.add(contTable);
	}
	
	
//    public void success(Object result) {
//            List result = new ArrayList();
//            final int sizeContenidos = transacciones.size();
//
//            /** Esto se ejecuta despues de renderear la tabla. Puede quedar vacio */
//            RendererCallback callback = new RendererCallback() {
//                    public void onRendered() {
//                            int i = 1;
//                            for (TransaccionCCDto transaccion : transacciones) {
//                                    ExpandCellPopup desc = new ExpandCellPopup(transaccion.getDescripcion(),
//                                                    10, false, false);
//                                    cuentaCorriente.setWidget(i++, 3, desc);
//                            }
//                    }
//            };
//
//            /** Implementando este metodo le decimos como queremos que complete
//la tabla */
//            ClientTableModel tableModel = new ClientTableModelImpl() {
//                    public Object getCell(int rowNum, int cellNum) {
//                            if (rowNum >= (1 + sizeContenidos) | cellNum >= 8) {
//                                    return null;
//                            }
//                            if (rowNum == 0) {
//                                    return headerMovimientos[cellNum];
//                            }
//                            if (cellNum == 3) {
//                                    return "";
//                            }
//                            TransaccionCCDto transaccion = transacciones.get(rowNum - 1);
//                            return transaccion.getRow()[cellNum];
//                    }
//            };
//
//            // Rendereo de la tabla
//            TableBulkRenderer renderer = new NexusTableBulkRender(cuentaCorriente);
//            renderer.renderRows(tableModel, callback);
//
//    }
}

