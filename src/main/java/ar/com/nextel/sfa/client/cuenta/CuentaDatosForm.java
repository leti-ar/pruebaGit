package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.CargoDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaPotencialDto;
import ar.com.nextel.sfa.client.dto.DatosDebitoCuentaBancariaDto;
import ar.com.nextel.sfa.client.dto.DatosDebitoTarjetaCreditoDto;
import ar.com.nextel.sfa.client.dto.DatosEfectivoDto;
import ar.com.nextel.sfa.client.dto.DatosPagoDto;
import ar.com.nextel.sfa.client.dto.DivisionDto;
import ar.com.nextel.sfa.client.dto.DocumentoDto;
import ar.com.nextel.sfa.client.dto.EmailDto;
import ar.com.nextel.sfa.client.dto.FacturaElectronicaDto;
import ar.com.nextel.sfa.client.dto.OportunidadNegocioDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.ProveedorDto;
import ar.com.nextel.sfa.client.dto.RubroDto;
import ar.com.nextel.sfa.client.dto.SexoDto;
import ar.com.nextel.sfa.client.dto.SuscriptorDto;
import ar.com.nextel.sfa.client.dto.TarjetaCreditoValidatorResultDto;
import ar.com.nextel.sfa.client.dto.TelefonoDto;
import ar.com.nextel.sfa.client.dto.TipoContribuyenteDto;
import ar.com.nextel.sfa.client.dto.TipoCuentaBancariaDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.dto.TipoEmailDto;
import ar.com.nextel.sfa.client.dto.TipoTarjetaDto;
import ar.com.nextel.sfa.client.dto.TipoTelefonoDto;
import ar.com.nextel.sfa.client.dto.VerazResponseDto;
import ar.com.nextel.sfa.client.enums.EstadoOportunidadEnum;
import ar.com.nextel.sfa.client.enums.SexoEnum;
import ar.com.nextel.sfa.client.enums.TipoCuentaEnum;
import ar.com.nextel.sfa.client.enums.TipoDocumentoEnum;
import ar.com.nextel.sfa.client.enums.TipoEmailEnum;
import ar.com.nextel.sfa.client.enums.TipoFormaPagoEnum;
import ar.com.nextel.sfa.client.enums.TipoTarjetaEnum;
import ar.com.nextel.sfa.client.enums.TipoTelefonoEnum;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.util.FormUtils;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.DualPanel;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.TitledPanel;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;
import ar.com.snoop.gwt.commons.client.window.MessageWindow;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CuentaDatosForm extends Composite {

	private static CuentaDatosForm instance;

	private DualPanel fechaUsuarioTable;
	private FlexTable usuario;
	private FlexTable fechaCreacion;
	private FlexTable mainPanel = new FlexTable();
	private FlexTable datosCuentaTable = new FlexTable();
	private FlexTable datosOppTable = new FlexTable();
	private FlexTable emailTable = new FlexTable();
	private FlexTable telefonoTable = new FlexTable();
	private FlexTable formaDePagoTable = new FlexTable();
	private FlexTable vendedorTable = new FlexTable();
	private FlexTable efectivoTable = new FlexTable();
	private FlexTable cuentaBancariaTable = new FlexTable();
	private FlexTable tarjetaTable = new FlexTable();

	private FacturaElectronicaDto facturaElectronicaOriginal;
	
	private TitledPanel datosCuentaPanel = new TitledPanel(Sfa.constant().cuentaPanelTitle());
	private TitledPanel datosOppPanel = new TitledPanel(Sfa.constant().cuentaPanelTitle());
	private TitledPanel formaDePagoPanel = new TitledPanel(Sfa.constant().formaDePagoPanelTitle());;
	private TitledPanel vendedorPanel = new TitledPanel(Sfa.constant().vendedorPanelTitle());

	private FacturaElectronicaUI facturaElectronicaUI = new FacturaElectronicaUI();
	private Command aceptarFacturaElectronica;
	private HTML iconoLupa = IconFactory.vistaPreliminar();
	private HTML iconoEditarEstdo = IconFactory.lapiz();
	private CuentaPotencialDto oportunidadDto;
	private CuentaUIData cuentaUIData;
	private DatosPagoDto datosPago;
	private List<Widget> camposObligatorios = new ArrayList<Widget>();
	private boolean showPanelDatosCuenta = true;
	private boolean showCamposUSE = false;
	private static final String ANCHO_PRIMER_COLUMNA = "11%";
	private static final String ANCHO_TERCER_COLUMNA = "6%";
	private static final String ANCHO_TABLA_PANEL = "80%";

	private static int SCORE_DNI_INEXISTENTE = 3;

	private static final DateTimeFormat DATE_TIME_FORMAT = DateTimeFormat.getMediumDateFormat();

	private List<String> estilos = new ArrayList<String>();
	private int estiloUsado = 0;

	public static CuentaDatosForm getInstance() {
		if (instance == null) {
			instance = new CuentaDatosForm();
		}
		return instance;
	}

	private CuentaDatosForm() {
		cuentaUIData = new CuentaUIData();
		initWidget(mainPanel);
		iconoEditarEstdo.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				CambioEstadoOppForm cambioEstadoForm = CambioEstadoOppForm.getInstance();
				cambioEstadoForm.setSize("780", "350");
				cambioEstadoForm.cargarPopup();
			}
		});

		int fila = 0;
		mainPanel.setWidth("100%");
		mainPanel.setWidget(fila++, 0, createDatosCuentaPanel());
		mainPanel.setWidget(fila++, 0, createDatosOppPanel());
		mainPanel.setWidget(fila++, 0, createTelefonoPanel());
		mainPanel.setWidget(fila++, 0, createEmailPanel());
		mainPanel.setWidget(fila++, 0, createFormaDePagoPanel());
		mainPanel.setWidget(fila++, 0, createVendedorPanel());
		mainPanel.setWidget(fila, 0, createFechaUsuarioPanel());

		iconoLupa.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if ("".equals(cuentaUIData.getNumeroDocumento().getText())) {
					MessageDialog.getInstance().showAceptar(Sfa.constant().ERR_DIALOG_TITLE(),
							"Debe ingresar un número de documento", MessageDialog.getCloseCommand());
				} else {
					PersonaDto personaDto = getVerazSearch(cuentaUIData.getNumeroDocumento(), cuentaUIData
							.getTipoDocumento(), cuentaUIData.getSexo());
					inicializarVeraz(cuentaUIData.getVerazRta());
					CuentaRpcService.Util.getInstance().consultarVeraz(personaDto,
							new DefaultWaitCallback<VerazResponseDto>() {

								public void success(VerazResponseDto result) {
									if (result != null) {
										setearValoresRtaVeraz(result, cuentaUIData.getApellido(),
												cuentaUIData.getNombre(), cuentaUIData.getRazonSocial(),
												cuentaUIData.getSexo(), cuentaUIData.getVerazRta());
										cuentaUIData.exportarNombreApellidoARazonSocial();
									}
								}
							});
				}
			}
		});
	}

	private Widget createDatosCuentaPanel() {
		armarTablaPanelDatos();
		datosCuentaPanel.add(datosCuentaTable);
		return datosCuentaPanel;
	}

	private Widget createDatosOppPanel() {
		armarTablaPanelOppDatos();
		datosOppPanel.add(datosOppTable);
		return datosOppPanel;
	}

	public void armarTablaPanelDatos() {
		datosCuentaTable.clear();
		datosCuentaTable.setCellSpacing(7);
		datosCuentaTable.setWidth(ANCHO_TABLA_PANEL);
		datosCuentaTable.getFlexCellFormatter().setColSpan(1, 1, 4);
		// datosCuentaTable.setBorderWidth(1);

		int row = 0;
		datosCuentaTable.setWidget(row, 0, cuentaUIData.getTipoDocLabel());
		datosCuentaTable.setWidget(row, 1, cuentaUIData.getTipoDocumento());
		datosCuentaTable.setWidget(row, 3, cuentaUIData.getNumDocLabel());
		datosCuentaTable.setWidget(row, 4, cuentaUIData.getNumeroDocumento());
		datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		row++;
		datosCuentaTable.setWidget(row, 0, cuentaUIData.getRazSocLabel());
		datosCuentaTable.setWidget(row, 1, cuentaUIData.getRazonSocial());
		datosCuentaTable.getFlexCellFormatter().setColSpan(row, 1, 4);
		datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		row++;
		if (cuentaUIData.getNomDivLabel().isVisible()) {
			datosCuentaTable.setWidget(row, 0, cuentaUIData.getNomDivLabel());
			datosCuentaTable.setWidget(row, 1, cuentaUIData.getNombreDivision());
			datosCuentaTable.getFlexCellFormatter().setColSpan(row, 1, 4);
			datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
			row++;
		} else {
			datosCuentaTable.removeRow(row);
		}
		datosCuentaTable.setWidget(row, 0, cuentaUIData.getNombreLabel());
		datosCuentaTable.setWidget(row, 1, cuentaUIData.getNombre());
		datosCuentaTable.setWidget(row, 3, cuentaUIData.getApellidoLabel());
		datosCuentaTable.setWidget(row, 4, cuentaUIData.getApellido());
		datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		row++;
		datosCuentaTable.setWidget(row, 0, cuentaUIData.getSexoLabel());
		datosCuentaTable.setWidget(row, 1, cuentaUIData.getSexo());
		datosCuentaTable.setWidget(row, 3, cuentaUIData.getFecNacLabel());
		datosCuentaTable.setWidget(row, 4, cuentaUIData.getFechaNacimientoGrid());
		datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		row++;
		datosCuentaTable.setWidget(row, 0, cuentaUIData.getContrLabel());
		datosCuentaTable.setWidget(row, 1, cuentaUIData.getContribuyente());
		datosCuentaTable.setWidget(row, 3, null);
		datosCuentaTable.setWidget(row, 4, null);
		datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		row++;
		if (cuentaUIData.getCargoLabel().isVisible()) {
			datosCuentaTable.setWidget(row, 0, cuentaUIData.getCargoLabel());
			datosCuentaTable.setWidget(row, 1, cuentaUIData.getCargo());
			datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
			row++;
		} else {
			datosCuentaTable.removeRow(row);
		}
		if (cuentaUIData.getIibbLabel().isVisible()) {
			datosCuentaTable.setWidget(row, 0, cuentaUIData.getIibbLabel());
			datosCuentaTable.setWidget(row, 1, cuentaUIData.getIibb());
			datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
			row++;
		} else {
			datosCuentaTable.removeRow(row);
		}
		datosCuentaTable.setWidget(row, 0, cuentaUIData.getProvAntLabel());
		datosCuentaTable.setWidget(row, 1, cuentaUIData.getProveedorAnterior());
		datosCuentaTable.setWidget(row, 3, cuentaUIData.getRubroLabel());
		datosCuentaTable.setWidget(row, 4, cuentaUIData.getRubro());
		datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		row++;
		datosCuentaTable.setWidget(row, 0, cuentaUIData.getClaseClLabel());
		datosCuentaTable.setWidget(row, 1, cuentaUIData.getClaseCliente());
		datosCuentaTable.setWidget(row, 3, cuentaUIData.getCategLabel());
		datosCuentaTable.setWidget(row, 4, cuentaUIData.getCategoria());
		datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		row++;
		datosCuentaTable.setWidget(row, 0, cuentaUIData.getCicloFacLabel());
		datosCuentaTable.setWidget(row, 1, cuentaUIData.getCicloFacturacion());
		datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);

		datosCuentaTable.setWidget(row, 2, iconoLupa);

		datosCuentaTable.setWidget(row, 3, cuentaUIData.getVerazLabel());
		inicializarVeraz(cuentaUIData.getVerazRta());
		datosCuentaTable.setWidget(row, 4, cuentaUIData.getVerazRta());
		if (showCamposUSE) {
			row++;
			datosCuentaTable.setWidget(row, 0, cuentaUIData.getUseLabel());
			datosCuentaTable.setWidget(row, 1, cuentaUIData.getUse());
			datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		}
	}

	public void armarTablaPanelOppDatos() {
		datosOppTable.clear();
		datosOppTable.setCellSpacing(7);
		datosOppTable.setWidth(ANCHO_TABLA_PANEL);
		datosOppTable.getFlexCellFormatter().setColSpan(1, 1, 4);

		int row = 0;
		datosOppTable.setWidget(row, 0, null);
		datosOppTable.setWidget(row, 1, null);
		datosOppTable.setWidget(row, 2, null);
		datosOppTable.setWidget(row, 3, null);
		row++;
		datosOppTable.setWidget(row, 0, cuentaUIData.getRazSocLabel());
		datosOppTable.setWidget(row, 1, cuentaUIData.getRazonSocial());
		datosOppTable.getFlexCellFormatter().setColSpan(row, 1, 3);
		datosOppTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		row++;
		datosOppTable.setWidget(row, 0, cuentaUIData.getNombreLabel());
		datosOppTable.setWidget(row, 1, cuentaUIData.getNombre());
		datosOppTable.setWidget(row, 2, cuentaUIData.getApellidoLabel());
		datosOppTable.setWidget(row, 3, cuentaUIData.getApellido());
		datosOppTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		datosOppTable.getFlexCellFormatter().setWidth(row, 2, ANCHO_TERCER_COLUMNA);
		row++;
		datosOppTable.setWidget(row, 0, cuentaUIData.getTipoDocLabel());
		datosOppTable.setWidget(row, 1, cuentaUIData.getOppTipoDocumento());
		datosOppTable.setWidget(row, 2, cuentaUIData.getNumDocLabel());
		datosOppTable.setWidget(row, 3, cuentaUIData.getNumeroDocumento());
		datosOppTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		datosOppTable.getFlexCellFormatter().setWidth(row, 2, ANCHO_TERCER_COLUMNA);
		row++;
		FlexTable tabla = new FlexTable();
		tabla.setWidth("50%");
		tabla.setWidget(0, 0, cuentaUIData.getOppEstado());
		tabla.setWidget(0, 1, iconoEditarEstdo);
		datosOppTable.setWidget(row, 0, cuentaUIData.getOppVencimientoLabel());
		datosOppTable.setWidget(row, 1, cuentaUIData.getOppVencimiento());
		datosOppTable.setWidget(row, 2, cuentaUIData.getOppEstadoLabel());
		datosOppTable.setWidget(row, 3, tabla);
		datosOppTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		datosOppTable.getFlexCellFormatter().setWidth(row, 2, ANCHO_TERCER_COLUMNA);
		row++;
		datosOppTable.setWidget(row, 0, cuentaUIData.getRubroLabel());
		datosOppTable.setWidget(row, 1, cuentaUIData.getOppRubro());
		datosOppTable.setWidget(row, 2, cuentaUIData.getOppCompetenciaProvLabel());
		datosOppTable.setWidget(row, 3, cuentaUIData.getOppCompetenciaProv());
		datosOppTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		datosOppTable.getFlexCellFormatter().setWidth(row, 2, ANCHO_TERCER_COLUMNA);
		row++;
		datosOppTable.setWidget(row, 0, cuentaUIData.getOppCompetenciaEquipoLabel());
		datosOppTable.setWidget(row, 1, cuentaUIData.getOppCompetenciaEquipo());
		datosOppTable.setWidget(row, 2, cuentaUIData.getOppTerminalesEstimadasLabel());
		datosOppTable.setWidget(row, 3, cuentaUIData.getOppTerminalesEstimadas());
		datosOppTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		datosOppTable.getFlexCellFormatter().setWidth(row, 2, "8%"/* ANCHO_TERCER_COLUMNA */);
		row++;
		datosOppTable.setWidget(row, 0, cuentaUIData.getOppVisitasLabel());
		datosOppTable.setWidget(row, 1, cuentaUIData.getOppVisitas());
		datosOppTable.setWidget(row, 2, null);
		datosOppTable.setWidget(row, 3, null);
		datosOppTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);

	}

	public void inicializarVeraz(Label verazLabel) {
		estilos.add("verazAceptar");
		estilos.add("verazRevisar");
		estilos.add("verazRechazar");
		verazLabel.setText("");
		verazLabel.removeStyleName(estilos.get(estiloUsado));
	}

	public PersonaDto getVerazSearch(TextBox numDoc, ListBox tipoDoc, ListBox sexo) {
		PersonaDto personaDto = new PersonaDto();
		DocumentoDto documentoDto = new DocumentoDto(numDoc.getText(), (TipoDocumentoDto) tipoDoc
				.getSelectedItem());
		personaDto.setDocumento(documentoDto);
		personaDto.setIdTipoDocumento(documentoDto.getTipoDocumento().getId());
		personaDto.setSexo((SexoDto) sexo.getSelectedItem());
		return personaDto;
	}

	private Widget createTelefonoPanel() {
		telefonoTable.setWidth(ANCHO_TABLA_PANEL);
		telefonoTable.addStyleName("layout");
		TitledPanel telefonoPanel = new TitledPanel(Sfa.constant().telefonoPanelTitle());
		telefonoPanel.add(telefonoTable);
		cuentaUIData.getObservaciones().addStyleName("obsTextAreaCuentaDatos");

		telefonoTable.setWidget(0, 0, cuentaUIData.getPrincLabel());
		telefonoTable.setWidget(0, 1, cuentaUIData.getTelPrincipalTextBox());
		telefonoTable.setText(0, 2, Sfa.constant().adicional());
		telefonoTable.setWidget(0, 3, cuentaUIData.getTelAdicionalTextBox());
		telefonoTable.setText(1, 0, Sfa.constant().celular());
		telefonoTable.setWidget(1, 1, cuentaUIData.getTelCelularTextBox());
		telefonoTable.setText(1, 2, Sfa.constant().fax());
		telefonoTable.setWidget(1, 3, cuentaUIData.getTelFaxTextBox());
		telefonoTable.setWidget(2, 0, cuentaUIData.getObservLabel());
		telefonoTable.setWidget(2, 1, cuentaUIData.getObservaciones());
		telefonoTable.getFlexCellFormatter().setColSpan(2, 1, 3);
		telefonoTable.getFlexCellFormatter().setWidth(0, 0, ANCHO_PRIMER_COLUMNA);
		telefonoTable.getFlexCellFormatter().setWidth(1, 0, ANCHO_PRIMER_COLUMNA);
		telefonoTable.getFlexCellFormatter().setWidth(2, 0, ANCHO_PRIMER_COLUMNA);
		return telefonoPanel;
	}

	private Widget createEmailPanel() {
		emailTable.setWidth(ANCHO_TABLA_PANEL);
		emailTable.addStyleName("layout");
		TitledPanel emailPanel = new TitledPanel(Sfa.constant().emailPanelTitle());
		emailPanel.add(emailTable);

		emailTable.setText(0, 0, Sfa.constant().personal());
		emailTable.setWidget(0, 1, cuentaUIData.getEmailPersonal());
		emailTable.setText(0, 2, Sfa.constant().laboral());
		emailTable.setWidget(0, 3, cuentaUIData.getEmailLaboral());
		emailTable.setText(0, 4, Sfa.constant().facturaElectronica());
		emailTable.setWidget(0, 5, cuentaUIData.getEmailFacturaElectronica());
		emailTable.getFlexCellFormatter().setWidth(0, 0, ANCHO_PRIMER_COLUMNA);

		return emailPanel;
	}

	private Widget createFormaDePagoPanel() {
		formaDePagoTable = new FlexTable();
		formaDePagoTable.setWidth(ANCHO_TABLA_PANEL);
		formaDePagoTable.setWidget(0, 0, getCuentaBancariaPanel());
		formaDePagoTable.setWidget(1, 0, getTarjetaCreditoPanel());
		formaDePagoTable.setWidget(2, 0, getEfectivoPanel());
		formaDePagoTable.setWidget(3, 0, getFacturaElectronicaLink());

		formaDePagoPanel.add(formaDePagoTable);
		return formaDePagoPanel;
	}

	public FlexTable getEfectivoPanel() {
		efectivoTable.setWidth("100%");
		efectivoTable.addStyleName("layout");
		efectivoTable.setText(0, 0, Sfa.constant().modalidad());
		efectivoTable.setWidget(0, 1, cuentaUIData.getFormaPago());
		efectivoTable.getFlexCellFormatter().setHorizontalAlignment(0, 0, HorizontalPanel.ALIGN_LEFT);
		efectivoTable.getFlexCellFormatter().setHorizontalAlignment(0, 1, HorizontalPanel.ALIGN_LEFT);
		efectivoTable.getFlexCellFormatter().setWidth(0, 0, ANCHO_PRIMER_COLUMNA);
		return efectivoTable;
	}

	public Widget getFacturaElectronicaLink() {
		Button facturaElectronicaLink = new Button("Factura Electrónica");
		facturaElectronicaLink.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				facturaElectronicaUI.setOnAceptar(getAceptarFacturaelectronica());
				facturaElectronicaUI.show();
				facturaElectronicaUI.center();
			}
		});
		return facturaElectronicaLink;
	}
	
	public Command getAceptarFacturaelectronica(){
		if(aceptarFacturaElectronica == null){
			aceptarFacturaElectronica = new Command() {
				public void execute() {
					if (facturaElectronicaUI.getFacturaElectronicaHabilitada()) {
						if (cuentaUIData.getFacturaElectronica() == null) {
							cuentaUIData.setFacturaElectronica(new FacturaElectronicaDto());
						}
						cuentaUIData.getFacturaElectronica().setEmail(facturaElectronicaUI.getEmail());
					} else {
						cuentaUIData.setFacturaElectronica(null);
					}
				}
			};
		}
		return aceptarFacturaElectronica;
	}

	public FlexTable getCuentaBancariaPanel() {
		cuentaBancariaTable.setWidth("100%");
		cuentaBancariaTable.addStyleName("layout");
		cuentaBancariaTable.setVisible(false);

		cuentaBancariaTable.setText(0, 0, Sfa.constant().modalidad());
		cuentaBancariaTable.setWidget(0, 1, cuentaUIData.getFormaPago());
		cuentaBancariaTable.setText(0, 3, Sfa.constant().tipoCuenta());
		cuentaBancariaTable.setWidget(0, 4, cuentaUIData.getTipoCuentaBancaria());

		cuentaBancariaTable.setText(1, 0, Sfa.constant().cbu());
		cuentaBancariaTable.setWidget(1, 1, cuentaUIData.getCbu());
		cuentaBancariaTable.getFlexCellFormatter().setColSpan(1, 1, 4);
		cuentaBancariaTable.getFlexCellFormatter().addStyleName(1, 0, "req");

		cuentaBancariaTable.getFlexCellFormatter().setHorizontalAlignment(0, 0, HorizontalPanel.ALIGN_LEFT);
		cuentaBancariaTable.getFlexCellFormatter().setHorizontalAlignment(0, 1, HorizontalPanel.ALIGN_LEFT);
		cuentaBancariaTable.getFlexCellFormatter().setHorizontalAlignment(0, 3, HorizontalPanel.ALIGN_LEFT);
		cuentaBancariaTable.getFlexCellFormatter().setHorizontalAlignment(0, 4, HorizontalPanel.ALIGN_LEFT);
		cuentaBancariaTable.getFlexCellFormatter().setHorizontalAlignment(1, 0, HorizontalPanel.ALIGN_LEFT);
		cuentaBancariaTable.getFlexCellFormatter().setHorizontalAlignment(1, 1, HorizontalPanel.ALIGN_LEFT);
		cuentaBancariaTable.getFlexCellFormatter().setWidth(0, 0, ANCHO_PRIMER_COLUMNA);
		cuentaBancariaTable.getFlexCellFormatter().setWidth(1, 0, ANCHO_PRIMER_COLUMNA);

		return cuentaBancariaTable;
	}

	public FlexTable getTarjetaCreditoPanel() {
		tarjetaTable.setWidth("100%");
		tarjetaTable.addStyleName("layout");
		tarjetaTable.setVisible(false);

		tarjetaTable.setText(0, 0, Sfa.constant().modalidad());
		tarjetaTable.setWidget(0, 1, cuentaUIData.getFormaPago());
		tarjetaTable.setText(0, 3, Sfa.constant().tarjetaTipo());
		tarjetaTable.setWidget(0, 4, cuentaUIData.getTipoTarjeta());

		tarjetaTable.setText(1, 0, Sfa.constant().nroTarjeta());
		tarjetaTable.setWidget(1, 1, cuentaUIData.getNumeroTarjeta());
		tarjetaTable.setText(1, 3, Sfa.constant().vtoMes());
		tarjetaTable.setWidget(1, 4, cuentaUIData.getMesVto());

		tarjetaTable.setText(2, 0, Sfa.constant().vtoAnio());
		tarjetaTable.setWidget(2, 1, cuentaUIData.getAnioVto());
		tarjetaTable.setWidget(2, 3, null /* camposTabDatos.getValidarTarjeta() */);
		tarjetaTable.setWidget(2, 4, null);

		tarjetaTable.getFlexCellFormatter().addStyleName(1, 0, "req");
		tarjetaTable.getFlexCellFormatter().addStyleName(2, 0, "req");

		tarjetaTable.getFlexCellFormatter().setWidth(0, 0, ANCHO_PRIMER_COLUMNA);
		tarjetaTable.getFlexCellFormatter().setWidth(1, 0, ANCHO_PRIMER_COLUMNA);

		return tarjetaTable;
	}

	private Widget createVendedorPanel() {
		vendedorTable = new FlexTable();
		vendedorTable.setWidth(ANCHO_TABLA_PANEL);
		vendedorTable.addStyleName("layout");

		vendedorPanel.add(vendedorTable);

		vendedorTable.setText(0, 0, Sfa.constant().vendedorNombre());
		vendedorTable.setWidget(0, 1, cuentaUIData.getVendedorNombre());
		vendedorTable.setText(0, 2, null);
		vendedorTable.setWidget(0, 3, null);

		vendedorTable.setText(1, 0, Sfa.constant().telefono());
		vendedorTable.setWidget(1, 1, cuentaUIData.getVendedorTelefono());
		vendedorTable.setText(1, 2, Sfa.constant().canalVentas());
		vendedorTable.setWidget(1, 3, cuentaUIData.getTipoCanalVentas());

		vendedorTable.getFlexCellFormatter().setWidth(0, 0, ANCHO_PRIMER_COLUMNA);
		vendedorTable.getFlexCellFormatter().setWidth(1, 0, ANCHO_PRIMER_COLUMNA);
		return vendedorPanel;
	}

	private Widget createFechaUsuarioPanel() {
		fechaUsuarioTable = new DualPanel();
		usuario = new FlexTable();
		fechaCreacion = new FlexTable();
		usuario.addStyleName("layout");
		fechaCreacion.addStyleName("layout");
		usuario.setText(0, 0, Sfa.constant().usuario());
		usuario.setWidget(0, 1, cuentaUIData.getUsuario());
		fechaCreacion.setText(0, 0, Sfa.constant().fechaCreacion());
		fechaCreacion.setWidget(0, 1, cuentaUIData.getFechaCreacion());

		fechaUsuarioTable.setLeft(usuario);
		fechaUsuarioTable.setRight(fechaCreacion);

		return fechaUsuarioTable;
	}

	public void reset() {
		cuentaUIData.clean();
	}

	public void ponerDatosSuscriptorBusquedaEnFormulario(SuscriptorDto cuentaSuscriptorDto) {
		CuentaDto cuentaSusc = cuentaSuscriptorDto.getGranCuenta();
		cargarPanelDatos(cuentaSusc);
		cargarPanelTelefonoFax(cuentaSusc);
		cargarPanelEmails(cuentaSusc);
		cargarPanelFormaPago(cuentaSusc);
		cargarPanelVendedor(cuentaSusc);
		cargarPanelUsuario(cuentaSusc);
	}

	public void ponerDatosBusquedaEnFormulario(CuentaDto cuentaDto) {
		if (showPanelDatosCuenta)
			cargarPanelDatos(cuentaDto);
		cargarPanelTelefonoFax(cuentaDto);
		cargarPanelEmails(cuentaDto);
		cargarPanelFormaPago(cuentaDto);
		cargarPanelVendedor(cuentaDto);
		cargarPanelUsuario(cuentaDto);
	}

	public void ponerDatosOportunidadEnFormulario(CuentaPotencialDto oportunidadDto) {
		cargarPanelDatosOportunidad(oportunidadDto);
		cargarPanelTelefonoFax(oportunidadDto.getCuentaOrigen());
		cargarPanelEmails(oportunidadDto.getCuentaOrigen());
	}

	public void cargarPanelDatos(CuentaDto cuentaDto) {
		try {
			cuentaUIData.getTipoDocumento().setSelectedItem(
					cuentaDto.getPersona().getDocumento().tipoDocumento);
			cuentaUIData.getNumeroDocumento().setText(cuentaDto.getPersona().getDocumento().getNumero());
			setDefaultComboSexo(cuentaDto.getPersona().getIdTipoDocumento(), cuentaDto.getPersona()
					.getDocumento().getNumero());
		} catch (Exception e) {
			// la cuenta puede llegar sin datos de documento... en ese caso que no haga nada.
		}
		cuentaUIData.getRazonSocial().setText(cuentaDto.getPersona().getRazonSocial());
		cuentaUIData.getNombre().setText(cuentaDto.getPersona().getNombre());
		cuentaUIData.getApellido().setText(cuentaDto.getPersona().getApellido());
		cuentaUIData.getSexo().setSelectedItem(cuentaDto.getPersona().getSexo());
		cuentaUIData.getRubro().setSelectedItem(cuentaDto.getRubro());
		if (cuentaDto.getPersona().getFechaNacimiento() != null)
			cuentaUIData.getFechaNacimiento().setSelectedDate(
					DATE_TIME_FORMAT.parse(cuentaDto.getPersona().getFechaNacimiento()));
		cuentaUIData.getContribuyente().setSelectedItem(cuentaDto.getTipoContribuyente());
		cuentaUIData.getCargo().setSelectedItem(cuentaDto.getPersona().getCargo());
		cuentaUIData.getIibb().setText(cuentaDto.getIibb());
		if (cuentaDto.getCategoriaCuenta().getDescripcion().equals(TipoCuentaEnum.DIV.getTipo())) {
			cuentaUIData.getNombreDivision().setText(((DivisionDto) cuentaDto).getNombre());
		}
		cuentaUIData.getProveedorAnterior().setSelectedItem(cuentaDto.getProveedorInicial());
		cuentaUIData.getCategoria()
				.setText(
						cuentaDto.getCategoriaCuenta() != null ? cuentaDto.getCategoriaCuenta()
								.getDescripcion() : "");
		cuentaUIData.getClaseCliente().setSelectedItem(cuentaDto.getClaseCuenta());
		cuentaUIData.getCicloFacturacion().setText(
				cuentaDto.getCicloFacturacion() != null ? cuentaDto.getCicloFacturacion().getDescripcion()
						: "");
		cuentaUIData.getUse().setText(cuentaDto.getUse());
	}

	public void cargarPanelDatosOportunidad(final CuentaPotencialDto oportunidadDto) {
		this.oportunidadDto = oportunidadDto;
		cuentaUIData.getRazonSocial().setText(oportunidadDto.getPersona().getRazonSocial());
		cuentaUIData.getNombre().setText(oportunidadDto.getPersona().getNombre());
		cuentaUIData.getApellido().setText(oportunidadDto.getPersona().getApellido());
		cuentaUIData.getOppTipoDocumento().setText(
				oportunidadDto.getPersona().getDocumento() != null ? oportunidadDto.getPersona()
						.getDocumento().tipoDocumento.getDescripcion() : "");
		cuentaUIData.getNumeroDocumento().setText(
				oportunidadDto.getPersona().getDocumento() != null ? oportunidadDto.getPersona()
						.getDocumento().getNumero() : "");
		cuentaUIData.getOppVencimiento().setText(
				oportunidadDto.getFechaVencimiento() != null ? DateTimeFormat.getMediumDateFormat().format(
						oportunidadDto.getFechaVencimiento()) : "");
		cuentaUIData.getOppRubro().setText(
				oportunidadDto.getRubro() != null ? oportunidadDto.getRubro().getDescripcion() : "");
		cuentaUIData.getOppTerminalesEstimadas().setText(
				oportunidadDto.getTerminalesEstimadas() != null ? oportunidadDto.getTerminalesEstimadas()
						.toString() : "");
		// popup motivo no cerrado
		cuentaUIData.getOppNroOpp().setText(oportunidadDto.getNumero());
		if (!oportunidadDto.isEsReserva()) {
			cuentaUIData.getEstadoOpp().setSelectedItem(
					((OportunidadNegocioDto) oportunidadDto).getEstadoJustificado().getEstado());
			DeferredCommand.addCommand(new IncrementalCommand() {
				public boolean execute() {
					if (cuentaUIData.getMotivosNoCierre() == null
							|| cuentaUIData.getMotivosNoCierre().isEmpty()) {
						return true;
					}
					cuentaUIData.getRadioGroupMotivos()
							.setValueChecked(
									((OportunidadNegocioDto) oportunidadDto).getEstadoJustificado()
											.getMotivo() != null ? ((OportunidadNegocioDto) oportunidadDto)
											.getEstadoJustificado().getMotivo().getId().toString() : "1");
					return false;
				}
			});
			cuentaUIData.getOppObservaciones().setText(
					((OportunidadNegocioDto) oportunidadDto).getEstadoJustificado().getObservacionesMotivo());
			cuentaUIData
					.getOppVisitas()
					.setText(
							((OportunidadNegocioDto) oportunidadDto).getCantidadVisitas() != null ? ((OportunidadNegocioDto) oportunidadDto)
									.getCantidadVisitas().toString()
									: "");
			cuentaUIData
					.getOppEstado()
					.setText(
							((OportunidadNegocioDto) oportunidadDto).getEstadoJustificado() != null ? ((OportunidadNegocioDto) oportunidadDto)
									.getEstadoJustificado().getEstado().getDescripcion()
									: "");
			cuentaUIData
					.getOppCompetenciaEquipo()
					.setText(
							((OportunidadNegocioDto) oportunidadDto).getCantidadEquiposCompetencia() != null ? ((OportunidadNegocioDto) oportunidadDto)
									.getCantidadEquiposCompetencia().toString()
									: "");
			cuentaUIData
					.getOppCompetenciaProv()
					.setText(
							((OportunidadNegocioDto) oportunidadDto).getProveedorCompetencia() != null ? ((OportunidadNegocioDto) oportunidadDto)
									.getProveedorCompetencia().getDescripcion()
									: "");
			CambioEstadoOppForm.getInstance().showHideTablaMotivos(
					EstadoOportunidadEnum.NO_CERRADA.getId().equals(
							((OportunidadNegocioDto) oportunidadDto).getEstadoJustificado().getEstado()
									.getId()));
		}
	}

	public void cargarPanelTelefonoFax(CuentaDto cuentaDto) {
		for (TelefonoDto telefono : cuentaDto.getPersona().getTelefonos()) {
			TipoTelefonoDto tipoTelefono = telefono.getTipoTelefono();
			if (tipoTelefono.getId() == TipoTelefonoEnum.PRINCIPAL.getTipo()) {
				cuentaUIData.getTelPrincipalTextBox().getArea().setText(telefono.getArea());
				cuentaUIData.getTelPrincipalTextBox().getNumero().setText(telefono.getNumeroLocal());
				cuentaUIData.getTelPrincipalTextBox().getInterno().setText(telefono.getInterno());
			}
			if (tipoTelefono.getId() == TipoTelefonoEnum.PARTICULAR.getTipo()
					|| tipoTelefono.getId() == TipoTelefonoEnum.ADICIONAL.getTipo()) {
				cuentaUIData.getTelAdicionalTextBox().getArea().setText(telefono.getArea());
				cuentaUIData.getTelAdicionalTextBox().getNumero().setText(telefono.getNumeroLocal());
				cuentaUIData.getTelAdicionalTextBox().getInterno().setText(telefono.getInterno());
			}
			if (tipoTelefono.getId() == TipoTelefonoEnum.CELULAR.getTipo()) {
				cuentaUIData.getTelCelularTextBox().getArea().setText(telefono.getArea());
				cuentaUIData.getTelCelularTextBox().getNumero().setText(telefono.getNumeroLocal());
			}
			if (tipoTelefono.getId() == TipoTelefonoEnum.FAX.getTipo()) {
				cuentaUIData.getTelFaxTextBox().getArea().setText(telefono.getArea());
				cuentaUIData.getTelFaxTextBox().getNumero().setText(telefono.getNumeroLocal());
				cuentaUIData.getTelFaxTextBox().getInterno().setText(telefono.getInterno());
			}
		}
		cuentaUIData.getObservaciones().setText(cuentaDto.getObservacionesTelMail());
	}

	public void cargarPanelEmails(CuentaDto cuentaDto) {
		for (EmailDto email : cuentaDto.getPersona().getEmails()) {
			TipoEmailDto tipoEmail = email.getTipoEmail();
			if (TipoEmailEnum.PERSONAL.getTipo().equals(tipoEmail.getId())) {
				cuentaUIData.getEmailPersonal().setText(email.getEmail());
			}
			if (TipoEmailEnum.LABORAL.getTipo().equals(tipoEmail.getId())) {
				cuentaUIData.getEmailLaboral().setText(email.getEmail());
			}
		}
	}

	public void cargarPanelFormaPago(CuentaDto cuentaDto) {
		String id_formaPago = "";
		if (cuentaDto.getDatosPago().isEfectivo()) {
			datosPago = (DatosEfectivoDto) cuentaDto.getDatosPago();
			cuentaUIData.getFormaPago().setSelectedItem(((DatosEfectivoDto) datosPago).formaPagoAsociada());
			id_formaPago = TipoFormaPagoEnum.EFECTIVO.getTipo();
		} else if (cuentaDto.getDatosPago().isDebitoCuentaBancaria()) {
			datosPago = (DatosDebitoCuentaBancariaDto) cuentaDto.getDatosPago();
			cuentaUIData.getFormaPago().setSelectedItem(
					((DatosDebitoCuentaBancariaDto) datosPago).formaPagoAsociada());
			cuentaUIData.getTipoCuentaBancaria().selectByValue(
					((DatosDebitoCuentaBancariaDto) datosPago).getTipoCuentaBancaria().getItemValue());
			cuentaUIData.getCbu().setText(((DatosDebitoCuentaBancariaDto) datosPago).getCbu());
			id_formaPago = TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo();
		} else if (cuentaDto.getDatosPago().isDebitoTarjetaCredito()) {
			datosPago = (DatosDebitoTarjetaCreditoDto) cuentaDto.getDatosPago();
			cuentaUIData.getFormaPago().setSelectedItem(
					((DatosDebitoTarjetaCreditoDto) datosPago).formaPagoAsociada());
			cuentaUIData.getMesVto().setSelectedIndex(
					((DatosDebitoTarjetaCreditoDto) datosPago).getMesVencimientoTarjeta() - 1);
			cuentaUIData.getAnioVto().setText(
					Short.toString(((DatosDebitoTarjetaCreditoDto) datosPago).getAnoVencimientoTarjeta()));
			cuentaUIData.getTipoTarjeta().setSelectedItem(
					((DatosDebitoTarjetaCreditoDto) datosPago).getTipoTarjeta());
			cuentaUIData.getNumeroTarjeta().setText(((DatosDebitoTarjetaCreditoDto) datosPago).getNumero());
			id_formaPago = TipoFormaPagoEnum.TARJETA_CREDITO.getTipo();
		}
		cuentaUIData.setFacturaElectronica(cuentaDto.getFacturaElectronica());
		//el param tiene null(pero está bien porque no tenia)
		this.setFacturaElectronicaOriginal(cuentaUIData.getFacturaElectronica());
		setVisiblePanelFormaPagoYActualizarCamposObligatorios(id_formaPago);
	}

	public void setVisiblePanelFormaPagoYActualizarCamposObligatorios(String id_formaPago) {
		cuentaUIData.getCamposObligatoriosFormaPago().clear();
		cuentaUIData.getFormaPago().selectByValue(id_formaPago);
		if (id_formaPago.equals(TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo())) {
			cuentaBancariaTable.setWidget(0, 1, cuentaUIData.getFormaPago());
			efectivoTable.setVisible(false);
			cuentaBancariaTable.setVisible(true);
			tarjetaTable.setVisible(false);
			cuentaUIData.getCamposObligatoriosFormaPago().add(cuentaUIData.getCbu());
		} else if (id_formaPago.equals(TipoFormaPagoEnum.TARJETA_CREDITO.getTipo())) {
			tarjetaTable.setWidget(0, 1, cuentaUIData.getFormaPago());
			efectivoTable.setVisible(false);
			cuentaBancariaTable.setVisible(false);
			tarjetaTable.setVisible(true);
			cuentaUIData.getCamposObligatoriosFormaPago().add(cuentaUIData.getNumeroTarjeta());
			cuentaUIData.getCamposObligatoriosFormaPago().add(cuentaUIData.getAnioVto());
		} else { // if (id_formaPago.equals(TipoFormaPagoEnum.EFECTIVO.getTipo())) {
			efectivoTable.setWidget(0, 1, cuentaUIData.getFormaPago());
			efectivoTable.setVisible(true);
			cuentaBancariaTable.setVisible(false);
			tarjetaTable.setVisible(false);
		}
	}

	public void cargarPanelVendedor(CuentaDto cuentaDto) {
		cuentaUIData.getVendedorNombre().setText(
				cuentaDto.getVendedor() != null ? cuentaDto.getVendedor().getNombre() : "");
		cuentaUIData.getVendedorTelefono().setText(
				cuentaDto.getVendedor() != null ? cuentaDto.getVendedor().getTelefono() : "");
		cuentaUIData.getTipoCanalVentas().setSelectedItem(cuentaDto.getTipoCanalVentas());
	}

	public void cargarPanelUsuario(CuentaDto cuentaDto) {
		cuentaUIData.getUsuario().setText(cuentaDto.getNombreUsuarioCreacion());
		if (cuentaDto.getFechaCreacion() != null)
			cuentaUIData.getFechaCreacion().setText(
					DateTimeFormat.getMediumDateFormat().format(cuentaDto.getFechaCreacion()));
	}

	public void setAtributosCamposCuenta(CuentaDto cuentaDto) {

		cuentaUIData.enableFields();
		iconoLupa.setVisible(true);
		cuentaUIData.getVerazRta().setVisible(true);
		cuentaUIData.getVerazLabel().setVisible(true);
		showPanelDatosCuenta = true;

		boolean docTipoCUIL = false;
		boolean docTipoCUIT = false;
		if (cuentaDto.getPersona().getDocumento() != null) {
			long idTipoDocumento = cuentaDto.getPersona().getDocumento().getTipoDocumento().getId();
			docTipoCUIL = idTipoDocumento == TipoDocumentoEnum.CUIL.getTipo()
					|| idTipoDocumento == TipoDocumentoEnum.CUIT.getTipo();
			docTipoCUIT = idTipoDocumento == TipoDocumentoEnum.CUIT.getTipo();
		}
		List<Widget> campos = new ArrayList<Widget>();
		if (!docTipoCUIT) {
			campos.add(cuentaUIData.getRazonSocial());
		}
		campos.add(cuentaUIData.getTipoDocumento());
		campos.add(cuentaUIData.getNumeroDocumento());
		campos.add(cuentaUIData.getCategoria());
		campos.add(cuentaUIData.getClaseCliente());
		campos.add(cuentaUIData.getCicloFacturacion());

		FormUtils.disableFields(campos);

		boolean isVancuc = RegularExpressionConstants.isVancuc(cuentaDto.getCodigoVantive());
		iconoLupa.setVisible(isVancuc);
		cuentaUIData.getVerazLabel().setVisible(isVancuc);
		cuentaUIData.getVerazRta().setVisible(isVancuc);

		cuentaUIData.getIibb().setVisible(docTipoCUIL);
		cuentaUIData.getIibbLabel().setVisible(docTipoCUIL);

		cuentaUIData.getNombreDivision().setVisible(false);
		cuentaUIData.getNomDivLabel().setVisible(false);

		cuentaUIData.getUse().setVisible(!docTipoCUIL);
		cuentaUIData.getUseLabel().setVisible(!docTipoCUIL);

		cuentaUIData.getCargo().setVisible(
				cuentaDto.getPersona().getSexo().getItemValue().equals(
						Long.toString(SexoEnum.ORGANIZACION.getId())));
		cuentaUIData.getCargoLabel().setVisible(
				cuentaDto.getPersona().getSexo().getItemValue().equals(
						Long.toString(SexoEnum.ORGANIZACION.getId())));
		cuentaUIData.getObservaciones().setVisible(true);
		cuentaUIData.getObservLabel().setVisible(true);
		vendedorPanel.setVisible(false);

	}

	public void setAtributosCamposAlAgregarDivision(CuentaDto cuentaDto) {
		setAtributosCamposCuenta(cuentaDto != null ? cuentaDto : CuentaEdicionTabPanel.getInstance()
				.getCuenta2editDto());
		cuentaUIData.getNombreDivision().setVisible(true);
		cuentaUIData.getNomDivLabel().setVisible(true);
	}

	public void setAtributosCamposAlAgregarSuscriptor(CuentaDto cuentaDto) {
		setAtributosCamposCuenta(cuentaDto != null ? cuentaDto : CuentaEdicionTabPanel.getInstance()
				.getCuenta2editDto());
	}

	public void setAtributosCamposAlMostrarResuladoBusqueda(CuentaDto cuentaDto) {
		List<Widget> campos = new ArrayList<Widget>();
		if (cuentaDto.getCategoriaCuenta().getDescripcion().equals(TipoCuentaEnum.DIV.getTipo())) {
			setAtributosCamposAlAgregarDivision(cuentaDto);
			campos.add(cuentaUIData.getNombreDivision());
		} else if (cuentaDto.getCategoriaCuenta().getDescripcion().equals(TipoCuentaEnum.SUS.getTipo())) {
			setAtributosCamposAlAgregarSuscriptor(cuentaDto);
		} else {
			setAtributosCamposCuenta(cuentaDto);
		}
		campos.add(cuentaUIData.getNombre());
		campos.add(cuentaUIData.getApellido());
		campos.add(cuentaUIData.getRazonSocial());
		campos.add(cuentaUIData.getSexo());
		campos.add(cuentaUIData.getFechaNacimiento());
		campos.add(cuentaUIData.getProveedorAnterior());
		campos.add(cuentaUIData.getContribuyente());
		campos.add(cuentaUIData.getCargo());
		campos.add(cuentaUIData.getRubro());
		campos.add(cuentaUIData.getIibb());
		campos.add(cuentaUIData.getClaseCliente());
		campos.add(cuentaUIData.getCategoria());

		iconoLupa.setVisible(false);
		cuentaUIData.getVerazLabel().setVisible(false);
		cuentaUIData.getVerazRta().setVisible(false);

		campos.add(cuentaUIData.getTelPrincipalTextBox().getArea());
		campos.add(cuentaUIData.getTelPrincipalTextBox().getNumero());
		campos.add(cuentaUIData.getTelPrincipalTextBox().getInterno());
		campos.add(cuentaUIData.getObservaciones());

		// deshabilita los campos tel/email que NO estan en carga.
		for (TelefonoDto tel : cuentaDto.getPersona().getTelefonos()) {
			if (!tel.isEnCarga()) {
				if (tel.getTipoTelefono().getId() == TipoTelefonoEnum.ADICIONAL.getTipo()) {
					campos.add(cuentaUIData.getTelAdicionalTextBox().getArea());
					campos.add(cuentaUIData.getTelAdicionalTextBox().getNumero());
					campos.add(cuentaUIData.getTelAdicionalTextBox().getInterno());
				} else if (tel.getTipoTelefono().getId() == TipoTelefonoEnum.CELULAR.getTipo()) {
					campos.add(cuentaUIData.getTelCelularTextBox().getArea());
					campos.add(cuentaUIData.getTelCelularTextBox().getNumero());
				} else if (tel.getTipoTelefono().getId() == TipoTelefonoEnum.FAX.getTipo()) {
					campos.add(cuentaUIData.getTelFaxTextBox().getArea());
					campos.add(cuentaUIData.getTelFaxTextBox().getNumero());
					campos.add(cuentaUIData.getTelFaxTextBox().getInterno());
				}
			}
		}
		for (EmailDto email : cuentaDto.getPersona().getEmails()) {
			if (!email.isEnCarga()) {
				if (email.getTipoEmail().getId() == TipoEmailEnum.PERSONAL.getTipo()) {
					campos.add(cuentaUIData.getEmailPersonal());
				} else if (email.getTipoEmail().getId() == TipoEmailEnum.LABORAL.getTipo()) {
					campos.add(cuentaUIData.getEmailLaboral());
				}
			}
		}

		campos.add(cuentaUIData.getFormaPago());
		campos.add(cuentaUIData.getCbu());
		campos.add(cuentaUIData.getTipoCuentaBancaria());
		campos.add(cuentaUIData.getTipoTarjeta());
		campos.add(cuentaUIData.getNumeroTarjeta());
		campos.add(cuentaUIData.getAnioVto());
		campos.add(cuentaUIData.getMesVto());

		campos.add(cuentaUIData.getVendedorNombre());
		campos.add(cuentaUIData.getVendedorTelefono());
		campos.add(cuentaUIData.getTipoCanalVentas());

		FormUtils.disableFields(campos);

		// issue 20130: ocultar vendedor siempre hasta que esté el perfil adm vta
		// vendedorPanel.setVisible( ClientContext.getInstance().getUsuario().getId().
		// equals(
		// cuentaDto.getVendedor().getId())
		// );

	}

	public void setAtributosCamposAlMostrarResuladoBusquedaFromOpp(CuentaPotencialDto cuentaPotencialDto) {

		List<Widget> disabledFields = new ArrayList<Widget>();

		if (cuentaPotencialDto.getCuentaOrigen().getCategoriaCuenta().getDescripcion().equals(
				TipoCuentaEnum.SUS.getTipo()))
			setAtributosCamposAlAgregarSuscriptor(cuentaPotencialDto.getCuentaOrigen());
		else
			setAtributosCamposCuenta(cuentaPotencialDto.getCuentaOrigen());

		cuentaUIData.disableFields();

		cuentaUIData.getOppEstado().setVisible(!cuentaPotencialDto.isEsReserva());
		cuentaUIData.getOppEstadoLabel().setVisible(!cuentaPotencialDto.isEsReserva());
		cuentaUIData.getOppCompetenciaEquipo().setVisible(!cuentaPotencialDto.isEsReserva());
		cuentaUIData.getOppCompetenciaEquipoLabel().setVisible(!cuentaPotencialDto.isEsReserva());
		cuentaUIData.getOppCompetenciaProv().setVisible(!cuentaPotencialDto.isEsReserva());
		cuentaUIData.getOppCompetenciaProvLabel().setVisible(!cuentaPotencialDto.isEsReserva());
		iconoEditarEstdo.setVisible(!cuentaPotencialDto.isEsReserva());

		FormUtils.disableFields(disabledFields);
		cuentaUIData.getObservaciones().setVisible(false);
		cuentaUIData.getObservLabel().setVisible(false);

		showPanelDatosCuenta = false;
	}

	public void setAtributosCamposSoloLectura() {
		cuentaUIData.disableFields();
	}

	public void setUItipoEditorCuenta(boolean editorCuenta) {
		datosOppPanel.setVisible(!editorCuenta);
		datosCuentaPanel.setVisible(editorCuenta);
		formaDePagoPanel.setVisible(editorCuenta);
		// usuario.setVisible(editorCuenta);
		fechaUsuarioTable.setVisible(editorCuenta);

		// issue 20130, ocultar siempre hasta que este el perfil adm vta.
		// vendedorPanel.setVisible(editorCuenta);
		vendedorPanel.setVisible(false);

		if (editorCuenta) {
			for (Widget label : cuentaUIData.getLabelsObligatorios()) {
				label.addStyleName("req");
			}
		} else {
			for (Widget label : cuentaUIData.getLabelsObligatorios()) {
				label.removeStyleName("req");
			}
		}
	}

	public boolean formularioDatosDirty() {
		boolean retorno = false;
		CuentaEdicionTabPanel cuentaTab = CuentaEdicionTabPanel.getInstance();

		// PANEL DATOS
		if ((FormUtils.fieldDirty(cuentaUIData.getRazonSocial(), cuentaTab.getCuenta2editDto().getPersona()
				.getRazonSocial()))
				|| (FormUtils.fieldDirty(cuentaUIData.getNombre(), cuentaTab.getCuenta2editDto().getPersona()
						.getNombre()))
				|| (FormUtils.fieldDirty(cuentaUIData.getApellido(), cuentaTab.getCuenta2editDto()
						.getPersona().getApellido()))
				|| (FormUtils.fieldDirty(cuentaUIData.getSexo(), cuentaTab.getCuenta2editDto().getPersona()
						.getSexo().getItemValue()))
				|| (FormUtils.fieldDirty(cuentaUIData.getFechaNacimiento(), cuentaTab.getCuenta2editDto()
						.getPersona().getFechaNacimiento() != null ? DateTimeFormat.getMediumDateFormat()
						.format(
								DATE_TIME_FORMAT.parse(cuentaTab.getCuenta2editDto().getPersona()
										.getFechaNacimiento())) : ""))
				|| (FormUtils.fieldDirty(cuentaUIData.getContribuyente(), cuentaTab.getCuenta2editDto()
						.getTipoContribuyente() != null ? cuentaTab.getCuenta2editDto()
						.getTipoContribuyente().getItemValue() : null))
				|| (FormUtils.fieldDirty(cuentaUIData.getProveedorAnterior(), cuentaTab.getCuenta2editDto()
						.getProveedorInicial().getItemValue()))
				|| (FormUtils.fieldDirty(cuentaUIData.getRubro(),
						cuentaTab.getCuenta2editDto().getRubro() != null ? cuentaTab.getCuenta2editDto()
								.getRubro().getItemValue() : null))
				|| (FormUtils.fieldDirty(cuentaUIData.getClaseCliente(), cuentaTab.getCuenta2editDto()
						.getClaseCuenta().getItemValue()))
				|| (FormUtils.fieldDirty(cuentaUIData.getCategoria(), cuentaTab.getCuenta2editDto()
						.getCategoriaCuenta().getItemText()))
				|| (FormUtils.fieldDirty(cuentaUIData.getObservaciones(), cuentaTab.getCuenta2editDto()
						.getObservacionesTelMail()))
				|| (FormUtils.fieldDirty(cuentaUIData.getIibb(), cuentaTab.getCuenta2editDto().getIibb())))
				//|| facturaElectronicaUI.isDirty())
				
			return true;

		if (cuentaTab.getCuenta2editDto().getCategoriaCuenta().getDescripcion().equals(
				TipoCuentaEnum.DIV.getTipo())) {
			if ((cuentaUIData.getNombreDivision().getText() == null || cuentaUIData.getNombreDivision()
					.getText().equals(""))
					|| (FormUtils.fieldDirty(cuentaUIData.getNombreDivision(), ((DivisionDto) cuentaTab
							.getCuenta2editDto()).getNombre())))
				return true;
		}

		// PANEL FORMA DE PAGO
		if (!cuentaUIData.getFormaPago().getSelectedItemId().equals(
				cuentaTab.getCuenta2editDto().getFormaPago().getId() + "")) {
			return true;
		}

		if (cuentaUIData.getFormaPago().getSelectedItemId().equals(
				TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo())) {
			if (cuentaTab.getCuenta2editDto().getDatosPago() instanceof DatosDebitoCuentaBancariaDto) {
				if (FormUtils.fieldDirty(cuentaUIData.getCbu(), ((DatosDebitoCuentaBancariaDto) cuentaTab
						.getCuenta2editDto().getDatosPago()).getCbu())
						|| FormUtils.fieldDirty(cuentaUIData.getTipoCuentaBancaria(),
								((DatosDebitoCuentaBancariaDto) cuentaTab.getCuenta2editDto().getDatosPago())
										.getTipoCuentaBancaria().getItemValue())) {
					retorno = true;
				}
			} else {
				if ((!cuentaUIData.getCbu().getText().trim().equals("")))
					return true;
			}
		}
		if (cuentaUIData.getFormaPago().getSelectedItemId().equals(
				TipoFormaPagoEnum.TARJETA_CREDITO.getTipo())) {
			if (cuentaTab.getCuenta2editDto().getDatosPago() instanceof DatosDebitoTarjetaCreditoDto) {
				if ((FormUtils.fieldDirty(cuentaUIData.getNumeroTarjeta(),
						((DatosDebitoTarjetaCreditoDto) cuentaTab.getCuenta2editDto().getDatosPago())
								.getNumero()))
						|| (FormUtils.fieldDirty(cuentaUIData.getAnioVto(),
								((DatosDebitoTarjetaCreditoDto) cuentaTab.getCuenta2editDto().getDatosPago())
										.getAnoVencimientoTarjeta()
										+ ""))
						|| (FormUtils.fieldDirty(cuentaUIData.getMesVto(),
								((DatosDebitoTarjetaCreditoDto) cuentaTab.getCuenta2editDto().getDatosPago())
										.getMesVencimientoTarjeta()
										+ ""))
						|| (FormUtils.fieldDirty(cuentaUIData.getTipoTarjeta(),
								((DatosDebitoTarjetaCreditoDto) cuentaTab.getCuenta2editDto().getDatosPago())
										.getTipoTarjeta().getItemValue())))
					retorno = true;
			} else {
				if ((!cuentaUIData.getNumeroTarjeta().getText().trim().equals(""))
						|| (cuentaUIData.getAnioVto().getText().trim().endsWith("")))
					return true;
			}
		}

		// PANEL TELEFONOS
		boolean noTienePrincipal = true;
		boolean noTieneAdicional = true;
		boolean noTieneCelular = true;
		boolean noTieneFax = true;

		// compara con el dto
		for (TelefonoDto telefono : cuentaTab.getCuenta2editDto().getPersona().getTelefonos()) {
			TipoTelefonoDto tipoTelefono = telefono.getTipoTelefono();

			if (tipoTelefono.getId() == TipoTelefonoEnum.PRINCIPAL.getTipo()) {
				if ((FormUtils
						.fieldDirty(cuentaUIData.getTelPrincipalTextBox().getArea(), telefono.getArea()))
						|| (FormUtils.fieldDirty(cuentaUIData.getTelPrincipalTextBox().getNumero(), telefono
								.getNumeroLocal()))
						|| (FormUtils.fieldDirty(cuentaUIData.getTelPrincipalTextBox().getInterno(), telefono
								.getInterno())))
					retorno = true;
				noTienePrincipal = false;
			}
			if (tipoTelefono.getId() == TipoTelefonoEnum.PARTICULAR.getTipo()
					|| tipoTelefono.getId() == TipoTelefonoEnum.ADICIONAL.getTipo()) {
				if ((FormUtils
						.fieldDirty(cuentaUIData.getTelAdicionalTextBox().getArea(), telefono.getArea()))
						|| (FormUtils.fieldDirty(cuentaUIData.getTelAdicionalTextBox().getNumero(), telefono
								.getNumeroLocal()))
						|| (FormUtils.fieldDirty(cuentaUIData.getTelAdicionalTextBox().getInterno(), telefono
								.getInterno())))
					retorno = true;
				noTieneAdicional = false;
			}
			if (tipoTelefono.getId() == TipoTelefonoEnum.CELULAR.getTipo()) {
				if ((FormUtils.fieldDirty(cuentaUIData.getTelCelularTextBox().getArea(), telefono.getArea()))
						|| (FormUtils.fieldDirty(cuentaUIData.getTelCelularTextBox().getNumero(), telefono
								.getNumeroLocal())))
					retorno = true;
				noTieneCelular = false;
			}
			if (tipoTelefono.getId() == TipoTelefonoEnum.FAX.getTipo()) {
				if ((FormUtils.fieldDirty(cuentaUIData.getTelFaxTextBox().getArea(), telefono.getArea()))
						|| (FormUtils.fieldDirty(cuentaUIData.getTelFaxTextBox().getNumero(), telefono
								.getNumeroLocal()))
						|| (FormUtils.fieldDirty(cuentaUIData.getTelFaxTextBox().getInterno(), telefono
								.getInterno())))
					retorno = true;
				noTieneFax = false;
			}
		}
		// chequea si hay nuevos
		if (noTienePrincipal) {
			if ((!cuentaUIData.getTelPrincipalTextBox().getArea().getText().trim().equals(""))
					|| (!cuentaUIData.getTelPrincipalTextBox().getNumero().getText().trim().equals(""))
					|| (!cuentaUIData.getTelPrincipalTextBox().getInterno().getText().trim().equals("")))
				retorno = true;
		}
		if (noTieneAdicional) {
			if ((!cuentaUIData.getTelAdicionalTextBox().getArea().getText().trim().equals(""))
					|| (!cuentaUIData.getTelAdicionalTextBox().getNumero().getText().trim().equals(""))
					|| (!cuentaUIData.getTelAdicionalTextBox().getInterno().getText().trim().equals("")))
				retorno = true;
		}
		if (noTieneCelular) {
			if ((!cuentaUIData.getTelCelularTextBox().getArea().getText().trim().equals(""))
					|| (!cuentaUIData.getTelCelularTextBox().getNumero().getText().trim().equals("")))
				retorno = true;
		}
		if (noTieneFax) {
			if ((!cuentaUIData.getTelFaxTextBox().getArea().getText().trim().equals(""))
					|| (!cuentaUIData.getTelFaxTextBox().getNumero().getText().trim().equals(""))
					|| (!cuentaUIData.getTelFaxTextBox().getInterno().getText().trim().equals("")))
				retorno = true;
		}

		// PANEL MAILS
		boolean noTienePersonal = true;
		boolean noTieneLaboral = true;
		boolean noTieneFacturaElectronica = true;

		for (EmailDto email : cuentaTab.getCuenta2editDto().getPersona().getEmails()) {
			TipoEmailDto tipo = email.getTipoEmail();
			if (TipoEmailEnum.PERSONAL.getTipo().equals(tipo.getId())) {
				if ((FormUtils.fieldDirty(cuentaUIData.getEmailPersonal(), email.getEmail())))
					retorno = true;
				noTienePersonal = false;
			}
			if (TipoEmailEnum.LABORAL.getTipo().equals(tipo.getId())) {
				if ((FormUtils.fieldDirty(cuentaUIData.getEmailLaboral(), email.getEmail())))
					retorno = true;
				noTieneLaboral = false;
			}
		}
		
		
		if (noTienePersonal) {
			if (!cuentaUIData.getEmailPersonal().getText().equals(""))
				retorno = true;
		}
		if (noTieneLaboral) {
			if (!cuentaUIData.getEmailLaboral().getText().equals(""))
				retorno = true;
		}

		FacturaElectronicaDto facturaElectronicaDto = cuentaTab.getCuenta2editDto().getFacturaElectronica();
		if (facturaElectronicaDto != null) {
			noTieneFacturaElectronica = false;
			if ((FormUtils.fieldDirty(cuentaUIData.getEmailFacturaElectronica(), cuentaTab.getCuenta2editDto().getFacturaElectronica().getEmail()))) {
				retorno = true;
			}
		} 
	

		if (noTieneFacturaElectronica) {
			if (!cuentaUIData.getEmailFacturaElectronica().getText().equals(""))
				retorno = true;
		}
		
	
		return retorno;
	}

	/**
	 * selecciona la opcion del combo sexo que corresponde al tipo de documento
	 */
	public void setDefaultComboSexo(long tipoDocId, String docNumero) {
		if (tipoDocId == TipoDocumentoEnum.LC.getTipo())
			cuentaUIData.getSexo().selectByValue(Long.toString(SexoEnum.FEMENINO.getId()));
		else if (tipoDocId == TipoDocumentoEnum.CUIT.getTipo()
				|| tipoDocId == TipoDocumentoEnum.CUIT_EXT.getTipo())
			cuentaUIData.getSexo().selectByValue(Long.toString(SexoEnum.getId(docNumero)));
		else
			cuentaUIData.getSexo().selectByValue(Long.toString(SexoEnum.MASCULINO.getId()));

	}

	/**
	 * 
	 * @return
	 */
	public List<String> validarCompletitud() {
		GwtValidator validator = CuentaEdicionTabPanel.getInstance().getValidator();
		validator.clear();

		camposObligatorios.clear();
		camposObligatorios = cuentaUIData.getCamposObligatorios();
		camposObligatorios.addAll(cuentaUIData.getCamposObligatoriosFormaPago());
		if (CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getCategoriaCuenta().getDescripcion()
				.equals(TipoCuentaEnum.DIV.getTipo())) {
			camposObligatorios.add(cuentaUIData.getNombreDivision());
		}

		for (Widget campo : camposObligatorios) {
			if (campo instanceof TextBox)
				if (((TextBox) campo).isEnabled())
					validator.addTarget((TextBox) campo).required(
							Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll("\\{1\\}",
									((TextBox) campo).getName()));
			if (campo instanceof ListBox)
				if (((ListBox) campo).isEnabled()) {
					String texto = ((ListBox) campo).getSelectedItemText() != null ? ((ListBox) campo)
							.getSelectedItemText().trim() : "";
					validator.addTarget(texto).required(
							Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll("\\{1\\}",
									((ListBox) campo).getName()));
				}
		}
		validator.fillResult();
		return validator.getErrors();
	}

	/**
	 * 
	 * @return
	 */
	public List<String> validarCamposTabDatos() {
		GwtValidator validator = CuentaEdicionTabPanel.getInstance().getValidator();
		validator.clear();

		if (!cuentaUIData.getNombre().getText().equals("") && cuentaUIData.getNombre().isEnabled())
			validator.addTarget(cuentaUIData.getNombre()).alphabetic(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}", cuentaUIData.getNombre().getName()));
		if (!cuentaUIData.getApellido().getText().equals("") && cuentaUIData.getApellido().isEnabled())
			validator.addTarget(cuentaUIData.getApellido()).alphabetic(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}", cuentaUIData.getApellido().getName()));

		if (!cuentaUIData.getNombreDivision().getText().equals("")
				&& cuentaUIData.getNombreDivision().isEnabled())
			validator.addTarget(cuentaUIData.getNombreDivision()).alphabetic(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							cuentaUIData.getNombreDivision().getName()));

		if (!cuentaUIData.getFechaNacimiento().getTextBox().getText().equals("")
				&& cuentaUIData.getFechaNacimiento().getTextBox().isEnabled()) {
			if (!validator.addTarget(cuentaUIData.getFechaNacimiento().getTextBox()).date(
					Sfa.constant().ERR_FECHA_NO_VALIDA().replaceAll("\\{1\\}",
							cuentaUIData.getFechaNacimiento().getTextBox().getName())).hasErrorMsg())
				validator.addTarget(cuentaUIData.getFechaNacimiento().getTextBox()).dateSmallerToday(
						Sfa.constant().ERR_FECHA_NAC_MENOR_HOY());
		}
		if (!cuentaUIData.getEmailPersonal().getText().equals("")
				&& cuentaUIData.getEmailPersonal().isEnabled())
			validator.addTarget(cuentaUIData.getEmailPersonal()).mail(
					Sfa.constant().ERR_EMAIL_NO_VALIDO().replaceAll("\\{1\\}",
							cuentaUIData.getEmailPersonal().getName()));
		if (!cuentaUIData.getEmailLaboral().getText().equals("")
				&& cuentaUIData.getEmailLaboral().isEnabled())
			validator.addTarget(cuentaUIData.getEmailLaboral()).mail(
					Sfa.constant().ERR_EMAIL_NO_VALIDO().replaceAll("\\{1\\}",
							cuentaUIData.getEmailLaboral().getName()));

		if (!cuentaUIData.getTelPrincipalTextBox().getArea().getText().equals("")
				&& cuentaUIData.getTelPrincipalTextBox().getArea().isEnabled())
			validator.addTarget(cuentaUIData.getTelPrincipalTextBox().getArea()).numericPositive(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							cuentaUIData.getTelPrincipalTextBox().getArea().getName()));
		if (!cuentaUIData.getTelPrincipalTextBox().getNumero().getText().equals("")
				&& cuentaUIData.getTelPrincipalTextBox().getNumero().isEnabled())
			validator.addTarget(cuentaUIData.getTelPrincipalTextBox().getNumero()).numericPositive(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							cuentaUIData.getTelPrincipalTextBox().getNumero().getName()));
		if (!cuentaUIData.getTelPrincipalTextBox().getInterno().getText().equals("")
				&& cuentaUIData.getTelPrincipalTextBox().getInterno().isEnabled())
			validator.addTarget(cuentaUIData.getTelPrincipalTextBox().getInterno()).numericPositive(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							cuentaUIData.getTelPrincipalTextBox().getInterno().getName()));
		if (!cuentaUIData.getTelAdicionalTextBox().getArea().getText().equals("")
				&& cuentaUIData.getTelAdicionalTextBox().getArea().isEnabled())
			validator.addTarget(cuentaUIData.getTelAdicionalTextBox().getArea()).numericPositive(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							cuentaUIData.getTelAdicionalTextBox().getArea().getName()));
		if (!cuentaUIData.getTelAdicionalTextBox().getNumero().getText().equals("")
				&& cuentaUIData.getTelAdicionalTextBox().getNumero().isEnabled())
			validator.addTarget(cuentaUIData.getTelAdicionalTextBox().getNumero()).numericPositive(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							cuentaUIData.getTelAdicionalTextBox().getNumero().getName()));
		if (!cuentaUIData.getTelAdicionalTextBox().getInterno().getText().equals("")
				&& cuentaUIData.getTelAdicionalTextBox().getInterno().isEnabled())
			validator.addTarget(cuentaUIData.getTelAdicionalTextBox().getInterno()).numericPositive(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							cuentaUIData.getTelAdicionalTextBox().getInterno().getName()));
		if (!cuentaUIData.getTelFaxTextBox().getArea().getText().equals("")
				&& cuentaUIData.getTelFaxTextBox().getArea().isEnabled())
			validator.addTarget(cuentaUIData.getTelFaxTextBox().getArea()).numericPositive(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							cuentaUIData.getTelFaxTextBox().getArea().getName()));
		if (!cuentaUIData.getTelFaxTextBox().getNumero().getText().equals("")
				&& cuentaUIData.getTelFaxTextBox().getNumero().isEnabled())
			validator.addTarget(cuentaUIData.getTelFaxTextBox().getNumero()).numericPositive(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							cuentaUIData.getTelFaxTextBox().getNumero().getName()));
		if (!cuentaUIData.getTelFaxTextBox().getInterno().getText().equals("")
				&& cuentaUIData.getTelFaxTextBox().getInterno().isEnabled())
			validator.addTarget(cuentaUIData.getTelFaxTextBox().getInterno()).numericPositive(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							cuentaUIData.getTelFaxTextBox().getInterno().getName()));
		if (!cuentaUIData.getTelCelularTextBox().getArea().getText().equals("")
				&& cuentaUIData.getTelCelularTextBox().getArea().isEnabled())
			validator.addTarget(cuentaUIData.getTelCelularTextBox().getArea()).numericPositive(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							cuentaUIData.getTelCelularTextBox().getArea().getName()));
		if (!cuentaUIData.getTelCelularTextBox().getNumero().getText().equals("")
				&& cuentaUIData.getTelCelularTextBox().getNumero().isEnabled())
			validator.addTarget(cuentaUIData.getTelCelularTextBox().getNumero()).numericPositive(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							cuentaUIData.getTelCelularTextBox().getNumero().getName()));

		if (cuentaUIData.getFormaPago().getSelectedItemId().equals(
				TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo())) {
			if (!cuentaUIData.getCbu().getText().equals("") && cuentaUIData.getCbu().isEnabled()) {
				if (cuentaUIData.getCbu().getText().length() < 22) {
					validator.addError(Sfa.constant().ERR_NUMERO_CBU());
				}
			}
		}

		if (cuentaUIData.getFormaPago().getSelectedItemId().equals(
				TipoFormaPagoEnum.TARJETA_CREDITO.getTipo())) {
			if (!cuentaUIData.getNumeroTarjeta().getText().equals("")
					&& cuentaUIData.getNumeroTarjeta().isEnabled()) {
				int cantidadDigitosNroTarjeta = 16; // VIS - MASTER - CABAL
				if (cuentaUIData.getTipoTarjeta().getSelectedItemId().equals(TipoTarjetaEnum.AMX.getId())) {
					cantidadDigitosNroTarjeta = 15;
				} else if (cuentaUIData.getTipoTarjeta().getSelectedItemId().equals(
						TipoTarjetaEnum.DIN.getId())) {
					cantidadDigitosNroTarjeta = 14;
				}
				if (cuentaUIData.getNumeroTarjeta().getText().length() < cantidadDigitosNroTarjeta) {
					validator.addError(Sfa.constant().ERR_NUMERO_DE_TARJETA().replaceAll("\\{1\\}",
							cantidadDigitosNroTarjeta + ""));
				}
			}

			if (!cuentaUIData.getAnioVto().getText().equals("") && cuentaUIData.getAnioVto().isEnabled()) {
				try {
					int valor = Integer.parseInt(cuentaUIData.getAnioVto().getText());
					if (cuentaUIData.getAnioVto().getText().length() < 4
							|| valor < cuentaUIData.getCurrentYear()
							|| valor > (cuentaUIData.getCurrentYear() + 5)) {
						validator.addError(Sfa.constant().ERR_ANIO_NO_VALIDO());
					}
				} catch (Exception e) {
					validator.addError(Sfa.constant().ERR_ANIO_NO_VALIDO());
				}
			}
		}
		validator.fillResult();
		return validator.getErrors();
	}

	/**
	 * 
	 */
	public void validarTarjeta() {
		if (!cuentaUIData.getNumeroTarjeta().getText().equals("")) {
			CuentaRpcService.Util.getInstance().validarTarjeta(cuentaUIData.getNumeroTarjeta().getText(),
					new Integer(cuentaUIData.getMesVto().getSelectedItemId()),
					new Integer(cuentaUIData.getAnioVto().getText()),
					new DefaultWaitCallback<TarjetaCreditoValidatorResultDto>() {
						public void success(TarjetaCreditoValidatorResultDto tarjetaCreditoValidatorResult) {
							if (tarjetaCreditoValidatorResult == null) {
								ErrorDialog.getInstance().show(Sfa.constant().ERR_AL_VALIDAR_TARJETA());
							} else if (tarjetaCreditoValidatorResult.getIsValid()) {
								MessageWindow.alert(Sfa.constant().ERR_DIA_HABIL());
							} else {
								ErrorDialog.getInstance().show(Sfa.constant().ERR_TARJETA_NO_VALIDA());
							}
						}
					});
		} else {
			ErrorDialog.getInstance().show(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll("\\{1\\}",
							cuentaUIData.getNumeroTarjeta().getName()));
		}
	}

	/**
	 * 
	 */
	public void cambiarVisibilidadCamposSegunSexo() {
		cuentaUIData.getCargoLabel().setVisible(
				cuentaUIData.getSexo().getSelectedItemId().equals(
						Long.toString(SexoEnum.ORGANIZACION.getId())));
		cuentaUIData.getCargo().setVisible(
				cuentaUIData.getSexo().getSelectedItemId().equals(
						Long.toString(SexoEnum.ORGANIZACION.getId())));
		armarTablaPanelDatos();
	}

	/**
	 * 
	 * @return
	 */
	public CuentaDto getCuentaDtoFromEditor() {

		if (CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getCategoriaCuenta().getDescripcion()
				.equals(TipoCuentaEnum.DIV.getTipo())) {
			((DivisionDto) CuentaEdicionTabPanel.getInstance().getCuenta2editDto()).setNombre(cuentaUIData
					.getNombreDivision().getText());
		}

		PersonaDto personaDto = CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getPersona();

		// panel datos
		personaDto.setRazonSocial(cuentaUIData.getRazonSocial().getText());
		personaDto.setNombre(cuentaUIData.getNombre().getText());
		personaDto.setApellido(cuentaUIData.getApellido().getText());
		personaDto.setSexo(new SexoDto(Long.parseLong(cuentaUIData.getSexo().getSelectedItemId()),
				cuentaUIData.getSexo().getSelectedItemText()));
		if (!cuentaUIData.getFechaNacimiento().getTextBox().getText().equals(""))
			personaDto.setFechaNacimiento(DATE_TIME_FORMAT.format(cuentaUIData.getFechaNacimiento()
					.getSelectedDate()));
		CuentaEdicionTabPanel.getInstance().getCuenta2editDto().setTipoContribuyente(
				new TipoContribuyenteDto(Long.parseLong(cuentaUIData.getContribuyente().getSelectedItemId()),
						cuentaUIData.getContribuyente().getSelectedItemText()));
		long selectedSexo = cuentaUIData.getSexo().getSelectedItemId() != null
				&& !"".equals(cuentaUIData.getSexo().getSelectedItemId().trim()) ? Long
				.parseLong(cuentaUIData.getSexo().getSelectedItemId()) : -1;
		if (selectedSexo == SexoEnum.ORGANIZACION.getId()) {
			CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getPersona().setCargo(
					new CargoDto(Long.parseLong(cuentaUIData.getCargo().getSelectedItemId()), cuentaUIData
							.getCargo().getSelectedItemText()));
			CuentaEdicionTabPanel.getInstance().getCuenta2editDto().setIibb(cuentaUIData.getIibb().getText());
		}
		CuentaEdicionTabPanel.getInstance().getCuenta2editDto().setProveedorInicial(
				new ProveedorDto(Long.parseLong(cuentaUIData.getProveedorAnterior().getSelectedItemId()),
						cuentaUIData.getProveedorAnterior().getSelectedItemText()));
		CuentaEdicionTabPanel.getInstance().getCuenta2editDto().setRubro(
				new RubroDto(Long.parseLong(cuentaUIData.getRubro().getSelectedItemId()), cuentaUIData
						.getRubro().getSelectedItemText()));

		// Panel Telefono/Fax
		List<TelefonoDto> phonos = new ArrayList<TelefonoDto>();
		Long principalId = getIdTelefono(CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getPersona()
				.getTelefonos(), TipoTelefonoEnum.PRINCIPAL.getDesc());
		if (!cuentaUIData.getTelPrincipalTextBox().getNumero().getText().equals("") || principalId != null) {
			String principalText = cuentaUIData.getTelPrincipalTextBox().getNumero().getText().trim();
			phonos.add(new TelefonoDto(principalId, !principalText.equals("") ? cuentaUIData
					.getTelPrincipalTextBox().getArea().getText().trim() : "",
					!principalText.equals("") ? cuentaUIData.getTelPrincipalTextBox().getInterno().getText()
							.trim() : "", principalText, personaDto.getId(), Boolean.TRUE,
					new TipoTelefonoDto(TipoTelefonoEnum.PRINCIPAL.getTipo(), TipoTelefonoEnum.PRINCIPAL
							.getDesc())));
		}
		Long adicionalId = getIdTelefono(CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getPersona()
				.getTelefonos(), TipoTelefonoEnum.ADICIONAL.getDesc());
		if (!cuentaUIData.getTelAdicionalTextBox().getNumero().getText().equals("") || adicionalId != null) {
			String adicionalText = cuentaUIData.getTelAdicionalTextBox().getNumero().getText().trim();
			phonos.add(new TelefonoDto(adicionalId, !adicionalText.equals("") ? cuentaUIData
					.getTelAdicionalTextBox().getArea().getText().trim() : "",
					!adicionalText.equals("") ? cuentaUIData.getTelAdicionalTextBox().getInterno().getText()
							.trim() : "", adicionalText, personaDto.getId(), Boolean.FALSE,
					new TipoTelefonoDto(TipoTelefonoEnum.ADICIONAL.getTipo(), TipoTelefonoEnum.ADICIONAL
							.getDesc())));
		}
		Long celularId = getIdTelefono(CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getPersona()
				.getTelefonos(), TipoTelefonoEnum.CELULAR.getDesc());
		if (!cuentaUIData.getTelCelularTextBox().getNumero().getText().equals("") || celularId != null) {
			String celularText = cuentaUIData.getTelCelularTextBox().getNumero().getText().trim();
			phonos.add(new TelefonoDto(celularId, !celularText.equals("") ? cuentaUIData
					.getTelCelularTextBox().getArea().getText().trim() : "", "", celularText, personaDto
					.getId(), Boolean.FALSE, new TipoTelefonoDto(TipoTelefonoEnum.CELULAR.getTipo(),
					TipoTelefonoEnum.CELULAR.toString())));
		}
		Long faxId = getIdTelefono(CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getPersona()
				.getTelefonos(), TipoTelefonoEnum.FAX.getDesc());
		if (!cuentaUIData.getTelFaxTextBox().getNumero().getText().equals("") || faxId != null) {
			String faxText = cuentaUIData.getTelFaxTextBox().getNumero().getText().trim();
			phonos.add(new TelefonoDto(faxId, !faxText.equals("") ? cuentaUIData.getTelFaxTextBox().getArea()
					.getText().trim() : "", !faxText.equals("") ? cuentaUIData.getTelFaxTextBox()
					.getInterno().getText().trim() : "", faxText, personaDto.getId(), Boolean.FALSE,
					new TipoTelefonoDto(TipoTelefonoEnum.FAX.getTipo(), TipoTelefonoEnum.FAX.toString())));
		}
		CuentaEdicionTabPanel.getInstance().getCuenta2editDto().setObservacionesTelMail(
				cuentaUIData.getObservaciones().getText());
		personaDto.setTelefonos(phonos);

		// Panel Emails
		List<EmailDto> mails = new ArrayList<EmailDto>();
		Long personalId = getIdEmail(CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getPersona()
				.getEmails(), TipoEmailEnum.PERSONAL.getTipo());
		if (!cuentaUIData.getEmailPersonal().getText().equals("") || personalId != null) {
			TipoEmailDto tipoEmail = new TipoEmailDto(TipoEmailEnum.PERSONAL.getTipo(),
					TipoEmailEnum.PERSONAL.getDesc());
			mails.add(new EmailDto(personalId, cuentaUIData.getEmailPersonal().getText().trim(), false,
					tipoEmail));
		}
		Long laboralId = getIdEmail(CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getPersona()
				.getEmails(), TipoEmailEnum.LABORAL.getTipo());
		if (!cuentaUIData.getEmailLaboral().getText().equals("") || laboralId != null) {
			mails.add(new EmailDto(laboralId, cuentaUIData.getEmailLaboral().getText().trim(), false,
					new TipoEmailDto(TipoEmailEnum.LABORAL.getTipo(), TipoEmailEnum.LABORAL.getDesc())));
		}
		personaDto.setEmails(mails);
		
		//Factura electrónica
		if (!cuentaUIData.getEmailFacturaElectronica().getText().equals("")) {
			if (cuentaUIData.getFacturaElectronica() == null) {
				cuentaUIData.setFacturaElectronica(new FacturaElectronicaDto());
			} 
			cuentaUIData.getFacturaElectronica().setEmail(cuentaUIData.getEmailFacturaElectronica().getValue());
		}		
		
				
		// Panel Formas de Pago
		DatosPagoDto datosPago = null;
		if (cuentaUIData.getFormaPago().getSelectedItemId().equals(
				TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo())) {
			CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getFormaPago().setId(
					Long.parseLong(TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo()));
			datosPago = new DatosDebitoCuentaBancariaDto();
			((DatosDebitoCuentaBancariaDto) datosPago).setId(CuentaEdicionTabPanel.getInstance()
					.getCuenta2editDto().getDatosPago().getId());
			((DatosDebitoCuentaBancariaDto) datosPago).setFormaPagoAsociada(CuentaEdicionTabPanel
					.getInstance().getCuenta2editDto().getFormaPago());
			((DatosDebitoCuentaBancariaDto) datosPago).setCbu(cuentaUIData.getCbu().getText());
			((DatosDebitoCuentaBancariaDto) datosPago).setTipoCuentaBancaria(new TipoCuentaBancariaDto(Long
					.parseLong(cuentaUIData.getTipoCuentaBancaria().getSelectedItemId()), cuentaUIData
					.getTipoCuentaBancaria().getSelectedItemText(), cuentaUIData.getTipoCuentaBancaria()
					.getSelectedItemText()));
		} else if (cuentaUIData.getFormaPago().getSelectedItemId().equals(
				TipoFormaPagoEnum.TARJETA_CREDITO.getTipo())) {
			datosPago = new DatosDebitoTarjetaCreditoDto();
			((DatosDebitoTarjetaCreditoDto) datosPago).setId(CuentaEdicionTabPanel.getInstance()
					.getCuenta2editDto().getDatosPago().getId());
			CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getFormaPago().setId(
					Long.parseLong(TipoFormaPagoEnum.TARJETA_CREDITO.getTipo()));
			((DatosDebitoTarjetaCreditoDto) datosPago).setFormaPagoAsociada(CuentaEdicionTabPanel
					.getInstance().getCuenta2editDto().getFormaPago());
			((DatosDebitoTarjetaCreditoDto) datosPago).setNumero(cuentaUIData.getNumeroTarjeta().getText());
			((DatosDebitoTarjetaCreditoDto) datosPago).setAnoVencimientoTarjeta(Short.parseShort(cuentaUIData
					.getAnioVto().getText()));
			((DatosDebitoTarjetaCreditoDto) datosPago).setMesVencimientoTarjeta(Short.parseShort(cuentaUIData
					.getMesVto().getSelectedItemId()));
			((DatosDebitoTarjetaCreditoDto) datosPago).setTipoTarjeta(new TipoTarjetaDto(Long
					.parseLong(cuentaUIData.getTipoTarjeta().getSelectedItemId()), cuentaUIData
					.getTipoTarjeta().getSelectedItemText()));
		} else {// if (camposTabDatos.getFormaPago().getSelectedItemId().equals(TipoFormaPagoEnum.EFECTIVO)) {
			datosPago = new DatosEfectivoDto();
			((DatosEfectivoDto) datosPago).setId(CuentaEdicionTabPanel.getInstance().getCuenta2editDto()
					.getDatosPago().getId());
			CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getFormaPago().setId(
					Long.parseLong(TipoFormaPagoEnum.EFECTIVO.getTipo()));
			((DatosEfectivoDto) datosPago).setFormaPagoAsociada(CuentaEdicionTabPanel.getInstance()
					.getCuenta2editDto().getFormaPago());
			CuentaEdicionTabPanel.getInstance().getCuenta2editDto().setDatosPago(datosPago);
		}
		//Forma de pago
		if (!cuentaUIData.getEmailFacturaElectronica().getValue().equals("")) {
			CuentaEdicionTabPanel.getInstance().getCuenta2editDto().setFacturaElectronica(
					cuentaUIData.getFacturaElectronica());
		} else {
			CuentaEdicionTabPanel.getInstance().getCuenta2editDto().setFacturaElectronica(null);
		}
		CuentaEdicionTabPanel.getInstance().getCuenta2editDto().setDatosPago(datosPago);
		CuentaEdicionTabPanel.getInstance().getCuenta2editDto().setPersona(personaDto);
		return CuentaEdicionTabPanel.getInstance().getCuenta2editDto();
	}

	private Long getIdTelefono(List<TelefonoDto> telefonos, String tipo) {
		Long id = null;
		for (TelefonoDto tel : telefonos) {
			if (tel.getTipoTelefono().getDescripcion().equals(tipo)) {
				return tel.getId();
			}
		}
		return id;
	}

	private Long getIdEmail(List<EmailDto> emails, Long tipo) {
		Long id = null;
		for (EmailDto email : emails) {
			if (email.getTipoEmail().getId().equals(tipo)) {
				return email.getId();
			}
		}
		return id;
	}

	public CuentaUIData getCamposTabDatos() {
		return cuentaUIData;
	}

	public void setCamposTabDatos(CuentaUIData camposTabDatos) {
		this.cuentaUIData = camposTabDatos;
	}

	public CuentaPotencialDto getOportunidadDto() {
		return oportunidadDto;
	}

	public void setOportunidadDto(CuentaPotencialDto oportunidadDto) {
		this.oportunidadDto = oportunidadDto;
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

		if (result.getEstado() == null) {
			// analyzeExplicacion();
		} else {
			if ("RECHAZAR".equals(result.getEstado()))
				rechazar(result, veraz);
			else if ("REVISAR".equals(result.getEstado())) {
				revisar(result, veraz);
				result.getExplicacion();
			} else if ("ACEPTAR".equals(result.getEstado()))
				aceptar(result, veraz);
			else if ("REVISAR POR CONYUGE".equals(result.getEstado())) {
				MessageDialog.getInstance().setDialogTitle("Resultado Veraz");
				MessageDialog
						.getInstance()
						.showAceptar(
								"La respuesta de Veraz ha sido REVISAR POR CÓNYUGE. Puede continuar con la operación.",
								MessageDialog.getCloseCommand());
			} else {
				MessageDialog.getInstance().setDialogTitle("Resultado Veraz");
				MessageDialog.getInstance().showAceptar(
						"La Respuesta de Veraz ha sido REVISAR. Puede continuar con la operación.",
						MessageDialog.getCloseCommand());
			}
		}

		veraz.setText(result.getEstado());
	}

	private void rechazar(VerazResponseDto result, Label veraz) {
		veraz.addStyleName(estilos.get(2));
		estiloUsado = 2;
		MessageDialog.getInstance().setDialogTitle("Resultado Veraz");
		MessageDialog.getInstance().showAceptar("La respuesta de Veraz ha sido: RECHAZAR.",
				MessageDialog.getCloseCommand());
	}

	private void aceptar(VerazResponseDto result, Label veraz) {
		veraz.addStyleName(estilos.get(0));
		estiloUsado = 0;
		MessageDialog.getInstance().setDialogTitle("Resultado Veraz");
		MessageDialog.getInstance().showAceptar(
				"La respuesta de Veraz ha sido: ACEPTAR. Puede continuar con la operaci\363n.",
				MessageDialog.getCloseCommand());
	}

	private void revisar(VerazResponseDto result, Label veraz) {
		veraz.addStyleName(estilos.get(1));
		estiloUsado = 1;
		if (result.getScoreDni() != SCORE_DNI_INEXISTENTE) {
			MessageDialog.getInstance().setDialogTitle("Resultado Veraz");
			MessageDialog.getInstance().showAceptar("La respuesta de Veraz ha sido: REVISAR.",
					MessageDialog.getCloseCommand());
		} else {
			MessageDialog.getInstance().setDialogTitle("Resultado Veraz");
			MessageDialog.getInstance().showAceptar(
					"La respuesta de Veraz ha sido A REVISAR. Documento inexistente",
					MessageDialog.getCloseCommand());
		}
	}
	
	public void setFacturaElectronicaOriginal(FacturaElectronicaDto facturaElectronica) {
		this.facturaElectronicaOriginal = facturaElectronica;
		if (facturaElectronica != null) {
			cuentaUIData.getEmailFacturaElectronica().setText(facturaElectronica.getEmail());
			if(facturaElectronica.isCargadaEnVantive())
				cuentaUIData.getEmailFacturaElectronica().setEnabled(false);
				
		}
	}
	
	

}
