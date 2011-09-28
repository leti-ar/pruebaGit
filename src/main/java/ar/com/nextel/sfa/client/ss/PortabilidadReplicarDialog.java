package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.widget.ModalMessageDialog;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.nextel.sfa.client.widget.TitledPanel;
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
	private List<Integer> indices = new ArrayList<Integer>();
	
	public PortabilidadReplicarDialog(){
		super(TITULO,false,true);
		addStyleName("gwt-ItemSolicitudDialog");
		
}
	
	public void show(List<LineaSolicitudServicioDto> unasLineas,int indexLinea) {
		lineas = unasLineas;
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
			
			// Datos a Replicar
			pnlDescripcionReplica = new TitledPanel("Datos a Replicar");
			gridLayout = new Grid(5,2);
			gridLayout.addStyleName("layout");
			gridLayout.getColumnFormatter().setWidth(0, "150px");
			gridLayout.getColumnFormatter().setWidth(1, "350px");
			
			gridLayout.setHTML(0,0, "Razon Social:"); 	gridLayout.setHTML(0,1, strRazonSocial);
			gridLayout.setHTML(1,0, "Nombre:");			gridLayout.setHTML(1,1, strNombre);
			gridLayout.setHTML(2,0, "Apellido:");		gridLayout.setHTML(2,1, strApellido);
			gridLayout.setHTML(3,0, "Tipo Documento:");	gridLayout.setHTML(3,1, tipoDocumento.getDescripcion());
			gridLayout.setHTML(4,0, "Nro. Documento:");	gridLayout.setHTML(4,1, strNroDocumento);
			
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
			int indice = 0;
			for (LineaSolicitudServicioDto linea : lineas) {
				if(linea.getPortabilidad() != null && linea.getPortabilidad() != lineas.get(indexLinea).getPortabilidad()){
					tblDetalle.setWidget(newRow, 0, new CheckBox());
					tblDetalle.setHTML(newRow, 1, linea.getItem() != null ? linea.getItem().getDescripcion() : Sfa.constant().whiteSpace());
					tblDetalle.setHTML(newRow, 2, linea.getAlias() != null ? linea.getAlias() : Sfa.constant().whiteSpace());
					tblDetalle.setHTML(newRow, 3, linea.getPlan() != null ? linea.getPlan().getDescripcion() : Sfa.constant().whiteSpace());
					tblDetalle.setHTML(newRow, 4, linea.getTipoSolicitud() != null ? linea.getTipoSolicitud().getDescripcion() : Sfa.constant().whiteSpace());
					indices.add(indice);
					newRow++;
				}
				indice++;
			}

			//Click Listener
			clickListener = new ClickListener() {
				public void onClick(Widget sender) {
					if(sender == lnkAceptar){
						for(int i = 1; i < tblDetalle.getRowCount(); i++){
							if(((CheckBox)tblDetalle.getWidget(i, 0)).getValue()){
								lineas.get(i - 1).getPortabilidad().setRazonSocial(strRazonSocial);
								lineas.get(i - 1).getPortabilidad().setNombre(strNombre);
								lineas.get(i - 1).getPortabilidad().setApellido(strApellido);
								lineas.get(i - 1).getPortabilidad().setTipoDocumento(tipoDocumento);
								lineas.get(i - 1).getPortabilidad().setNumeroDocumento(strNroDocumento);
							}
						}
						hide();
						tblDetalle.removeAllRows();
					}
					if (sender == lnkCancelar) {
						hide();
						tblDetalle.removeAllRows();
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
	
}
