package ar.com.nextel.sfa.client.contacto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.cuenta.CuentaContactoForm;
import ar.com.nextel.sfa.client.cuenta.EditarCuentaUI;
import ar.com.nextel.sfa.client.domicilio.DomicilioUI;
import ar.com.nextel.sfa.client.dto.ContactoCuentaDto;
import ar.com.nextel.sfa.client.dto.DocumentoDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.SexoDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.dto.VerazResponseDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ContactosUI extends NextelDialog implements ClickListener {

	private FlexTable datosCuentaTable;
	private FlexTable telefonoTable;
	private ContactoUIData contactosData;
	private SimpleLink aceptar;
	private SimpleLink cancelar;
	private Button agregar;
	private DomicilioUI domicilioUI;
	private DomiciliosCuentaDto domicilioAEditar;
	private FlexTable domicilioTable;
	private TabPanel mainTabPanel = new TabPanel();

	private HTML iconoLupa = IconFactory.vistaPreliminar();

	private List<String> estilos = new ArrayList<String>();
	private int estiloUsado = 0;
	private Command aceptarCommand;

	ClickListener listener = new ClickListener() {
		public void onClick(Widget sender) {
			if (sender == aceptar) {
				List errors = contactosData.validarCampoObligatorio();
				errors.addAll(contactosData.validarNumeroDocumento());
				if (errors.isEmpty()) {
					aceptarCommand.execute();
					hide();
				} else {
					ErrorDialog.getInstance().show(errors, false);
				}
			} else if (sender == cancelar) {
				hide();
			} else {
				DomicilioUI.getInstance().setParentContacto(true);
				DomicilioUI.getInstance().showAndCenter();
			}
			CuentaContactoForm.getInstance().setFormDirty(!contactosData.isSaved());
		}
	};

	public ContactosUI() {
		super("Crear Contacto", false, true);
		init();
	}

	private void init() {
		setWidth("740px");
		contactosData = new ContactoUIData();
		// TabPanel mainTabPanel = new TabPanel();
		mainTabPanel.addStyleDependentName("gwt-TabPanelBottom crearCuentaTabPanel");
		mainTabPanel.setWidth("98%");
		mainTabPanel.add(createDatosCuentaPanel(), "Datos");
		mainTabPanel.add(createDomicilioPanel(), "Domicilios");
		mainTabPanel.add(createTelefonoPanel(), "Telefonos");
		mainTabPanel.selectTab(0);
		add(mainTabPanel);

		aceptar = new SimpleLink("Aceptar");
		aceptar.addClickListener(listener);

		cancelar = new SimpleLink("Cerrar");
		cancelar.addClickListener(listener);

		addFormButtons(aceptar);
		addFormButtons(cancelar);
		setFormButtonsVisible(true);
		setFooterVisible(false);
		
		estilos.add("verazAceptar");
		estilos.add("verazRevisar");
		estilos.add("verazRechazar");
		
	}

	public void cargarPopupEditarContacto(ContactoCuentaDto contacto) {
		inicializarVeraz(contactosData.getVeraz());
		contactosData.clean();
		// Limpia la tabla de domicilios
		while (domicilioTable.getRowCount() > 1) {
			domicilioTable.removeRow(1);
		}
		contactosData.setContactoDto(contacto);
		aceptar.setVisible(true);
		if (EditarCuentaUI.edicionReadOnly) {
		   contactosData.disableFields();
		} else {
		   contactosData.enableFields();
		}
		setearDomicilio();
		mainTabPanel.selectTab(0);
		showAndCenter();
	}

	private Widget createDatosCuentaPanel() {
		datosCuentaTable = new FlexTable();
		datosCuentaTable.setWidth("100%");
		datosCuentaTable.getFlexCellFormatter().setColSpan(1, 1, 4);
		datosCuentaTable.setCellSpacing(5);

		FlowPanel datosCuentaPanel = new FlowPanel();
		datosCuentaPanel.addStyleName("gwt-TabPanelBottom content");

		datosCuentaTable.setText(0, 0, Sfa.constant().tipoDocumento());
		datosCuentaTable.setWidget(0, 1, contactosData.getTipoDocumento());
		datosCuentaTable.setWidget(0, 3, new Label(Sfa.constant().numero()));
		datosCuentaTable.setWidget(0, 4, contactosData.getNumeroDocumento());
		datosCuentaTable.setWidget(2, 0, new Label(Sfa.constant().nombre()));
		datosCuentaTable.setWidget(2, 1, contactosData.getNombre());
		datosCuentaTable.setWidget(2, 3, new Label(Sfa.constant().apellido()));
		datosCuentaTable.setWidget(2, 4, contactosData.getApellido());
		datosCuentaTable.setText(3, 0, Sfa.constant().sexo());
		datosCuentaTable.setWidget(3, 1, contactosData.getSexo());
		datosCuentaTable.setText(4, 0, Sfa.constant().cargo());
		datosCuentaTable.setWidget(4, 1, contactosData.getCargo());
		FlexTable verazTable = new FlexTable();
		verazTable.setWidget(0, 0, iconoLupa);
		verazTable.setText(0, 1, Sfa.constant().veraz());
		verazTable.setWidget(0, 2, contactosData.getVeraz());

		datosCuentaPanel.add(datosCuentaTable);
		datosCuentaPanel.add(verazTable);

		iconoLupa.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if ("".equals(contactosData.getNumeroDocumento().getText())) {
					MessageDialog.getInstance();
					MessageDialog.getInstance().showAceptar("Debe ingresar un número de documento",
							MessageDialog.getCloseCommand());
				} else {
					PersonaDto personaDto = getVerazSearch(contactosData.getNumeroDocumento(), contactosData
							.getTipoDocumento(), contactosData.getSexo());
					inicializarVeraz(contactosData.getVeraz());
					CuentaRpcService.Util.getInstance().consultarVeraz(personaDto,
							new DefaultWaitCallback<VerazResponseDto>() {
								public void success(VerazResponseDto result) {
									if (result != null) {
										setearValoresRtaVeraz(result, contactosData.getApellido(),
												contactosData.getNombre(), null, contactosData.getSexo(),
												contactosData.getVeraz());
									}
								}
							});
				}
			}
		});

		return datosCuentaPanel;
	}

	public void inicializarVeraz(Label verazLabel) {
		verazLabel.setText("");
		verazLabel.removeStyleName(estilos.get(estiloUsado));
	}

	public void setearValoresRtaVeraz(VerazResponseDto result, TextBox apellido, TextBox nombre,
			TextBox razonSocial, ListBox sexo, Label veraz) {
		apellido.setText(result.getApellido());
		apellido.setEnabled(true);
		apellido.setReadOnly(false);
		nombre.setText(result.getNombre());
		nombre.setEnabled(true);
		nombre.setReadOnly(false);
		if (razonSocial != null) {
			razonSocial.setText(result.getRazonSocial());
		}
		sexo.setEnabled(true);

		if ("ACEPTAR".equals(result.getEstado())) {
			veraz.setStyleName(estilos.get(0));
			estiloUsado = 0;
		} else if ("REVISAR".equals(result.getEstado())) {
			veraz.setStyleName(estilos.get(1));
			estiloUsado = 1;
		} else {
			veraz.setStyleName(estilos.get(2));
			estiloUsado = 2;
		}

		veraz.setText(result.getEstado());
		MessageDialog.getInstance().setDialogTitle("Resultado Veraz");
		MessageDialog.getInstance().showAceptar(result.getMensaje(), MessageDialog.getCloseCommand());
	}

	private PersonaDto getVerazSearch(TextBox numDoc, ListBox tipoDoc, ListBox sexo) {
		PersonaDto personaDto = new PersonaDto();
		DocumentoDto documentoDto = new DocumentoDto(numDoc.getText(), (TipoDocumentoDto) tipoDoc
				.getSelectedItem());
		personaDto.setDocumento(documentoDto);
		personaDto.setIdTipoDocumento(documentoDto.getTipoDocumento().getId());
		personaDto.setSexo((SexoDto) sexo.getSelectedItem());
		return personaDto;
	}

	/**
	 * @author eSalvador Refresca la grilla de domicilios
	 **/
	public void refrescaTablaConNuevoDomicilio() {
		domicilioTable.clear();
		setearDomicilio();
	}

	private Widget createDomicilioPanel() {
		FlowPanel domicilioPanel = new FlowPanel();
		domicilioTable = new FlexTable();
		domicilioTable.addClickHandler(new DomicilioTableListener());
		agregar = new Button("Crear Nuevo");
		agregar.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				DomicilioUI.getInstance().setComandoAceptar(new Command() {
					public void execute() {
						DomiciliosCuentaDto domicilio = DomicilioUI.getInstance().getDomicilioAEditar();
						contactosData.getDomicilios().add(domicilio);
						refrescaTablaConNuevoDomicilio();
					}
				});
				DomicilioUI.getInstance().cargarListBoxEntregaFacturacion(null, null);
				DomicilioUI.getInstance().setParentContacto(true);
				DomicilioUI.getInstance().cargarPopupNuevoDomicilio(new DomiciliosCuentaDto());
			}
		});

		agregar.addStyleName("crearDomicilioButton");
		SimplePanel crearNuevo = new SimplePanel();

		crearNuevo.setHeight("17px");
		crearNuevo.setWidth("750");
		domicilioPanel.addStyleDependentName("gwt-TabPanelBottom content");
		crearNuevo.add(agregar);
		domicilioPanel.add(crearNuevo);

		String[] widths = { "24px", "100%" };
		for (int col = 0; col < widths.length; col++) {
			domicilioTable.getColumnFormatter().setWidth(col, widths[col]);
		}
		domicilioTable.getColumnFormatter().addStyleName(0, "alignCenter");
		domicilioTable.getColumnFormatter().addStyleName(1, "alignCenter");
		domicilioTable.setCellPadding(0);
		domicilioTable.setCellSpacing(0);
		domicilioTable.addStyleName("gwt-BuscarCuentaResultTable");
		domicilioTable.getRowFormatter().addStyleName(0, "header");
		domicilioTable.setWidth("100%");
		domicilioTable.setHTML(0, 0, Sfa.constant().whiteSpace());
		domicilioTable.setText(0, 1, Sfa.constant().domicilios());
		domicilioPanel.add(domicilioTable);

		return domicilioPanel;
	}

	public void setearDomicilio() {
		int row = 1;
		if (contactosData.getDomicilios() != null) {
			for (Iterator<DomiciliosCuentaDto> iter = contactosData.getDomicilios().iterator(); iter
					.hasNext();) {
				DomiciliosCuentaDto domicilioCuentaDto = (DomiciliosCuentaDto) iter.next();
				domicilioTable.setWidget(row, 0, IconFactory.lapiz());
				domicilioTable.setHTML(row, 1, domicilioCuentaDto.getDomicilios());
				row++;
			}
		}
	}

	private class DomicilioTableListener implements ClickHandler {
		public void onClick(ClickEvent clickEvent) {
			Cell cell = ((com.google.gwt.user.client.ui.HTMLTable) clickEvent.getSource())
					.getCellForEvent(clickEvent);
			if (cell == null) {
				return;
			}
			int fila = cell.getRowIndex();
			int columna = cell.getCellIndex();

			if (fila != 0) {
				DomiciliosCuentaDto domicilio = contactosData.getPersonaDto().getDomicilios().get(fila - 1);
				// Acciones a tomar cuando haga click en los lapices de edicion:
				if (columna == 0) {
					domicilioAEditar = domicilio;
					DomicilioUI.getInstance().setDomicilioAEditar(domicilioAEditar);
					DomicilioUI.getInstance().cargarListBoxEntregaFacturacion(
							contactosData.getPersonaDto().getDomicilios(), domicilioAEditar);
					if (domicilio.getVantiveId() != null) {
						DomicilioUI.getInstance().setParentContacto(true);
						DomicilioUI.getInstance().openPopupAdviseDialog(
								DomicilioUI.getInstance().getOpenDomicilioUICommand());
					} else {
						DomicilioUI.getInstance().setComandoAceptar(new Command() {
							public void execute() {
								PersonaDto persona = contactosData.getPersonaDto();
								int index = persona.getDomicilios().indexOf(domicilioAEditar);
								persona.getDomicilios().remove(index);
								persona.getDomicilios().add(index,
										DomicilioUI.getInstance().getDomicilioAEditar());
								domicilioAEditar = null;
								refrescaTablaConNuevoDomicilio();
							}
						});
						DomicilioUI.getInstance().setParentContacto(true);
						DomicilioUI.getInstance().cargarPopupEditarDomicilio(domicilioAEditar);
					}
				}
			}
		}
	}

	public void refrescaTablaConDomiciliosBorrados(int row) {
		domicilioTable.removeRow(row);
		setearDomicilio();
	}

	private Widget createTelefonoPanel() {
		telefonoTable = new FlexTable();
		telefonoTable.getFlexCellFormatter().setColSpan(1, 1, 4);
		telefonoTable.getFlexCellFormatter().setColSpan(2, 1, 4);
		telefonoTable.getFlexCellFormatter().setColSpan(4, 1, 4);
		telefonoTable.setWidth("100%");
		telefonoTable.setCellSpacing(6);
		FlowPanel telefonoPanel = new FlowPanel();
		telefonoPanel.addStyleDependentName("gwt-TabPanelBottom content");
		telefonoPanel.add(telefonoTable);

		telefonoTable.setWidget(0, 0, new Label(Sfa.constant().telefono()));
		telefonoTable.setText(1, 0, Sfa.constant().principal());
		telefonoTable.setWidget(1, 1, contactosData.getTelefonoPrincipal());
		telefonoTable.setText(1, 2, Sfa.constant().adicional());
		telefonoTable.setWidget(1, 3, contactosData.getTelefonoAdicional());
		telefonoTable.setText(2, 0, Sfa.constant().celular());
		telefonoTable.setWidget(2, 1, contactosData.getTelefonoCelular());
		telefonoTable.setText(2, 2, Sfa.constant().fax());
		telefonoTable.setWidget(2, 3, contactosData.getFax());
		telefonoTable.setWidget(3, 0, new Label(Sfa.constant().emailPanelTitle()));
		telefonoTable.setText(4, 0, Sfa.constant().personal());
		telefonoTable.setWidget(4, 1, contactosData.getEmailPersonal());
		telefonoTable.setText(4, 2, Sfa.constant().laboral());
		telefonoTable.setWidget(4, 3, contactosData.getEmailLaboral());

		return telefonoPanel;
	}

	public DomicilioUI getDomicilioUI() {
		return this.domicilioUI;
	}

	public void onClick(Widget arg0) {
	}

	public void setAceptarCommand(Command aceptarCommand) {
		this.aceptarCommand = aceptarCommand;
	}

	public ContactoCuentaDto getContacto() {
		return contactosData.getContactoDto();
	}

}
