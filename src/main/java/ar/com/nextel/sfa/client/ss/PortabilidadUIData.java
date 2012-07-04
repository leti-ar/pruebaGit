package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.ModalidadCobroDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.ProveedorDto;
import ar.com.nextel.sfa.client.dto.SolicitudPortabilidadDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.dto.TipoPersonaDto;
import ar.com.nextel.sfa.client.dto.TipoTelefoniaDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.initializer.PortabilidadInitializer;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.FechaDatePicker;
import ar.com.nextel.sfa.client.widget.ModalMessageDialog;
import ar.com.nextel.sfa.client.widget.TelefonoTextBox;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author diazho
 *
 */
public class PortabilidadUIData extends Composite {

	interface PortabilidadUiBinder extends UiBinder<Widget , PortabilidadUIData> {}
    private static PortabilidadUiBinder uiBinder = GWT.create(PortabilidadUiBinder.class);

	@UiField CheckBox chkRecibeSMS;
	@UiField CheckBox chkNoPoseeTel;
	@UiField CheckBox chkNoPoseeEmail;
	
	@UiField(provided = true) HTML lnkCopiarCuenta = IconFactory.copiar();
	
	@UiField TelefonoTextBox txtTelefono;
	@UiField(provided = true) TelefonoTextBox txtTelefonoPortar = new TelefonoTextBox(false);
	
	@UiField Label lblNroSS;
	@UiField Label lblNroUltimaFacura;
	@UiField Label lblTipoDocumento;	
	@UiField Label lblNroDocumento;
	@UiField Label lblRazonSocial;
	@UiField Label lblNombre;
	@UiField Label lblApellido;
	//LF - CR SFA - Carga Datos Apoderado
	@UiField Label lblTipoDocApod;
	@UiField Label lblNroDocApod;
	@UiField Label lblNombreApod;
	@UiField Label lblApellidoApod;
	
	@UiField Label lblTelefono;
	@UiField Label lblEmail;
	@UiField Label lblProveedorAnterior;
	@UiField Label lblTipoTelefonia;
	@UiField Label lblModalidadCobro;
	@UiField Label lblTelefonoPortar;
//	LF - CR SFA - Carga Datos Apoderado
	@UiField Label lblFechaUltimaFactura;	
	@UiField Label lblTipoPersona;
	
	
	@UiField ListBox lstTipoDocumento;
	@UiField ListBox lstTipoTelefonia;
	@UiField ListBox lstModalidadCobro;
	@UiField ListBox lstProveedorAnterior;
//	LF - CR SFA - Carga Datos Apoderado
	@UiField ListBox lstTipoDocApod;
	@UiField ListBox lstTipoPersona;
	
	@UiField RegexTextBox txtEmail;
	@UiField RegexTextBox txtNroSS;
	@UiField RegexTextBox txtNombre;
	@UiField RegexTextBox txtApellido;
	@UiField RegexTextBox txtRazonSocial;
	@UiField RegexTextBox txtNroDocumento;
	@UiField RegexTextBox txtNroUltimaFacura;
	
//	LF - CR SFA - Carga Datos Apoderado
	@UiField RegexTextBox txtNroDocApod;
	@UiField RegexTextBox txtNombreApod;
	@UiField RegexTextBox txtApellidoApod;
	@UiField (provided = true) FechaDatePicker fechaUltFactura = new FechaDatePicker();
	
	private Command cmndAceptar;
	private CheckBox chkPortabilidad;
	private Button btnReserva;
	private TextBox txtReserva;
	
	private static final String V1 = "\\{1\\}";
	private static final String OBLIGATORIO = "req";
	private static final String WARNING = "Advertencia";

	private PersonaDto persona;
	private SolicitudPortabilidadDto solicitudPortabilidad;
	private List<TipoDocumentoDto> listaTipoDocumento = new ArrayList<TipoDocumentoDto>();
	private SolicitudServicioDto solicitudServicio = null;
	private TipoPersonaDto tipoPersonaCuenta = null;
	
	/**
	 * @param focusListener 
	 * 
	 */
	public PortabilidadUIData() {
		initWidget(uiBinder.createAndBindUi(this));
		
		lblNroSS.addStyleName(OBLIGATORIO);
		lblTipoDocumento.addStyleName(OBLIGATORIO);	
		lblNroDocumento.addStyleName(OBLIGATORIO);
		lblTelefonoPortar.addStyleName(OBLIGATORIO);
		lblTelefono.addStyleName(OBLIGATORIO);
		lblTelefono.addStyleName(OBLIGATORIO);
		lblProveedorAnterior.addStyleName(OBLIGATORIO);
		lblTipoTelefonia.addStyleName(OBLIGATORIO);
		lblModalidadCobro.addStyleName(OBLIGATORIO);
//		lblNombre.addStyleName(OBLIGATORIO);
//		lblApellido.addStyleName(OBLIGATORIO);
//		lblRazonSocial.addStyleName(OBLIGATORIO);
		lblTipoPersona.addStyleName(OBLIGATORIO);	
		
		lblApellido.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblNroUltimaFacura.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblNroDocumento.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		//LF - CR SFA - Carga Datos Apoderado
		lblNroDocApod.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblApellidoApod.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblFechaUltimaFactura.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		
		txtRazonSocial.setEnabled(false);

		chkRecibeSMS.addStyleName("portabilidadCheck");
		chkNoPoseeTel.addStyleName("portabilidadCheck");
		chkNoPoseeEmail.addStyleName("portabilidadCheck");
		lnkCopiarCuenta.addStyleName("floatRight");
		
		lstProveedorAnterior.setSelectedIndex(-1);
		lstTipoTelefonia.setSelectedIndex(-1);
		
		// LF - #3287
		lstTipoDocApod.setSelectedIndex(-1);
		//tTelefonoPortar.getArea().setMaxLength(2);
		txtNroUltimaFacura.setMaxLength(50);
		txtNroSS.setEnabled(false);
		
		fechaUltFactura.getTextBox().setMaxLength(10);
		
		comprobarTipoTelefonia();
		validarTipoDocumento();
//		validarTipoDocApoderado();
		
		cmndAceptar = new Command() {
			public void execute() {
				txtTelefonoPortar.getArea().setFocus(true);
				txtTelefonoPortar.getArea().selectAll();
				ModalMessageDialog.getInstance().hide();
			}
		};
		
		txtTelefonoPortar.getArea().addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if(txtTelefonoPortar.getArea().getText().length() > 0){
					SolicitudRpcService.Util.getInstance().getExisteEnAreaCobertura(Integer.valueOf(txtTelefonoPortar.getArea().getText()), 
							new DefaultWaitCallback<Boolean>() {
						@Override
						public void success(Boolean result) {
							if(!result){ 
								ModalMessageDialog.getInstance().showAceptar(WARNING, 
										"El prefijo no está dentro del area de cobertura, no se podra efectuar la portabilidad", cmndAceptar);
							}
						}
					});
				}
			}
		});
		
	}
	
	/**
	 * 
	 * @param evt
	 */
	@UiHandler(value={"lnkCopiarCuenta","chkNoPoseeTel","chkNoPoseeEmail"})
	void onCLick(ClickEvent evt){
		if(evt.getSource() == lnkCopiarCuenta){
			// #LF - #3278
			if(tipoPersonaCuenta == null) {
				SolicitudRpcService.Util.getInstance().obtenerTipoPersonaCuenta(this.getSolicitudServicio(), new DefaultWaitCallback<TipoPersonaDto>() {
					@Override
					public void success(TipoPersonaDto result) {
						setTipoPersonaCuenta(result);
						replicarTipoPersonaCuenta(result);
					}
				});
			} else {
				replicarTipoPersonaCuenta(tipoPersonaCuenta);
			}
		}
		else if(evt.getSource() == chkNoPoseeTel){
			if(chkNoPoseeTel.getValue()){
				txtTelefono.getArea().setText("");
				txtTelefono.getNumero().setText("");
				txtTelefono.getInterno().setText("");

				txtTelefono.getArea().setEnabled(false);
				txtTelefono.getNumero().setEnabled(false);
				txtTelefono.getInterno().setEnabled(false);
			}else{
				txtTelefono.getArea().setEnabled(true);
				txtTelefono.getNumero().setEnabled(true);
				txtTelefono.getInterno().setEnabled(true);
			}
		}else if(evt.getSource() == chkNoPoseeEmail){
			if(chkNoPoseeEmail.getValue()){
				txtEmail.setText("");
				txtEmail.setEnabled(false);
			}else txtEmail.setEnabled(true);
		}
	}
	
	private void replicarTipoPersonaCuenta(TipoPersonaDto tipoPersonaCuenta) {
		lstTipoPersona.setSelectedItem(tipoPersonaCuenta);
		if(tipoPersonaCuenta.getDescripcion().equals("FISICA")) {
			resetearTipoDocCuit(true);
			txtNombre.setText(persona.getNombre());
			txtApellido.setText(persona.getApellido());
			txtNroDocumento.setText(persona.getDocumento().getNumero());
			lstTipoDocumento.selectByText(persona.getDocumento().getTipoDocumento().getDescripcion());
		} else {
			resetearTipoDocCuit(false);
			txtRazonSocial.setText(persona.getRazonSocial());
			txtNroDocumento.setText(persona.getDocumento().getNumero());
			lstTipoDocumento.selectByText(persona.getDocumento().getTipoDocumento().getDescripcion());
		}
	}
	
	/**
	 * Maneja los eventos del tipo ChangeEvent de los componentes construidos desde ui.xml referenciados por UiHandler
	 * @param evt
	 */
	@UiHandler(value={"lstTipoDocumento","lstTipoTelefonia","lstTipoDocApod", "lstTipoPersona"})
	void onChange(ChangeEvent evt){
		if(evt.getSource() == lstTipoDocumento) validarTipoDocumento();
		else if(evt.getSource() == lstTipoTelefonia) comprobarTipoTelefonia();
		//LF - CR SFA - Carga Datos Apoderado
		else if(evt.getSource() == lstTipoDocApod) {
			validarTipoDocApoderado();
			txtNroDocApod.setText("");
		} else if(evt.getSource() == lstTipoPersona) {
			if(isPersonaFisica()) {
				resetearTipoDocCuit(true);
			} else {
				resetearTipoDocCuit(false);
			}
		}
	}

	public void resetearTipoDocCuit(boolean esFisica) {
		if(esFisica) {
			lstTipoDocumento.clear();
			lstTipoDocumento.addAllItems(this.getListaTipoDocumento());
		} else {
			lstTipoDocumento.clear();
			TipoDocumentoDto cuit = obtenerTipoDocumentoCuit();
			lstTipoDocumento.addItem(cuit);
		}
		txtNroDocumento.setText(null);
		habilitarCamposTipoPersona(esFisica);
	}
	
	private TipoDocumentoDto obtenerTipoDocumentoCuit(){
		TipoDocumentoDto cuit = new TipoDocumentoDto();
		for (Iterator<TipoDocumentoDto> iterator = this.getListaTipoDocumento().iterator(); iterator.hasNext();) {
			TipoDocumentoDto tipoDocumento = (TipoDocumentoDto) iterator.next();
			if(tipoDocumento.getDescripcion().equals("CUIT")) {
				cuit = tipoDocumento;
			}
		}
		return cuit;
	}
	
	/**
	 * 
	 */
	private void validarTipoDocumento(){
		if(lstTipoDocumento.getSelectedItemText().equals("CUIL") || lstTipoDocumento.getSelectedItemText().equals("CUIT"))
			txtNroDocumento.setPattern(RegularExpressionConstants.cuilCuit);
		else if(lstTipoDocumento.getSelectedItemText().equals("DNI")) 
			txtNroDocumento.setPattern(RegularExpressionConstants.dni);
		else 
			txtNroDocumento.setPattern(RegularExpressionConstants.documentoOtros);
	}
	
	private void validarTipoDocApoderado(){
		if(lstTipoDocApod.getSelectedItemText() != null) {
			if(lstTipoDocApod.getSelectedItemText().equals("CUIL") || lstTipoDocApod.getSelectedItemText().equals("CUIT"))
				txtNroDocApod.setPattern(RegularExpressionConstants.cuilCuit);
			else if(lstTipoDocApod.getSelectedItemText().equals("DNI")) 
				txtNroDocApod.setPattern(RegularExpressionConstants.dni);
			else 
				txtNroDocApod.setPattern(RegularExpressionConstants.documentoOtros);
		}
	}
	
	
	/**
	 * 
	 * @param unChkPortabilidad
	 */
	public void setCHKportabilidad(CheckBox unChkPortabilidad){
		chkPortabilidad = unChkPortabilidad;
	}
	
	/**
	 * 
	 * @param unBtnReserva
	 */
	public void setReserva(Button unBtnReserva,TextBox unTxtReserva){
		btnReserva = unBtnReserva;
		txtReserva = unTxtReserva;
	}
	
	/**
	 * 
	 */
	private void comprobarTipoTelefonia(){
		if(lstTipoTelefonia.getSelectedItemText().equals("POSTPAGO")){
			lblNroUltimaFacura.addStyleName(OBLIGATORIO);
			chkRecibeSMS.setValue(false);
			chkRecibeSMS.setEnabled(true);
			txtNroUltimaFacura.setEnabled(true);
			fechaUltFactura.bloquear(false);
			lblFechaUltimaFactura.addStyleName(OBLIGATORIO);
		}else{
			lblNroUltimaFacura.removeStyleName(OBLIGATORIO);
			chkRecibeSMS.setValue(true);
			chkRecibeSMS.setEnabled(false);
			txtNroUltimaFacura.setText("");
			txtNroUltimaFacura.setEnabled(false);
			fechaUltFactura.bloquear(true);
			lblFechaUltimaFactura.removeStyleName(OBLIGATORIO);
		}
	}
	
	/**
	 * 
	 */
	public void resetearPortabilidad(){
		chkRecibeSMS.setValue(false);
		chkRecibeSMS.setEnabled(true);

		txtTelefono.clean();
		txtTelefonoPortar.clean();

//		if(this.getTipoPersona().intValue() != 1) {
		lstTipoDocumento.setSelectedIndex(0);
//		lstTipoDocApod.setSelectedIndex(0);
//		}
		lstModalidadCobro.setSelectedIndex(0);
		lstTipoTelefonia.setSelectedIndex(-1);
		lstProveedorAnterior.setSelectedIndex(-1);

		lstTipoPersona.setSelectedIndex(0);
		
		txtEmail.setText(null);
		txtNroSS.setText(null);;
		txtNombre.setText(null);;
		txtApellido.setText(null);;
		txtRazonSocial.setText(null);;
		txtNroDocumento.setText(null);;
		txtNroUltimaFacura.setText(null);;

		comprobarTipoTelefonia();
		validarTipoDocumento();
		validarTipoDocApoderado();

		txtTelefono.getArea().setEnabled(false);
		txtTelefono.getNumero().setEnabled(false);
		txtTelefono.getInterno().setEnabled(false);
		txtEmail.setEnabled(false);
		
		chkNoPoseeTel.setValue(true);
		chkNoPoseeEmail.setValue(true);
		
		//LF - CR SFA - Carga Datos Apoderado
		fechaUltFactura.getTextBox().setText(null);
	    lstTipoDocApod.setSelectedIndex(-1);
	    txtNroDocApod.setText(null);
	    txtNombreApod.setText(null);
	    txtApellidoApod.setText(null);

		this.setVisible(false);
	}

	/**
	 * 
	 * @param initilizer
	 */
	public void setInitializer(PortabilidadInitializer initiliazer){
		lstTipoDocumento.addAllItems(initiliazer.getLstTipoDocumento());
		lstProveedorAnterior.addAllItems(initiliazer.getLstProveedorAnterior());
		lstTipoTelefonia.addAllItems(initiliazer.getLstTipoTelefonia()); 
		lstModalidadCobro.addAllItems(initiliazer.getLstModalidadCobro()); 
		//LF - CR SFA - Carga Datos Apoderado
		lstTipoDocApod.addAllItems(initiliazer.getLstTipoDocumento());
		lstTipoPersona.addAllItems(initiliazer.getLstTipoPersona());

		lnkCopiarCuenta.setVisible(true);
		//LF
		listaTipoDocumento.addAll(initiliazer.getLstTipoDocumento());
	}
	
	public void setPersona(PersonaDto unaPersona){
		persona = unaPersona;
	}
	
	/**
	 * 
	 */
	public void removePersona(){
		if(persona != null) persona = null;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<String> ejecutarValidacion(){
		GwtValidator validador = new GwtValidator();
		
		if(lstProveedorAnterior.getSelectedIndex() < 0)
			validador.addTarget(lstProveedorAnterior).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Proveedor anterior"));

		if(lstTipoTelefonia.getSelectedIndex() < 0)
			validador.addTarget(lstTipoTelefonia).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Tipo Telefonia"));

		if(!chkNoPoseeTel.getValue())
			validador.addTarget(txtTelefono.getNumero()).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Telefono"));
		
		if(lstTipoPersona.getSelectedIndex() < 0)
			validador.addTarget(lstTipoPersona).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Tipo Persona"));
		
		validador.addTarget(txtTelefonoPortar.getNumero()).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Telefono a Portar"));
		
		if(lstTipoTelefonia.getSelectedItemText().equals("POSTPAGO")){
			validador.addTarget(txtNroUltimaFacura).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Nro. Ultima Factura"));
			validador.addTarget(fechaUltFactura.getTextBox()).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Fecha de Emisión"));
			if(!fechaUltFactura.getTextBox().getText().equals("")) {
				if(!fechaUltFactura.validarFecha()) {
					validador.addError("El campo Portabilidad: Fecha de Emisión debe tener el siguiente formato dd/mm/yyyy");
				} else {
					Date fechaActual = new Date();
					if(fechaUltFactura.getFecha().compareTo(fechaActual) == 1) {
						validador.addError("El campo Portabilidad: Fecha de Emisión no debe ser mayor a la fecha actual");
					}
				}
			}

		}
		
		if(isPersonaFisica()) {
			validador.addTarget(txtNombre).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Nombre"));
			validador.addTarget(txtApellido).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Apellido"));
		} else {
			validador.addTarget(txtNroDocumento).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Nro. de Documento"));
			validador.addTarget(txtRazonSocial).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Razon Social"));
//			validador.addTarget(txtNombre).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Nombre"));
//			validador.addTarget(txtApellido).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Apellido"));
			validador.addTarget(txtNombreApod).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Nombre Apoderado"));
			validador.addTarget(txtApellidoApod).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Apellido Apoderado"));
			validador.addTarget(lstTipoDocApod).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Tipo Documento Apoderado"));
			validador.addTarget(txtNroDocApod).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Número Documento Apoderado"));
		}
		String numAportar = txtTelefonoPortar.getArea().getText() + txtTelefonoPortar.getNumero().getText();
		if(!numAportar.equals("")) {
			if(numAportar.length() != 10) validador.addError("La cantidad de digitos de Portabilidad: Nro. a Portar (Codigo Area + Telefono) debe ser igual a 10");
		}
		
		validador.fillResult();
		return validador.getErrors();
	}
	
	/**
	 * 
	 * @param idLinea
	 */
	public void loadSolicitudPortabilidad(SolicitudPortabilidadDto solPortDto,boolean comoNuevo){
		solicitudPortabilidad = solPortDto;
	
		txtEmail.setText(solicitudPortabilidad.getEmail());
		txtNroSS.setText(solicitudPortabilidad.getNroSS());
		txtNombre.setText(solicitudPortabilidad.getNombre());
		txtApellido.setText(solicitudPortabilidad.getApellido());
		txtRazonSocial.setText(solicitudPortabilidad.getRazonSocial());
		txtNroDocumento.setText(solicitudPortabilidad.getNumeroDocumento());
		txtNroUltimaFacura.setText(solicitudPortabilidad.getNroUltimaFacura());
		txtTelefonoPortar.getArea().setText(solicitudPortabilidad.getAreaTelefono());
		txtTelefonoPortar.getNumero().setText(solicitudPortabilidad.getTelefonoPortar());

		lstTipoDocumento.setSelectedItem(solicitudPortabilidad.getTipoDocumento());
		lstTipoTelefonia.setSelectedItem(solicitudPortabilidad.getTipoTelefonia());
		lstModalidadCobro.setSelectedItem(solicitudPortabilidad.getModalidadCobro());
		lstProveedorAnterior.setSelectedItem(solicitudPortabilidad.getProveedorAnterior());
		//LF - CR SFA - Carga Datos Apoderado
		lstTipoDocApod.setSelectedItem(solicitudPortabilidad.getTipoDocumentoRep());
		txtNroDocApod.setText(solicitudPortabilidad.getNumeroDocRep());
		txtNombreApod.setText(solicitudPortabilidad.getNombreRep());
		txtApellidoApod.setText(solicitudPortabilidad.getApellidoRep());
		
		lstTipoPersona.setSelectedItem(solicitudPortabilidad.getTipoPersona());
		
		setVisible(true);
		comprobarTipoTelefonia();
		validarTipoDocumento();
		validarTipoDocApoderado();

		if(solicitudPortabilidad.getTelefono() != null){
			String[] telefono = solicitudPortabilidad.getTelefono().split("-");
			if(telefono.length == 1) txtTelefono.getNumero().setText(telefono[0]);
			else if(telefono.length == 3){
				txtTelefono.getArea().setText(telefono[0]);
				txtTelefono.getNumero().setText(telefono[1]);
				txtTelefono.getInterno().setText(telefono[2]);
			}else if(telefono.length == 2){
				if(telefono[0].length() < telefono[1].length()){
					txtTelefono.getArea().setText(telefono[0]);
					txtTelefono.getNumero().setText(telefono[1]);
				}else{
					txtTelefono.getNumero().setText(telefono[0]);
					txtTelefono.getInterno().setText(telefono[1]);
				}
			}
		}else txtTelefono.getNumero().setText(solicitudPortabilidad.getTelefono());

		if(solicitudPortabilidad.getTelefono() == null){
			chkNoPoseeTel.setValue(true);
			txtTelefono.getArea().setText("");
			txtTelefono.getNumero().setText("");
			txtTelefono.getInterno().setText("");
			txtTelefono.getArea().setEnabled(false);
			txtTelefono.getNumero().setEnabled(false);
			txtTelefono.getInterno().setEnabled(false);
		}else{
			if(solicitudPortabilidad.getTelefono().length() > 0){
				chkNoPoseeTel.setValue(false);
				txtTelefono.getArea().setEnabled(true);
				txtTelefono.getNumero().setEnabled(true);
				txtTelefono.getInterno().setEnabled(true);
			}else{
				chkNoPoseeTel.setValue(true);
				txtTelefono.getArea().setText("");
				txtTelefono.getNumero().setText("");
				txtTelefono.getInterno().setText("");
				txtTelefono.getArea().setEnabled(false);
				txtTelefono.getNumero().setEnabled(false);
				txtTelefono.getInterno().setEnabled(false);
			}
		}

		if(solicitudPortabilidad.getEmail() == null){
			chkNoPoseeEmail.setValue(true);
			txtEmail.setText("");
			txtEmail.setEnabled(false);
		}else{
			if(solicitudPortabilidad.getEmail().length() > 0){
				chkNoPoseeEmail.setValue(false);
				txtEmail.setEnabled(true);
			}else{
				chkNoPoseeEmail.setValue(true);
				txtEmail.setText("");
				txtEmail.setEnabled(false);
			}
		}
			
		if(comoNuevo){
			chkNoPoseeTel.setValue(false);
			txtTelefono.getArea().setEnabled(true);
			txtTelefono.getNumero().setEnabled(true);
			txtTelefono.getInterno().setEnabled(true);

			chkNoPoseeEmail.setValue(false);
			txtEmail.setEnabled(true);
			//LF
			resetearTipoDocCuit(true);
		} else {
			if(solicitudPortabilidad.getTipoPersona() == null ){
				resetearTipoDocCuit(true);
			} else if(!solicitudPortabilidad.getTipoPersona().getDescripcion().equals("FISICA")) {
				String numDoc = solicitudPortabilidad.getNumeroDocumento();
				resetearTipoDocCuit(false);
				txtNroDocumento.setText(numDoc);
			}
		}
		
		if(solicitudPortabilidad.getTipoTelefonia() == null) {
			lstTipoTelefonia.setSelectedIndex(-1);
			lstTipoTelefonia.setSelectedItem(null);
		}
		
		//LF - #3320 
		if(solicitudPortabilidad.getTipoDocumentoRep() == null) {
			lstTipoDocApod.setSelectedIndex(-1);
			lstTipoDocApod.setSelectedItem(null);
		}

		if(solicitudPortabilidad.getNroSS() != null){
			if(lstTipoTelefonia.getSelectedItemText().equals("POSTPAGO")) chkRecibeSMS.setValue(solicitudPortabilidad.isRecibeSMS());
		}

		if(solicitudPortabilidad.getProveedorAnterior() == null) {
			lstProveedorAnterior.setSelectedIndex(-1);
			lstProveedorAnterior.setSelectedItem(null);
		}

		chkPortabilidad.setValue(true);
		txtReserva.setText("");
		txtReserva.setEnabled(false);
		btnReserva.setEnabled(false);
		
		if(lstTipoTelefonia.getSelectedItemText().equals("POSTPAGO")) {
			fechaUltFactura.setFecha(solicitudPortabilidad.getFechaUltFactura());
		}
	}
	
    public boolean isEmpty(String s) {
    	if(s == null) return true;
    	if(s.length() == 0) return true;
        return false;
    }

	/* GETTERS & SETTERS ================================================================== */
	
	public SolicitudPortabilidadDto getSolicitudPortabilidad(LineaSolicitudServicioDto lineaSolicitudServicio) {
		if(chkPortabilidad.getValue()){
			if(lineaSolicitudServicio.getId() != null) solicitudPortabilidad.setLineaSolicitudServicio(lineaSolicitudServicio.getId());

			solicitudPortabilidad.setRecibeSMS(chkRecibeSMS.getValue());
			solicitudPortabilidad.setEmail(txtEmail.getText());
			solicitudPortabilidad.setNroSS(txtNroSS.getText());
			solicitudPortabilidad.setNombre(txtNombre.getText());
			solicitudPortabilidad.setApellido(txtApellido.getText());
			solicitudPortabilidad.setRazonSocial(txtRazonSocial.getText());
			solicitudPortabilidad.setNumeroDocumento(txtNroDocumento.getText());
			solicitudPortabilidad.setAreaTelefono(txtTelefonoPortar.getArea().getText());
			solicitudPortabilidad.setTelefonoPortar(txtTelefonoPortar.getNumero().getText());
			solicitudPortabilidad.setNroUltimaFacura(txtNroUltimaFacura.getText());

			solicitudPortabilidad.setProveedorAnterior((ProveedorDto)lstProveedorAnterior.getSelectedItem());
//			if(getTipoPersona().intValue() == 1) {
//				solicitudPortabilidad.setTipoDocumento(null);
//			} else {
			solicitudPortabilidad.setTipoDocumento((TipoDocumentoDto)lstTipoDocumento.getSelectedItem());
//			}
			solicitudPortabilidad.setTipoTelefonia((TipoTelefoniaDto)lstTipoTelefonia.getSelectedItem());
			solicitudPortabilidad.setModalidadCobro((ModalidadCobroDto)lstModalidadCobro.getSelectedItem());

			String telefono = txtTelefono.getNumero().getText();
			if(!isEmpty(txtTelefono.getArea().getText())) telefono = txtTelefono.getArea().getText() + "-" + telefono; 
			if(!isEmpty(txtTelefono.getInterno().getText())) telefono =  telefono + "-" + txtTelefono.getInterno().getText();
			solicitudPortabilidad.setTelefono(telefono);
			
			//LF - CR SFA - Carga Datos Apoderado
			solicitudPortabilidad.setFechaUltFactura(fechaUltFactura.getFecha());
			solicitudPortabilidad.setTipoDocumentoRep((TipoDocumentoDto)lstTipoDocApod.getSelectedItem());
			solicitudPortabilidad.setNumeroDocRep(txtNroDocApod.getText());
			solicitudPortabilidad.setNombreRep(txtNombreApod.getText());
			solicitudPortabilidad.setApellidoRep(txtApellidoApod.getText());
			solicitudPortabilidad.setTipoPersona((TipoPersonaDto)lstTipoPersona.getSelectedItem());
		}
		resetearPortabilidad();
		return solicitudPortabilidad;
	}
	
	/**
	 * Metodo que habilita/deshabilita ciertos campos dependiendo si la persona es fisica o juridica.
	 */
	public void habilitarCamposTipoPersona(boolean esFisica) {
		if(esFisica) {
			lblNombre.addStyleName("req");
			lblApellido.addStyleName("req");
			lblRazonSocial.removeStyleName("req");
			lblTipoDocApod.removeStyleName("req");
			lblNroDocApod.removeStyleName("req");
			lblNombreApod.removeStyleName("req");
			lblApellidoApod.removeStyleName("req");
			txtRazonSocial.setEnabled(false);
			txtNombre.setEnabled(true);
			txtApellido.setEnabled(true);
			txtRazonSocial.setText(null);
		} else {
			lblRazonSocial.addStyleName("req");
			lblTipoDocApod.addStyleName("req");
			lblNroDocApod.addStyleName("req");
			lblNombreApod.addStyleName("req");
			lblApellidoApod.addStyleName("req");
			lblNombre.removeStyleName("req");
			lblApellido.removeStyleName("req");					
			txtRazonSocial.setEnabled(true);
			txtNombre.setEnabled(false);
			txtApellido.setEnabled(false);	
			txtNombre.setText(null);
			txtApellido.setText(null);	
		}
	}
	
	
	public boolean isPersonaFisica(){
		if(lstTipoPersona.getSelectedItemText().equals("FISICA"))
			return true;
		else return false;
	}

	public void setSolicitudPortabilidad(SolicitudPortabilidadDto solicitudPortabilidad) {
		this.solicitudPortabilidad = solicitudPortabilidad;
	}

	public TelefonoTextBox getTxtTelefono() {
		return txtTelefono;
	}

	public void setTxtTelefono(TelefonoTextBox txtTelefono) {
		this.txtTelefono = txtTelefono;
	}

	public TelefonoTextBox getTxtTelefonoPortar() {
		return txtTelefonoPortar;
	}

	public void setTxtTelefonoPortar(TelefonoTextBox txtTelefonoPortar) {
		this.txtTelefonoPortar = txtTelefonoPortar;
	}

	public CheckBox getChkRecibeSMS() {
		return chkRecibeSMS;
	}

	public void setChkRecibeSMS(CheckBox chkRecibeSMS) {
		this.chkRecibeSMS = chkRecibeSMS;
	}

	public RegexTextBox getTxtEmail() {
		return txtEmail;
	}

	public void setTxtEmail(RegexTextBox txtEmail) {
		this.txtEmail = txtEmail;
	}

	public RegexTextBox getTxtNroSS() {
		return txtNroSS;
	}

	public void setTxtNroSS(RegexTextBox txtNroSS) {
		this.txtNroSS = txtNroSS;
	}

	public RegexTextBox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(RegexTextBox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public RegexTextBox getTxtApellido() {
		return txtApellido;
	}

	public void setTxtApellido(RegexTextBox txtApellido) {
		this.txtApellido = txtApellido;
	}

	public RegexTextBox getTxtRazonSocial() {
		return txtRazonSocial;
	}

	public void setTxtRazonSocial(RegexTextBox txtRazonSocial) {
		this.txtRazonSocial = txtRazonSocial;
	}

	public RegexTextBox getTxtNroDocumento() {
		return txtNroDocumento;
	}

	public void setTxtNroDocumento(RegexTextBox txtNroDocumento) {
		this.txtNroDocumento = txtNroDocumento;
	}

	public RegexTextBox getTxtNroUltimaFacura() {
		return txtNroUltimaFacura;
	}

	public void setTxtNroUltimaFacura(RegexTextBox txtNroUltimaFacura) {
		this.txtNroUltimaFacura = txtNroUltimaFacura;
	}

	public ListBox getLstTipoDocumento() {
		return lstTipoDocumento;
	}

	public void setLstTipoDocumento(ListBox lstTipoDocumento) {
		this.lstTipoDocumento = lstTipoDocumento;
	}

	public ListBox getLstTipoTelefonia() {
		return lstTipoTelefonia;
	}

	public void setLstTipoTelefonia(ListBox lstTipoTelefonia) {
		this.lstTipoTelefonia = lstTipoTelefonia;
	}

	public ListBox getLstModalidadCobor() {
		return lstModalidadCobro;
	}

	public void setLstModalidadCobor(ListBox lstModalidadCobro) {
		this.lstModalidadCobro = lstModalidadCobro;
	}

	public ListBox getLstProveedorAnterior() {
		return lstProveedorAnterior;
	}

	public void setLstProveedorAnterior(ListBox lstProveedorAnterior) {
		this.lstProveedorAnterior = lstProveedorAnterior;
	}

	public CheckBox getChkPortabilidad() {
		return chkPortabilidad;
	}

	public void setChkPortabilidad(CheckBox chkPortabilidad) {
		this.chkPortabilidad = chkPortabilidad;
	}

	public ListBox getLstTipoDocApod() {
		return lstTipoDocApod;
	}

	public void setLstTipoDocApod(ListBox lstTipoDocApod) {
		this.lstTipoDocApod = lstTipoDocApod;
	}

	public RegexTextBox getTxtNroDocApod() {
		return txtNroDocApod;
	}

	public void setTxtNroDocApod(RegexTextBox txtNroDocApod) {
		this.txtNroDocApod = txtNroDocApod;
	}

	public RegexTextBox getTxtNombreApod() {
		return txtNombreApod;
	}

	public void setTxtNombreApod(RegexTextBox txtNombreApod) {
		this.txtNombreApod = txtNombreApod;
	}

	public RegexTextBox getTxtApellidoApod() {
		return txtApellidoApod;
	}

	public void setTxtApellidoApod(RegexTextBox txtApellidoApod) {
		this.txtApellidoApod = txtApellidoApod;
	}

	public FechaDatePicker getFechaUltFactura() {
		return fechaUltFactura;
	}

	public void setFechaUltFactura(FechaDatePicker fechaUltFactura) {
		this.fechaUltFactura = fechaUltFactura;
	}

	public ListBox getLstTipoPersona() {
		return lstTipoPersona;
	}

	public void setLstTipoPersona(ListBox lstTipoPersona) {
		this.lstTipoPersona = lstTipoPersona;
	}

	private List<TipoDocumentoDto> getListaTipoDocumento() {
		return listaTipoDocumento;
	}

	private void setListaTipoDocumento(List<TipoDocumentoDto> listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}

	public SolicitudServicioDto getSolicitudServicio() {
		return solicitudServicio;
	}

	public void setSolicitudServicio(SolicitudServicioDto solicitudServicio) {
		this.solicitudServicio = solicitudServicio;
	}

	public TipoPersonaDto getTipoPersonaCuenta() {
		return tipoPersonaCuenta;
	}

	public void setTipoPersonaCuenta(TipoPersonaDto tipoPersonaCuenta) {
		this.tipoPersonaCuenta = tipoPersonaCuenta;
	}

}