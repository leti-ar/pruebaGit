package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.CreditoFidelizacionDto;
import ar.com.nextel.sfa.client.dto.DatosEquipoPorEstadoDto;
import ar.com.nextel.sfa.client.dto.ResumenEquipoDto;
import ar.com.nextel.sfa.client.dto.ScoringDto;
import ar.com.nextel.sfa.client.dto.TransaccionCCDto;
import ar.com.nextel.sfa.client.initializer.InfocomInitializer;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

public class InfocomRpcServiceDelegate {

	private InfocomRpcServiceAsync infocomRpcService;

	public InfocomRpcServiceDelegate() {
	}

	public InfocomRpcServiceDelegate(InfocomRpcServiceAsync infocomRpcSerive) {
		this.infocomRpcService = infocomRpcSerive;
	}

	public void getInfocomInitializer(String numeroCuenta, String codigoVantive, String responsablePago, DefaultWaitCallback<InfocomInitializer> callback) {
		WaitWindow.show();
		infocomRpcService.getInfocomInitializer(numeroCuenta, codigoVantive, responsablePago, callback);
	}

	public void getDetalleCreditoFidelizacion(String idCuenta, String codigoVantive,
			DefaultWaitCallback<CreditoFidelizacionDto> callback) {
		WaitWindow.show();
		infocomRpcService.getDetalleCreditoFidelizacion(idCuenta, codigoVantive, callback);
	}

	public void getCuentaCorriente(String numeroCuenta, String codigoVantive, String responsablePago, DefaultWaitCallback<List<TransaccionCCDto>> callback) {
		WaitWindow.show();
		infocomRpcService.getCuentaCorriente(numeroCuenta, codigoVantive, responsablePago, callback);
	}
	
	public void getInformacionEquipos(String numeroCuenta, String codigoVantive, String estado, DefaultWaitCallback<List<DatosEquipoPorEstadoDto>> callback) {
		WaitWindow.show();
		infocomRpcService.getInformacionEquipos(numeroCuenta, codigoVantive, estado, callback);
	}
	
	public void getResumenEquipos(String numeroCuenta, String codigoVantive, String responsablePago, DefaultWaitCallback<ResumenEquipoDto> callback) {
		WaitWindow.show();
		infocomRpcService.getResumenEquipos(numeroCuenta, codigoVantive, responsablePago, callback);
	}
	
	public void consultarScoring(Long numeroCuenta, String codigoVantive, DefaultWaitCallback<ScoringDto> callback) {
		WaitWindow.show();
		infocomRpcService.consultarScoring(numeroCuenta, codigoVantive, callback);
	}

}
