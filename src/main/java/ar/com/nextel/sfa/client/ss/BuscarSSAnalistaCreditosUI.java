package ar.com.nextel.sfa.client.ss;

import java.util.HashMap;

import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;

/**
 * Esta clase es creada para poder realizar la busqueda de las SS si el usuario logueado 
 * es Analista de Creditos, y se podra filtrar por las SS del mismo y de todos los 
 * analista de creditos que estén en la misma sucursal.
 * @author fernaluc
 *
 */

public class BuscarSSAnalistaCreditosUI extends BuscarSSGenericoUI {
	
	BuscarSSAnalistaCreditosFilterUI buscadorSSCerradasFilterForm;

	public BuscarSSAnalistaCreditosUI(){
		super();
	}

	@Override
	public void firstLoad() {
		buscarSSCerradasResultPanel = new BuscarSSCerradasResultUI(this);		
		if(!ClientContext.getInstance().soyClienteNexus()) {			
			buscadorSSCerradasFilterForm = new BuscarSSAnalistaCreditosFilterUI();
			buscadorSSCerradasFilterForm.setBuscarCuentaResultPanel(buscarSSCerradasResultPanel);
			mainPanel.add(buscadorSSCerradasFilterForm);
		}
		
		super.firstLoad();	
	}

	@Override
	public boolean load() {
		// Si es cliente de Nexus no aparecerá ningun filtro para la busqueda, se busca directamente todas las SS 
		// que correspondan al usuario y a los analistas de creditos de la misma sucursal.
		if(ClientContext.getInstance().vengoDeNexus() && ClientContext.getInstance().soyClienteNexus()) {	
			SolicitudServicioCerradaDto solicitudServicioCerradaDto = new SolicitudServicioCerradaDto();
			solicitudServicioCerradaDto.setIdSucursal(ClientContext.getInstance().getVendedor().getIdSucursal());
			solicitudServicioCerradaDto.setIdCuenta(new Long(1782538));//Long.parseLong(ClientContext.getInstance().getClienteNexus().getCustomerId()));
			buscarSSCerradasResultPanel.searchSolicitudesServicio(solicitudServicioCerradaDto);
			exportarExcelSSResultUI.setVisible(true);
			buscarSSCerradasResultPanel.setVisible(true);
		}else {
			exportarExcelSSResultUI.setVisible(false);
			buscarSSCerradasResultPanel.setVisible(false);
		}
		
		buscarSSTotalesResultUI.setVisible(false);
		cambiosSSCerradasResultUI.setVisible(false);
		
		return true;
	}

	@Override
	public boolean esAnalistaCreditos() {
		return true;
	}
	
}
