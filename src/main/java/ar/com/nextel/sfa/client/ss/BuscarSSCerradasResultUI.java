package ar.com.nextel.sfa.client.ss;

import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;
import ar.com.nextel.sfa.client.dto.SolicitudesServicioTotalesDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;

/**
 * Muestra la tabla correspondiente a las SS cerradas, resultado del buscador.
 * 
 * @author juliovesco
 * 
 */
public class BuscarSSCerradasResultUI extends FlowPanel {

	private FlexTable resultTable;
	private SimplePanel resultTableWrapper;
	private List<SolicitudServicioCerradaDto> solicitudesServicioDto;
	private Long totalRegistrosBusqueda;
	private BuscarSSTotalesResultUI buscarSSTotalesResultUI;
	private CambiosSSCerradasResultUI cambiosSSCerradasResultUI;

	public Long getTotalRegistrosBusqueda() {
		return totalRegistrosBusqueda;
	}

	public void setTotalRegistrosBusqueda(Long totalRegistrosBusqueda) {
		this.totalRegistrosBusqueda = totalRegistrosBusqueda;
	}

	public BuscarSSCerradasResultUI() {
		super();
		addStyleName("gwt-BuscarCuentaResultPanel");
		resultTableWrapper = new SimplePanel();
		resultTableWrapper.addStyleName("resultTableWrapper");
		add(resultTableWrapper);
		setVisible(false);
	}

	/**
	 * Metodo publico que contiene lo que se desea ejecutar la primera vez que
	 * se busca. (o sea, cuando se hace click al boton Buscar)
	 * 
	 * @param: cuentaSearchDto
	 * */
	public void searchSSCerradas(SolicitudServicioCerradaDto solicitudServicioSearchDto) {
		this.searchSSCerradas(solicitudServicioSearchDto, true);
				};	

	private void searchSSCerradas(SolicitudServicioCerradaDto solicitudServicioCerradaDto, boolean firstTime) {
		SolicitudRpcService.Util.getInstance().searchSSCerrada(solicitudServicioCerradaDto,
				new DefaultWaitCallback<SolicitudesServicioTotalesDto>() {
					public void success(SolicitudesServicioTotalesDto result) {
						setSolicitudServicioDto(result.getSolicitudes());
						buscarSSTotalesResultUI.setValues(result
								.getTotalEquipos().toString(), result
								.getTotalPataconex().toString(), result.getTotalEquiposFirmados().toString());
						buscarSSTotalesResultUI.setVisible(true);
					}
				});
	}
	
	public void setSolicitudServicioDto(List<SolicitudServicioCerradaDto> solicitudServicioDto) {
		this.solicitudesServicioDto = solicitudServicioDto;
		loadTable();
	}

	private void loadTable() {
		if (resultTable != null) {
			resultTable.unsinkEvents(Event.getEventsSunk(resultTable
					.getElement()));
			resultTable.removeFromParent();
		}
		resultTable = new FlexTable();
		resultTable.addTableListener(new Listener());
		initTable(resultTable);
		resultTableWrapper.setWidget(resultTable);
		int row = 1;
		for (SolicitudServicioCerradaDto solicitudServicioDto : solicitudesServicioDto) {
			resultTable.setWidget(row, 0, IconFactory.word());
			resultTable.setHTML(row, 1, solicitudServicioDto.getNumeroSS());
			resultTable.setHTML(row, 2, solicitudServicioDto.getNumeroCuenta());
			resultTable.setHTML(row, 3, solicitudServicioDto.getRazonSocial());
			resultTable.setHTML(row, 4, solicitudServicioDto.getCantidadEquipos().toString());
			resultTable.setHTML(row, 5, solicitudServicioDto.getPataconex() ? "SI" : "NO");
			resultTable.setHTML(row, 6, solicitudServicioDto.getFirmas() ? "SI" : "NO");
			row++;
		}
		setVisible(true);
	}

	private void initTable(FlexTable table) {
		String[] widths = { "24px", "150px", "150px", "150px", "250px",
				"195px", "170px", };
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.getColumnFormatter().addStyleName(0, "alignCenter");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarCuentaResultTable");
		table.getRowFormatter().addStyleName(0, "header");
		table.setHTML(0, 0, Sfa.constant().whiteSpace());
		table.setHTML(0, 1, "N° SS");
		table.setHTML(0, 2, "N° Cuenta");
		table.setHTML(0, 3, "Razon Social");
		table.setHTML(0, 4, "Equipos");
		table.setHTML(0, 5, "Pataconex");
		table.setHTML(0, 6, "Firmas");
	}

	public void setBuscarSSTotalesResultUI(
			BuscarSSTotalesResultUI buscarSSTotalesResultUI) {
		this.buscarSSTotalesResultUI = buscarSSTotalesResultUI;
	}

	private class Listener implements TableListener {

		public void onCellClicked(SourcesTableEvents arg0, int arg1, int arg2) {
			if (arg1 >= 1) {
				String numeroSS = resultTable.getHTML(arg1, 1).toString();
				SolicitudServicioCerradaDto solicitud = buscarSS(numeroSS);
				cambiosSSCerradasResultUI
						.setSolicitudServicioCerradaDto(solicitud);
				cambiosSSCerradasResultUI.setVisible(true);
			}

		}

	}

	private SolicitudServicioCerradaDto buscarSS(String numeroSS) {
		for (Iterator iterator = solicitudesServicioDto.iterator(); iterator
				.hasNext();) {
			SolicitudServicioCerradaDto solicitudServicioDto = (SolicitudServicioCerradaDto) iterator
					.next();
			if (solicitudServicioDto.getNumeroSS().equals(numeroSS)) {
				return solicitudServicioDto;
			}
		}
		return null;
	}

	public void setCambiosSSCerradasResultUI(
			CambiosSSCerradasResultUI cambiosSSCerradasResultUI) {
		this.cambiosSSCerradasResultUI = cambiosSSCerradasResultUI;
	}

}
