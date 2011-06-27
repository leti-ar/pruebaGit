package ar.com.nextel.sfa.client.ss;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.InfocomRpcService;
import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.cuenta.CuentaClientService;
import ar.com.nextel.sfa.client.cuenta.CuentaEdicionTabPanel;
import ar.com.nextel.sfa.client.dto.CreateSaveSSTransfResultDto;
import ar.com.nextel.sfa.client.dto.CreateSaveSolicitudServicioResultDto;
import ar.com.nextel.sfa.client.dto.CuentaSSDto;
import ar.com.nextel.sfa.client.dto.GeneracionCierreResultDto;
import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.dto.ItemSolicitudTasadoDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.ListaPreciosDto;
import ar.com.nextel.sfa.client.dto.MessageDto;
import ar.com.nextel.sfa.client.dto.ModeloDto;
import ar.com.nextel.sfa.client.dto.OrigenSolicitudDto;
import ar.com.nextel.sfa.client.dto.PlanDto;
import ar.com.nextel.sfa.client.dto.ResultadoReservaNumeroTelefonoDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalIncluidoDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalLineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioRequestDto;
import ar.com.nextel.sfa.client.dto.TipoPlanDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;
import ar.com.nextel.sfa.client.enums.PermisosEnum;
import ar.com.nextel.sfa.client.infocom.InfocomUIData;
import ar.com.nextel.sfa.client.initializer.ContratoViewInitializer;
import ar.com.nextel.sfa.client.initializer.InfocomInitializer;
import ar.com.nextel.sfa.client.initializer.LineasSolicitudServicioInitializer;
import ar.com.nextel.sfa.client.initializer.SolicitudInitializer;
import ar.com.nextel.sfa.client.util.HistoryUtils;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.ModalMessageDialog;
import ar.com.nextel.sfa.client.widget.RazonSocialClienteBar;
import ar.com.nextel.sfa.client.widget.UILoader;
import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;
import ar.com.snoop.gwt.commons.client.dto.ListBoxItemImpl;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class EditarSSUI extends ApplicationUI implements ClickHandler, ClickListener, EditarSSUIController {

	public static final String ID_CUENTA = "idCuenta";
	public static final String ID_GRUPO_SS = "idGrupoSS";
	public static final String ID_CUENTA_POTENCIAL = "idCuentaPotencial";
	public static final String CODIGO_VANTIVE = "codigoVantive";
	private static final String validarCompletitudFailStyle = "validarCompletitudFailButton";
	private static final String VENDEDOR_NO_COMISIONABLE = "VENDEDOR_NO_COMISIONABLE";
	
	private InfocomUIData infocomUIData;
	private String idCuenta;
	private String codigoVantive;
	private TabPanel tabs;
	private DatosSSUI datos;
	private DatosTransferenciaSSUI datosTranferencia;
	private VariosSSUI varios;
	private EditarSSUIData editarSSUIData;
	private FormButtonsBar formButtonsBar;
	private RazonSocialClienteBar razonSocialClienteBar;
	private SimpleLink guardarButton;
	private SimpleLink cancelarButton;
	private SimpleLink acionesSS;
	private Button validarCompletitud;
	private String tokenLoaded;
	private PopupPanel generarCerrarMenu;
	private SimpleLink cerrarSolicitud;
	private SimpleLink generarSolicitud;
	private DefaultWaitCallback<GeneracionCierreResultDto> generacionCierreCallback;
	private CerrarSSUI cerrarSSUI;
	private boolean guardandoSolicitud = false;
	private boolean cerrandoSolicitud = false;
	private String codigoVant;
	private boolean cerrandoAux;
	private CuentaEdicionTabPanel cuenta;
	
	private String grupoSS;
	private HashMap<String, Long> knownInstancias;
	FlowPanel linksCrearSS;

	public EditarSSUI() {
		super();
		addStyleName("Gwt-EditarSSUI");
		knownInstancias = ClientContext.getInstance().getKnownInstance();
		linksCrearSS = new FlowPanel();
	}

	private void getInfocomData(String idCuenta, String responsablePago, String codigoVantive) {
		InfocomRpcService.Util.getInstance().getInfocomInitializer(idCuenta, codigoVantive, responsablePago, new DefaultWaitCallback<InfocomInitializer>() {
			public void success(InfocomInitializer result) {
				if (result != null) {
					editarSSUIData.setInfocom(result);
				}
			}
		});
	}

	private boolean loadInfocom(String cuentaID, String codigoVantive) {
		if (infocomUIData==null) {
			infocomUIData = new InfocomUIData();
			infocomUIData.setIdCuenta(cuentaID);
			infocomUIData.setCodigoVantive(codigoVantive);
			idCuenta = cuentaID;
			this.codigoVantive = codigoVantive;
			infocomUIData.getResponsablePago().clear();
			this.getInfocomData(idCuenta, idCuenta, codigoVantive);
		}
		return true;
	}
	
	public boolean load() {
		tokenLoaded = History.getToken();
		String cuenta = HistoryUtils.getParam(ID_CUENTA);
		cuenta = cuenta != null && !"".equals(cuenta) ? cuenta : null;
		grupoSS = HistoryUtils.getParam(ID_GRUPO_SS);
		String cuentaPotencial = HistoryUtils.getParam(ID_CUENTA_POTENCIAL);
		String codigoVantive = HistoryUtils.getParam(CODIGO_VANTIVE);
		mainPanel.setVisible(false);
//		tabs.selectTab(0);
		
		linksCrearSS.clear();
		if(grupoSS != null && knownInstancias != null && 
				!grupoSS.equals(knownInstancias.get(GrupoSolicitudDto.ID_TRANSFERENCIA).toString()) &&
				!ClientContext.getInstance().checkPermiso(PermisosEnum.OCULTA_LINK_GENERAR_SS.getValue())){
			linksCrearSS.add(wrap(generarSolicitud));
		}
		linksCrearSS.add(wrap(cerrarSolicitud));
		
		
		if (cuenta == null && cuentaPotencial == null && codigoVantive == null) {
			ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
			ErrorDialog.getInstance().show(Sfa.constant().ERR_URL_PARAMS_EMPTY(), false);
		} else if (codigoVantive != null && codigoVantive.length() > 9 && codigoVantive.endsWith(".100000")) {
			ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
			ErrorDialog.getInstance().show(Sfa.constant().ERR_NO_ACCESO_CREAR_SS(), false);
		} else {
			SolicitudServicioRequestDto solicitudServicioRequestDto = new SolicitudServicioRequestDto();
			solicitudServicioRequestDto.setIdCuenta(cuenta != null ? Long.parseLong(cuenta) : null);
			solicitudServicioRequestDto.setIdCuentaPotencial(cuentaPotencial != null ? Long
					.parseLong(cuentaPotencial) : null);
			solicitudServicioRequestDto.setNumeroCuenta(codigoVantive);
			
			if (grupoSS != null) {
				solicitudServicioRequestDto.setIdGrupoSolicitud(Long.parseLong(grupoSS));
			} else {
				//MGR - #1050
				if(knownInstancias != null){
					solicitudServicioRequestDto.setIdGrupoSolicitud(
							knownInstancias.get(GrupoSolicitudDto.ID_EQUIPOS_ACCESORIOS));
				}
				
			}

			if(knownInstancias != null && solicitudServicioRequestDto.getIdGrupoSolicitud().equals(
					knownInstancias.get(GrupoSolicitudDto.ID_TRANSFERENCIA))){
				SolicitudRpcService.Util.getInstance().createSolicitudServicioTranferencia(solicitudServicioRequestDto, 
						new DefaultWaitCallback<CreateSaveSSTransfResultDto>() {
							public void success(final CreateSaveSSTransfResultDto result) {
								if(result.isError()){
									ErrorDialog.getInstance().setDialogTitle("Aviso");
									StringBuilder msgString = new StringBuilder();
									for (MessageDto msg : result.getMessages()) {
										msgString.append("<span class=\"error\">- " + msg.getDescription()
												+ "</span><br>");
									}
									ErrorDialog.getInstance().show(msgString.toString(), false);
								
								}else{
									Command abrirSSCreada = new Command() {
										public void execute() {
											MessageDialog.getInstance().hide();
											ssCreadaSuccess(result.getSolicitud());
										}
									};
									
									if(!result.getMessages().isEmpty()){
										StringBuilder msgString = new StringBuilder();
										for (MessageDto msg : result.getMessages()) {
											msgString.append("<span class=\"error\">- " + msg.getDescription()
													+ "</span><br>");
										}
										MessageDialog.getInstance().showAceptar("Aviso",msgString.toString(), abrirSSCreada);
									}else{
										abrirSSCreada.execute();
									}
								}
							}
					
							public void failure(Throwable caught) {
								History.back();
								super.failure(caught);
							}
						});
			}else{
				//MGR - ISDN 1824 - Ya no devuelve una SolicitudServicioDto, sino un CreateSaveSolicitudServicioResultDto 
				//que permite realizar el manejo de mensajes
				SolicitudRpcService.Util.getInstance().createSolicitudServicio(solicitudServicioRequestDto,
						new DefaultWaitCallback<CreateSaveSolicitudServicioResultDto>() {

					//MGR - ISDN 1824
					@Override
					public void success(final CreateSaveSolicitudServicioResultDto result) {
						
						if(result.isError()){
							ErrorDialog.getInstance().setDialogTitle("Aviso");
							StringBuilder msgString = new StringBuilder();
							for (MessageDto msg : result.getMessages()) {
								msgString.append("<span class=\"error\">- " + msg.getDescription()
										+ "</span><br>");
							}
							ErrorDialog.getInstance().show(msgString.toString(), false);
						
						}else{
							Command abrirSSCreada = new Command() {
								public void execute() {
									MessageDialog.getInstance().hide();
									SolicitudServicioDto solicitud = result.getSolicitud();
									loadInfocom(String.valueOf(solicitud.getCuenta().getId()), solicitud.getCuenta().getCodigoVantive());
									ssCreadaSuccess(solicitud);
								}
							};
							
							if(!result.getMessages().isEmpty()){
								StringBuilder msgString = new StringBuilder();
								for (MessageDto msg : result.getMessages()) {
									msgString.append("<span class=\"error\">- " + msg.getDescription()
											+ "</span><br>");
								}
								MessageDialog.getInstance().showAceptar("Aviso",msgString.toString(), abrirSSCreada);
							}else{
								abrirSSCreada.execute();
							}
						}
					}

					public void failure(Throwable caught) {
						History.back();
						super.failure(caught);
					}
				});
			}
				editarSSUIData.clean();
				varios.cleanScoring();
			}
			return true;
		
	}
	
	private void ssCreadaSuccess(SolicitudServicioDto solicitud){
		editarSSUIData.setSaved(true);
		// varios.setScoringVisible(!solicitud.getGrupoSolicitud().isCDW());
		razonSocialClienteBar.setCliente(solicitud.getCuenta().getCodigoVantive());
		razonSocialClienteBar.setRazonSocial(solicitud.getCuenta().getPersona()
				.getRazonSocial());
		razonSocialClienteBar.setIdCuenta(solicitud.getCuenta().getId(), solicitud
				.getCuenta().getCodigoVantive());
		codigoVant = solicitud.getCuenta().getCodigoVantive();
		editarSSUIData.setSolicitud(solicitud);
		
		//MGR - #962 - #1017
		if(ClientContext.getInstance().
				checkPermiso(PermisosEnum.SELECT_OPC_TELEMARKETING_COMB_ORIGEN.getValue())){
			editarSSUIData.getOrigen().selectByText("Telemarketing");
		}
		
		//MGR - #1152
		boolean esProspect =RegularExpressionConstants.isVancuc(solicitud.getCuenta().getCodigoVantive());
		
		if(solicitud.getNumero() == null && ClientContext.getInstance().
				checkPermiso(PermisosEnum.AUTOCOMPLETAR_TRIPTICO.getValue()) && !esProspect){
			editarSSUIData.getNss().setText(String.valueOf(solicitud.getTripticoNumber()));
		} 
		
		validarCompletitud(false);
		//datos.refresh();

		tabs.clear();
		if(solicitud.getGrupoSolicitud().isTransferencia()){
			tabs.add(datosTranferencia, "Transf.");
			datosTranferencia.setDatosSolicitud(solicitud);
		// el refresh se llama desde seDatosSolicitud.
			//	datosTranferencia.refresh();
		}
		else{
			tabs.add(datos, "Datos");
			tabs.add(varios, "Varios");
			datos.refresh();
		}
		tabs.selectTab(0);
		mainPanel.setVisible(true);
	}
	

	public void firstLoad() {
		razonSocialClienteBar = new RazonSocialClienteBar();
		
		//MGR - #1015
		if( (ClientContext.getInstance().vengoDeNexus() && !ClientContext.getInstance().soyClienteNexus())
				|| !ClientContext.getInstance().vengoDeNexus()){
			mainPanel.add(razonSocialClienteBar);
		}
		
		razonSocialClienteBar.setEnabledSilvioSoldan();

		validarCompletitud = new Button("Validar Completitud");
		validarCompletitud.addStyleName("validarCompletitudButton");
		validarCompletitud.addClickHandler(this);
		mainPanel.add(validarCompletitud);
		tabs = new TabPanel();
		tabs.setWidth("98%");
		tabs.addStyleName("mlr5 mb10 mt5");
		mainPanel.add(tabs);
		editarSSUIData = new EditarSSUIData(this);
//		tabs.add(datos = new DatosSSUI(this), "Datos");
//		tabs.add(varios = new VariosSSUI(this), "Varios");
//		tabs.selectTab(0);
		datos = new DatosSSUI(this);
		varios = new VariosSSUI(this);
		datosTranferencia = new DatosTransferenciaSSUI(this);
		grupoSS = HistoryUtils.getParam(ID_GRUPO_SS);

		tabs.addBeforeSelectionHandler(new BeforeSelectionHandler<Integer>() {
			public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
				if (event.getItem().intValue() == 1) {
					varios.refresh();
				}
			}
		});

		SolicitudRpcService.Util.getInstance().getSolicitudInitializer(
				new DefaultWaitCallback<SolicitudInitializer>() {
					public void success(SolicitudInitializer initializer) {
						loadInitializer(initializer);
					};
				});

		generarCerrarMenu = new PopupPanel(true);
		generarCerrarMenu.addStyleName("dropUpStyle");

//		FlowPanel linksCrearSS = new FlowPanel();
		generarSolicitud = new SimpleLink("Generar");
		cerrarSolicitud = new SimpleLink("Cerrar");
		
		//MGR - #1122
//		if(grupoSS != null && knownInstancias != null && 
//				!grupoSS.equals(knownInstancias.get(GrupoSolicitudDto.ID_TRANSFERENCIA).toString()) &&
//				!ClientContext.getInstance().checkPermiso(PermisosEnum.OCULTA_LINK_GENERAR_SS.getValue())){
//			linksCrearSS.add(wrap(generarSolicitud));
//		}
//		
//		linksCrearSS.add(wrap(cerrarSolicitud));
		generarSolicitud.addClickListener(this);
		cerrarSolicitud.addClickListener(this);
		generarCerrarMenu.setWidget(linksCrearSS);

		formButtonsBar = new FormButtonsBar();
		mainPanel.add(formButtonsBar);
		formButtonsBar.addStyleName("mt10");
		formButtonsBar.addLink(guardarButton = new SimpleLink("Guardar"));
		formButtonsBar.addLink(acionesSS = new SimpleLink("^SS"));
		formButtonsBar.addLink(cancelarButton = new SimpleLink("Cancelar"));
		guardarButton.addClickListener(this);
		acionesSS.addClickListener(this);
		cancelarButton.addClickListener(this);
	}

	private Widget wrap(Widget w) {
		SimplePanel wrapper = new SimplePanel();
		wrapper.setWidget(w);
		return wrapper;
	}

	private void loadInitializer(SolicitudInitializer initializer) {
		editarSSUIData.getOrigen().addAllItems(initializer.getOrigenesSolicitud());

		//MGR - #1458
		if(initializer.getOrigenesSolicitud().size() ==1){
			editarSSUIData.getOrigen().setSelectedIndex(1);
		}
		
		if (ClientContext.getInstance().getVendedor().isAP()) {
			for (Iterator<OrigenSolicitudDto> iterator = initializer
					.getOrigenesSolicitud().iterator(); iterator.hasNext();) {
				OrigenSolicitudDto origen = (OrigenSolicitudDto) iterator.next();
				if (!"ATP".equals(origen.getDescripcion())) {
					iterator.remove();
				}
			}
		} else if (ClientContext.getInstance().getVendedor().isDealer()) {
			for (Iterator<OrigenSolicitudDto> iterator = initializer
					.getOrigenesSolicitud().iterator(); iterator.hasNext();) {
				OrigenSolicitudDto origen = (OrigenSolicitudDto) iterator.next();
				if (!"Vendedor".equals(origen.getDescripcion())) {
					iterator.remove();
				}
			}
		} else if (ClientContext.getInstance().getVendedor().isADMCreditos()) {
			for (Iterator<OrigenSolicitudDto> iterator = initializer
					.getOrigenesSolicitud().iterator(); iterator.hasNext();) {
				OrigenSolicitudDto origen = (OrigenSolicitudDto) iterator.next();
				if (!"ATP".equals(origen.getDescripcion()) && !"Vendedor".equals(origen.getDescripcion())) {
					iterator.remove();
				}
			}
		}
		
		editarSSUIData.getOrigenTR().addAllItems(initializer.getOrigenesSolicitud());
		if(initializer.getOrigenesSolicitud().size() == 1){
			editarSSUIData.getOrigenTR().setSelectedIndex(1);
		}
		
		editarSSUIData.getAnticipo().addAllItems(initializer.getTiposAnticipo());

		if(ClientContext.getInstance().getVendedor().isDealer()){
			ListBoxItem item = new ListBoxItemImpl(
					ClientContext.getInstance().getVendedor().getApellidoYNombre(), 
					ClientContext.getInstance().getVendedor().getId().toString());
			editarSSUIData.getVendedor().addItem(item);
			editarSSUIData.getVendedor().setSelectedItem(item);
		} 
		else if(ClientContext.getInstance().getVendedor().isAP()){
			if(knownInstancias != null){
				ListBoxItem item = new ListBoxItemImpl(EditarSSUIData.NO_COMISIONABLE, 
							knownInstancias.get(VENDEDOR_NO_COMISIONABLE).toString());
				editarSSUIData.getVendedor().addItem(item);
				editarSSUIData.getVendedor().setSelectedItem(item);
			}
		}
		else{
			editarSSUIData.getVendedor().addAllItems(initializer.getVendedores());
		}
		
		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_SUCURSAL_ORIGEN.getValue())){
			editarSSUIData.getSucursalOrigen().addAllItems(initializer.getSucursales());
			
			//Para que cargue correctamente la opcion del combo
			if(editarSSUIData.getIdSucursalSolicitud() != null){
				editarSSUIData.getSucursalOrigen().selectByValue(editarSSUIData.getIdSucursalSolicitud().toString());
			}else if(editarSSUIData.getVendedor().getSelectedItem() != null){
				VendedorDto vendAux = (VendedorDto) editarSSUIData.getVendedor().getSelectedItem();
				editarSSUIData.getSucursalOrigen().selectByValue(vendAux.getIdSucursal().toString());
			}
		}
		else{
			ListBoxItem item = new ListBoxItemImpl(
			ClientContext.getInstance().getVendedor().getIdSucursal().toString(), 
			ClientContext.getInstance().getVendedor().getIdSucursal().toString());
			editarSSUIData.getSucursalOrigen().addItem(item);
			editarSSUIData.getSucursalOrigen().setSelectedIndex(1);
		}
	}

	public boolean unload(String token) {
		if (!editarSSUIData.isSaved() && !tokenLoaded.equals(token)) {
			ModalMessageDialog.getInstance().showSiNoCancelar(Sfa.constant().guardar(),
					Sfa.constant().MSG_PREGUNTA_GUARDAR(), new SaveSSCommand(true, token),
					new SaveSSCommand(false, token), ModalMessageDialog.getCloseCommand());
			return false;
		}
		return true;
	}

	private class SaveSSCommand implements Command {

		private boolean save = true;
		private String token;

		public SaveSSCommand(boolean save, String token) {
			this.save = save;
			this.token = token;
		}

		public void execute() {
			List<String> errors = null;
			if (save) {
				if(editarSSUIData.getGrupoSolicitud()!= null && editarSSUIData.getGrupoSolicitud().isTransferencia()){
					errors = editarSSUIData.validarTransferenciaParaGuardar(datosTranferencia.getContratosSSChequeados());
				}else{
					errors = editarSSUIData.validarParaGuardar();
				}
				if (errors.isEmpty()) {
					if(editarSSUIData.getGrupoSolicitud()!= null && editarSSUIData.getGrupoSolicitud().isTransferencia()){
						editarSSUIData.validarPlanesCedentes(guardarSolicitudCallback());
					}else{
						guardar();
						editarSSUIData.setSaved(true);
					}
					
				} else {
					ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
					ErrorDialog.getInstance().show(errors, false);
				}
			} else {
				editarSSUIData.desreservarNumerosNoGuardados();
				editarSSUIData.setSaved(true);
			}
			// Continuo a la página a la que me dirigía en el caso de que no haya errores.
			if (errors == null || errors.isEmpty()) {
				History.newItem(token);
			}
			ModalMessageDialog.getInstance().hide();
		}
	}
	
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		onClick(sender);
	}

	public void onClick(Widget sender) {
		if (sender == guardarButton) {
			List<String> errors = null;
			if(editarSSUIData.getGrupoSolicitud()!= null && editarSSUIData.getGrupoSolicitud().isTransferencia()){	
				errors = editarSSUIData.validarTransferenciaParaGuardar(datosTranferencia.getContratosSSChequeados());
			}
			else{
				errors = editarSSUIData.validarParaGuardar();
			}
			if (errors.isEmpty()) {
				if(editarSSUIData.getGrupoSolicitud()!= null && editarSSUIData.getGrupoSolicitud().isTransferencia()){
					editarSSUIData.validarPlanesCedentes(guardarSolicitudCallback());
				}else{
					guardar();
				}
			} else {
				ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
				ErrorDialog.getInstance().show(errors, false);
			}
		} else if (sender == cancelarButton) {
			History.newItem("");
		} else if (sender == validarCompletitud) {
			validarCompletitud(true);
		} else if (sender == acionesSS) {
			generarCerrarMenu.show();
			generarCerrarMenu.setPopupPosition(acionesSS.getAbsoluteLeft() - 10,
					acionesSS.getAbsoluteTop() - generarCerrarMenu.getOffsetHeight());
		} else if (sender == generarSolicitud || sender == cerrarSolicitud) {
			generarCerrarMenu.hide();
			openGenerarCerrarSolicitdDialog(sender == cerrarSolicitud);
		}
	}

	private void guardar() {
		if (guardandoSolicitud) {
			return;
		}
		guardandoSolicitud = true;
		
		if(editarSSUIData.getGrupoSolicitud()!= null && editarSSUIData.getGrupoSolicitud().isTransferencia()){
			
			SolicitudRpcService.Util.getInstance().saveSolicituServicioTranferencia(obtenerSolicitudTransferencia(false),
					new DefaultWaitCallback<CreateSaveSSTransfResultDto>() {

						public void success(CreateSaveSSTransfResultDto result) {
							guardandoSolicitud = false;
							editarSSUIData.setSolicitud(result.getSolicitud());
							datosTranferencia.setDatosSolicitud(result.getSolicitud());
//							datosTranferencia.refresh();
							editarSSUIData.setSaved(true);
							
							if(result.isError()){
								StringBuilder msgString = new StringBuilder();
								for (MessageDto msg : result.getMessages()) {
									msgString.append("<span class=\"warn\">- " + msg.getDescription()
											+ "</span><br>");
								}
								MessageDialog.getInstance().showAceptar("Aviso",msgString.toString(), MessageDialog.getCloseCommand());
							}
						}

						public void failure(Throwable caught) {
							guardandoSolicitud = false;
							super.failure(caught);
						}
					});
		}
		else{
			//MGR - ISDN 1824 - Como se realizan validaciones, ya no recibe una SolicitudServicioDto
			//sino una SaveSolicitudServicioResultDto que permite realizar el manejo de mensajes
			SolicitudRpcService.Util.getInstance().saveSolicituServicio(editarSSUIData.getSolicitudServicio(),
					new DefaultWaitCallback<CreateSaveSolicitudServicioResultDto>() {
						
						public void success(CreateSaveSolicitudServicioResultDto result) {
							guardandoSolicitud = false;
							editarSSUIData.setSolicitud(result.getSolicitud());
							datos.refresh();
							
							// MessageDialog.getInstance().showAceptar("Guardado Exitoso",
							// Sfa.constant().MSG_SOLICITUD_GUARDADA_OK(), MessageDialog.getCloseCommand());
							editarSSUIData.setSaved(true);
							
							//MGR - ISDN 1824
							if(result.isError()){
								StringBuilder msgString = new StringBuilder();
								for (MessageDto msg : result.getMessages()) {
									msgString.append("<span class=\"warn\">- " + msg.getDescription()
											+ "</span><br>");
								}
								MessageDialog.getInstance().showAceptar("Aviso",msgString.toString(), MessageDialog.getCloseCommand());
							}
						}

						public void failure(Throwable caught) {
							guardandoSolicitud = false;
							super.failure(caught);
						}
					});
		}
	}

	private void openGenerarCerrarSolicitdDialog(boolean cerrando) {
		cerrandoAux = cerrando;
		cuenta = CuentaEdicionTabPanel.getInstance();
		
		//obtengo la cuenta que acaba de seleccionar
		Long idCuenta = null;
		if (HistoryUtils.getParam("idCuenta") != null) {
			idCuenta = Long.parseLong(HistoryUtils.getParam("idCuenta"));
		} else if (HistoryUtils.getParam("cuenta_id") != null) {
			idCuenta = Long.parseLong(HistoryUtils.getParam("cuenta_id"));
		}
		CuentaClientService.cargarDatosCuenta(idCuenta, codigoVant, false, false);
			
		WaitWindow.show();
        DeferredCommand.addCommand(new IncrementalCommand() {
	        public boolean execute() {
	        	if (CuentaClientService.cuentaDto == null){
	            	return true;
	        	}
		        //si el campo nombre no está cargado significa que no están cargados los campos obligatorios de la cuenta
		        if (CuentaClientService.cuentaDto.getPersona().getNombre() != null) {
		        	
		        	List<String> errors = null;
		        	if(editarSSUIData.getGrupoSolicitud()!= null && editarSSUIData.getGrupoSolicitud().isTransferencia()){
						errors = editarSSUIData.validarTransferenciaParaCerrarGenerar(datosTranferencia.getContratosSSChequeados(),false);
					}else{
						errors = editarSSUIData.validarParaCerrarGenerar(false);
					}
		        	
		            if (errors.isEmpty()) {
		            	
		            	if(editarSSUIData.getGrupoSolicitud()!= null && editarSSUIData.getGrupoSolicitud().isTransferencia()){
		            		editarSSUIData.validarPlanesCedentes(abrirCerrarDialogCallback());
		            	}else{
		            		abrirDialogCerrar();
		            	}
		            } else {
		            	ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
		                ErrorDialog.getInstance().show(errors, false);
		            }
		        } else {
		        	ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
	                ErrorDialog.getInstance().show("Debe completar los campos obligatorios de la cuenta");
		        }
		        WaitWindow.hide();
		        return false;
	        }
        });
	}
	
	private Command generarCerrarSolicitudCommand() {
		return new Command() {
			public void execute() {
				editarSSUIData.setSolicitudServicioGeneracion(getCerrarSSUI().getCerrarSSUIData()
						.getSolicitudServicioGeneracion());
				// Se comenta por el nuevo cartel de cargando;
				CerradoSSExitosoDialog.getInstance().showLoading(cerrandoSolicitud);
				List<String> errors = null;

				if(editarSSUIData.getGrupoSolicitud()!= null && editarSSUIData.getGrupoSolicitud().isTransferencia()){
					errors = editarSSUIData.validarTransferenciaParaCerrarGenerar(datosTranferencia.getContratosSSChequeados(),true);
					
				}else{
					errors = editarSSUIData.validarParaCerrarGenerar(true);
				}
				if (errors.isEmpty()) {
					//MGR - #1481 - No vuelvo a validar los planes para que no aparesca el mensaje de
					//aviso dos veces.
					cerrarGenerarSolicitud();
				} else {
					CerradoSSExitosoDialog.getInstance().hideLoading();
					ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
					ErrorDialog.getInstance().show(errors, false);
				}
			}
		};
	}
	
	private DefaultWaitCallback<GeneracionCierreResultDto> getGeneracionCierreCallback() {
		if (generacionCierreCallback == null) {
			generacionCierreCallback = new DefaultWaitCallback<GeneracionCierreResultDto>() {
				public void success(GeneracionCierreResultDto result) {
					CerradoSSExitosoDialog.getInstance().hideLoading();
					if (!result.isError()) {
						final String rtfFileName = result.getRtfFileName();
						
						Command mostrarDialogCerrado = new Command() {
							
							public void execute() {
								MessageDialog.getInstance().hide();
								Command aceptar = null;
								if (cerrandoSolicitud) {
									aceptar = new Command() {
										public void execute() {
											History.newItem("");
											History.fireCurrentHistoryState();
										}
									};
								} else {
									aceptar = CerradoSSExitosoDialog.getCloseCommand();
								}
								CerradoSSExitosoDialog.getInstance().setAceptarCommand(aceptar);
								//MGR - #1415
								CerradoSSExitosoDialog.getInstance().showCierreExitoso(rtfFileName, editarSSUIData.getIdSolicitudServicio());
							}
						};
						
						editarSSUIData.setSaved(true);
						if(!result.getMessages().isEmpty()){
							StringBuilder msgString = new StringBuilder();
							for (MessageDto msg : result.getMessages()) {
								msgString.append("<span class=\"error\">- " + msg.getDescription()
										+ "</span><br>");
							}
							MessageDialog.getInstance().showAceptar("Aviso",msgString.toString(), mostrarDialogCerrado);
						}
						else{
							mostrarDialogCerrado.execute();
						}

					} else {
						ErrorDialog.getInstance().setDialogTitle("Aviso");
						StringBuilder msgString = new StringBuilder();
						for (MessageDto msg : result.getMessages()) {
							msgString.append("<span class=\"error\">- " + msg.getDescription()
									+ "</span><br>");
						}
						ErrorDialog.getInstance().show(msgString.toString(), false);
					}
				}

				public void failure(Throwable caught) {
					CerradoSSExitosoDialog.getInstance().hide();
					super.failure(caught);
				}
			};
		}
		return generacionCierreCallback;
	}

	private boolean validarCompletitud(boolean showErrorDialog) {
		List<String> errors = null;

		errors = editarSSUIData.validarCompletitud();
		if (!errors.isEmpty()) {
			validarCompletitud.addStyleName(validarCompletitudFailStyle);
			if (showErrorDialog) {
				ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
				ErrorDialog.getInstance().show(errors, false);
			}
		} else {
			validarCompletitud.removeStyleName(validarCompletitudFailStyle);
		}
		return errors.isEmpty();
	}

	private CerrarSSUI getCerrarSSUI() {
		if (cerrarSSUI == null) {
			cerrarSSUI = new CerrarSSUI();
			cerrarSSUI.setAceptarCommand(generarCerrarSolicitudCommand());
		}
		return cerrarSSUI;
	}

	public EditarSSUIData getEditarSSUIData() {
		return editarSSUIData;
	}

	public void getLineasSolicitudServicioInitializer(
			DefaultWaitCallback<LineasSolicitudServicioInitializer> defaultWaitCallback) {
		SolicitudRpcService.Util.getInstance().getLineasSolicitudServicioInitializer(
				editarSSUIData.getGrupoSolicitud(), defaultWaitCallback);
	}

	public void getListaPrecios(TipoSolicitudDto tipoSolicitudDto,
			DefaultWaitCallback<List<ListaPreciosDto>> defaultWaitCallback) {
		SolicitudRpcService.Util.getInstance().getListasDePrecios(tipoSolicitudDto, defaultWaitCallback);
	}

	public void getPlanesPorItemYTipoPlan(ItemSolicitudTasadoDto itemSolicitudTasado, TipoPlanDto tipoPlan,
			DefaultWaitCallback<List<PlanDto>> callback) {
		SolicitudRpcService.Util.getInstance().getPlanesPorItemYTipoPlan(itemSolicitudTasado, tipoPlan,
				editarSSUIData.getCuentaId(), callback);
	}

	public void getServiciosAdicionales(LineaSolicitudServicioDto linea,
			DefaultWaitCallback<List<ServicioAdicionalLineaSolicitudServicioDto>> defaultWaitCallback) {
		SolicitudRpcService.Util.getInstance().getServiciosAdicionales(linea, editarSSUIData.getCuentaId(),
				defaultWaitCallback);
	}

	public String getNombreProximoMovil() {
		return editarSSUIData.getNombreMovil();
	}

	public void reservarNumeroTelefonico(long numero, long idTipoTelefonia, long idModalidadCobro,
			long idLocalidad, DefaultWaitCallback<ResultadoReservaNumeroTelefonoDto> callback) {
		SolicitudRpcService.Util.getInstance().reservarNumeroTelefonico(numero, idTipoTelefonia,
				idModalidadCobro, idLocalidad, callback);
	}

	public void desreservarNumeroTelefonico(long numero, Long idLineaSolicitudServicio,
			DefaultWaitCallback callback) {
		SolicitudRpcService.Util.getInstance().desreservarNumeroTelefono(numero, idLineaSolicitudServicio,
				callback);
	}

	public void getModelos(String imei, Long idTipoSolicitud, Long idListaPrecios,
			DefaultWaitCallback<List<ModeloDto>> callback) {
		SolicitudRpcService.Util.getInstance().getModelos(imei, idTipoSolicitud, idListaPrecios, callback);
	}

	public void verificarNegativeFiles(String numero, DefaultWaitCallback<String> callback) {
		SolicitudRpcService.Util.getInstance().verificarNegativeFiles(numero, callback);
	}

	public static String getEditarSSUrl(Long idCuenta, Long idGrupo) {
		StringBuilder builder = new StringBuilder(UILoader.AGREGAR_SOLICITUD + "?");
		if (idCuenta != null && idCuenta > 0)
			builder.append(EditarSSUI.ID_CUENTA + "=" + idCuenta + "&");
		builder.append(EditarSSUI.ID_GRUPO_SS + "=" + idGrupo);
		return builder.toString();
	}

	public static String getEditarSSUrl(Long idCuentaPotencial, Long idGrupo, String codigoVanvite,
			Long idCuenta) {
		StringBuilder builder = new StringBuilder(UILoader.AGREGAR_SOLICITUD + "?");
		if (idCuentaPotencial != null)
			builder.append(EditarSSUI.ID_CUENTA_POTENCIAL + "=" + idCuentaPotencial + "&");
		if (codigoVanvite != null)
			builder.append(EditarSSUI.CODIGO_VANTIVE + "=" + codigoVanvite + "&");
		if (idCuenta != null && idCuenta > 0)
			builder.append(EditarSSUI.ID_CUENTA + "=" + idCuenta + "&");
		if (idGrupo != null)
			builder.append(EditarSSUI.ID_GRUPO_SS + "=" + idGrupo);
		return builder.toString();
	}

	public void borrarDescuentoSeleccionados() {
		datos.borrarDescuentoSeleccionados();
	}

	public void getPlanesPorTipoPlan(TipoPlanDto tipoPlan,
			DefaultWaitCallback<List<PlanDto>> callback) {
		SolicitudRpcService.Util.getInstance().getPlanesPorTipoPlan(tipoPlan.getId(), editarSSUIData.getCuentaId(), callback);
	}

	public void getServiciosAdicionalesContrato(Long idPlan,
			DefaultWaitCallback<List<ServicioAdicionalIncluidoDto>> callback) {
		SolicitudRpcService.Util.getInstance().getServiciosAdicionalesContrato(idPlan, callback);
	}
	
	/**
	 * Devuelve la SS de Transferencia con todos los datos ingresados
	 */
	private SolicitudServicioDto obtenerSolicitudTransferencia(boolean cerrandoSS){
		SolicitudServicioDto ssDto = editarSSUIData.getSolicitudServicioTranferencia();
		
		if(cerrandoSS){
			
			VendedorDto vendLog = ClientContext.getInstance().getVendedor();
			//MGR - #1453 - Se modifica la logica al cerrar tanto para la cuenta como para la ss
			//Logica para asignarle vendedor a la cuenta
			//El vendedor de la cuenta solo se cambia si la cuenta es prospect
			if(!ssDto.getCuenta().isCliente()){
				if(vendLog.isAP()){
					ssDto.getCuenta().setVendedor(datosTranferencia.getCtaCedenteDto().getVendedor());
				
				}else if(vendLog.isADMCreditos()){
					VendedorDto vendCombo = (VendedorDto) editarSSUIData.getVendedor().getSelectedItem();
					
					if(vendCombo.isAP() || 
							vendCombo.getId().equals(knownInstancias.get(VENDEDOR_NO_COMISIONABLE))){
						ssDto.getCuenta().setVendedor(datosTranferencia.getCtaCedenteDto().getVendedor());
					
					}else { //En el combo un dealer o eecc
						ssDto.getCuenta().setVendedor(vendCombo);
					}
					
				}else { //Es dealer
					ssDto.getCuenta().setVendedor(vendLog);
				}
			}
			
			//Logica para asignarle vendedor a la solicitud de servicio
			//En este caso no importa si la cuenta es prospect o no
			if(vendLog.isAP()){
				if(knownInstancias != null){
					VendedorDto vendAuxDto = new VendedorDto();
					vendAuxDto.setId(knownInstancias.get(VENDEDOR_NO_COMISIONABLE));
					ssDto.setVendedor(vendAuxDto);
				
				}
			}else if(vendLog.isADMCreditos()){
				VendedorDto vendCombo = (VendedorDto) editarSSUIData.getVendedor().getSelectedItem();
				
				if(vendCombo.isAP() || 
						vendCombo.getId().equals(knownInstancias.get(VENDEDOR_NO_COMISIONABLE))){
					VendedorDto vendAuxDto = new VendedorDto();
					vendAuxDto.setId(knownInstancias.get(VENDEDOR_NO_COMISIONABLE));
					ssDto.setVendedor(vendAuxDto);
				
				}else { //En el combo un dealer o eecc
					ssDto.setVendedor(vendCombo);
				}
				
			}else{ //Es dealer
				ssDto.setVendedor(vendLog);
			}
		}
//		datosTranferencia.actualizarActivosVisibles();
		ssDto.setCuentaCedente(datosTranferencia.getCtaCedenteDto());
		ssDto.setContratosCedidos(datosTranferencia.getContratosSSChequeados());
		return ssDto;
	}

	public void getContratoViewInitializer(
			DefaultWaitCallback<ContratoViewInitializer> defaultWaitCallback) {
		SolicitudRpcService.Util.getInstance().getContratoViewInitializer(
				editarSSUIData.getGrupoSolicitud(), defaultWaitCallback);
	}
	
	//------------------------------------------
	
	//Se llama al querer guardar una SS de Transferencia si todas las validacioens salieron bien
	private DefaultWaitCallback<List<String>> guardarSolicitudCallback(){
		
		DefaultWaitCallback<List<String>> callback = new DefaultWaitCallback<List<String>>() {
			@Override
			public void success(List<String> errors) {
				if (errors.isEmpty()) {
					guardar();
					editarSSUIData.setSaved(true);
				//MGR - #1481
				}else if(ClientContext.getInstance().getVendedor().isADMCreditos()){
					
					Command guardarConAviso = new Command() {
						
						public void execute() {
							MessageDialog.getInstance().hide();
							guardar();
							editarSSUIData.setSaved(true);
						}
					};
					
					StringBuilder msgString = new StringBuilder();
					for (String error : errors) {
						msgString.append("<span>" + error + "</span><br>");
					}
					MessageDialog.getInstance().showAceptar("Aviso",msgString.toString(), guardarConAviso);
				}else{
					ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
					ErrorDialog.getInstance().show(errors, false);
				}
			}
		};
		return callback;
	}
	
	private void cerrarGenerarSolicitud(){
		SolicitudServicioDto ssDto = null;
		String pinMaestro = getCerrarSSUI().getCerrarSSUIData().getPin().getText();
		if(editarSSUIData.getGrupoSolicitud()!= null &&
				editarSSUIData.getGrupoSolicitud().isTransferencia()){
			ssDto = obtenerSolicitudTransferencia(true);
		}else{
			ssDto =editarSSUIData.getSolicitudServicio();
			//El vendedor de la cuenta solo se cambia si la cuenta es prospect y el usuario logueado es Administrador de creditos
			if(isProspectAndADMCreditos(ssDto.getCuenta())) {
				ssDto.getCuenta().setVendedor((VendedorDto) editarSSUIData.getVendedor().getSelectedItem());						
			}		
		}
		
		SolicitudRpcService.Util.getInstance().generarCerrarSolicitud(
				ssDto, pinMaestro, cerrandoSolicitud,
				getGeneracionCierreCallback());
	}

	private DefaultWaitCallback<List<String>> abrirCerrarDialogCallback(){

		DefaultWaitCallback<List<String>> callback = new DefaultWaitCallback<List<String>>() {
			@Override
			public void success(List<String> errors) {
				if (errors.isEmpty()) {
					abrirDialogCerrar();
					//MGR - #1481
				} else if(ClientContext.getInstance().getVendedor().isADMCreditos()){
					
					Command abrirDialogCerrarConAviso = new Command() {
						
						public void execute() {
							MessageDialog.getInstance().hide();
							abrirDialogCerrar();
						}
					};
					
					StringBuilder msgString = new StringBuilder();
					for (String error : errors) {
						msgString.append("<span>" + error + "</span><br>");
					}
					MessageDialog.getInstance().showAceptar("Aviso",msgString.toString(), abrirDialogCerrarConAviso);
				}else{
					CerradoSSExitosoDialog.getInstance().hideLoading();
					ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
					ErrorDialog.getInstance().show(errors, false);
				}
			}
		};
		return callback;
	}
	
	private void abrirDialogCerrar(){
		cerrandoSolicitud = cerrandoAux;
        getCerrarSSUI().setTitleCerrar(cerrandoAux);
        getCerrarSSUI().show(editarSSUIData.getCuenta().getPersona(),
        editarSSUIData.getCuenta().isCliente(), editarSSUIData.getSolicitudServicioGeneracion(),
        editarSSUIData.isCDW(), editarSSUIData.isMDS(), editarSSUIData.hasItemBB(), editarSSUIData.isTRANSFERENCIA());
	}
	

	//Evaluacion si la cuenta es prospect , el usuario logueado es Administrador de creditos y fue elegido un vendedor
	private boolean isProspectAndADMCreditos(CuentaSSDto cuentaDto) {
		return !cuentaDto.isCliente() &&
		       ClientContext.getInstance().getVendedor().isADMCreditos() &&
		       editarSSUIData.getVendedor().getSelectedItem() != null;
	}	
	
	
}
