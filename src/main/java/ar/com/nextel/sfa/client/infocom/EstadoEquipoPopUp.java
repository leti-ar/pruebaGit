package ar.com.nextel.sfa.client.infocom;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.dto.DatosEquipoPorEstadoDto;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.nextel.sfa.client.widget.NextelTable;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class EstadoEquipoPopUp extends NextelDialog {
	private NextelTable table;
	private List estadosList = new ArrayList<DatosEquipoPorEstadoDto>();

	public EstadoEquipoPopUp(String title, String width, List<DatosEquipoPorEstadoDto> estadosList) {
		super(title, false, true);
		this.setWidth(width);
		this.addStyleName("estadoDialog");
		this.estadosList = estadosList;
		table = new NextelTable();
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
		contTable.addStyleName("resultTableWrapper");
		//contTable.addStyleName("contPanel");
		String[] widths = { "164px", "164px", "149px", "149px", "149px", "149px", "149px", "149px", "149px" };
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarCuentaResultTable");
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
		contTable.add(table);
		this.add(contTable);
	}
	
	
//	private void initTable(FlexTable table) {
//		table = new FlexTable();
//		SimplePanel contTable = new SimplePanel();
//		contTable.addStyleName("contPanel");
//		String[] widths = { "164px", "164px", "149px", "149px", "149px", "149px", "149px", "149px", "149px", };
//		String[] titles = { "Numero cuenta", "Plan", "Modelo", "Tipo telefonia", "Forma contratacion", "Fecha", "Numero contrato", "Motivo", "Numero solicitud"};
//		for (int col = 0; col < widths.length; col++) {
//			table.setHTML(0, col, titles[col]);
//			table.getColumnFormatter().setWidth(col, widths[col]);
//		}
//		//table.getColumnFormatter().addStyleName(0, "alignCenter");
//		//table.getColumnFormatter().addStyleName(1, "alignCenter");
//		//table.getColumnFormatter().addStyleName(2, "alignCenter");
//		table.setCellPadding(0);
//		table.setCellSpacing(0);
//		table.addStyleName("gwt-BuscarCuentaResultTable estadoTable");
//		table.getRowFormatter().addStyleName(0, "header");
//		contTable.add(table);
//		this.add(contTable);
//	}
	
	public void setEstado(List<DatosEquipoPorEstadoDto> listDatosEquiposPorEstado) {
		refreshEstadoTable(listDatosEquiposPorEstado); 
	}
	
	private void refreshEstadoTable (List<DatosEquipoPorEstadoDto> listDatosEquiposPorEstado) {
		while (table.getRowCount() > 1) {
			table.removeRow(1);
		}
		int row = 1;
		for (DatosEquipoPorEstadoDto datoEquipoPorEstadoDto : listDatosEquiposPorEstado) {
			table.setHTML(row, 0, datoEquipoPorEstadoDto.getNumeroCuenta());
			table.setHTML(row, 1, datoEquipoPorEstadoDto.getPlan());
			table.setHTML(row, 2, datoEquipoPorEstadoDto.getModelo());
			table.setHTML(row, 3, datoEquipoPorEstadoDto.getTipoTelefonia());
			table.setHTML(row, 4, datoEquipoPorEstadoDto.getFormaContratacion());
			table.setHTML(row, 5, datoEquipoPorEstadoDto.getFecha());
			table.setHTML(row, 6, datoEquipoPorEstadoDto.getNumeroContrato().toString());
			table.setHTML(row, 7, datoEquipoPorEstadoDto.getMotivo());
			table.setHTML(row, 8, datoEquipoPorEstadoDto.getNumeroSolicitudServicio());
			row++;
		}
	}
}
