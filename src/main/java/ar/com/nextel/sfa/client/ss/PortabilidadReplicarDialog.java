package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.ProveedorDto;
import ar.com.nextel.sfa.client.dto.SolicitudPortabilidadDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.dto.TipoPersonaDto;
import ar.com.nextel.sfa.client.dto.TipoTelefoniaDto;
import ar.com.nextel.sfa.client.initializer.PortabilidadInitializer;
import ar.com.nextel.sfa.client.util.PortabilidadUtil;
import ar.com.nextel.sfa.client.widget.ModalMessageDialog;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.nextel.sfa.client.widget.TitledPanel;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

@SuppressWarnings("deprecation")
public class PortabilidadReplicarDialog extends NextelDialog{

	private static final String TITULO = "Replicar Datos de Portabilidad"; 
	
	private SimpleLink lnkAceptar;
	private SimpleLink lnkCancelar;
	private SimplePanel pnlTblDetalle;
	private FlexTable tblDetalle;
	private TitledPanel pnlDescripcionReplica;
	private TitledPanel pnlDetalleReplica;
	private ClickListener clickListener;
	private Grid gridLayout;
	private List<LineaSolicitudServicioDto> lineas;
	private String strRazonSocial;
	private String strNombre;
	private String strApellido;
	private TipoDocumentoDto tipoDocumento;
	private String strNroDocumento;
	private SolicitudServicioDto solicitudServicio;
	private PortabilidadUtil portabilidadUtil;
	private TipoTelefoniaDto tipoTelefonia;
	private ProveedorDto proveedorAnterior;
	private String strTelefonoContacto;
	private String strEmailContacto;
	private TipoPersonaDto tipoPersona;
	
	public PortabilidadReplicarDialog(){
		super(TITULO,false,true);
		addStyleName("gwt-ItemSolicitudDialog");
		
}
	
	public void show(SolicitudServicioDto unaSolicitudServicio,int indexLinea,
			final PortabilidadInitializer initializer,final DatosSSUI datos,final EditarSSUIController controller) {
		solicitudServicio = unaSolicitudServicio;
		lineas = solicitudServicio.getLineas();
		tipoTelefonia = initializer.getLstTipoTelefonia().get(0);
		proveedorAnterior = initializer.getLstProveedorAnterior().get(0);
			
		boolean existenPortabilidades = false;
		
		for (LineaSolicitudServicioDto linea : lineas) {
			if(linea.getPortabilidad() != null) existenPortabilidades = true;
		}
		
		if(existenPortabilidades){
			strRazonSocial = lineas.get(indexLinea).getPortabilidad().getRazonSocial();
			strNombre = lineas.get(indexLinea).getPortabilidad().getNombre();
			strApellido = lineas.get(indexLinea).getPortabilidad().getApellido();
			tipoDocumento = lineas.get(indexLinea).getPortabilidad().getTipoDocumento();
			strNroDocumento = lineas.get(indexLinea).getPortabilidad().getNumeroDocumento();
			strTelefonoContacto = lineas.get(indexLinea).getPortabilidad().getTelefono();
			strEmailContacto = lineas.get(indexLinea).getPortabilidad().getEmail();
			
			// Datos a Replicar
			pnlDescripcionReplica = new TitledPanel("Datos a Replicar");
			gridLayout = new Grid(4,2);
			gridLayout.addStyleName("layout");
			gridLayout.getColumnFormatter().setWidth(0, "150px");
			gridLayout.getColumnFormatter().setWidth(1, "350px");
			
			tipoPersona = lineas.get(indexLinea).getPortabilidad().getTipoPersona();
			if(tipoPersona != null) {
				// LF #3278
//				if (solicitudServicio.getCuenta().isEmpresa()) { //JURIDICA
				if(!tipoPersona.getDescripcion().equals("FISICA")) {
					gridLayout.setHTML(0,0, "Razon Social:"); 	gridLayout.setHTML(0,1, strRazonSocial);
					gridLayout.setHTML(1,0, "Tipo Documento:");	gridLayout.setHTML(1,1, tipoDocumento.getDescripcion());
					gridLayout.setHTML(2,0, "Nro. Documento:");	gridLayout.setHTML(2,1, strNroDocumento);
				} else { //FISICA
					gridLayout.setHTML(0,0, "Nombre:");			gridLayout.setHTML(0,1, strNombre);
					gridLayout.setHTML(1,0, "Apellido:");		gridLayout.setHTML(1,1, strApellido);
					gridLayout.setHTML(2,0, "Tipo Documento:");	gridLayout.setHTML(2,1, tipoDocumento.getDescripcion());
					gridLayout.setHTML(3,0, "Nro. Documento:");	gridLayout.setHTML(3,1, strNroDocumento);
				}
			}
			
			// Grilla
			pnlTblDetalle = new SimplePanel();
			pnlTblDetalle.addStyleName("portabilidadReplicar");
			pnlDetalleReplica = new TitledPanel("Seleccion de Lineas"); 
			tblDetalle = new FlexTable();

			String[] cabeceras = {Sfa.constant().whiteSpace(),"Item","Alias","Plan","Tipo SS"};
			for(int i = 0; i < cabeceras.length; i++){
				tblDetalle.setHTML(0,i, cabeceras[i]);
			}

			tblDetalle.setCellPadding(0);
			tblDetalle.setCellSpacing(0);
			tblDetalle.addStyleName("dataTable");
			tblDetalle.setWidth("98%");
			tblDetalle.getRowFormatter().addStyleName(0, "header");

			int newRow = 1;
			for (LineaSolicitudServicioDto linea : lineas) {
				if(/*linea.getPortabilidad() != null && */linea.getPortabilidad() != lineas.get(indexLinea).getPortabilidad()){
					tblDetalle.setWidget(newRow, 0, new CheckBox());
					tblDetalle.setHTML(newRow, 1, linea.getItem() != null ? linea.getItem().getDescripcion() : Sfa.constant().whiteSpace());
					tblDetalle.setHTML(newRow, 2, linea.getAlias() != null ? linea.getAlias() : Sfa.constant().whiteSpace());
					tblDetalle.setHTML(newRow, 3, linea.getPlan() != null ? linea.getPlan().getDescripcion() : Sfa.constant().whiteSpace());
					tblDetalle.setHTML(newRow, 4, linea.getTipoSolicitud() != null ? linea.getTipoSolicitud().getDescripcion() : Sfa.constant().whiteSpace());
					newRow++;
				}
			}

			//Click Listener
			clickListener = new ClickListener() {
				public void onClick(Widget sender) {
					if(sender == lnkAceptar){
						boolean check = false;
						for(int i = 1; i < tblDetalle.getRowCount() && !check; i++){
							if(((CheckBox)tblDetalle.getWidget(i, 0)).getValue()) check = true;
						}
						
						if(!check){
							hide();
							tblDetalle.removeAllRows();
							datos.refresh();	
						}else{
							for(int i = 1; i < tblDetalle.getRowCount(); i++){
								if(((CheckBox)tblDetalle.getWidget(i, 0)).getValue()){
									for (LineaSolicitudServicioDto linea : lineas) {
										if(tblDetalle.getHTML(i, 2).equals(linea.getAlias())){
											if(linea.getPortabilidad() == null){
												linea.setPortabilidad(new SolicitudPortabilidadDto());
	 											linea.getPortabilidad().setTipoTelefonia(tipoTelefonia);
	 											linea.getPortabilidad().setProveedorAnterior(proveedorAnterior);
											}
											
											linea.getPortabilidad().setRazonSocial(strRazonSocial);
											linea.getPortabilidad().setNombre(strNombre);
											linea.getPortabilidad().setApellido(strApellido);
											linea.getPortabilidad().setTipoDocumento(tipoDocumento);
											linea.getPortabilidad().setNumeroDocumento(strNroDocumento);
											linea.getPortabilidad().setEmail(strEmailContacto);
											linea.getPortabilidad().setTelefono(strTelefonoContacto);
											// LF
											linea.getPortabilidad().setTipoPersona(tipoPersona);
											// LF - #3286
											linea.getPortabilidad().setRecibeSMS(true);

											String numeroReservado = linea.getNumeroReserva();
											boolean tieneNReserva = numeroReservado != null && numeroReservado.length() > 0;
											if (tieneNReserva) {
												controller.desreservarNumeroTelefonico(Long.parseLong(numeroReservado), linea.getId(),
														new DefaultWaitCallback() {
															public void success(Object result) {
															}
														});
											}
										}
									}
									asignarNroSSPortabilidad();
								}
							}

							hide();
							tblDetalle.removeAllRows();
							datos.refresh();	
							
							List<Integer> idRepetidos = new ArrayList<Integer>();
							
							for (LineaSolicitudServicioDto linea : lineas) {
								for(int i = 0; i < linea.getServiciosAdicionales().size(); i++){
									for(int j = 0; j < linea.getServiciosAdicionales().size(); j++){
										if(i != j){
											Long idA = linea.getServiciosAdicionales().get(i).getServicioAdicional().getId();
											Long idB = linea.getServiciosAdicionales().get(j).getServicioAdicional().getId();

											if(idA == idB) linea.getServiciosAdicionales().remove(j);
										}
									}
								}
								idRepetidos.clear();
							}
						}// end check
					}
					if (sender == lnkCancelar) {
						hide();
						tblDetalle.removeAllRows();
						datos.refresh();
					}
				}
			};
			
			// Botones
			lnkAceptar = new SimpleLink("ACEPTAR");
			lnkCancelar = new SimpleLink("CANCELAR");

			lnkAceptar.addClickListener(clickListener);
			lnkCancelar.addClickListener(clickListener);

			// Add
			pnlDescripcionReplica.add(gridLayout);
			add(pnlDescripcionReplica);

			pnlTblDetalle.setWidget(tblDetalle);
			pnlDetalleReplica.add(pnlTblDetalle);
			add(pnlDetalleReplica);
			
			addFormButtons(lnkAceptar);
			addFormButtons(lnkCancelar);

			setFormButtonsVisible(true);
			setFooterVisible(false);

			showAndCenter();
		}else{
			ModalMessageDialog.getInstance().showAceptar(
					"No existen otras Solicitudes de Portabilidad para replicar los datos", ModalMessageDialog.getCloseCommand());
		}
	}

	/**
	 * Portabilidad
	 */
	private void asignarNroSSPortabilidad(){
		if(portabilidadUtil == null) portabilidadUtil = new PortabilidadUtil();
		portabilidadUtil.generarNroSS(solicitudServicio);
	}

}
