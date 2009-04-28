package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.FidelizacionDto;
import ar.com.nextel.sfa.client.dto.ResumenEquipoDto;
import ar.com.nextel.sfa.client.dto.ScoringDto;
import ar.com.nextel.sfa.client.dto.TransaccionCCDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("cuenta.rpc")
public interface InfocomRpcService extends RemoteService {
	
	public class Util{
		
		private static InfocomRpcServiceAsync infocomRpcService = null;
		private static InfocomRpcServiceDelegate infocomRpcServiceDelegate = null;
		
		public static InfocomRpcServiceDelegate getInstance(){
			if(infocomRpcServiceDelegate == null){
				infocomRpcService = GWT.create(InfocomRpcService.class);
				infocomRpcServiceDelegate = GWT.create(InfocomRpcServiceDelegate.class);
			}
			return infocomRpcServiceDelegate;
		}

	}
	
	public ScoringDto getScoring();
	
	public ResumenEquipoDto getResumenEquipo();
	
	public FidelizacionDto getFidelizacion();
	
	public List<TransaccionCCDto> getTransaccionCC();
	
}
