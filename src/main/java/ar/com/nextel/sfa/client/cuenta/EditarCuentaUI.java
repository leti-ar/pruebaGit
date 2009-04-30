package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.util.HistoryUtils;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.nextel.sfa.client.widget.UILoader;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

public class EditarCuentaUI extends ApplicationUI {

	public EditarCuentaUI() {
		super();
	}

	public void load() {
		Long cuentaID = Long.parseLong(HistoryUtils.getParam("cuenta_id"));
		String cod_vantive = HistoryUtils.getParam("cod_vantive");
		final CuentaEdicionTabPanel cuentaTab = CuentaEdicionTabPanel.getInstance();
		boolean panelVisible = true;
		cuentaTab.clean();
		
		
		if (cuentaID!=null || cod_vantive!=null) {
			if("***".equals(cod_vantive)){
				panelVisible = false;
			}else{
				CuentaRpcService.Util.getInstance().selectCuenta(cuentaID, cod_vantive,new DefaultWaitCallback<CuentaDto>() {
					public void success(CuentaDto cuentaDto) {
						//datos tabPanel
						cuentaTab.setCliente(cuentaDto.getCodigoVantive());
						cuentaTab.setRazonSocial(cuentaDto.getPersona()!=null ? cuentaDto.getPersona().getRazonSocial():"");
						//carga info pestaña Datos
						cuentaTab.getCuentaDatosForm().cargarDatos(cuentaDto);
						//carga info pestaña Domicilio
						loadDomiciliosFormPanel(cuentaDto,cuentaTab);
					}
				});				
			}
		}
		if (!panelVisible){
			UILoader.getInstance().setPage(0);
			ErrorDialog.getInstance().show("No tiene permiso para ver esa cuenta.");
		}else{
			mainPanel.add(cuentaTab.getCuentaEdicionPanel());
		}
	}

	public void unload() {
	}

	/**
	 * @author eSalvador
	 **/
	private void loadDomiciliosFormPanel(CuentaDto cuentaDto, CuentaEdicionTabPanel cuentaTab) {
		if (cuentaDto.getPersona() != null) {
			// List<DomiciliosCuentaDto> domicilios;
			// domicilios = cuentaDto.getPersona().getDomicilios();
			// TODO: Sacar este HardCode FEO de pasarle la cuenta por parametro!!
			cuentaTab.getCuentaDomicilioForm().cargaTablaDomicilios(cuentaDto);
		}

	}

}
