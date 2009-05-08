package ar.com.nextel.sfa.client.ss;

import java.util.List;

import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.dto.ListaPreciosDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioRequestDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.initializer.LineasSolicitudServicioInitializer;
import ar.com.nextel.sfa.client.initializer.SolicitudInitializer;
import ar.com.nextel.sfa.client.util.HistoryUtils;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.RazonSocialClienteBar;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class EditarSSUI extends ApplicationUI implements ClickListener, EditarSSUIController {

	public static final String ID_CUENTA = "idCuenta";

	protected boolean firstLoad = true;
	private TabPanel tabs;
	private DatosSSUI datos;
	private VariosSSUI varios;
	private EditarSSUIData editarSSUIData;
	private FormButtonsBar formButtonsBar;
	private RazonSocialClienteBar razonSocialClienteBar;
	private SimpleLink guardarButton;
	private SimpleLink cancelarButton;
	private Button validarCompletitud;
	private static final String validarCompletitudFailStyle = "validarCompletitudFailButton";

	public EditarSSUI() {
		super();
		addStyleName("Gwt-EditarSSUI");
	}

	public void load() {
		if (firstLoad) {
			firstLoad = false;
			init();
		}
		String cuenta = HistoryUtils.getParam(ID_CUENTA);
		if (cuenta == null) {
			mainPanel.setVisible(false);
			ErrorDialog.getInstance().show("No ingreso la cuenta para la cual desea cargar la solicitud");
		} else {
			SolicitudServicioRequestDto solicitudServicioRequestDto = new SolicitudServicioRequestDto();
			solicitudServicioRequestDto.setIdCuenta(Long.parseLong(cuenta));
			solicitudServicioRequestDto.setIdCuentaPotencial(null);
			// solicitudServicioRequestDto.setNumeroCuenta(numeroCuenta);
			solicitudServicioRequestDto.setIdGrupoSolicitud(1l);
			SolicitudRpcService.Util.getInstance().createSolicitudServicio(solicitudServicioRequestDto,
					new DefaultWaitCallback<SolicitudServicioDto>() {
						public void success(SolicitudServicioDto solicitud) {
							// if(solicitud.ge)
							// razonSocialClienteBar
							editarSSUIData.setSolicitud(solicitud);
							validarCompletitud(false);
						}
					});
			editarSSUIData.clean();
			mainPanel.setVisible(true);
		}
	}

	private void init() {
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
		SolicitudRpcService.Util.getInstance().getSolicitudInitializer(
				new DefaultWaitCallback<SolicitudInitializer>() {
					public void success(SolicitudInitializer initializer) {
						loadInitializer(initializer);
					};
				});
		formButtonsBar = new FormButtonsBar();
		mainPanel.add(formButtonsBar);
		formButtonsBar.addStyleName("mt10");
		guardarButton = new SimpleLink("Guardar");
		cancelarButton = new SimpleLink("Cancelar");
		formButtonsBar.addLink(guardarButton);
		formButtonsBar.addLink(new SimpleLink("^SS"));
		formButtonsBar.addLink(cancelarButton);
		guardarButton.addClickListener(this);
		cancelarButton.addClickListener(this);
	}

	private void loadInitializer(SolicitudInitializer initializer) {
		editarSSUIData.getOrigen().addAllItems(initializer.getOrigenesSolicitud());
		editarSSUIData.getAnticipo().addAllItems(initializer.getTiposAnticipo());
	}

	public void unload() {
	}

	public void onClick(Widget sender) {
		if (sender == guardarButton) {
			guardar();
		} else if (sender == cancelarButton) {
			History.newItem("");
			History.fireCurrentHistoryState();
		} else if (sender == validarCompletitud) {
			validarCompletitud(true);
		}
	}

	private void guardar() {
		SolicitudRpcService.Util.getInstance().saveSolicituServicio(editarSSUIData.getSolicitudServicio(),
				new DefaultWaitCallback<SolicitudServicioDto>() {
					public void success(SolicitudServicioDto result) {
						Window.alert("Se ha guardado la solicitud con exito");
					}
				});
	}

	private void validarCompletitud(boolean showErrorDialog) {
		List<String> errors = editarSSUIData.validarCompletitud();
		if (!errors.isEmpty()) {
			validarCompletitud.addStyleName(validarCompletitudFailStyle);
			if (showErrorDialog) {
				ErrorDialog.getInstance().show(errors);
			}
		} else {
			validarCompletitud.removeStyleName(validarCompletitudFailStyle);
		}
	}

	public EditarSSUIData getEditarSSUIData() {
		return editarSSUIData;
	}

	public void getLineasSolicitudServicioInitializer(
			DefaultWaitCallback<LineasSolicitudServicioInitializer> defaultWaitCallback) {
		SolicitudRpcService.Util.getInstance().getLineasSolicitudServicioInitializer(null,
				defaultWaitCallback);

	}

	public void getListaPrecios(TipoSolicitudDto tipoSolicitudDto,
			DefaultWaitCallback<ListaPreciosDto> defaultWaitCallback) {

	}
}
