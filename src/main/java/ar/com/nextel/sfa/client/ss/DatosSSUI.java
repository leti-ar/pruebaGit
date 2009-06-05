package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.cuenta.DomicilioUI;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalLineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.TitledPanel;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.FocusListenerAdapter;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class DatosSSUI extends Composite implements ClickListener, TableListener {

	private FlowPanel mainpanel;
	private EditarSSUIData editarSSUIData;
	private FlexTable detalleSS;
	private Grid serviciosAdicionales;
	private EditarSSUIController controller;
	private Button crearDomicilio;
	private Button crearLinea;
	private ItemSolicitudDialog itemSolicitudDialog;
	private NumberFormat currencyFormat = NumberFormat.getCurrencyFormat();
	private int selectedDetalleRow = 0;
	private boolean blockServicioAdicionalLoad = false;
	private HTML editarDomicioFacturacion;
	private HTML borrarDomicioFacturacion;
	private HTML editarDomicioEntrega;
	private HTML borrarDomicioEntrega;
	private FocusListener focusListener;
	private int editingServicioAdRow = -1;
	private RegexTextBox precioVenta;
	private DomiciliosCuentaDto domicilioAEditar = null;

	private static final String SELECTED_ROW = "selectedRow";

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

		editarDomicioFacturacion = IconFactory.lapiz();
		borrarDomicioFacturacion = IconFactory.cancel();
		editarDomicioEntrega = IconFactory.lapiz();
		borrarDomicioEntrega = IconFactory.cancel();
		editarDomicioFacturacion.addClickListener(this);
		borrarDomicioFacturacion.addClickListener(this);
		editarDomicioEntrega.addClickListener(this);
		borrarDomicioEntrega.addClickListener(this);

		Grid layoutDomicilio = new Grid(3, 4);
		layoutDomicilio.addStyleName("layout");
		layoutDomicilio.setHTML(0, 0, Sfa.constant().entregaReq());
		layoutDomicilio.setWidget(0, 1, editarSSUIData.getEntrega());
		layoutDomicilio.setWidget(0, 2, editarDomicioEntrega );
		layoutDomicilio.setWidget(0, 3, borrarDomicioEntrega );
		layoutDomicilio.setHTML(1, 0, Sfa.constant().facturacionReq());
		layoutDomicilio.setWidget(1, 1, editarSSUIData.getFacturacion());
		layoutDomicilio.setWidget(1, 2, editarDomicioFacturacion);
		layoutDomicilio.setWidget(1, 3, borrarDomicioFacturacion);
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
		detalleSS = new FlexTable();
		String[] titlesDetalle = { Sfa.constant().whiteSpace(), Sfa.constant().whiteSpace(), "Item",
				"Pcio Vta.", "Alias", "Plan", "Pcio Vta. Plan", "Localidad", "Nº Reserva", "Tipo SS",
				"Cant.", "DDN", "DDI", "Roaming" };
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
		serviciosAdicionales.addTableListener(this);
		String[] titlesServAd = { Sfa.constant().whiteSpace(), "Servicio adicional", "Precio de lista",
				"Precio de venta" };
		for (int i = 0; i < titlesServAd.length; i++) {
			serviciosAdicionales.setHTML(0, i, titlesServAd[i]);
		}
		serviciosAdicionales.setCellPadding(0);
		serviciosAdicionales.setCellSpacing(0);
		serviciosAdicionales.addStyleName("dataTable");
		serviciosAdicionales.setWidth("98%");
		serviciosAdicionales.getRowFormatter().addStyleName(0, "header");
		serviciosAdicionales.getCellFormatter().setWidth(0, 0, "15px");
		serviciosAdicionales.getCellFormatter().setWidth(0, 2, "120px");
		serviciosAdicionales.getCellFormatter().setWidth(0, 3, "120px");
		SimplePanel wrapper2 = new SimplePanel();
		wrapper2.addStyleName("resumenSSTableWrapper mlr5");
		wrapper2.setWidget(serviciosAdicionales);
		detalle.add(wrapper2);

		return detalle;
	}

	public void onClick(Widget sender) {
		if (sender == crearLinea) {
			openItemSolicitudDialog(new LineaSolicitudServicioDto());
		} else if (sender == crearDomicilio || sender == editarDomicioFacturacion
				|| sender == editarDomicioEntrega) {
			onClickEdicionDomicilios(sender);
		} else if (sender == borrarDomicioFacturacion || sender == borrarDomicioEntrega) {
			if (sender == borrarDomicioFacturacion) {
				domicilioAEditar = (DomiciliosCuentaDto) editarSSUIData.getFacturacion().getSelectedItem();
			} else if (sender == borrarDomicioEntrega) {
				domicilioAEditar = (DomiciliosCuentaDto) editarSSUIData.getEntrega().getSelectedItem();
			}
			DomicilioUI.getInstance().openPopupDeleteDialog(editarSSUIData.getCuenta().getPersona(),
					domicilioAEditar, new Command(){
				public void execute() {
					editarSSUIData.refreshDomiciliosListBox();
				}
			});
		}
	}

	public void onClickEdicionDomicilios(Widget sender) {
		DomicilioUI.getInstance().setComandoAceptar(getCommandGuardarDomicilio());
		if (sender == crearDomicilio) {
			DomicilioUI.getInstance().cargarPopupNuevoDomicilio(new DomiciliosCuentaDto());
		} else if (sender == editarDomicioEntrega) {
			DomicilioUI.getInstance().cargarPopupEditarDomicilio(
					(DomiciliosCuentaDto) editarSSUIData.getEntrega().getSelectedItem());
		} else if (sender == editarDomicioFacturacion) {
			DomicilioUI.getInstance().cargarPopupEditarDomicilio(
					(DomiciliosCuentaDto) editarSSUIData.getFacturacion().getSelectedItem());
		}
	}

	private Command getCommandGuardarDomicilio() {
		return new Command() {
			public void execute() {
				DomiciliosCuentaDto domicilio = DomicilioUI.getInstance().getDomicilioAEditar();
				PersonaDto persona = editarSSUIData.getCuenta().getPersona();
				persona.getDomicilios().add(domicilio);
				editarSSUIData.refreshDomiciliosListBox();
			}
		};
	}

	public void onCellClicked(SourcesTableEvents sender, final int row, int col) {
		if (detalleSS == sender) {
			if (row > 0) {
				if (col > 1) {
					// Carga servicios adicionales en la tabla
					if (!blockServicioAdicionalLoad) {
						detalleSS.getRowFormatter().removeStyleName(selectedDetalleRow, SELECTED_ROW);
						detalleSS.getRowFormatter().addStyleName(row, SELECTED_ROW);
						selectedDetalleRow = row;
						loadServiciosAdicionales();
					}
				} else if (col == 0) {
					// Abre panel de edicion de la LineaSolicitudServicio
					openItemSolicitudDialog(editarSSUIData.getLineasSolicitudServicio().get(row - 1));
				} else if (col == 1) {
					// Elimina la LineaSolicitudServicio
					MessageDialog.getInstance().showAceptarCancelar("Desea eliminar el Item?", new Command() {
						public void execute() {
							editarSSUIData.removeLineaSolicitudServicio(row - 1);
							detalleSS.removeRow(row);
							MessageDialog.getInstance().hide();
						};
					}, MessageDialog.getCloseCommand());
				}
			}
		} else if (serviciosAdicionales == sender) {
			if (col == 0 && row > 0) {
				// agrega o quita servicio adicional
				CheckBox check = (CheckBox) serviciosAdicionales.getWidget(row, col);
				ServicioAdicionalLineaSolicitudServicioDto servicioSelected;
				servicioSelected = editarSSUIData.getServiciosAdicionales().get(selectedDetalleRow - 1).get(
						row - 1);
				List<ServicioAdicionalLineaSolicitudServicioDto> saGuardados = editarSSUIData
						.getLineasSolicitudServicio().get(selectedDetalleRow - 1).getServiciosAdicionales();
				if (saGuardados.contains(servicioSelected)) {
					saGuardados.get(saGuardados.indexOf(servicioSelected)).setChecked(check.isChecked());
				} else {
					servicioSelected.setChecked(check.isChecked());
					saGuardados.add(servicioSelected);
				}
			} else if (col == 3 && row > 0) {
				// Muestra textbox de edicion de precio de venta
				blockServicioAdicionalLoad = true;
				ServicioAdicionalLineaSolicitudServicioDto servicioSelected;
				servicioSelected = editarSSUIData.getServiciosAdicionales().get(selectedDetalleRow - 1).get(
						row - 1);
				getPrecioVentaTextBox().setText(
						NumberFormat.getDecimalFormat().format(servicioSelected.getPrecioVenta()));
				editingServicioAdRow = row;
				serviciosAdicionales.setWidget(row, 3, getPrecioVentaTextBox());
				getPrecioVentaTextBox().setFocus(true);
			}
		}
	}

	/** Crea y abre el Dialog para editar la LineaSolicitudServicioDto */
	private void openItemSolicitudDialog(LineaSolicitudServicioDto linea) {
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
		itemSolicitudDialog.show(linea);
	}

	/**
	 * Agrega una LineaSolicitudServicioDto tanto a la tabla como a la Solicitud de Servicio. Maneja la lógica
	 * de la clonación de items con plan cuando la cantidad es mayor a 1.
	 */
	private void addLineaSolicitudServicio(LineaSolicitudServicioDto linea) {
		ArrayList<LineaSolicitudServicioDto> nuevasLineas = new ArrayList();
		nuevasLineas.add(linea);
		if (linea.getPlan() != null) {
			int catLineas = linea.getCantidad();
			if (catLineas > 1) {
				linea.setCantidad(1);
				for (int i = 0; i < catLineas - 1; i++) {
					LineaSolicitudServicioDto lineaCloned = linea.clone();
					lineaCloned.setNumeradorLinea(null);
					nuevasLineas.add(lineaCloned);
				}
			}

		}
		int firstNewRow = 0;
		for (int i = 0; i < nuevasLineas.size(); i++) {
			LineaSolicitudServicioDto nueva = nuevasLineas.get(i);
			int newRow = editarSSUIData.addLineaSolicitudServicio(nueva) + 1;
			if (firstNewRow == 0) {
				firstNewRow = newRow;
			} else {
				nueva.setAlias(editarSSUIData.getNombreMovil());
			}
			drawDetalleSSRow(nueva, newRow);
		}

		onCellClicked(detalleSS, firstNewRow, 2);
	}

	/** Limpia y recarga la tabla de Detalle de Solicitud de Servicio completamente */
	public void refreshDetalleSSTable() {
		while (detalleSS.getRowCount() > 1) {
			detalleSS.removeRow(1);
		}
		for (LineaSolicitudServicioDto linea : editarSSUIData.getLineasSolicitudServicio()) {
			drawDetalleSSRow(linea, editarSSUIData.getLineasSolicitudServicio().indexOf(linea) + 1);
		}
		if (!editarSSUIData.getLineasSolicitudServicio().isEmpty()) {
			onCellClicked(detalleSS, 1, 2);
		}
	}

	/** Agrega una fila a la tabla de Detalle de Solicitud de Servicio en la posicion indicada */
	private void drawDetalleSSRow(LineaSolicitudServicioDto linea, int newRow) {
		detalleSS.setWidget(newRow, 0, IconFactory.lapiz());
		detalleSS.setWidget(newRow, 1, IconFactory.cancel());
		detalleSS.setText(newRow, 2, linea.getItem().getDescripcion());
		detalleSS.setText(newRow, 3, currencyFormat.format(linea.getPrecioVenta()));
		detalleSS.setText(newRow, 4, linea.getAlias() != null ? linea.getAlias() : "");
		detalleSS.setText(newRow, 5, linea.getPlan() != null ? linea.getPlan().getDescripcion() : "");
		detalleSS.setText(newRow, 6, linea.getPlan() != null ? currencyFormat.format(linea
				.getPrecioVentaPlan()) : "");
		detalleSS.setText(newRow, 7, linea.getLocalidad() != null ? linea.getLocalidad().getDescripcion()
				: "");
		detalleSS.setText(newRow, 8, linea.getNumeroReserva());
		detalleSS.setText(newRow, 9, linea.getTipoSolicitud().getDescripcion());
		detalleSS.setText(newRow, 10, "" + linea.getCantidad());
		detalleSS.setText(newRow, 11, linea.getDdn() ? "Si" : "No");
		detalleSS.setText(newRow, 12, linea.getDdi() ? "Si" : "No");
		detalleSS.setText(newRow, 13, linea.getRoaming() ? "Si" : "No");
		detalleSS.getCellFormatter().addStyleName(newRow, 3, "alignRight");
		detalleSS.getCellFormatter().addStyleName(newRow, 6, "alignRight");
	}

	/**
	 * Carga la tabla de Servicios Adicionales con los correspondientes a la linea seleccionada en la tabla
	 * Detalle. Si no están en el cliente los trae del server
	 */
	private void loadServiciosAdicionales() {
		LineaSolicitudServicioDto linea = editarSSUIData.getLineasSolicitudServicio().get(
				selectedDetalleRow - 1);
		if (linea.getPlan() == null) {
			serviciosAdicionales.resizeRows(1);
			return;
		}
		List servicios = editarSSUIData.getServiciosAdicionales().get(selectedDetalleRow - 1);
		if (!servicios.isEmpty()) {
			refreshServiciosAdicionalesTable(selectedDetalleRow - 1);
		} else {
			controller.getServiciosAdicionales(linea,
					new DefaultWaitCallback<List<ServicioAdicionalLineaSolicitudServicioDto>>() {
						public void success(List<ServicioAdicionalLineaSolicitudServicioDto> list) {
							editarSSUIData.loadServiciosAdicionales(selectedDetalleRow - 1, list);
							blockServicioAdicionalLoad = false;
							refreshServiciosAdicionalesTable(selectedDetalleRow - 1);
						}

						public void failure(Throwable caught) {
							blockServicioAdicionalLoad = false;
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
		List<ServicioAdicionalLineaSolicitudServicioDto> allServiciosAdicionales = editarSSUIData
				.getServiciosAdicionales().get(lineaIndex);
		LineaSolicitudServicioDto linea = editarSSUIData.getLineasSolicitudServicio().get(lineaIndex);
		serviciosAdicionales.resizeRows(allServiciosAdicionales.size() + 1);
		int row = 1;
		for (ServicioAdicionalLineaSolicitudServicioDto servicioAdicional : allServiciosAdicionales) {
			int saIndex = linea.getServiciosAdicionales().indexOf(servicioAdicional);
			if (saIndex >= 0) {
				servicioAdicional = linea.getServiciosAdicionales().get(saIndex);
			}
			CheckBox check = new CheckBox();
			if (servicioAdicional.isObligatorio()) {
				check.setEnabled(false);
				check.setChecked(true);
			} else if (servicioAdicional.isChecked()) {
				check.setChecked(true);
			}
			serviciosAdicionales.setWidget(row, 0, check);
			serviciosAdicionales.setHTML(row, 1, servicioAdicional.getDescripcionServicioAdicional());
			serviciosAdicionales.setHTML(row, 2, currencyFormat.format(servicioAdicional.getPrecioLista()));
			serviciosAdicionales.setHTML(row, 3, currencyFormat.format(servicioAdicional.getPrecioVenta()));
			serviciosAdicionales.getCellFormatter().addStyleName(row, 1, "alignLeft");
			serviciosAdicionales.getCellFormatter().addStyleName(row, 2, "alignRight");
			serviciosAdicionales.getCellFormatter().addStyleName(row, 3, "alignRight");
			row++;
		}
	}

	/** Listener para el TextBox que modifica el valor del Precio de Venta de los Servicios Adicionales */
	private FocusListener getTextBoxFocusListener() {
		if (focusListener == null) {
			focusListener = new FocusListenerAdapter() {
				public void onLostFocus(Widget sender) {
					blockServicioAdicionalLoad = false;
					TextBox textBox = (TextBox) sender;
					ServicioAdicionalLineaSolicitudServicioDto servicioSelected;
					servicioSelected = editarSSUIData.getServiciosAdicionales().get(selectedDetalleRow - 1)
							.get(editingServicioAdRow - 1);
					double valor = servicioSelected.getPrecioVenta();
					MessageDialog.getInstance().setDialogTitle("Error");
					try {
						valor = NumberFormat.getDecimalFormat().parse(textBox.getText());
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
					serviciosAdicionales.setHTML(editingServicioAdRow, 3, NumberFormat.getCurrencyFormat()
							.format(valor));
					editarSSUIData.getModificarValorServicioAdicional(selectedDetalleRow - 1,
							editingServicioAdRow - 1, valor);
					editingServicioAdRow = -1;
				}
			};
		}
		return focusListener;
	}

	/** TextBox que modifica el valor del Precio de Venta de los Servicios Adicionales */
	private TextBox getPrecioVentaTextBox() {
		if (precioVenta == null) {
			precioVenta = new RegexTextBox(RegularExpressionConstants.importe);
			precioVenta.addFocusListener(getTextBoxFocusListener());
			precioVenta.setWidth("110px");
			precioVenta.addKeyboardListener(new KeyboardListenerAdapter() {
				public void onKeyPress(Widget sender, char keyCode, int modifiers) {
					if (KeyboardListener.KEY_ENTER == keyCode) {
						getTextBoxFocusListener().onLostFocus(precioVenta);
					}
				}
			});
		}
		return precioVenta;
	}
}
