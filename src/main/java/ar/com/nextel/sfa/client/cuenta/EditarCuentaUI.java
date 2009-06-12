package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.DocumentoDto;
import ar.com.nextel.sfa.client.dto.GranCuentaDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.util.HistoryUtils;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

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
					cargaPaneles();
				}
			});	
		} else {//viene de resultado de busqueda
			Long cuentaID = Long.parseLong(HistoryUtils.getParam("cuenta_id"));
			String cod_vantive = HistoryUtils.getParam("cod_vantive");
				// Si algunos de los dos tiene datos validos, carga los paneles.
				CuentaRpcService.Util.getInstance().selectCuenta(cuentaID, cod_vantive,new DefaultWaitCallback<GranCuentaDto>() {
					public void success(GranCuentaDto ctaDto) {
						if (!ClientContext.getInstance().getUsuario().getId().equals(ctaDto.getVendedor().getId())) {
							History.back();
							ErrorDialog.getInstance().show("No tiene permiso para ver esa cuenta.");
						}else{
							cuentaTab.setCuenta2editDto(ctaDto);
							cuentaTab.getCuentaDatosForm().setAtributosCamposAlMostrarResuladoBusqueda(ctaDto);
							cargaPaneles();
						}
					}
				});
		}
		return true;
	}

	public void firstLoad() {
	}

	/**
	 * TODO Mover esto a cuentaTab? .rgm
	 * @author eSalvador 
	 **/
	private void cargaPaneles() {
		//Busca la cuenta con alguno de los dos datos NO nulos.
		if(cuentaTab.getCuenta2editDto().getCodigoVantive() != null){
			cuentaTab.setCliente(cuentaTab.getCuenta2editDto().getCodigoVantive());
		}
		cuentaTab.setRazonSocial(cuentaTab.getCuenta2editDto().getPersona()!=null ? cuentaTab.getCuenta2editDto().getPersona().getRazonSocial():"");
		//carga info pestaña Datos
		cuentaTab.getCuentaDatosForm().ponerDatosBusquedaEnFormulario(cuentaTab.getCuenta2editDto());
		//carga info pestaña Domicilio
		if (cuentaTab.getCuenta2editDto().getPersona() != null) {
			cuentaTab.getCuentaDomicilioForm().cargaTablaDomicilios(cuentaTab.getCuenta2editDto());
		}
		//carga info pestaña Contactos
		if (cuentaTab.getCuenta2editDto().getContactos() != null) {
			cuentaTab.getCuentaContactoForm().setListaContactos(cuentaTab.getCuenta2editDto().getContactos());
			cuentaTab.getCuentaContactoForm().cargarTabla();
		}
			
		mainPanel.add(cuentaTab.getCuentaEdicionPanel());
	}

	public boolean unload(String token) {
		return true;
	}
}
