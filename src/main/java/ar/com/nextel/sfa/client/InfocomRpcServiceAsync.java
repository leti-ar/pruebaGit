package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.FidelizacionDto;
import ar.com.nextel.sfa.client.dto.ResumenEquipoDto;
import ar.com.nextel.sfa.client.dto.ScoringDto;
import ar.com.nextel.sfa.client.dto.TransaccionCCDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface InfocomRpcServiceAsync {

	public void getScoring(AsyncCallback<ScoringDto> callback);
	
	public void getResumenEquipo(AsyncCallback<ResumenEquipoDto> callback);
	
	public void getFidelizacion(AsyncCallback<FidelizacionDto> callback);
	
	public void getTransaccionCC(AsyncCallback<List<TransaccionCCDto>> callback);
}
