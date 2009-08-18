package ar.com.nextel.sfa.client.cuenta;

import java.util.List;

import ar.com.nextel.sfa.client.dto.ContactoCuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DivisionDto;
import ar.com.nextel.sfa.client.dto.GranCuentaDto;
import ar.com.nextel.sfa.client.dto.SuscriptorDto;
import ar.com.nextel.sfa.client.dto.TipoContribuyenteDto;
import ar.com.nextel.sfa.client.enums.TipoContribuyenteEnum;
import ar.com.nextel.sfa.client.enums.TipoCuentaEnum;
import ar.com.nextel.sfa.client.util.HistoryUtils;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.widget.ApplicationUI;

public class EditarCuentaUI extends ApplicationUI {

	private final CuentaEdicionTabPanel cuentaTab = CuentaEdicionTabPanel.getInstance();
	
	public static boolean esEdicionCuenta =  true;
	
	public EditarCuentaUI() {
		super();
	}

	public boolean load() {
		esEdicionCuenta = true;
		resetEditor();
		//viene de popup "Agregar Cuenta"
		if (HistoryUtils.getParam("nroDoc")!=null) {
			cuentaTab.getCuentaDatosForm().setAtributosCamposCuenta(CuentaClientService.granCuentaDto);
			completarVisualizacionDatos(CuentaClientService.granCuentaDto);
		//viene de popup "Agregar Division"			
		} else if (HistoryUtils.getParam("div")!=null) {
			cuentaTab.getCuentaDatosForm().setAtributosCamposAlAgregarDivision(CuentaClientService.cuentaDto);
			completarVisualizacionDatos(CuentaClientService.cuentaDto);
		//viene de popup "Agregar Suscriptor"			
		} else if (HistoryUtils.getParam("sus")!=null) {
			cuentaTab.getCuentaDatosForm().setAtributosCamposAlAgregarSuscriptor(CuentaClientService.cuentaDto);
			completarVisualizacionDatos(CuentaClientService.cuentaDto);
		//viene de busqueda OPP			
		} else if (HistoryUtils.getParam("opp")!=null) {
			esEdicionCuenta = false;
			cuentaTab.setCuenta2editDto(CuentaClientService.oportunidadDto.getCuentaOrigen());
			cuentaTab.setPriorityFlag(CuentaClientService.oportunidadDto.getPrioridad().getId());
		    cuentaTab.getCuentaDatosForm().setAtributosCamposAlMostrarResuladoBusquedaFromOpp(CuentaClientService.oportunidadDto.getCuentaOrigen());
			cuentaTab.getCuentaDatosForm().ponerDatosOportunidadEnFormulario(CuentaClientService.oportunidadDto);
			cuentaTab.setNumeroCtaPot(CuentaClientService.oportunidadDto.getNumero());
			completarVisualizacionDatos(CuentaClientService.oportunidadDto.getCuentaOrigen());
		//viene de resultado de busqueda			
		} else if (HistoryUtils.getParam("cuenta_id")!=null) {			
			if(RegularExpressionConstants.isVancuc(CuentaClientService.cuentaDto.getCodigoVantive())) {
			   cuentaTab.getCuentaDatosForm().setAtributosCamposCuenta(CuentaClientService.cuentaDto);
			} else {
			   cuentaTab.getCuentaDatosForm().setAtributosCamposAlMostrarResuladoBusqueda(CuentaClientService.cuentaDto);
			}
			completarVisualizacionDatos(CuentaClientService.cuentaDto);
		}
		return false;
	}

	/**
	 * 
	 */
	private void resetEditor() {
		cuentaTab.clean();
		cuentaTab.getTabPanel().selectTab(0);
		CuentaDomiciliosForm.getInstance().setHuboCambios(false);
		CuentaContactoForm.getInstance().setFormDirty(false);
		cuentaTab.setPriorityFlag(new Long("0"));
	}
	
	/**
	 * 
	 * @param cuentaDto
	 */
	private void completarVisualizacionDatos(CuentaDto cuentaDto) {
		cuentaTab.setCuenta2editDto(cuentaDto);
		if (!esEdicionCuenta) 
			cuentaTab.getCuentaDatosForm().armarTablaPanelOppDatos();
		else 
			cuentaTab.getCuentaDatosForm().armarTablaPanelDatos();
		cargaPanelesCuenta();
		cuentaTab.validarCompletitud(false);
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
		if (cuenta.getTipoContribuyente()==null) {
			cuenta.setTipoContribuyente(new TipoContribuyenteDto(TipoContribuyenteEnum.CONS_FINAL.getId(),
					TipoContribuyenteEnum.CONS_FINAL.getDescripcion()));   
		}
		if(esEdicionCuenta) 
			cuentaTab.getCuentaDatosForm().ponerDatosBusquedaEnFormulario(cuenta);
		
		//carga info pestaña Domicilio
		cuentaTab.getCuentaDomicilioForm().getCrearDomicilio().setVisible(esEdicionCuenta);
		if (cuenta.getPersona() != null) {
			cuentaTab.getCuentaDomicilioForm().cargaTablaDomicilios(cuenta);
		}
		//carga info pestaña Contactos
		cuentaTab.getCuentaContactoForm().getCrearButton().setVisible(esEdicionCuenta);
		cargarInfoContactos(cuentaTab.getCuenta2editDto().getCategoriaCuenta().getDescripcion());

		//prepara UI para edicion cuenta o visualizacion opp 
		cuentaTab.setTabsTipoEditorCuenta(esEdicionCuenta);
		cuentaTab.getCuentaDatosForm().setUItipoEditorCuenta(esEdicionCuenta);		

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
