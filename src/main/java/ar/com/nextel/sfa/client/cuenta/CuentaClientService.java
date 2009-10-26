package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.dto.CrearCuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaPotencialDto;
import ar.com.nextel.sfa.client.dto.DocumentoDto;
import ar.com.nextel.sfa.client.dto.GranCuentaDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.widget.UILoader;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.IncrementalCommand;

public class CuentaClientService {

	public  static GranCuentaDto       granCuentaDto;
	public  static CuentaDto           cuentaDto;
	public  static CuentaPotencialDto  cuentaPotencialDto;
	private static boolean             error;
	public static String nombreFromVeraz;
	public static String apellidoFromVeraz;

	/**
	 * 
	 * @param idTipoDoc
	 * @param nroDoc
	 * @param idOpp
	 * @param nombre
	 * @param apellido
	 */
	public static void reservaCreacionCuentaFromVeraz(Long idTipoDoc, String nroDoc, String nombre, String apellido) {
		nombreFromVeraz   = nombre   !=null ? nombre.toUpperCase()   : "";
		apellidoFromVeraz = apellido !=null ? apellido.toUpperCase() : "";
		reservaCreacionCuenta(idTipoDoc, nroDoc, null, true);
	}
	
	/**
	 * 	
	 * @param idTipoDoc
	 * @param nroDoc
	 * @param idOpp
	 */
	public static void reservaCreacionCuenta(Long idTipoDoc, String nroDoc, Long idOpp) {
		reservaCreacionCuenta(idTipoDoc, nroDoc, idOpp, true);
	}
	
	/**
	 * 
	 * @param idTipoDoc
	 * @param nroDoc
	 * @param idOpp
	 * @param redir
	 */
	public static void reservaCreacionCuenta(final Long idTipoDoc, final String nroDoc, final Long idOpp, boolean redir) {
		granCuentaDto = null;
		error         = false;
		TipoDocumentoDto tipoDoc      = new TipoDocumentoDto(idTipoDoc,null);
		DocumentoDto docDto           = new DocumentoDto(nroDoc,tipoDoc);
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
		if (redir) {
			DeferredCommand.addCommand(new IncrementalCommand() {
				public boolean execute() {
					if (granCuentaDto == null && !error) 
						return true;
					if (!error)
						History.newItem(UILoader.EDITAR_CUENTA + "?nroDoc=" + nroDoc +"&idDoc=" + idTipoDoc + "&idOpp="+idOpp + "&cuenta_id="+granCuentaDto.getId());
					return false;
				}
			});
		}
	}	

	/**
	 * 	
	 * @param id_cuentaPadre
	 */
	public static void crearDivision(final Long id_cuentaPadre) {
		crearDivision(id_cuentaPadre,true);
	}
	/**
	 * 
	 * @param id_cuentaPadre
	 * @param redir
	 */
	public static void crearDivision(final Long id_cuentaPadre, boolean redir) {
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
		if (redir) {
			DeferredCommand.addCommand(new IncrementalCommand() {
				public boolean execute() {
					if (cuentaDto==null && !error) 
						return true;
					if (!error)
						History.newItem(UILoader.EDITAR_CUENTA + "?div=true" + "&idCtaPadre="+id_cuentaPadre + "&cuenta_id="+cuentaDto.getId());
					return false;
				}
			});
		}
	}
	
	/**
	 * 
	 * @param id_cuentaPadre
	 */
	public static void crearSuscriptor(final Long id_cuentaPadre) {
		crearSuscriptor(id_cuentaPadre,true);
	}
	/**
	 * 
	 * @param id_cuentaPadre
	 * @param redir
	 */
	public static void crearSuscriptor(final Long id_cuentaPadre, boolean redir) {
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
		if (redir) {
			DeferredCommand.addCommand(new IncrementalCommand() {
				public boolean execute() {
					if (cuentaDto == null && !error) 
						return true;
					if (!error)
						History.newItem(UILoader.EDITAR_CUENTA + "?sus=true" + "&idCtaPadre="+id_cuentaPadre + "&cuenta_id="+cuentaDto.getId());
					return false;
				}
			});
		}
	}
	
	/**
	 * 
	 * @param idOpp
	 */
	public static void getOportunidadNegocio(final Long idOpp) {
		getOportunidadNegocio(idOpp,true); 
	}
	/**
	 * 
	 * @param idOpp
	 * @param redir
	 */
	public static void getOportunidadNegocio(final Long idOpp,boolean redir) {
		cuentaPotencialDto = null;
		error = false;
		CuentaRpcService.Util.getInstance().getOportunidadNegocio(idOpp,new DefaultWaitCallback<CuentaPotencialDto>() {
			public void success(CuentaPotencialDto ctaPot) {
				cuentaPotencialDto = ctaPot;
			}
			public void failure(Throwable caught) {
				error = true;
				super.failure(caught);
			}
		});
		if (redir) {
			DeferredCommand.addCommand(new IncrementalCommand() {
				public boolean execute() {
					if (cuentaPotencialDto == null && !error) 
						return true;
					if (!error)
						History.newItem(UILoader.EDITAR_CUENTA + "?opp=true"+"&idOpp="+idOpp + "&cuenta_id=" + cuentaPotencialDto.getCuentaOrigen().getId());
					return false;
				}
			});
		}
	}

	/**
	 * 
	 * @param cuentaID
	 * @param cod_vantive
	 */
	public static void cargarDatosCuenta(Long cuentaID, String cod_vantive) {
		cargarDatosCuenta(cuentaID, cod_vantive, null,true);
	}

	/**
	 * 
	 * @param cuentaID
	 * @param cod_vantive
	 */
	public static void cargarDatosCuenta(final Long cuentaID, final String cod_vantive, final String filtradoPorDni) {
		cargarDatosCuenta(cuentaID, cod_vantive, filtradoPorDni,true);
	}
	/**
	 * 
	 * @param cuentaID
	 * @param cod_vantive
	 * @param filtradoPorDni
	 * @param readOnly
	 * @param redir
	 */
	public static void cargarDatosCuenta(final Long cuentaID, final String cod_vantive, final String filtradoPorDni, boolean redir) {
		cuentaDto = null;
		error = false;
		CuentaRpcService.Util.getInstance().selectCuenta(cuentaID, cod_vantive,(filtradoPorDni!=null),new DefaultWaitCallback<CuentaDto>() {
			public void success(CuentaDto ctaDto) {
				cuentaDto = ctaDto;
			}
			public void failure(Throwable caught) {
				error = true;
				ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
				super.failure(caught);
			}
		});
		if (redir) {
			DeferredCommand.addCommand(new IncrementalCommand() {
				public boolean execute() {
					if (cuentaDto == null && !error) 
						return true;
					if (!error) { 
						History.newItem(UILoader.EDITAR_CUENTA + "?cuenta_id=" + cuentaID + "&cod_vantive="+cod_vantive+"&filByDni="+filtradoPorDni);
					}
					return false;
				}
			});
		}
	}
}
