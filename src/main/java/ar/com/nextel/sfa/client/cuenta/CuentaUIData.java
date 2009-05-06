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
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.datepicker.SimpleDatePicker;

import com.google.gwt.user.client.ui.ChangeListener;
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
	
	private TextBox vendedorNombre   = new TextBox();
	private Label   vendedorTelefono = new Label();
	private Label   use              = new Label();
	
	private TextArea observaciones = new TextArea();

	private SimpleDatePicker fechaNacimiento = new SimpleDatePicker(false);
	
	private Label veraz = new Label("TODO");
	private Label usuario;
	private Label fechaCreacion;

	private FlexTable efectivoTable       = new FlexTable();
	private FlexTable cuentaBancariaTable = new FlexTable();
	private FlexTable tarjetaTable        = new FlexTable();

	PersonaDto persona = new PersonaDto();
	List <TipoTelefonoDto>tipoTelefono = new ArrayList();

	public CuentaUIData() {
        init();
	}

	public void init() {
		formaPago.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				setVisibilidadFormasDePago(((ListBox) sender).getSelectedItemId());
			}
		});
		setCombos();
		cbu.setWidth("90%");
		observaciones.addStyleName("textAreaCuentaData");
		
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

	public FlexTable getEfectivoPanel() {
		efectivoTable.setWidth("80%");
		efectivoTable.addStyleName("layout");
		efectivoTable.setText(0, 0, Sfa.constant().modalidad());
		efectivoTable.setWidget(0, 1, getFormaPago());
		efectivoTable.setText(0, 3, null);
		efectivoTable.setText(0, 4, null);
		return efectivoTable;
	}
	public FlexTable getCuentaBancariaPanel() {
		cuentaBancariaTable.setWidth("80%");
		cuentaBancariaTable.addStyleName("layout");
		cuentaBancariaTable.setVisible(false);
		
		cuentaBancariaTable.setText(0, 0, Sfa.constant().modalidad());
		cuentaBancariaTable.setWidget(0, 1, getFormaPago());		
		cuentaBancariaTable.setText(0, 3, Sfa.constant().tipoCuenta());
		cuentaBancariaTable.setWidget(0, 4, getTipoCuentaBancaria());
		
		cuentaBancariaTable.setText(1, 0, Sfa.constant().cbu());
		cuentaBancariaTable.setWidget(1, 1, getCbu());		
		cuentaBancariaTable.getFlexCellFormatter().setColSpan(1, 1, 4);
		return cuentaBancariaTable;
	}
	
	public FlexTable getTarjetaCreditoPanel() {
		tarjetaTable.setWidth("80%");
		tarjetaTable.addStyleName("layout");
		tarjetaTable.setVisible(false);
		
		tarjetaTable.setText(0, 0, Sfa.constant().modalidad());
		tarjetaTable.setWidget(0, 1, getFormaPago());		
		tarjetaTable.setText(0, 3, Sfa.constant().tarjetaTipo());
		tarjetaTable.setWidget(0, 4, getTipoTarjeta());
		
		tarjetaTable.setText(1, 0, Sfa.constant().nroTarjeta());
		tarjetaTable.setWidget(1, 1, getNumeroTarjeta());		
		tarjetaTable.setText(1, 3, Sfa.constant().vtoMes());
		tarjetaTable.setWidget(1, 4, getMesVto());

		tarjetaTable.setText(2, 0, Sfa.constant().vtoAnio());
		tarjetaTable.setWidget(2, 1, getAnioVto());		
		tarjetaTable.setText(2, 3, "ValidarTarjeta-TODO");
		tarjetaTable.setWidget(2, 4, null);
		return tarjetaTable; 
	}
	
	public void setVisibilidadFormasDePago(String idSelected) {
		if (idSelected.equals(TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo())) {
			setVisibleFormaPagoCuentaBancaria();
		} else if (idSelected.equals(TipoFormaPagoEnum.TARJETA_CREDITO.getTipo())) {
			setVisibleFormaPagoTarjeta();
		} else {  //	if (idSelected.equals(TipoFormaPagoEnum.EFECTIVO.getTipo())) {
			setVisibleFormaPagoEfectivo();
		}
		
	}
	
    public void setVisibleFormaPagoEfectivo() {
		efectivoTable.setWidget(0, 1, getFormaPago());
		efectivoTable.setVisible(true);
		cuentaBancariaTable.setVisible(false);
		tarjetaTable.setVisible(false);    	
    }
    public void setVisibleFormaPagoCuentaBancaria() {
		cuentaBancariaTable.setWidget(0, 1, getFormaPago());
		efectivoTable.setVisible(false);
		cuentaBancariaTable.setVisible(true);
		tarjetaTable.setVisible(false);    	
    }
    public void setVisibleFormaPagoTarjeta() {
		tarjetaTable.setWidget(0, 1, getFormaPago());
		efectivoTable.setVisible(false);
		cuentaBancariaTable.setVisible(false);
		tarjetaTable.setVisible(true);
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

	public Label getUsuario() {
		return usuario;
	}

	public Label getFechaCreacion() {
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
}
