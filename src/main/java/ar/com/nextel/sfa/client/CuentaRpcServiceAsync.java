package ar.com.nextel.sfa.client;

import java.util.List;

import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchResultDto;
import ar.com.nextel.sfa.client.dto.DivisionDto;
import ar.com.nextel.sfa.client.dto.DocumentoDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.GranCuentaDto;
import ar.com.nextel.sfa.client.dto.NormalizarCPAResultDto;
import ar.com.nextel.sfa.client.dto.NormalizarDomicilioResultDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.ProvinciaDto;
import ar.com.nextel.sfa.client.dto.SuscriptorDto;
import ar.com.nextel.sfa.client.dto.TarjetaCreditoValidatorResultDto;
import ar.com.nextel.sfa.client.dto.VerazResponseDto;
import ar.com.nextel.sfa.client.initializer.AgregarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.BuscarCuentaInitializer;
import ar.com.nextel.sfa.client.initializer.CrearContactoInitializer;
import ar.com.nextel.sfa.client.initializer.VerazInitializer;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CuentaRpcServiceAsync {
	public void getAgregarCuentaInitializer(AsyncCallback<AgregarCuentaInitializer> callback);
	public void getBuscarCuentaInitializer(AsyncCallback<BuscarCuentaInitializer> callback);
	public void saveGranCuenta(GranCuentaDto cuentaDto, AsyncCallback<GranCuentaDto> callback);
	public void crearDivision(Long id_cuentaPadre, AsyncCallback<CuentaDto> callback);
	public void crearSuscriptor(Long id_cuentaPadre, AsyncCallback<CuentaDto> callback);
	public void searchCuenta(CuentaSearchDto cuentaSearchDto, AsyncCallback<List<CuentaSearchResultDto>> callback);
	public void getVerazInitializer(AsyncCallback<VerazInitializer> callback);
	public void consultarVeraz(PersonaDto personaDto, AsyncCallback<VerazResponseDto> callback);
	public void selectCuenta(Long cuentaId, String cod_vantive,AsyncCallback<CuentaDto> callback);
	public void reservaCreacionCuenta(DocumentoDto docDto, AsyncCallback<GranCuentaDto> callback);
	public void validarTarjeta(String numeroTarjeta, Integer mesVto, Integer anoVto,AsyncCallback<TarjetaCreditoValidatorResultDto> callback);
	public void getDomicilioPorCPA(String cpa, AsyncCallback<NormalizarCPAResultDto> callback);
    public void normalizarDomicilio(DomiciliosCuentaDto domicilioANormalizar, AsyncCallback<NormalizarDomicilioResultDto> callback);
	public void getCrearContactoInitializer(AsyncCallback<CrearContactoInitializer> callback); 
	public void getProvinciasInitializer(AsyncCallback<List<ProvinciaDto>> callback);
}
