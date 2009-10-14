package ar.com.nextel.sfa.client.ss;

import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalLineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;

public class ServiciosAdicionalesTable extends Composite {

	private Grid table;
	private EditarSSUIData editarSSUIData;
	private TextBox precioVenta;
	private int selectedLineaSSRow;
	private int editingServicioAdicionalRow = -1;
	private boolean editing = false;
	private EditarSSUIController controller;
	public static final int COL_AGREGAR_QUITAR = 0;
	public static final int COL_PRECIO_VENTA = 3;
	private final NumberFormat currencyFormat = NumberFormat.getCurrencyFormat();

	public ServiciosAdicionalesTable(EditarSSUIController controller) {
		this.controller = controller;
		this.editarSSUIData = controller.getEditarSSUIData();
		SimplePanel wrapper = new SimplePanel();
		initWidget(wrapper);
		table = new Grid(1, 4);
		String[] titlesServAd = { Sfa.constant().whiteSpace(), "Servicio adicional", "Precio de lista",
				"Precio de venta" };
		for (int i = 0; i < titlesServAd.length; i++) {
			table.setHTML(0, i, titlesServAd[i]);
		}
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("dataTable");
		table.setWidth("98%");
		table.getRowFormatter().addStyleName(0, "header");
		table.getCellFormatter().setWidth(0, 0, "15px");
		table.getCellFormatter().setWidth(0, 2, "120px");
		table.getCellFormatter().setWidth(0, 3, "120px");
		wrapper.addStyleName("resumenSSTableWrapper mlr5");
		wrapper.setWidget(table);
	}

	public void agregarQuitarServicioAdicional(int row) {
		// agrega o quita servicio adicional
		CheckBox check = (CheckBox) table.getWidget(row, COL_AGREGAR_QUITAR);
		ServicioAdicionalLineaSolicitudServicioDto servicioSelected;
		servicioSelected = editarSSUIData.getServiciosAdicionales().get(selectedLineaSSRow - 1).get(row - 1);
		List<ServicioAdicionalLineaSolicitudServicioDto> saGuardados = editarSSUIData
				.getLineasSolicitudServicio().get(selectedLineaSSRow - 1).getServiciosAdicionales();
		if (saGuardados.contains(servicioSelected)) {
			saGuardados.get(saGuardados.indexOf(servicioSelected)).setChecked(check.getValue());
		} else {
			servicioSelected.setChecked(check.getValue());
			saGuardados.add(servicioSelected);
		}
	}

	public void editarPrecioDeVentaServicioAdicional(int row) {
		// Muestra textbox de edicion de precio de venta
		editing = true;
		ServicioAdicionalLineaSolicitudServicioDto servicioSelected;
		servicioSelected = editarSSUIData.getServiciosAdicionales().get(selectedLineaSSRow - 1).get(row - 1);
		getPrecioVentaTextBox().setText(
				NumberFormat.getDecimalFormat().format(servicioSelected.getPrecioVenta()));
		editingServicioAdicionalRow = row;
		table.setWidget(row, COL_PRECIO_VENTA, getPrecioVentaTextBox());
		getPrecioVentaTextBox().setFocus(true);
	}

	/** TextBox que modifica el valor del Precio de Venta de los Servicios Adicionales */
	private TextBox getPrecioVentaTextBox() {
		if (precioVenta == null) {
			precioVenta = new RegexTextBox(RegularExpressionConstants.importe);
			precioVenta.addBlurHandler(new BlurHandler() {
				public void onBlur(BlurEvent blurEvent) {
					updatePrecioVentaDeServiciosAdicionales(precioVenta.getText());
				}
			});
			precioVenta.setWidth("110px");
			precioVenta.addKeyPressHandler(new KeyPressHandler() {
				public void onKeyPress(KeyPressEvent keyPressEvent) {
					if (KeyCodes.KEY_ENTER == keyPressEvent.getCharCode()) {
						updatePrecioVentaDeServiciosAdicionales(precioVenta.getText());
					}
				}
			});
		}
		return precioVenta;
	}

	private void updatePrecioVentaDeServiciosAdicionales(String precioVenta) {
		editing = false;
		ServicioAdicionalLineaSolicitudServicioDto servicioSelected;
		servicioSelected = editarSSUIData.getServiciosAdicionales().get(selectedLineaSSRow - 1).get(
				editingServicioAdicionalRow - 1);
		double valor = servicioSelected.getPrecioVenta();
		MessageDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
		try {
			valor = NumberFormat.getDecimalFormat().parse(precioVenta);
		} catch (NumberFormatException e) {
			MessageDialog.getInstance().showAceptar("Ingrese un monto válido",
					MessageDialog.getCloseCommand());
		}
		if (valor > servicioSelected.getPrecioVenta()) {
			MessageDialog.getInstance().showAceptar(
					"El desvío debe ser menor o igual al precio de lista del servicio adicional",
					MessageDialog.getCloseCommand());
			valor = servicioSelected.getPrecioVenta();
		}
		table.setHTML(editingServicioAdicionalRow, 3, NumberFormat.getCurrencyFormat().format(valor));
		editarSSUIData.modificarValorServicioAdicional(selectedLineaSSRow - 1,
				editingServicioAdicionalRow - 1, valor);
		editingServicioAdicionalRow = -1;
	}

	/**
	 * Carga la tabla de Servicios Adicionales con los correspondientes a la LineaSolicitudServicio
	 * seleccionada en la tabla Detalle. Si no están en el cliente los trae del server.
	 */
	public void setServiciosAdicionalesFor(int selectedLineaSS) {
		this.selectedLineaSSRow = selectedLineaSS;
		if (selectedLineaSSRow < 1
				|| editarSSUIData.getLineasSolicitudServicio().get(selectedLineaSSRow - 1).getPlan() == null) {
			table.resizeRows(1);
			return;
		}
		List serviciosAdicionalesOriginales = editarSSUIData.getServiciosAdicionales().get(
				selectedLineaSSRow - 1);
		if (!serviciosAdicionalesOriginales.isEmpty()) {
			editarSSUIData.mergeServiciosAdicionalesConLineaSolicitudServicio(selectedLineaSSRow - 1,
					serviciosAdicionalesOriginales);
			refreshServiciosAdicionalesTable(selectedLineaSSRow - 1);
		} else {
			controller.getServiciosAdicionales(editarSSUIData.getLineasSolicitudServicio().get(
					selectedLineaSSRow - 1),
					new DefaultWaitCallback<List<ServicioAdicionalLineaSolicitudServicioDto>>() {
						public void success(List<ServicioAdicionalLineaSolicitudServicioDto> list) {
							editarSSUIData.loadServiciosAdicionales(selectedLineaSSRow - 1, list);
							editing = false;
							refreshServiciosAdicionalesTable(selectedLineaSSRow - 1);
						}

						public void failure(Throwable caught) {
							editing = false;
							super.failure(caught);
						}
					});
		}
	}

	/**
	 * Actualiza gráficamente la Tabla de Servicios Adicionales. Recibe la posición de la
	 * LineaSolicitudServicioDto dentro de la lista "lineas" de SolicitudServicioDto.
	 */
	private void refreshServiciosAdicionalesTable(int lineaIndex) {
		List<ServicioAdicionalLineaSolicitudServicioDto> serviciosAdicionalesOriginales = editarSSUIData
				.getServiciosAdicionales().get(lineaIndex);
		LineaSolicitudServicioDto linea = editarSSUIData.getLineasSolicitudServicio().get(lineaIndex);
		table.resizeRows(serviciosAdicionalesOriginales.size() + 1);
		int row = 1;
		for (ServicioAdicionalLineaSolicitudServicioDto servicioAdicional : serviciosAdicionalesOriginales) {
			// Si la LineaSolicituServicio tiene el Servicio Adicional, tomo el de la misma.
			int saIndex = linea.getServiciosAdicionales().indexOf(servicioAdicional);
			if (saIndex >= 0) {
				servicioAdicional = linea.getServiciosAdicionales().get(saIndex);
			}
			CheckBox check = new CheckBox();
			check.setEnabled(!servicioAdicional.isObligatorio());
			check.setValue(servicioAdicional.isObligatorio() || servicioAdicional.isChecked());
			table.setWidget(row, 0, check);
			table.setHTML(row, 1, servicioAdicional.getDescripcionServicioAdicional());
			table.setHTML(row, 2, currencyFormat.format(servicioAdicional.getPrecioLista()));
			table.setHTML(row, 3, currencyFormat.format(servicioAdicional.getPrecioVenta()));
			table.getCellFormatter().addStyleName(row, 1, "alignLeft");
			table.getCellFormatter().addStyleName(row, 2, "alignRight");
			table.getCellFormatter().addStyleName(row, 3, "alignRight");
			row++;
		}
	}

	public void clear() {
		table.resizeRows(1);
	}

	public Grid getTable() {
		return table;
	}

	public boolean isEditing() {
		return editing;
	}
}
