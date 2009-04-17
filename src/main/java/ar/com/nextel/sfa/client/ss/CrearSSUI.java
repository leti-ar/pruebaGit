package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioRequestDto;
import ar.com.nextel.sfa.client.util.HistoryUtils;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.TabPanel;

public class CrearSSUI extends ApplicationUI {

	public static final String ID_CUENTA = "idCuenta";

	protected boolean firstLoad = true;
	private TabPanel tabs;
	private DatosSSUI datos;
	private VariosSSUI varios;
	private CrearSSUIData crearSSUIData;

	public CrearSSUI() {
		super();
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
							crearSSUIData.setSolicitud(solicitud);
						}
					});
			mainPanel.setVisible(true);
		}
	}

	private void init() {
		tabs = new TabPanel();
		mainPanel.add(tabs);
		crearSSUIData = new CrearSSUIData();
		tabs.add(datos = new DatosSSUI(crearSSUIData), "Datos");
		tabs.add(varios = new VariosSSUI(crearSSUIData), "Varios");
	}

	public void unload() {
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				crearSSUIData.clean();
			}
		});
	}

}
