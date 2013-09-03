package ar.com.nextel.sfa.client.initializer;

import java.util.List;

import ar.com.nextel.sfa.client.dto.BancoDto;
import ar.com.nextel.sfa.client.dto.CalifCrediticiaDto;
import ar.com.nextel.sfa.client.dto.CalificacionDto;
import ar.com.nextel.sfa.client.dto.ComDto;
import ar.com.nextel.sfa.client.dto.CompPagoDto;
import ar.com.nextel.sfa.client.dto.EstadoCreditBancoCentralDto;
import ar.com.nextel.sfa.client.dto.FirmanteDto;
import ar.com.nextel.sfa.client.dto.RiskCodeDto;
import ar.com.nextel.sfa.client.dto.TipoCuentaBancariaDto;
import ar.com.nextel.sfa.client.dto.TipoTarjetaDto;
import ar.com.nextel.sfa.client.dto.ValidacionDomicilioDto;
import ar.com.nextel.sfa.client.dto.VerazNosisDto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CaratulaInitializer implements IsSerializable{

	private List<ValidacionDomicilioDto> validDomicilio;
	private List<BancoDto> bancos;
	private List<TipoCuentaBancariaDto> tipoCtaBancaria;
	private List<TipoTarjetaDto> tipoTarjCred;
	private List<CalifCrediticiaDto> califCrediticia;
	private List<ComDto> com;
	private List<CalificacionDto> calificacion;
	private List<RiskCodeDto> riskCode;
	private List<CompPagoDto> compPago;
	private List<FirmanteDto> firmante;
	private List<VerazNosisDto> verazNosis;
	private List<EstadoCreditBancoCentralDto> estadoCredBC;
	
	public List<ValidacionDomicilioDto> getValidDomicilio() {
		return validDomicilio;
	}

	public void setValidDomicilio(List<ValidacionDomicilioDto> validDomicilio) {
		this.validDomicilio = validDomicilio;
	}

	public List<BancoDto> getBancos() {
		return bancos;
	}

	public void setBancos(List<BancoDto> bancos) {
		this.bancos = bancos;
	}

	public List<TipoCuentaBancariaDto> getTipoCtaBancaria() {
		return tipoCtaBancaria;
	}

	public void setTipoCtaBancaria(List<TipoCuentaBancariaDto> tipoCtaBancaria) {
		this.tipoCtaBancaria = tipoCtaBancaria;
	}

	public List<TipoTarjetaDto> getTipoTarjCred() {
		return tipoTarjCred;
	}

	public void setTipoTarjCred(List<TipoTarjetaDto> tipoTarjCred) {
		this.tipoTarjCred = tipoTarjCred;
	}

	public List<CalifCrediticiaDto> getCalifCrediticia() {
		return califCrediticia;
	}

	public void setCalifCrediticia(List<CalifCrediticiaDto> califCrediticia) {
		this.califCrediticia = califCrediticia;
	}

	public List<ComDto> getCom() {
		return com;
	}

	public void setCom(List<ComDto> com) {
		this.com = com;
	}

	public List<CalificacionDto> getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(List<CalificacionDto> calificacion) {
		this.calificacion = calificacion;
	}

	public List<RiskCodeDto> getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(List<RiskCodeDto> riskCode) {
		this.riskCode = riskCode;
	}

	public List<CompPagoDto> getCompPago() {
		return compPago;
	}

	public void setCompPago(List<CompPagoDto> compPago) {
		this.compPago = compPago;
	}

	public List<FirmanteDto> getFirmante() {
		return firmante;
	}

	public void setFirmante(List<FirmanteDto> firmante) {
		this.firmante = firmante;
	}

	public List<VerazNosisDto> getVerazNosis() {
		return verazNosis;
	}

	public void setVerazNosis(List<VerazNosisDto> verazNosis) {
		this.verazNosis = verazNosis;
	}

	public List<EstadoCreditBancoCentralDto> getEstadoCredBC() {
		return estadoCredBC;
	}

	public void setEstadoCredBC(List<EstadoCreditBancoCentralDto> estadoCredBC) {
		this.estadoCredBC = estadoCredBC;
	}
}
