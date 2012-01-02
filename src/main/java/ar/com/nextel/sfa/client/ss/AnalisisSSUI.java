package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.mapping.Array;

import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.CreateSaveSolicitudServicioResultDto;
import ar.com.nextel.sfa.client.dto.EstadoPorSolicitudDto;
import ar.com.nextel.sfa.client.dto.EstadoSolicitudDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.widget.TitledPanel;
import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import com.google.gwt.dev.asm.Label;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class AnalisisSSUI extends Composite {

	private FlowPanel mainpanel;
	private EditarSSUIController controller;
	private EditarSSUIData editarSSUIData;
	private Grid cambiarEstadoSS;
	private NumberFormat currencyFormat = NumberFormat.getCurrencyFormat();
	private final long pass = 2l;
	private final long fail = 3l;
	private final long aConfirmar = 5l;
	private final long carpetIncompleta = 6l;
	List<Long> opciones = new ArrayList<Long>();

	public AnalisisSSUI(EditarSSUIController controller) {
		mainpanel = new FlowPanel();
		this.controller = controller;
		initWidget(mainpanel);
		this.controller = controller;
		editarSSUIData = controller.getEditarSSUIData();
	
		mainpanel.add(getCambiarEstadoSS());
	}

	private Widget getCambiarEstadoSS() {
		TitledPanel cambiarEstadoPanel = new TitledPanel(Sfa.constant().whiteSpace());
		final SimpleLink cambio = new SimpleLink("Cargar Estados");
		cambio.addStyleName("ml5");
		cambio.addStyleName("infocomSimpleLink");
		
		cambio.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				refresh();
			}
		});
		
		cambiarEstadoPanel.add(cambio);
		cambiarEstadoPanel.add(getEstadoTable());
		Grid nuevoEstado= new Grid(1,2);
		nuevoEstado.setHTML(0,0,Sfa.constant().nuevoEstado());
		nuevoEstado.setWidget(0, 1,editarSSUIData.getNuevoEstado());
        cambiarEstadoPanel.add(nuevoEstado);
		Grid mail = new Grid(7,2);
		mail.addStyleName("layout");
		mail.setHTML(0, 0, Sfa.constant().mail());
		mail.setHTML(0, 1, Sfa.constant().whiteSpace());
		mail.setHTML(1, 0, Sfa.constant().titulo());
		mail.setWidget(1, 1, editarSSUIData.getTitulo());
        mail.setHTML(2, 0,Sfa.constant().comentAnalista() );
		mail.setWidget(2, 1, editarSSUIData.getComentarioAnalista());
		mail.setHTML(3, 0, Sfa.constant().notaAdicional());
     	mail.setWidget(3, 1,editarSSUIData.getNotaAdicional());
		mail.setHTML(4, 0,  Sfa.constant().enviarA());
		mail.setWidget(4, 1,editarSSUIData.getEnviarA());
	    mail.setWidget(5, 0, new CheckBox("Enviar"));
	    mail.setHTML(5, 1, Sfa.constant().whiteSpace());
	    
	    Button ingresarCambio = new Button("Cambiar Estado");
	    ingresarCambio.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addEstado();
			}
	    });
	    
	    editarSSUIData.getNuevoEstado().addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				
				editarSSUIData.getComentarioAnalista().clear();
				if(editarSSUIData.getNuevoEstado().getSelectedItemId() != null){
					if(editarSSUIData.getComentarioAnalistaMensajePorEstado(editarSSUIData.getComentarioAnalistaMensaje(), new Long(editarSSUIData.getNuevoEstado().getSelectedItemId())) != null){						
						editarSSUIData.getComentarioAnalista().addAllItems(editarSSUIData.getComentarioAnalistaMensajePorEstado(editarSSUIData.getComentarioAnalistaMensaje(), new Long(editarSSUIData.getNuevoEstado().getSelectedItemId())));
					}					
				}
			}
		});
//	    mail.setHTML(6, 0, Sfa.constant().whiteSpace());
	    mail.setWidget(6, 0,ingresarCambio);
	    mail.setWidget(6, 1, new Button("Cancelar Cambio"));
	    cambiarEstadoPanel.add(mail);
		return cambiarEstadoPanel;
	}

	private Widget getEstadoTable() {
		SimplePanel wrapper = new SimplePanel();
		wrapper.addStyleName("detalleSSTableWrapper mlr5");
		cambiarEstadoSS = new Grid(1, 3);
		String[] titles = { "Estado", "Fecha", "Usuario"};
		for (int i = 0; i < titles.length; i++) {
			cambiarEstadoSS.setHTML(0, i, titles[i]);
		}
		cambiarEstadoSS.setCellPadding(0);
		cambiarEstadoSS.setCellSpacing(0);
		cambiarEstadoSS.addStyleName("dataTable");
		cambiarEstadoSS.setWidth("98%");
		cambiarEstadoSS.getRowFormatter().addStyleName(0, "header");
		wrapper.add(cambiarEstadoSS);
		return wrapper;
	}

//	/** Realiza la actualizacion visual necesaria para mostrar los datos correctos */
	public void refresh() {
		int row = 1;
		
		if(editarSSUIData.getSolicitudServicio() != null){
		SolicitudServicioDto solicitud = editarSSUIData.getSolicitudServicio();
		cambiarEstadoSS.resizeRows(row);
		if(solicitud.getHistorialEstados().size() == 0){
			cleanGrid();
			opciones.clear();
			opciones.add(pass);
			opciones.add(fail);
			opciones.add(aConfirmar);
			opciones.add(carpetIncompleta);
		}else{
				for (int i = 0; i < solicitud.getHistorialEstados().size() ; i++) {
					cambiarEstadoSS.resizeRows(row + 1);
					//solicitud.getCuenta().getPersona().getRazonSocial()
					if(solicitud.getHistorialEstados().get(i).getEstado() != null){
						cambiarEstadoSS.setHTML(row, 0, solicitud.getHistorialEstados().get(i).getEstado().getItemText());				
					}
					cambiarEstadoSS.setHTML(row, 1, solicitud.getHistorialEstados().get(i).getFecha().toString());
					cambiarEstadoSS.setHTML(row, 2, solicitud.getHistorialEstados().get(i).getUsuario());
					row++;
					if(i == solicitud.getHistorialEstados().size()-1){
						if(solicitud.getHistorialEstados().get(i).getEstado() != null){
							long code = solicitud.getHistorialEstados().get(i).getEstado().getCode();	
							if(code == pass){
								opciones.clear();
								opciones.add(fail);
							}
							else if(code == fail){
								opciones.clear();
								opciones.add(pass);
							}
							else if((code == carpetIncompleta)||(code == aConfirmar)){
								opciones.clear();
								opciones.add(pass);
								opciones.add(fail);
							}
						}
					}				
				}
			}
		
			if(editarSSUIData.getNuevoEstado() != null){
				editarSSUIData.getNuevoEstado().clear();							
				editarSSUIData.getNuevoEstado().addAllItems(editarSSUIData.getOpcionesEstadoPorEstadoIds(editarSSUIData.getOpcionesEstado(), opciones));
			}
		}
	}

	public void cleanGrid(){
		cambiarEstadoSS.resizeRows(2);
		cambiarEstadoSS.setHTML(1, 0, " ");
		cambiarEstadoSS.setHTML(1, 1, " ");
		cambiarEstadoSS.setHTML(1, 2, " ");
	}
	
	private void addEstado(){
		EstadoSolicitudDto nuevoEstado = new EstadoSolicitudDto(new Long(editarSSUIData.getNuevoEstado().getSelectedIndex()+1),editarSSUIData.getNuevoEstado().getSelectedItemText());
		
		EstadoPorSolicitudDto estadoPorSolicitudDto = new EstadoPorSolicitudDto();

		estadoPorSolicitudDto.setEstado(nuevoEstado);
		estadoPorSolicitudDto.setFecha(new Date());
		if(!editarSSUIData.getSolicitudServicio().getNumero().equals("")){
			estadoPorSolicitudDto.setNumeroSolicitud(new Long(editarSSUIData.getSolicitudServicio().getNumero()));			
		}
		String usuario = editarSSUIData.getSolicitudServicio().getUsuarioCreacion().getApellidoYNombre();
		estadoPorSolicitudDto.setUsuario(usuario);
		
		editarSSUIData.getSolicitudServicio().addHistorialEstados(estadoPorSolicitudDto);

//		SolicitudRpcService.Util.getInstance().saveEstadoPorSolicitudDto(estadoPorSolicitudDto, new DefaultWaitCallback<Boolean>() {
//
//			@Override
//			public void success(Boolean result) {
//				refresh();
//			}
//		});
	}
	
	private void cancelarCambio(){
		
	}
//	/** Realiza la actualizacion visual necesaria para mostrar los datos correctos */
//	public void refresh() {
//		int row = 1;
//		double[] totales = { 0, 0, 0, 0, 0, 0, 0, 0 };
//		resumenSS.resizeRows(row);
//		for (LineaSolicitudServicioDto linea : editarSSUIData.getLineasSolicitudServicio()) {
//			linea.refreshPrecioServiciosAdicionales();
//			resumenSS.resizeRows(row + 1);
//			resumenSS.setHTML(row, 0, linea.getItem().getDescripcion());
//			resumenSS.setHTML(row, 1, "" + linea.getCantidad());
//			totales[1] = totales[1] + linea.getCantidad();
//			resumenSS.setHTML(row, 2, linea.getTipoSolicitud().getTipoSolicitudBase() != null ? linea
//					.getTipoSolicitud().getTipoSolicitudBase().getFormaContratacion() : "");
//			resumenSS.setHTML(row, 3, currencyFormat.format(linea.getPrecioVenta()
//					+ linea.getPrecioAlquilerVenta()));
//			//MGR - #1135
//			totales[3] = totales[3] + (linea.getPrecioVenta() * linea.getCantidad() ) 
//							+ (linea.getPrecioAlquilerVenta() * linea.getCantidad());
//			resumenSS.setHTML(row, 4, linea.getPlan() != null ? linea.getPlan().getDescripcion() : "");
//			resumenSS.setHTML(row, 5, currencyFormat.format(linea.getPrecioVentaPlan()));
//			totales[5] = totales[5] + linea.getPrecioVentaPlan();
//			resumenSS.setHTML(row, 6, currencyFormat.format(linea.getPrecioGarantiaVenta()));
//			totales[6] = totales[6] + linea.getPrecioGarantiaVenta();
//			resumenSS.setHTML(row, 7, currencyFormat.format(linea.getPrecioServiciosAdicionalesVenta()));
//			totales[7] = totales[7] + linea.getPrecioServiciosAdicionalesVenta();
//			row++;
//		}
//		resumenSS.resizeRows(row + 1);
//		resumenSS.setHTML(row, 0, "TOTAL");
//		resumenSS.setHTML(row, 1, "" + Math.round(totales[1]));
//		resumenSS.setHTML(row, 3, currencyFormat.format(totales[3]));
//		resumenSS.setHTML(row, 5, currencyFormat.format(totales[5]));
//		resumenSS.setHTML(row, 6, currencyFormat.format(totales[6]));
//		resumenSS.setHTML(row, 7, currencyFormat.format(totales[7]));
//
//		editarSSUIData.recarcularValores();
//	}
}