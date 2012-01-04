package ar.com.nextel.sfa.client.ss;

import java.util.List;

import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.ModalidadCobroDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.ProveedorDto;
import ar.com.nextel.sfa.client.dto.SolicitudPortabilidadDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.dto.TipoTelefoniaDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.initializer.PortabilidadInitializer;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.validator.GwtValidator;
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
import com.google.gwt.user.client.Window;
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
	@UiField Label lblTelefono;
	@UiField Label lblEmail;
	@UiField Label lblProveedorAnterior;
	@UiField Label lblTipoTelefonia;
	@UiField Label lblModalidadCobro;
	@UiField Label lblTelefonoPortar;
	
	@UiField ListBox lstTipoDocumento;
	@UiField ListBox lstTipoTelefonia;
	@UiField ListBox lstModalidadCobro;
	@UiField ListBox lstProveedorAnterior;
	
	@UiField RegexTextBox txtEmail;
	@UiField RegexTextBox txtNroSS;
	@UiField RegexTextBox txtNombre;
	@UiField RegexTextBox txtApellido;
	@UiField RegexTextBox txtRazonSocial;
	@UiField RegexTextBox txtNroDocumento;
	@UiField RegexTextBox txtNroUltimaFacura;
	
	private Command cmndAceptar;
	private CheckBox chkPortabilidad;
	private Button btnReserva;
	private TextBox txtReserva;
	
	private static final String V1 = "\\{1\\}";
	private static final String OBLIGATORIO = "req";
	private static final String WARNING = "Advertencia";

	private PersonaDto persona;
	private SolicitudPortabilidadDto solicitudPortabilidad;
	
	/**
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
		
		lblApellido.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblNroUltimaFacura.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblNroDocumento.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		chkRecibeSMS.addStyleName("portabilidadCheck");
		chkNoPoseeTel.addStyleName("portabilidadCheck");
		chkNoPoseeEmail.addStyleName("portabilidadCheck");
		lnkCopiarCuenta.addStyleName("floatRight");
		
		comprobarTipoTelefonia();

		cmndAceptar = new Command() {
			public void execute() {
				txtTelefonoPortar.getArea().setFocus(true);
				txtTelefonoPortar.getArea().selectAll();
				ModalMessageDialog.getInstance().hide();
			}
		};
		
		txtTelefonoPortar.getArea().addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
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
		});
		
	}
	
	/**
	 * 
	 * @param evt
	 */
	@UiHandler(value={"lnkCopiarCuenta","chkNoPoseeTel","chkNoPoseeEmail"})
	void onCLick(ClickEvent evt){
		if(evt.getSource() == lnkCopiarCuenta){
			if(persona != null){
				txtNombre.setText(persona.getNombre());
				txtApellido.setText(persona.getApellido());
				txtRazonSocial.setText(persona.getRazonSocial());
				txtNroDocumento.setText(persona.getDocumento().getNumero());
				lstTipoDocumento.selectByText(persona.getDocumento().getTipoDocumento().getDescripcion());
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
	
	/**
	 * Maneja los eventos del tipo ChangeEvent de los componentes construidos desde ui.xml referenciados por UiHandler
	 * @param evt
	 */
	@UiHandler(value={"lstTipoDocumento","lstTipoTelefonia"})
	void onChange(ChangeEvent evt){
		if(evt.getSource() == lstTipoDocumento){
			if(lstTipoDocumento.getSelectedItemText().equals("CUIL") || lstTipoDocumento.getSelectedItemText().equals("CUIT"))
				txtNroDocumento.setPattern(RegularExpressionConstants.cuilCuit);
			else if(lstTipoDocumento.getSelectedItemText().equals("DNI")) 
				txtNroDocumento.setPattern(RegularExpressionConstants.dni);
			else txtNroDocumento.setPattern(RegularExpressionConstants.documentoOtros);
		}else if(evt.getSource() == lstTipoTelefonia) comprobarTipoTelefonia();
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
			lblRazonSocial.addStyleName(OBLIGATORIO);
			lblNombre.addStyleName(OBLIGATORIO);
			lblApellido.addStyleName(OBLIGATORIO);

			chkRecibeSMS.setValue(false);
			chkRecibeSMS.setEnabled(true);
		}else{
			lblNroUltimaFacura.removeStyleName(OBLIGATORIO);
			lblRazonSocial.removeStyleName(OBLIGATORIO);
			lblNombre.removeStyleName(OBLIGATORIO);
			lblApellido.removeStyleName(OBLIGATORIO);

			chkRecibeSMS.setValue(true);
			chkRecibeSMS.setEnabled(false);
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

		lstTipoDocumento.setSelectedIndex(0);
		lstTipoTelefonia.setSelectedIndex(0);
		lstModalidadCobro.setSelectedIndex(0);
		lstProveedorAnterior.setSelectedIndex(0);
			
		txtEmail.setText(null);
		txtNroSS.setText(null);;
		txtNombre.setText(null);;
		txtApellido.setText(null);;
		txtRazonSocial.setText(null);;
		txtNroDocumento.setText(null);;
		txtNroUltimaFacura.setText(null);;

		comprobarTipoTelefonia();
		
		txtTelefono.getArea().setEnabled(false);
		txtTelefono.getNumero().setEnabled(false);
		txtTelefono.getInterno().setEnabled(false);
		txtEmail.setEnabled(false);
		
		chkNoPoseeTel.setValue(true);
		chkNoPoseeEmail.setValue(true);

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

		lnkCopiarCuenta.setVisible(true);
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
		
		if(!chkNoPoseeTel.getValue())
			validador.addTarget(txtTelefono.getNumero()).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Telefono"));
		
		validador.addTarget(txtTelefonoPortar.getNumero()).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Telefono a Portar"));
		validador.addTarget(txtNroDocumento).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Nro. de Documento"));
		
		if(lstTipoTelefonia.getSelectedItemText().equals("POSTPAGO")){
			validador.addTarget(txtNroUltimaFacura).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Nro. Ultima Factura"));
			validador.addTarget(txtRazonSocial).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Razon Social"));
			validador.addTarget(txtNombre).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Nombre"));
			validador.addTarget(txtApellido).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(V1, "Portabilidad: Apellido"));
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

		setVisible(true);
		comprobarTipoTelefonia();
		
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
		}
		
		if(solicitudPortabilidad.getNroSS() != null){
			if(lstTipoTelefonia.getSelectedItemText().equals("POSTPAGO")) chkRecibeSMS.setValue(solicitudPortabilidad.isRecibeSMS());
		}

		chkPortabilidad.setValue(true);
		txtReserva.setText("");
		txtReserva.setEnabled(false);
		btnReserva.setEnabled(false);
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
			solicitudPortabilidad.setTipoDocumento((TipoDocumentoDto)lstTipoDocumento.getSelectedItem());
			solicitudPortabilidad.setTipoTelefonia((TipoTelefoniaDto)lstTipoTelefonia.getSelectedItem());
			solicitudPortabilidad.setModalidadCobro((ModalidadCobroDto)lstModalidadCobro.getSelectedItem());

			String telefono = txtTelefono.getNumero().getText();
			if(!isEmpty(txtTelefono.getArea().getText())) telefono = txtTelefono.getArea().getText() + "-" + telefono; 
			if(!isEmpty(txtTelefono.getInterno().getText())) telefono =  telefono + "-" + txtTelefono.getInterno().getText();
			solicitudPortabilidad.setTelefono(telefono);
		}
		resetearPortabilidad();
		return solicitudPortabilidad;
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
}