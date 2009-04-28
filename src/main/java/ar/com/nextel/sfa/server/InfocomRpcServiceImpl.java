package ar.com.nextel.sfa.server;

import java.util.List;

import org.springframework.web.context.WebApplicationContext;

import ar.com.nextel.sfa.client.InfocomRpcService;
import ar.com.nextel.sfa.client.dto.FidelizacionDto;
import ar.com.nextel.sfa.client.dto.ResumenEquipoDto;
import ar.com.nextel.sfa.client.dto.ScoringDto;
import ar.com.nextel.sfa.client.dto.TransaccionCCDto;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class InfocomRpcServiceImpl extends RemoteServiceServlet implements InfocomRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WebApplicationContext context;

	public ScoringDto getScoring() {
		// TODO Auto-generated method stub
		return null;
	}

	public FidelizacionDto getFidelizacion() {
		// TODO Auto-generated method stub
		return null;
	}

	public ResumenEquipoDto getResumenEquipo() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TransaccionCCDto> getTransaccionCC() {
		// TODO Auto-generated method stub
		return null;
	}

}
