package ar.com.nextel.sfa.client.cuenta;


import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.util.ModalUtils;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.util.WindowUtils;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class CaratulaVerazModalDialog extends NextelDialog {

	private static CaratulaVerazModalDialog caratulaVerazModalDialog;
	private PopupPanel modalPopup;
	private HTML verazMessage;
	private SimpleLink guardar;
	private SimpleLink cancelar;
	private String verazFileName;

	public static CaratulaVerazModalDialog getInstance() {
		if (caratulaVerazModalDialog == null) {
			caratulaVerazModalDialog = new CaratulaVerazModalDialog();
		}
		return caratulaVerazModalDialog;
	}

	private CaratulaVerazModalDialog() {
		super("", false, true);
//		mainPanel.setWidth("800px");
//		mainPanel.setHeight("700px");
		ScrollPanel simplePanel = new ScrollPanel();
//		SimplePanel simplePanel = new SimplePanel();
//		simplePanel.addStyleName("alignCenter m30");
		simplePanel.addStyleName("ml30");
		simplePanel.setWidth("800px");
		simplePanel.setHeight("700px");
		verazMessage = new HTML("Consulta Veraz");
		simplePanel.add(verazMessage);
		add(simplePanel);
		cancelar = new SimpleLink("Cerrar");
		cancelar.setStyleName("link");
		addFormButtons(cancelar);
		guardar = new SimpleLink("Descargar");
		guardar.setStyleName("link");
		addFormButtons(guardar);
		setFormButtonsVisible(true);
		setFooterVisible(false);
		cancelar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				hide();
			}
		});
		guardar.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				
				CuentaRpcService.Util.getInstance().
						obtenerPahtArchivoVeraz(verazFileName, new DefaultWaitCallback<String>() {

							public void success(String result) {
								if(result != null){
									WindowUtils.redirect("/" + WindowUtils.getContextRoot() + "/download/" +
											result + "?module=veraz&service=rtf&name=" + verazFileName);
								}else{
									ErrorDialog.getInstance().setDialogTitle(ErrorDialog.ERROR);
									ErrorDialog.getInstance().show(Sfa.constant().ERR_FILE_NOT_FOUND(), false);
								}
							}
				});
				
			}
		});
	}

	public void showAndCenter(String fileName) {
		verazFileName = fileName;
		setDialogTitle("Consulta archivo: " + fileName);
		CuentaRpcService.Util.getInstance().leerConsultaDetalleVeraz(fileName, 
				new DefaultWaitCallback<String>() {

					public void success(String result) {
						if (result != null) {
							verazMessage.setHTML(result);
							showAndCenter();
						}else{
							ErrorDialog.getInstance().setDialogTitle(ErrorDialog.ERROR);
							ErrorDialog.getInstance().show(Sfa.constant().ERR_FILE_NOT_FOUND(), false);
						}
					}
				});
//		verazMessage.setHTML("<HTML><HEAD>"
//				+	"<meta http-equiv='Content-Type' content='text/htlm;charset=iso-8859-1'>"
//				+	"<meta http-equiv='Content-Style-Type' content='text/css'>"
//				+	"<STYLE>"
//				+	"p.cabezal1 {font-family:Times New Roman; font-size:14pt; font-style:normal;font-weight:bold; color:#000000}"
//				+	"p.cabezal2 {font-family:Times New Roman; font-size:10pt; font-style:normal;font-weight:bold; color:#000000}"
//				+	"PRE.comun  {font-family:Courier New; font-size:10pt; font-style:normal;font-weight:normal; color:#000000}"
//				+	"PRE.comun-bold {font-family:Courier New; font-size:10pt; font-style:normal;font-weight:bold; color:#000000}"
//				+	"PRE.notas {font-family:Courier New; font-size:10pt; font-style:italic;font-weight:normal }"
//				+	"</STYLE>"
//				+	"<TITLE> LOTE: V710494 - BZ44790000NX (20101230)</TITLE>"
//				+	"</HEAD>"
//				+	"<BODY bgcolor='#FFFFFF'>"
//				+	"<FONT size='4' style='font-family:Times New Roman'><B>NEXTEL ARGENTINA S.A.                CONSULTA</FONT><BR>"
//				+	"<FONT size='4' style='font-family:Times New Roman'><B>LOTE:V710494-BZ44790000NX EMITIDO EL 30/12/2010 (INFORME 01/01)</FONT><BR>"
//				+	"<PRE class='notas'>El presente informe es para uso y acceso exclusivo de las empresas              "
//				+	"<PRE class='notas'>integrantes del Telco Bureau.La informacion aportada por las empresas           "
//				+	"<PRE class='notas'>integrantes del Telco Bureau no consta en el informe comercial del              "
//				+	"<PRE class='notas'>Titular del Dato consultado y es propiedad de las empresas que la               "
//				+	"<PRE class='notas'>suministraron para su procesamiento. En cumplimiento de los terminos y          "
//				+	"<PRE class='notas'>condiciones establecidos en el Contrato de Procesamiento de Informacion,        "
//				+	"<PRE class='notas'>Organizacion Veraz S.A. desconoce el contenido de la misma."	                    
//				+	"<PRE class='comun-bold' ><HR ALIGN=LEFT SIZE=3 NOSHADE WIDTH=640PX><U>DATOS SEGUN SU CONSULTA EFECTUADA EL: 30/12/2010                                </U>"
//				+	"<PRE class='comun'>"
//				+	"      SEXO=MASCULINO"                                                            
//				+	"<PRE class='comun'>"
//				+	"      CUIT=20-23388246-2"                                                        
//				+	"<PRE class='comun-bold' ><HR ALIGN=LEFT SIZE=3 NOSHADE WIDTH=640PX><U>DATOS SEGUN BASE DE VALIDACION VERAZ:                                          </U>"
//				+	"<PRE class='comun-bold'>"
//				+	"      CUIL 20-23388246-2 VALIDADO"                                               
//				+	"<PRE class='comun'>"
//				+	"      GIROTTI,MARCELO GABRIEL                       EDAD=37  FECHA=08/05/1973"   
//				+	"<PRE class='comun'>"
//				+	"      AV LA PLATA 443  (1235)  C.FEDERAL"                                        
//				+	"<PRE class='comun'>"
//				+	"     DNI 23.388.246 (DUPLICADO)"                                                
//				+	"<PRE class='comun-bold' ><HR ALIGN=LEFT SIZE=3 NOSHADE WIDTH=640PX><U>DATOS SEGUN BASE DE INFORMACION:                                                </U>"
//				+	"<PRE class='comun'>"
//				+	"      LAPRIDA 00405 00000"                                                       
//				+	"<PRE class='comun'>"
//				+	"      (6620) CHIVILCOY BS.AIRES"                                                 
//				+	"<PRE class='comun'>"
//				+	"           APORTADA POR ULTIMA VEZ EN: 02/2010 CANTIDAD:   3"                    
//				+	"<PRE class='comun'>"
//				+	"      AV BERNARDINO RIVADAVIA 4391 6"
//				+   "<PRE class='comun'>"
//			    +	"     (1205)   C.FEDERAL"                                                        
//			    +	"<PRE class='comun'>"
//			    +	"	  APORTADA POR ULTIMA VEZ EN: 04/2007 CANTIDAD:   2"                    
//			    +	"<PRE class='comun'>"
//			    +	"	  AV LA PLATA 443 5"                                                         
//			    +	"<PRE class='comun'>"
//			    +  	"	(1235)   C.FEDERAL"                                                        
//			    +	"<PRE class='comun'>"
//			    +   "    	APORTADA POR ULTIMA VEZ EN: 08/2005 CANTIDAD:   2"                    
//			    +	"<PRE class='comun'>"
//			    +	"		AV LA PLATA 443 5 C"                                                       
//			    +	"<PRE class='comun'>"
//			    +	"	  (1235)   C.FEDERAL"                                                        
//			    +	"<PRE class='comun'>"
//			    +   "  		APORTADA POR ULTIMA VEZ EN: 08/2005 CANTIDAD:   2"                    
//			    +	"<PRE class='comun-bold' ><HR ALIGN=LEFT SIZE=3 NOSHADE WIDTH=640PX><U>                      OBSERVACIONES (ULTIMOS 5 A&#209;OS)                       </U>"
//			    +	"<PRE class='comun-bold' >"
//			    +	"<P><U>NO REGISTRA                                                                     </U></P>"
//			    +	"<PRE class='comun-bold' ><HR ALIGN=LEFT SIZE=3 NOSHADE WIDTH=640PX><U>          PRODUCTOS SEGUN BASE VERAZ (FUENTE PROPIA - ULTIMOS 2 A&#241;OS)           </U>"
//			    +	"<PRE class='comun-bold'>"
//			    +	"	*CLIENTE                             DEUDA         |    2010    |    2009    |"  
//			    +   "<PRE class='comun-bold' >"
//			    +	"<P><U>DESDE   PRODUCTO                     TOTAL  VENCIDO|DNOSAJJMAMFE|DNOSAJJMAMFE|  </U></P>"
//			    +	"<PRE class='comun'>"
//			    +	"	06/2001 PRESTAMOS EN CUOTAS              0        0|------------|111111111111|"  
//			    +	"<PRE class='comun'>"
//			    +	"	02/1999 TARJETAS DE CREDITO           3558        0|-11111111111|111111111111|"  
//			    +	"<PRE class='comun'>"
//			    +	"	07/2003 DESCUBIERTOS EN CTA.CTE.      1627        0|-11111101111|111111111101|"  
//			    +	"<PRE class='comun-bold'><HR ALIGN=LEFT SIZE=1 NOSHADE WIDTH=640PX><U>TOTAL:                                5185        0                             </U>"
//			    +	"<PRE class='notas'>Monedas = ARS:Pesos Argentinos,USD:Dolares,EUR:Euros."                           
//			    +	"<PRE class='notas'>Situaciones='-':Sin Informacion,0:No utilizado,S:Saldo No Significativo,"        
//			    +	"<PRE class='notas'>1:Normal,2:Atraso 31/60 dias,3:Atraso 61/90 dias,4:Atraso 91/120 dias,"          
//			    +	"<PRE class='notas'>6:Atraso 121/180 dias,9:Atraso 181/360 dias,G:En Gestion Extrajudicial,"        
//			    +	"<PRE class='notas'>J:Juicio Iniciado,I:Incobrable o atraso mayor a 360 dias,R:Refinanciacion,"      
//			    +	"<PRE class='notas'>E:Tarjeta extraviada o incluida en Boletin por seguridad,C:Operacion Cerrada,"   
//			    +	"<PRE class='notas'>D:Operacion cerrada por decision de la entidad,L:Denuncia de siniestro."         
//			    +	"<PRE class='notas'>NOTA:Los importes corresponden a la ultima situacion informada en el"            
//			    +	"<PRE class='notas'>cuatrimestre."                                                                   
//			    +	"<PRE class='notas'>Esta informacion es exclusiva de Organizacion Veraz SA y confeccionada"          
//			    +	"<PRE class='notas'>en funcion de los datos aportados directamente por nuestros clientes."           
//			    +	"<PRE class='comun-bold' ><HR ALIGN=LEFT SIZE=3 NOSHADE WIDTH=640PX><U>        CHEQUES RECHAZADOS AL: 23/12/2010  (FUENTE BCRA - ULTIMOS 2 A&#209;OS)       </U>"
//			    +	"<PRE class='comun-bold' >"
//			    +	"<P><U>NO REGISTRA                                                                     </U></P>"
//			    +	"<PRE class='comun-bold' ><HR ALIGN=LEFT SIZE=3 NOSHADE WIDTH=640PX><U>       DEUDORES DEL SISTEMA FINANCIERO (FUENTE BCRA - ULTIMOS 2 A&#209;OS)           </U>"
//			    +	"<PRE class='comun-bold'>"
//			    +	"NO REGISTRA"                                                                     
//			    +	"<PRE class='comun-bold' ><HR ALIGN=LEFT SIZE=3 NOSHADE WIDTH=640PX><U>                          CONSULTAS (ULTIMOS 5 A�OS)                            </U>"
//			    +	"<PRE class='comun-bold' >"
//			    +	"<P><U>FECHA   EMPRESA/ENTIDAD                                                         </U></P>"
//			    +	"<PRE class='comun-bold'>"
//			    +	"	SECTOR FINANCIERO"                                                               
//			    +	"<PRE class='comun'>"
//			    +	"	06/2010 AMERICAN EXPRESS"                                                        
//			    +	"<PRE class='comun'>"
//			    +	"	01/2010 BBVA  BANCO FRANCES SA"                                                  
//			    +	"<PRE class='comun'>"
//			    +	"	08/2009 STANDARD BANK ARGENTINA SA"                                              
//			    +	"<PRE class='comun'>"
//			    +	"	09/2006 BANKBOSTON"                                                              
//			    +	"<PRE class='comun-bold'>"
//			    +	"	SECTOR NO FINANCIERO"                                                            
//			    +	"<PRE class='comun-bold'>"
//			    +	"	12/2010 NEXTEL ARGENTINA S.A."                                                   
//			    +	"<PRE class='comun'>"
//			    +	"        ========================================================="           
//			    +	"<PRE class='comun-bold'>"
//			    +	"        FIN DEL INFORME 01/01 REFERENCIA:0563040700#ZOTGLZSQTSKXF"           
//			    +	"<PRE class='comun'>"
//				+	"</BODY>"
//				+	"</HTML>");
//		super.showAndCenter();
	}

	public void show() {
		modalPopup = ModalUtils.showModal(modalPopup);
		super.show();
	}
	
	public void hide() {
		modalPopup = ModalUtils.hideModal(modalPopup);
		super.hide();
	}
}
