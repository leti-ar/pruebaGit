package ar.com.nextel.sfa.client.cuenta;

import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.util.HistoryUtils;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

public class EditarCuentaUI extends ApplicationUI {

	public EditarCuentaUI() {
		super();
	}
	
	public void load() {
		String cuentaID = HistoryUtils.getParam("cuenta_id");
		String cod_vantive = HistoryUtils.getParam("cod_vantive");
		final CuentaEdicionTabPanel cuentaTab = CuentaEdicionTabPanel.getInstance();
		cuentaTab.clean();
		if (cuentaID!=null||cod_vantive!=null) {
			CuentaRpcService.Util.getInstance().selectCuenta(new Long(1), cod_vantive,new DefaultWaitCallback<CuentaDto>() {
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
						loadDomiciliosFormPanel(cuentaDto,cuentaTab);
					}
				}
			});
		}
		mainPanel.add(cuentaTab.getCuentaEdicionPanel());
	}

	public void unload() {
	}

	/**
	 * @author eSalvador
	 **/
	private void loadDomiciliosFormPanel(CuentaDto cuentaDto, CuentaEdicionTabPanel cuentaTab){
		if (cuentaDto.getPersona() != null){
			 List<DomiciliosCuentaDto> domicilios;
			domicilios = cuentaDto.getPersona().getDomicilios();
			cuentaTab.getCuentaDomicilioForm().cargaTablaDomicilios(domicilios);
		}
		
	}
	
}

