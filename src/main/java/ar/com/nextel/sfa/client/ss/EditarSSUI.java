package ar.com.nextel.sfa.client.ss;

import java.util.List;

import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.GeneracionCierreResultDto;
import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.dto.ItemSolicitudTasadoDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.ListaPreciosDto;
import ar.com.nextel.sfa.client.dto.ModeloDto;
import ar.com.nextel.sfa.client.dto.PlanDto;
import ar.com.nextel.sfa.client.dto.ResultadoReservaNumeroTelefonoDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalLineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioRequestDto;
import ar.com.nextel.sfa.client.dto.TipoPlanDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.initializer.LineasSolicitudServicioInitializer;
import ar.com.nextel.sfa.client.initializer.SolicitudInitializer;
import ar.com.nextel.sfa.client.util.HistoryUtils;
import ar.com.nextel.sfa.client.util.MessageUtils;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.RazonSocialClienteBar;
import ar.com.nextel.sfa.client.widget.UILoader;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class EditarSSUI extends ApplicationUI implements ClickListener, EditarSSUIController {

	public static final String ID_CUENTA = "idCuenta";
	public static final String ID_GRUPO_SS = "idGrupoSS";
	private static final String validarCompletitudFailStyle = "validarCompletitudFailButton";

	private TabPanel tabs;
	private DatosSSUI datos;
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
	private GenerarSSUI generarSSUI;
	private boolean cerrandoSolicitud;

	public EditarSSUI() {
		super();
		addStyleName("Gwt-EditarSSUI");
	}

	public boolean load() {
		tokenLoaded = History.getToken();
		String cuenta = HistoryUtils.getParam(ID_CUENTA);
		String grupoSS = HistoryUtils.getParam(ID_GRUPO_SS);
		mainPanel.setVisible(false);
		if (cuenta == null) {
			ErrorDialog.getInstance().show("No ingreso la cuenta para la cual desea cargar la solicitud");
		} else {
			SolicitudServicioRequestDto solicitudServicioRequestDto = new SolicitudServicioRequestDto();
			solicitudServicioRequestDto.setIdCuenta(Long.parseLong(cuenta));
			solicitudServicioRequestDto.setIdCuentaPotencial(null);
			// solicitudServicioRequestDto.setNumeroCuenta(numeroCuenta);
			if (grupoSS != null) {
				solicitudServicioRequestDto.setIdGrupoSolicitud(Long.parseLong(grupoSS));
			} else {
				solicitudServicioRequestDto.setIdGrupoSolicitud(GrupoSolicitudDto.ID_EQUIPOS_ACCESORIOS);
			}
			SolicitudRpcService.Util.getInstance().createSolicitudServicio(solicitudServicioRequestDto,
					new DefaultWaitCallback<SolicitudServicioDto>() {
						public void success(SolicitudServicioDto solicitud) {
							editarSSUIData.setSaved(true);
							razonSocialClienteBar.setCliente(solicitud.getCuenta().getCodigoVantive());
							razonSocialClienteBar.setRazonSocial(solicitud.getCuenta().getPersona()
									.getRazonSocial());
							razonSocialClienteBar.setIdCuenta(solicitud.getCuenta().getId(), solicitud
									.getCuenta().getIdVantive());
							editarSSUIData.setSolicitud(solicitud);
							validarCompletitud(false);
							datos.refresh();
							mainPanel.setVisible(true);
						}

						public void failure(Throwable caught) {
							History.back();
							super.failure(caught);
						}
					});
			editarSSUIData.clean();
		}
		return true;
	}

	public void firstLoad() {
		razonSocialClienteBar = new RazonSocialClienteBar();
		mainPanel.add(razonSocialClienteBar);

		validarCompletitud = new Button("Validar Completitud");
		validarCompletitud.addStyleName("validarCompletitudButton");
		validarCompletitud.addClickListener(this);
		mainPanel.add(validarCompletitud);
		tabs = new TabPanel();
		tabs.setWidth("98%");
		tabs.addStyleName("mlr5 mb10 mt5");
		mainPanel.add(tabs);
		editarSSUIData = new EditarSSUIData();
		tabs.add(datos = new DatosSSUI(this), "Datos");
		tabs.add(varios = new VariosSSUI(this), "Varios");
		tabs.selectTab(0);
		tabs.addTabListener(new TabListener() {
			public void onTabSelected(SourcesTabEvents tab, int index) {
				if (index == 1) {
					varios.refresh();
				}
			}

			public boolean onBeforeTabSelected(SourcesTabEvents arg0, int arg1) {
				return true;
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

		FlowPanel linksCrearSS = new FlowPanel();
		generarSolicitud = new SimpleLink("Generar");
		cerrarSolicitud = new SimpleLink("Cerrar");
		linksCrearSS.add(wrap(generarSolicitud));
		linksCrearSS.add(wrap(cerrarSolicitud));
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
		editarSSUIData.getAnticipo().addAllItems(initializer.getTiposAnticipo());
	}

	public boolean unload(String token) {
		if (!editarSSUIData.isSaved() && !tokenLoaded.equals(token)) {
			MessageDialog.getInstance().showSiNoCancelar(Sfa.constant().guardar(),
					Sfa.constant().MSG_PREGUNTA_GUARDAR(), new SaveSSCommand(true, token),
					new SaveSSCommand(false, token), MessageDialog.getCloseCommand());
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
			List errors = null;
			if (save) {
				errors = editarSSUIData.validarParaGuardar();
				if (errors.isEmpty()) {
					guardar();
				} else {
					ErrorDialog.getInstance().show(errors);
				}
			}
			editarSSUIData.setSaved(true);
			// Continuo a la página a la que me dirigía.
			History.newItem(token);
			MessageDialog.getInstance().hide();
		}
	}

	public void onClick(Widget sender) {
		if (sender == guardarButton) {
			List errors = editarSSUIData.validarParaGuardar();
			if (errors.isEmpty()) {
				guardar();
			} else {
				ErrorDialog.getInstance().show(errors);
			}
		} else if (sender == cancelarButton) {
			History.newItem("");
		} else if (sender == validarCompletitud) {
			validarCompletitud(true);
		} else if (sender == acionesSS) {
			generarCerrarMenu.show();
			generarCerrarMenu.setPopupPosition(acionesSS.getAbsoluteLeft() - 10,
					acionesSS.getAbsoluteTop() - 50);
		} else if (sender == generarSolicitud || sender == cerrarSolicitud) {
			generarCerrarMenu.hide();
			openGenerarCerrarSolicitdDialog(sender == cerrarSolicitud);
		}
	}

	private void guardar() {
		SolicitudRpcService.Util.getInstance().saveSolicituServicio(editarSSUIData.getSolicitudServicio(),
				new DefaultWaitCallback<SolicitudServicioDto>() {
					public void success(SolicitudServicioDto result) {
						editarSSUIData.setSolicitud(result);
						datos.refresh();
						MessageDialog.getInstance().showAceptar("Guardado Exitoso",
								Sfa.constant().MSG_SOLICITUD_GUARDADA_OK(), MessageDialog.getCloseCommand());
						editarSSUIData.setSaved(true);
					}
				});
	}

	private void openGenerarCerrarSolicitdDialog(boolean cerrando) {
		List errors = editarSSUIData.validarParaCerrarGenerar(cerrando);
		if (errors.isEmpty()) {
			cerrandoSolicitud = cerrando;
			getGenerarSSUI().setTitleCerrar(cerrando);
			getGenerarSSUI().show(editarSSUIData.getCuenta().getPersona(),
					editarSSUIData.getSolicitudServicioGeneracion());
		} else {
			ErrorDialog.getInstance().show(errors);
		}
	}

	private Command generarCerrarSolicitudCommand() {
		return new Command() {
			public void execute() {
				editarSSUIData.setSolicitudServicioGeneracion(getGenerarSSUI().getGenerarSSUIData()
						.getSolicitudServicioGeneracion());
				CerrarSSDialog.getInstance().showLoading(cerrandoSolicitud);
				List errors = editarSSUIData.validarParaCerrarGenerar(true);
				if (errors.isEmpty()) {
					SolicitudRpcService.Util.getInstance().generarCerrarSolicitud(
							editarSSUIData.getSolicitudServicio(), "", cerrandoSolicitud,
							getGeneracionCierreCallback());
				} else {
					CerrarSSDialog.getInstance().hide();
					ErrorDialog.getInstance().setDialogTitle(ErrorDialog.ERROR);
					ErrorDialog.getInstance().show(errors, false);
				}
			}
		};
	}

	private DefaultWaitCallback<GeneracionCierreResultDto> getGeneracionCierreCallback() {
		if (generacionCierreCallback == null) {
			generacionCierreCallback = new DefaultWaitCallback<GeneracionCierreResultDto>() {
				public void success(GeneracionCierreResultDto result) {
					CerrarSSDialog.getInstance().hide();
					if (!result.isError()) {
						editarSSUIData.setSaved(true);
						Command aceptar = null;
						if (cerrandoSolicitud) {
							aceptar = new Command() {
								public void execute() {
									History.newItem("");
									History.fireCurrentHistoryState();
								}
							};
						} else {
							aceptar = CerrarSSDialog.getCloseCommand();
						}
						CerrarSSDialog.getInstance().setAceptarCommand(aceptar);
						CerrarSSDialog.getInstance().showCierreExitoso(result.getRtfFileName());
					} else {
						ErrorDialog.getInstance().setDialogTitle(ErrorDialog.ERROR);
						ErrorDialog.getInstance().show(MessageUtils.getMessagesHTML(result.getMessages()));
					}
				}

				public void failure(Throwable caught) {
					CerrarSSDialog.getInstance().hide();
					super.failure(caught);
				}
			};
		}
		return generacionCierreCallback;
	}

	private boolean validarCompletitud(boolean showErrorDialog) {
		List<String> errors = editarSSUIData.validarCompletitud();
		if (!errors.isEmpty()) {
			validarCompletitud.addStyleName(validarCompletitudFailStyle);
			if (showErrorDialog) {
				ErrorDialog.getInstance().setDialogTitle(ErrorDialog.ERROR);
				ErrorDialog.getInstance().show(errors, false);
			}
		} else {
			validarCompletitud.removeStyleName(validarCompletitudFailStyle);
		}
		return errors.isEmpty();
	}

	private GenerarSSUI getGenerarSSUI() {
		if (generarSSUI == null) {
			generarSSUI = new GenerarSSUI();
			generarSSUI.setAceptarCommand(generarCerrarSolicitudCommand());
		}
		return generarSSUI;
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

	public void desreservarNumeroTelefonico(long numero, DefaultWaitCallback callback) {
		SolicitudRpcService.Util.getInstance().desreservarNumeroTelefono(numero, callback);
	}

	public void getModelos(String imei, Long idTipoSolicitud, Long idListaPrecios,
			DefaultWaitCallback<List<ModeloDto>> callback) {
		SolicitudRpcService.Util.getInstance().getModelos(imei, idTipoSolicitud, idListaPrecios, callback);
	}

	public void verificarNegativeFiles(String numero, DefaultWaitCallback<String> callback) {
		SolicitudRpcService.Util.getInstance().verificarNegativeFiles(numero, callback);
	}

	public static String getEditarSSUrl(long idCuenta, long idGrupo) {
		return UILoader.AGREGAR_SOLICITUD + "?" + EditarSSUI.ID_CUENTA + "=" + idCuenta + "&"
				+ EditarSSUI.ID_GRUPO_SS + "=" + idGrupo;
	}
}
