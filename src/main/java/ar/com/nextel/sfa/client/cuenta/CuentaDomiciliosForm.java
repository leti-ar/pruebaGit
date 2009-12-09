package ar.com.nextel.sfa.client.cuenta;

import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.domicilio.DomicilioUI;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.EstadoTipoDomicilioDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.SuscriptorDto;
import ar.com.nextel.sfa.client.enums.TipoCuentaEnum;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

/**
 * @author eSalvador
 **/
public class CuentaDomiciliosForm extends Composite {

	private static CuentaDomiciliosForm instance;
	private FlowPanel mainPanel;
	private FormButtonsBar footerBar;
	private FlexTable datosTabla;
	private DomiciliosCuentaDto domicilioAEditar;
	private CuentaDto cuentaDto;
	private boolean huboCambios = false;
	private Button crearDomicilio;
	private SimplePanel crearDomicilioWrapper;

	public static CuentaDomiciliosForm getInstance() {
		if (instance == null) {
			instance = new CuentaDomiciliosForm();
		}
		return instance;
	}

	private CuentaDomiciliosForm() {
		mainPanel = new FlowPanel();
		footerBar = new FormButtonsBar();
		datosTabla = new FlexTable();
		datosTabla.setWidth("100%");
		agregaTableListeners();
		initWidget(mainPanel);
		mainPanel.setWidth("100%");
		mainPanel.addStyleName("gwt-BuscarCuentaResultTable");
		//
		crearDomicilio = new Button("Crear nuevo");
		crearDomicilio.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				DomicilioUI.getInstance().cargarListBoxEntregaFacturacion(
						cuentaDto.getPersona().getDomicilios(), null);
				DomicilioUI.getInstance().setComandoAceptar(new Command() {
					public void execute() {
						DomiciliosCuentaDto domicilio = DomicilioUI.getInstance().getDomicilioAEditar();
						cuentaDto.getPersona().getDomicilios().add(domicilio);
						refrescaTablaConNuevoDomicilio();
						huboCambios = true;
					}
				});
				DomicilioUI.getInstance().setParentContacto(false);
				DomicilioUI.getInstance().cargarPopupNuevoDomicilio(new DomiciliosCuentaDto());
			}
		});
		crearDomicilio.addStyleName("crearDomicilioButton");
		crearDomicilioWrapper = new SimplePanel();
		crearDomicilioWrapper.add(crearDomicilio);
		crearDomicilioWrapper.addStyleName("h20");
		mainPanel.add(crearDomicilioWrapper);
		//
		mainPanel.add(datosTabla);
		mainPanel.add(footerBar);
	}
	
	private void initTableCompleta(FlexTable table) {
        limpiarPrimeraFilaTabla();
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

	private void initTableOpp(FlexTable table) {
		limpiarPrimeraFilaTabla();
		String[] widths = { "5%", "95%"};
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.getColumnFormatter().addStyleName(0, "alignCenter");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarCuentaResultTable");
		table.getRowFormatter().addStyleName(0, "header");
		table.setHTML(0, 0, Sfa.constant().whiteSpace());
		table.setHTML(0, 1, Sfa.constant().domicilios());
	}
	
	
	/**
	 * @author eSalvador Refresca la grilla de domicilios
	 **/
	public void refrescaTablaConNuevoDomicilio() {
		cargaTablaDomicilios(cuentaDto);
	}

	/**
	 * @author eSalvador
	 **/
	public void cargaTablaDomicilios(final CuentaDto cuentaDto) {
		this.cuentaDto = cuentaDto;
        int col;
		List<DomiciliosCuentaDto> domicilios = cuentaDto.getPersona().getDomicilios();
		limpiaTablaDomicilios();
		crearDomicilioWrapper.setVisible(!EditarCuentaUI.edicionReadOnly);
		if (!EditarCuentaUI.edicionReadOnly || EditarCuentaUI.esEdicionCuenta) {
			initTableCompleta(datosTabla);
		} else {
			initTableOpp(datosTabla);
		}

		for (int i = 0; i < domicilios.size(); i++) {
			if (domicilios.get(i) != null) {
				// Carga los iconos:
				col = 0;
				datosTabla.setWidget(i + 1, col, IconFactory.lapiz());
				datosTabla.getCellFormatter().setAlignment(i+1, col, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);

				if (!EditarCuentaUI.edicionReadOnly || EditarCuentaUI.esEdicionCuenta) {
					datosTabla.setWidget(i + 1, ++col, IconFactory.copiar());
					datosTabla.getCellFormatter().setAlignment(i+1, col, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
					datosTabla.setWidget(i + 1, ++col, IconFactory.cancel());
					datosTabla.getCellFormatter().setAlignment(i+1, col, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
					if ((domicilios.get(i).getIdEntrega() != null)	&& (domicilios.get(i).getIdFacturacion() != null)) {
						Long idEntrega = domicilios.get(i).getIdEntrega();
						Long idFacturacion = domicilios.get(i).getIdFacturacion();
						// Entrega;
						if (idEntrega == 2) {
							datosTabla.setHTML(i + 1, ++col, "Principal");
						} else if (idEntrega == 0) {
							datosTabla.setHTML(i + 1, ++col, "Si");
						} else if (idEntrega == 1) {
							datosTabla.setHTML(i + 1, ++col, "No");
						}
						datosTabla.getCellFormatter().addStyleName(i + 1, col, "alignCenter");

						// Facturacion:
						if (idFacturacion == 2) {
							datosTabla.setHTML(i + 1, ++col, "Principal");
						} else if (idFacturacion == 0) {
							datosTabla.setHTML(i + 1, ++col, "Si");
						} else if (idFacturacion == 1) {
							datosTabla.setHTML(i + 1, ++col, "No");
						}
						datosTabla.getCellFormatter().addStyleName(i + 1, col, "alignCenter");
					}
				}
				datosTabla.setHTML(i + 1, ++col, domicilios.get(i).getDomicilios());
			}
		}
	}

	private void limpiarPrimeraFilaTabla() {
		if (datosTabla.isCellPresent(0, 0)) {
			datosTabla.removeRow(0);
		}
	}
	
	public void limpiaTablaDomicilios() {
		while (datosTabla.getRowCount() > 1) {
			datosTabla.removeRow(1);
		}
	}

	public void agregaTableListeners() {
		datosTabla.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent clickEvent) {
				Cell cell = ((HTMLTable) clickEvent.getSource()).getCellForEvent(clickEvent);
				if (cell == null) {
					return;
				}
				int row = cell.getRowIndex();
				int col = cell.getCellIndex();
				if (row != 0) {
					DomiciliosCuentaDto domicilio = null;
					if (isSuscriptor(cuentaDto)) {
						domicilio = ((SuscriptorDto) cuentaDto).getGranCuenta().getPersona().getDomicilios()
								.get(row - 1);
					} else {
						domicilio = cuentaDto.getPersona().getDomicilios().get(row - 1);
					}
					// Acciones a tomar cuando haga click en los lapices de edicion:
					if (col == 0) {

						domicilioAEditar = domicilio;
						DomicilioUI.getInstance().setDomicilioAEditar(domicilioAEditar);
						DomicilioUI.getInstance().cargarListBoxEntregaFacturacion(
								cuentaDto.getPersona().getDomicilios(), domicilioAEditar);
						DomicilioUI.getInstance().setParentContacto(false);
						DomicilioUI.getInstance().hide();
						if (domicilio.getVantiveId() != null) {
							DomicilioUI.getInstance().openPopupAdviseDialog(
									DomicilioUI.getInstance().getOpenDomicilioUICommand());
						} else {
							DomicilioUI.getInstance().setComandoAceptar(new Command() {
								public void execute() {
									PersonaDto persona = cuentaDto.getPersona();
									int index = persona.getDomicilios().indexOf(domicilioAEditar);
									persona.getDomicilios().remove(index);
									persona.getDomicilios().add(index,
											DomicilioUI.getInstance().getDomicilioAEditar());
									domicilioAEditar = DomicilioUI.getInstance().getDomicilioAEditar();
									refrescaTablaConNuevoDomicilio();
									huboCambios = true;
								}
							});
							DomicilioUI.getInstance().cargarPopupEditarDomicilio(domicilioAEditar,EditarCuentaUI.edicionReadOnly);
						}
					}
					// Acciones a tomar cuando haga click en iconos de copiado de domicilios:
					if (col == 1) {
						if (EditarCuentaUI.edicionReadOnly && EditarCuentaUI.esEdicionCuenta) {
						    MessageDialog.getInstance().showAceptar(Sfa.constant().ERR_NO_COPY(), MessageDialog.getCloseCommand());
						} else {
							domicilioAEditar = domicilio;
							DomiciliosCuentaDto domicilioCopiado = domicilioAEditar.clone();
							domicilioCopiado.setId(null);
							domicilioCopiado.setNombreUsuarioUltimaModificacion(null);
							domicilioCopiado.setFecha_ultima_modificacion(null);
							if (EstadoTipoDomicilioDto.PRINCIPAL.getId().equals(domicilioCopiado.getIdEntrega())) {
								domicilioCopiado.setIdEntrega(EstadoTipoDomicilioDto.SI.getId());
							}
							if (EstadoTipoDomicilioDto.PRINCIPAL.getId().equals(
									domicilioCopiado.getIdFacturacion())) {
								domicilioCopiado.setIdFacturacion(EstadoTipoDomicilioDto.SI.getId());
							}
							DomicilioUI.getInstance().setComandoAceptar(new Command() {
								public void execute() {
									DomiciliosCuentaDto domicilio = DomicilioUI.getInstance()
									.getDomicilioAEditar();
									cuentaDto.getPersona().getDomicilios().add(domicilio);
									refrescaTablaConNuevoDomicilio();
									huboCambios = true;
								}
							});
							DomicilioUI.getInstance().setParentContacto(false);
							DomicilioUI.getInstance().cargarListBoxEntregaFacturacion(
									cuentaDto.getPersona().getDomicilios(), domicilioCopiado);
							DomicilioUI.getInstance().cargarPopupCopiarDomicilio(domicilioCopiado);
						}
					}
					// Acciones a tomar cuando haga click en iconos de borrado de domicilios:
					if (col == 2) {
						if (EditarCuentaUI.edicionReadOnly && EditarCuentaUI.esEdicionCuenta) {
							MessageDialog.getInstance().showAceptar(Sfa.constant().ERR_NO_DELETE(), MessageDialog.getCloseCommand());
						} else {
							final int rowABorrar = row;
							DomicilioUI.getInstance().hide();
							domicilioAEditar = domicilio;
							DomicilioUI.getInstance().openPopupDeleteDialog(cuentaDto.getPersona(),
									domicilioAEditar, new Command() {
								public void execute() {
									refrescaTablaConNuevoDomicilio();
									huboCambios = true;
								}
							});
						}
					}
				}
			}
		});
	}

	boolean isGranCuenta(CuentaDto cuentaDto) {
		return cuentaDto.getCategoriaCuenta().getDescripcion().equals(TipoCuentaEnum.CTA.getTipo());
	}

	boolean isDivision(CuentaDto cuentaDto) {
		return cuentaDto.getCategoriaCuenta().getDescripcion().equals(TipoCuentaEnum.DIV.getTipo());
	}

	boolean isSuscriptor(CuentaDto cuentaDto) {
		return cuentaDto.getCategoriaCuenta().getDescripcion().equals(TipoCuentaEnum.SUS.getTipo());
	}

	public List<String> validarCompletitud() {
		return validarCompletitud(cuentaDto.getPersona().getDomicilios());
	}

	public static List<String> validarCompletitud(List<DomiciliosCuentaDto> domicilios) {
		GwtValidator validator = new GwtValidator();
		validator.clear();
		boolean hayDomicilioEntrega = false;
		boolean hayDomicilioFacturacion = false;

		if (domicilios == null || domicilios.size() < 0) {
			validator.addError(Sfa.constant().ERR_DOMICILIO_ENTREGA());
			validator.addError(Sfa.constant().ERR_DOMICILIO_FACTURACION());
		} else {
			for (DomiciliosCuentaDto domi : domicilios) {
				if ((domi.getIdEntrega() != null) && (domi.getIdFacturacion() != null)) {
					if (domi.getIdEntrega().equals(EstadoTipoDomicilioDto.PRINCIPAL.getId())) {
						hayDomicilioEntrega = new Boolean(true);
					}
					;
					if (domi.getIdFacturacion().equals(EstadoTipoDomicilioDto.PRINCIPAL.getId())) {
						hayDomicilioFacturacion = new Boolean(true);
					}
					;
				}
			}
			if (!hayDomicilioEntrega)
				validator.addError(Sfa.constant().ERR_DOMICILIO_ENTREGA());
			if (!hayDomicilioFacturacion)
				validator.addError(Sfa.constant().ERR_DOMICILIO_FACTURACION());
		}
		validator.fillResult();
		return validator.getErrors();
	}

	public boolean formularioDatosDirty() {
		return huboCambios;
	}

	public FlexTable getDatosTabla() {
		return datosTabla;
	}

	public void setDatosTabla(FlexTable datosTabla) {
		this.datosTabla = datosTabla;
	}

	public void setHuboCambios(boolean huboCambios) {
		this.huboCambios = huboCambios;
	}

	public void setDomicilioAEditar(DomiciliosCuentaDto domicilioAEditar) {
		this.domicilioAEditar = domicilioAEditar;
	}

	public Button getCrearDomicilio() {
		return crearDomicilio;
	}

	public CuentaDto getCuenta() {
		return cuentaDto;
	}

}