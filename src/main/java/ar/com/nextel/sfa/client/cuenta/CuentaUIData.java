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
import ar.com.nextel.sfa.client.enums.TipoFormaPagoEnum;
import ar.com.nextel.sfa.client.initializer.AgregarCuentaInitializer;
import ar.com.nextel.sfa.client.widget.TelefonoTextBox;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.datepicker.SimpleDatePicker;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.FlexTable;
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
	private ListBox anioVto           = new ListBox();
	
	private TextBox numeroDocumento  = new TextBox();
	private TextBox razonSocial      = new TextBox();
	private TextBox nombre           = new TextBox();
	private TextBox apellido         = new TextBox();
	private TextBox categoria        = new TextBox();
	private TextBox nombreDivision   = new TextBox();
	private TextBox iibb             = new TextBox();
	private TextBox cicloFacturacion = new TextBox();
	private TextBox emailPersonal    = new TextBox();
	private TextBox emailLaboral     = new TextBox();
	private TextBox cbu              = new TextBox();
	private TextBox numeroTarjeta    = new TextBox();

	private TelefonoTextBox telPrincipalTextBox = new TelefonoTextBox();
	private TelefonoTextBox telAdicionalTextBox = new TelefonoTextBox();
	private TelefonoTextBox telCelularTextBox   = new TelefonoTextBox(false);
	private TelefonoTextBox telFaxTextBox       = new TelefonoTextBox();
	
	private TextBox usuario          = new TextBox();
	private TextBox fechaCreacion    = new TextBox();
	
	private TextBox vendedorNombre   = new TextBox();
	private Label   vendedorTelefono = new Label();
	private Label   use              = new Label();
	
	private TextArea observaciones = new TextArea();

	private SimpleDatePicker fechaNacimiento = new SimpleDatePicker(false);
	
	private Label veraz = new Label("TODO");


	PersonaDto persona = new PersonaDto();
	List <Widget>camposObligatorios; 
	List <Widget>camposObligatoriosFormaPago;
	List <TipoTelefonoDto>tipoTelefono = new ArrayList();

	public CuentaUIData() {
        init();
	}

	public void init() {
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
		
		setCombos();
		cbu.setWidth("90%");
		observaciones.addStyleName("textAreaCuentaData");
		usuario.setEnabled(false);
		fechaCreacion.setEnabled(false);
		
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
		fields.add(use);
		fields.add(proveedorAnterior);
		fields.add(rubro);
		fields.add(claseCliente);
		fields.add(categoria);
		fields.add(iibb);
		fields.add(cicloFacturacion);
		fields.add(veraz);
		fields.add(observaciones);
		fields.add(emailPersonal);
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
			        for(int i=0;i<6;i++) {
			        	String ano = Integer.toString(result.getAnio()+i);
			 		    anioVto.addItem(ano, ano);
			        }
				}
			});
		for(int i=1;i<13;i++) {
        	mesVto.addItem(Integer.toString(i), Integer.toString(i));
        }
	}

	
//	public void setVisibilidadFormasDePagoYactualizarCamposObligatorios(String idSelected) {
//		if (idSelected.equals(TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo())) {
//			CuentaDatosForm.getInstance().setVisibleFormaPagoCuentaBancaria();
//		} else if (idSelected.equals(TipoFormaPagoEnum.TARJETA_CREDITO.getTipo())) {
//			CuentaDatosForm.getInstance().setVisibleFormaPagoTarjeta();
//		} else {  //	if (idSelected.equals(TipoFormaPagoEnum.EFECTIVO.getTipo())) {
//			CuentaDatosForm.getInstance().setVisibleFormaPagoEfectivo();
//		}
//	}
	
	private void exportarNombreApellidoARazonSocial() {
		nombre.setText(nombre.getText().trim().toUpperCase());
		apellido.setText(apellido.getText().trim().toUpperCase());
		razonSocial.setText(nombre.getText() + " " + apellido.getText());
	}
	
	public PersonaDto getPersona() {
		persona.setApellido(apellido.getText());
		//TODO: Revisar lo del Documento!
		DocumentoDto doc = new DocumentoDto();
		TipoDocumentoDto tipoDoc = new TipoDocumentoDto(Long.parseLong(tipoDocumento.getSelectedItem().getItemValue()),tipoDocumento.getSelectedItem().getItemText());
		doc.setNumero(numeroDocumento.getText());
		doc.setTipoDocumento(tipoDoc);
		persona.setDocumento(doc);
		//
		persona.setFechaNacimiento(fechaNacimiento.getSelectedDate());
		persona.setNombre(nombre.getText());
		persona.setRazonSocial(razonSocial.getText());
		persona.setSexo(new SexoDto(Long.parseLong(sexo.getSelectedItem().getItemValue()),sexo.getSelectedItem().getItemText()));
		return persona;
	}
	
	/** Getters de todos los Widgets **/
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

	public Label getVendedorTelefono() {
		return vendedorTelefono;
	}

	public ListBox getCargo() {
		return cargo;
	}

	public TextBox getNombreDivision() {
		return nombreDivision;
	}

	public Label getUse() {
		return use;
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

	public ListBox getAnioVto() {
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
		if(camposObligatorios==null) {
			camposObligatorios = new ArrayList<Widget>();
			camposObligatorios.add(nombre);
			camposObligatorios.add(apellido);
			camposObligatorios.add(razonSocial);
			camposObligatorios.add(contribuyente);
			camposObligatorios.add(proveedorAnterior);
			camposObligatorios.add(rubro);
			camposObligatorios.add(telPrincipalTextBox.getArea());
			camposObligatorios.add(telPrincipalTextBox.getNumero());
		}		
		return camposObligatorios;
	}
	public List<Widget> getCamposObligatoriosFormaPago() {
		if(camposObligatoriosFormaPago==null) {
			camposObligatoriosFormaPago = new ArrayList<Widget>();
			camposObligatoriosFormaPago.add(cbu);
			camposObligatoriosFormaPago.add(numeroTarjeta);
			camposObligatoriosFormaPago.add(anioVto);
		}		
		return camposObligatoriosFormaPago;
	}
	
}
