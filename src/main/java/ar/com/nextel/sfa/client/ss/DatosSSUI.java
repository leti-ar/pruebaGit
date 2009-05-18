package ar.com.nextel.sfa.client.ss;

import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalLineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.TitledPanel;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;

public class DatosSSUI extends Composite implements ClickListener, TableListener {

	private FlowPanel mainpanel;
	private EditarSSUIData editarSSUIData;
	private Grid detalleSS;
	private Grid serviciosAdicionales;
	private EditarSSUIController controller;
	private Button crearDomicilio;
	private Button crearLinea;
	private ItemSolicitudDialog itemSolicitudDialog;
	private NumberFormat currencyFormat = NumberFormat.getCurrencyFormat();
	private int selectedDetalleRow = 0;

	public DatosSSUI(EditarSSUIController controller) {
		mainpanel = new FlowPanel();
		initWidget(mainpanel);
		this.controller = controller;
		editarSSUIData = controller.getEditarSSUIData();
		mainpanel.add(firstRow());
		mainpanel.add(getDomicilioPanel());
		mainpanel.add(getDetallePanel());
	}

	private Widget firstRow() {
		Grid layout = new Grid(1, 6);
		layout.addStyleName("layout");
		layout.setHTML(0, 0, Sfa.constant().nssReq());
		layout.setWidget(0, 1, editarSSUIData.getNss());
		layout.setHTML(0, 2, Sfa.constant().nflota());
		layout.setWidget(0, 3, editarSSUIData.getNflota());
		layout.setHTML(0, 4, Sfa.constant().origenReq());
		layout.setWidget(0, 5, editarSSUIData.getOrigen());
		return layout;
	}

	private Widget getDomicilioPanel() {
		TitledPanel domicilio = new TitledPanel("Domicilio");

		crearDomicilio = new Button("Crear nuevo");
		crearDomicilio.addClickListener(this);
		crearDomicilio.addStyleName("crearDomicilioButton");
		SimplePanel crearDomicilioWrapper = new SimplePanel();
		crearDomicilioWrapper.add(crearDomicilio);
		crearDomicilioWrapper.addStyleName("h20");

		domicilio.add(crearDomicilioWrapper);
		Grid layoutDomicilio = new Grid(3, 4);
		layoutDomicilio.addStyleName("layout");
		layoutDomicilio.setHTML(0, 0, Sfa.constant().entregaReq());
		layoutDomicilio.setWidget(0, 1, editarSSUIData.getEntrega());
		layoutDomicilio.setWidget(0, 2, IconFactory.lapiz());
		layoutDomicilio.setWidget(0, 3, IconFactory.cancel());
		layoutDomicilio.setHTML(1, 0, Sfa.constant().facturacionReq());
		layoutDomicilio.setWidget(1, 1, editarSSUIData.getFacturacion());
		layoutDomicilio.setWidget(1, 2, IconFactory.lapiz());
		layoutDomicilio.setWidget(1, 3, IconFactory.cancel());
		layoutDomicilio.setHTML(2, 0, Sfa.constant().aclaracion());
		layoutDomicilio.setWidget(2, 1, editarSSUIData.getAclaracion());
		layoutDomicilio.getColumnFormatter().setWidth(1, "500px");
		domicilio.add(layoutDomicilio);
		return domicilio;
	}

	private Widget getDetallePanel() {
		TitledPanel detalle = new TitledPanel("Detalle");

		crearLinea = new Button("Crear nuevo");
		crearLinea.addClickListener(this);
		crearLinea.addStyleName("crearLineaButton");
		SimplePanel crearLineaWrapper = new SimplePanel();
		crearLineaWrapper.add(crearLinea);
		crearLineaWrapper.addStyleName("h20");
		detalle.add(crearLineaWrapper);
		SimplePanel wrapper = new SimplePanel();
		wrapper.addStyleName("resumenSSTableWrapper mlr5");
		detalleSS = new Grid(1, 14);
		String[] titlesDetalle = { " ", " ", "Item", "Pcio Vta.", "Alias", "Plan", "Pcio Vta. Plan",
				"Localidad", "Nº Reserva", "Tipo SS", "Cant.", "DDN", "DDI", "Roaming" };
		for (int i = 0; i < titlesDetalle.length; i++) {
			detalleSS.setHTML(0, i, titlesDetalle[i]);
		}
		detalleSS.setCellPadding(0);
		detalleSS.setCellSpacing(0);
		detalleSS.addStyleName("dataTable");
		detalleSS.setWidth("98%");
		detalleSS.getRowFormatter().addStyleName(0, "header");
		detalleSS.addTableListener(this);
		wrapper.setWidget(detalleSS);
		detalle.add(wrapper);

		detalle.add(new Label("Servicios Adicionales"));

		serviciosAdicionales = new Grid(1, 4);
		String[] titlesServAd = { " ", "Servicio adicional", "Precio de lista", "Precio de venta" };
		for (int i = 0; i < titlesServAd.length; i++) {
			serviciosAdicionales.setHTML(0, i, titlesServAd[i]);
		}
		serviciosAdicionales.setCellPadding(0);
		serviciosAdicionales.setCellSpacing(0);
		serviciosAdicionales.addStyleName("dataTable");
		serviciosAdicionales.setWidth("98%");
		serviciosAdicionales.getRowFormatter().addStyleName(0, "header");
		SimplePanel wrapper2 = new SimplePanel();
		wrapper2.addStyleName("resumenSSTableWrapper mlr5");
		wrapper2.setWidget(serviciosAdicionales);
		detalle.add(wrapper2);
		return detalle;
	}

	public void onClick(Widget sender) {
		if (sender == crearLinea) {
			openItemSolicitudDialog();
		} else if (sender == crearDomicilio) {

		}

	}

	public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
		if (row > 0) {
			selectedDetalleRow = row;
			loadServiciosAdicionales();
		}
	}

	private void openItemSolicitudDialog() {
		if (itemSolicitudDialog == null) {
			itemSolicitudDialog = new ItemSolicitudDialog("Agregar Item", controller);
			Command aceptarCommand = new Command() {
				public void execute() {
					addLineaSolicitudServicio(itemSolicitudDialog.getItemSolicitudUIData()
							.getLineaSolicitudServicio());
				}
			};
			itemSolicitudDialog.setAceptarCommand(aceptarCommand);
		}
		itemSolicitudDialog.getItemSolicitudUIData().setLineaSolicitudServicio(
				new LineaSolicitudServicioDto());
		itemSolicitudDialog.showAndCenter();
	}

	private void addLineaSolicitudServicio(LineaSolicitudServicioDto linea) {
		editarSSUIData.addLineaSolicitudServicio(linea);
		int newRow = detalleSS.getRowCount();
		detalleSS.resizeRows(newRow + 1);
		detalleSS.setText(newRow, 2, linea.getItem().getDescripcion());
		detalleSS.setText(newRow, 3, currencyFormat.format(linea.getPrecioVenta()));
		detalleSS.setText(newRow, 4, linea.getAlias());
		detalleSS.setText(newRow, 5, linea.getPlan().getDescripcion());
		detalleSS.setText(newRow, 6, currencyFormat.format(linea.getPrecioVentaPlan()));
		detalleSS.setText(newRow, 7, linea.getLocalidad().getDescripcion());
		detalleSS.setText(newRow, 8, linea.getNumeroReserva());
		detalleSS.setText(newRow, 9, linea.getTipoSolicitud().getDescripcion());
		detalleSS.setText(newRow, 10, "" + linea.getCantidad());
		detalleSS.setText(newRow, 11, linea.getDdn() ? "Si" : "No");
		detalleSS.setText(newRow, 12, linea.getDdi() ? "Si" : "No");
		detalleSS.setText(newRow, 13, linea.getRoaming() ? "Si" : "No");
		onCellClicked(detalleSS, newRow, 1);
	}

	private void loadServiciosAdicionales() {
		LineaSolicitudServicioDto linea = editarSSUIData.getLineaSolicitudServicio().get(
				selectedDetalleRow - 1);
		if (linea.getServiciosAdicionales() != null) {
			renderServiciosAdicionalesTable(linea.getServiciosAdicionales());
		} else {
			controller.getServiciosAdicionales(linea, new DefaultWaitCallback<LineaSolicitudServicioDto>() {
				public void success(LineaSolicitudServicioDto lineaConServAdicionales) {
					editarSSUIData.getLineaSolicitudServicio().get(selectedDetalleRow - 1)
							.setServiciosAdicionales(lineaConServAdicionales.getServiciosAdicionales());
					renderServiciosAdicionalesTable(lineaConServAdicionales.getServiciosAdicionales());
				}
			});
		}
	}

	private void renderServiciosAdicionalesTable(List<ServicioAdicionalLineaSolicitudServicioDto> servicios) {
		serviciosAdicionales.resizeRows(servicios.size() + 1);
		int row = 1;
		for (ServicioAdicionalLineaSolicitudServicioDto servicioAdicional : servicios) {
			CheckBox check = new CheckBox();
			if (servicioAdicional.getObligatorio()) {
				check.setEnabled(false);
				check.setChecked(true);
			} else if (servicioAdicional.getChecked()) {
				check.setChecked(true);
			}
			serviciosAdicionales.setHTML(row, 0, check.toString());
			serviciosAdicionales.setHTML(row, 1, servicioAdicional.getDescripcionServicioAdicional());
			serviciosAdicionales.setHTML(row, 2, currencyFormat.format(servicioAdicional.getPrecioLista()));
			serviciosAdicionales.setHTML(row, 3, currencyFormat.format(servicioAdicional.getPrecioLista()));
			row++;
		}
	}
}
