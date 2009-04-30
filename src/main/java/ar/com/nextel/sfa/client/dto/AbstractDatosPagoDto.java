package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class AbstractDatosPagoDto implements DatosPagoDto, IsSerializable {

	/**
     * @see ar.com.nextel.model.cuentas.beans.DatosPago#isDebitoCuentaBancaria()
     */
    public boolean isDebitoCuentaBancaria() {
        return false;
    }

    /**
     * @see ar.com.nextel.model.cuentas.beans.DatosPago#isDebitoTarjetaCredito()
     */
    public boolean isDebitoTarjetaCredito() {
        return false;
    }

    /**
     * @see ar.com.nextel.model.cuentas.beans.DatosPago#isEfectivo()
     */
    public boolean isEfectivo() {
        return false;
    }
}
