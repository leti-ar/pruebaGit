package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.util.HistoryUtils;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.nextel.sfa.client.widget.UILoader;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

public class EditarCuentaUI extends ApplicationUI {

	private Boolean panelVisible = true;
	private final CuentaEdicionTabPanel cuentaTab = CuentaEdicionTabPanel.getInstance();
	
	public EditarCuentaUI() {
		super();
	}

	public void load() {
		Long cuentaID = Long.parseLong(HistoryUtils.getParam("cuenta_id"));
		String cod_vantive = HistoryUtils.getParam("cod_vantive");
		cuentaTab.clean();
		
		//Si la cuenta o el codigoVantive NO son nulos:
		if ((cuentaID!=null) || (!cod_vantive.equals("null"))) {
			//Si el codVantive es '***' o la cuenta es igual a 0:
			// lanza mensaje de error por permisos y pone invisible los paneles.
			if("***".equals(cod_vantive) || (cuentaID == 0)){
				//panelVisible = false;
				//UILoader.getInstance().clear();
				mainPanel.clear();
				new BuscarCuentaUI().load();
				//UILoader.getInstance().clear();
				UILoader.getInstance().setPage(0);
				ErrorDialog.getInstance().show("No tiene permiso para ver esa cuenta.");
			}else{
				// Si algunos de los dos tiene datos validos, carga los paneles.
					cargaPaneles(cuentaID,cod_vantive);
			}
		}
	}

	/**
	 * @author eSalvador 
	 **/
	private void cargaPaneles(Long cuentaID, String cod_vantive){
		//Busca la cuenta con alguno de los dos datos NO nulos.
		CuentaRpcService.Util.getInstance().selectCuenta(cuentaID, cod_vantive,new DefaultWaitCallback<CuentaDto>() {
			public void success(CuentaDto cuentaDto) {
			  //De todas formas, el servicio puede NO devolver ninguna cuenta: (Se pone el panel invisible!).
			  if (cuentaDto == null){
				  panelVisible = false;
				  mainPanel.clear();
				  new BuscarCuentaUI().load();
			  }else{
				if(cuentaDto.getCodigoVantive() != null){
					cuentaTab.setCliente(cuentaDto.getCodigoVantive());
				}else{
					cuentaTab.setCliente("***");
				}
				cuentaTab.setRazonSocial(cuentaDto.getPersona()!=null ? cuentaDto.getPersona().getRazonSocial():"");
				//carga info pestaña Datos
				cuentaTab.getCuentaDatosForm().cargarDatos(cuentaDto);
				//carga info pestaña Domicilio
				if (cuentaDto.getPersona() != null) {
					cuentaTab.getCuentaDomicilioForm().cargaTablaDomicilios(cuentaDto);
				}
				mainPanel.add(cuentaTab.getCuentaEdicionPanel());
			  }
			}
		});
	}

	public void unload() {
	}

	/**
	 * @author eSalvador
	 **/
//	private void loadDomiciliosFormPanel(CuentaDto cuentaDto, CuentaEdicionTabPanel cuentaTab) {
//		if (cuentaDto.getPersona() != null) {
//			// List<DomiciliosCuentaDto> domicilios;
//			// domicilios = cuentaDto.getPersona().getDomicilios();
//			// TODO: Esta MAL pasarle la cuenta por parametro!??!
//			cuentaTab.getCuentaDomicilioForm().cargaTablaDomicilios(cuentaDto);
//		}
//	}

}
