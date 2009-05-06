package ar.com.nextel.sfa.server.businessservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ar.com.nextel.business.cuentas.create.CreateCuentaBusinessOperator;
import ar.com.nextel.business.cuentas.create.businessUnits.SolicitudCuenta;
import ar.com.nextel.business.oportunidades.ReservaCreacionCuentaBusinessOperator;
import ar.com.nextel.business.oportunidades.ReservaCreacionCuentaBusinessOperatorResult;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.model.cuentas.beans.Cuenta;
import ar.com.nextel.services.components.sessionContext.SessionContextLoader;
import ar.com.nextel.services.exceptions.BusinessException;

@Service
public class CuentaBusinessService {

	private CreateCuentaBusinessOperator createCuentaBusinessOperator;
//	private EdicionCuentaBusinessOperator edicionCuentaBusinessOperator;
	private ReservaCreacionCuentaBusinessOperator reservaCreacionCuentaBusinessOperator;
	        
	private SessionContextLoader sessionContextLoader;
	private Repository repository;

	@Autowired
	public void setReservaCreacionCuentaBusinessOperator( 
			ReservaCreacionCuentaBusinessOperator reservaCreacionCuentaBusinessOperatorBean) {
		this.reservaCreacionCuentaBusinessOperator = reservaCreacionCuentaBusinessOperatorBean;
	}
	@Autowired
	public void setCreateCuentaBusinessOperator( 
			CreateCuentaBusinessOperator createCuentaBusinessOperatorBean) {
		this.createCuentaBusinessOperator = createCuentaBusinessOperatorBean;
	}
	@Autowired	
	public void setSessionContextLoader(SessionContextLoader sessionContextLoader) {
		this.sessionContextLoader = sessionContextLoader;
	}
	
	@Autowired
	public void setRepository(@Qualifier("repository")Repository repository) {
		this.repository = repository;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Cuenta reservarCrearCta(SolicitudCuenta solicitudCta) throws BusinessException {
		ReservaCreacionCuentaBusinessOperatorResult reservarCrearCta = reservaCreacionCuentaBusinessOperator.reservarCrearCuenta(solicitudCta);
		return reservarCrearCta.getCuenta();
	}

}
