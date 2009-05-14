package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.CategoriaCuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DatosDebitoCuentaBancariaDto;
import ar.com.nextel.sfa.client.dto.DatosDebitoTarjetaCreditoDto;
import ar.com.nextel.sfa.client.dto.DatosEfectivoDto;
import ar.com.nextel.sfa.client.dto.DatosPagoDto;
import ar.com.nextel.sfa.client.dto.EmailDto;
import ar.com.nextel.sfa.client.dto.TarjetaCreditoValidatorResultDto;
import ar.com.nextel.sfa.client.dto.TelefonoDto;
import ar.com.nextel.sfa.client.dto.TipoEmailDto;
import ar.com.nextel.sfa.client.dto.TipoTelefonoDto;
import ar.com.nextel.sfa.client.enums.SexoEnum;
import ar.com.nextel.sfa.client.enums.TipoDocumentoEnum;
import ar.com.nextel.sfa.client.enums.TipoEmailEnum;
import ar.com.nextel.sfa.client.enums.TipoFormaPagoEnum;
import ar.com.nextel.sfa.client.enums.TipoTelefonoEnum;
import ar.com.nextel.sfa.client.util.FormUtils;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.DualPanel;
import ar.com.nextel.sfa.client.widget.TitledPanel;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;
import ar.com.snoop.gwt.commons.client.window.MessageWindow;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;


public class CuentaDatosForm extends Composite {

	private static CuentaDatosForm instance = new CuentaDatosForm();
	private FlexTable mainPanel;
	private FlexTable datosCuentaTable;
	private FlexTable telefonoTable;
	private FlexTable emailTable;
	private FlexTable formaDePagoTable;
	private FlexTable vendedorTable;
	private DualPanel fechaUsuarioTable;
	private FlexTable usuario;
	private FlexTable fechaCreacion;
	private FlexTable efectivoTable       = new FlexTable();
	private FlexTable cuentaBancariaTable = new FlexTable();
	private FlexTable tarjetaTable        = new FlexTable();
	private CuentaUIData camposTabDatos   = new CuentaUIData();
	private CuentaEdicionTabPanel  cuentaTab = CuentaEdicionTabPanel.getInstance();
	private DatosPagoDto datosPago;
	private List <Widget>camposObligatorios = new ArrayList<Widget>();
	private TitledPanel datosCuentaPanel = new TitledPanel(Sfa.constant().cuentaPanelTitle());;
	private GwtValidator validator = new GwtValidator();
	
	public static CuentaDatosForm getInstance() {
		return instance;
	}
	
	private CuentaDatosForm() {
		mainPanel    = new FlexTable();
		initWidget(mainPanel);
		mainPanel.setWidth("100%");
		mainPanel.addStyleName("abmPanel2");
		mainPanel.setWidget(0,0,createDatosCuentaPanel());
		mainPanel.setWidget(1,0,createTelefonoPanel());
		mainPanel.setWidget(2,0,createEmailPanel());
		mainPanel.setWidget(3,0,createFormaDePagoPanel());
		mainPanel.setWidget(4,0,createVendedorPanel());
		mainPanel.setWidget(5,0,createFechaUsuarioPanel());
	}
	
	private Widget createDatosCuentaPanel() {
		datosCuentaTable = new FlexTable();
		datosCuentaTable.setWidth("80%");
		datosCuentaTable.addStyleName("layout");
		datosCuentaTable.getFlexCellFormatter().setColSpan(1, 1, 4);
		camposTabDatos.getRazonSocial().setWidth("90%");
		armarTablaPanelDatos();
		datosCuentaPanel.add(datosCuentaTable);
		return datosCuentaPanel;
	}

	public void	armarTablaPanelDatos() {
		datosCuentaTable.clear();
		int row = 0;
		datosCuentaTable.setWidget(row, 0, camposTabDatos.getTipoDocLabel());
		datosCuentaTable.setWidget(row, 1, camposTabDatos.getTipoDocumento());
		datosCuentaTable.setWidget(row, 3, camposTabDatos.getNumDocLabel());
		datosCuentaTable.setWidget(row, 4, camposTabDatos.getNumeroDocumento());
		row++; 
		datosCuentaTable.setWidget(row, 0, camposTabDatos.getRazSocLabel());
		datosCuentaTable.setWidget(row, 1, camposTabDatos.getRazonSocial());
		datosCuentaTable.getFlexCellFormatter().setColSpan(row, 1, 4);
		row++;
		datosCuentaTable.setWidget(row, 0, camposTabDatos.getNombreLabel());
		datosCuentaTable.setWidget(row, 1, camposTabDatos.getNombre());
		datosCuentaTable.setWidget(row, 3, camposTabDatos.getApellidoLabel());
		datosCuentaTable.setWidget(row, 4, camposTabDatos.getApellido());
		row++;
		datosCuentaTable.setWidget(row, 0, camposTabDatos.getSexoLabel());
		datosCuentaTable.setWidget(row, 1, camposTabDatos.getSexo());
		datosCuentaTable.setWidget(row, 3, camposTabDatos.getFecNacLabel());
		datosCuentaTable.setWidget(row, 4, camposTabDatos.getFechaNacimientoGrid());
		row++;
		datosCuentaTable.setWidget(row, 0, camposTabDatos.getContrLabel());
		datosCuentaTable.setWidget(row, 1, camposTabDatos.getContribuyente());
		datosCuentaTable.setWidget(row, 3, camposTabDatos.getNomDivLabel());
		datosCuentaTable.setWidget(row, 4, camposTabDatos.getNombreDivision());
		row++;
		if(camposTabDatos.getCargoLabel().isVisible()) {
			datosCuentaTable.setWidget(row, 0, camposTabDatos.getCargoLabel());
			datosCuentaTable.setWidget(row, 1, camposTabDatos.getCargo());
			row++;
		}
		if(camposTabDatos.getIibbLabel().isVisible()) {
			datosCuentaTable.setWidget(row, 0, camposTabDatos.getIibbLabel());
			datosCuentaTable.setWidget(row, 1, camposTabDatos.getIibb());
			row++;
		}
		datosCuentaTable.setWidget(row, 0, camposTabDatos.getProvAntLabel());
		datosCuentaTable.setWidget(row, 1, camposTabDatos.getProveedorAnterior());
		datosCuentaTable.setWidget(row, 3, camposTabDatos.getRubroLabel());
		datosCuentaTable.setWidget(row, 4, camposTabDatos.getRubro());
		row++;
		datosCuentaTable.setWidget(row, 0, camposTabDatos.getClaseCliente());
		datosCuentaTable.setWidget(row, 1, camposTabDatos.getClaseCliente());
		datosCuentaTable.setWidget(row, 3, camposTabDatos.getCategLabel());
		datosCuentaTable.setWidget(row, 4, camposTabDatos.getCategoria());
		row++;
		datosCuentaTable.setWidget(row, 0, camposTabDatos.getCicloFacLabel());
		datosCuentaTable.setWidget(row, 1, camposTabDatos.getCicloFacturacion());
		datosCuentaTable.setWidget(row, 3, camposTabDatos.getVerazLabel());
		datosCuentaTable.setWidget(row, 4, camposTabDatos.getVeraz());
		row++;
		datosCuentaTable.setWidget(row, 0, camposTabDatos.getUseLabel());
		datosCuentaTable.setWidget(row, 1, camposTabDatos.getUse());
	}
	
	private Widget createTelefonoPanel() {
		telefonoTable = new FlexTable();
		telefonoTable.setWidth("80%");
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
		return telefonoPanel;
	}

	private Widget createEmailPanel() {
		emailTable = new FlexTable();
		emailTable.setWidth("80%");
		emailTable.addStyleName("layout");
		TitledPanel emailPanel = new TitledPanel(Sfa.constant().emailPanelTitle());
		emailPanel.add(emailTable);
		
		emailTable.setText(0, 0, Sfa.constant().personal());
		emailTable.setWidget(0, 1, camposTabDatos.getEmailPersonal());
		emailTable.setText(0, 2, Sfa.constant().laboral());
		emailTable.setWidget(0, 3, camposTabDatos.getEmailLaboral());
		return emailPanel;
	}

	private Widget createFormaDePagoPanel() {
		formaDePagoTable = new FlexTable();
		formaDePagoTable.setWidth("100%");
		formaDePagoTable.setWidget(0, 0, getCuentaBancariaPanel());
		formaDePagoTable.setWidget(1, 0, getTarjetaCreditoPanel());
		formaDePagoTable.setWidget(2, 0, getEfectivoPanel());
		
		TitledPanel formaDePagoPanel = new TitledPanel(Sfa.constant().formaDePagoPanelTitle());
		formaDePagoPanel.add(formaDePagoTable);
		
		return formaDePagoPanel;
	}
	
	public FlexTable getEfectivoPanel() {
		efectivoTable.setWidth("80%");
		efectivoTable.addStyleName("layout");
		efectivoTable.setText(0, 0, Sfa.constant().modalidad());
		efectivoTable.setWidget(0, 1, camposTabDatos.getFormaPago());
		efectivoTable.setText(0, 3, null);
		efectivoTable.setText(0, 4, null);
		return efectivoTable;
	}
	
	public FlexTable getCuentaBancariaPanel() {
		cuentaBancariaTable.setWidth("80%");
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
		return cuentaBancariaTable;
	}
	
	public FlexTable getTarjetaCreditoPanel() {
		tarjetaTable.setWidth("80%");
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
		
		return tarjetaTable; 
	}
	
	private Widget createVendedorPanel() {
		vendedorTable = new FlexTable();
		vendedorTable.setWidth("80%");
		vendedorTable.addStyleName("layout");
		TitledPanel vendedorPanel = new TitledPanel(Sfa.constant().vendedorPanelTitle());
		vendedorPanel.add(vendedorTable);
		
		vendedorTable.setText(0, 0, Sfa.constant().vendedorNombre());
		vendedorTable.setWidget(0, 1, camposTabDatos.getVendedorNombre());
		vendedorTable.setText(0, 2, null);
		vendedorTable.setWidget(0, 3, null);
		
		vendedorTable.setText(1, 0, Sfa.constant().telefono());
		vendedorTable.setWidget(1, 1, camposTabDatos.getVendedorTelefono());
		vendedorTable.setText(1, 2, Sfa.constant().canalVentas());
		vendedorTable.setWidget(1, 3, camposTabDatos.getTipoCanalVentas());
		
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
		if (cuentaDto.getPersona()!=null) {
			cargarPanelDatos(cuentaDto);
			cargarPanelTelefonoFax(cuentaDto);
			cargarPanelEmails(cuentaDto);
			cargarPanelFormaPago(cuentaDto);
            cargarPanelVendedor(cuentaDto);
            cargarPanelUsuario(cuentaDto);
		}
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
		camposTabDatos.getFechaNacimiento().setSelectedDate(cuentaDto.getPersona().getFechaNacimiento());
		camposTabDatos.getContribuyente().setSelectedItem(cuentaDto.getTipoContribuyente());
		camposTabDatos.getIibb().setText(cuentaDto.getIibb());
		camposTabDatos.getNombreDivision().setText("TODO");
		camposTabDatos.getProveedorAnterior().setSelectedItem(cuentaDto.getProveedorInicial());
		camposTabDatos.getCategoria().setText(cuentaDto.getCategoriaCuenta().getDescripcion());
		camposTabDatos.getClaseCliente().setSelectedItem(cuentaDto.getClaseCuenta());
		camposTabDatos.getCicloFacturacion().setText(cuentaDto.getCicloFacturacion().getCodigoFNCL());
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
			camposTabDatos.getFormaPago().setSelectedItem(((DatosEfectivoDto)datosPago).getFormaPagoAsociada());
			id_formaPago = TipoFormaPagoEnum.EFECTIVO.getTipo();
		}
		else if (cuentaDto.getDatosPago().isDebitoCuentaBancaria()) {			
			datosPago = (DatosDebitoCuentaBancariaDto) cuentaDto.getDatosPago();
			camposTabDatos.getFormaPago().setSelectedItem(((DatosDebitoCuentaBancariaDto)datosPago).getFormaPagoAsociada());
			camposTabDatos.getTipoCuentaBancaria().selectByValue(((DatosDebitoCuentaBancariaDto)datosPago).getTipoCuentaBancaria().getItemValue());
			camposTabDatos.getCbu().setText(((DatosDebitoCuentaBancariaDto)datosPago).getCbu());
			id_formaPago = TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo();
		}
		else if (cuentaDto.getDatosPago().isDebitoTarjetaCredito()) {			
			datosPago = (DatosDebitoTarjetaCreditoDto) cuentaDto.getDatosPago();
			camposTabDatos.getFormaPago().setSelectedItem(((DatosDebitoTarjetaCreditoDto)datosPago).getFormaPagoAsociada());
			camposTabDatos.getMesVto().setSelectedIndex(((DatosDebitoTarjetaCreditoDto)datosPago).getMesVencimientoTarjeta());
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
		camposTabDatos.getFechaCreacion().setText(DateTimeFormat.getMediumDateFormat().format(cuentaDto.getFechaCreacion()));
    }
    
	public void deshabilitarCamposAlAgregarCuenta(CuentaDto cuentaDto) {
		List <Widget>campos = new ArrayList<Widget>();
		campos.add(camposTabDatos.getRazonSocial());
		campos.add(camposTabDatos.getTipoDocumento());
		campos.add(camposTabDatos.getNumeroDocumento());
		campos.add(camposTabDatos.getCategoria());
		campos.add(camposTabDatos.getClaseCliente());
		campos.add(camposTabDatos.getCicloFacturacion());
	    
		FormUtils.disableFields(campos);
	    
		camposTabDatos.getIibb().setVisible(false);
		camposTabDatos.getCargo().setVisible(false);
		camposTabDatos.getNombreDivision().setVisible(false);
		camposTabDatos.getIibbLabel().setVisible(false);
		camposTabDatos.getCargoLabel().setVisible(false);
		camposTabDatos.getNomDivLabel().setVisible(false);
		camposTabDatos.getCargo().setVisible(cuentaDto.getPersona().getSexo().getItemValue().equals(Long.toString(SexoEnum.ORGANIZACION.getId())));
		camposTabDatos.getCargoLabel().setVisible(cuentaDto.getPersona().getSexo().getItemValue().equals(Long.toString(SexoEnum.ORGANIZACION.getId())));
		armarTablaPanelDatos();
	}
    
	public boolean formularioDatosDirty() {
		boolean retorno = false;
		
		if ( (FormUtils.fieldDirty(camposTabDatos.getRazonSocial(), cuentaTab.getCuenta2editDto().getPersona().getRazonSocial()))
		   ||(FormUtils.fieldDirty(camposTabDatos.getSexo(), cuentaTab.getCuenta2editDto().getPersona().getSexo().getItemValue()))
		   ||(FormUtils.fieldDirty(camposTabDatos.getFechaNacimiento(), cuentaTab.getCuenta2editDto().getPersona().getFechaNacimiento()!=null?DateTimeFormat.getMediumDateFormat().format(cuentaTab.getCuenta2editDto().getPersona().getFechaNacimiento()):""))
   		   ||(FormUtils.fieldDirty(camposTabDatos.getContribuyente(), cuentaTab.getCuenta2editDto().getTipoContribuyente().getItemValue()))
		   ||(FormUtils.fieldDirty(camposTabDatos.getProveedorAnterior(), cuentaTab.getCuenta2editDto().getProveedorInicial().getItemValue())) 
		   ||(FormUtils.fieldDirty(camposTabDatos.getRubro(), cuentaTab.getCuenta2editDto().getRubro().getItemValue()))
		   ||(FormUtils.fieldDirty(camposTabDatos.getClaseCliente(), cuentaTab.getCuenta2editDto().getClaseCuenta().getItemValue()))
		   ||(FormUtils.fieldDirty(camposTabDatos.getCategoria(), cuentaTab.getCuenta2editDto().getCategoriaCuenta().getItemText()))
   		   ||(FormUtils.fieldDirty(camposTabDatos.getCicloFacturacion(), cuentaTab.getCuenta2editDto().getCicloFacturacion().getCodigoFNCL()))
		   ||(FormUtils.fieldDirty(camposTabDatos.getObservaciones(), cuentaTab.getCuenta2editDto().getObservacionesTelMail()))
		) return true;

		if (camposTabDatos.getFormaPago().getSelectedItemId().equals(TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo())) {
			if (cuentaTab.getCuenta2editDto().getDatosPago() instanceof DatosDebitoCuentaBancariaDto) {
				if	(FormUtils.fieldDirty(camposTabDatos.getCbu(), ((DatosDebitoCuentaBancariaDto) cuentaTab.getCuenta2editDto().getDatosPago()).getCbu())) {
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
				)	retorno = true;
			}	
		} else {
			if ((!camposTabDatos.getCbu().getText().trim().equals("")))
				return true;
		}
		
		//telefonos
        //si la cuenta no tiene telefonos ingresados, simplemente se fija que el campo sea distinto de ""
		if (cuentaTab.getCuenta2editDto().getPersona().getTelefonos().size()<1) {
			if ((!camposTabDatos.getTelPrincipalTextBox().getArea().getText().trim().equals("")) 
				||(!camposTabDatos.getTelPrincipalTextBox().getNumero().getText().trim().equals(""))
				||(!camposTabDatos.getTelPrincipalTextBox().getInterno().getText().trim().equals("")) 
			    ||(!camposTabDatos.getTelAdicionalTextBox().getArea().getText().trim().equals(""))
				||(!camposTabDatos.getTelAdicionalTextBox().getNumero().getText().trim().equals(""))
				||(!camposTabDatos.getTelAdicionalTextBox().getInterno().getText().trim().equals(""))
			    ||(!camposTabDatos.getTelCelularTextBox().getArea().getText().trim().equals(""))
				||(!camposTabDatos.getTelCelularTextBox().getNumero().getText().trim().equals(""))
			    ||(!camposTabDatos.getTelFaxTextBox().getArea().getText().trim().equals(""))
				||(!camposTabDatos.getTelFaxTextBox().getNumero().getText().trim().equals(""))
				||(!camposTabDatos.getTelFaxTextBox().getInterno().getText().trim().equals(""))
			) retorno = true;
		} else {
            for (TelefonoDto telefono : cuentaTab.getCuenta2editDto().getPersona().getTelefonos()) {
				TipoTelefonoDto tipoTelefono = telefono.getTipoTelefono();

				if ( tipoTelefono.getId()==TipoTelefonoEnum.PRINCIPAL.getTipo()) {
					if ( (FormUtils.fieldDirty(camposTabDatos.getTelPrincipalTextBox().getArea(),telefono.getArea())) 
						||(FormUtils.fieldDirty(camposTabDatos.getTelPrincipalTextBox().getNumero(),telefono.getNumeroLocal()))
						||(FormUtils.fieldDirty(camposTabDatos.getTelPrincipalTextBox().getInterno(),telefono.getInterno())) 
					) retorno = true;   
				}
				if ( tipoTelefono.getId()==TipoTelefonoEnum.PARTICULAR.getTipo() ||
						tipoTelefono.getId()==TipoTelefonoEnum.ADICIONAL.getTipo()) {
					if ( (FormUtils.fieldDirty(camposTabDatos.getTelAdicionalTextBox().getArea(),telefono.getArea()))
						||(FormUtils.fieldDirty(camposTabDatos.getTelAdicionalTextBox().getNumero(),telefono.getNumeroLocal()))
						||(FormUtils.fieldDirty(camposTabDatos.getTelAdicionalTextBox().getInterno(),telefono.getInterno()))
					) retorno = true;
				}
				if ( tipoTelefono.getId()==TipoTelefonoEnum.CELULAR.getTipo()) {
					if ( (FormUtils.fieldDirty(camposTabDatos.getTelCelularTextBox().getArea(),telefono.getArea()))
						||(FormUtils.fieldDirty(camposTabDatos.getTelCelularTextBox().getNumero(),telefono.getNumeroLocal()))
					) retorno = true;
				}
				if ( tipoTelefono.getId()==TipoTelefonoEnum.FAX.getTipo()) {
					if ( (FormUtils.fieldDirty(camposTabDatos.getTelFaxTextBox().getArea(),telefono.getArea()))
						||(FormUtils.fieldDirty(camposTabDatos.getTelFaxTextBox().getNumero(),telefono.getNumeroLocal()))
						||(FormUtils.fieldDirty(camposTabDatos.getTelFaxTextBox().getInterno(),telefono.getInterno()))
					) retorno = true;
				}
			}
		}
		
		//mails
		if (cuentaTab.getCuenta2editDto().getPersona().getEmails().size()<1) {
            for (EmailDto email : cuentaTab.getCuenta2editDto().getPersona().getEmails()) {
				TipoEmailDto tipo = email.getTipoEmail();
                if (tipo.getId()==TipoEmailEnum.PERSONAL.getTipo()) {
    				if ( (FormUtils.fieldDirty(camposTabDatos.getEmailPersonal(),email.getEmail()))) 
    					retorno = true;
                }
                if (tipo.getId()==TipoEmailEnum.LABORAL.getTipo()) {
    				if ( (FormUtils.fieldDirty(camposTabDatos.getEmailLaboral(),email.getEmail()))) 
    					retorno = true;
                }
            }
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
	
	public List<String> validarCompletitud() {
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

	public List<String> validarCamposTabDatos() {
		validator.clear();

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
	
	public void cambiarVisibilidadCamposSegunSexo() {
		camposTabDatos.getCargoLabel().setVisible(camposTabDatos.getSexo().getSelectedItemId().equals(Long.toString(SexoEnum.ORGANIZACION.getId())));
		camposTabDatos.getCargo().setVisible(camposTabDatos.getSexo().getSelectedItemId().equals(Long.toString(SexoEnum.ORGANIZACION.getId())));
		armarTablaPanelDatos();
	}
	
	private CuentaDto getCuentaDtoFromEditor() {
		CuentaDto cuentaDto = new CuentaDto();
		CategoriaCuentaDto catCtaDto = new CategoriaCuentaDto();
		catCtaDto.setCode(getCamposTabDatos().getCategoria().getSelectedText());
		catCtaDto.setDescripcion(getCamposTabDatos().getCategoria().getText());
//		catCtaDto.setDescripcion(descripcion)(cuentaData.getCategoria().getText());
//		cuentaDto.setCategoriaCuenta(cuentaData.getCategoria().getText());
		return cuentaDto;
	}

	public CuentaUIData getCamposTabDatos() {
		return camposTabDatos;
	}

	public void setCamposTabDatos(CuentaUIData camposTabDatos) {
		this.camposTabDatos = camposTabDatos;
	}
	
}
