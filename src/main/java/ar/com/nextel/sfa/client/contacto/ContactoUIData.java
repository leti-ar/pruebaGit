package ar.com.nextel.sfa.client.contacto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.cuenta.CuentaEdicionTabPanel;
import ar.com.nextel.sfa.client.dto.CargoDto;
import ar.com.nextel.sfa.client.dto.ContactoCuentaDto;
import ar.com.nextel.sfa.client.dto.DocumentoDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.EmailDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.SexoDto;
import ar.com.nextel.sfa.client.dto.TelefonoDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.dto.TipoEmailDto;
import ar.com.nextel.sfa.client.dto.TipoTelefonoDto;
import ar.com.nextel.sfa.client.enums.TipoDocumentoEnum;
import ar.com.nextel.sfa.client.enums.TipoEmailEnum;
import ar.com.nextel.sfa.client.enums.TipoTelefonoEnum;
import ar.com.nextel.sfa.client.initializer.CrearContactoInitializer;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.TelefonoTextBox;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.nextel.sfa.client.widget.ValidationTextBox;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ContactoUIData extends UIData implements ChangeListener, ClickListener {

	private ListBox tipoDocumento = new ListBox();
	private RegexTextBox numeroDocumento = new RegexTextBox(RegularExpressionConstants.dni);
	private ValidationTextBox nombre = new ValidationTextBox("[a-zA-Z\\%]*");
	private ValidationTextBox apellido = new ValidationTextBox("[a-zA-Z\\%]*");
	private ListBox sexo = new ListBox();
	private ListBox cargo = new ListBox("");
	private TelefonoTextBox telefonoPrincipal = new TelefonoTextBox();
	private TelefonoTextBox telefonoCelular = new TelefonoTextBox(false);
	private TelefonoTextBox telefonoAdicional = new TelefonoTextBox();
	private TelefonoTextBox fax = new TelefonoTextBox();
	private TextBox emailPersonal = new TextBox();
	private TextBox emailLaboral = new TextBox();
	private Label veraz = new Label();
	private Long newContactosId = 0L;
	private ContactoCuentaDto contactoCuentaDto;
	boolean saved = true;
	
	public List<String> validarCampoObligatorio() {
		GwtValidator validator = new GwtValidator();
		validator.addTarget(nombre).required("El campo " + Sfa.constant().nombre() + " es obligatorio");
		validator.addTarget(apellido).required("El campo " + Sfa.constant().apellido() + " es obligatorio");
		validator.fillResult();
		return validator.getErrors();
	}
	
	public List<String> validarNumeroDocumento() {
		GwtValidator validator = new GwtValidator();
		if (numeroDocumento.getText().equals("")) {
			validator.addError(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll("\\{1\\}", Sfa.constant().numeroDocumento()));
		} else {
			if (tipoDocumento.getSelectedItemId().equals(Long.toString(TipoDocumentoEnum.CUIL.getTipo())) ||
					tipoDocumento.getSelectedItemId().equals(Long.toString(TipoDocumentoEnum.CUIT.getTipo())) ||
					  tipoDocumento.getSelectedItemId().equals(Long.toString(TipoDocumentoEnum.CUIT_EXT.getTipo()))) {
				if (!numeroDocumento.getText().matches(RegularExpressionConstants.cuilCuit)) {
					validator.addError(Sfa.constant().ERR_FORMATO_CUIL());
				} else {
					validator.addTarget(numeroDocumento).cuil(Sfa.constant().ERR_DATO_CUIL());	
				}
			}
		}
		validator.fillResult();
		return validator.getErrors();
	}
	
	public List<String> validarVeraz(){
		GwtValidator validator = new GwtValidator();
		if (numeroDocumento.getText().equals("")) {
			validator.addError(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll("\\{1\\}", Sfa.constant().numeroDocumento()));
		} else {
			validator.addTarget(numeroDocumento).numericPositive(Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}", Sfa.constant().numeroDocumento()));	
		}
		if (!validator.getErrors().isEmpty()) {
			ErrorDialog.getInstance().show(validator.getErrors());
		}
		validator.fillResult();
		return validator.getErrors();
	}
	
	
	public ContactoUIData() {
		fields.add(tipoDocumento);
		fields.add(numeroDocumento);
		fields.add(nombre);
		fields.add(apellido);
		fields.add(sexo);
		fields.add(cargo);
		fields.add(telefonoPrincipal.getArea());
		fields.add(telefonoPrincipal.getNumero());
		fields.add(telefonoPrincipal.getInterno());
		fields.add(telefonoAdicional.getArea());
		fields.add(telefonoAdicional.getNumero());
		fields.add(telefonoAdicional.getInterno());
		fields.add(telefonoCelular.getArea());
		fields.add(telefonoCelular.getNumero());
		fields.add(fax.getArea());
		fields.add(fax.getNumero());
		fields.add(fax.getInterno());
		fields.add(emailPersonal);
		fields.add(emailLaboral);

		// Change listener
		for (Widget field : fields) {
			if (field instanceof SourcesChangeEvents) {
				((SourcesChangeEvents) field).addChangeListener(this);
			} else if (field instanceof SourcesClickEvents) {
				((SourcesClickEvents) field).addClickListener(this);

			}
		}
				
		tipoDocumento.setWidth("125px");
		sexo.setWidth("100px");
		cargo.setWidth("250px");
		
		//numeroDocumento.setMaxLength(10);
		nombre.setMaxLength(19);
		apellido.setMaxLength(19);
		
		emailPersonal.setMaxLength(50);
		emailLaboral.setMaxLength(50);
		
		CuentaRpcService.Util.getInstance().CrearContactoInitializer(
				new DefaultWaitCallback<CrearContactoInitializer>() {
					public void success(CrearContactoInitializer result) {
						setCombos(result);
					}
				});
		
		tipoDocumento.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent arg0) {
				numeroDocumento.setText("");
				if (tipoDocumento.getSelectedItemId().equals(TipoDocumentoEnum.CUIL.getTipo()+"") ||
					tipoDocumento.getSelectedItemId().equals(TipoDocumentoEnum.CUIT.getTipo()+"") ||
					tipoDocumento.getSelectedItemId().equals(TipoDocumentoEnum.CUIT_EXT.getTipo()+"") ){
					numeroDocumento.setPattern(RegularExpressionConstants.cuilCuit);
				} else if (tipoDocumento.getSelectedItemId().equals(TipoDocumentoEnum.DNI.getTipo()+"")) {
					numeroDocumento.setPattern(RegularExpressionConstants.dni);
				} else {
					numeroDocumento.setPattern(RegularExpressionConstants.documentoOtros);
				}
			}
		});
		
	}

	public void onChange(Widget sender) {
		saved = false;
	}
	
	public void onClick(Widget sender) {
		saved = false;
	}
	
	private void setCombos(CrearContactoInitializer datos) {
		tipoDocumento.addAllItems(datos.getTiposDocumento());
		sexo.addAllItems(datos.getSexos());
		cargo.addAllItems(datos.getCargos());
	}
		
	public void setContactoDto(ContactoCuentaDto contactoCuentaDto){
		this.contactoCuentaDto = contactoCuentaDto;
		enableFields();
		//completo la pantalla de info gral
		if(contactoCuentaDto.getPersona()!=null){
			apellido.setText(contactoCuentaDto.getPersona().getApellido());
			nombre.setText(contactoCuentaDto.getPersona().getNombre());
			numeroDocumento.setText(contactoCuentaDto.getPersona().getDocumento().getNumero());
			tipoDocumento.setSelectedItem(contactoCuentaDto.getPersona().getDocumento().getTipoDocumento());
			//completo la pantalla de telefonos
			List<TelefonoDto> listaTelefonos = new ArrayList();
			listaTelefonos = contactoCuentaDto.getPersona().getTelefonos();
			if (listaTelefonos != null) {
				for (Iterator iter = listaTelefonos.iterator(); iter.hasNext();) {
					TelefonoDto telefonoDto = (TelefonoDto) iter.next();
					if (telefonoDto.getTipoTelefono().getId()==TipoTelefonoEnum.PRINCIPAL.getTipo()) {
						telefonoPrincipal.getArea().setText(telefonoDto.getArea());
						telefonoPrincipal.getNumero().setText(telefonoDto.getNumeroLocal());
						telefonoPrincipal.getInterno().setText(telefonoDto.getInterno());
					} else 	if (telefonoDto.getTipoTelefono().getId()==TipoTelefonoEnum.ADICIONAL.getTipo()) {
						telefonoAdicional.getArea().setText(telefonoDto.getArea());
						telefonoAdicional.getNumero().setText(telefonoDto.getNumeroLocal());
						telefonoAdicional.getInterno().setText(telefonoDto.getInterno());
					} else 	if (telefonoDto.getTipoTelefono().getId()==TipoTelefonoEnum.CELULAR.getTipo()) {
						telefonoCelular.getArea().setText(telefonoDto.getArea());
						telefonoCelular.getNumero().setText(telefonoDto.getNumeroLocal());
					} else 	if (telefonoDto.getTipoTelefono().getId()==TipoTelefonoEnum.FAX.getTipo()) {
						fax.getArea().setText(telefonoDto.getArea());
						fax.getNumero().setText(telefonoDto.getNumeroLocal());
						fax.getInterno().setText(telefonoDto.getInterno());
					}			
				}
			}
			cargo.setSelectedItem(contactoCuentaDto.getPersona().getCargo());
			
			//completo los emails
			List<EmailDto> listaEmails = new ArrayList<EmailDto>();
			listaEmails = contactoCuentaDto.getPersona().getEmails();
			if (listaEmails != null) {
				for(EmailDto email : listaEmails) {
					//if (email.getTipoEmail().getId()==TipoEmailEnum.PERSONAL.getTipo()) {
					if (TipoEmailEnum.PERSONAL.getTipo().equals(email.getTipoEmail().getId())) {
						emailPersonal.setText(email.getEmail());
					} else 
						emailLaboral.setText(email.getEmail());
				}
			}
		} else {
			contactoCuentaDto.setPersona(new PersonaDto());
			contactoCuentaDto.getPersona().setDocumento(new DocumentoDto());			
		}
	}
	
	public ContactoCuentaDto getContactoDto() {
		ContactoCuentaDto contactoDto = contactoCuentaDto;
		contactoDto.setCuenta(CuentaEdicionTabPanel.getInstance().getCuenta2editDto());
		newContactosId--;
		contactoDto.setId(newContactosId);
		contactoDto.setPersona(this.getPersonaDto());
		if(this.getCargo().getSelectedItemId()!=null) {
		CargoDto cargo = new CargoDto(new Long (this.getCargo().getSelectedItemId()),this.getCargo().getSelectedItemText());
		contactoDto.getPersona().setCargo(cargo);
		}
		return contactoDto;
	}
	
	public PersonaDto getPersonaDto() {
		PersonaDto persona = new PersonaDto();
		persona.setApellido(apellido.getText());
		persona.setNombre(nombre.getText());
		persona.setCargo((CargoDto)cargo.getSelectedItem());
		persona.setIdTipoDocumento((Long.parseLong(tipoDocumento.getSelectedItem().getItemValue())));
		persona.setDocumento(getDocumentoDto());
		persona.setDomicilios(getDomicilios());
		persona.setEmails(getEmailDto());
		persona.setSexo((SexoDto)sexo.getSelectedItem());
		persona.setTelefonos(getTelefonoDto());
		return persona;
	}
	
	public List<DomiciliosCuentaDto> getDomicilios(){
		return contactoCuentaDto.getPersona().getDomicilios();
	}
	
	public DocumentoDto getDocumentoDto() {
		DocumentoDto documentoDto = new DocumentoDto();
		documentoDto.setTipoDocumento(getTipoDocumentoDto());
		documentoDto.setNumero(this.numeroDocumento.getText());
		return documentoDto;		
	}	
	
	
	public TipoDocumentoDto getTipoDocumentoDto() {
		TipoDocumentoDto tipoDocumentoDto = (TipoDocumentoDto) tipoDocumento.getSelectedItem();
		return tipoDocumentoDto;
	}
	
	public List<EmailDto> getEmailDto() {
		List<EmailDto> emails = new ArrayList<EmailDto>();
		if(!emailPersonal.getText().trim().equals("")) {
			EmailDto emailPersonalDto = new EmailDto();
			emailPersonalDto.setTipoEmail(getTipoEmailDto(TipoEmailEnum.PERSONAL.getTipo(),TipoEmailEnum.PERSONAL.getDesc()));
			emailPersonalDto.setEmail(emailPersonal.getText());
			emails.add(emailPersonalDto);
		}
		if(!emailLaboral.getText().trim().equals("")) {
			EmailDto emailLaboralDto = new EmailDto();
			emailLaboralDto.setTipoEmail(getTipoEmailDto(TipoEmailEnum.LABORAL.getTipo(),TipoEmailEnum.LABORAL.getDesc()));
			emailLaboralDto.setEmail(emailLaboral.getText());
			emails.add(emailLaboralDto);
		}
		return emails;
	}
	
	
	public List<TelefonoDto> getTelefonoDto() {
		List<TelefonoDto> telefonos = new ArrayList<TelefonoDto>();
		
		if (!telefonoPrincipal.getNumero().getText().trim().equals("")) {
			TelefonoDto telefonoPrincipalDto = new TelefonoDto();
			telefonoPrincipalDto.setTipoTelefono(getTipoTelefonoDto(TipoTelefonoEnum.PRINCIPAL.getTipo(),TipoTelefonoEnum.PRINCIPAL.getDesc()));
			telefonoPrincipalDto.setNumeroLocal(telefonoPrincipal.getNumero().getText());
			telefonoPrincipalDto.setArea(telefonoPrincipal.getArea().getText());
			telefonoPrincipalDto.setInterno(telefonoPrincipal.getInterno().getText());
			telefonoPrincipalDto.setPrincipal(true);
			telefonos.add(telefonoPrincipalDto);
		}
		if (!telefonoCelular.getNumero().getText().trim().equals("")) {
			TelefonoDto telefonoCelularDto = new TelefonoDto();
			telefonoCelularDto.setTipoTelefono(getTipoTelefonoDto(TipoTelefonoEnum.CELULAR.getTipo(),TipoTelefonoEnum.CELULAR.getDesc()));
			telefonoCelularDto.setArea(telefonoCelular.getArea().getText());
			telefonoCelularDto.setNumeroLocal(telefonoCelular.getNumero().getText());
			telefonos.add(telefonoCelularDto);
		}
		if (!telefonoAdicional.getNumero().getText().trim().equals("")) {
			TelefonoDto telefonoAdicionalDto = new TelefonoDto();
			telefonoAdicionalDto.setTipoTelefono(getTipoTelefonoDto(TipoTelefonoEnum.ADICIONAL.getTipo(),TipoTelefonoEnum.ADICIONAL.getDesc()));
			telefonoAdicionalDto.setNumeroLocal(telefonoAdicional.getNumero().getText());
			telefonoAdicionalDto.setArea(telefonoAdicional.getArea().getText());
			telefonoAdicionalDto.setInterno(telefonoAdicional.getInterno().getText());
			telefonos.add(telefonoAdicionalDto);
		}
		if (!fax.getNumero().getText().trim().equals("")) {
			TelefonoDto telefonoFaxDto = new TelefonoDto();
			telefonoFaxDto.setTipoTelefono(getTipoTelefonoDto(TipoTelefonoEnum.FAX.getTipo(),TipoTelefonoEnum.FAX.getDesc()));
			telefonoFaxDto.setNumeroLocal(fax.getNumero().getText());
			telefonoFaxDto.setArea(fax.getArea().getText());
			telefonoFaxDto.setInterno(fax.getInterno().getText());	
			telefonos.add(telefonoFaxDto);
		}
		return telefonos;
	}
	

	public TipoTelefonoDto getTipoTelefonoDto(long tipoEnum, String tipo ) {
		TipoTelefonoDto tipoTelefonoDto = new TipoTelefonoDto();
		tipoTelefonoDto.setId(tipoEnum);
		tipoTelefonoDto.setDescripcion(tipo);
		return tipoTelefonoDto;
	}
	
	public TipoEmailDto getTipoEmailDto(long tipoEnum, String tipo) {
		TipoEmailDto tipoEmailDto = new TipoEmailDto();
		tipoEmailDto.setId(tipoEnum);
		tipoEmailDto.setDescripcion(tipo);
		return tipoEmailDto;
	}

	public ListBox getTipoDocumento() {
		return tipoDocumento;
	}

	public TextBox getNumeroDocumento() {
		return numeroDocumento;
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

	public ListBox getCargo() {
		return cargo;
	}

	public TelefonoTextBox getTelefonoPrincipal() {
		return telefonoPrincipal;
	}
	
	public TelefonoTextBox getTelefonoCelular() {
		return telefonoCelular;
	}

	public TelefonoTextBox getTelefonoAdicional() {
		return telefonoAdicional;
	}

	public TelefonoTextBox getFax() {
		return fax;
	}

	public TextBox getEmailPersonal() {
		return emailPersonal;
	}

	public TextBox getEmailLaboral() {
		return emailLaboral;
	}
	
	public Label getVeraz(){
		return veraz;
	}
	
	public void setTelefonoPrincipalArea(String string) {
		telefonoPrincipal.getArea().setText(string);
	}
	
	public void setTelefonoPrincipalNumero(String string) {
		telefonoPrincipal.getNumero().setText(string);
	}
	
	public void setTelefonoPrincipalInterno(String string) {
		telefonoPrincipal.getInterno().setText(string);
	}
	
	public void setTelefonoAdicionalArea(String string) {
		telefonoAdicional.getArea().setText(string);
	}
	
	public void setTelefonoAdicionalNumero(String string) {
		telefonoAdicional.getNumero().setText(string);
	}
	
	public void setTelefonoAdicionalInterno(String string) {
		telefonoAdicional.getInterno().setText(string);
	}
	
	public void setTelefonoCelularArea(String string) {
		telefonoCelular.getArea().setText(string);
	}
	
	public void setTelefonoCelularNumero(String string) {
		telefonoCelular.getNumero().setText(string);
	}
	
	public void setFaxArea(String string) {
		fax.getArea().setText(string);
	}
	
	public void setFaxNumero(String string) {
		fax.getNumero().setText(string);
	}
	
	public void setFaxInterno(String string) {
		fax.getInterno().setText(string);
	}
	
	public void setApellido (String string) {
		apellido.setText(string);
	}
	
	public void setNombre (String string) {
		nombre.setText(string);
	}
	
	public void setNumeroDocumento (String string) {
		numeroDocumento.setText(string);
	}
	
	public void setTipoDocumento (TipoDocumentoDto item) {
		tipoDocumento.setSelectedItem(item);
	}
	
	public void setEmailPersonal(String string) {
		emailPersonal.setText(string);
	}
	
	public void setEmailLaboral(String string) {
		emailLaboral.setText(string);
	}
		
	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public boolean isSaved() {
		return saved;
	}

}
