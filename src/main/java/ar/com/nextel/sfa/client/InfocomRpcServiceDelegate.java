package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.FidelizacionDto;
import ar.com.nextel.sfa.client.dto.ResumenEquipoDto;
import ar.com.nextel.sfa.client.dto.ScoringDto;
import ar.com.nextel.sfa.client.dto.TransaccionCCDto;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;


public class InfocomRpcServiceDelegate {
	
	private InfocomRpcServiceAsync infocomRpcService;
	
	public InfocomRpcServiceDelegate(){
	}
	
	public InfocomRpcServiceDelegate(InfocomRpcServiceAsync infocomRpcSerive){
		this.infocomRpcService = infocomRpcSerive;
	}
	
	public void getScoring(DefaultWaitCallback<ScoringDto> callback){
		WaitWindow.show();
		infocomRpcService.getScoring(callback);
	}
	
	public void getResumenEquipo(DefaultWaitCallback<ResumenEquipoDto> callback){
		WaitWindow.show();
		infocomRpcService.getResumenEquipo(callback);
	}
	
	public void getFidelizacion(DefaultWaitCallback<FidelizacionDto> callback){
		WaitWindow.show();
		infocomRpcService.getFidelizacion(callback);
	}
	
	public void getTransaccionCC(DefaultWaitCallback<List<TransaccionCCDto>> callback){
		WaitWindow.show();
		infocomRpcService.getTransaccionCC(callback);
	}
}
