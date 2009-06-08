package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.dto.CuentaSearchDto;

/**
 * @author eSalvador
 **/
public interface BuscarCuentaController {

	public void searchCuentas(CuentaSearchDto cuentaSearchDto);
	
	public void setResultadoVisible(boolean visible);
	
}
