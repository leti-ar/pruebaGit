package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.CargoDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DatosDebitoCuentaBancariaDto;
import ar.com.nextel.sfa.client.dto.DatosDebitoTarjetaCreditoDto;
import ar.com.nextel.sfa.client.dto.DatosEfectivoDto;
import ar.com.nextel.sfa.client.dto.DatosPagoDto;
import ar.com.nextel.sfa.client.dto.DocumentoDto;
import ar.com.nextel.sfa.client.dto.EmailDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.ProveedorDto;
import ar.com.nextel.sfa.client.dto.RubroDto;
import ar.com.nextel.sfa.client.dto.SexoDto;
import ar.com.nextel.sfa.client.dto.TarjetaCreditoValidatorResultDto;
import ar.com.nextel.sfa.client.dto.TelefonoDto;
import ar.com.nextel.sfa.client.dto.TipoContribuyenteDto;
import ar.com.nextel.sfa.client.dto.TipoCuentaBancariaDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.dto.TipoEmailDto;
import ar.com.nextel.sfa.client.dto.TipoTarjetaDto;
import ar.com.nextel.sfa.client.dto.TipoTelefonoDto;
import ar.com.nextel.sfa.client.dto.VerazResponseDto;
import ar.com.nextel.sfa.client.enums.PermisosEnum;
import ar.com.nextel.sfa.client.enums.SexoEnum;
import ar.com.nextel.sfa.client.enums.TipoDocumentoEnum;
import ar.com.nextel.sfa.client.enums.TipoEmailEnum;
import ar.com.nextel.sfa.client.enums.TipoFormaPagoEnum;
import ar.com.nextel.sfa.client.enums.TipoTelefonoEnum;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.util.FormUtils;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.DualPanel;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.TitledPanel;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;
import ar.com.snoop.gwt.commons.client.window.MessageWindow;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CuentaDatosForm extends Composite {

	private static CuentaDatosForm instance = new CuentaDatosForm();
	
	private DualPanel fechaUsuarioTable;
	private FlexTable usuario;
	private FlexTable fechaCreacion;
	private FlexTable mainPanel           = new FlexTable();
	private FlexTable datosCuentaTable    = new FlexTable();
	private FlexTable emailTable          = new FlexTable();
	private FlexTable telefonoTable       = new FlexTable();
	private FlexTable formaDePagoTable    = new FlexTable();
	private FlexTable vendedorTable       = new FlexTable();
	private FlexTable efectivoTable       = new FlexTable();
	private FlexTable cuentaBancariaTable = new FlexTable();
	private FlexTable tarjetaTable        = new FlexTable();
	private HTML iconoLupa = IconFactory.vistaPreliminar();
	private TitledPanel vendedorPanel     = new TitledPanel(Sfa.constant().vendedorPanelTitle());
	private CuentaUIData camposTabDatos   = new CuentaUIData();
	private DatosPagoDto datosPago;
	private List <Widget>camposObligatorios = new ArrayList<Widget>();
	private TitledPanel datosCuentaPanel = new TitledPanel(Sfa.constant().cuentaPanelTitle());
	private boolean showCamposUSE = false;
	private static final String ANCHO_PRIMER_COLUMNA = "11%";
	private static final String ANCHO_TABLA_PANEL    = "80%";
	
	private List<String> estilos = new ArrayList<String>();
	private int estiloUsado = 0;
	
	public static CuentaDatosForm getInstance() {
		return instance;
	}
	
	private CuentaDatosForm() {
		initWidget(mainPanel);
		mainPanel.setWidth("100%");
		mainPanel.setWidget(0,0,createDatosCuentaPanel());
		mainPanel.setWidget(1,0,createTelefonoPanel());
		mainPanel.setWidget(2,0,createEmailPanel());
		mainPanel.setWidget(3,0,createFormaDePagoPanel());
		mainPanel.setWidget(4,0,createVendedorPanel());
		mainPanel.setWidget(5,0,createFechaUsuarioPanel());
	}
	
	private Widget createDatosCuentaPanel() {
		camposTabDatos.getRazonSocial().setWidth("90%");
		armarTablaPanelDatos();
		datosCuentaPanel.add(datosCuentaTable);
		return datosCuentaPanel;
	}

	public void	armarTablaPanelDatos() {
		datosCuentaTable.clear();
		datosCuentaTable.setCellSpacing(7);
		datosCuentaTable.setWidth(ANCHO_TABLA_PANEL);
		datosCuentaTable.getFlexCellFormatter().setColSpan(1, 1, 4);

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
		datosCuentaTable.setWidget(row, 3, camposTabDatos.getNomDivLabel());
		datosCuentaTable.setWidget(row, 4, camposTabDatos.getNombreDivision());
		datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
		row++;
		if(camposTabDatos.getCargoLabel().isVisible()) {
			datosCuentaTable.setWidget(row, 0, camposTabDatos.getCargoLabel());
			datosCuentaTable.setWidget(row, 1, camposTabDatos.getCargo());
			datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
			row++;
		}
		if(camposTabDatos.getIibbLabel().isVisible()) {
			datosCuentaTable.setWidget(row, 0, camposTabDatos.getIibbLabel());
			datosCuentaTable.setWidget(row, 1, camposTabDatos.getIibb());
			datosCuentaTable.getFlexCellFormatter().setWidth(row, 0, ANCHO_PRIMER_COLUMNA);
			row++;
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
		iconoLupa.addClickListener(new ClickListener() {
			public void onClick (Widget sender) {
				PersonaDto personaDto = getVerazSearch(camposTabDatos.getNumeroDocumento(), 
						camposTabDatos.getTipoDocumento(), camposTabDatos.getSexo());
				inicializarVeraz(camposTabDatos.getVerazRta());
				CuentaRpcService.Util.getInstance().consultarVeraz(personaDto, 
						new DefaultWaitCallback<VerazResponseDto>() {

					public void success(VerazResponseDto result) {
						if (result != null) {
							setearValoresRtaVeraz(result, camposTabDatos.getApellido(), camposTabDatos.getNombre(), 
									camposTabDatos.getRazonSocial(), camposTabDatos.getSexo(), camposTabDatos.getVerazRta());
						}
					}
				});
			}
		});	
		
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

	public void inicializarVeraz(Label verazLabel) {
		estilos.add("verazAceptar");
        estilos.add("verazRevisar");
        estilos.add("verazRechazar");
        verazLabel.setText("");
        verazLabel.removeStyleName(estilos.get(estiloUsado));
	}

	public PersonaDto getVerazSearch(TextBox numDoc, ListBox tipoDoc, ListBox sexo) {
		//if ((numDoc!=null) && (tipoDoc!=null) && (sexo!=null)) {
			PersonaDto personaDto = new PersonaDto();
			DocumentoDto documentoDto = new DocumentoDto(numDoc.getText(), (TipoDocumentoDto) tipoDoc.getSelectedItem());
			personaDto.setDocumento(documentoDto);
			personaDto.setIdTipoDocumento(documentoDto.getTipoDocumento().getId());
			personaDto.setSexo((SexoDto) sexo.getSelectedItem());
		//} 
		return personaDto;
	}
	
	private Widget createTelefonoPanel() {
		telefonoTable.setWidth(ANCHO_TABLA_PANEL);
		telefonoTable.addStyleName("layout");
		TitledPanel telefonoPanel = new TitledPanel(Sfa.constant().telefonoPanelTitle());
		telefonoPanel.add(telefonoTable);
		camposTabDatos.getObservaciones().addStyleName("obsTextAreaCuentaDatos");
		
		telefonoTable.setText(0, 0, Sfa.constant().principal());
		telefonoTable.setWidget(0, 1, camposTabDatos.getTelPrincipalTextBox());
		telefonoTable.setText(0, 2, Sfa.constant().adicional());
		telefonoTable.setWidget(0, 3, camposTabDatos.getTelAdicionalTextBox());
		telefonoTable.setText(1, 0, Sfa.constant().celular());
		telefonoTable.setWidget(1, 1, camposTabDatos.getTelCelularTextBox());
		telefonoTable.setText(1, 2, Sfa.constant().fax());
		telefonoTable.setWidget(1, 3, camposTabDatos.getTelFaxTextBox());
		telefonoTable.setText(2, 0, Sfa.constant().observaciones());
		telefonoTable.setWidget(2, 1, camposTabDatos.getObservaciones());
		telefonoTable.getFlexCellFormatter().setColSpan(2, 1, 3);
		telefonoTable.getFlexCellFormatter().addStyleName(0, 0, "req");
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
		
		TitledPanel formaDePagoPanel = new TitledPanel(Sfa.constant().formaDePagoPanelTitle());
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
		tarjetaTable.setWidget(2, 3, camposTabDatos.getValidarTarjeta());
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

	public void ponerDatosBusquedaEnFormulario(CuentaDto cuentaDto) {
		cargarPanelDatos(cuentaDto);
		cargarPanelTelefonoFax(cuentaDto);
		cargarPanelEmails(cuentaDto);
		cargarPanelFormaPago(cuentaDto);
		cargarPanelVendedor(cuentaDto);
		cargarPanelUsuario(cuentaDto);
	}
	
	public void cargarPanelDatos(CuentaDto cuentaDto) {
		if (cuentaDto.getPersona().getDocumento()!=null) {
			camposTabDatos.getTipoDocumento().setSelectedItem(cuentaDto.getPersona().getDocumento().tipoDocumento) ;
			camposTabDatos.getNumeroDocumento().setText(cuentaDto.getPersona().getDocumento().getNumero());
		}
		setDefaultComboSexo(cuentaDto.getPersona().getIdTipoDocumento(),cuentaDto.getPersona().getDocumento().getNumero());
		camposTabDatos.getRazonSocial().setText(cuentaDto.getPersona().getRazonSocial());
		camposTabDatos.getNombre().setText(cuentaDto.getPersona().getNombre());
		camposTabDatos.getApellido().setText(cuentaDto.getPersona().getApellido());
		camposTabDatos.getSexo().setSelectedItem(cuentaDto.getPersona().getSexo());
		camposTabDatos.getRubro().setSelectedItem(cuentaDto.getRubro());
		camposTabDatos.getFechaNacimiento().setSelectedDate(cuentaDto.getPersona().getFechaNacimiento());
		camposTabDatos.getContribuyente().setSelectedItem(cuentaDto.getTipoContribuyente());
		camposTabDatos.getCargo().setSelectedItem(cuentaDto.getPersona().getCargo());
		camposTabDatos.getIibb().setText(cuentaDto.getIibb());
		camposTabDatos.getNombreDivision().setText("TODO");
		camposTabDatos.getProveedorAnterior().setSelectedItem(cuentaDto.getProveedorInicial());
		camposTabDatos.getCategoria().setText(cuentaDto.getCategoriaCuenta().getDescripcion());
		camposTabDatos.getClaseCliente().setSelectedItem(cuentaDto.getClaseCuenta());
		camposTabDatos.getCicloFacturacion().setText(cuentaDto.getCicloFacturacion().getDescripcion());
		camposTabDatos.getUse().setText(cuentaDto.getUse());
	}
	
	public void cargarPanelTelefonoFax(CuentaDto cuentaDto) {
		for ( TelefonoDto telefono : cuentaDto.getPersona().getTelefonos()) {
			TipoTelefonoDto tipoTelefono = telefono.getTipoTelefono();
			if ( tipoTelefono.getId()==TipoTelefonoEnum.PRINCIPAL.getTipo()) {
				camposTabDatos.getTelPrincipalTextBox().getArea().setText(telefono.getArea());
				camposTabDatos.getTelPrincipalTextBox().getNumero().setText(telefono.getNumeroLocal());
				camposTabDatos.getTelPrincipalTextBox().getInterno().setText(telefono.getInterno());
			}
			if ( tipoTelefono.getId()==TipoTelefonoEnum.PARTICULAR.getTipo() ||
					 tipoTelefono.getId()==TipoTelefonoEnum.ADICIONAL.getTipo()) {
				camposTabDatos.getTelAdicionalTextBox().getArea().setText(telefono.getArea());
				camposTabDatos.getTelAdicionalTextBox().getNumero().setText(telefono.getNumeroLocal());
				camposTabDatos.getTelAdicionalTextBox().getInterno().setText(telefono.getInterno());
			}
			if ( tipoTelefono.getId()==TipoTelefonoEnum.CELULAR.getTipo()) {
				camposTabDatos.getTelCelularTextBox().getArea().setText(telefono.getArea());
				camposTabDatos.getTelCelularTextBox().getNumero().setText(telefono.getNumeroLocal());
			}
			if ( tipoTelefono.getId()==TipoTelefonoEnum.FAX.getTipo()) {
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
		    if (tipoEmail.getId()==TipoEmailEnum.PERSONAL.getTipo()) {
		    	camposTabDatos.getEmailPersonal().setText(email.getEmail());
		    }
		    if (tipoEmail.getId()==TipoEmailEnum.LABORAL.getTipo()) {
		    	camposTabDatos.getEmailLaboral().setText(email.getEmail());
		    }
		}    	
    }
    
    public void cargarPanelFormaPago(CuentaDto cuentaDto) {
        String id_formaPago = "";
		if (cuentaDto.getDatosPago().isEfectivo()) {
			datosPago = (DatosEfectivoDto) cuentaDto.getDatosPago();
			camposTabDatos.getFormaPago().setSelectedItem(((DatosEfectivoDto)datosPago).formaPagoAsociada());
			id_formaPago = TipoFormaPagoEnum.EFECTIVO.getTipo();
		}
		else if (cuentaDto.getDatosPago().isDebitoCuentaBancaria()) {			
			datosPago = (DatosDebitoCuentaBancariaDto) cuentaDto.getDatosPago();
			camposTabDatos.getFormaPago().setSelectedItem(((DatosDebitoCuentaBancariaDto)datosPago).formaPagoAsociada());
			camposTabDatos.getTipoCuentaBancaria().selectByValue(((DatosDebitoCuentaBancariaDto)datosPago).getTipoCuentaBancaria().getItemValue());
			camposTabDatos.getCbu().setText(((DatosDebitoCuentaBancariaDto)datosPago).getCbu());
			id_formaPago = TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo();
		}
		else if (cuentaDto.getDatosPago().isDebitoTarjetaCredito()) {			
			datosPago = (DatosDebitoTarjetaCreditoDto) cuentaDto.getDatosPago();
			camposTabDatos.getFormaPago().setSelectedItem(((DatosDebitoTarjetaCreditoDto)datosPago).formaPagoAsociada());
			camposTabDatos.getMesVto().setSelectedIndex(((DatosDebitoTarjetaCreditoDto)datosPago).getMesVencimientoTarjeta()-1);
			camposTabDatos.getAnioVto().setText(Short.toString(((DatosDebitoTarjetaCreditoDto)datosPago).getAnoVencimientoTarjeta()));
			camposTabDatos.getTipoTarjeta().setSelectedItem(((DatosDebitoTarjetaCreditoDto)datosPago).getTipoTarjeta());
			camposTabDatos.getNumeroTarjeta().setText(((DatosDebitoTarjetaCreditoDto)datosPago).getNumero());
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
    	} else {  //	if (id_formaPago.equals(TipoFormaPagoEnum.EFECTIVO.getTipo())) {
        	efectivoTable.setWidget(0, 1, camposTabDatos.getFormaPago());
    		efectivoTable.setVisible(true);
    		cuentaBancariaTable.setVisible(false);
    		tarjetaTable.setVisible(false);
    	}
    }

    public void cargarPanelVendedor(CuentaDto cuentaDto) {
		camposTabDatos.getVendedorNombre().setText(cuentaDto.getVendedor()!=null?cuentaDto.getVendedor().getNombre():"");
		camposTabDatos.getVendedorTelefono().setText(cuentaDto.getVendedor()!=null?cuentaDto.getVendedor().getTelefono():"");
		camposTabDatos.getTipoCanalVentas().setSelectedItem(cuentaDto.getTipoCanalVentas());
    }

    public void cargarPanelUsuario(CuentaDto cuentaDto) {
		camposTabDatos.getUsuario().setText(cuentaDto.getNombreUsuarioCreacion());
		if (cuentaDto.getFechaCreacion()!=null) 
			camposTabDatos.getFechaCreacion().setText(DateTimeFormat.getMediumDateFormat().format(cuentaDto.getFechaCreacion()));
    }
    
    /**
     * 
     * @param cuentaDto
     */
	public void setAtributosCamposAlAgregarCuenta(CuentaDto cuentaDto) {
		
		camposTabDatos.enableFields();
		iconoLupa.setVisible(true);
		camposTabDatos.getVerazRta().setVisible(true);
		camposTabDatos.getVerazLabel().setVisible(true);
		
        boolean docTipoCUIL = cuentaDto.getPersona().getDocumento().getTipoDocumento().getId()==TipoDocumentoEnum.CUIL.getTipo() ||
                              cuentaDto.getPersona().getDocumento().getTipoDocumento().getId()==TipoDocumentoEnum.CUIT.getTipo();
		
		List <Widget>campos = new ArrayList<Widget>();
		campos.add(camposTabDatos.getRazonSocial());
		campos.add(camposTabDatos.getTipoDocumento());
		campos.add(camposTabDatos.getNumeroDocumento());
		campos.add(camposTabDatos.getCategoria());
		campos.add(camposTabDatos.getClaseCliente());
		campos.add(camposTabDatos.getCicloFacturacion());
	    
		FormUtils.disableFields(campos);
		
		camposTabDatos.getIibb().setVisible(docTipoCUIL);
		camposTabDatos.getIibbLabel().setVisible(docTipoCUIL);
		
		camposTabDatos.getNombreDivision().setVisible(false /*TODO*/);
		camposTabDatos.getNomDivLabel().setVisible(false /*TODO*/);
		
		camposTabDatos.getUse().setVisible(!docTipoCUIL);
		camposTabDatos.getUseLabel().setVisible(!docTipoCUIL);

		camposTabDatos.getCargo().setVisible(cuentaDto.getPersona().getSexo().getItemValue().equals(Long.toString(SexoEnum.ORGANIZACION.getId())));
		camposTabDatos.getCargoLabel().setVisible(cuentaDto.getPersona().getSexo().getItemValue().equals(Long.toString(SexoEnum.ORGANIZACION.getId())));
		
		vendedorPanel.setVisible(false);
	
		if(ClientContext.getInstance().checkPermiso(PermisosEnum.ACCESO.getValue())) {
			String ver = "yes";
		} else {
			String ver = "no";
		}
	}

	/**
	 * 
	 * @param cuentaDto
	 */
	public void setAtributosCamposAlMostrarResuladoBusqueda(CuentaDto cuentaDto) {
		
		setAtributosCamposAlAgregarCuenta(cuentaDto);
		
		List <Widget>campos = new ArrayList<Widget>();
		campos.add(camposTabDatos.getNombre());
		campos.add(camposTabDatos.getApellido());
		campos.add(camposTabDatos.getSexo());
		campos.add(camposTabDatos.getFechaNacimiento());
		campos.add(camposTabDatos.getProveedorAnterior());
		campos.add(camposTabDatos.getContribuyente());
		campos.add(camposTabDatos.getRubro());
		campos.add(camposTabDatos.getIibb());
		campos.add(camposTabDatos.getClaseCliente());
		campos.add(camposTabDatos.getCategoria());
		
		campos.add(camposTabDatos.getTelPrincipalTextBox().getArea());
		campos.add(camposTabDatos.getTelPrincipalTextBox().getNumero());
		campos.add(camposTabDatos.getTelPrincipalTextBox().getInterno());
		campos.add(camposTabDatos.getObservaciones());

		if (!camposTabDatos.getEmailPersonal().getText().equals("")) 
			campos.add(camposTabDatos.getEmailPersonal());

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

		iconoLupa.setVisible(false);
		camposTabDatos.getVerazRta().setVisible(false);
		camposTabDatos.getVerazLabel().setVisible(false);
		
		vendedorPanel.setVisible( ClientContext.getInstance().getUsuario().getUserName().
				                   equalsIgnoreCase(
	                    		  cuentaDto.getVendedor().getNombre())
	                    		);
		
	}
	
	
	public boolean formularioDatosDirty() {
		boolean retorno = false;
		CuentaEdicionTabPanel cuentaTab = CuentaEdicionTabPanel.getInstance();

		//PANEL DATOS		
		if ( (FormUtils.fieldDirty(camposTabDatos.getRazonSocial(), cuentaTab.getCuenta2editDto().getPersona().getRazonSocial()))
		   ||(FormUtils.fieldDirty(camposTabDatos.getSexo(), cuentaTab.getCuenta2editDto().getPersona().getSexo().getItemValue()))
		   ||(FormUtils.fieldDirty(camposTabDatos.getFechaNacimiento(), cuentaTab.getCuenta2editDto().getPersona().getFechaNacimiento()!=null?DateTimeFormat.getMediumDateFormat().format(cuentaTab.getCuenta2editDto().getPersona().getFechaNacimiento()):""))
   		   ||(FormUtils.fieldDirty(camposTabDatos.getContribuyente(), cuentaTab.getCuenta2editDto().getTipoContribuyente().getItemValue()))
		   ||(FormUtils.fieldDirty(camposTabDatos.getProveedorAnterior(), cuentaTab.getCuenta2editDto().getProveedorInicial().getItemValue())) 
		   ||(FormUtils.fieldDirty(camposTabDatos.getRubro(), cuentaTab.getCuenta2editDto().getRubro().getItemValue()))
		   ||(FormUtils.fieldDirty(camposTabDatos.getClaseCliente(), cuentaTab.getCuenta2editDto().getClaseCuenta().getItemValue()))
		   ||(FormUtils.fieldDirty(camposTabDatos.getCategoria(), cuentaTab.getCuenta2editDto().getCategoriaCuenta().getItemText()))
   		   //||(FormUtils.fieldDirty(camposTabDatos.getCicloFacturacion(), cuentaTab.getCuenta2editDto().getCicloFacturacion().getDescripcion()))
		   ||(FormUtils.fieldDirty(camposTabDatos.getObservaciones(), cuentaTab.getCuenta2editDto().getObservacionesTelMail()))
		) return true;

		//PANEL FORMA DE PAGO
		if (camposTabDatos.getFormaPago().getSelectedItemId().equals(TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo())) {
			if (cuentaTab.getCuenta2editDto().getDatosPago() instanceof DatosDebitoCuentaBancariaDto) {
				if	(FormUtils.fieldDirty(camposTabDatos.getCbu(), ((DatosDebitoCuentaBancariaDto) cuentaTab.getCuenta2editDto().getDatosPago()).getCbu())
					 ||	FormUtils.fieldDirty(camposTabDatos.getTipoCuentaBancaria(),((DatosDebitoCuentaBancariaDto) cuentaTab.getCuenta2editDto().getDatosPago()).getTipoCuentaBancaria().getItemValue())
				    ) {
					retorno = true;
				}	
			} else {
				if ((!camposTabDatos.getCbu().getText().trim().equals("")))
					return true;
			}
		}
		if (camposTabDatos.getFormaPago().getSelectedItemId().equals(TipoFormaPagoEnum.TARJETA_CREDITO.getTipo())) {
			if (cuentaTab.getCuenta2editDto().getDatosPago() instanceof DatosDebitoTarjetaCreditoDto) {
				if ( (FormUtils.fieldDirty(camposTabDatos.getNumeroTarjeta(), ((DatosDebitoTarjetaCreditoDto) cuentaTab.getCuenta2editDto().getDatosPago()).getNumero()))
				   ||(FormUtils.fieldDirty(camposTabDatos.getAnioVto(), ((DatosDebitoTarjetaCreditoDto) cuentaTab.getCuenta2editDto().getDatosPago()).getAnoVencimientoTarjeta()+""))
				   ||(FormUtils.fieldDirty(camposTabDatos.getMesVto(), ((DatosDebitoTarjetaCreditoDto) cuentaTab.getCuenta2editDto().getDatosPago()).getMesVencimientoTarjeta()+""))
				   ||(FormUtils.fieldDirty(camposTabDatos.getTipoTarjeta(), ((DatosDebitoTarjetaCreditoDto) cuentaTab.getCuenta2editDto().getDatosPago()).getTipoTarjeta().getItemValue()))
				)	retorno = true;
			} else {
				if ((!camposTabDatos.getNumeroTarjeta().getText().trim().equals(""))
					||(camposTabDatos.getAnioVto().getText().trim().endsWith(""))	
				  )	return true;
			}
		} 
		
		//PANEL TELEFONOS
		boolean noTienePrincipal = true;
		boolean noTieneAdicional = true;
		boolean noTieneCelular   = true;
		boolean noTieneFax       = true;

		//compara con el dto
		for (TelefonoDto telefono : cuentaTab.getCuenta2editDto().getPersona().getTelefonos()) {
			TipoTelefonoDto tipoTelefono = telefono.getTipoTelefono();

			if ( tipoTelefono.getId()==TipoTelefonoEnum.PRINCIPAL.getTipo()) {
				if ( (FormUtils.fieldDirty(camposTabDatos.getTelPrincipalTextBox().getArea(),telefono.getArea())) 
						||(FormUtils.fieldDirty(camposTabDatos.getTelPrincipalTextBox().getNumero(),telefono.getNumeroLocal()))
						||(FormUtils.fieldDirty(camposTabDatos.getTelPrincipalTextBox().getInterno(),telefono.getInterno())) 
				) retorno = true;   
				noTienePrincipal = false;
			} 
			if ( tipoTelefono.getId()==TipoTelefonoEnum.PARTICULAR.getTipo() ||
					tipoTelefono.getId()==TipoTelefonoEnum.ADICIONAL.getTipo()) {
				if ( (FormUtils.fieldDirty(camposTabDatos.getTelAdicionalTextBox().getArea(),telefono.getArea()))
						||(FormUtils.fieldDirty(camposTabDatos.getTelAdicionalTextBox().getNumero(),telefono.getNumeroLocal()))
						||(FormUtils.fieldDirty(camposTabDatos.getTelAdicionalTextBox().getInterno(),telefono.getInterno()))
				) retorno = true;
				noTieneAdicional = false;
			} 
			if ( tipoTelefono.getId()==TipoTelefonoEnum.CELULAR.getTipo()) {
				if ( (FormUtils.fieldDirty(camposTabDatos.getTelCelularTextBox().getArea(),telefono.getArea()))
						||(FormUtils.fieldDirty(camposTabDatos.getTelCelularTextBox().getNumero(),telefono.getNumeroLocal()))
				) retorno = true;
				noTieneCelular = false;
			}
			if ( tipoTelefono.getId()==TipoTelefonoEnum.FAX.getTipo()) {
				if ( (FormUtils.fieldDirty(camposTabDatos.getTelFaxTextBox().getArea(),telefono.getArea()))
						||(FormUtils.fieldDirty(camposTabDatos.getTelFaxTextBox().getNumero(),telefono.getNumeroLocal()))
						||(FormUtils.fieldDirty(camposTabDatos.getTelFaxTextBox().getInterno(),telefono.getInterno()))
				) retorno = true;
				noTieneFax = false;
			}
		}
		//chequea si hay nuevos
		if (noTienePrincipal) {
			if ((!camposTabDatos.getTelPrincipalTextBox().getArea().getText().trim().equals("")) 
					||(!camposTabDatos.getTelPrincipalTextBox().getNumero().getText().trim().equals(""))
					||(!camposTabDatos.getTelPrincipalTextBox().getInterno().getText().trim().equals(""))
			) retorno = true;
		}
		if (noTieneAdicional) {
			if ((!camposTabDatos.getTelAdicionalTextBox().getArea().getText().trim().equals(""))
					||(!camposTabDatos.getTelAdicionalTextBox().getNumero().getText().trim().equals(""))
					||(!camposTabDatos.getTelAdicionalTextBox().getInterno().getText().trim().equals(""))
			) retorno = true;
		}
		if (noTieneCelular) {
			if ((!camposTabDatos.getTelCelularTextBox().getArea().getText().trim().equals(""))
					||(!camposTabDatos.getTelCelularTextBox().getNumero().getText().trim().equals(""))
			) retorno = true;
		}
		if (noTieneFax) {
			if ((!camposTabDatos.getTelFaxTextBox().getArea().getText().trim().equals(""))
					||(!camposTabDatos.getTelFaxTextBox().getNumero().getText().trim().equals(""))
					||(!camposTabDatos.getTelFaxTextBox().getInterno().getText().trim().equals(""))
			) retorno = true;
		}
		
	//PANEL MAILS
		boolean noTienePersonal = true;
		boolean noTieneLaboral  = true;

		for (EmailDto email : cuentaTab.getCuenta2editDto().getPersona().getEmails()) {
			TipoEmailDto tipo = email.getTipoEmail();
			if (tipo.getId()==TipoEmailEnum.PERSONAL.getTipo()) {
				if ( (FormUtils.fieldDirty(camposTabDatos.getEmailPersonal(),email.getEmail()))) 
					retorno = true;
				noTienePersonal = false;
			}
			if (tipo.getId()==TipoEmailEnum.LABORAL.getTipo()) {
				if ( (FormUtils.fieldDirty(camposTabDatos.getEmailLaboral(),email.getEmail()))) 
					retorno = true;
				noTieneLaboral  = false;
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
	    if (tipoDocId==TipoDocumentoEnum.LC.getTipo()) {
	    	camposTabDatos.getSexo().selectByValue(Long.toString(SexoEnum.FEMENINO.getId()));
	    } else if (tipoDocId==TipoDocumentoEnum.CUIT.getTipo()||tipoDocId==TipoDocumentoEnum.CUIT_EXT.getTipo()) {
	    	camposTabDatos.getSexo().selectByValue(Long.toString(SexoEnum.getId(docNumero)));
	    } else{
	    	camposTabDatos.getSexo().selectByValue(Long.toString(SexoEnum.MASCULINO.getId()));
	    }
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
		for(Widget campo : camposObligatorios) {
			if (campo instanceof TextBox)
				validator.addTarget((TextBox)campo).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll("\\{1\\}", ((TextBox)campo).getName()));
			if (campo instanceof ListBox)
				validator.addTarget(((ListBox)campo).getSelectedItemText()).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll("\\{1\\}",((ListBox)campo).getName()));			
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

		if (!camposTabDatos.getNombre().getText().equals("")) 
			validator.addTarget(camposTabDatos.getNombre()).alphabetic(Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}", camposTabDatos.getNombre().getName()));
		if (!camposTabDatos.getApellido().getText().equals("")) 
			validator.addTarget(camposTabDatos.getApellido()).alphabetic(Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}", camposTabDatos.getApellido().getName()));
		
		if (!camposTabDatos.getFechaNacimiento().getTextBox().getText().equals("")) 
			validator.addTarget(camposTabDatos.getFechaNacimiento().getTextBox()).date(Sfa.constant().ERR_FECHA_NO_VALIDA().replaceAll("\\{1\\}", camposTabDatos.getFechaNacimiento().getTextBox().getName()));

		if (!camposTabDatos.getEmailPersonal().getText().equals(""))
		    validator.addTarget(camposTabDatos.getEmailPersonal()).mail(Sfa.constant().ERR_EMAIL_NO_VALIDO().replaceAll("\\{1\\}", camposTabDatos.getEmailPersonal().getName()));
		if (!camposTabDatos.getEmailLaboral().getText().equals(""))
			validator.addTarget(camposTabDatos.getEmailLaboral()).mail(Sfa.constant().ERR_EMAIL_NO_VALIDO().replaceAll("\\{1\\}", camposTabDatos.getEmailLaboral().getName()));

		if(!camposTabDatos.getTelPrincipalTextBox().getArea().getText().equals("")) 
			validator.addTarget(camposTabDatos.getTelPrincipalTextBox().getArea()).numericPositive(Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}", camposTabDatos.getTelPrincipalTextBox().getArea().getName()));
		if(!camposTabDatos.getTelPrincipalTextBox().getNumero().getText().equals("")) 
			validator.addTarget(camposTabDatos.getTelPrincipalTextBox().getNumero()).numericPositive(Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}", camposTabDatos.getTelPrincipalTextBox().getNumero().getName()));
		if(!camposTabDatos.getTelPrincipalTextBox().getInterno().getText().equals(""))
			validator.addTarget(camposTabDatos.getTelPrincipalTextBox().getInterno()).numericPositive(Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}", camposTabDatos.getTelPrincipalTextBox().getInterno().getName()));
		if(!camposTabDatos.getTelAdicionalTextBox().getArea().getText().equals(""))
			validator.addTarget(camposTabDatos.getTelAdicionalTextBox().getArea()).numericPositive(Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}", camposTabDatos.getTelAdicionalTextBox().getArea().getName()));
		if(!camposTabDatos.getTelAdicionalTextBox().getNumero().getText().equals(""))
			validator.addTarget(camposTabDatos.getTelAdicionalTextBox().getNumero()).numericPositive(Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}", camposTabDatos.getTelAdicionalTextBox().getNumero().getName()));
		if(!camposTabDatos.getTelAdicionalTextBox().getInterno().getText().equals(""))
			validator.addTarget(camposTabDatos.getTelAdicionalTextBox().getInterno()).numericPositive(Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}", camposTabDatos.getTelAdicionalTextBox().getInterno().getName()));
		if(!camposTabDatos.getTelFaxTextBox().getArea().getText().equals(""))
			validator.addTarget(camposTabDatos.getTelFaxTextBox().getArea()).numericPositive(Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}", camposTabDatos.getTelFaxTextBox().getArea().getName()));
		if(!camposTabDatos.getTelFaxTextBox().getNumero().getText().equals(""))
			validator.addTarget(camposTabDatos.getTelFaxTextBox().getNumero()).numericPositive(Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}", camposTabDatos.getTelFaxTextBox().getNumero().getName()));
		if(!camposTabDatos.getTelFaxTextBox().getInterno().getText().equals(""))
			validator.addTarget(camposTabDatos.getTelFaxTextBox().getInterno()).numericPositive(Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}", camposTabDatos.getTelFaxTextBox().getInterno().getName()));
		if(!camposTabDatos.getTelCelularTextBox().getArea().getText().equals(""))
			validator.addTarget(camposTabDatos.getTelCelularTextBox().getArea()).numericPositive(Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}", camposTabDatos.getTelCelularTextBox().getArea().getName()));
		if(!camposTabDatos.getTelCelularTextBox().getNumero().getText().equals(""))
			validator.addTarget(camposTabDatos.getTelCelularTextBox().getNumero()).numericPositive(Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}", camposTabDatos.getTelCelularTextBox().getNumero().getName()));
		
		if(!camposTabDatos.getNumeroTarjeta().getText().equals(""))
			validator.addTarget(camposTabDatos.getNumeroTarjeta()).numericPositive(Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}", camposTabDatos.getNumeroTarjeta().getName()));

		if(!camposTabDatos.getAnioVto().getText().equals("")) {
			try {
				int valor = Integer.parseInt(camposTabDatos.getAnioVto().getText());
				if (valor<camposTabDatos.getCurrentYear()||valor>(camposTabDatos.getCurrentYear()+5)) {
					validator.addError(Sfa.constant().ERR_ANIO_NO_VALIDO());
				}	
			} catch (Exception e) {
				validator.addError(Sfa.constant().ERR_ANIO_NO_VALIDO());
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
			CuentaRpcService.Util.getInstance().validarTarjeta(camposTabDatos.getNumeroTarjeta().getText(), new Integer(camposTabDatos.getMesVto().getSelectedItemId()), new Integer(camposTabDatos.getAnioVto().getText()), new DefaultWaitCallback() {
				public void success(Object result) {
					TarjetaCreditoValidatorResultDto tarjetaCreditoValidatorResult = (TarjetaCreditoValidatorResultDto) result;
					if(tarjetaCreditoValidatorResult==null) {
						ErrorDialog.getInstance().show(Sfa.constant().ERR_AL_VALIDAR_TARJETA());
					}else if (tarjetaCreditoValidatorResult.getIsValid()) {
						MessageWindow.alert(Sfa.constant().ERR_DIA_HABIL());
					} else {
						ErrorDialog.getInstance().show(Sfa.constant().ERR_TARJETA_NO_VALIDA());
					}
				}
			});
		} else {
			ErrorDialog.getInstance().show(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll("\\{1\\}", camposTabDatos.getNumeroTarjeta().getName()));
		}
	}
	
	/**
	 * 
	 */
	public void cambiarVisibilidadCamposSegunSexo() {
		camposTabDatos.getCargoLabel().setVisible(camposTabDatos.getSexo().getSelectedItemId().equals(Long.toString(SexoEnum.ORGANIZACION.getId())));
		camposTabDatos.getCargo().setVisible(camposTabDatos.getSexo().getSelectedItemId().equals(Long.toString(SexoEnum.ORGANIZACION.getId())));
		armarTablaPanelDatos();
	}
	
	/**
	 * 
	 * @return
	 */
	public CuentaDto getCuentaDtoFromEditor() {
		PersonaDto personaDto = CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getPersona();
		
		//panel datos
		personaDto.setRazonSocial(camposTabDatos.getRazonSocial().getText());
		personaDto.setNombre(camposTabDatos.getNombre().getText());
		personaDto.setApellido(camposTabDatos.getApellido().getText());
		personaDto.setSexo(new SexoDto(Long.parseLong(camposTabDatos.getSexo().getSelectedItemId()),camposTabDatos.getSexo().getSelectedItemText()));
		if (!camposTabDatos.getFechaNacimiento().getTextBox().getText().equals(""))
			personaDto.setFechaNacimiento(camposTabDatos.getFechaNacimiento().getSelectedDate());
		CuentaEdicionTabPanel.getInstance().getCuenta2editDto().setTipoContribuyente(new TipoContribuyenteDto(Long.parseLong(camposTabDatos.getContribuyente().getSelectedItemId()),camposTabDatos.getContribuyente().getSelectedItemText()));
		if(camposTabDatos.getSexo().getSelectedItemId().equals(SexoEnum.ORGANIZACION)) {
			CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getPersona().setCargo(new CargoDto(Long.parseLong(camposTabDatos.getCargo().getSelectedItemId()),camposTabDatos.getCargo().getSelectedItemText()));
		}
		CuentaEdicionTabPanel.getInstance().getCuenta2editDto().setProveedorInicial(new ProveedorDto(Long.parseLong(camposTabDatos.getProveedorAnterior().getSelectedItemId()),camposTabDatos.getProveedorAnterior().getSelectedItemText()));
		CuentaEdicionTabPanel.getInstance().getCuenta2editDto().setRubro(new RubroDto(Long.parseLong(camposTabDatos.getRubro().getSelectedItemId()),camposTabDatos.getRubro().getSelectedItemText()));

		//Panel Telefono/Fax
		List <TelefonoDto>phonos = new ArrayList<TelefonoDto>();
       if (!camposTabDatos.getTelPrincipalTextBox().getNumero().getText().equals("")) {
        	TipoTelefonoDto tipoTel = new TipoTelefonoDto(TipoTelefonoEnum.PRINCIPAL.getTipo(),TipoTelefonoEnum.PRINCIPAL.toString());
        	TelefonoDto tel =new TelefonoDto(
        			camposTabDatos.getTelPrincipalTextBox().getArea().getText(),
        			camposTabDatos.getTelPrincipalTextBox().getInterno().getText(),
        			camposTabDatos.getTelPrincipalTextBox().getNumero().getText(),
        			personaDto,
        			Boolean.TRUE,
        			tipoTel
        		);
        	tel.setPersona(personaDto);
        	phonos.add(tel);
         }
        if (!camposTabDatos.getTelAdicionalTextBox().getNumero().getText().equals("")) {
        	TipoTelefonoDto tipoTel = new TipoTelefonoDto(TipoTelefonoEnum.ADICIONAL.getTipo(),TipoTelefonoEnum.ADICIONAL.toString());
        	phonos.add(new TelefonoDto(
        			camposTabDatos.getTelAdicionalTextBox().getArea().getText(),
        			camposTabDatos.getTelAdicionalTextBox().getInterno().getText(),
        			camposTabDatos.getTelAdicionalTextBox().getNumero().getText(),
        			personaDto,
        			Boolean.FALSE,
        			tipoTel
        		)
        	);
         }
        if (!camposTabDatos.getTelCelularTextBox().getNumero().getText().equals("")) {
        	TipoTelefonoDto tipoTel = new TipoTelefonoDto(TipoTelefonoEnum.CELULAR.getTipo(),TipoTelefonoEnum.CELULAR.toString());
        	phonos.add(new TelefonoDto(
        			camposTabDatos.getTelCelularTextBox().getArea().getText(),
                    "",
        			camposTabDatos.getTelCelularTextBox().getNumero().getText(),
        			personaDto,
        			Boolean.FALSE,
        			tipoTel
        		)
        	);
         }
        if (!camposTabDatos.getTelFaxTextBox().getNumero().getText().equals("")) {
        	TipoTelefonoDto tipoTel = new TipoTelefonoDto(TipoTelefonoEnum.FAX.getTipo(),TipoTelefonoEnum.FAX.toString());
        	phonos.add(new TelefonoDto(
        			camposTabDatos.getTelFaxTextBox().getArea().getText(),
        			camposTabDatos.getTelFaxTextBox().getInterno().getText(),
        			camposTabDatos.getTelFaxTextBox().getNumero().getText(),
        			personaDto,
        			Boolean.FALSE,
        			tipoTel
        		)
        	);
         }
        CuentaEdicionTabPanel.getInstance().getCuenta2editDto().setObservacionesTelMail(camposTabDatos.getObservaciones().getText()); 
        personaDto.setTelefonos(phonos);
        
        //Panel Emails
		List <EmailDto>mails = new ArrayList<EmailDto>();
        if (!camposTabDatos.getEmailPersonal().getText().equals("")) {
       	   TipoEmailDto tipoEmail = new TipoEmailDto(TipoEmailEnum.PERSONAL.getTipo(),TipoEmailEnum.PERSONAL.toString());
        	mails.add(new EmailDto(camposTabDatos.getEmailPersonal().getText(),false,tipoEmail ));	
        }
        if (!camposTabDatos.getEmailLaboral().getText().equals("")) {
        	   TipoEmailDto tipoEmail = new TipoEmailDto(TipoEmailEnum.LABORAL.getTipo(),TipoEmailEnum.LABORAL.toString());
         	mails.add(new EmailDto(camposTabDatos.getEmailLaboral().getText(),false,tipoEmail ));	
        }
        personaDto.setEmails(mails);        
        
		
       //Panel Formas de Pago
        DatosPagoDto datosPago = null;
        if (camposTabDatos.getFormaPago().getSelectedItemId().equals(TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo())) {
        	CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getFormaPago().setId(Long.parseLong(TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo()));
        	datosPago = new DatosDebitoCuentaBancariaDto();
        	((DatosDebitoCuentaBancariaDto)datosPago).setId(CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getDatosPago().getId());
        	((DatosDebitoCuentaBancariaDto)datosPago).setFormaPagoAsociada(CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getFormaPago());
        	((DatosDebitoCuentaBancariaDto)datosPago).setCbu(camposTabDatos.getCbu().getText());
        	((DatosDebitoCuentaBancariaDto)datosPago).setTipoCuentaBancaria(new TipoCuentaBancariaDto(Long.parseLong(camposTabDatos.getTipoCuentaBancaria().getSelectedItemId()),camposTabDatos.getTipoCuentaBancaria().getSelectedItemText(),camposTabDatos.getTipoCuentaBancaria().getSelectedItemText()));
        	
        } else if (camposTabDatos.getFormaPago().getSelectedItemId().equals(TipoFormaPagoEnum.TARJETA_CREDITO.getTipo())) {
        	datosPago = new DatosDebitoTarjetaCreditoDto();
        	((DatosDebitoTarjetaCreditoDto)datosPago).setId(CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getDatosPago().getId());
        	CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getFormaPago().setId(Long.parseLong(TipoFormaPagoEnum.TARJETA_CREDITO.getTipo()));
        	((DatosDebitoTarjetaCreditoDto)datosPago).setFormaPagoAsociada(CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getFormaPago());
        	((DatosDebitoTarjetaCreditoDto)datosPago).setNumero(camposTabDatos.getNumeroTarjeta().getText());
        	((DatosDebitoTarjetaCreditoDto)datosPago).setAnoVencimientoTarjeta(Short.parseShort(camposTabDatos.getAnioVto().getText()));
        	((DatosDebitoTarjetaCreditoDto)datosPago).setMesVencimientoTarjeta(Short.parseShort(camposTabDatos.getMesVto().getSelectedItemId()));
        	((DatosDebitoTarjetaCreditoDto)datosPago).setTipoTarjeta(new TipoTarjetaDto(Long.parseLong(camposTabDatos.getTipoTarjeta().getSelectedItemId()),camposTabDatos.getTipoTarjeta().getSelectedItemText()));
        } else {//if (camposTabDatos.getFormaPago().getSelectedItemId().equals(TipoFormaPagoEnum.EFECTIVO)) {
        	datosPago = new DatosEfectivoDto();
        	((DatosEfectivoDto)datosPago).setId(CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getDatosPago().getId());
        	CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getFormaPago().setId(Long.parseLong(TipoFormaPagoEnum.EFECTIVO.getTipo()));
        	((DatosEfectivoDto)datosPago).setFormaPagoAsociada(CuentaEdicionTabPanel.getInstance().getCuenta2editDto().getFormaPago());
        	CuentaEdicionTabPanel.getInstance().getCuenta2editDto().setDatosPago(datosPago);
        }
        CuentaEdicionTabPanel.getInstance().getCuenta2editDto().setDatosPago(datosPago);
        CuentaEdicionTabPanel.getInstance().getCuenta2editDto().setPersona(personaDto);  		
		return CuentaEdicionTabPanel.getInstance().getCuenta2editDto();
	}

	public CuentaUIData getCamposTabDatos() {
		return camposTabDatos;
	}

	public void setCamposTabDatos(CuentaUIData camposTabDatos) {
		this.camposTabDatos = camposTabDatos;
	}
	
	public void setearValoresRtaVeraz(VerazResponseDto result, TextBox apellido, TextBox nombre, TextBox razonSocial, ListBox sexo, Label veraz) {
        apellido.setText(result.getApellido());
        apellido.setEnabled(true);
        
        nombre.setText(result.getNombre());
        nombre.setEnabled(true);
        
        if (razonSocial!=null) {
        razonSocial.setText(result.getRazonSocial());
        }
        
        sexo.setEnabled(true);
        
        if ("ACEPTAR".equals(result.getEstado())) {
        	veraz.addStyleName(estilos.get(0));
        	estiloUsado = 0;
        } else if ("REVISAR".equals(result.getEstado())) {
        	veraz.addStyleName(estilos.get(1));
        	estiloUsado = 1;
        }else {
        	veraz.addStyleName(estilos.get(2));
        	estiloUsado = 2;
        }
      
        veraz.setText(result.getEstado());
        
        MessageDialog.getInstance().setDialogTitle("Resultado Veraz");
        MessageDialog.getInstance().showAceptar(result.getMensaje(), MessageDialog.getInstance().getCloseCommand());
	}
}
