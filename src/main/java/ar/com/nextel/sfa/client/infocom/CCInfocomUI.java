package ar.com.nextel.sfa.client.infocom;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.widget.TitledPanel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author mrial
 */
public class CCInfocomUI extends Composite {
	
	private InfocomUIData infocomUIData;
	private TitledPanel cuentaCorrienteTitledPanel;
	private SimplePanel cuentaCorrienteTableWrapper;
	private FlexTable cuentaCorrienteTable;
	private SimplePanel descripcionTableWrapper;
	private FlexTable descripcionTable;
	
	
	public CCInfocomUI(InfocomUIData infocomUIData){
		this.infocomUIData = infocomUIData;
		cuentaCorrienteTitledPanel = new TitledPanel("Cuenta Corriente");
		cuentaCorrienteTitledPanel.addStyleName("contenedor");

		initCCTable();
		initDescripcionTable();

		cuentaCorrienteTableWrapper = new SimplePanel();
		cuentaCorrienteTableWrapper.addStyleName("contTable");
		cuentaCorrienteTableWrapper.add(cuentaCorrienteTable);

		descripcionTableWrapper = new SimplePanel();
		descripcionTableWrapper.addStyleName("contTable");
		descripcionTableWrapper.add(descripcionTable);

		cuentaCorrienteTitledPanel.add(cuentaCorrienteTableWrapper);
		cuentaCorrienteTitledPanel.add(descripcionTableWrapper);
	}


	public void initCCTable() {
		cuentaCorrienteTable = new FlexTable();
		String[] widths = { "130px", "80px", "100px", "100px","100px", "90px", "90px" };
		String[] titles = { Sfa.constant().numeroCuenta(), Sfa.constant().clase(), Sfa.constant().venc(), Sfa.constant().descripcion(), 
				Sfa.constant().numeroComprobante(), Sfa.constant().importe(), Sfa.constant().saldo() };
		for (int col = 0; col < widths.length; col++) {
			cuentaCorrienteTable.setHTML(0, col, titles[col]);
			cuentaCorrienteTable.getColumnFormatter().setWidth(col, widths[col]);
			cuentaCorrienteTable.getColumnFormatter().addStyleName(col, "alignCenter");
		}
		cuentaCorrienteTable.setCellPadding(0);
		cuentaCorrienteTable.setCellSpacing(0);
		cuentaCorrienteTable.addStyleName("dataTable");
		cuentaCorrienteTable.getRowFormatter().addStyleName(0, "header");
	}

	public void initDescripcionTable() {
		descripcionTable = new FlexTable();
		String[] widths = { "639px", "149px", "149px" };
		String[] titles = { "Descripcion", "A vencer", "Vencida" };
		for (int col = 0; col < widths.length; col++) {
			descripcionTable.setHTML(0, col, titles[col]);
			descripcionTable.getColumnFormatter().setWidth(col, widths[col]);
			descripcionTable.getColumnFormatter().addStyleName(col, "alignCenter");
		}
		descripcionTable.setHTML(1, 0, "EQUIPOS");
		descripcionTable.setHTML(2, 0, "SERVICIOS");
		descripcionTable.setHTML(3, 0, "Total");
		descripcionTable.setCellPadding(0);
		descripcionTable.setCellSpacing(0);
		//descripcionTable.addStyleName("infocomDescripcionTable");
		descripcionTable.addStyleName("dataTable");
		descripcionTable.getRowFormatter().addStyleName(0, "header");
	}

	public Widget getCCTitledPanel() {
		return cuentaCorrienteTitledPanel;
	}

	
//	public Widget getCCTables() {
//		return cuentaCorrienteWrapper;
//	}
	
//	private void loadDescrpTable() {
////		if (descrpTable != null) {
////			descrpTable.unsinkEvents(Event.getEventsSunk(descrpTable.getElement()));
////			descrpTable.removeFromParent();
////		}
//		//descrpTable = new FlexTable();
//		initDescrpTable(cuentaCorrienteWrapper);
//		setVisible(true);		
//	}

	
	
	
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

