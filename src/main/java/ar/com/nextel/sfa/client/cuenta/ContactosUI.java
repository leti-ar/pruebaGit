package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.ContactoCuentaDto;
import ar.com.nextel.sfa.client.dto.DocumentoDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.EmailDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.SexoDto;
import ar.com.nextel.sfa.client.dto.TelefonoDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.dto.VerazResponseDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ContactosUI extends NextelDialog implements ClickListener {

	private FlexTable datosCuentaTable;
	private FlexTable telefonoTable;
	private ContactoUIData contactosData;
	private SimpleLink aceptar;
	private SimpleLink cancelar;
	private Button agregar;
	private DomicilioUI domicilioUI;
	private DomiciliosCuentaDto domicilioAEditar;
	//private CuentaDto cuentaDto;
	private ContactoCuentaDto contactoCuentaDto;

	private CuentaDatosForm cuentaDatosForm;
	private CuentaContactoForm cuentaContactoForm;
	private FlexTable domicilioTable;
	private CuentaDomiciliosForm cuentaDomiciliosForm;
	
	private int contactoABorrar = -1;
	
	private HTML iconoLupa = IconFactory.vistaPreliminar();
	
	private List<String> estilos = new ArrayList<String>();
	private int estiloUsado = 0;
	private List<DomiciliosCuentaDto> listaDomicilios;
	
	private static ContactosUI cuentaCrearContactoPopUp = null;

	public static ContactosUI getInstance() {
		if (cuentaCrearContactoPopUp == null) {
			cuentaCrearContactoPopUp = new ContactosUI();
		}
		return cuentaCrearContactoPopUp;
	}
	

	ClickListener listener = new ClickListener(){
		public void onClick(Widget sender){
		if(sender == aceptar){
			contactoCuentaDto = contactosData.getContactoDto();
			if (contactoABorrar != -1) {
				cuentaContactoForm.getInstance().eliminarContacto(contactoABorrar);
			}
			validarCampoObligatorio(true);
		}
		else if(sender == cancelar){
			hide();
		}
		else{
			DomicilioUI.getInstance().showAndCenter();
		}
	}};


	public ContactosUI() {
		super("Crear Contacto");
		init();
	}

	private void init() {
		setWidth("740px");
		contactosData = new ContactoUIData();
		TabPanel mainTabPanel = new TabPanel();
		mainTabPanel
				.addStyleDependentName("gwt-TabPanelBottom crearCuentaTabPanel");
		mainTabPanel.setWidth("98%");
		mainTabPanel.add(createDatosCuentaPanel(), "Datos");
		mainTabPanel.add(createDomicilioPanel(), "Domicilios");
		mainTabPanel.add(createTelefonoPanel(), "Telefonos");
		mainTabPanel.selectTab(0);
		add(mainTabPanel);

		aceptar = new SimpleLink("Aceptar");
		aceptar.addClickListener(listener);

		cancelar = new SimpleLink("Cerrar");
		cancelar.addClickListener(listener);
		
		addFormButtons(aceptar);
		addFormButtons(cancelar);
		setFormButtonsVisible(true);
		setFooterVisible(false);
	}

	public void cargaPopupNuevoContacto() {
		//Instancia un nuevo Contacto vacio
		this.contactoCuentaDto = new ContactoCuentaDto();
		contactosData.clean();
		aceptar.setVisible(true);
		contactosData.enableFields();
		showAndCenter();
	}

	public void cargarPopupEditarContacto(ContactoCuentaDto contacto, int fila) {
		contactoABorrar = fila;
		//completo la pantalla de info gral
		contactosData.enableFields();
		contactosData.setApellido(contacto.getApellido());
		contactosData.setNombre(contacto.getNombre());
		contactosData.setNumeroDocumento(contacto.getNumeroDocumento());
		contactosData.setTipoDocumento(contacto.getTipoDocumento());
		//completo la pantalla de telefonos
		List<TelefonoDto> listaTelefonos = new ArrayList();
		listaTelefonos = contacto.getPersona().getTelefonos();
		if (listaTelefonos != null) {
			for (Iterator iter = listaTelefonos.iterator(); iter.hasNext();) {
				TelefonoDto telefonoDto = (TelefonoDto) iter.next();

				if (telefonoDto.getPrincipal()) {
					contactosData.setTelefonoPrincipalArea(telefonoDto.getArea());
					contactosData.setTelefonoPrincipalNumero(telefonoDto.getNumeroLocal());
					contactosData.setTelefonoPrincipalInterno(telefonoDto.getInterno());
				} else if ("Adicional".equals(telefonoDto.getTipoTelefono().getDescripcion())) {
					contactosData.setTelefonoAdicionalArea(telefonoDto.getArea());
					contactosData.setTelefonoAdicionalNumero(telefonoDto.getNumeroLocal());
					contactosData.setTelefonoAdicionalInterno(telefonoDto.getInterno());
				} else if ("Celular".equals(telefonoDto.getTipoTelefono().getDescripcion())) {
					contactosData.setTelefonoCelularArea(telefonoDto.getArea());
					contactosData.setTelefonoCelularNumero(telefonoDto.getNumeroLocal());
				} else if ("Fax".equals(telefonoDto.getTipoTelefono().getDescripcion())) {
					contactosData.setFaxArea(telefonoDto.getArea());
					contactosData.setFaxNumero(telefonoDto.getNumeroLocal());
					contactosData.setFaxInterno(telefonoDto.getInterno());
				}			
			}
		}
		//completo los emails
		List<EmailDto> listaEmails = new ArrayList();
		listaEmails = contacto.getEmails();
		if (listaEmails != null) {
			for (Iterator iter = listaEmails.iterator(); iter.hasNext();) {
				EmailDto emailDto = (EmailDto) iter.next();
				if ("Personal".equals(emailDto.getTipoEmail())) {
					contactosData.setEmailPersonal(emailDto.getEmail());
				} else 
					contactosData.setEmailLaboral(emailDto.getEmail());
			}
		}

		showAndCenter();
	}
			

	private Widget createDatosCuentaPanel() {
		datosCuentaTable = new FlexTable();
		datosCuentaTable.setWidth("100%");
		datosCuentaTable.getFlexCellFormatter().setColSpan(1, 1, 4);
		datosCuentaTable.setCellSpacing(5);
		
		FlowPanel datosCuentaPanel = new FlowPanel();
		datosCuentaPanel.addStyleName("gwt-TabPanelBottom content");
	
		datosCuentaTable.setText(0, 0, Sfa.constant().tipoDocumento());
		datosCuentaTable.setWidget(0, 1, contactosData.getTipoDocumento());
		datosCuentaTable.setWidget(0, 3, new Label(Sfa.constant().numero()));
		datosCuentaTable.setWidget(0, 4, contactosData.getNumeroDocumento());
		datosCuentaTable.setWidget(2, 0, new Label(Sfa.constant().nombre()));
		datosCuentaTable.setWidget(2, 1, contactosData.getNombre());
		datosCuentaTable.setWidget(2, 3, new Label(Sfa.constant().apellido()));
		datosCuentaTable.setWidget(2, 4, contactosData.getApellido());
		datosCuentaTable.setText(3, 0, Sfa.constant().sexo());
		datosCuentaTable.setWidget(3, 1, contactosData.getSexo());
		datosCuentaTable.setText(4, 0, Sfa.constant().cargo());
		datosCuentaTable.setWidget(4, 1, contactosData.getCargo());
		FlexTable verazTable = new FlexTable();
		verazTable.setWidget(0, 0, iconoLupa);
		verazTable.setText(0, 1, Sfa.constant().veraz());
		verazTable.setWidget(0, 2, contactosData.getVeraz());
	
		datosCuentaPanel.add(datosCuentaTable);
		datosCuentaPanel.add(verazTable);
		
		iconoLupa.addClickListener(new ClickListener() {
			public void onClick (Widget sender) {
				PersonaDto personaDto = getVerazSearch(contactosData.getNumeroDocumento(), contactosData.getTipoDocumento(), contactosData.getSexo());
				inicializarVeraz(contactosData.getVeraz());
				CuentaRpcService.Util.getInstance().consultarVeraz(personaDto, 
				new DefaultWaitCallback<VerazResponseDto>() {
				
					public void success(VerazResponseDto result) {
						if (result != null) {
							setearValoresRtaVeraz(result, contactosData.getApellido(), contactosData.getNombre(), 
									null, contactosData.getSexo(), contactosData.getVeraz());
						}
					}
				});
			}
		});	

		return datosCuentaPanel;
	}
	
	public void inicializarVeraz(Label verazLabel) {
		estilos.add("verazAceptar");
        estilos.add("verazRevisar");
        estilos.add("verazRechazar");
        verazLabel.setText("");
        verazLabel.removeStyleName(estilos.get(estiloUsado));
	}
	
	public void setearValoresRtaVeraz(VerazResponseDto result, TextBox apellido, TextBox nombre, TextBox razonSocial, ListBox sexo, Label veraz) {
		apellido.setText(result.getApellido());
		apellido.setEnabled(true);        
		nombre.setText(result.getNombre());
		nombre.setEnabled(true);        
		if (razonSocial!=null) {
			razonSocial.setText(result.getRazonSocial());
		}
		sexo.setEnabled(true);

		if ("ACEPTAR".equals(result.getEstado())) {
			veraz.addStyleName(estilos.get(0));
			estiloUsado = 0;
		} else if ("REVISAR".equals(result.getEstado())) {
			veraz.addStyleName(estilos.get(1));
			estiloUsado = 1;
		}else {
			veraz.addStyleName(estilos.get(2));
			estiloUsado = 2;
		}

		veraz.setText(result.getEstado());
		MessageDialog.getInstance().setDialogTitle("Resultado Veraz");
		MessageDialog.getInstance().showAceptar(result.getMensaje(), MessageDialog.getInstance().getCloseCommand());
	}
	
	private PersonaDto getVerazSearch(TextBox numDoc, ListBox tipoDoc, ListBox sexo) {
		//if ((numDoc!=null) && (tipoDoc!=null) && (sexo!=null)) {
			PersonaDto personaDto = new PersonaDto();
			DocumentoDto documentoDto = new DocumentoDto(numDoc.getText(), (TipoDocumentoDto) tipoDoc.getSelectedItem());
			personaDto.setDocumento(documentoDto);
			personaDto.setIdTipoDocumento(documentoDto.getTipoDocumento().getId());
			personaDto.setSexo((SexoDto) sexo.getSelectedItem());
		//} 
		return personaDto;
	}

	/**
	 * @author eSalvador
	 * Refresca la grilla de domicilios
	 **/
	public void refrescaTablaConNuevoDomicilio(){
		domicilioTable.clear();
		//cargaTablaDomicilios();
		setearDomicilio();
	}
	
	private Widget createDomicilioPanel() {
		FlowPanel domicilioPanel = new FlowPanel();
		domicilioTable = new FlexTable();
		domicilioTable.addTableListener(new Listener());
		agregar = new Button("Crear Nuevo");
		agregar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				DomicilioUI.getInstance().setComandoAceptar(new Command(){
					public void execute() {
						   DomiciliosCuentaDto domicilio = DomicilioUI.getInstance().getDomicilioAEditar();
						   /**Aca deberia sacarse la lista de domicilios de los contactos, NO de la persona!*/
//						   ContactoCuentaDto contacto = new ContactoCuentaDto();
//						   PersonaDto persona = contacto.getPersona();
//						   persona.getDomicilios().add(domicilio);
						   contactosData.setDomicilio(domicilio);
						   refrescaTablaConNuevoDomicilio();
					}
				});
				DomicilioUI.getInstance().hideLabelsParaContactos();
				DomicilioUI.getInstance().cargarPopupNuevoDomicilio();
			}
		});
		
		
		agregar.addStyleName("crearDomicilioButton");
		SimplePanel crearNuevo = new SimplePanel(); 
				
		crearNuevo.setHeight("17px");
		crearNuevo.setWidth("750");
		domicilioPanel.addStyleDependentName("gwt-TabPanelBottom content");
		crearNuevo.add(agregar);
		domicilioPanel.add(crearNuevo);
	
		String[] widths = { "24px", "100%" };
		for (int col = 0; col < widths.length; col++) {
			domicilioTable.getColumnFormatter().setWidth(col, widths[col]);
		}
		domicilioTable.getColumnFormatter().addStyleName(0, "alignCenter");
		domicilioTable.getColumnFormatter().addStyleName(1, "alignCenter");
		domicilioTable.setCellPadding(0);
		domicilioTable.setCellSpacing(0);
		domicilioTable.addStyleName("gwt-BuscarCuentaResultTable");
		domicilioTable.getRowFormatter().addStyleName(0, "header");
		domicilioTable.setWidth("100%");
		domicilioTable.setHTML(0, 0, Sfa.constant().whiteSpace());
		domicilioTable.setText(0, 1, Sfa.constant().domicilios());
		domicilioPanel.add(domicilioTable);

		
		return domicilioPanel;
	}


	public void setearDomicilio() {
		listaDomicilios = contactosData.getContactoDto().getPersona().getDomicilios();
		//listaDomicilios = contactoCuentaDto.getPersona().getDomicilios();
		int row = 1; 
		if (listaDomicilios != null) {
			for (Iterator<DomiciliosCuentaDto> iter = listaDomicilios.iterator(); iter.hasNext();) {
				DomiciliosCuentaDto domicilioCuentaDto = (DomiciliosCuentaDto) iter.next();
				domicilioTable.setWidget(row, 0, IconFactory.lapiz());
				domicilioTable.setText(row, 1, armarDomicilio(domicilioCuentaDto));			
				row++;
			}
		}
	}

	private class Listener implements TableListener {
		public void onCellClicked(SourcesTableEvents arg0, int fila, int columna) {
			//boton editar
			if ((fila>=1) && (columna==0)) {
				DomicilioUI.getInstance().cargarPopupEditarDomicilio(listaDomicilios.get(fila-1));
			}
		}
	}
	
		
	private String armarDomicilio(DomiciliosCuentaDto domicilioCuentaDto) {
		String domicilio = comprobarCalle(domicilioCuentaDto.getCalle()) 
			+ comprobarNumero(domicilioCuentaDto.getNumero()) 
			+ comprobarPiso(domicilioCuentaDto.getPiso()) 
			+ comprobarDto(domicilioCuentaDto.getDepartamento()) 
			+ comprobarUnidad(domicilioCuentaDto.getUnidad_funcional()) 
			+ comprobarTorre(domicilioCuentaDto.getTorre()) 
			+ comprobarLocalidad(domicilioCuentaDto.getLocalidad()) 
			+ comprobarPartido(domicilioCuentaDto.getPartido()) 
			+ comprobarProvincia(domicilioCuentaDto.getProvincia().getDescripcion()) 
			+ comprobarCodigoPostal(domicilioCuentaDto.getCpa(),domicilioCuentaDto.getCodigo_postal());
		
		return domicilio;
	}
	
	
	private Widget createTelefonoPanel() {
		telefonoTable = new FlexTable();
		telefonoTable.getFlexCellFormatter().setColSpan(1, 1, 4);
		telefonoTable.getFlexCellFormatter().setColSpan(2, 1, 4);
		telefonoTable.getFlexCellFormatter().setColSpan(4, 1, 4);
		telefonoTable.setWidth("100%");
		telefonoTable.setCellSpacing(6);
		FlowPanel telefonoPanel = new FlowPanel();
		telefonoPanel.addStyleDependentName("gwt-TabPanelBottom content");
		telefonoPanel.add(telefonoTable);

		telefonoTable.setWidget(0, 0, new Label(Sfa.constant().telefono()));
		telefonoTable.setText(1, 0, Sfa.constant().principal());
		telefonoTable.setWidget(1, 1, contactosData.getTelefonoPrincipal());
		telefonoTable.setText(1, 2, Sfa.constant().adicional());
		telefonoTable.setWidget(1, 3, contactosData.getTelefonoAdicional());
		telefonoTable.setText(2, 0, Sfa.constant().celular());
		telefonoTable.setWidget(2, 1, contactosData.getTelefonoCelular());
		telefonoTable.setText(2, 2, Sfa.constant().fax());
		telefonoTable.setWidget(2, 3, contactosData.getFax());
		telefonoTable.setWidget(3, 0, new Label(Sfa.constant()
				.emailPanelTitle()));
		telefonoTable.setText(4, 0, Sfa.constant().personal());
		telefonoTable.setWidget(4, 1, contactosData.getEmailPersonal());
		telefonoTable.setText(4, 2, Sfa.constant().laboral());
		telefonoTable.setWidget(4, 3, contactosData.getEmailLaboral());

		return telefonoPanel;
	}
	
	private void validarCampoObligatorio(boolean showErrorDialog) {
		List<String> errors = contactosData.validarCampoObligatorio();
		if (!errors.isEmpty()) {
			if (showErrorDialog) {
				ErrorDialog.getInstance().show(errors);
			}
		} else {
			cuentaContactoForm.getInstance().setearContactos(contactoCuentaDto, contactoABorrar);
			this.hide();
		}
	}

	
	public DomicilioUI getDomicilioUI() {
		return this.domicilioUI;
	}
	

	public void onClick(Widget arg0) {
	}

	/**
	 * @author eSalvador
	 **/
	private void cargaTablaDomicilios() {
		//this.contactoCuentaDto = contactoCuentaDto;
	
		List<DomiciliosCuentaDto> domicilios;
		domicilios = contactoCuentaDto.getPersona().getDomicilios();
		
		//Limpia la tabla de domicilios incialmente, si esta con datos:
		if (datosCuentaTable.getRowCount() > 1){
			for (int j = 1; j < (datosCuentaTable.getRowCount() - 1); j++) {
				datosCuentaTable.removeRow(j);
			}
		}
		//
		for (int i = 0; i < domicilios.size(); i++) {
			if (domicilios.get(i) != null) {
				// Carga los iconos:
				datosCuentaTable.setWidget(i + 1, 0, IconFactory.lapiz());

				// if (cuenta.isPuedeVerInfocom()) {
				datosCuentaTable.setWidget(i + 1, 1, IconFactory.copiar());
				// }
				if (true) {
					datosCuentaTable.setWidget(i + 1, 2, IconFactory.cancel());
				}
				
				if ((domicilios.get(i).getIdEntrega() != null) && (domicilios.get(i).getIdFacturacion() != null)){
				
				Long idEntrega = domicilios.get(i).getIdEntrega();
				Long idFacturacion = domicilios.get(i).getIdFacturacion();
					/** Logica para mostrar tipoDomicilio en la grilla de Resultados: */

				datosCuentaTable.getCellFormatter().addStyleName(i + 1, 3, "alignCenter");
				datosCuentaTable.getCellFormatter().addStyleName(i + 1, 4, "alignCenter");

					//Entrega;
					if (idEntrega == 2) { 
						datosCuentaTable.setHTML(i + 1, 3, "Principal");	
					} else if(idEntrega == 0) {
						datosCuentaTable.setHTML(i + 1, 3, "Si");
					} else if(idEntrega == 1) {
						datosCuentaTable.setHTML(i + 1, 3, "No");
					}
					
					// Facturacion:
					if  (idFacturacion == 2){
						datosCuentaTable.setHTML(i + 1, 4, "Principal");
					} else if(idFacturacion == 0){
						datosCuentaTable.setHTML(i + 1, 4, "Si");
					}else if(idFacturacion == 1) {
						datosCuentaTable.setHTML(i + 1, 4, "No");
					}
				}
				datosCuentaTable.setHTML(i + 1, 5, domicilios.get(i).getDomicilios());
			}
		}
	}
	
	private String comprobarCalle(String calle) {
		if (!"".equals(calle)) {
			return calle.toUpperCase() + " ";
		} else 
			return "";
	}

	private String comprobarNumero(Long numero) {
		if (numero!=null) {
			return String.valueOf(numero) + " ";
		} else 
			return "";
	}
	
	private String comprobarPiso(String piso) {
		if (!"".equals(piso)) {
			return "Piso " + piso + " ";
		} else 
			return "";
	}
	
	private String comprobarDto(String dto) {
		if (!"".equals(dto)) {
			return "Dto. " + dto + " ";
		} else 
			return "";
	}
	
	private String comprobarUnidad(String unidad) {
		if (unidad==null) {
			return "";
		}
		if (!"".equals(unidad)) {
			return "UF " + unidad + " ";
		} else 
			return "";
	}
	
	private String comprobarTorre(String torre) {
		if (!"".equals(torre)) {
			return "Torre " + torre + " ";
		} else 
			return "";
	}
	
	private String comprobarLocalidad(String localidad) {
		if (!"".equals(localidad)) {
			return localidad + " ";
		} else 
			return "";
	}
	
	private String comprobarPartido(String partido) {
		if (!"".equals(partido)) {
			return "- " + partido + " -" + " ";
		} else 
			return "";
	}
	
	private String comprobarProvincia(String provincia) {
		if (!"".equals(provincia)) {
			return provincia + " ";
		} else 
			return "";
	}

	private String comprobarCodigoPostal(String cpa, String cp) {
		if (!"".equals(cpa)) {
			return "(" + cpa + ")";
		} else 
			if (!"".equals(cp)) { 
				return "(" + cp + ")";
			}
		return "";
	}
}
