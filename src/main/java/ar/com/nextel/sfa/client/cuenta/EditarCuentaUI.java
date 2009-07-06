package ar.com.nextel.sfa.client.cuenta;

import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.ContactoCuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DivisionDto;
import ar.com.nextel.sfa.client.dto.DocumentoDto;
import ar.com.nextel.sfa.client.dto.GranCuentaDto;
import ar.com.nextel.sfa.client.dto.SuscriptorDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.enums.TipoCuentaEnum;
import ar.com.nextel.sfa.client.util.HistoryUtils;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.History;

public class EditarCuentaUI extends ApplicationUI {

	private final CuentaEdicionTabPanel cuentaTab = CuentaEdicionTabPanel.getInstance();
	
	public EditarCuentaUI() {
		super();
	}

	public boolean load() {
		//resetEditor();
		//viene de popup "Agregar Cuenta"
		if (HistoryUtils.getParam("nroDoc")!=null) {
			TipoDocumentoDto tipoDoc = new TipoDocumentoDto(Long.parseLong(HistoryUtils.getParam("tipoDoc")),null);
			DocumentoDto docDto = new DocumentoDto(HistoryUtils.getParam("nroDoc"),tipoDoc);
			CuentaRpcService.Util.getInstance().reservaCreacionCuenta(docDto,new DefaultWaitCallback<GranCuentaDto>() {
				public void success(GranCuentaDto cuentaDto) {
					resetEditor();
					cuentaTab.getCuentaDatosForm().setAtributosCamposCuenta(cuentaDto);
					completarVisualizacionDatos(cuentaDto);
				}
			});
		//viene de popup "Agregar Division"			
		} else if (HistoryUtils.getParam("div")!=null) {
			Long id_cuentaPadre = Long.parseLong(HistoryUtils.getParam("cuenta_id"));				
			CuentaRpcService.Util.getInstance().crearDivision(id_cuentaPadre,new DefaultWaitCallback<CuentaDto>() {
				public void success(CuentaDto cuentaDto) {
					resetEditor();
					cuentaTab.getCuentaDatosForm().setAtributosCamposAlAgregarDivision(cuentaDto);
					completarVisualizacionDatos(cuentaDto);
				}
			});		
		//viene de popup "Agregar Suscriptor"			
		} else if (HistoryUtils.getParam("sus")!=null) {
			Long id_cuentaPadre = Long.parseLong(HistoryUtils.getParam("cuenta_id"));				
			CuentaRpcService.Util.getInstance().crearSuscriptor(id_cuentaPadre,new DefaultWaitCallback<CuentaDto>() {
				public void success(CuentaDto cuentaDto) {
					resetEditor();
					cuentaTab.getCuentaDatosForm().setAtributosCamposAlAgregarSuscriptor(cuentaDto);
					completarVisualizacionDatos(cuentaDto);
				}
			});
		//viene de resultado de busqueda			
		} else {  
			Long cuentaID = Long.parseLong(HistoryUtils.getParam("cuenta_id"));
			String cod_vantive = HistoryUtils.getParam("cod_vantive") !=null?HistoryUtils.getParam("cod_vantive") : null;
			CuentaRpcService.Util.getInstance().selectCuenta(cuentaID, cod_vantive,new DefaultWaitCallback<CuentaDto>() {
				public void success(CuentaDto cuentaDto) {
					if (puedenMostrarseDatos(cuentaDto)) {
						resetEditor();
						cuentaTab.getCuentaDatosForm().setAtributosCamposAlMostrarResuladoBusqueda(cuentaDto);
						completarVisualizacionDatos(cuentaDto);
					}
				}
			});
		}
		return true;
	}

	/**
	 * 
	 */
	private void resetEditor() {
		cuentaTab.clean();
		cuentaTab.getTabPanel().selectTab(0);
		CuentaDomiciliosForm.getInstance().setHuboCambios(false);
		CuentaContactoForm.getInstance().setFormDirty(false);
	}
	
	/**
	 * 
	 * @param cuentaDto
	 */
	private void completarVisualizacionDatos(CuentaDto cuentaDto) {
		cuentaTab.setCuenta2editDto(cuentaDto);
		cuentaTab.getCuentaDatosForm().armarTablaPanelDatos();
		cargaPanelesCuenta();
		cuentaTab.validarCompletitud(false);
	}
	
	/**
	 * 
	 * @param cuentaDto
	 * @return
	 */
	private boolean puedenMostrarseDatos(CuentaDto cuentaDto) {
		boolean result = true;
		if (HistoryUtils.getParam("por_dni").equals("0")) { //se filtro busqueda por documento/dni
			//usuario logueado no es el mismo que el vendedor de la cuenta
			if (!ClientContext.getInstance().getUsuario().getUserName().equals(cuentaDto.getVendedor().getUsuarioDto().getUserName())) {
				result = false;
				ErrorDialog.getInstance().show(Sfa.constant().ERR_NO_ACCESO_CUENTA());
				History.back();
			}
		}
		return result;
	}
	
	/**
	 * 	
	 */
	public void firstLoad() {
	}
	
	/**
	 * 
	 */
	private void cargaPanelesCuenta() {
		//Busca la cuenta con alguno de los dos datos NO nulos.
		CuentaDto cuenta = (CuentaDto)cuentaTab.getCuenta2editDto(); 
		if(cuenta.getCodigoVantive() != null){
			cuentaTab.setCliente(cuenta.getCodigoVantive());
		}
		cuentaTab.setRazonSocial(cuenta.getPersona()!=null ? cuenta.getPersona().getRazonSocial():"");
		//carga info pestaña Datos
		cuentaTab.getCuentaDatosForm().ponerDatosBusquedaEnFormulario(cuenta);
		//carga info pestaña Domicilio
		if (cuenta.getPersona() != null) {
			cuentaTab.getCuentaDomicilioForm().cargaTablaDomicilios(cuenta);
		}
		//carga info pestaña Contactos
		cargarInfoContactos(cuentaTab.getCuenta2editDto().getCategoriaCuenta().getDescripcion());
		//agrega tabs al panel principal
		mainPanel.add(cuentaTab.getCuentaEdicionPanel());
	}

	/**
	 * 
	 * @param categoriaCuenta
	 */
	private void cargarInfoContactos(String categoriaCuenta) {
		List <ContactoCuentaDto>contactos = null;
		if (categoriaCuenta.equals(TipoCuentaEnum.CTA.getTipo())) {
			contactos = ((GranCuentaDto)cuentaTab.getCuenta2editDto()).getContactos();
		} else if (categoriaCuenta.equals(TipoCuentaEnum.SUS.getTipo())) {
			contactos = ((SuscriptorDto)cuentaTab.getCuenta2editDto()).getGranCuenta().getContactos();
		} else if (categoriaCuenta.equals(TipoCuentaEnum.DIV.getTipo())) {
			contactos = ((DivisionDto)cuentaTab.getCuenta2editDto()).getGranCuenta().getContactos();
		}
		if (contactos!=null) {
			cuentaTab.getCuentaContactoForm().setListaContactos(contactos);
			cuentaTab.getCuentaContactoForm().cargarTabla();
		}
	}
	
	/**
	 * 
	 */
	public boolean unload(String token) {
		return true;
	}
}
