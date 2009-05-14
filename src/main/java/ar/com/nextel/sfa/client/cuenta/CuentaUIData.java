package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.DocumentoDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.SexoDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.dto.TipoTelefonoDto;
import ar.com.nextel.sfa.client.enums.TipoTarjetaEnum;
import ar.com.nextel.sfa.client.initializer.AgregarCuentaInitializer;
import ar.com.nextel.sfa.client.widget.TelefonoTextBox;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.datepicker.SimpleDatePicker;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FocusListener;
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

	private ListBox tipoDocumento     = new ListBox();
	private ListBox sexo              = new ListBox();
	private ListBox contribuyente     = new ListBox();
	private ListBox rubro             = new ListBox();
	private ListBox formaPago         = new ListBox();
	private ListBox claseCliente      = new ListBox();
	private ListBox proveedorAnterior = new ListBox();
	private ListBox cargo             = new ListBox();
	private ListBox tipoCuentaBancaria= new ListBox();
	private ListBox tipoTarjeta       = new ListBox();
	private ListBox tipoCanalVentas   = new ListBox();
	private ListBox mesVto            = new ListBox();
	
	private TextBox numeroDocumento  = new TextBox();
	private TextBox razonSocial      = new TextBox();
	private TextBox nombre           = new TextBox();
	private TextBox apellido         = new TextBox();
	private TextBox categoria        = new TextBox();
	private TextBox nombreDivision   = new TextBox();
	private TextBox iibb             = new TextBox();
	private TextBox use              = new TextBox();
	private TextBox cicloFacturacion = new TextBox();
	private TextBox emailPersonal    = new TextBox();
	private TextBox emailLaboral     = new TextBox();
	private TextBox cbu              = new TextBox();
	private TextBox numeroTarjeta    = new TextBox();
	private TextBox anioVto          = new TextBox();

	private TelefonoTextBox telPrincipalTextBox = new TelefonoTextBox();
	private TelefonoTextBox telAdicionalTextBox = new TelefonoTextBox();
	private TelefonoTextBox telCelularTextBox   = new TelefonoTextBox(false);
	private TelefonoTextBox telFaxTextBox       = new TelefonoTextBox();
	
	private TextBox usuario          = new TextBox();
	private TextBox fechaCreacion    = new TextBox();
	
	private TextBox vendedorNombre   = new TextBox();
	private TextBox vendedorTelefono = new TextBox();
	
	
	private Label   tipoDocLabel  = new Label(Sfa.constant().tipoDocumento());
	private Label   numDocLabel   = new Label(Sfa.constant().numero());
	private Label   razSocLabel   = new Label(Sfa.constant().razonSocial());
	private Label   nombreLabel   = new Label(Sfa.constant().nombre());
	private Label   apellidoLabel = new Label(Sfa.constant().apellido());
	private Label   sexoLabel     = new Label(Sfa.constant().sexo());
	private Label   fecNacLabel   = new Label(Sfa.constant().fechaNacimiento());
	private Label   contrLabel    = new Label(Sfa.constant().contribuyente());
	private Label   provAntLabel  = new Label(Sfa.constant().provedorAnterior());
	private Label   rubroLabel    = new Label(Sfa.constant().rubro());
	private Label   claseClLabel  = new Label(Sfa.constant().claseCliente());
	private Label   categLabel    = new Label(Sfa.constant().categoria());
	private Label   cicloFacLabel = new Label(Sfa.constant().cicloFacturacion());
	private Label   verazLabel    = new Label(Sfa.constant().veraz());
	private Label   useLabel      = new Label(Sfa.constant().use());
	private Label   cargoLabel    = new Label(Sfa.constant().cargo());
	private Label   iibbLabel     = new Label(Sfa.constant().iibb());
	private Label   nomDivLabel   = new Label(Sfa.constant().nombreDivision());
	private Label   cbuLabel      = new Label(Sfa.constant().cbu());
	private Label   numTarLabel   = new Label(Sfa.constant().nroTarjeta());
	private Label   prinLabel     = new Label(Sfa.constant().principal());
	
	private TextArea observaciones = new TextArea();
	private SimpleDatePicker fechaNacimiento = new SimpleDatePicker(false);
	private SimpleLink validarTarjeta = new SimpleLink(Sfa.constant().validarTarjeta(), "#", true);
	private Label veraz = new Label("TODO");

	PersonaDto persona = new PersonaDto();
	List <Widget>camposObligatorios =  new ArrayList<Widget>(); 
	List <Widget>camposObligatoriosFormaPago = new ArrayList<Widget>();
	List <TipoTelefonoDto>tipoTelefono = new ArrayList<TipoTelefonoDto>();
    private int currentYear;	
	
	public CuentaUIData() {
        init();
	}

	public void init() {
		sexo.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				CuentaDatosForm.getInstance().cambiarVisibilidadCamposSegunSexo(); 
			}
		});
		formaPago.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				CuentaDatosForm.getInstance().setVisiblePanelFormaPagoYActualizarCamposObligatorios(((ListBox) sender).getSelectedItemId());
			}
		});
		nombre.addFocusListener(new FocusListener() {
			public void onFocus(Widget sender) {
			}
			public void onLostFocus(Widget sender) {
				exportarNombreApellidoARazonSocial();
			}
		});
		apellido.addFocusListener(new FocusListener() {
			public void onFocus(Widget sender) {
			}
			public void onLostFocus(Widget sender) {
				exportarNombreApellidoARazonSocial();
			}
		});
		tipoTarjeta.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				setAtributosNumeroTarjeta(); 
			}
		});
		validarTarjeta.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				CuentaDatosForm.getInstance().validarTarjeta();
			}
		});
		setAtributosDeCampos();
		setCombos();
		
	}
	

	private void setCombos() {
		CuentaRpcService.Util.getInstance().getAgregarCuentaInitializer(
			new DefaultWaitCallback<AgregarCuentaInitializer>() {
				public void success(AgregarCuentaInitializer result) {
					tipoDocumento.addAllItems(result.getTiposDocumento());
					contribuyente.addAllItems(result.getTiposContribuyentes());
					rubro.addAllItems(result.getRubro());
					sexo.addAllItems(result.getSexo());
					claseCliente.addAllItems(result.getClaseCliente());
					formaPago.addAllItems(result.getFormaPago());
					proveedorAnterior.addAllItems(result.getProveedorAnterior());
					cargo.addAllItems(result.getCargo());
					tipoCuentaBancaria.addAllItems(result.getTipoCuentaBancaria());
					tipoTarjeta.addAllItems(result.getTipoTarjeta());
					tipoCanalVentas.addAllItems(result.getTipoCanalVentas());
		        	currentYear = result.getAnio();
				}
			});
		for(int i=1;i<13;i++) {
        	mesVto.addItem(Integer.toString(i), Integer.toString(i));
        }
	}

	private void exportarNombreApellidoARazonSocial() {
		nombre.setText(nombre.getText().trim().toUpperCase());
		apellido.setText(apellido.getText().trim().toUpperCase());
		razonSocial.setText(nombre.getText() + " " + apellido.getText());
	}
	
//	public PersonaDto getPersona() {
//		persona.setApellido(apellido.getText());
//		//TODO: Revisar lo del Documento!
//		DocumentoDto doc = new DocumentoDto();
//		TipoDocumentoDto tipoDoc = new TipoDocumentoDto(Long.parseLong(tipoDocumento.getSelectedItem().getItemValue()),tipoDocumento.getSelectedItem().getItemText());
//		doc.setNumero(numeroDocumento.getText());
//		doc.setTipoDocumento(tipoDoc);
//		persona.setDocumento(doc);
//		//
//		persona.setFechaNacimiento(fechaNacimiento.getSelectedDate());
//		persona.setNombre(nombre.getText());
//		persona.setRazonSocial(razonSocial.getText());
//		persona.setSexo(new SexoDto(Long.parseLong(sexo.getSelectedItem().getItemValue()),sexo.getSelectedItem().getItemText()));
//		return persona;
//	}
	
	private void setAtributosDeCampos() {

		//obligatorios (style)
		nombreLabel.addStyleName("req");
		apellidoLabel.addStyleName("req");
		razSocLabel.addStyleName("req");		
		contrLabel.addStyleName("req");
		provAntLabel.addStyleName("req");
		rubroLabel.addStyleName("req");
		prinLabel.addStyleName("req");
		cbuLabel.addStyleName("req");
		numTarLabel.addStyleName("req");
		
        //nombres
		nombre.setName(Sfa.constant().nombre());
		apellido.setName(Sfa.constant().apellido());
		razonSocial.setName(Sfa.constant().razonSocial());		
		contribuyente.setName(Sfa.constant().contribuyente());
        fechaNacimiento.getTextBox().setName(Sfa.constant().fechaNacimiento());
		proveedorAnterior.setName(Sfa.constant().provedorAnterior());
		rubro.setName(Sfa.constant().rubro());
		telPrincipalTextBox.getArea().setName(Sfa.constant().telefonoPanelTitle() + " " + Sfa.constant().principal()  + " " + Sfa.constant().area());
		telPrincipalTextBox.getNumero().setName(Sfa.constant().telefonoPanelTitle()  + " " + Sfa.constant().principal() + " " + Sfa.constant().numero());		
		telPrincipalTextBox.getInterno().setName(Sfa.constant().telefonoPanelTitle()  + " " + Sfa.constant().principal()  + " " + Sfa.constant().interno());
		telAdicionalTextBox.getArea().setName(Sfa.constant().telefonoPanelTitle()  + " " + Sfa.constant().adicional()  + " " + Sfa.constant().area());
		telAdicionalTextBox.getNumero().setName(Sfa.constant().telefonoPanelTitle()  + " " + Sfa.constant().adicional()  + " " + Sfa.constant().numero());
		telAdicionalTextBox.getInterno().setName(Sfa.constant().telefonoPanelTitle()  + " " + Sfa.constant().adicional()  + " " + Sfa.constant().interno());
		telFaxTextBox.getArea().setName(Sfa.constant().telefonoPanelTitle() + " " + Sfa.constant().fax()  + " " + Sfa.constant().area());
		telFaxTextBox.getNumero().setName(Sfa.constant().telefonoPanelTitle() + " " + Sfa.constant().fax()  + " " + Sfa.constant().numero());
		telFaxTextBox.getInterno().setName(Sfa.constant().telefonoPanelTitle() + " " + Sfa.constant().fax()  + " " + Sfa.constant().interno());
		telCelularTextBox.getArea().setName(Sfa.constant().telefonoPanelTitle() + " " + Sfa.constant().celular()  + " " + Sfa.constant().area());
		telCelularTextBox.getNumero().setName(Sfa.constant().telefonoPanelTitle() + " " + Sfa.constant().celular()  + " " + Sfa.constant().numero());
		emailPersonal.setName(Sfa.constant().emailPanelTitle() + " " + Sfa.constant().personal());
		emailLaboral.setName(Sfa.constant().emailPanelTitle() + " " + Sfa.constant().laboral());
		cbu.setName(Sfa.constant().cbu());
		numeroTarjeta.setName(Sfa.constant().nroTarjeta());
		
		//maxLenght
		nombre.setMaxLength(19);
		apellido.setMaxLength(19);
		razonSocial.setMaxLength(40);
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
		cbu.setMaxLength(22);
		
		//formato
		cbu.setWidth("90%");
		observaciones.addStyleName("textAreaCuentaData");
		usuario.setEnabled(false);
		fechaCreacion.setEnabled(false);
		mesVto.setWidth("60");
		anioVto.setWidth("70");
		proveedorAnterior.setWidth("150");
		sexo.setWidth("150");
		formaPago.setWidth("250");
		tipoTarjeta.setWidth("60");
	}
	
	private void setAtributosNumeroTarjeta() {
		if(tipoTarjeta.getSelectedItemId().equals(TipoTarjetaEnum.VIS.getId())
		 ||tipoTarjeta.getSelectedItemId().equals(TipoTarjetaEnum.MAS.getId())
 		 ||tipoTarjeta.getSelectedItemId().equals(TipoTarjetaEnum.CAB.getId())
		) {
			numeroTarjeta.setMaxLength(16);
		}
		else if(tipoTarjeta.getSelectedItemId().equals(TipoTarjetaEnum.AMX.getId())) {
			numeroTarjeta.setMaxLength(15);
		}
		else if(tipoTarjeta.getSelectedItemId().equals(TipoTarjetaEnum.DIN.getId())) {
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
	public TextBox getNombre() {
		return nombre;
	}
	public TextBox getApellido() {
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
	public Label getVeraz() {
		return veraz;
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
	public TextBox getIibb() {
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
	public TextBox getAnioVto() {
		return anioVto;
	}
	public TextBox getCbu() {
		return cbu;
	}
	public TextBox getNumeroTarjeta() {
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
		camposObligatorios.clear();
		camposObligatorios.add(nombre);
		camposObligatorios.add(apellido);
		camposObligatorios.add(razonSocial);
		camposObligatorios.add(contribuyente);
		camposObligatorios.add(proveedorAnterior);
		camposObligatorios.add(rubro);
//		camposObligatorios.add(telPrincipalTextBox.getArea());
		camposObligatorios.add(telPrincipalTextBox.getNumero());
		return camposObligatorios;
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
}
