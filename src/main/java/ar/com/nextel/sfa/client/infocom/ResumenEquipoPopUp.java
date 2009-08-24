package ar.com.nextel.sfa.client.infocom;

import java.util.List;

import ar.com.nextel.sfa.client.dto.DatosEquipoPorEstadoDto;
import ar.com.nextel.sfa.client.dto.EquipoDto;
import ar.com.nextel.sfa.client.dto.ResumenEquipoDto;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.widget.SimpleLabel;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ResumenEquipoPopUp extends NextelDialog {

	private FlexTable encabezadoTable;
	private FlexTable resumenTable;
	private InlineHTML razonSocial;
	private InlineHTML numeroCliente;
	private InlineHTML flota;
	private InlineHTML factura;
	private InlineHTML emision;
	private String responsablePago;

	
	private final NumberFormat numberFormat = NumberFormat.getCurrencyFormat();

	public ResumenEquipoPopUp(String title, String width, String responsablePago) {
		super(title);
		this.setWidth(width);
		this.addStyleName("equipoDialog");
		this.responsablePago = responsablePago;
		initResumenEquipoPopUp();
	}

	private void initResumenEquipoPopUp() {
		razonSocial = new InlineHTML();
		numeroCliente = new InlineHTML();
		flota = new InlineHTML();
		factura = new InlineHTML();
		emision = new InlineHTML();
		encabezadoTable = new FlexTable();
		encabezadoTable.setWidget(0, 0, new Label("Razon Social:"));
		encabezadoTable.setWidget(0, 1, razonSocial);
		encabezadoTable.setWidget(1, 0, new Label("N° de Cliente:"));
		encabezadoTable.setWidget(1, 1, numeroCliente);
		encabezadoTable.setWidget(1, 2, new Label("Factura N°:"));
		encabezadoTable.setWidget(1, 3, factura);
		encabezadoTable.setWidget(2, 0, new Label("Flota:"));
		encabezadoTable.setWidget(2, 1, flota);
		encabezadoTable.setWidget(2, 2, new Label("Emision:"));
		encabezadoTable.setWidget(2, 3, emision);
		this.add(encabezadoTable);
		resumenTable = new FlexTable();
		resumenTable.addStyleName("miTablaInvisible");
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
		contPanel.addStyleName("resultTableWrapper");
		String[] widths = { "74px", "99px", "99px", "99px", "99px", "99px",
				"99px", "99px", "99px", "99px", "99px", "99px", "99px", "99px" };
		for (int col = 1; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
			table.getColumnFormatter().addStyleName(col, "alignCenter");
		}
		
		table.setCellPadding(0);
		table.setCellSpacing(0);		
		table.addStyleName("gwt-BuscarCuentaResultTable");		
		table.getRowFormatter().addStyleName(0, "header");
		table.setHTML(0, 0, "Cliente");
		table.setHTML(0, 1, "Nro.Id");
		table.setHTML(0, 2, "Nro.Tel");
		table.setHTML(0, 3, "Abono");
		table.setHTML(0, 4, "Alq");
		table.setHTML(0, 5, "Gtía");
		table.setHTML(0, 6, "Serv");
		table.setHTML(0, 7, "Prop y Reint");
		table.setHTML(0, 8, "Exc. Radio");
		table.setHTML(0, 9, "Exc. Tel");
		table.setHTML(0, 10, "Red Fija");
		table.setHTML(0, 11, "DDN");
		table.setHTML(0, 12, "DDI y Roam");
		table.setHTML(0, 13, "Pagers");
		table.setHTML(0, 14, "Tot c/imp");
		if (responsablePago!=null) {
			table.getColumnFormatter().addStyleName(0, "cg1");
			table.setHTML(0, 0, "");
			//table.getColumnFormatter().addStyleName(0, "alignCenter");
		} else {
			table.getColumnFormatter().setWidth(0, "99px");
		}
		contPanel.add(table);
		this.add(contPanel);
	}
	
	public void setResumenPorEquipo(ResumenEquipoDto resumenEquipoDto) {
		setEncabezado(resumenEquipoDto);
		refreshResumenTable(resumenEquipoDto.getEquipos());
	}
	
	private void setEncabezado(ResumenEquipoDto resumenEquipoDto) {
		if (responsablePago!=null) {
			razonSocial.setText(resumenEquipoDto.getRazonSocial());
			numeroCliente.setText(resumenEquipoDto.getNumeroCliente());
			flota.setText(resumenEquipoDto.getFlota());
			factura.setText(resumenEquipoDto.getFacturaNumero());
			emision.setText(resumenEquipoDto.getEmision());
		} else {
			razonSocial.setText(resumenEquipoDto.getRazonSocial());
			flota.setText(resumenEquipoDto.getFlota());
		}
	}
	
	private void refreshResumenTable(List<EquipoDto> listaEquipos) {
		while(resumenTable.getRowCount()>1) {
			resumenTable.removeRow(1);
		}
		int row = 1;
		for (EquipoDto equipo : listaEquipos) {
			resumenTable.setHTML(row, 0, equipo.getCliente());
			resumenTable.setHTML(row, 1, equipo.getNumeroID());
			resumenTable.setHTML(row, 2, equipo.getTelefono());
			resumenTable.setHTML(row, 3, numberFormat.format(equipo.getAbono()));
			resumenTable.setHTML(row, 4, numberFormat.format(equipo.getAlquiler()));
			resumenTable.setHTML(row, 5, numberFormat.format(equipo.getGarantia()));
			resumenTable.setHTML(row, 6, numberFormat.format(equipo.getServicios()));
			resumenTable.setHTML(row, 7, numberFormat.format(equipo.getProporcionalYReintegros()));
			resumenTable.setHTML(row, 8, numberFormat.format(equipo.getExcedenteRadio()));
			resumenTable.setHTML(row, 9, numberFormat.format(equipo.getExcedenteTelefonia()));
			resumenTable.setHTML(row, 10, numberFormat.format(equipo.getRedFija()));
			resumenTable.setHTML(row, 11, numberFormat.format(equipo.getDdn()));
			resumenTable.setHTML(row, 12, numberFormat.format(equipo.getDdiYRoaming()));
			resumenTable.setHTML(row, 13, numberFormat.format(equipo.getPagers()));
			resumenTable.setHTML(row, 14, numberFormat.format(equipo.getTotalConImpuestos()));
			row++;			
		}
		
	}

}
