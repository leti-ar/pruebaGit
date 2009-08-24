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
	private SimplePanel descripcionTableWrapper;
	
	
	public CCInfocomUI(InfocomUIData infocomUIData){
		this.infocomUIData = infocomUIData;
		cuentaCorrienteTitledPanel = new TitledPanel("Cuenta Corriente");
		cuentaCorrienteTitledPanel.addStyleName("contenedor");	

		cuentaCorrienteTableWrapper = new SimplePanel();
		//cuentaCorrienteTableWrapper.addStyleName("contTable");
		cuentaCorrienteTableWrapper.addStyleName("resultTableWrapper");
		cuentaCorrienteTableWrapper.add(infocomUIData.initCCTable());

		descripcionTableWrapper = new SimplePanel();
		descripcionTableWrapper.setHeight("100px");
		//descripcionTableWrapper.addStyleName("contTable");
		descripcionTableWrapper.addStyleName("resultTableWrapper");
		descripcionTableWrapper.add(infocomUIData.initDescripcionTable());

		cuentaCorrienteTitledPanel.add(cuentaCorrienteTableWrapper);
		cuentaCorrienteTitledPanel.add(descripcionTableWrapper);
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

