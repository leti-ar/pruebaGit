package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.CargoDto;
import ar.com.nextel.sfa.client.dto.ContactoDto;
import ar.com.nextel.sfa.client.dto.DocumentoDto;
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
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.TelefonoTextBox;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class ContactoUIData extends UIData {

	ListBox tipoDocumento = new ListBox("");
	TextBox numeroDocumento = new TextBox();
	TextBox nombre = new TextBox();
	TextBox apellido = new TextBox();
	ListBox sexo = new ListBox("");
	ListBox cargo = new ListBox("");
	TelefonoTextBox telefonoPrincipal = new TelefonoTextBox();
	TelefonoTextBox telefonoCelular = new TelefonoTextBox();
	TelefonoTextBox telefonoAdicional = new TelefonoTextBox();
	TelefonoTextBox fax = new TelefonoTextBox();
	TextBox emailPersonal = new TextBox();
	TextBox emailLaboral = new TextBox();
	Label veraz = new Label();
	
	private DomicilioUI domicilioUI;
	

	public List<String> validarCampoObligatorio() {
		GwtValidator validator = new GwtValidator();
		validator.addTarget(nombre).required("El campo " + Sfa.constant().nombre() + " es obligatorio");
		validator.addTarget(apellido).required("El campo " + Sfa.constant().apellido() + " es obligatorio");
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
	
	public void setContacto() {
		System.out.println("Acá hay que llenar la tabla con la info del contacto que agregaron recién");
	}
	
	public ContactoUIData() {
		fields.add(tipoDocumento);
		fields.add(numeroDocumento);
		fields.add(nombre);
		fields.add(apellido);
		fields.add(sexo);
		fields.add(cargo);
		fields.add(telefonoPrincipal);
		fields.add(telefonoCelular);
		fields.add(telefonoAdicional);
		fields.add(fax);
		fields.add(emailPersonal);
		fields.add(emailLaboral);

		tipoDocumento.setWidth("125px");
		sexo.setWidth("100px");
		cargo.setWidth("250px");

		CuentaRpcService.Util.getInstance().CrearContactoInitializer(
				new DefaultWaitCallback<CrearContactoInitializer>() {
					public void success(CrearContactoInitializer result) {
						setCombos(result);
					}
				});
	}

	private void setCombos(CrearContactoInitializer datos) {
		tipoDocumento.addAllItems(datos.getTiposDocumento());
		sexo.addAllItems(datos.getSexos());
		cargo.addAllItems(datos.getCargos());
	}
	
	
	public ContactoDto getContactoDto() {
		ContactoDto contactoDto = new ContactoDto();
		contactoDto.setPersonaDto(this.getPersonaDto());
		return contactoDto;
	}
	
	public PersonaDto getPersonaDto() {
		PersonaDto persona = new PersonaDto();
		persona.setApellido(apellido.getText());
		persona.setNombre(nombre.getText());
		persona.setCargo((CargoDto)cargo.getSelectedItem());
		persona.setIdTipoDocumento((Long.parseLong(tipoDocumento.getSelectedItem().getItemValue())));
		//arreglar este que sigue
		persona.setDocumento(getDocumentoDto());
		//pasarle una lista de domicilios, hacer que cuando agregas un nuevo domicilio se carguen en la tabla de la pantalla
		//persona.setDomicilios(domicilioUI.getInstance().getDomiciliosData().getDomicilio());
		persona.setEmails(getEmailDto());
		persona.setSexo((SexoDto)sexo.getSelectedItem());
		persona.setTelefonos(getTelefonoDto());
		return persona;
	}
	
	
	public DocumentoDto getDocumentoDto() {
		DocumentoDto documentoDto = new DocumentoDto();
		documentoDto.setTipoDocumento(getTipoDocumentoDto());
		documentoDto.setNumero(this.numeroDocumento.getText());
		return documentoDto;		
	}	
	
	//en el combo tengo los nombres de los tipos de documento, como lo relaciono con lo que tiene el TipoDocumentoEnum?? (ahora le paso siempre DNI)
	public TipoDocumentoDto getTipoDocumentoDto() {
		TipoDocumentoDto tipoDocumentoDto = new TipoDocumentoDto();
		tipoDocumentoDto.setId(TipoDocumentoEnum.DNI.getTipo());
		tipoDocumentoDto.setDescripcion("DNI");
		return tipoDocumentoDto;
	}
	
	public List<EmailDto> getEmailDto() {
		List<EmailDto> emails = new ArrayList();
		EmailDto emailPersonalDto = new EmailDto();
		emailPersonalDto.setTipoEmail(getTipoEmailDto(TipoEmailEnum.PERSONAL.getTipo(),"Personal"));
		emailPersonalDto.setEmail(emailPersonal.getText());
		EmailDto emailLaboralDto = new EmailDto();
		emailLaboralDto.setTipoEmail(getTipoEmailDto(TipoEmailEnum.LABORAL.getTipo(),"Laboral"));
		emailLaboralDto.setEmail(emailLaboral.getText());
		emails.add(emailPersonalDto);
		emails.add(emailLaboralDto);
		return emails;
	}
	
	
	public List<TelefonoDto> getTelefonoDto() {
		List<TelefonoDto> telefonos = new ArrayList();
		TelefonoDto telefonoPrincipalDto = new TelefonoDto();
		telefonoPrincipalDto.setTipoTelefono(getTipoTelefonoDto(TipoTelefonoEnum.PRINCIPAL.getTipo(),"Principal"));
		telefonoPrincipalDto.setNumeroLocal(telefonoPrincipal.getNumero().getText());
		telefonoPrincipalDto.setArea(telefonoPrincipal.getArea().getText());
		telefonoPrincipalDto.setInterno(telefonoPrincipal.getInterno().getText());
		telefonoPrincipalDto.setPrincipal(true);
		
		TelefonoDto telefonoCelularDto = new TelefonoDto();
		telefonoCelularDto.setTipoTelefono(getTipoTelefonoDto(TipoTelefonoEnum.CELULAR.getTipo(),"Celular"));
		telefonoCelularDto.setArea(telefonoCelular.getArea().getText());
		telefonoCelularDto.setNumeroLocal(telefonoCelular.getNumero().getText());
		
		TelefonoDto telefonoAdicionalDto = new TelefonoDto();
		telefonoAdicionalDto.setTipoTelefono(getTipoTelefonoDto(TipoTelefonoEnum.ADICIONAL.getTipo(),"Adicional"));
		telefonoAdicionalDto.setNumeroLocal(telefonoAdicional.getNumero().getText());
		telefonoAdicionalDto.setArea(telefonoAdicional.getArea().getText());
		telefonoAdicionalDto.setInterno(telefonoAdicional.getInterno().getText());
		
		TelefonoDto telefonoFaxDto = new TelefonoDto();
		telefonoFaxDto.setTipoTelefono(getTipoTelefonoDto(TipoTelefonoEnum.FAX.getTipo(),"Fax"));
		telefonoFaxDto.setNumeroLocal(fax.getNumero().getText());
		telefonoFaxDto.setArea(fax.getArea().getText());
		telefonoFaxDto.setInterno(fax.getInterno().getText());	
		
		telefonos.add(telefonoPrincipalDto);
		telefonos.add(telefonoCelularDto);
		telefonos.add(telefonoAdicionalDto);
		telefonos.add(telefonoFaxDto);		
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

}
