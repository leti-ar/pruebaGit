package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.CrearCuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DocumentoDto;
import ar.com.nextel.sfa.client.dto.GranCuentaDto;
import ar.com.nextel.sfa.client.dto.OportunidadNegocioDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.widget.UILoader;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.IncrementalCommand;

public class CuentaClientService {

	public static GranCuentaDto         granCuentaDto;
	public static CuentaDto             cuentaDto;
	public static OportunidadNegocioDto oportunidadDto;
	private static boolean error;

	/**
	 * 	
	 * @param idTipoDoc
	 * @param nroDoc
	 * @param idOpp
	 */
	public static void reservaCreacionCuenta(Long idTipoDoc, final String nroDoc, Long idOpp) {
		granCuentaDto = null;
		error = false;
		TipoDocumentoDto tipoDoc = new TipoDocumentoDto(idTipoDoc,null);
		DocumentoDto docDto = new DocumentoDto(nroDoc,tipoDoc);

		CrearCuentaDto crearCuentaDto = new CrearCuentaDto(docDto,idOpp);
		CuentaRpcService.Util.getInstance().reservaCreacionCuenta(crearCuentaDto,new DefaultWaitCallback<GranCuentaDto>() {
			public void success(GranCuentaDto ctaDto) {
				granCuentaDto = ctaDto;
			}
			public void failure(Throwable caught) {
				error = true;
				super.failure(caught);
			}
		});
		DeferredCommand.addCommand(new IncrementalCommand() {
			public boolean execute() {
				if (granCuentaDto == null && !error) 
					return true;
				if (!error)
					History.newItem(UILoader.EDITAR_CUENTA + "?nroDoc=" + nroDoc);
				return false;
			}
		});
	}	
	
	/**
	 * 	
	 * @param id_cuentaPadre
	 */
	public static void crearDivision(Long id_cuentaPadre) {
		cuentaDto = null;
		error     = false;
		CuentaRpcService.Util.getInstance().crearDivision(id_cuentaPadre,new DefaultWaitCallback<CuentaDto>() {
			public void success(CuentaDto ctaDto) {
				cuentaDto = ctaDto;
			}
			public void failure(Throwable caught) {
				error = true;
				super.failure(caught);
			}
			
		});		
		DeferredCommand.addCommand(new IncrementalCommand() {
			public boolean execute() {
				if (cuentaDto==null && !error) 
					return true;
				if (!error)
					History.newItem(UILoader.EDITAR_CUENTA + "?div=true");
				return false;
			}
		});
	}
	
	/**
	 * 
	 * @param id_cuentaPadre
	 */
	public static void crearSuscriptor(Long id_cuentaPadre) {
		cuentaDto = null;
		error = false;
		CuentaRpcService.Util.getInstance().crearSuscriptor(id_cuentaPadre,new DefaultWaitCallback<CuentaDto>() {
			public void success(CuentaDto ctaDto) {
				cuentaDto = ctaDto;
			}
			public void failure(Throwable caught) {
				error = true;
				super.failure(caught);
			}
		});
		DeferredCommand.addCommand(new IncrementalCommand() {
			public boolean execute() {
				if (cuentaDto == null && !error) 
					return true;
				if (!error)
					History.newItem(UILoader.EDITAR_CUENTA + "?sus=true");
				return false;
			}
		});
	}
	
	/**
	 * 
	 * @param idOpp
	 */
	public static void getOportunidadNegocio(Long idOpp) {
		oportunidadDto = null;
		error = false;
		CuentaRpcService.Util.getInstance().getOportunidadNegocio(idOpp,new DefaultWaitCallback<OportunidadNegocioDto>() {
			public void success(OportunidadNegocioDto opDto) {
				oportunidadDto = opDto;
			}
			public void failure(Throwable caught) {
				error = true;
				super.failure(caught);
			}
		});
		DeferredCommand.addCommand(new IncrementalCommand() {
			public boolean execute() {
				if (oportunidadDto == null && !error) 
					return true;
				if (!error)
					History.newItem(UILoader.EDITAR_CUENTA + "?opp=true");
				return false;
			}
		});
	}

	/**
	 * 
	 * @param cuentaID
	 * @param cod_vantive
	 */
	public static void cargarDatosCuenta(Long cuentaID, String cod_vantive, final String filtradoPorDni) {
		cuentaDto = null;
		error = false;
		CuentaRpcService.Util.getInstance().selectCuenta(cuentaID, cod_vantive,new DefaultWaitCallback<CuentaDto>() {
			public void success(CuentaDto ctaDto) {
				cuentaDto = ctaDto;
			}
			public void failure(Throwable caught) {
				error = true;
				super.failure(caught);
			}
		});
		DeferredCommand.addCommand(new IncrementalCommand() {
			public boolean execute() {
				if (cuentaDto == null && !error) 
					return true;
				if (!error && puedenMostrarseDatos(cuentaDto,filtradoPorDni)) 
					History.newItem(UILoader.EDITAR_CUENTA + "?cuenta_id=" + cuentaDto.getId());
				return false;
			}
		});
	}

	/**
	 * 
	 * @param cuentaID
	 * @param cod_vantive
	 */
	public static void cargarDatosCuenta(Long cuentaID, String cod_vantive) {
		cargarDatosCuenta(cuentaID, cod_vantive, null);
	}

	/**
	 * 
	 * @param cuentaDto
	 * @return
	 */
	public static boolean puedenMostrarseDatos(CuentaDto cuentaDto, String filtradoPorDni) {
		boolean result = true;
		if (filtradoPorDni!=null && filtradoPorDni.equals("0")) { //se filtro busqueda por documento/dni
			//usuario logueado no es el mismo que el vendedor de la cuenta
			if (!ClientContext.getInstance().getUsuario().getUserName().equals(cuentaDto.getVendedor().getUsuarioDto().getUserName())) {
				result = false;
				ErrorDialog.getInstance().show(Sfa.constant().ERR_NO_ACCESO_CUENTA());
				//History.back();
			}
		}
		return result;
	}
}
