package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.debug.DebugConstants;
import ar.com.nextel.sfa.client.domicilio.DomicilioUI;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.ModalMessageDialog;
import ar.com.nextel.sfa.client.widget.TitledPanel;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

public class DatosSSUI extends Composite implements ClickHandler {

	private FlowPanel mainpanel;
	private EditarSSUIData editarSSUIData;
	private Grid nnsLayout;
	private Grid domicilioLayout;
	private FlexTable detalleSS;
	private ServiciosAdicionalesTable serviciosAdicionales;
	private EditarSSUIController controller;
	private Button crearDomicilio;
	private Button crearLinea;
	private ItemSolicitudDialog itemSolicitudDialog;
	private NumberFormat currencyFormat = NumberFormat.getCurrencyFormat();
	private int selectedDetalleRow = 0;
	private HTML editarDomicioFacturacion;
	private HTML borrarDomicioFacturacion;
	private HTML editarDomicioEntrega;
	private HTML borrarDomicioEntrega;
	private DomiciliosCuentaDto domicilioAEditar = null;
	private TextBox precioVentaPlan;
	private List listaDomicilios = new ArrayList<DomiciliosCuentaDto>();

	private static final String SELECTED_ROW = "selectedRow";

	public DatosSSUI(EditarSSUIController controller) {
		mainpanel = new FlowPanel();
		initWidget(mainpanel);
		this.controller = controller;
		editarSSUIData = controller.getEditarSSUIData();
		mainpanel.add(getNssLayout());
		mainpanel.add(getDomicilioPanel());
		mainpanel.add(getDetallePanel());
	}

	private Widget getNssLayout() {
		nnsLayout = new Grid(1, 6);
		nnsLayout.addStyleName("layout");
		refreshNssLayout();
		return nnsLayout;
	}

	private void refreshNssLayout() {
		nnsLayout.setHTML(0, 0, Sfa.constant().nssReq());
		nnsLayout.setWidget(0, 1, editarSSUIData.getNss());
		if (editarSSUIData.getGrupoSolicitud() == null
				|| !GrupoSolicitudDto.ID_CDW.equals(editarSSUIData.getGrupoSolicitud().getId())) {
			nnsLayout.setHTML(0, 2, Sfa.constant().nflota());
			nnsLayout.setWidget(0, 3, editarSSUIData.getNflota());
			nnsLayout.setHTML(0, 4, Sfa.constant().origenReq());
			nnsLayout.setWidget(0, 5, editarSSUIData.getOrigen());
		} else {
			nnsLayout.setHTML(0, 2, Sfa.constant().origenReq());
			nnsLayout.setWidget(0, 3, editarSSUIData.getOrigen());
			nnsLayout.clearCell(0, 4);
			nnsLayout.clearCell(0, 5);
		}
	}

	private Widget getDomicilioPanel() {
		TitledPanel domicilio = new TitledPanel("Domicilio");

		crearDomicilio = new Button("Crear nuevo");
		crearDomicilio.ensureDebugId("EditarSS-Datos-CrearDomicilio");
		crearDomicilio.addClickHandler(this);
		crearDomicilio.addStyleName("crearDomicilioButton");
		SimplePanel crearDomicilioWrapper = new SimplePanel();
		crearDomicilioWrapper.add(crearDomicilio);
		crearDomicilioWrapper.addStyleName("h20");
		domicilio.add(crearDomicilioWrapper);

		editarDomicioFacturacion = IconFactory.lapiz();
		borrarDomicioFacturacion = IconFactory.cancel();
		editarDomicioEntrega = IconFactory.lapiz();
		borrarDomicioEntrega = IconFactory.cancel();
		editarDomicioFacturacion.addClickHandler(this);
		borrarDomicioFacturacion.addClickHandler(this);
		editarDomicioEntrega.addClickHandler(this);
		borrarDomicioEntrega.addClickHandler(this);

		domicilioLayout = new Grid(3, 4);
		domicilioLayout.addStyleName("layout");
		domicilioLayout.getColumnFormatter().setWidth(1, "500px");
		refreshDomicilioLayout();
		domicilio.add(domicilioLayout);
		return domicilio;
	}

	private void refreshDomicilioLayout() {
		if (editarSSUIData.getGrupoSolicitud() == null
				|| !GrupoSolicitudDto.ID_CDW.equals(editarSSUIData.getGrupoSolicitud().getId())) {
			domicilioLayout.setHTML(0, 0, Sfa.constant().entregaReq());
			domicilioLayout.setWidget(0, 1, editarSSUIData.getEntrega());
			domicilioLayout.setWidget(0, 2, editarDomicioEntrega);
			domicilioLayout.setWidget(0, 3, borrarDomicioEntrega);
			domicilioLayout.setHTML(1, 0, Sfa.constant().facturacionReq());
			domicilioLayout.setWidget(1, 1, editarSSUIData.getFacturacion());
			domicilioLayout.setWidget(1, 2, editarDomicioFacturacion);
			domicilioLayout.setWidget(1, 3, borrarDomicioFacturacion);
			domicilioLayout.setHTML(2, 0, Sfa.constant().aclaracion());
			domicilioLayout.setWidget(2, 1, editarSSUIData.getAclaracion());
		} else {
			domicilioLayout.setHTML(0, 0, Sfa.constant().facturacionReq());
			domicilioLayout.setWidget(0, 1, editarSSUIData.getFacturacion());
			domicilioLayout.setWidget(0, 2, editarDomicioFacturacion);
			domicilioLayout.setWidget(0, 3, borrarDomicioFacturacion);
			domicilioLayout.setHTML(1, 0, Sfa.constant().emailReq());
			domicilioLayout.setWidget(1, 1, editarSSUIData.getEmail());
			domicilioLayout.clearCell(1, 2);
			domicilioLayout.clearCell(1, 3);
			domicilioLayout.clearCell(2, 0);
			domicilioLayout.clearCell(2, 1);
		}
	}

	private Widget getDetallePanel() {
		TitledPanel detalle = new TitledPanel("Detalle");

		crearLinea = new Button("Crear nuevo");
		crearLinea.ensureDebugId(DebugConstants.EDITAR_SOLICITUD_DATOS_BUTTON_CREAR_LINEA);
		crearLinea.addClickHandler(this);
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
		detalleSS.ensureDebugId(DebugConstants.EDITAR_SOLICITUD_DATOS_DETTALE_TABLE);
		detalleSS.addStyleName("dataTable");
		detalleSS.setWidth("98%");
		detalleSS.getRowFormatter().addStyleName(0, "header");
		detalleSS.addClickHandler(this);
		wrapper.setWidget(detalleSS);
		detalle.add(wrapper);

		Label serviciosAdicionalesLabel = new Label("Servicios Adicionales");
		serviciosAdicionalesLabel.addStyleName("mt5 mb5");
		detalle.add(serviciosAdicionalesLabel);

		serviciosAdicionales = new ServiciosAdicionalesTable(controller);
		serviciosAdicionales.getTable().addClickHandler(this);
		detalle.add(serviciosAdicionales);

		return detalle;
	}

	public void onClick(ClickEvent clickEvent) {
		Widget sender = (Widget) clickEvent.getSource();
		if (sender == crearLinea) {
			openItemSolicitudDialog(new LineaSolicitudServicioDto());
		} else if (sender == crearDomicilio || sender == editarDomicioFacturacion
				|| sender == editarDomicioEntrega) {
			onClickEdicionDomicilios(sender);
		} else if (sender == borrarDomicioFacturacion || sender == borrarDomicioEntrega) {
			if ((sender == borrarDomicioFacturacion)) {
				borrarDomicilioFacturacion();

			} else if (sender == borrarDomicioEntrega) {
				borrarDomicilioEntrega();
			}

		} else if (sender == detalleSS || sender == serviciosAdicionales.getTable()) {
			Cell cell = ((HTMLTable) sender).getCellForEvent(clickEvent);
			if (cell != null) {
				onTableClick(sender, cell.getRowIndex(), cell.getCellIndex());
			}
		}
	}

	public void borrarDomicilioFacturacion() {
		if (editarSSUIData.getFacturacion().getSelectedItemText() == null) {
		} else {
			domicilioAEditar = (DomiciliosCuentaDto) editarSSUIData.getFacturacion().getSelectedItem();
			DomicilioUI.getInstance().openPopupDeleteDialog(editarSSUIData.getCuenta().getPersona(),
					domicilioAEditar, new Command() {
						public void execute() {
							editarSSUIData.refreshDomiciliosListBox();
						}
					});
		}
	}

	public void borrarDomicilioEntrega() {
		if (editarSSUIData.getEntrega().getSelectedItemText() == null) {
		} else {
			domicilioAEditar = (DomiciliosCuentaDto) editarSSUIData.getEntrega().getSelectedItem();
			DomicilioUI.getInstance().openPopupDeleteDialog(editarSSUIData.getCuenta().getPersona(),
					domicilioAEditar, new Command() {
						public void execute() {
							editarSSUIData.refreshDomiciliosListBox();
						}
					});
		}
	}

	public void onClickEdicionDomicilios(Widget sender) {
		// Verifico si esta completo el domicilio, si no esta no haca ninguna accion
		if (((sender == editarDomicioEntrega) && (editarSSUIData.getEntrega().getSelectedItemText() == null))
				|| ((sender == editarDomicioFacturacion) && (editarSSUIData.getFacturacion()
						.getSelectedItemText() == null))) {
		} else {
			DomicilioUI.getInstance().setComandoAceptar(getCommandGuardarDomicilio());
			listaDomicilios = editarSSUIData.getCuenta().getPersona().getDomicilios();

			if (sender == crearDomicilio) {
				domicilioAEditar = new DomiciliosCuentaDto();
			} else if (sender == editarDomicioEntrega) {
				domicilioAEditar = (DomiciliosCuentaDto) editarSSUIData.getEntrega().getSelectedItem();
			} else if (sender == editarDomicioFacturacion) {
				domicilioAEditar = (DomiciliosCuentaDto) editarSSUIData.getFacturacion().getSelectedItem();
			}
			domicilioAEditar = domicilioAEditar != null ? domicilioAEditar : new DomiciliosCuentaDto();
			DomicilioUI.getInstance().setParentContacto(false);
			DomicilioUI.getInstance().cargarListBoxEntregaFacturacion(listaDomicilios, domicilioAEditar);
			DomicilioUI.getInstance().cargarPopupEditarDomicilio(domicilioAEditar);
		}
	}

	private Command getCommandGuardarDomicilio() {
		return new Command() {
			public void execute() {
				DomiciliosCuentaDto domicilio = DomicilioUI.getInstance().getDomicilioAEditar();
				editarSSUIData.addDomicilio(domicilio);
				editarSSUIData.refreshDomiciliosListBox();
			}
		};
	}

	public void onTableClick(Widget sender, final int row, int col) {
		if (detalleSS == sender) {
			if (row > 0) {
				if (col == 6) {
					if (!serviciosAdicionales.isEditing()) {
						editarPrecioDeVentaPlan();
					}
				} else if (col > 1) {
					// Carga servicios adicionales en la tabla
					if (!serviciosAdicionales.isEditing()) {
						selectDetalleLineaSSRow(row);
					}
				} else if (col == 0) {
					// Abre panel de edicion de la LineaSolicitudServicio
					openItemSolicitudDialog(editarSSUIData.getLineasSolicitudServicio().get(row - 1));
				} else if (col == 1) {
					// Elimina la LineaSolicitudServicio
					ModalMessageDialog.getInstance().showAceptarCancelar("", "Desea eliminar el Item?",
							new Command() {
								public void execute() {
									removeDetalleLineaSSRow(row);
								};
							}, ModalMessageDialog.getCloseCommand());
				}
			}
		} else if (serviciosAdicionales.getTable() == sender) {
			if (col == 0 && row > 0) {
				serviciosAdicionales.agregarQuitarServicioAdicional(row);
			} else if (col == 3 && row > 0) {
				serviciosAdicionales.editarPrecioDeVentaServicioAdicional(row);
			}
		}
	}

	private void selectDetalleLineaSSRow(int row) {
		if (row > 0) {
			detalleSS.getRowFormatter().removeStyleName(selectedDetalleRow, SELECTED_ROW);
			detalleSS.getRowFormatter().addStyleName(row, SELECTED_ROW);
		}
		selectedDetalleRow = row;
		serviciosAdicionales.setServiciosAdicionalesFor(row);
	}

	private void removeDetalleLineaSSRow(int row) {
		editarSSUIData.removeLineaSolicitudServicio(row - 1);
		detalleSS.removeRow(row);
		if (selectedDetalleRow == row) {
			selectDetalleLineaSSRow(detalleSS.getRowCount() <= 1 ? 0 : 1);
		} else if (selectedDetalleRow > row) {
			selectDetalleLineaSSRow(--selectedDetalleRow);
		}
		ModalMessageDialog.getInstance().hide();
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
		// Si tiene plan (no es un accesorio por ej)
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

		onTableClick(detalleSS, firstNewRow, 2);
	}

	/** Limpia y recarga la tabla de Detalle de Solicitud de Servicio completamente */
	private void refreshDetalleSSTable() {
		while (detalleSS.getRowCount() > 1) {
			detalleSS.removeRow(1);
		}
		for (LineaSolicitudServicioDto linea : editarSSUIData.getLineasSolicitudServicio()) {
			drawDetalleSSRow(linea, editarSSUIData.getLineasSolicitudServicio().indexOf(linea) + 1);
		}
		if (!editarSSUIData.getLineasSolicitudServicio().isEmpty()) {
			onTableClick(detalleSS, 1, 2);
		} else {
			serviciosAdicionales.clear();
		}
	}

	/** Agrega una fila a la tabla de Detalle de Solicitud de Servicio en la posicion indicada */
	private void drawDetalleSSRow(LineaSolicitudServicioDto linea, int newRow) {
		detalleSS.setWidget(newRow, 0, IconFactory.lapiz());
		detalleSS.setWidget(newRow, 1, IconFactory.cancel());
		detalleSS.setHTML(newRow, 2, linea.getItem().getDescripcion());
		detalleSS.setHTML(newRow, 3, currencyFormat.format(linea.getPrecioVenta()));
		detalleSS.setHTML(newRow, 4, linea.getAlias() != null ? linea.getAlias() : "");
		detalleSS.setHTML(newRow, 5, linea.getPlan() != null ? linea.getPlan().getDescripcion() : "");
		detalleSS.setHTML(newRow, 6, linea.getPlan() != null ? currencyFormat.format(linea
				.getPrecioVentaPlan()) : "");
		detalleSS.setHTML(newRow, 7, linea.getLocalidad() != null ? linea.getLocalidad().getDescripcion()
				: "");
		detalleSS.setHTML(newRow, 8, linea.getNumeroReserva());
		detalleSS.setHTML(newRow, 9, linea.getTipoSolicitud().getDescripcion());
		detalleSS.setHTML(newRow, 10, "" + linea.getCantidad());
		detalleSS.setHTML(newRow, 11, linea.getDdn() ? IconFactory.tildeVerde().toString() : Sfa.constant()
				.whiteSpace());
		detalleSS.setHTML(newRow, 12, linea.getDdi() ? IconFactory.tildeVerde().toString() : Sfa.constant()
				.whiteSpace());
		detalleSS.setHTML(newRow, 13, linea.getRoaming() ? IconFactory.tildeVerde().toString() : Sfa
				.constant().whiteSpace());
		detalleSS.getCellFormatter().addStyleName(newRow, 3, "alignRight");
		detalleSS.getCellFormatter().addStyleName(newRow, 6, "alignRight");
	}

	public void editarPrecioDeVentaPlan() {
		LineaSolicitudServicioDto lineaSS = editarSSUIData.getLineasSolicitudServicio().get(
				selectedDetalleRow - 1);
		getPlanPrecioVentaTextBox().setText(
				NumberFormat.getDecimalFormat().format(lineaSS.getPrecioVentaPlan()));
		detalleSS.setWidget(selectedDetalleRow, 6, getPlanPrecioVentaTextBox());
		getPlanPrecioVentaTextBox().setFocus(true);
	}

	public TextBox getPlanPrecioVentaTextBox() {
		if (precioVentaPlan == null) {
			precioVentaPlan = new RegexTextBox(RegularExpressionConstants.importe);
			precioVentaPlan.addBlurHandler(new BlurHandler() {
				public void onBlur(BlurEvent blurEvent) {
					updatePrecioVentaDePlan(precioVentaPlan.getText());
				}
			});
			precioVentaPlan.setWidth("110px");
			precioVentaPlan.addKeyPressHandler(new KeyPressHandler() {
				public void onKeyPress(KeyPressEvent keyPressEvent) {
					if (KeyCodes.KEY_ENTER == keyPressEvent.getCharCode()) {
						updatePrecioVentaDePlan(precioVentaPlan.getText());
					}
				}
			});
		}
		return precioVentaPlan;
	}

	private void updatePrecioVentaDePlan(String precioVenta) {
		LineaSolicitudServicioDto lineaSS = editarSSUIData.getLineasSolicitudServicio().get(
				selectedDetalleRow - 1);
		double valor = lineaSS.getPrecioVentaPlan();
		MessageDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
		try {
			valor = NumberFormat.getDecimalFormat().parse(precioVenta);
		} catch (NumberFormatException e) {
			MessageDialog.getInstance().showAceptar("Ingrese un monto válido",
					MessageDialog.getCloseCommand());
		}
		if (valor > lineaSS.getPrecioVentaPlan()) {
			MessageDialog.getInstance().showAceptar(
					"El desvío debe ser menor o igual al precio de lista del Plan",
					MessageDialog.getCloseCommand());
			valor = lineaSS.getPrecioVentaPlan();
		}
		detalleSS.setHTML(selectedDetalleRow, 6, NumberFormat.getCurrencyFormat().format(valor));
		editarSSUIData.modificarValorPlan(selectedDetalleRow - 1, valor);
	}

	public void refresh() {
		refreshNssLayout();
		refreshDomicilioLayout();
		refreshDetalleSSTable();
	}

}
