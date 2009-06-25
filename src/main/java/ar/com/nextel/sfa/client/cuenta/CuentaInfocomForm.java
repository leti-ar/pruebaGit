package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.widget.TitledPanel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author eSalvador 
 **/
public class CuentaInfocomForm  extends Composite {

	private FlexTable mainPanel           = new FlexTable();
	private TitledPanel creditoFidelizacionPanel = new TitledPanel(Sfa.constant().creditoFidelizacionTitle());
	private TitledPanel cuentaCorrientePanel = new TitledPanel(Sfa.constant().cuentaCorriente());
	private FlexTable datosTablaCuentaCorriente;
	private FlexTable datosTablaCCEquiposServicios;
	private FlexTable datosTablaCreditoFidelizacion;
	private static final String ANCHO_PRIMER_COLUMNA = "11%";
	private static final String ANCHO_TABLA_PANEL    = "80%";
	
	private static CuentaInfocomForm instance = new CuentaInfocomForm();
	
	public static CuentaInfocomForm getInstance() {
		return instance;
	}
	
	private CuentaInfocomForm() {
		datosTablaCuentaCorriente = new FlexTable();
		datosTablaCreditoFidelizacion  = new FlexTable();
		datosTablaCCEquiposServicios  = new FlexTable();
		initWidget(mainPanel);
		mainPanel.setWidth("100%");
		mainPanel.setWidget(0,0,createDatosCreditoFidelizacion());
		mainPanel.setWidget(1,0,createDatosCuetaCorriente());
	}
	
	private Widget createDatosCreditoFidelizacion() {
		datosTablaCreditoFidelizacion = new FlexTable();
		datosTablaCreditoFidelizacion.setWidth(ANCHO_TABLA_PANEL);
		datosTablaCreditoFidelizacion.addStyleName("layout");
		
		creditoFidelizacionPanel.add(datosTablaCreditoFidelizacion);
		
		datosTablaCreditoFidelizacion.getFlexCellFormatter().setWidth(0, 0, ANCHO_PRIMER_COLUMNA);
		datosTablaCreditoFidelizacion.getFlexCellFormatter().setWidth(1, 0, ANCHO_PRIMER_COLUMNA);
		initTableCreditoFidelizacion(datosTablaCreditoFidelizacion);
		return creditoFidelizacionPanel;
	}
	
	private Widget createDatosCuetaCorriente() {
		datosTablaCuentaCorriente = new FlexTable();
		datosTablaCCEquiposServicios  = new FlexTable();
		datosTablaCuentaCorriente.setWidth(ANCHO_TABLA_PANEL);
		datosTablaCuentaCorriente.addStyleName("layout");
		datosTablaCCEquiposServicios.setWidth(ANCHO_TABLA_PANEL);
		datosTablaCCEquiposServicios.addStyleName("layout");
		
		cuentaCorrientePanel.add(datosTablaCuentaCorriente);
		cuentaCorrientePanel.add(datosTablaCCEquiposServicios);
		
		datosTablaCuentaCorriente.getFlexCellFormatter().setWidth(0, 0, ANCHO_PRIMER_COLUMNA);
		datosTablaCuentaCorriente.getFlexCellFormatter().setWidth(1, 0, ANCHO_PRIMER_COLUMNA);
		datosTablaCCEquiposServicios.getFlexCellFormatter().setWidth(0, 0, ANCHO_PRIMER_COLUMNA);
		datosTablaCCEquiposServicios.getFlexCellFormatter().setWidth(1, 0, ANCHO_PRIMER_COLUMNA);
		
		initTableCuentaCorriente(datosTablaCuentaCorriente);
		initTableCCEquiposServicios(datosTablaCCEquiposServicios);
		
		return cuentaCorrientePanel;
	}
	
	private void initTableCreditoFidelizacion(FlexTable table) {

		String[] widths = { "24px", "24px", "24px", "100px", "100px", "75%", "50px" };
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.getColumnFormatter().addStyleName(0, "alignCenter");
		table.getColumnFormatter().addStyleName(1, "alignCenter");
		table.getColumnFormatter().addStyleName(2, "alignCenter");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarCuentaResultTable");
		table.getRowFormatter().addStyleName(0, "header");
		table.setHTML(0, 0, Sfa.constant().whiteSpace());
		table.setHTML(0, 1, Sfa.constant().whiteSpace());
		table.setHTML(0, 2, Sfa.constant().whiteSpace());
		table.setHTML(0, 3, Sfa.constant().entrega());
		table.setHTML(0, 4, Sfa.constant().facturacion());
		table.setHTML(0, 5, Sfa.constant().domicilios());
		table.setHTML(0, 6, Sfa.constant().whiteSpace());
	}

	private void initTableCuentaCorriente(FlexTable table) {

		String[] widths = { "100px", "35px", "50px", "80px", "80px", "80px", "75px" };
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.addStyleName("gwt-BuscarCuentaResultTable");
		table.getRowFormatter().addStyleName(0, "header");
		//table.setHTML(0, 0, Sfa.constant().numeroCuenta());
		table.setHTML(0, 0, "Número Cuenta");
		table.setHTML(0, 1, Sfa.constant().claseCliente());
		table.setHTML(0, 2, Sfa.constant().vencimiento());
		//table.setHTML(0, 3, Sfa.constant().descripcion());
		table.setHTML(0, 3, "Descripcion");
		table.setHTML(0, 4, "Número Comprobante");
//		table.setHTML(0, 5, Sfa.constant().importe());
		table.setHTML(0, 5, "Importe");
//		table.setHTML(0, 6, Sfa.constant().saldo());
		table.setHTML(0, 6, "Saldo");
	}
	private void initTableCCEquiposServicios(FlexTable table) {

		String[] widths = { "24px", "24px", "24px", "100px", "100px", "75%", "50px" };
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.getColumnFormatter().addStyleName(0, "alignCenter");
		table.getColumnFormatter().addStyleName(1, "alignCenter");
		table.getColumnFormatter().addStyleName(2, "alignCenter");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarCuentaResultTable");
		table.getRowFormatter().addStyleName(0, "header");
		table.setHTML(0, 0, Sfa.constant().whiteSpace());
		table.setHTML(0, 1, Sfa.constant().whiteSpace());
		table.setHTML(0, 2, Sfa.constant().whiteSpace());
		table.setHTML(0, 3, Sfa.constant().entrega());
		table.setHTML(0, 4, Sfa.constant().facturacion());
		table.setHTML(0, 5, Sfa.constant().domicilios());
		table.setHTML(0, 6, Sfa.constant().whiteSpace());
	}
}
