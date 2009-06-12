package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DivisionDto;
import ar.com.nextel.sfa.client.dto.DocumentoDto;
import ar.com.nextel.sfa.client.dto.GranCuentaDto;
import ar.com.nextel.sfa.client.dto.SuscriptorDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.util.HistoryUtils;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;

/**
 * @author eSalvador
 **/
public class EditarCuentaUI extends ApplicationUI {


	private final CuentaEdicionTabPanel cuentaTab = CuentaEdicionTabPanel.getInstance();
//	private UserCenterDto userCenter;
	
	public EditarCuentaUI() {
		super();
	}

	public boolean load() {
		cuentaTab.clean();
		
		//viene de popup "Agregar"
		if (HistoryUtils.getParam("nroDoc")!=null) {
			TipoDocumentoDto tipoDoc = new TipoDocumentoDto(Long.parseLong(HistoryUtils.getParam("tipoDoc")),null);
			DocumentoDto docDto = new DocumentoDto(HistoryUtils.getParam("nroDoc"),tipoDoc);
			CuentaRpcService.Util.getInstance().reservaCreacionCuenta(docDto,new DefaultWaitCallback<GranCuentaDto>() {
				public void success(GranCuentaDto cuentaDto) {
					cuentaTab.setCuenta2editDto(cuentaDto);
					cuentaTab.validarCompletitudButton.addStyleName(cuentaTab.VALIDAR_COMPLETITUD_FAIL_STYLE);
					cuentaTab.getCuentaDatosForm().setAtributosCamposAlAgregarCuenta(cuentaDto);
					cuentaTab.getCuentaDatosForm().armarTablaPanelDatos();
					cargaPanelesGranCuenta();
				}
			});
		} else {//viene de resultado de busqueda
			Long cuentaID = Long.parseLong(HistoryUtils.getParam("cuenta_id"));
			String cod_vantive = HistoryUtils.getParam("cod_vantive");
				// Si algunos de los dos tiene datos validos, carga los paneles.
			/***/
			CuentaRpcService.Util.getInstance().selectCuenta(cuentaID, cod_vantive,new DefaultWaitCallback<CuentaDto>() {
					public void success(CuentaDto ctaDto) {
						SuscriptorDto cuentaSuscriptorDto = new SuscriptorDto();
						DivisionDto cuentaDivisionDto = new DivisionDto();
						CuentaDto cuentaDto = new CuentaDto();

					  /**TODO: Descomentar este IF y sacar el popup cuando se termine bien lo de Division en la proxima Iteracion!*/
					 if  (!"DIVISION".equals(getTipoCuenta(ctaDto))){
						
						if ("SUSCRIPTOR".equals(getTipoCuenta(ctaDto))){
							cuentaSuscriptorDto.setGranCuenta(ctaDto);
							cuentaDto = ctaDto;
						}else if ("GRAN CUENTA".equals(getTipoCuenta(ctaDto))){
							cuentaDto = ctaDto;
						}
						/**TODO: Investigar mas adelante, si no se deberia comparar por ID en vez de por UserName!!! */
						if (!ClientContext.getInstance().getUsuario().getUserName().equals(cuentaDto.getVendedor().getUsuarioDto().getUserName())) {
							ErrorDialog.getInstance().show("No tiene permiso para ver esa cuenta.");
							History.back();
						}else{
							String categoriaCuenta = cuentaDto.getCategoriaCuenta().getDescripcion();
							if ("SUSCRIPTOR".equals(categoriaCuenta)){
								cuentaTab.setCuenta2editDto(cuentaDto);
								cuentaTab.getCuentaDatosForm().setAtributosCamposAlMostrarResuladoBusqueda(cuentaDto);
								cargaPanelesSuscriptor();
							}
							if ("GRAN CUENTA".equals(categoriaCuenta)){
								cuentaTab.setCuenta2editDto(cuentaDto);
								cuentaTab.getCuentaDatosForm().setAtributosCamposAlMostrarResuladoBusqueda(cuentaDto);
								cargaPanelesGranCuenta();
							}
							if ("DIVISION".equals(categoriaCuenta)){
								//cuentaTab.setCuenta2editDto(cuentaDto);
								//cuentaTab.getCuentaDatosForm().setAtributosCamposAlMostrarResuladoBusqueda(cuentaDto);
								//cargaPanelesDivision();
							}
						}
					 }else{
						/**TODO: Descomentar el codigo y sacar el popup cuando se termine bien lo de Division en la proxima Iteracion!*/	
						ErrorDialog.getInstance().show("Esta funcionalidad no esta disponible por el momento.");
						History.back();
						/***/
					 }
					}
				});
				/***/
		}
		return true;
	}

	private String getTipoCuenta(CuentaDto cuentaDto){
		String tipoCuenta = "";
		if (cuentaDto.getCategoriaCuenta() != null){
			if("SUSCRIPTOR".equals(cuentaDto.getCategoriaCuenta().getDescripcion())){
				tipoCuenta = "SUSCRIPTOR";
			}else if("GRAN CUENTA".equals(cuentaDto.getCategoriaCuenta().getDescripcion())){
				tipoCuenta = "GRAN CUENTA";
			}else if("DIVISION".equals(cuentaDto.getCategoriaCuenta().getDescripcion())){
				tipoCuenta = "DIVISION";
			}
		}else{
			tipoCuenta = "SUSCRIPTOR";
		}
	return tipoCuenta;
	}
		
	public void firstLoad() {
	}
	
	/**
	 * @author eSalvador
	 * TODO: Terminar en la segunda iteracion!
	 **/
	private void cargaPanelesDivision() {
		DivisionDto division = ((GranCuentaDto)cuentaTab.getCuenta2editDto()).getDivisiones().get(0);
		
		if(division.getCodigoVantive() != null){
			cuentaTab.setCliente(division.getCodigoVantive());
		}
		cuentaTab.setRazonSocial(division.getPersona()!=null ? division.getPersona().getRazonSocial():"");
		//carga info pestaña Datos
		cuentaTab.getCuentaDatosForm().ponerDatosBusquedaEnFormulario(division);
		//carga info pestaña Domicilio
		if (division.getPersona() != null) {
			cuentaTab.getCuentaDomicilioForm().cargaTablaDomicilios(division);
		}
		//carga info pestaña Contactos
//		if (division.getContactos() != null) {
//			cuentaTab.getCuentaContactoForm().setListaContactos(cuentaTab.getCuenta2editDto().getContactos());
//			cuentaTab.getCuentaContactoForm().cargarTabla();
//		}
			
		mainPanel.add(cuentaTab.getCuentaEdicionPanel());
	}
	
	/**
	 * @author eSalvador
	 * TODO: Terminar en la segunda iteracion!
	 **/
	private void cargaPanelesSuscriptor() {
		SuscriptorDto suscriptor = ((GranCuentaDto)cuentaTab.getCuenta2editDto()).getSuscriptores().get(0);
		
		if(suscriptor.getCodigoVantive() != null){
			cuentaTab.setCliente(suscriptor.getCodigoVantive());
		}
		cuentaTab.setRazonSocial(suscriptor.getPersona()!=null ? suscriptor.getPersona().getRazonSocial():"");
		//carga info pestaña Datos
		cuentaTab.getCuentaDatosForm().ponerDatosSuscriptorBusquedaEnFormulario(suscriptor);
		//carga info pestaña Domicilio
		if (suscriptor.getGranCuenta().getPersona() != null) {
			cuentaTab.getCuentaDomicilioForm().cargaTablaDomicilios(suscriptor);
		}
		//carga info pestaña Contactos
//		if (cuentaTab.getCuenta2editDto().getContactos() != null) {
//			cuentaTab.getCuentaContactoForm().setListaContactos(cuentaTab.getCuenta2editDto().getContactos());
//			cuentaTab.getCuentaContactoForm().cargarTabla();
//		}
		mainPanel.add(cuentaTab.getCuentaEdicionPanel());
	}
	
	/**
	 * @author eSalvador
	 **/
	private void cargaPanelesGranCuenta() {
		//Busca la cuenta con alguno de los dos datos NO nulos.
		GranCuentaDto granCuenta = (GranCuentaDto)cuentaTab.getCuenta2editDto();
		if(granCuenta.getCodigoVantive() != null){
			cuentaTab.setCliente(granCuenta.getCodigoVantive());
		}
		cuentaTab.setRazonSocial(granCuenta.getPersona()!=null ? granCuenta.getPersona().getRazonSocial():"");
		//carga info pestaña Datos
		cuentaTab.getCuentaDatosForm().ponerDatosBusquedaEnFormulario(granCuenta);
		//carga info pestaña Domicilio
		if (granCuenta.getPersona() != null) {
			cuentaTab.getCuentaDomicilioForm().cargaTablaDomicilios(granCuenta);
		}
		//carga info pestaña Contactos
		if (granCuenta.getContactos() != null) {
			cuentaTab.getCuentaContactoForm().setListaContactos(granCuenta.getContactos());
			cuentaTab.getCuentaContactoForm().cargarTabla();
		}
			
		mainPanel.add(cuentaTab.getCuentaEdicionPanel());
	}

	public boolean unload(String token) {
		return true;
	}
}
