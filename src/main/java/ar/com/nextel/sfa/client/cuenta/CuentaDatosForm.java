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
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
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

	private TitledPanel datosCuentaPanel = new TitledPanel(Sfa.constant().cuentaPanelTitle());
	private TitledPanel datosOppPanel = new TitledPanel(Sfa.constant().cuentaPanelTitle());
	private TitledPanel formaDePagoPanel = new TitledPanel(Sfa.constant().formaDePagoPanelTitle());;
	private TitledPanel vendedorPanel = new TitledPanel(Sfa.constant().vendedorPanelTitle());

	private HTML iconoLupa = IconFactory.vistaPreliminar();
	private HTML iconoEditarEstdo = IconFactory.lapiz();
	private CuentaPotencialDto oportunidadDto;
	private CuentaUIData camposTabDatos;
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
		camposTabDatos = new CuentaUIData();
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
				if ("".equals(camposTabDatos.getNumeroDocumento().getText())) {
					MessageDialog.getInstance().showAceptar(Sfa.constant().ERR_DIALOG_TITLE(),
							"Debe ingresar un número de documento", MessageDialog.getCloseCommand());
				} else {
					PersonaDto personaDto = getVerazSearch(camposTabDatos.getNumeroDocumento(),
							camposTabDatos.getTipoDocumento(), camposTabDatos.getSexo());
					inicializarVeraz(camposTabDatos.getVerazRta());
					CuentaRpcService.Util.getInstance().consultarVeraz(personaDto,
							new DefaultWaitCallback<VerazResponseDto>() {

								public void success(VerazResponseDto result) {
									if (result != null) {
										setearValoresRtaVeraz(result, camposTabDatos.getApellido(),
												camposTabDatos.getNombre(), camposTabDatos.getRazonSocial(),
												camposTabDatos.getSexo(), camposTabDatos.getVerazRta());
										camposTabDatos.exportarNombreApellidoARazonSocial();
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
		datosCuentaTable.setWidget(row, 0, camposTabDatos.getTipoDocLabel());
		datosCuentaTable.setWidget(row, 1, camposTabDatos.getTipoDocumento());
		datosCuentaTable.setWidget(row, 3, camposTabDatos.getNumDocLabel());
		datosCuentaTable.setWidget(row, 4, camposTabDatos.getNumeroDocumento());
		datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		row++;
		datosCuentaTable.setWidget(row, 0, camposTabDatos.getRazSocLabel());
		datosCuentaTable.setWidget(row, 1, camposTabDatos.getRazonSocial());
		datosCuentaTable.getFlexCellFormatter().setColSpan(row, 1, 4);
		datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		row++;
		if (camposTabDatos.getNomDivLabel().isVisible()) {
			datosCuentaTable.setWidget(row, 0, camposTabDatos.getNomDivLabel());
			datosCuentaTable.setWidget(row, 1, camposTabDatos.getNombreDivision());
			datosCuentaTable.getFlexCellFormatter().setColSpan(row, 1, 4);
			datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
			row++;
		} else {
			datosCuentaTable.removeRow(row);
		}
		datosCuentaTable.setWidget(row, 0, camposTabDatos.getNombreLabel());
		datosCuentaTable.setWidget(row, 1, camposTabDatos.getNombre());
		datosCuentaTable.setWidget(row, 3, camposTabDatos.getApellidoLabel());
		datosCuentaTable.setWidget(row, 4, camposTabDatos.getApellido());
		datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		row++;
		datosCuentaTable.setWidget(row, 0, camposTabDatos.getSexoLabel());
		datosCuentaTable.setWidget(row, 1, camposTabDatos.getSexo());
		datosCuentaTable.setWidget(row, 3, camposTabDatos.getFecNacLabel());
		datosCuentaTable.setWidget(row, 4, camposTabDatos.getFechaNacimientoGrid());
		datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		row++;
		datosCuentaTable.setWidget(row, 0, camposTabDatos.getContrLabel());
		datosCuentaTable.setWidget(row, 1, camposTabDatos.getContribuyente());
		datosCuentaTable.setWidget(row, 3, null);
		datosCuentaTable.setWidget(row, 4, null);
		datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		row++;
		if (camposTabDatos.getCargoLabel().isVisible()) {
			datosCuentaTable.setWidget(row, 0, camposTabDatos.getCargoLabel());
			datosCuentaTable.setWidget(row, 1, camposTabDatos.getCargo());
			datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
			row++;
		} else {
			datosCuentaTable.removeRow(row);
		}
		if (camposTabDatos.getIibbLabel().isVisible()) {
			datosCuentaTable.setWidget(row, 0, camposTabDatos.getIibbLabel());
			datosCuentaTable.setWidget(row, 1, camposTabDatos.getIibb());
			datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
			row++;
		} else {
			datosCuentaTable.removeRow(row);
		}
		datosCuentaTable.setWidget(row, 0, camposTabDatos.getProvAntLabel());
		datosCuentaTable.setWidget(row, 1, camposTabDatos.getProveedorAnterior());
		datosCuentaTable.setWidget(row, 3, camposTabDatos.getRubroLabel());
		datosCuentaTable.setWidget(row, 4, camposTabDatos.getRubro());
		datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		row++;
		datosCuentaTable.setWidget(row, 0, camposTabDatos.getClaseClLabel());
		datosCuentaTable.setWidget(row, 1, camposTabDatos.getClaseCliente());
		datosCuentaTable.setWidget(row, 3, camposTabDatos.getCategLabel());
		datosCuentaTable.setWidget(row, 4, camposTabDatos.getCategoria());
		datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		row++;
		datosCuentaTable.setWidget(row, 0, camposTabDatos.getCicloFacLabel());
		datosCuentaTable.setWidget(row, 1, camposTabDatos.getCicloFacturacion());
		datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);

		datosCuentaTable.setWidget(row, 2, iconoLupa);

		datosCuentaTable.setWidget(row, 3, camposTabDatos.getVerazLabel());
		inicializarVeraz(camposTabDatos.getVerazRta());
		datosCuentaTable.setWidget(row, 4, camposTabDatos.getVerazRta());
		if (showCamposUSE) {
			row++;
			datosCuentaTable.setWidget(row, 0, camposTabDatos.getUseLabel());
			datosCuentaTable.setWidget(row, 1, camposTabDatos.getUse());
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
		datosOppTable.setWidget(row, 0, camposTabDatos.getRazSocLabel());
		datosOppTable.setWidget(row, 1, camposTabDatos.getRazonSocial());
		datosOppTable.getFlexCellFormatter().setColSpan(row, 1, 3);
		datosOppTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		row++;
		datosOppTable.setWidget(row, 0, camposTabDatos.getNombreLabel());
		datosOppTable.setWidget(row, 1, camposTabDatos.getNombre());
		datosOppTable.setWidget(row, 2, camposTabDatos.getApellidoLabel());
		datosOppTable.setWidget(row, 3, camposTabDatos.getApellido());
		datosOppTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		datosOppTable.getFlexCellFormatter().setWidth(row, 2, ANCHO_TERCER_COLUMNA);
		row++;
		datosOppTable.setWidget(row, 0, camposTabDatos.getTipoDocLabel());
		datosOppTable.setWidget(row, 1, camposTabDatos.getOppTipoDocumento());
		datosOppTable.setWidget(row, 2, camposTabDatos.getNumDocLabel());
		datosOppTable.setWidget(row, 3, camposTabDatos.getNumeroDocumento());
		datosOppTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		datosOppTable.getFlexCellFormatter().setWidth(row, 2, ANCHO_TERCER_COLUMNA);
		row++;
		FlexTable tabla = new FlexTable();
		tabla.setWidth("50%");
		tabla.setWidget(0, 0, camposTabDatos.getOppEstado());
		tabla.setWidget(0, 1, iconoEditarEstdo);
		datosOppTable.setWidget(row, 0, camposTabDatos.getOppVencimientoLabel());
		datosOppTable.setWidget(row, 1, camposTabDatos.getOppVencimiento());
		datosOppTable.setWidget(row, 2, camposTabDatos.getOppEstadoLabel());
		datosOppTable.setWidget(row, 3, tabla);
		datosOppTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		datosOppTable.getFlexCellFormatter().setWidth(row, 2, ANCHO_TERCER_COLUMNA);
		row++;
		datosOppTable.setWidget(row, 0, camposTabDatos.getRubroLabel());
		datosOppTable.setWidget(row, 1, camposTabDatos.getOppRubro());
		datosOppTable.setWidget(row, 2, camposTabDatos.getOppCompetenciaProvLabel());
		datosOppTable.setWidget(row, 3, camposTabDatos.getOppCompetenciaProv());
		datosOppTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		datosOppTable.getFlexCellFormatter().setWidth(row, 2, ANCHO_TERCER_COLUMNA);
		row++;
		datosOppTable.setWidget(row, 0, camposTabDatos.getOppCompetenciaEquipoLabel());
		datosOppTable.setWidget(row, 1, camposTabDatos.getOppCompetenciaEquipo());
		datosOppTable.setWidget(row, 2, camposTabDatos.getOppTerminalesEstimadasLabel());
		datosOppTable.setWidget(row, 3, camposTabDatos.getOppTerminalesEstimadas());
		datosOppTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		datosOppTable.getFlexCellFormatter().setWidth(row, 2, "8%"/* ANCHO_TERCER_COLUMNA */);
		row++;
		datosOppTable.setWidget(row, 0, camposTabDatos.getOppVisitasLabel());
		datosOppTable.setWidget(row, 1, camposTabDatos.getOppVisitas());
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
		camposTabDatos.getObservaciones().addStyleName("obsTextAreaCuentaDatos");

		telefonoTable.setWidget(0, 0, camposTabDatos.getPrincLabel());
		telefonoTable.setWidget(0, 1, camposTabDatos.getTelPrincipalTextBox());
		telefonoTable.setText(0, 2, Sfa.constant().adicional());
		telefonoTable.setWidget(0, 3, camposTabDatos.getTelAdicionalTextBox());
		telefonoTable.setText(1, 0, Sfa.constant().celular());
		telefonoTable.setWidget(1, 1, camposTabDatos.getTelCelularTextBox());
		telefonoTable.setText(1, 2, Sfa.constant().fax());
		telefonoTable.setWidget(1, 3, camposTabDatos.getTelFaxTextBox());
		telefonoTable.setWidget(2, 0, camposTabDatos.getObservLabel());
		telefonoTable.setWidget(2, 1, camposTabDatos.getObservaciones());
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
		emailTable.setWidget(0, 1, camposTabDatos.getEmailPersonal());
		emailTable.setText(0, 2, Sfa.constant().laboral());
		emailTable.setWidget(0, 3, camposTabDatos.getEmailLaboral());
		emailTable.getFlexCellFormatter().setWidth(0, 0, ANCHO_PRIMER_COLUMNA);

		return emailPanel;
	}

	private Widget createFormaDePagoPanel() {
		formaDePagoTable = new FlexTable();
		formaDePagoTable.setWidth(ANCHO_TABLA_PANEL);
		formaDePagoTable.setWidget(0, 0, getCuentaBancariaPanel());
		formaDePagoTable.setWidget(1, 0, getTarjetaCreditoPanel());
		formaDePagoTable.setWidget(2, 0, getEfectivoPanel());
		formaDePagoPanel.add(formaDePagoTable);
		return formaDePagoPanel;
	}

	public FlexTable getEfectivoPanel() {
		efectivoTable.setWidth("100%");
		efectivoTable.addStyleName("layout");
		efectivoTable.setText(0, 0, Sfa.constant().modalidad());
		efectivoTable.setWidget(0, 1, camposTabDatos.getFormaPago());
		efectivoTable.getFlexCellFormatter().setHorizontalAlignment(0, 0, HorizontalPanel.ALIGN_LEFT);
		efectivoTable.getFlexCellFormatter().setHorizontalAlignment(0, 1, HorizontalPanel.ALIGN_LEFT);
		efectivoTable.getFlexCellFormatter().setWidth(0, 0, ANCHO_PRIMER_COLUMNA);
		return efectivoTable;
	}

	public FlexTable getCuentaBancariaPanel() {
		cuentaBancariaTable.setWidth("100%");
		cuentaBancariaTable.addStyleName("layout");
		cuentaBancariaTable.setVisible(false);

		cuentaBancariaTable.setText(0, 0, Sfa.constant().modalidad());
		cuentaBancariaTable.setWidget(0, 1, camposTabDatos.getFormaPago());
		cuentaBancariaTable.setText(0, 3, Sfa.constant().tipoCuenta());
		cuentaBancariaTable.setWidget(0, 4, camposTabDatos.getTipoCuentaBancaria());

		cuentaBancariaTable.setText(1, 0, Sfa.constant().cbu());
		cuentaBancariaTable.setWidget(1, 1, camposTabDatos.getCbu());
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
		tarjetaTable.setWidget(0, 1, camposTabDatos.getFormaPago());
		tarjetaTable.setText(0, 3, Sfa.constant().tarjetaTipo());
		tarjetaTable.setWidget(0, 4, camposTabDatos.getTipoTarjeta());

		tarjetaTable.setText(1, 0, Sfa.constant().nroTarjeta());
		tarjetaTable.setWidget(1, 1, camposTabDatos.getNumeroTarjeta());
		tarjetaTable.setText(1, 3, Sfa.constant().vtoMes());
		tarjetaTable.setWidget(1, 4, camposTabDatos.getMesVto());

		tarjetaTable.setText(2, 0, Sfa.constant().vtoAnio());
		tarjetaTable.setWidget(2, 1, camposTabDatos.getAnioVto());
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
		vendedorTable.setWidget(0, 1, camposTabDatos.getVendedorNombre());
		vendedorTable.setText(0, 2, null);
		vendedorTable.setWidget(0, 3, null);

		vendedorTable.setText(1, 0, Sfa.constant().telefono());
		vendedorTable.setWidget(1, 1, camposTabDatos.getVendedorTelefono());
		vendedorTable.setText(1, 2, Sfa.constant().canalVentas());
		vendedorTable.setWidget(1, 3, camposTabDatos.getTipoCanalVentas());

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
		usuario.setWidget(0, 1, camposTabDatos.getUsuario());
		fechaCreacion.setText(0, 0, Sfa.constant().fechaCreacion());
		fechaCreacion.setWidget(0, 1, camposTabDatos.getFechaCreacion());

		fechaUsuarioTable.setLeft(usuario);
		fechaUsuarioTable.setRight(fechaCreacion);

		return fechaUsuarioTable;
	}

	public void reset() {
		camposTabDatos.clean();
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
			camposTabDatos.getTipoDocumento().setSelectedItem(
					cuentaDto.getPersona().getDocumento().tipoDocumento);
			camposTabDatos.getNumeroDocumento().setText(cuentaDto.getPersona().getDocumento().getNumero());
			setDefaultComboSexo(cuentaDto.getPersona().getIdTipoDocumento(), cuentaDto.getPersona()
					.getDocumento().getNumero());
		} catch (Exception e) {
			// la cuenta puede llegar sin datos de documento... en ese caso que no haga nada.
		}
		camposTabDatos.getRazonSocial().setText(cuentaDto.getPersona().getRazonSocial());
		camposTabDatos.getNombre().setText(cuentaDto.getPersona().getNombre());
		camposTabDatos.getApellido().setText(cuentaDto.getPersona().getApellido());
		camposTabDatos.getSexo().setSelectedItem(cuentaDto.getPersona().getSexo());
		camposTabDatos.getRubro().setSelectedItem(cuentaDto.getRubro());
		if (cuentaDto.getPersona().getFechaNacimiento() != null)
			camposTabDatos.getFechaNacimiento().setSelectedDate(
					DATE_TIME_FORMAT.parse(cuentaDto.getPersona().getFechaNacimiento()));
		camposTabDatos.getContribuyente().setSelectedItem(cuentaDto.getTipoContribuyente());
		camposTabDatos.getCargo().setSelectedItem(cuentaDto.getPersona().getCargo());
		camposTabDatos.getIibb().setText(cuentaDto.getIibb());
		if (cuentaDto.getCategoriaCuenta().getDescripcion().equals(TipoCuentaEnum.DIV.getTipo())) {
			camposTabDatos.getNombreDivision().setText(((DivisionDto) cuentaDto).getNombre());
		}
		camposTabDatos.getProveedorAnterior().setSelectedItem(cuentaDto.getProveedorInicial());
		camposTabDatos.getCategoria()
				.setText(
						cuentaDto.getCategoriaCuenta() != null ? cuentaDto.getCategoriaCuenta()
								.getDescripcion() : "");
		camposTabDatos.getClaseCliente().setSelectedItem(cuentaDto.getClaseCuenta());
		camposTabDatos.getCicloFacturacion().setText(
				cuentaDto.getCicloFacturacion() != null ? cuentaDto.getCicloFacturacion().getDescripcion()
						: "");
		camposTabDatos.getUse().setText(cuentaDto.getUse());
	}

	public void cargarPanelDatosOportunidad(final CuentaPotencialDto oportunidadDto) {
		this.oportunidadDto = oportunidadDto;
		camposTabDatos.getRazonSocial().setText(oportunidadDto.getPersona().getRazonSocial());
		camposTabDatos.getNombre().setText(oportunidadDto.getPersona().getNombre());
		camposTabDatos.getApellido().setText(oportunidadDto.getPersona().getApellido());
		camposTabDatos.getOppTipoDocumento().setText(
				oportunidadDto.getPersona().getDocumento() != null ? oportunidadDto.getPersona()
						.getDocumento().tipoDocumento.getDescripcion() : "");
		camposTabDatos.getNumeroDocumento().setText(
				oportunidadDto.getPersona().getDocumento() != null ? oportunidadDto.getPersona()
						.getDocumento().getNumero() : "");
		camposTabDatos.getOppVencimiento().setText(
				oportunidadDto.getFechaVencimiento() != null ? DateTimeFormat.getMediumDateFormat().format(
						oportunidadDto.getFechaVencimiento()) : "");
		camposTabDatos.getOppRubro().setText(
				oportunidadDto.getRubro() != null ? oportunidadDto.getRubro().getDescripcion() : "");
		camposTabDatos.getOppTerminalesEstimadas().setText(
				oportunidadDto.getTerminalesEstimadas() != null ? oportunidadDto.getTerminalesEstimadas()
						.toString() : "");
		// popup motivo no cerrado
		camposTabDatos.getOppNroOpp().setText(oportunidadDto.getNumero());
		if (!oportunidadDto.isEsReserva()) {
			camposTabDatos.getEstadoOpp().setSelectedItem(
					((OportunidadNegocioDto) oportunidadDto).getEstadoJustificado().getEstado());
			DeferredCommand.addCommand(new IncrementalCommand() {
				public boolean execute() {
					if (camposTabDatos.getMotivosNoCierre() == null
							|| camposTabDatos.getMotivosNoCierre().isEmpty()) {
						return true;
					}
					camposTabDatos.getRadioGroupMotivos()
							.setValueChecked(
									((OportunidadNegocioDto) oportunidadDto).getEstadoJustificado()
											.getMotivo() != null ? ((OportunidadNegocioDto) oportunidadDto)
											.getEstadoJustificado().getMotivo().getId().toString() : "1");
					return false;
				}
			});
			camposTabDatos.getOppObservaciones().setText(
					((OportunidadNegocioDto) oportunidadDto).getEstadoJustificado().getObservacionesMotivo());
			camposTabDatos
					.getOppVisitas()
					.setText(
							((OportunidadNegocioDto) oportunidadDto).getCantidadVisitas() != null ? ((OportunidadNegocioDto) oportunidadDto)
									.getCantidadVisitas().toString()
									: "");
			camposTabDatos
					.getOppEstado()
					.setText(
							((OportunidadNegocioDto) oportunidadDto).getEstadoJustificado() != null ? ((OportunidadNegocioDto) oportunidadDto)
									.getEstadoJustificado().getEstado().getDescripcion()
									: "");
			camposTabDatos
					.getOppCompetenciaEquipo()
					.setText(
							((OportunidadNegocioDto) oportunidadDto).getCantidadEquiposCompetencia() != null ? ((OportunidadNegocioDto) oportunidadDto)
									.getCantidadEquiposCompetencia().toString()
									: "");
			camposTabDatos
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
				camposTabDatos.getTelPrincipalTextBox().getArea().setText(telefono.getArea());
				camposTabDatos.getTelPrincipalTextBox().getNumero().setText(telefono.getNumeroLocal());
				camposTabDatos.getTelPrincipalTextBox().getInterno().setText(telefono.getInterno());
			}
			if (tipoTelefono.getId() == TipoTelefonoEnum.PARTICULAR.getTipo()
					|| tipoTelefono.getId() == TipoTelefonoEnum.ADICIONAL.getTipo()) {
				camposTabDatos.getTelAdicionalTextBox().getArea().setText(telefono.getArea());
				camposTabDatos.getTelAdicionalTextBox().getNumero().setText(telefono.getNumeroLocal());
				camposTabDatos.getTelAdicionalTextBox().getInterno().setText(telefono.getInterno());
			}
			if (tipoTelefono.getId() == TipoTelefonoEnum.CELULAR.getTipo()) {
				camposTabDatos.getTelCelularTextBox().getArea().setText(telefono.getArea());
				camposTabDatos.getTelCelularTextBox().getNumero().setText(telefono.getNumeroLocal());
			}
			if (tipoTelefono.getId() == TipoTelefonoEnum.FAX.getTipo()) {
				camposTabDatos.getTelFaxTextBox().getArea().setText(telefono.getArea());
				camposTabDatos.getTelFaxTextBox().getNumero().setText(telefono.getNumeroLocal());
				camposTabDatos.getTelFaxTextBox().getInterno().setText(telefono.getInterno());
			}
		}
		camposTabDatos.getObservaciones().setText(cuentaDto.getObservacionesTelMail());
	}

	public void cargarPanelEmails(CuentaDto cuentaDto) {
		for (EmailDto email : cuentaDto.getPersona().getEmails()) {
			TipoEmailDto tipoEmail = email.getTipoEmail();
			if (TipoEmailEnum.PERSONAL.getTipo().equals(tipoEmail.getId())) {
				camposTabDatos.getEmailPersonal().setText(email.getEmail());
			}
			if (TipoEmailEnum.LABORAL.getTipo().equals(tipoEmail.getId())) {
				camposTabDatos.getEmailLaboral().setText(email.getEmail());
			}
		}
	}

	public void cargarPanelFormaPago(CuentaDto cuentaDto) {
		String id_formaPago = "";
		if (cuentaDto.getDatosPago().isEfectivo()) {
			datosPago = (DatosEfectivoDto) cuentaDto.getDatosPago();
			camposTabDatos.getFormaPago().setSelectedItem(((DatosEfectivoDto) datosPago).formaPagoAsociada());
			id_formaPago = TipoFormaPagoEnum.EFECTIVO.getTipo();
		} else if (cuentaDto.getDatosPago().isDebitoCuentaBancaria()) {
			datosPago = (DatosDebitoCuentaBancariaDto) cuentaDto.getDatosPago();
			camposTabDatos.getFormaPago().setSelectedItem(
					((DatosDebitoCuentaBancariaDto) datosPago).formaPagoAsociada());
			camposTabDatos.getTipoCuentaBancaria().selectByValue(
					((DatosDebitoCuentaBancariaDto) datosPago).getTipoCuentaBancaria().getItemValue());
			camposTabDatos.getCbu().setText(((DatosDebitoCuentaBancariaDto) datosPago).getCbu());
			id_formaPago = TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo();
		} else if (cuentaDto.getDatosPago().isDebitoTarjetaCredito()) {
			datosPago = (DatosDebitoTarjetaCreditoDto) cuentaDto.getDatosPago();
			camposTabDatos.getFormaPago().setSelectedItem(
					((DatosDebitoTarjetaCreditoDto) datosPago).formaPagoAsociada());
			camposTabDatos.getMesVto().setSelectedIndex(
					((DatosDebitoTarjetaCreditoDto) datosPago).getMesVencimientoTarjeta() - 1);
			camposTabDatos.getAnioVto().setText(
					Short.toString(((DatosDebitoTarjetaCreditoDto) datosPago).getAnoVencimientoTarjeta()));
			camposTabDatos.getTipoTarjeta().setSelectedItem(
					((DatosDebitoTarjetaCreditoDto) datosPago).getTipoTarjeta());
			camposTabDatos.getNumeroTarjeta().setText(((DatosDebitoTarjetaCreditoDto) datosPago).getNumero());
			id_formaPago = TipoFormaPagoEnum.TARJETA_CREDITO.getTipo();
		}
		setVisiblePanelFormaPagoYActualizarCamposObligatorios(id_formaPago);
	}

	public void setVisiblePanelFormaPagoYActualizarCamposObligatorios(String id_formaPago) {
		camposTabDatos.getCamposObligatoriosFormaPago().clear();
		camposTabDatos.getFormaPago().selectByValue(id_formaPago);
		if (id_formaPago.equals(TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo())) {
			cuentaBancariaTable.setWidget(0, 1, camposTabDatos.getFormaPago());
			efectivoTable.setVisible(false);
			cuentaBancariaTable.setVisible(true);
			tarjetaTable.setVisible(false);
			camposTabDatos.getCamposObligatoriosFormaPago().add(camposTabDatos.getCbu());
		} else if (id_formaPago.equals(TipoFormaPagoEnum.TARJETA_CREDITO.getTipo())) {
			tarjetaTable.setWidget(0, 1, camposTabDatos.getFormaPago());
			efectivoTable.setVisible(false);
			cuentaBancariaTable.setVisible(false);
			tarjetaTable.setVisible(true);
			camposTabDatos.getCamposObligatoriosFormaPago().add(camposTabDatos.getNumeroTarjeta());
			camposTabDatos.getCamposObligatoriosFormaPago().add(camposTabDatos.getAnioVto());
		} else { // if (id_formaPago.equals(TipoFormaPagoEnum.EFECTIVO.getTipo())) {
			efectivoTable.setWidget(0, 1, camposTabDatos.getFormaPago());
			efectivoTable.setVisible(true);
			cuentaBancariaTable.setVisible(false);
			tarjetaTable.setVisible(false);
		}
	}

	public void cargarPanelVendedor(CuentaDto cuentaDto) {
		camposTabDatos.getVendedorNombre().setText(
				cuentaDto.getVendedor() != null ? cuentaDto.getVendedor().getNombre() : "");
		camposTabDatos.getVendedorTelefono().setText(
				cuentaDto.getVendedor() != null ? cuentaDto.getVendedor().getTelefono() : "");
		camposTabDatos.getTipoCanalVentas().setSelectedItem(cuentaDto.getTipoCanalVentas());
	}

	public void cargarPanelUsuario(CuentaDto cuentaDto) {
		camposTabDatos.getUsuario().setText(cuentaDto.getNombreUsuarioCreacion());
		if (cuentaDto.getFechaCreacion() != null)
			camposTabDatos.getFechaCreacion().setText(
					DateTimeFormat.getMediumDateFormat().format(cuentaDto.getFechaCreacion()));
	}

	public void setAtributosCamposCuenta(CuentaDto cuentaDto) {

		camposTabDatos.enableFields();
		iconoLupa.setVisible(true);
		camposTabDatos.getVerazRta().setVisible(true);
		camposTabDatos.getVerazLabel().setVisible(true);
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
			campos.add(camposTabDatos.getRazonSocial());
		}
		campos.add(camposTabDatos.getTipoDocumento());
		campos.add(camposTabDatos.getNumeroDocumento());
		campos.add(camposTabDatos.getCategoria());
		campos.add(camposTabDatos.getClaseCliente());
		campos.add(camposTabDatos.getCicloFacturacion());

		FormUtils.disableFields(campos);

		boolean isVancuc = RegularExpressionConstants.isVancuc(cuentaDto.getCodigoVantive());
		iconoLupa.setVisible(isVancuc);
		camposTabDatos.getVerazLabel().setVisible(isVancuc);
		camposTabDatos.getVerazRta().setVisible(isVancuc);

		camposTabDatos.getIibb().setVisible(docTipoCUIL);
		camposTabDatos.getIibbLabel().setVisible(docTipoCUIL);

		camposTabDatos.getNombreDivision().setVisible(false);
		camposTabDatos.getNomDivLabel().setVisible(false);

		camposTabDatos.getUse().setVisible(!docTipoCUIL);
		camposTabDatos.getUseLabel().setVisible(!docTipoCUIL);

		camposTabDatos.getCargo().setVisible(
				cuentaDto.getPersona().getSexo().getItemValue().equals(
						Long.toString(SexoEnum.ORGANIZACION.getId())));
		camposTabDatos.getCargoLabel().setVisible(
				cuentaDto.getPersona().getSexo().getItemValue().equals(
						Long.toString(SexoEnum.ORGANIZACION.getId())));
		camposTabDatos.getObservaciones().setVisible(true);
		camposTabDatos.getObservLabel().setVisible(true);
		vendedorPanel.setVisible(false);

	}

	public void setAtributosCamposAlAgregarDivision(CuentaDto cuentaDto) {
		setAtributosCamposCuenta(cuentaDto != null ? cuentaDto : CuentaEdicionTabPanel.getInstance()
				.getCuenta2editDto());
		camposTabDatos.getNombreDivision().setVisible(true);
		camposTabDatos.getNomDivLabel().setVisible(true);
	}

	public void setAtributosCamposAlAgregarSuscriptor(CuentaDto cuentaDto) {
		setAtributosCamposCuenta(cuentaDto != null ? cuentaDto : CuentaEdicionTabPanel.getInstance()
				.getCuenta2editDto());
	}

	public void setAtributosCamposAlMostrarResuladoBusqueda(CuentaDto cuentaDto) {
		List<Widget> campos = new ArrayList<Widget>();
		if (cuentaDto.getCategoriaCuenta().getDescripcion().equals(TipoCuentaEnum.DIV.getTipo())) {
			setAtributosCamposAlAgregarDivision(cuentaDto);
			campos.add(camposTabDatos.getNombreDivision());
		} else if (cuentaDto.getCategoriaCuenta().getDescripcion().equals(TipoCuentaEnum.SUS.getTipo())) {
			setAtributosCamposAlAgregarSuscriptor(cuentaDto);
		} else {
			setAtributosCamposCuenta(cuentaDto);
		}
		campos.add(camposTabDatos.getNombre());
		campos.add(camposTabDatos.getApellido());
		campos.add(camposTabDatos.getRazonSocial());
		campos.add(camposTabDatos.getSexo());
		campos.add(camposTabDatos.getFechaNacimiento());
		campos.add(camposTabDatos.getProveedorAnterior());
		campos.add(camposTabDatos.getContribuyente());
		campos.add(camposTabDatos.getCargo());
		campos.add(camposTabDatos.getRubro());
		campos.add(camposTabDatos.getIibb());
		campos.add(camposTabDatos.getClaseCliente());
		campos.add(camposTabDatos.getCategoria());

		iconoLupa.setVisible(false);
		camposTabDatos.getVerazLabel().setVisible(false);
		camposTabDatos.getVerazRta().setVisible(false);

		campos.add(camposTabDatos.getTelPrincipalTextBox().getArea());
		campos.add(camposTabDatos.getTelPrincipalTextBox().getNumero());
		campos.add(camposTabDatos.getTelPrincipalTextBox().getInterno());
		campos.add(camposTabDatos.getObservaciones());

		// deshabilita los campos tel/email que NO estan en carga.
		for (TelefonoDto tel : cuentaDto.getPersona().getTelefonos()) {
			if (!tel.isEnCarga()) {
				if (tel.getTipoTelefono().getId() == TipoTelefonoEnum.ADICIONAL.getTipo()) {
					campos.add(camposTabDatos.getTelAdicionalTextBox().getArea());
					campos.add(camposTabDatos.getTelAdicionalTextBox().getNumero());
					campos.add(camposTabDatos.getTelAdicionalTextBox().getInterno());
				} else if (tel.getTipoTelefono().getId() == TipoTelefonoEnum.CELULAR.getTipo()) {
					campos.add(camposTabDatos.getTelCelularTextBox().getArea());
					campos.add(camposTabDatos.getTelCelularTextBox().getNumero());
				} else if (tel.getTipoTelefono().getId() == TipoTelefonoEnum.FAX.getTipo()) {
					campos.add(camposTabDatos.getTelFaxTextBox().getArea());
					campos.add(camposTabDatos.getTelFaxTextBox().getNumero());
					campos.add(camposTabDatos.getTelFaxTextBox().getInterno());
				}
			}
		}
		for (EmailDto email : cuentaDto.getPersona().getEmails()) {
			if (!email.isEnCarga()) {
				if (email.getTipoEmail().getId() == TipoEmailEnum.PERSONAL.getTipo()) {
					campos.add(camposTabDatos.getEmailPersonal());
				} else if (email.getTipoEmail().getId() == TipoEmailEnum.LABORAL.getTipo()) {
					campos.add(camposTabDatos.getEmailLaboral());
				}
			}
		}

		campos.add(camposTabDatos.getFormaPago());
		campos.add(camposTabDatos.getCbu());
		campos.add(camposTabDatos.getTipoCuentaBancaria());
		campos.add(camposTabDatos.getTipoTarjeta());
		campos.add(camposTabDatos.getNumeroTarjeta());
		campos.add(camposTabDatos.getAnioVto());
		campos.add(camposTabDatos.getMesVto());

		campos.add(camposTabDatos.getVendedorNombre());
		campos.add(camposTabDatos.getVendedorTelefono());
		campos.add(camposTabDatos.getTipoCanalVentas());

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

		camposTabDatos.disableFields();

		camposTabDatos.getOppEstado().setVisible(!cuentaPotencialDto.isEsReserva());
		camposTabDatos.getOppEstadoLabel().setVisible(!cuentaPotencialDto.isEsReserva());
		camposTabDatos.getOppCompetenciaEquipo().setVisible(!cuentaPotencialDto.isEsReserva());
		camposTabDatos.getOppCompetenciaEquipoLabel().setVisible(!cuentaPotencialDto.isEsReserva());
		camposTabDatos.getOppCompetenciaProv().setVisible(!cuentaPotencialDto.isEsReserva());
		camposTabDatos.getOppCompetenciaProvLabel().setVisible(!cuentaPotencialDto.isEsReserva());
		iconoEditarEstdo.setVisible(!cuentaPotencialDto.isEsReserva());

		FormUtils.disableFields(disabledFields);
		camposTabDatos.getObservaciones().setVisible(false);
		camposTabDatos.getObservLabel().setVisible(false);

		showPanelDatosCuenta = false;
	}

	public void setAtributosCamposSoloLectura() {
		camposTabDatos.disableFields();
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
			for (Widget label : camposTabDatos.getLabelsObligatorios()) {
				label.addStyleName("req");
			}
		} else {
			for (Widget label : camposTabDatos.getLabelsObligatorios()) {
				label.removeStyleName("req");
			}
		}
	}

	public boolean formularioDatosDirty() {
		boolean retorno = false;
		CuentaEdicionTabPanel cuentaTab = CuentaEdicionTabPanel.getInstance();

		// PANEL DATOS
		if ((FormUtils.fieldDirty(camposTabDatos.getRazonSocial(), cuentaTab.getCuenta2editDto().getPersona()
				.getRazonSocial()))
				|| (FormUtils.fieldDirty(camposTabDatos.getNombre(), cuentaTab.getCuenta2editDto()
						.getPersona().getNombre()))
				|| (FormUtils.fieldDirty(camposTabDatos.getApellido(), cuentaTab.getCuenta2editDto()
						.getPersona().getApellido()))
				|| (FormUtils.fieldDirty(camposTabDatos.getSexo(), cuentaTab.getCuenta2editDto().getPersona()
						.getSexo().getItemValue()))
				|| (FormUtils.fieldDirty(camposTabDatos.getFechaNacimiento(), cuentaTab.getCuenta2editDto()
						.getPersona().getFechaNacimiento() != null ? DateTimeFormat.getMediumDateFormat()
						.format(
								DATE_TIME_FORMAT.parse(cuentaTab.getCuenta2editDto().getPersona()
										.getFechaNacimiento())) : ""))
				|| (FormUtils.fieldDirty(camposTabDatos.getContribuyente(), cuentaTab.getCuenta2editDto()
						.getTipoContribuyente() != null ? cuentaTab.getCuenta2editDto()
						.getTipoContribuyente().getItemValue() : null))
				|| (FormUtils.fieldDirty(camposTabDatos.getProveedorAnterior(), cuentaTab.getCuenta2editDto()
						.getProveedorInicial().getItemValue()))
				|| (FormUtils.fieldDirty(camposTabDatos.getRubro(),
						cuentaTab.getCuenta2editDto().getRubro() != null ? cuentaTab.getCuenta2editDto()
								.getRubro().getItemValue() : null))
				|| (FormUtils.fieldDirty(camposTabDatos.getClaseCliente(), cuentaTab.getCuenta2editDto()
						.getClaseCuenta().getItemValue()))
				|| (FormUtils.fieldDirty(camposTabDatos.getCategoria(), cuentaTab.getCuenta2editDto()
						.getCategoriaCuenta().getItemText()))
				|| (FormUtils.fieldDirty(camposTabDatos.getObservaciones(), cuentaTab.getCuenta2editDto()
						.getObservacionesTelMail()))
				|| (FormUtils.fieldDirty(camposTabDatos.getIibb(), cuentaTab.getCuenta2editDto().getIibb())))
			return true;

		if (cuentaTab.getCuenta2editDto().getCategoriaCuenta().getDescripcion().equals(
				TipoCuentaEnum.DIV.getTipo())) {
			if ((camposTabDatos.getNombreDivision().getText() == null || camposTabDatos.getNombreDivision()
					.getText().equals(""))
					|| (FormUtils.fieldDirty(camposTabDatos.getNombreDivision(), ((DivisionDto) cuentaTab
							.getCuenta2editDto()).getNombre())))
				return true;
		}

		// PANEL FORMA DE PAGO
		if (!camposTabDatos.getFormaPago().getSelectedItemId().equals(
				cuentaTab.getCuenta2editDto().getFormaPago().getId() + "")) {
			return true;
		}

		if (camposTabDatos.getFormaPago().getSelectedItemId().equals(
				TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo())) {
			if (cuentaTab.getCuenta2editDto().getDatosPago() instanceof DatosDebitoCuentaBancariaDto) {
				if (FormUtils.fieldDirty(camposTabDatos.getCbu(), ((DatosDebitoCuentaBancariaDto) cuentaTab
						.getCuenta2editDto().getDatosPago()).getCbu())
						|| FormUtils.fieldDirty(camposTabDatos.getTipoCuentaBancaria(),
								((DatosDebitoCuentaBancariaDto) cuentaTab.getCuenta2editDto().getDatosPago())
										.getTipoCuentaBancaria().getItemValue())) {
					retorno = true;
				}
			} else {
				if ((!camposTabDatos.getCbu().getText().trim().equals("")))
					return true;
			}
		}
		if (camposTabDatos.getFormaPago().getSelectedItemId().equals(
				TipoFormaPagoEnum.TARJETA_CREDITO.getTipo())) {
			if (cuentaTab.getCuenta2editDto().getDatosPago() instanceof DatosDebitoTarjetaCreditoDto) {
				if ((FormUtils.fieldDirty(camposTabDatos.getNumeroTarjeta(),
						((DatosDebitoTarjetaCreditoDto) cuentaTab.getCuenta2editDto().getDatosPago())
								.getNumero()))
						|| (FormUtils.fieldDirty(camposTabDatos.getAnioVto(),
								((DatosDebitoTarjetaCreditoDto) cuentaTab.getCuenta2editDto().getDatosPago())
										.getAnoVencimientoTarjeta()
										+ ""))
						|| (FormUtils.fieldDirty(camposTabDatos.getMesVto(),
								((DatosDebitoTarjetaCreditoDto) cuentaTab.getCuenta2editDto().getDatosPago())
										.getMesVencimientoTarjeta()
										+ ""))
						|| (FormUtils.fieldDirty(camposTabDatos.getTipoTarjeta(),
								((DatosDebitoTarjetaCreditoDto) cuentaTab.getCuenta2editDto().getDatosPago())
										.getTipoTarjeta().getItemValue())))
					retorno = true;
			} else {
				if ((!camposTabDatos.getNumeroTarjeta().getText().trim().equals(""))
						|| (camposTabDatos.getAnioVto().getText().trim().endsWith("")))
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
				if ((FormUtils.fieldDirty(camposTabDatos.getTelPrincipalTextBox().getArea(), telefono
						.getArea()))
						|| (FormUtils.fieldDirty(camposTabDatos.getTelPrincipalTextBox().getNumero(),
								telefono.getNumeroLocal()))
						|| (FormUtils.fieldDirty(camposTabDatos.getTelPrincipalTextBox().getInterno(),
								telefono.getInterno())))
					retorno = true;
				noTienePrincipal = false;
			}
			if (tipoTelefono.getId() == TipoTelefonoEnum.PARTICULAR.getTipo()
					|| tipoTelefono.getId() == TipoTelefonoEnum.ADICIONAL.getTipo()) {
				if ((FormUtils.fieldDirty(camposTabDatos.getTelAdicionalTextBox().getArea(), telefono
						.getArea()))
						|| (FormUtils.fieldDirty(camposTabDatos.getTelAdicionalTextBox().getNumero(),
								telefono.getNumeroLocal()))
						|| (FormUtils.fieldDirty(camposTabDatos.getTelAdicionalTextBox().getInterno(),
								telefono.getInterno())))
					retorno = true;
				noTieneAdicional = false;
			}
			if (tipoTelefono.getId() == TipoTelefonoEnum.CELULAR.getTipo()) {
				if ((FormUtils
						.fieldDirty(camposTabDatos.getTelCelularTextBox().getArea(), telefono.getArea()))
						|| (FormUtils.fieldDirty(camposTabDatos.getTelCelularTextBox().getNumero(), telefono
								.getNumeroLocal())))
					retorno = true;
				noTieneCelular = false;
			}
			if (tipoTelefono.getId() == TipoTelefonoEnum.FAX.getTipo()) {
				if ((FormUtils.fieldDirty(camposTabDatos.getTelFaxTextBox().getArea(), telefono.getArea()))
						|| (FormUtils.fieldDirty(camposTabDatos.getTelFaxTextBox().getNumero(), telefono
								.getNumeroLocal()))
						|| (FormUtils.fieldDirty(camposTabDatos.getTelFaxTextBox().getInterno(), telefono
								.getInterno())))
					retorno = true;
				noTieneFax = false;
			}
		}
		// chequea si hay nuevos
		if (noTienePrincipal) {
			if ((!camposTabDatos.getTelPrincipalTextBox().getArea().getText().trim().equals(""))
					|| (!camposTabDatos.getTelPrincipalTextBox().getNumero().getText().trim().equals(""))
					|| (!camposTabDatos.getTelPrincipalTextBox().getInterno().getText().trim().equals("")))
				retorno = true;
		}
		if (noTieneAdicional) {
			if ((!camposTabDatos.getTelAdicionalTextBox().getArea().getText().trim().equals(""))
					|| (!camposTabDatos.getTelAdicionalTextBox().getNumero().getText().trim().equals(""))
					|| (!camposTabDatos.getTelAdicionalTextBox().getInterno().getText().trim().equals("")))
				retorno = true;
		}
		if (noTieneCelular) {
			if ((!camposTabDatos.getTelCelularTextBox().getArea().getText().trim().equals(""))
					|| (!camposTabDatos.getTelCelularTextBox().getNumero().getText().trim().equals("")))
				retorno = true;
		}
		if (noTieneFax) {
			if ((!camposTabDatos.getTelFaxTextBox().getArea().getText().trim().equals(""))
					|| (!camposTabDatos.getTelFaxTextBox().getNumero().getText().trim().equals(""))
					|| (!camposTabDatos.getTelFaxTextBox().getInterno().getText().trim().equals("")))
				retorno = true;
		}

		// PANEL MAILS
		boolean noTienePersonal = true;
		boolean noTieneLaboral = true;

		for (EmailDto email : cuentaTab.getCuenta2editDto().getPersona().getEmails()) {
			TipoEmailDto tipo = email.getTipoEmail();
			if (TipoEmailEnum.PERSONAL.getTipo().equals(tipo.getId())) {
				if ((FormUtils.fieldDirty(camposTabDatos.getEmailPersonal(), email.getEmail())))
					retorno = true;
				noTienePersonal = false;
			}
			if (TipoEmailEnum.LABORAL.getTipo().equals(tipo.getId())) {
				if ((FormUtils.fieldDirty(camposTabDatos.getEmailLaboral(), email.getEmail())))
					retorno = true;
				noTieneLaboral = false;
			}
		}
		if (noTienePersonal) {
			if (!camposTabDatos.getEmailPersonal().getText().equals(""))
				retorno = true;
		}
		if (noTieneLaboral) {
			if (!camposTabDatos.getEmailLaboral().getText().equals(""))
				retorno = true;
		}

		return retorno;
	}

	/**
	 * selecciona la opcion del combo sexo que corresponde al tipo de documento
	 */
	public void setDefaultComboSexo(long tipoDocId, String docNumero) {
		if (tipoDocId == TipoDocumentoEnum.LC.getTipo())
			camposTabDatos.getSexo().selectByValue(Long.toString(SexoEnum.FEMENINO.getId()));
		else if (tipoDocId == TipoDocumentoEnum.CUIT.getTipo()
				|| tipoDocId == TipoDocumentoEnum.CUIT_EXT.getTipo())
			camposTabDatos.getSexo().selectByValue(Long.toString(SexoEnum.getId(docNumero)));
		else
			camposTabDatos.getSexo().selectByValue(Long.toString(SexoEnum.MASCULINO.getId()));

	}

	/**
	 * 
	 * @return
	 */
	public List<String> validarCompletitud() {
		GwtValidator validator = CuentaEdicionTabPanel.getInstance().getValidator();
		validator.clear();

		camposObligatorios.clear();
		camposObligatorios = camposTabDatos.getCamposObligatorios();
		camposObligatorios.addAll(camposTabDatos.getCamposObligatoriosFormaPago());
		if (CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getCategoriaCuenta().getDescripcion()
				.equals(TipoCuentaEnum.DIV.getTipo())) {
			camposObligatorios.add(camposTabDatos.getNombreDivision());
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

		if (!camposTabDatos.getNombre().getText().equals("") && camposTabDatos.getNombre().isEnabled())
			validator.addTarget(camposTabDatos.getNombre()).alphabetic(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}", camposTabDatos.getNombre().getName()));
		if (!camposTabDatos.getApellido().getText().equals("") && camposTabDatos.getApellido().isEnabled())
			validator.addTarget(camposTabDatos.getApellido()).alphabetic(
					Sfa.constant().ERR_FORMATO()
							.replaceAll("\\{1\\}", camposTabDatos.getApellido().getName()));

		if (!camposTabDatos.getNombreDivision().getText().equals("")
				&& camposTabDatos.getNombreDivision().isEnabled())
			validator.addTarget(camposTabDatos.getNombreDivision()).alphabetic(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							camposTabDatos.getNombreDivision().getName()));

		if (!camposTabDatos.getFechaNacimiento().getTextBox().getText().equals("")
				&& camposTabDatos.getFechaNacimiento().getTextBox().isEnabled()) {
			if (!validator.addTarget(camposTabDatos.getFechaNacimiento().getTextBox()).date(
					Sfa.constant().ERR_FECHA_NO_VALIDA().replaceAll("\\{1\\}",
							camposTabDatos.getFechaNacimiento().getTextBox().getName())).hasErrorMsg())
				validator.addTarget(camposTabDatos.getFechaNacimiento().getTextBox()).dateSmallerToday(
						Sfa.constant().ERR_FECHA_NAC_MENOR_HOY());
		}
		if (!camposTabDatos.getEmailPersonal().getText().equals("")
				&& camposTabDatos.getEmailPersonal().isEnabled())
			validator.addTarget(camposTabDatos.getEmailPersonal()).mail(
					Sfa.constant().ERR_EMAIL_NO_VALIDO().replaceAll("\\{1\\}",
							camposTabDatos.getEmailPersonal().getName()));
		if (!camposTabDatos.getEmailLaboral().getText().equals("")
				&& camposTabDatos.getEmailLaboral().isEnabled())
			validator.addTarget(camposTabDatos.getEmailLaboral()).mail(
					Sfa.constant().ERR_EMAIL_NO_VALIDO().replaceAll("\\{1\\}",
							camposTabDatos.getEmailLaboral().getName()));

		if (!camposTabDatos.getTelPrincipalTextBox().getArea().getText().equals("")
				&& camposTabDatos.getTelPrincipalTextBox().getArea().isEnabled())
			validator.addTarget(camposTabDatos.getTelPrincipalTextBox().getArea()).numericPositive(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							camposTabDatos.getTelPrincipalTextBox().getArea().getName()));
		if (!camposTabDatos.getTelPrincipalTextBox().getNumero().getText().equals("")
				&& camposTabDatos.getTelPrincipalTextBox().getNumero().isEnabled())
			validator.addTarget(camposTabDatos.getTelPrincipalTextBox().getNumero()).numericPositive(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							camposTabDatos.getTelPrincipalTextBox().getNumero().getName()));
		if (!camposTabDatos.getTelPrincipalTextBox().getInterno().getText().equals("")
				&& camposTabDatos.getTelPrincipalTextBox().getInterno().isEnabled())
			validator.addTarget(camposTabDatos.getTelPrincipalTextBox().getInterno()).numericPositive(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							camposTabDatos.getTelPrincipalTextBox().getInterno().getName()));
		if (!camposTabDatos.getTelAdicionalTextBox().getArea().getText().equals("")
				&& camposTabDatos.getTelAdicionalTextBox().getArea().isEnabled())
			validator.addTarget(camposTabDatos.getTelAdicionalTextBox().getArea()).numericPositive(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							camposTabDatos.getTelAdicionalTextBox().getArea().getName()));
		if (!camposTabDatos.getTelAdicionalTextBox().getNumero().getText().equals("")
				&& camposTabDatos.getTelAdicionalTextBox().getNumero().isEnabled())
			validator.addTarget(camposTabDatos.getTelAdicionalTextBox().getNumero()).numericPositive(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							camposTabDatos.getTelAdicionalTextBox().getNumero().getName()));
		if (!camposTabDatos.getTelAdicionalTextBox().getInterno().getText().equals("")
				&& camposTabDatos.getTelAdicionalTextBox().getInterno().isEnabled())
			validator.addTarget(camposTabDatos.getTelAdicionalTextBox().getInterno()).numericPositive(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							camposTabDatos.getTelAdicionalTextBox().getInterno().getName()));
		if (!camposTabDatos.getTelFaxTextBox().getArea().getText().equals("")
				&& camposTabDatos.getTelFaxTextBox().getArea().isEnabled())
			validator.addTarget(camposTabDatos.getTelFaxTextBox().getArea()).numericPositive(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							camposTabDatos.getTelFaxTextBox().getArea().getName()));
		if (!camposTabDatos.getTelFaxTextBox().getNumero().getText().equals("")
				&& camposTabDatos.getTelFaxTextBox().getNumero().isEnabled())
			validator.addTarget(camposTabDatos.getTelFaxTextBox().getNumero()).numericPositive(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							camposTabDatos.getTelFaxTextBox().getNumero().getName()));
		if (!camposTabDatos.getTelFaxTextBox().getInterno().getText().equals("")
				&& camposTabDatos.getTelFaxTextBox().getInterno().isEnabled())
			validator.addTarget(camposTabDatos.getTelFaxTextBox().getInterno()).numericPositive(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							camposTabDatos.getTelFaxTextBox().getInterno().getName()));
		if (!camposTabDatos.getTelCelularTextBox().getArea().getText().equals("")
				&& camposTabDatos.getTelCelularTextBox().getArea().isEnabled())
			validator.addTarget(camposTabDatos.getTelCelularTextBox().getArea()).numericPositive(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							camposTabDatos.getTelCelularTextBox().getArea().getName()));
		if (!camposTabDatos.getTelCelularTextBox().getNumero().getText().equals("")
				&& camposTabDatos.getTelCelularTextBox().getNumero().isEnabled())
			validator.addTarget(camposTabDatos.getTelCelularTextBox().getNumero()).numericPositive(
					Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}",
							camposTabDatos.getTelCelularTextBox().getNumero().getName()));

		if (camposTabDatos.getFormaPago().getSelectedItemId().equals(
				TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo())) {
			if (!camposTabDatos.getCbu().getText().equals("") && camposTabDatos.getCbu().isEnabled()) {
				if (camposTabDatos.getCbu().getText().length() < 22) {
					validator.addError(Sfa.constant().ERR_NUMERO_CBU());
				}
			}
		}

		if (camposTabDatos.getFormaPago().getSelectedItemId().equals(
				TipoFormaPagoEnum.TARJETA_CREDITO.getTipo())) {
			if (!camposTabDatos.getNumeroTarjeta().getText().equals("")
					&& camposTabDatos.getNumeroTarjeta().isEnabled()) {
				int cantidadDigitosNroTarjeta = 16; // VIS - MASTER - CABAL
				if (camposTabDatos.getTipoTarjeta().getSelectedItemId().equals(TipoTarjetaEnum.AMX.getId())) {
					cantidadDigitosNroTarjeta = 15;
				} else if (camposTabDatos.getTipoTarjeta().getSelectedItemId().equals(
						TipoTarjetaEnum.DIN.getId())) {
					cantidadDigitosNroTarjeta = 14;
				}
				if (camposTabDatos.getNumeroTarjeta().getText().length() < cantidadDigitosNroTarjeta) {
					validator.addError(Sfa.constant().ERR_NUMERO_DE_TARJETA().replaceAll("\\{1\\}",
							cantidadDigitosNroTarjeta + ""));
				}
			}

			if (!camposTabDatos.getAnioVto().getText().equals("") && camposTabDatos.getAnioVto().isEnabled()) {
				try {
					int valor = Integer.parseInt(camposTabDatos.getAnioVto().getText());
					if (camposTabDatos.getAnioVto().getText().length() < 4
							|| valor < camposTabDatos.getCurrentYear()
							|| valor > (camposTabDatos.getCurrentYear() + 5)) {
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
		if (!camposTabDatos.getNumeroTarjeta().getText().equals("")) {
			CuentaRpcService.Util.getInstance().validarTarjeta(camposTabDatos.getNumeroTarjeta().getText(),
					new Integer(camposTabDatos.getMesVto().getSelectedItemId()),
					new Integer(camposTabDatos.getAnioVto().getText()),
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
							camposTabDatos.getNumeroTarjeta().getName()));
		}
	}

	/**
	 * 
	 */
	public void cambiarVisibilidadCamposSegunSexo() {
		camposTabDatos.getCargoLabel().setVisible(
				camposTabDatos.getSexo().getSelectedItemId().equals(
						Long.toString(SexoEnum.ORGANIZACION.getId())));
		camposTabDatos.getCargo().setVisible(
				camposTabDatos.getSexo().getSelectedItemId().equals(
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
			((DivisionDto) CuentaEdicionTabPanel.getInstance().getCuenta2editDto()).setNombre(camposTabDatos
					.getNombreDivision().getText());
		}

		PersonaDto personaDto = CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getPersona();

		// panel datos
		personaDto.setRazonSocial(camposTabDatos.getRazonSocial().getText());
		personaDto.setNombre(camposTabDatos.getNombre().getText());
		personaDto.setApellido(camposTabDatos.getApellido().getText());
		personaDto.setSexo(new SexoDto(Long.parseLong(camposTabDatos.getSexo().getSelectedItemId()),
				camposTabDatos.getSexo().getSelectedItemText()));
		if (!camposTabDatos.getFechaNacimiento().getTextBox().getText().equals(""))
			personaDto.setFechaNacimiento(DATE_TIME_FORMAT.format(camposTabDatos.getFechaNacimiento()
					.getSelectedDate()));
		CuentaEdicionTabPanel.getInstance().getCuenta2editDto().setTipoContribuyente(
				new TipoContribuyenteDto(Long
						.parseLong(camposTabDatos.getContribuyente().getSelectedItemId()), camposTabDatos
						.getContribuyente().getSelectedItemText()));
		long selectedSexo = camposTabDatos.getSexo().getSelectedItemId() != null
				&& !"".equals(camposTabDatos.getSexo().getSelectedItemId().trim()) ? Long
				.parseLong(camposTabDatos.getSexo().getSelectedItemId()) : -1;
		if ( selectedSexo == SexoEnum.ORGANIZACION.getId()) {
			CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getPersona().setCargo(
					new CargoDto(Long.parseLong(camposTabDatos.getCargo().getSelectedItemId()),
							camposTabDatos.getCargo().getSelectedItemText()));
			CuentaEdicionTabPanel.getInstance().getCuenta2editDto().setIibb(
					camposTabDatos.getIibb().getText());
		}
		CuentaEdicionTabPanel.getInstance().getCuenta2editDto().setProveedorInicial(
				new ProveedorDto(Long.parseLong(camposTabDatos.getProveedorAnterior().getSelectedItemId()),
						camposTabDatos.getProveedorAnterior().getSelectedItemText()));
		CuentaEdicionTabPanel.getInstance().getCuenta2editDto().setRubro(
				new RubroDto(Long.parseLong(camposTabDatos.getRubro().getSelectedItemId()), camposTabDatos
						.getRubro().getSelectedItemText()));

		// Panel Telefono/Fax
		List<TelefonoDto> phonos = new ArrayList<TelefonoDto>();
		Long principalId = getIdTelefono(CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getPersona()
				.getTelefonos(), TipoTelefonoEnum.PRINCIPAL.getDesc());
		if (!camposTabDatos.getTelPrincipalTextBox().getNumero().getText().equals("") || principalId != null) {
			String principalText = camposTabDatos.getTelPrincipalTextBox().getNumero().getText().trim();
			phonos.add(new TelefonoDto(principalId, !principalText.equals("") ? camposTabDatos
					.getTelPrincipalTextBox().getArea().getText().trim() : "",
					!principalText.equals("") ? camposTabDatos.getTelPrincipalTextBox().getInterno()
							.getText().trim() : "", principalText, personaDto.getId(), Boolean.TRUE,
					new TipoTelefonoDto(TipoTelefonoEnum.PRINCIPAL.getTipo(), TipoTelefonoEnum.PRINCIPAL
							.getDesc())));
		}
		Long adicionalId = getIdTelefono(CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getPersona()
				.getTelefonos(), TipoTelefonoEnum.ADICIONAL.getDesc());
		if (!camposTabDatos.getTelAdicionalTextBox().getNumero().getText().equals("") || adicionalId != null) {
			String adicionalText = camposTabDatos.getTelAdicionalTextBox().getNumero().getText().trim();
			phonos.add(new TelefonoDto(adicionalId, !adicionalText.equals("") ? camposTabDatos
					.getTelAdicionalTextBox().getArea().getText().trim() : "",
					!adicionalText.equals("") ? camposTabDatos.getTelAdicionalTextBox().getInterno()
							.getText().trim() : "", adicionalText, personaDto.getId(), Boolean.FALSE,
					new TipoTelefonoDto(TipoTelefonoEnum.ADICIONAL.getTipo(), TipoTelefonoEnum.ADICIONAL
							.getDesc())));
		}
		Long celularId = getIdTelefono(CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getPersona()
				.getTelefonos(), TipoTelefonoEnum.CELULAR.getDesc());
		if (!camposTabDatos.getTelCelularTextBox().getNumero().getText().equals("") || celularId != null) {
			String celularText = camposTabDatos.getTelCelularTextBox().getNumero().getText().trim();
			phonos.add(new TelefonoDto(celularId, !celularText.equals("") ? camposTabDatos
					.getTelCelularTextBox().getArea().getText().trim() : "", "", celularText, personaDto
					.getId(), Boolean.FALSE, new TipoTelefonoDto(TipoTelefonoEnum.CELULAR.getTipo(),
					TipoTelefonoEnum.CELULAR.toString())));
		}
		Long faxId = getIdTelefono(CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getPersona()
				.getTelefonos(), TipoTelefonoEnum.FAX.getDesc());
		if (!camposTabDatos.getTelFaxTextBox().getNumero().getText().equals("") || faxId != null) {
			String faxText = camposTabDatos.getTelFaxTextBox().getNumero().getText().trim();
			phonos.add(new TelefonoDto(faxId, !faxText.equals("") ? camposTabDatos.getTelFaxTextBox()
					.getArea().getText().trim() : "", !faxText.equals("") ? camposTabDatos.getTelFaxTextBox()
					.getInterno().getText().trim() : "", faxText, personaDto.getId(), Boolean.FALSE,
					new TipoTelefonoDto(TipoTelefonoEnum.FAX.getTipo(), TipoTelefonoEnum.FAX.toString())));
		}
		CuentaEdicionTabPanel.getInstance().getCuenta2editDto().setObservacionesTelMail(
				camposTabDatos.getObservaciones().getText());
		personaDto.setTelefonos(phonos);

		// Panel Emails
		List<EmailDto> mails = new ArrayList<EmailDto>();
		Long personalId = getIdEmail(CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getPersona()
				.getEmails(), TipoEmailEnum.PERSONAL.getTipo());
		if (!camposTabDatos.getEmailPersonal().getText().equals("") || personalId != null) {
			TipoEmailDto tipoEmail = new TipoEmailDto(TipoEmailEnum.PERSONAL.getTipo(),
					TipoEmailEnum.PERSONAL.getDesc());
			mails.add(new EmailDto(personalId, camposTabDatos.getEmailPersonal().getText().trim(), false,
					tipoEmail));
		}
		Long laboralId = getIdEmail(CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getPersona()
				.getEmails(), TipoEmailEnum.LABORAL.getTipo());
		if (!camposTabDatos.getEmailLaboral().getText().equals("") || laboralId != null) {
			mails.add(new EmailDto(laboralId, camposTabDatos.getEmailLaboral().getText().trim(), false,
					new TipoEmailDto(TipoEmailEnum.LABORAL.getTipo(), TipoEmailEnum.LABORAL.getDesc())));
		}
		personaDto.setEmails(mails);

		// Panel Formas de Pago
		DatosPagoDto datosPago = null;
		if (camposTabDatos.getFormaPago().getSelectedItemId().equals(
				TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo())) {
			CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getFormaPago().setId(
					Long.parseLong(TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo()));
			datosPago = new DatosDebitoCuentaBancariaDto();
			((DatosDebitoCuentaBancariaDto) datosPago).setId(CuentaEdicionTabPanel.getInstance()
					.getCuenta2editDto().getDatosPago().getId());
			((DatosDebitoCuentaBancariaDto) datosPago).setFormaPagoAsociada(CuentaEdicionTabPanel
					.getInstance().getCuenta2editDto().getFormaPago());
			((DatosDebitoCuentaBancariaDto) datosPago).setCbu(camposTabDatos.getCbu().getText());
			((DatosDebitoCuentaBancariaDto) datosPago).setTipoCuentaBancaria(new TipoCuentaBancariaDto(Long
					.parseLong(camposTabDatos.getTipoCuentaBancaria().getSelectedItemId()), camposTabDatos
					.getTipoCuentaBancaria().getSelectedItemText(), camposTabDatos.getTipoCuentaBancaria()
					.getSelectedItemText()));
		} else if (camposTabDatos.getFormaPago().getSelectedItemId().equals(
				TipoFormaPagoEnum.TARJETA_CREDITO.getTipo())) {
			datosPago = new DatosDebitoTarjetaCreditoDto();
			((DatosDebitoTarjetaCreditoDto) datosPago).setId(CuentaEdicionTabPanel.getInstance()
					.getCuenta2editDto().getDatosPago().getId());
			CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getFormaPago().setId(
					Long.parseLong(TipoFormaPagoEnum.TARJETA_CREDITO.getTipo()));
			((DatosDebitoTarjetaCreditoDto) datosPago).setFormaPagoAsociada(CuentaEdicionTabPanel
					.getInstance().getCuenta2editDto().getFormaPago());
			((DatosDebitoTarjetaCreditoDto) datosPago).setNumero(camposTabDatos.getNumeroTarjeta().getText());
			((DatosDebitoTarjetaCreditoDto) datosPago).setAnoVencimientoTarjeta(Short
					.parseShort(camposTabDatos.getAnioVto().getText()));
			((DatosDebitoTarjetaCreditoDto) datosPago).setMesVencimientoTarjeta(Short
					.parseShort(camposTabDatos.getMesVto().getSelectedItemId()));
			((DatosDebitoTarjetaCreditoDto) datosPago).setTipoTarjeta(new TipoTarjetaDto(Long
					.parseLong(camposTabDatos.getTipoTarjeta().getSelectedItemId()), camposTabDatos
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
		return camposTabDatos;
	}

	public void setCamposTabDatos(CuentaUIData camposTabDatos) {
		this.camposTabDatos = camposTabDatos;
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

}
