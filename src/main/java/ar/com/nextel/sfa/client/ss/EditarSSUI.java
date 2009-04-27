package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioRequestDto;
import ar.com.nextel.sfa.client.initializer.SolicitudInitializer;
import ar.com.nextel.sfa.client.util.HistoryUtils;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.RazonSocialClienteBar;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class EditarSSUI extends ApplicationUI implements ClickListener {

	public static final String ID_CUENTA = "idCuenta";

	protected boolean firstLoad = true;
	private TabPanel tabs;
	private DatosSSUI datos;
	private VariosSSUI varios;
	private EditarSSUIData editarSSUIData;
	private FormButtonsBar formButtonsBar;
	private RazonSocialClienteBar razonSocialClienteBar;

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
			//solicitudServicioRequestDto.setNumeroCuenta(numeroCuenta);
			solicitudServicioRequestDto.setIdGrupoSolicitud(1l);
			SolicitudRpcService.Util.getInstance().createSolicitudServicio(solicitudServicioRequestDto,
					new DefaultWaitCallback<SolicitudServicioDto>() {
						public void success(SolicitudServicioDto solicitud) {
//							if(solicitud.ge)
//							razonSocialClienteBar
							editarSSUIData.setSolicitud(solicitud);
						}
					});
			mainPanel.setVisible(true);
		}
	}

	private void init() {
		razonSocialClienteBar  = new RazonSocialClienteBar();
		mainPanel.add(razonSocialClienteBar);
		
		Button validarCompletitud = new Button("Validar Completitud");
		validarCompletitud.addStyleName("validarCompletitudButton");
		mainPanel.add(validarCompletitud);
		tabs = new TabPanel();
		tabs.setWidth("98%");
		tabs.addStyleName("mlr5 mb10 mt5");
		mainPanel.add(tabs);
		editarSSUIData = new EditarSSUIData();
		tabs.add(datos = new DatosSSUI(editarSSUIData), "Datos");
		tabs.add(varios = new VariosSSUI(editarSSUIData), "Varios");
		tabs.selectTab(0);
		SolicitudRpcService.Util.getInstance().getSolicitudInitializer(new DefaultWaitCallback<SolicitudInitializer>(){
			public void success(SolicitudInitializer initializer) {
				loadInitializer(initializer);
			};
		});
		formButtonsBar = new FormButtonsBar();
		mainPanel.add(formButtonsBar);
		formButtonsBar.addStyleName("mt10");
		SimpleLink guardarButton = new SimpleLink("Guardar");
		SimpleLink cancelarButton = new SimpleLink("Cancelar");
		formButtonsBar.addLink(guardarButton);
		formButtonsBar.addLink(new SimpleLink("^SS"));
		formButtonsBar.addLink(cancelarButton);
		guardarButton.addClickListener(this);
	}
	
	private void loadInitializer(SolicitudInitializer initializer){
		editarSSUIData.getOrigen().addAllItems(initializer.getOrigenesSolicitud());
		editarSSUIData.getAnticipo().addAllItems(initializer.getTiposAnticipo());
	}

	public void unload() {
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				editarSSUIData.clean();
			}
		});
	}

	public void onClick(Widget sender) {
		
	}
	
	private void guardar(){
		SolicitudRpcService.Util.getInstance().saveSolicituServicio(new SolicitudServicioDto(), new DefaultWaitCallback<SolicitudServicioDto>(){
			public void success(SolicitudServicioDto result) {
				
			}
		});
	}
}
