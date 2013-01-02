package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.ClaseCuentaDto;
import ar.com.nextel.sfa.client.dto.FacturaElectronicaDto;
import ar.com.nextel.sfa.client.dto.MotivoNoCierreDto;
import ar.com.nextel.sfa.client.dto.TipoContribuyenteDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.dto.TipoTelefonoDto;
import ar.com.nextel.sfa.client.enums.EstadoOportunidadEnum;
import ar.com.nextel.sfa.client.enums.TipoTarjetaEnum;
import ar.com.nextel.sfa.client.initializer.AgregarCuentaInitializer;
import ar.com.nextel.sfa.client.util.HistoryUtils;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.widget.RadioButtonGroup;
import ar.com.nextel.sfa.client.widget.RadioButtonWithValue;
import ar.com.nextel.sfa.client.widget.TelefonoTextBox;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.datepicker.SimpleDatePicker;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author mrial
 * 
 */
public class CuentaUIData extends UIData {

	private static final int MAX_LENGHT_OBSERVACIONES = 200;
	private ListBox tipoDocumento = new ListBox();
	private ListBox sexo = new ListBox();
	private ListBox contribuyente = new ListBox();
	private ListBox rubro = new ListBox();
	private ListBox formaPago = new ListBox();
	private ListBox claseCliente = new ListBox();
	private ListBox proveedorAnterior = new ListBox();
	private ListBox cargo = new ListBox();
	private ListBox tipoCuentaBancaria = new ListBox();
	private ListBox tipoTarjeta = new ListBox();
	private ListBox tipoCanalVentas = new ListBox();
	private ListBox mesVto = new ListBox();
	private ListBox estadoOpp = new ListBox();

	private TextBox numeroDocumento = new TextBox();
	private TextBox razonSocial = new TextBox();
	private RegexTextBox nombre = new RegexTextBox(RegularExpressionConstants.letras);
	private RegexTextBox apellido = new RegexTextBox(RegularExpressionConstants.letras);
	private TextBox categoria = new TextBox();
	private TextBox nombreDivision = new TextBox();
	private RegexTextBox iibb = new RegexTextBox(RegularExpressionConstants.iibb);
	private TextBox use = new TextBox();
	private TextBox cicloFacturacion = new TextBox();
	private TextBox emailPersonal = new TextBox();
	private TextBox emailLaboral = new TextBox();
	
	private FacturaElectronicaPanel facturaElectronicaPanel = new FacturaElectronicaPanel();
	

	private RegexTextBox cbu = new RegexTextBox(RegularExpressionConstants.numeros);
	private RegexTextBox anioVto = new RegexTextBox(RegularExpressionConstants.getNumerosLimitado(4));
	private RegexTextBox numeroTarjeta = new RegexTextBox(RegularExpressionConstants.numeros);

	private TelefonoTextBox telPrincipalTextBox = new TelefonoTextBox();
	private TelefonoTextBox telAdicionalTextBox = new TelefonoTextBox();
	private TelefonoTextBox telCelularTextBox = new TelefonoTextBox(false);
	private TelefonoTextBox telFaxTextBox = new TelefonoTextBox();

	private TextBox usuario = new TextBox();
	private TextBox fechaCreacion = new TextBox();

	private TextBox vendedorNombre = new TextBox();
	private TextBox vendedorTelefono = new TextBox();

	private Label tipoDocLabel = new Label(Sfa.constant().tipoDocumento());
	private Label numDocLabel = new Label(Sfa.constant().numero());
	private Label razSocLabel = new Label(Sfa.constant().razonSocial());
	private Label nombreLabel = new Label(Sfa.constant().nombre());
	private Label apellidoLabel = new Label(Sfa.constant().apellido());
	private Label sexoLabel = new Label(Sfa.constant().sexo());
	private Label fecNacLabel = new Label(Sfa.constant().fechaNacimiento());
	private Label contrLabel = new Label(Sfa.constant().contribuyente());
	private Label provAntLabel = new Label(Sfa.constant().provedorAnterior());
	private Label rubroLabel = new Label(Sfa.constant().rubro());
	private Label claseClLabel = new Label(Sfa.constant().claseCliente());
	private Label categLabel = new Label(Sfa.constant().categoria());
	private Label cicloFacLabel = new Label(Sfa.constant().cicloFacturacion());
	private Label verazLabel = new Label(Sfa.constant().veraz());
	private Label useLabel = new Label(Sfa.constant().use());
	private Label cargoLabel = new Label(Sfa.constant().cargo());
	private Label iibbLabel = new Label(Sfa.constant().iibb());
	private Label nomDivLabel = new Label(Sfa.constant().nombreDivision());
	private Label cbuLabel = new Label(Sfa.constant().cbu());
	private Label numTarLabel = new Label(Sfa.constant().nroTarjeta());
	private Label prinLabel = new Label(Sfa.constant().principal());
	private Label observLabel = new Label(Sfa.constant().observaciones());

	private TextArea observaciones = new TextArea();
	private SimpleDatePicker fechaNacimiento = new SimpleDatePicker(false, true);
	private SimpleLink validarTarjeta = new SimpleLink(Sfa.constant().validarTarjeta(), "#", true);
	private Label verazRta = new Label();

	List<Widget> camposObligatorios = new ArrayList<Widget>();
	List<Widget> camposObligatoriosFormaPago = new ArrayList<Widget>();
	List<Widget> labelsObligatorios = new ArrayList<Widget>();

	List<TipoTelefonoDto> tipoTelefono = new ArrayList<TipoTelefonoDto>();

	// field para opp
	private RadioButtonGroup radioGroupMotivos = new RadioButtonGroup("motivo");
	private Label oppNumero = new Label(Sfa.constant().numero());

	private Label oppVencimientoLabel = new Label(Sfa.constant().vencimiento());
	private Label oppEstadoLabel = new Label(Sfa.constant().estado());
	private Label oppCompetenciaProvLabel = new Label(Sfa.constant().competencia());
	private Label oppCompetenciaEquipoLabel = new Label(Sfa.constant().competenciaEq());
	private Label oppTerminalesEstimadasLabel = new Label(Sfa.constant().terminalesEstimadas());
	private Label oppVisitasLabel = new Label(Sfa.constant().visitas());

	private Label oppNroLabel = new Label(Sfa.constant().nroOPP());
	private TextBox oppNroOpp = new TextBox();
	private Label oppEstadoPopupLabel = new Label(Sfa.constant().estado());
	private Label oppObservacionesLabel = new Label(Sfa.constant().observaciones());
	private TextArea oppObservaciones = new TextArea();

	private Label oppVencimiento = new Label(" ");
	private Label oppEstado = new Label(" ");
	private Label oppCompetenciaProv = new Label(" ");
	private Label oppCompetenciaEquipo = new Label(" ");
	private Label oppVisitas = new Label(" ");
	private TextBox oppTipoDocumento = new TextBox();
	private TextBox oppRubro = new TextBox();
	private TextBox oppTerminalesEstimadas = new TextBox();
	private FlexTable radioOpsTable = new FlexTable();
	private List<MotivoNoCierreDto> motivosNoCierre;

	// Factura electrónica
	private FacturaElectronicaDto facturaElectronica = null;

	private int currentYear;
	
	//MGR - 26-07-2010 - Incidente #0000703 - Guardo todas las opciones para no tener que regresar al servidor
	List<TipoContribuyenteDto> tiposContribuyentes = null;

	public CuentaUIData() {
		init();
	}

	public void init() {
		fechaNacimiento.setWeekendSelectable(true);
		setCombos();

		sexo.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				CuentaDatosForm.getInstance().cambiarVisibilidadCamposSegunSexo();
			}
		});
		formaPago.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				CuentaDatosForm.getInstance().setVisiblePanelFormaPagoYActualizarCamposObligatorios(
						((ListBox) event.getSource()).getSelectedItemId());
				setAtributosNumeroTarjeta();
			}
		});
		nombre.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent arg0) {
				if (!"CUIT".equals(tipoDocumento.getSelectedItemText())) {
					exportarNombreApellidoARazonSocial();
				}
			}
		});
		apellido.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent arg0) {
				if (!"CUIT".equals(tipoDocumento.getSelectedItemText())) {
					exportarNombreApellidoARazonSocial();
				}
			}
		});
		nombreDivision.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent arg0) {
				nombreDivision.setText(nombreDivision.getText().trim().toUpperCase());
			}
		});
		tipoTarjeta.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent arg0) {
				numeroTarjeta.setText("");
				setAtributosNumeroTarjeta();

			}
		});
		validarTarjeta.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				CuentaDatosForm.getInstance().validarTarjeta();
			}
		});

		observaciones.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent arg0) {
				if (observaciones.getText().length() > MAX_LENGHT_OBSERVACIONES) {
					ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
					ErrorDialog.getInstance().show(Sfa.constant().ERR_MAX_LARGO_CAMPO(), false);
					observaciones.setText(observaciones.getText().substring(0, MAX_LENGHT_OBSERVACIONES));
				}
			}
		});
		setAtributosDeCampos();
		fields.add(tipoDocumento);
		fields.add(numeroDocumento);
		fields.add(razonSocial);
		fields.add(nombre);
		fields.add(apellido);
		fields.add(sexo);
		fields.add(fechaNacimiento);
		fields.add(contribuyente);
		fields.add(nombreDivision);
		fields.add(cargo);
		fields.add(proveedorAnterior);
		fields.add(rubro);
		fields.add(claseCliente);
		fields.add(categoria);
		fields.add(iibb);
		fields.add(cicloFacturacion);
		fields.add(verazRta);
		fields.add(observaciones);
		fields.add(telPrincipalTextBox.getArea());
		fields.add(telPrincipalTextBox.getNumero());
		fields.add(telPrincipalTextBox.getInterno());
		fields.add(telAdicionalTextBox.getArea());
		fields.add(telAdicionalTextBox.getNumero());
		fields.add(telAdicionalTextBox.getInterno());
		fields.add(telCelularTextBox.getArea());
		fields.add(telCelularTextBox.getNumero());
		fields.add(telFaxTextBox.getArea());
		fields.add(telFaxTextBox.getNumero());
		fields.add(telFaxTextBox.getInterno());
		fields.add(emailPersonal);
		fields.add(getFacturaElectronicaPanel());
		fields.add(emailLaboral);
		fields.add(formaPago);
		fields.add(vendedorNombre);
		fields.add(vendedorTelefono);
		fields.add(tipoCanalVentas);
		fields.add(tipoCuentaBancaria);
		fields.add(tipoTarjeta);
		fields.add(mesVto);
		fields.add(anioVto);
		fields.add(cbu);
		fields.add(numeroTarjeta);

		fields.add(oppTipoDocumento);
		fields.add(oppRubro);
		fields.add(oppTerminalesEstimadas);
	}
	
	//MGR - 26-07-2010 - Incidente #0000703
	public void soloContribConsumidorFinal(){
		if(tiposContribuyentes != null){
			
			TipoContribuyenteDto tipoContTemp = null;
			java.util.Iterator<TipoContribuyenteDto> it = tiposContribuyentes.iterator();
			
			while (it.hasNext() && tipoContTemp == null) {
				
				TipoContribuyenteDto tipoCont = (TipoContribuyenteDto) it.next();
				if (tipoCont.isConsumidorFinal()) {
					tipoContTemp = tipoCont;
				}
			}
			
			if (tipoContTemp != null) {
				contribuyente.clear();
				contribuyente.clearPreseleccionados();
				contribuyente.addItem(tipoContTemp);
			}
		}
	}
	
	//MGR - #1069
	public void sinOpcionConsumidorFinal(){
		if(tiposContribuyentes != null){
			
			contribuyente.clear();
			contribuyente.clearPreseleccionados();
						
			java.util.Iterator<TipoContribuyenteDto> it = tiposContribuyentes.iterator();
			while (it.hasNext()){
				TipoContribuyenteDto tipoCont = (TipoContribuyenteDto) it.next();
				if (!tipoCont.isConsumidorFinal()) {
					contribuyente.addItem(tipoCont);
				}
			}
		}
	}
	
	public void todosContribuyentes(){
		if(tiposContribuyentes != null){
			contribuyente.clear(); 
			//contribuyente.clearPreseleccionados();
			contribuyente.addAllItems(tiposContribuyentes);
		}
	}

	private void setCombos() {
		
		
		CuentaRpcService.Util.getInstance().getAgregarCuentaInitializer(
				new DefaultWaitCallback<AgregarCuentaInitializer>() {
					private List<ClaseCuentaDto> listaClaseClientePorVendedor;
					private List<ClaseCuentaDto> listaClaseCliente;

					public void success(AgregarCuentaInitializer result) {
						tipoDocumento.addAllItems(result.getTiposDocumento());

						
						//contribuyente.addAllItems(result.getTiposContribuyentes());
						//MGR - 26/07/2010 - Incidente #0000703
						//Si el documento es DNI y es un nuevo cliente (combo esta habilitado), en el combo de contribuyente 
						//solo puede aparecer la opcion "CONSUMIDOR FINAL"
						tiposContribuyentes = result.getTiposContribuyentes();
						
						ListBoxItem selectedDocumentoItem = getTipoDocumento().getSelectedItem();
						if( selectedDocumentoItem != null &&
								((TipoDocumentoDto)selectedDocumentoItem).getCodigoVantive().equals("96") &&
								getContribuyente().isEnabled()){
							soloContribConsumidorFinal();
						}
						//MGR - #1069
						/* Si el documento es CUIT/CUIL y es un nuevo cliente (combo esta habilitado), en el combo de contribuyente 
						no debe aparecer la opcion "CONSUMIDOR FINAL"*/
						else if(selectedDocumentoItem != null &&
							( ((TipoDocumentoDto)selectedDocumentoItem).getCodigoVantive().equals("80") 
							 || ((TipoDocumentoDto)selectedDocumentoItem).getCodigoVantive().equals("1000") ) 
							&& getContribuyente().isEnabled()){
							sinOpcionConsumidorFinal();
						}
						else{
							todosContribuyentes();
						}
						
						
						rubro.addAllItems(result.getRubro());
						sexo.addAllItems(result.getSexo());

						
												
						
						formaPago.addAllItems(result.getFormaPago());
						proveedorAnterior.addAllItems(result.getProveedorAnterior());
						cargo.addAllItems(result.getCargo());
						tipoCuentaBancaria.addAllItems(result.getTipoCuentaBancaria());
						tipoTarjeta.addAllItems(result.getTipoTarjeta());
						tipoCanalVentas.addAllItems(result.getTipoCanalVentas());
						currentYear = result.getAnio();
						motivosNoCierre = result.getMotivoNoCierre();
						llenarListaMotivoNoCierre();
						estadoOpp.addAllItems(result.getEstadoOportunidad());
						estadoOpp.removeItem(EstadoOportunidadEnum.CERRADA.getId().intValue() - 1);
						
						listaClaseClientePorVendedor = result.getClaseClientePorVendedor();
						listaClaseCliente = result.getClaseCliente();
						
						
						//agrego un deferredCommand para asegurarme que la cuenta exista y en base a ello saber que valor ponerle a los combos
						//le agrego un waitWindow 
						WaitWindow.show();
						DeferredCommand.addCommand(new IncrementalCommand() {
							public boolean execute() {
								if (CuentaClientService.tengoCuentaEnNull()){
									return true;
								}
								cargarValoresComboClaseCliente();
								WaitWindow.hide();
								return false;
							}

							private void cargarValoresComboClaseCliente() {
//								MGR - Mejoras Perfil Telemarketing. REQ#1. Cambia la definicion de prospect para Telemarketing
//								Si no es cliente, es prospect o prospect en carga
//								boolean esProspect =RegularExpressionConstants.isVancuc(CuentaClientService.getCodigoVantive());
								boolean esProspect =CuentaClientService.isProspectOrProspectEnCarga();
//						si es prospect le agrego solo los perfilados, sino agrego todo
								if(esProspect){
									claseCliente.addAllItems(listaClaseClientePorVendedor);
								}else{
									claseCliente.addAllItems(listaClaseCliente);
								}
								
							}
						});

						
					}
				});
		for (int i = 1; i < 13; i++) {
			mesVto.addItem(Integer.toString(i), Integer.toString(i));
		}
	}

	private void llenarListaMotivoNoCierre() {
		for (MotivoNoCierreDto motivo : motivosNoCierre) {
			radioGroupMotivos.addRadio(motivo.getDescripcion(), motivo.getId().toString());
		}
		armarRadioOption();
	}

	public void armarRadioOption() {
		int indexRow = 0;
		int indexCol = 0;

		FlexTable titleTable = new FlexTable();
		titleTable.setWidth("100%");
		titleTable.setWidget(0, 0, new Label("Motivo:"));
		titleTable.getFlexCellFormatter().setWidth(0, 0, "15%");

		radioOpsTable.setWidget(indexRow++, 0, titleTable);

		for (RadioButtonWithValue radioBtn : getRadioGroupMotivos().getRadios()) {
			radioOpsTable.setWidget(indexRow, indexCol++, radioBtn);
			if (indexCol == 3) {
				indexCol = 0;
				indexRow++;
			}
		}

		FlexTable obsTable = new FlexTable();
		obsTable.setWidth("100%");
		obsTable.setWidget(0, 0, getOppObservacionesLabel());
		obsTable.setWidget(0, 1, getOppObservaciones());
		obsTable.getFlexCellFormatter().setWidth(0, 0, "15%");
		obsTable.getFlexCellFormatter().setWidth(0, 1, "85%");
		radioOpsTable.setWidget(++indexRow, 0, obsTable);
		radioOpsTable.getFlexCellFormatter().setColSpan(indexRow, 0, 3);
	}

	public void exportarNombreApellidoARazonSocial() {
		if (tipoDocumento.getSelectedItemText() != "CUIT") {
			nombre.setText(nombre.getText().trim().toUpperCase());
			apellido.setText(apellido.getText().trim().toUpperCase());
			razonSocial.setText(nombre.getText() + " " + apellido.getText());
		}
	}

	private void setAtributosDeCampos() {

		// style
		oppVencimiento.addStyleName("fontNormalGris");
		oppEstado.addStyleName("fontNormalGris");
		oppCompetenciaProv.addStyleName("fontNormalGris");
		oppCompetenciaEquipo.addStyleName("fontNormalGris");
		oppVisitas.addStyleName("fontNormalGris");
		observaciones.addStyleName("obsTextAreaCuentaData");
		oppObservaciones.addStyleName("obsTextAreaCuentaData");

		// nombres
		nombre.setName(Sfa.constant().nombre());
		apellido.setName(Sfa.constant().apellido());
		razonSocial.setName(Sfa.constant().razonSocial());
		nombreDivision.setName(Sfa.constant().nombreDivision());
		contribuyente.setName(Sfa.constant().contribuyente());
		fechaNacimiento.getTextBox().setName(Sfa.constant().fechaNacimiento());
		proveedorAnterior.setName(Sfa.constant().provedorAnterior());
		rubro.setName(Sfa.constant().rubro());
		telPrincipalTextBox.getArea().setName(
				Sfa.constant().telefonoPanelTitle() + " " + Sfa.constant().principal() + " "
						+ Sfa.constant().area());
		telPrincipalTextBox.getNumero().setName(
				Sfa.constant().telefonoPanelTitle() + " " + Sfa.constant().principal() + " "
						+ Sfa.constant().numero());
		telPrincipalTextBox.getInterno().setName(
				Sfa.constant().telefonoPanelTitle() + " " + Sfa.constant().principal() + " "
						+ Sfa.constant().interno());
		telAdicionalTextBox.getArea().setName(
				Sfa.constant().telefonoPanelTitle() + " " + Sfa.constant().adicional() + " "
						+ Sfa.constant().area());
		telAdicionalTextBox.getNumero().setName(
				Sfa.constant().telefonoPanelTitle() + " " + Sfa.constant().adicional() + " "
						+ Sfa.constant().numero());
		telAdicionalTextBox.getInterno().setName(
				Sfa.constant().telefonoPanelTitle() + " " + Sfa.constant().adicional() + " "
						+ Sfa.constant().interno());
		telFaxTextBox.getArea().setName(
				Sfa.constant().telefonoPanelTitle() + " " + Sfa.constant().fax() + " "
						+ Sfa.constant().area());
		telFaxTextBox.getNumero().setName(
				Sfa.constant().telefonoPanelTitle() + " " + Sfa.constant().fax() + " "
						+ Sfa.constant().numero());
		telFaxTextBox.getInterno().setName(
				Sfa.constant().telefonoPanelTitle() + " " + Sfa.constant().fax() + " "
						+ Sfa.constant().interno());
		telCelularTextBox.getArea().setName(
				Sfa.constant().telefonoPanelTitle() + " " + Sfa.constant().celular() + " "
						+ Sfa.constant().area());
		telCelularTextBox.getNumero().setName(
				Sfa.constant().telefonoPanelTitle() + " " + Sfa.constant().celular() + " "
						+ Sfa.constant().numero());
		emailPersonal.setName(Sfa.constant().emailPanelTitle() + " " + Sfa.constant().personal());
		emailLaboral.setName(Sfa.constant().emailPanelTitle() + " " + Sfa.constant().laboral());
		
		//emailFacturaElectronica.setName(Sfa.constant().emailPanelTitle() + " " + Sfa.constant().facturaElectronica());
		
		cbu.setName(Sfa.constant().cbu());
		numeroTarjeta.setName(Sfa.constant().nroTarjeta());
		numeroTarjeta.setName(Sfa.constant().nroTarjeta());
		anioVto.setName(Sfa.constant().vtoAnio());

		// maxLenght
		nombre.setMaxLength(19);
		apellido.setMaxLength(19);
		razonSocial.setMaxLength(40);
		nombreDivision.setMaxLength(40);
		categoria.setMaxLength(15);
		iibb.setMaxLength(20);
		vendedorNombre.setMaxLength(50);
		vendedorTelefono.setMaxLength(15);
		telPrincipalTextBox.getArea().setMaxLength(5);
		telPrincipalTextBox.getNumero().setMaxLength(8);
		telPrincipalTextBox.getInterno().setMaxLength(4);
		telAdicionalTextBox.getArea().setMaxLength(5);
		telAdicionalTextBox.getNumero().setMaxLength(8);
		telAdicionalTextBox.getInterno().setMaxLength(4);
		telFaxTextBox.getArea().setMaxLength(5);
		telFaxTextBox.getNumero().setMaxLength(8);
		telFaxTextBox.getInterno().setMaxLength(4);
		telCelularTextBox.getArea().setMaxLength(5);
		telCelularTextBox.getNumero().setMaxLength(10);
		emailLaboral.setMaxLength(50);
		emailPersonal.setMaxLength(50);
	//	emailFacturaElectronica.setMaxLength(50);
		cbu.setMaxLength(22);

		// formato
		razonSocial.setWidth("90%");
		cbu.setWidth("90%");
		nombreDivision.setWidth("90%");
		usuario.setEnabled(false);
		usuario.setReadOnly(true);
		fechaCreacion.setEnabled(false);
		fechaCreacion.setReadOnly(true);
		oppNroOpp.setEnabled(false);
		oppNroOpp.setReadOnly(true);
		mesVto.setWidth("60");
		anioVto.setWidth("70");
		proveedorAnterior.setWidth("150");
		sexo.setWidth("150");
		formaPago.setWidth("250");
		tipoTarjeta.setWidth("60");
		oppEstado.setWidth("100%");
		oppObservaciones.setWidth("100%");
	}

	private void setAtributosNumeroTarjeta() {
		if (tipoTarjeta.getSelectedItemId().equals(TipoTarjetaEnum.VIS.getId())
				|| tipoTarjeta.getSelectedItemId().equals(TipoTarjetaEnum.MAS.getId())
				|| tipoTarjeta.getSelectedItemId().equals(TipoTarjetaEnum.CAB.getId())) {
			numeroTarjeta.setMaxLength(16);
		} else if (tipoTarjeta.getSelectedItemId().equals(TipoTarjetaEnum.AMX.getId())) {
			numeroTarjeta.setMaxLength(15);
		} else if (tipoTarjeta.getSelectedItemId().equals(TipoTarjetaEnum.DIN.getId())) {
			numeroTarjeta.setMaxLength(14);
		}
	}

	/** Getters de todos los Widgets **/
	public Label getCargoLabel() {
		return cargoLabel;
	}

	public Label getTipoDocLabel() {
		return tipoDocLabel;
	}

	public Label getNumDocLabel() {
		return numDocLabel;
	}

	public Label getRazSocLabel() {
		return razSocLabel;
	}

	public Label getNombreLabel() {
		return nombreLabel;
	}

	public Label getApellidoLabel() {
		return apellidoLabel;
	}

	public Label getSexoLabel() {
		return sexoLabel;
	}

	public Label getFecNacLabel() {
		return fecNacLabel;
	}

	public Label getContrLabel() {
		return contrLabel;
	}

	public Label getProvAntLabel() {
		return provAntLabel;
	}

	public Label getRubroLabel() {
		return rubroLabel;
	}

	public Label getClaseClLabel() {
		return claseClLabel;
	}

	public Label getCategLabel() {
		return categLabel;
	}

	public Label getCicloFacLabel() {
		return cicloFacLabel;
	}

	public Label getVerazLabel() {
		return verazLabel;
	}

	public Label getIibbLabel() {
		return iibbLabel;
	}

	public Label getNomDivLabel() {
		return nomDivLabel;
	}

	public Label getPrincLabel() {
		return prinLabel;
	}

	public Label getNumTarLabel() {
		return numTarLabel;
	}

	public Label getCbuLabel() {
		return cbuLabel;
	}

	public ListBox getTipoDocumento() {
		return tipoDocumento;
	}

	public TextBox getNumeroDocumento() {
		return numeroDocumento;
	}

	public TextBox getRazonSocial() {
		return razonSocial;
	}

	public RegexTextBox getNombre() {
		return nombre;
	}

	public RegexTextBox getApellido() {
		return apellido;
	}

	public ListBox getSexo() {
		return sexo;
	}

	public Widget getFechaNacimientoGrid() {
		Grid datePickerFull = new Grid(1, 2);
		datePickerFull.setWidget(0, 0, fechaNacimiento.getTextBox());
		datePickerFull.setWidget(0, 1, fechaNacimiento);
		return datePickerFull;
	}

	public SimpleDatePicker getFechaNacimiento() {
		return fechaNacimiento;
	}

	public ListBox getContribuyente() {
		return contribuyente;
	}

	public ListBox getProveedorAnterior() {
		return proveedorAnterior;
	}

	public ListBox getRubro() {
		return rubro;
	}

	public ListBox getClaseCliente() {
		return claseCliente;
	}

	public TextBox getCategoria() {
		return categoria;
	}

	public TextBox getCicloFacturacion() {
		return cicloFacturacion;
	}

	public Label getVerazRta() {
		return verazRta;
	}

	public TextArea getObservaciones() {
		return observaciones;
	}

	public TextBox getEmailPersonal() {
		return emailPersonal;
	}

	public TextBox getEmailLaboral() {
		return emailLaboral;
	}
	

	public ListBox getFormaPago() {
		return formaPago;
	}

	public TextBox getUsuario() {
		return usuario;
	}

	public TextBox getFechaCreacion() {
		return fechaCreacion;
	}

	public RegexTextBox getIibb() {
		return iibb;
	}

	public TextBox getVendedorNombre() {
		return vendedorNombre;
	}

	public TextBox getVendedorTelefono() {
		return vendedorTelefono;
	}

	public ListBox getCargo() {
		return cargo;
	}

	public TextBox getNombreDivision() {
		return nombreDivision;
	}

	public Label getUseLabel() {
		return useLabel;
	}

	public ListBox getTipoCuentaBancaria() {
		return tipoCuentaBancaria;
	}

	public ListBox getTipoTarjeta() {
		return tipoTarjeta;
	}

	public ListBox getTipoCanalVentas() {
		return tipoCanalVentas;
	}

	public ListBox getMesVto() {
		return mesVto;
	}

	public RegexTextBox getAnioVto() {
		return anioVto;
	}

	public RegexTextBox getCbu() {
		return cbu;
	}

	public RegexTextBox getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public List<TipoTelefonoDto> getTipoTelefono() {
		return tipoTelefono;
	}

	public TelefonoTextBox getTelPrincipalTextBox() {
		return telPrincipalTextBox;
	}

	public TelefonoTextBox getTelAdicionalTextBox() {
		return telAdicionalTextBox;
	}

	public TelefonoTextBox getTelCelularTextBox() {
		return telCelularTextBox;
	}

	public TelefonoTextBox getTelFaxTextBox() {
		return telFaxTextBox;
	}

	public List<Widget> getCamposObligatorios() {
		// ademas de devolver la lista de campos obligatorios, actua de reset.
		camposObligatorios.clear();
		camposObligatorios.add(nombre);
		camposObligatorios.add(apellido);
		camposObligatorios.add(razonSocial);
		camposObligatorios.add(contribuyente);
		camposObligatorios.add(proveedorAnterior);
		camposObligatorios.add(rubro);
		camposObligatorios.add(telPrincipalTextBox.getNumero());
		return camposObligatorios;
	}

	public List<Widget> getLabelsObligatorios() {
		if (labelsObligatorios.isEmpty()) {
			labelsObligatorios.add(nombreLabel);
			labelsObligatorios.add(nomDivLabel);
			labelsObligatorios.add(apellidoLabel);
			labelsObligatorios.add(razSocLabel);
			labelsObligatorios.add(contrLabel);
			labelsObligatorios.add(provAntLabel);
			labelsObligatorios.add(rubroLabel);
			labelsObligatorios.add(prinLabel);
			labelsObligatorios.add(cbuLabel);
			labelsObligatorios.add(numTarLabel);
		}
		return labelsObligatorios;
	}

	public List<Widget> getCamposObligatoriosFormaPago() {
		return camposObligatoriosFormaPago;
	}

	public SimpleLink getValidarTarjeta() {
		return validarTarjeta;
	}

	public TextBox getUse() {
		return use;
	}

	public int getCurrentYear() {
		return currentYear;
	}

	public RadioButtonGroup getRadioGroupMotivos() {
		return radioGroupMotivos;
	}

	public Label getObservLabel() {
		return observLabel;
	}

	public Label getOppNumero() {
		return oppNumero;
	}

	public Label getOppVencimientoLabel() {
		return oppVencimientoLabel;
	}

	public Label getOppEstadoLabel() {
		return oppEstadoLabel;
	}

	public Label getOppCompetenciaProvLabel() {
		return oppCompetenciaProvLabel;
	}

	public Label getOppCompetenciaEquipoLabel() {
		return oppCompetenciaEquipoLabel;
	}

	public Label getOppTerminalesEstimadasLabel() {
		return oppTerminalesEstimadasLabel;
	}

	public Label getOppVisitasLabel() {
		return oppVisitasLabel;
	}

	public TextBox getOppTipoDocumento() {
		return oppTipoDocumento;
	}

	public Label getOppVencimiento() {
		return oppVencimiento;
	}

	public Label getOppEstado() {
		return oppEstado;
	}

	public TextBox getOppRubro() {
		return oppRubro;
	}

	public Label getOppCompetenciaProv() {
		return oppCompetenciaProv;
	}

	public Label getOppCompetenciaEquipo() {
		return oppCompetenciaEquipo;
	}

	public TextBox getOppTerminalesEstimadas() {
		return oppTerminalesEstimadas;
	}

	public Label getOppVisitas() {
		return oppVisitas;
	}

	public ListBox getEstadoOpp() {
		return estadoOpp;
	}

	public Label getOppNroLabel() {
		return oppNroLabel;
	}

	public TextBox getOppNroOpp() {
		return oppNroOpp;
	}

	public Label getOppEstadoPopupLabel() {
		return oppEstadoPopupLabel;
	}

	public Label getOppObservacionesLabel() {
		return oppObservacionesLabel;
	}

	public TextArea getOppObservaciones() {
		return oppObservaciones;
	}

	public FlexTable getRadioOpsTable() {
		return radioOpsTable;
	}

	public List<MotivoNoCierreDto> getMotivosNoCierre() {
		return motivosNoCierre;
	}

	public boolean tieneFacturaElectronicaHabilitada() {
		return facturaElectronica != null;
	}

	public FacturaElectronicaDto getFacturaElectronica() {
		return facturaElectronica;
	}

	public void setFacturaElectronica(FacturaElectronicaDto facturaElectronica) {
		this.facturaElectronica = facturaElectronica;
	}

	public void setFacturaElectronicaPanel(FacturaElectronicaPanel facturaElectronicaPanel) {
		this.facturaElectronicaPanel = facturaElectronicaPanel;
	}

	public FacturaElectronicaPanel getFacturaElectronicaPanel() {
		return facturaElectronicaPanel;
	}
}
