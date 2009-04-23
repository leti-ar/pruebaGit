package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.dto.CambiosSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Muestra la tabla inferior del buscador con los resultados de los cambios de
 * la SS cerrada seleccionada
 * 
 * @author juliovesco
 * 
 */
public class CambiosSSCerradasResultUI extends FlowPanel {

	private FlexTable resultTable;
	private FlexTable cambiosTable;
	private FlowPanel resultTableWrapper;
	private SolicitudServicioCerradaDto solicitudServicioCerradaDto;
	private Label labelDatosSS = new Label ("Datos de SS");
	private Label labelNroSS = new Label();
	private Label labelNroCLiente = new Label();
	private Label labelRazonSocial = new Label();

	public CambiosSSCerradasResultUI() {
		super();
		addStyleName("gwt-BuscarCuentaResultPanel");
		this.add(labelNroSS);
		this.add(labelNroCLiente);
		this.add(labelRazonSocial);
		resultTableWrapper = new FlowPanel();
		resultTableWrapper.addStyleName("resultTableWrapper");
		add(resultTableWrapper);
		setVisible(false);
	}

	public void setSolicitudServicioCerradaDto(
			SolicitudServicioCerradaDto solicitudServicioCerradaDto) {
		this.solicitudServicioCerradaDto = solicitudServicioCerradaDto;
		cambiosTable = new FlexTable();
		cambiosTable.setWidget(0, 0, labelDatosSS);
		labelDatosSS.addStyleName("mt5");
		cambiosTable.setWidget(1, 0, labelNroSS);
		labelNroSS.addStyleName("mr50");
		labelNroSS.addStyleName("mtb5");
		cambiosTable.setWidget(1, 1, labelNroCLiente);
		labelNroCLiente.addStyleName("mlr40");
		labelNroCLiente.addStyleName("mtb5");
		cambiosTable.setWidget(1, 2, labelRazonSocial);		
		labelRazonSocial.addStyleName("mlr40");
		labelRazonSocial.addStyleName("mtb5");
		labelNroSS.setText("N° SS: "
				+ solicitudServicioCerradaDto.getNumeroSS());
		labelNroCLiente.setText("N° Cliente: "
				+ solicitudServicioCerradaDto.getNumeroCuenta());
		labelRazonSocial.setText("Razon Social: "
				+ solicitudServicioCerradaDto.getRazonSocial());
		resultTableWrapper.add(cambiosTable);
		loadTable();
	}

	private void loadTable() {
		if (resultTable != null) {
			resultTable.unsinkEvents(Event.getEventsSunk(resultTable
					.getElement()));
			resultTable.removeFromParent();
		}
		resultTable = new FlexTable();
		initTable(resultTable);
		resultTableWrapper.add(resultTable);
		int row = 1;
		for (CambiosSolicitudServicioDto cambiosDto : solicitudServicioCerradaDto
				.getCambios()) {
			resultTable.setHTML(row, 0, cambiosDto.getFecha().toString());
			resultTable.setHTML(row, 1, cambiosDto.getEstado());
			resultTable.setHTML(row, 2, cambiosDto.getComentario());
			row++;
		}
		setVisible(true);
	}

	private void initTable(FlexTable table) {
		String[] widths = { "300px", "175px", "450px" };
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.getColumnFormatter().addStyleName(0, "alignCenter");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarCuentaResultTable");
		table.getRowFormatter().addStyleName(0, "header");
		table.setHTML(0, 0, "Fecha");
		table.setHTML(0, 1, "Estado");
		table.setHTML(0, 2, "Comentario");
	}

}
