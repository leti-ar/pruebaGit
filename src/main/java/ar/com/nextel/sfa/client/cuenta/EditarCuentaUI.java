package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.util.HistoryUtils;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

public class EditarCuentaUI extends ApplicationUI {

	public EditarCuentaUI() {
		super();
	}
	
	public void load() {
		String cuentaID = HistoryUtils.getParam("cuenta_id");
		final CuentaEdicionTabPanel cuentaTab = CuentaEdicionTabPanel.getInstance();
		cuentaTab.clean();
		if (cuentaID!=null) {
			CuentaRpcService.Util.getInstance().selectCuenta(new Long(cuentaID), new DefaultWaitCallback<CuentaDto>() {
				public void success(CuentaDto cuentaDto) {
					cuentaTab.setCliente(cuentaDto.getCodigoVantive());
					if (cuentaDto.getPersona()!=null) {
						cuentaTab.setRazonSocial(cuentaDto.getPersona().getRazonSocial());
						if (cuentaDto.getPersona().getDocumento()!=null) {
							cuentaTab.getCuentaDatosForm().getCuentaEditor().getTipoDocumento().setItemSelected(cuentaDto.getPersona().getIdTipoDocumento().intValue(), true) ;
							cuentaTab.getCuentaDatosForm().getCuentaEditor().getNumeroDocumento().setText(cuentaDto.getPersona().getDocumento().getNumero());
						}
						cuentaTab.getCuentaDatosForm().getCuentaEditor().getApellido().setText(cuentaDto.getPersona().getApellido());
						cuentaTab.getCuentaDatosForm().getCuentaEditor().getRazonSocial().setText(cuentaDto.getPersona().getRazonSocial());
					}
				}
			});
		}
		mainPanel.add(cuentaTab.getCuentaEdicionPanel());
	}

	public void unload() {
	}

}
