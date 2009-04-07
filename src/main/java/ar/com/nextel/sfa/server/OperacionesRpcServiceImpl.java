package ar.com.nextel.sfa.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.nextel.business.vendedores.RegistroVendedores;
import ar.com.nextel.framework.security.Usuario;
import ar.com.nextel.model.cuentas.beans.Vendedor;
import ar.com.nextel.model.oportunidades.beans.CuentaPotencial;
import ar.com.nextel.model.oportunidades.beans.OperacionEnCurso;
import ar.com.nextel.sfa.client.OperacionesRpcService;
import ar.com.nextel.sfa.client.dto.CuentaPotencialDto;
import ar.com.nextel.sfa.client.dto.OperacionEnCursoDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.snoop.gwt.commons.client.exception.RpcExceptionMessages;
import ar.com.snoop.gwt.commons.server.RemoteService;

/**
 * @author esalvador
 */


public class OperacionesRpcServiceImpl extends RemoteService implements OperacionesRpcService{

	private WebApplicationContext context;
	private RegistroVendedores registroVendedores;

	@Override
	public void init() throws ServletException {
		// TODO: Investigar como se llega al modelo y como se implementa en el proyecto viejo,
		super.init();
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		registroVendedores = (RegistroVendedores) context.getBean("registroVendedores");
	}

	public List<OperacionEnCursoDto> searchOpEnCurso()
			throws RpcExceptionMessages {
		List<OperacionEnCursoDto> opResult = new ArrayList<OperacionEnCursoDto>();
		OperacionEnCursoDto operacionDto = new OperacionEnCursoDto();
		
		//TODO: Cambiar este hardcodeo de abajo por la obtencion del Vendedor correspondiente.
		Usuario usuario = new Usuario();
		usuario.setUserName("acsa1");
		Vendedor vendedor = registroVendedores.getVendedor(usuario);
		
		Iterator iterator = vendedor.getOperacionesEnCurso().iterator();
		for (; iterator.hasNext();) {       
			OperacionEnCurso operacion = (OperacionEnCurso) iterator.next();
			operacionDto.setNumeroCliente(operacion.getNumeroCliente());
			operacionDto.setRazonSocial(operacion.getRazonSocial());
			operacionDto.setDescripcionGrupo(operacion.getDescripcionGrupo());
			opResult.add(operacionDto);
		}
		return opResult;
	}

	public List<CuentaPotencialDto> searchReservas() throws RpcExceptionMessages {
		List<CuentaPotencialDto> reservasResult = new ArrayList<CuentaPotencialDto>();
		CuentaPotencialDto reservaDto = new CuentaPotencialDto();
		
		//TODO: Cambiar este hardcodeo de abajo por la obtencion del Vendedor correspondiente.
		Usuario usuario = new Usuario();
		usuario.setUserName("acsa1");
		Vendedor vendedor = registroVendedores.getVendedor(usuario);
		//
		
		for (CuentaPotencial cuentaPotencial : vendedor.getCuentasPotenciales()) {
			//PersonaDto persDto = mapeoPersona(cuentaPotencial);
			reservaDto.setCodigoVantive(cuentaPotencial.getCodigoVantive());
			reservaDto.setNumero(cuentaPotencial.getNumero());
			//reservaDto.setRazonSocial(persDto.getRazonSocial());
			reservaDto.setRazonSocial(cuentaPotencial.getPersona().getRazonSocial());
			//reservaDto.setTelefono(persDto.getTelefonos());
			reservasResult.add(reservaDto);
		}
		return reservasResult;
	}

	private PersonaDto mapeoPersona(CuentaPotencial cuentaPotencial) {
		PersonaDto personaDto = new PersonaDto();
		personaDto.setRazonSocial(cuentaPotencial.getPersona().getRazonSocial());
		//Set<Telefono> tel = reserva.getPersona().getTelefonos();
		//Set<TelefonoDto> telDto = new HashSet<TelefonoDto>();
		//telDto.add((TelefonoDto)tel);
		//personaDto.setTelefonos(telDto);
		return personaDto;
	}
	
}
