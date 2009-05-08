package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DatosDebitoCuentaBancariaDto;
import ar.com.nextel.sfa.client.dto.DatosDebitoTarjetaCreditoDto;
import ar.com.nextel.sfa.client.dto.DatosEfectivoDto;
import ar.com.nextel.sfa.client.dto.DatosPagoDto;
import ar.com.nextel.sfa.client.dto.EmailDto;
import ar.com.nextel.sfa.client.dto.TelefonoDto;
import ar.com.nextel.sfa.client.dto.TipoEmailDto;
import ar.com.nextel.sfa.client.dto.TipoTelefonoDto;
import ar.com.nextel.sfa.client.enums.TipoEmailEnum;
import ar.com.nextel.sfa.client.enums.TipoFormaPagoEnum;
import ar.com.nextel.sfa.client.enums.TipoTelefonoEnum;
import ar.com.nextel.sfa.client.util.FormUtils;
import ar.com.nextel.sfa.client.widget.DualPanel;
import ar.com.nextel.sfa.client.widget.TitledPanel;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
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
	private CuentaUIData cuentaEditor;
	private DatosPagoDto datosPago;
	private List <Widget>camposObligatorios = new ArrayList<Widget>();
	
	public static CuentaDatosForm getInstance() {
		return instance;
	}
	
	private CuentaDatosForm() {
		cuentaEditor = new CuentaUIData();
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
		cuentaEditor.getRazonSocial().setWidth("90%");
		
		TitledPanel datosCuentaPanel = new TitledPanel(Sfa.constant().cuentaPanelTitle());
        int row = 0;
		datosCuentaTable.setText(row, 0, Sfa.constant().tipoDocumento());
		datosCuentaTable.setWidget(row, 1, cuentaEditor.getTipoDocumento());
		datosCuentaTable.setText(row, 3, Sfa.constant().numero());
		datosCuentaTable.setWidget(row, 4, cuentaEditor.getNumeroDocumento());
		datosCuentaTable.getFlexCellFormatter().addStyleName(row, 3, "req");
		row++; 
		datosCuentaTable.setText(row, 0, Sfa.constant().razonSocial());
		datosCuentaTable.setWidget(row, 1, cuentaEditor.getRazonSocial());
		datosCuentaTable.getFlexCellFormatter().setColSpan(row, 1, 4);
		datosCuentaTable.getFlexCellFormatter().addStyleName(row, 0, "req");
		row++;
		datosCuentaTable.setText(row, 0, Sfa.constant().nombre());
		datosCuentaTable.setWidget(row, 1, cuentaEditor.getNombre());
		datosCuentaTable.setText(row, 3, Sfa.constant().apellido());
		datosCuentaTable.setWidget(row, 4, cuentaEditor.getApellido());
		datosCuentaTable.getFlexCellFormatter().addStyleName(row, 0, "req");
		datosCuentaTable.getFlexCellFormatter().addStyleName(row, 3, "req");
		row++;
		datosCuentaTable.setText(row, 0, Sfa.constant().sexo());
		datosCuentaTable.setWidget(row, 1, cuentaEditor.getSexo());
		datosCuentaTable.setText(row, 3, Sfa.constant().fechaNacimiento());
		datosCuentaTable.setWidget(row, 4, cuentaEditor.getFechaNacimientoGrid());
		row++;
		datosCuentaTable.setText(row, 0, Sfa.constant().contribuyente());
		datosCuentaTable.setWidget(row, 1, cuentaEditor.getContribuyente());
		datosCuentaTable.setText(row, 3, Sfa.constant().cargo());
		datosCuentaTable.setWidget(row, 4, cuentaEditor.getCargo());
		datosCuentaTable.getFlexCellFormatter().addStyleName(row, 0, "req");
		row++;
		datosCuentaTable.setText(row, 0, Sfa.constant().iibb());
		datosCuentaTable.setWidget(row, 1, cuentaEditor.getIibb());
		datosCuentaTable.setText(row, 3, Sfa.constant().nombreDivision());
		datosCuentaTable.setWidget(row, 4, cuentaEditor.getNombreDivision());
		row++;
		datosCuentaTable.setText(row, 0, Sfa.constant().provedorAnterior());
		datosCuentaTable.setWidget(row, 1, cuentaEditor.getProveedorAnterior());
		datosCuentaTable.setText(row, 3, Sfa.constant().rubro());
		datosCuentaTable.setWidget(row, 4, cuentaEditor.getRubro());
		datosCuentaTable.getFlexCellFormatter().addStyleName(row, 0, "req");
		datosCuentaTable.getFlexCellFormatter().addStyleName(row, 3, "req");
		row++;
		datosCuentaTable.setText(row, 0, Sfa.constant().claseCliente());
		datosCuentaTable.setWidget(row, 1, cuentaEditor.getClaseCliente());
		datosCuentaTable.setText(row, 3, Sfa.constant().categoria());
		datosCuentaTable.setWidget(row, 4, cuentaEditor.getCategoria());
		row++;
		datosCuentaTable.setText(row, 0, Sfa.constant().cicloFacturacion());
		datosCuentaTable.setWidget(row, 1, cuentaEditor.getCicloFacturacion());
		datosCuentaTable.setText(row, 3, Sfa.constant().veraz());
		datosCuentaTable.setWidget(row, 4, cuentaEditor.getVeraz());
		row++;
		datosCuentaTable.setText(row, 0, Sfa.constant().use());
		datosCuentaTable.setWidget(row, 1, cuentaEditor.getUse());
		
		
		datosCuentaPanel.add(datosCuentaTable);
		return datosCuentaPanel;
	}

	private Widget createTelefonoPanel() {
		telefonoTable = new FlexTable();
		telefonoTable.setWidth("80%");
		telefonoTable.addStyleName("layout");
		TitledPanel telefonoPanel = new TitledPanel(Sfa.constant().telefonoPanelTitle());
		telefonoPanel.add(telefonoTable);
		cuentaEditor.getObservaciones().addStyleName("obsTextAreaCuentaDatos");
		
		telefonoTable.setText(0, 0, Sfa.constant().principal());
		telefonoTable.setWidget(0, 1, cuentaEditor.getTelPrincipalTextBox());
		telefonoTable.setText(0, 2, Sfa.constant().adicional());
		telefonoTable.setWidget(0, 3, cuentaEditor.getTelAdicionalTextBox());
		telefonoTable.setText(1, 0, Sfa.constant().celular());
		telefonoTable.setWidget(1, 1, cuentaEditor.getTelCelularTextBox());
		telefonoTable.setText(1, 2, Sfa.constant().fax());
		telefonoTable.setWidget(1, 3, cuentaEditor.getTelFaxTextBox());
		telefonoTable.setText(2, 0, Sfa.constant().observaciones());
		telefonoTable.setWidget(2, 1, cuentaEditor.getObservaciones());
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
		emailTable.setWidget(0, 1, cuentaEditor.getEmailPersonal());
		emailTable.setText(0, 2, Sfa.constant().laboral());
		emailTable.setWidget(0, 3, cuentaEditor.getEmailLaboral());
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
		efectivoTable.setWidget(0, 1, cuentaEditor.getFormaPago());
		efectivoTable.setText(0, 3, null);
		efectivoTable.setText(0, 4, null);
		return efectivoTable;
	}
	
	public FlexTable getCuentaBancariaPanel() {
		cuentaBancariaTable.setWidth("80%");
		cuentaBancariaTable.addStyleName("layout");
		cuentaBancariaTable.setVisible(false);
		
		cuentaBancariaTable.setText(0, 0, Sfa.constant().modalidad());
		cuentaBancariaTable.setWidget(0, 1, cuentaEditor.getFormaPago());		
		cuentaBancariaTable.setText(0, 3, Sfa.constant().tipoCuenta());
		cuentaBancariaTable.setWidget(0, 4, cuentaEditor.getTipoCuentaBancaria());
		
		cuentaBancariaTable.setText(1, 0, Sfa.constant().cbu());
		cuentaBancariaTable.setWidget(1, 1, cuentaEditor.getCbu());		
		cuentaBancariaTable.getFlexCellFormatter().setColSpan(1, 1, 4);
		cuentaBancariaTable.getFlexCellFormatter().addStyleName(1, 0, "req");
		return cuentaBancariaTable;
	}
	
	public FlexTable getTarjetaCreditoPanel() {
		tarjetaTable.setWidth("80%");
		tarjetaTable.addStyleName("layout");
		tarjetaTable.setVisible(false);
		
		tarjetaTable.setText(0, 0, Sfa.constant().modalidad());
		tarjetaTable.setWidget(0, 1, cuentaEditor.getFormaPago());		
		tarjetaTable.setText(0, 3, Sfa.constant().tarjetaTipo());
		tarjetaTable.setWidget(0, 4, cuentaEditor.getTipoTarjeta());
		
		tarjetaTable.setText(1, 0, Sfa.constant().nroTarjeta());
		tarjetaTable.setWidget(1, 1, cuentaEditor.getNumeroTarjeta());		
		tarjetaTable.setText(1, 3, Sfa.constant().vtoMes());
		tarjetaTable.setWidget(1, 4, cuentaEditor.getMesVto());

		tarjetaTable.setText(2, 0, Sfa.constant().vtoAnio());
		tarjetaTable.setWidget(2, 1, cuentaEditor.getAnioVto());		
		tarjetaTable.setText(2, 3, "ValidarTarjeta-TODO");
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
		vendedorTable.setWidget(0, 1, cuentaEditor.getVendedorNombre());
		vendedorTable.setText(0, 2, null);
		vendedorTable.setWidget(0, 3, null);
		
		vendedorTable.setText(1, 0, Sfa.constant().telefono());
		vendedorTable.setWidget(1, 1, cuentaEditor.getVendedorTelefono());
		vendedorTable.setText(1, 2, Sfa.constant().canalVentas());
		vendedorTable.setWidget(1, 3, cuentaEditor.getTipoCanalVentas());
		
		return vendedorPanel;
	}
	
	private Widget createFechaUsuarioPanel() {
		fechaUsuarioTable = new DualPanel();
		usuario = new FlexTable();
		fechaCreacion = new FlexTable();
		usuario.addStyleName("layout");
		fechaCreacion.addStyleName("layout");
		usuario.setText(0, 0, Sfa.constant().usuario());
		usuario.setWidget(0, 1, cuentaEditor.getUsuario());
		fechaCreacion.setText(0, 0, Sfa.constant().fechaCreacion());
		fechaCreacion.setWidget(0, 1, cuentaEditor.getFechaCreacion());

		fechaUsuarioTable.setLeft(usuario);
		fechaUsuarioTable.setRight(fechaCreacion);

		return fechaUsuarioTable;
	}

	public void reset() {
		cuentaEditor.clean();
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
			cuentaEditor.getTipoDocumento().setSelectedItem(cuentaDto.getPersona().getDocumento().tipoDocumento) ;
			cuentaEditor.getNumeroDocumento().setText(cuentaDto.getPersona().getDocumento().getNumero());
		}
		cuentaEditor.getRazonSocial().setText(cuentaDto.getPersona().getRazonSocial());
		cuentaEditor.getNombre().setText(cuentaDto.getPersona().getNombre());
		cuentaEditor.getApellido().setText(cuentaDto.getPersona().getApellido());
		cuentaEditor.getSexo().setSelectedItem(cuentaDto.getPersona().getSexo());
		cuentaEditor.getFechaNacimiento().setSelectedDate(cuentaDto.getPersona().getFechaNacimiento());
		cuentaEditor.getContribuyente().setSelectedItem(cuentaDto.getTipoContribuyente());
		cuentaEditor.getIibb().setText(cuentaDto.getIibb());
		cuentaEditor.getNombreDivision().setText("TODO");
		//cuentaEditor.getCarto(). TODO
		cuentaEditor.getProveedorAnterior().setSelectedItem(cuentaDto.getProveedorInicial());
		cuentaEditor.getCategoria().setText(cuentaDto.getCategoriaCuenta().getDescripcion());
		cuentaEditor.getClaseCliente().setSelectedItem(cuentaDto.getClaseCuenta());
		cuentaEditor.getCicloFacturacion().setText(cuentaDto.getCicloFacturacion().getCodigoFNCL());
		cuentaEditor.getUse().setText(cuentaDto.getUse());
	}
	
	public void cargarPanelTelefonoFax(CuentaDto cuentaDto) {
		for ( TelefonoDto telefono : cuentaDto.getPersona().getTelefonos()) {
			TipoTelefonoDto tipoTelefono = telefono.getTipoTelefono();
			if ( tipoTelefono.getId()==TipoTelefonoEnum.PRINCIPAL.getTipo()) {
				cuentaEditor.getTelPrincipalTextBox().getArea().setText(telefono.getArea());
				cuentaEditor.getTelPrincipalTextBox().getNumero().setText(telefono.getNumeroLocal());
				cuentaEditor.getTelPrincipalTextBox().getInterno().setText(telefono.getInterno());
			}
			if ( tipoTelefono.getId()==TipoTelefonoEnum.PARTICULAR.getTipo() ||
					 tipoTelefono.getId()==TipoTelefonoEnum.ADICIONAL.getTipo()) {
				cuentaEditor.getTelAdicionalTextBox().getArea().setText(telefono.getArea());
				cuentaEditor.getTelAdicionalTextBox().getNumero().setText(telefono.getNumeroLocal());
				cuentaEditor.getTelAdicionalTextBox().getInterno().setText(telefono.getInterno());
			}
			if ( tipoTelefono.getId()==TipoTelefonoEnum.CELULAR.getTipo()) {
				cuentaEditor.getTelCelularTextBox().getArea().setText(telefono.getArea());
				cuentaEditor.getTelCelularTextBox().getNumero().setText(telefono.getNumeroLocal());
			}
			if ( tipoTelefono.getId()==TipoTelefonoEnum.FAX.getTipo()) {
				cuentaEditor.getTelFaxTextBox().getArea().setText(telefono.getArea());
				cuentaEditor.getTelFaxTextBox().getNumero().setText(telefono.getNumeroLocal());
				cuentaEditor.getTelFaxTextBox().getInterno().setText(telefono.getInterno());
			}
		}
		cuentaEditor.getObservaciones().setText(cuentaDto.getObservacionesTelMail());
	}
	
    public void cargarPanelEmails(CuentaDto cuentaDto) {
        for (EmailDto email : cuentaDto.getPersona().getEmails()) {
		    TipoEmailDto tipoEmail = email.getTipoEmail();
		    if (tipoEmail.getId()==TipoEmailEnum.PERSONAL.getTipo()) {
		    	cuentaEditor.getEmailPersonal().setText(email.getEmail());
		    }
		    if (tipoEmail.getId()==TipoEmailEnum.LABORAL.getTipo()) {
		    	cuentaEditor.getEmailLaboral().setText(email.getEmail());
		    }
		}    	
    }
    
    public void cargarPanelFormaPago(CuentaDto cuentaDto) {
        String id_formaPago = "";
    	//if (cuentaDto.getDatosPago() instanceof DatosEfectivoDto) {
		if (cuentaDto.getDatosPago().isEfectivo()) {
			datosPago = (DatosEfectivoDto) cuentaDto.getDatosPago();
			cuentaEditor.getFormaPago().setSelectedItem(((DatosEfectivoDto)datosPago).getFormaPagoAsociada());
			id_formaPago = ((DatosEfectivoDto)datosPago).getFormaPagoAsociada().getItemValue();
		}
		//else if (cuentaDto.getDatosPago() instanceof DatosDebitoCuentaBancariaDto) {
		else if (cuentaDto.getDatosPago().isDebitoCuentaBancaria()) {			
			datosPago = (DatosDebitoCuentaBancariaDto) cuentaDto.getDatosPago();
			cuentaEditor.getFormaPago().setSelectedItem(((DatosDebitoCuentaBancariaDto)datosPago).getFormaPagoAsociada());
			cuentaEditor.getTipoCuentaBancaria().setSelectedItem(((DatosDebitoCuentaBancariaDto)datosPago).getTipoCuentaBancaria());
			cuentaEditor.getCbu().setText(((DatosDebitoCuentaBancariaDto)datosPago).getCbu());
			id_formaPago = ((DatosDebitoCuentaBancariaDto)datosPago).getFormaPagoAsociada().getItemValue();
		}
		//else if (cuentaDto.getDatosPago() instanceof DatosDebitoTarjetaCreditoDto) {
		else if (cuentaDto.getDatosPago().isDebitoTarjetaCredito()) {			
			datosPago = (DatosDebitoTarjetaCreditoDto) cuentaDto.getDatosPago();
			cuentaEditor.getFormaPago().setSelectedItem(((DatosDebitoTarjetaCreditoDto)datosPago).getFormaPagoAsociada());
			cuentaEditor.getMesVto().setSelectedIndex(((DatosDebitoTarjetaCreditoDto)datosPago).getMesVencimientoTarjeta());
			cuentaEditor.getAnioVto().setSelectedIndex(((DatosDebitoTarjetaCreditoDto)datosPago).getAnoVencimientoTarjeta());
			cuentaEditor.getTipoTarjeta().setSelectedItem(((DatosDebitoTarjetaCreditoDto)datosPago).getTipoTarjeta());
			cuentaEditor.getNumeroTarjeta().setText(((DatosDebitoTarjetaCreditoDto)datosPago).getNumero());
			id_formaPago = ((DatosDebitoTarjetaCreditoDto)datosPago).getFormaPagoAsociada().getItemValue();
		}    	
		setVisiblePanelFormaPagoYActualizarCamposObligatorios(id_formaPago);
    }
    
    public void setVisiblePanelFormaPagoYActualizarCamposObligatorios(String id_formaPago) {
		cuentaEditor.getCamposObligatoriosFormaPago().clear();
    	if (id_formaPago.equals(TipoFormaPagoEnum.CUENTA_BANCARIA.getTipo())) {
    		cuentaBancariaTable.setWidget(0, 1, cuentaEditor.getFormaPago());
    		efectivoTable.setVisible(false);
    		cuentaBancariaTable.setVisible(true);
    		tarjetaTable.setVisible(false);    	
    		cuentaEditor.getCamposObligatoriosFormaPago().add(cuentaEditor.getCbu());
    	} else if (id_formaPago.equals(TipoFormaPagoEnum.TARJETA_CREDITO.getTipo())) {
    		tarjetaTable.setWidget(0, 1, cuentaEditor.getFormaPago());
    		efectivoTable.setVisible(false);
    		cuentaBancariaTable.setVisible(false);
    		tarjetaTable.setVisible(true);
    		cuentaEditor.getCamposObligatoriosFormaPago().add(cuentaEditor.getNumeroTarjeta());
    		cuentaEditor.getCamposObligatoriosFormaPago().add(cuentaEditor.getAnioVto());
    	} else {  //	if (id_formaPago.equals(TipoFormaPagoEnum.EFECTIVO.getTipo())) {
        	efectivoTable.setWidget(0, 1, cuentaEditor.getFormaPago());
    		efectivoTable.setVisible(true);
    		cuentaBancariaTable.setVisible(false);
    		tarjetaTable.setVisible(false);
    	}
    }

    public void cargarPanelVendedor(CuentaDto cuentaDto) {
		cuentaEditor.getVendedorNombre().setText(cuentaDto.getVendedor()!=null?cuentaDto.getVendedor().getNombre():"");
		cuentaEditor.getVendedorTelefono().setText(cuentaDto.getVendedor()!=null?cuentaDto.getVendedor().getTelefono():"");
		cuentaEditor.getTipoCanalVentas().setSelectedItem(cuentaDto.getTipoCanalVentas());
    }

    public void cargarPanelUsuario(CuentaDto cuentaDto) {
		cuentaEditor.getUsuario().setText(cuentaDto.getNombreUsuarioCreacion());
		cuentaEditor.getFechaCreacion().setText(DateTimeFormat.getMediumDateFormat().format(cuentaDto.getFechaCreacion()));
    }
    
	public void deshabilitarCamposAlAgregarCuenta() {
		List <Widget>campos = new ArrayList<Widget>();
		campos.add(cuentaEditor.getRazonSocial());
		campos.add(cuentaEditor.getTipoDocumento());
		campos.add(cuentaEditor.getNumeroDocumento());
		campos.add(cuentaEditor.getCategoria());
		campos.add(cuentaEditor.getClaseCliente());
		campos.add(cuentaEditor.getCicloFacturacion());
	    FormUtils.disableFields(campos);
	}
    
	public boolean formularioDatosDirty() {
		boolean retorno = false;
		CuentaEdicionTabPanel  cuentaTab = CuentaEdicionTabPanel.getInstance();
		if ( (FormUtils.fieldDirty(cuentaEditor.getRazonSocial(), cuentaTab.getCuenta2editDto().getPersona().getRazonSocial()))
		   ||(FormUtils.fieldDirty(cuentaEditor.getSexo(), cuentaTab.getCuenta2editDto().getPersona().getSexo().getItemValue()))
		   ||(FormUtils.fieldDirty(cuentaEditor.getFechaNacimiento(), cuentaTab.getCuenta2editDto().getPersona().getFechaNacimiento()!=null?DateTimeFormat.getMediumDateFormat().format(cuentaTab.getCuenta2editDto().getPersona().getFechaNacimiento()):""))
   		   ||(FormUtils.fieldDirty(cuentaEditor.getContribuyente(), cuentaTab.getCuenta2editDto().getTipoContribuyente().getItemValue()))
		   ||(FormUtils.fieldDirty(cuentaEditor.getProveedorAnterior(), cuentaTab.getCuenta2editDto().getProveedorInicial().getItemValue())) 
		   ||(FormUtils.fieldDirty(cuentaEditor.getRubro(), cuentaTab.getCuenta2editDto().getRubro().getItemValue()))
		   ||(FormUtils.fieldDirty(cuentaEditor.getClaseCliente(), cuentaTab.getCuenta2editDto().getClaseCuenta().getItemValue()))
		   ||(FormUtils.fieldDirty(cuentaEditor.getCategoria(), cuentaTab.getCuenta2editDto().getCategoriaCuenta().getItemText()))
   		   ||(FormUtils.fieldDirty(cuentaEditor.getCicloFacturacion(), cuentaTab.getCuenta2editDto().getCicloFacturacion().getCodigoFNCL()))
		   ||(FormUtils.fieldDirty(cuentaEditor.getObservaciones(), cuentaTab.getCuenta2editDto().getObservacionesTelMail()))
		) return true;

		//telefonos
        //si la cuenta no tiene telefonos ingresados, simplemente se fija que el campo sea distinto de ""
		if (cuentaTab.getCuenta2editDto().getPersona().getTelefonos().size()<1) {
			if ((!cuentaEditor.getTelPrincipalTextBox().getArea().getText().trim().equals("")) 
				||(!cuentaEditor.getTelPrincipalTextBox().getNumero().getText().trim().equals(""))
				||(!cuentaEditor.getTelPrincipalTextBox().getInterno().getText().trim().equals("")) 
			    ||(!cuentaEditor.getTelAdicionalTextBox().getArea().getText().trim().equals(""))
				||(!cuentaEditor.getTelAdicionalTextBox().getNumero().getText().trim().equals(""))
				||(!cuentaEditor.getTelAdicionalTextBox().getInterno().getText().trim().equals(""))
			    ||(!cuentaEditor.getTelCelularTextBox().getArea().getText().trim().equals(""))
				||(!cuentaEditor.getTelCelularTextBox().getNumero().getText().trim().equals(""))
			    ||(!cuentaEditor.getTelFaxTextBox().getArea().getText().trim().equals(""))
				||(!cuentaEditor.getTelFaxTextBox().getNumero().getText().trim().equals(""))
				||(!cuentaEditor.getTelFaxTextBox().getInterno().getText().trim().equals(""))
			) retorno = true;
		} else {
			for (int i=0;i<cuentaTab.getCuenta2editDto().getPersona().getTelefonos().size();i++) {
				TelefonoDto telefono = (TelefonoDto)cuentaTab.getCuenta2editDto().getPersona().getTelefonos().get(i);
				TipoTelefonoDto tipoTelefono = telefono.getTipoTelefono();

				if ( tipoTelefono.getId()==TipoTelefonoEnum.PRINCIPAL.getTipo()) {
					if ( (FormUtils.fieldDirty(cuentaEditor.getTelPrincipalTextBox().getArea(),telefono.getArea())) 
						||(FormUtils.fieldDirty(cuentaEditor.getTelPrincipalTextBox().getNumero(),telefono.getNumeroLocal()))
						||(FormUtils.fieldDirty(cuentaEditor.getTelPrincipalTextBox().getInterno(),telefono.getInterno())) 
					) retorno = true;   
				}
				if ( tipoTelefono.getId()==TipoTelefonoEnum.PARTICULAR.getTipo() ||
						tipoTelefono.getId()==TipoTelefonoEnum.ADICIONAL.getTipo()) {
					if ( (FormUtils.fieldDirty(cuentaEditor.getTelAdicionalTextBox().getArea(),telefono.getArea()))
						||(FormUtils.fieldDirty(cuentaEditor.getTelAdicionalTextBox().getNumero(),telefono.getNumeroLocal()))
						||(FormUtils.fieldDirty(cuentaEditor.getTelAdicionalTextBox().getInterno(),telefono.getInterno()))
					) retorno = true;
				}
				if ( tipoTelefono.getId()==TipoTelefonoEnum.CELULAR.getTipo()) {
					if ( (FormUtils.fieldDirty(cuentaEditor.getTelCelularTextBox().getArea(),telefono.getArea()))
						||(FormUtils.fieldDirty(cuentaEditor.getTelCelularTextBox().getNumero(),telefono.getNumeroLocal()))
					) retorno = true;
				}
				if ( tipoTelefono.getId()==TipoTelefonoEnum.FAX.getTipo()) {
					if ( (FormUtils.fieldDirty(cuentaEditor.getTelFaxTextBox().getArea(),telefono.getArea()))
						||(FormUtils.fieldDirty(cuentaEditor.getTelFaxTextBox().getNumero(),telefono.getNumeroLocal()))
						||(FormUtils.fieldDirty(cuentaEditor.getTelFaxTextBox().getInterno(),telefono.getInterno()))
					) retorno = true;
				}
			}
		}
		return retorno;
	}
	
	public boolean datosObligatoriosCompletos() {
		boolean datosOK = true;
		camposObligatorios.clear();
		camposObligatorios = cuentaEditor.getCamposObligatorios();
		camposObligatorios.addAll(cuentaEditor.getCamposObligatoriosFormaPago());
		for(Widget campo : camposObligatorios) {
			if(FormUtils.fieldEmpty(campo)) {
				return false;	
			}
		}
		return datosOK;
	}
	
	public CuentaUIData getCuentaEditor() {
		return cuentaEditor;
	}

	public void setCuentaEditor(CuentaUIData cuentaEditor) {
		this.cuentaEditor = cuentaEditor;
	}
	
}
