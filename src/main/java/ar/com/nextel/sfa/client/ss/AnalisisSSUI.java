package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.constant.SfaStatic;
import ar.com.nextel.sfa.client.dto.EstadoPorSolicitudDto;
import ar.com.nextel.sfa.client.dto.EstadoSolicitudDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.widget.TitledPanel;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
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
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class AnalisisSSUI extends Composite {

	private FlowPanel mainpanel;
	private EditarSSUIController controller;
	private EditarSSUIData editarSSUIData;
	private Grid cambiarEstadoSS;
	private NumberFormat currencyFormat = NumberFormat.getCurrencyFormat();
	private long pass;
	private long fail;
	List<Long> opciones = new ArrayList<Long>();
	
	Button cancelarCambio = new Button("Cancelar Cambio");
	//Button ingresarCambio = new Button("Cambiar Estado");
	 
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
				editarSSUIData.getComentarioAnalista().setEnabled(true);
				editarSSUIData.getEnviarA().setEnabled(true);
				editarSSUIData.getNuevoEstado().setEnabled(true);
				editarSSUIData.getTitulo().setEnabled(true);
				editarSSUIData.getNotaAdicional().setEnabled(true);
				editarSSUIData.getEnviar().setEnabled(true);
				cancelarCambio.setEnabled(true);
				refresh();
			}
		});
		
		cambiarEstadoPanel.add(cambio);
		cambiarEstadoPanel.add(getEstadoTable());
		Grid nuevoEstado= new Grid(1,2);
		nuevoEstado.setHTML(0,0,Sfa.constant().nuevoEstado());
		nuevoEstado.setWidget(0, 1,editarSSUIData.getNuevoEstado());
        cambiarEstadoPanel.add(nuevoEstado);
    	Grid mail = new Grid(8,2);
	
		mail.addStyleName("layout");
		mail.setHTML(0, 0, Sfa.constant().mail());
		mail.setHTML(0, 1, Sfa.constant().whiteSpace());
		mail.setHTML(1, 0, Sfa.constant().titulo());
		mail.setWidget(1, 1, editarSSUIData.getTitulo());
        mail.setHTML(2, 0,Sfa.constant().comentAnalista() );
		mail.setWidget(2, 1, editarSSUIData.getComentarioAnalista());
		mail.setHTML(3, 0, Sfa.constant().notaAdicional());
     	mail.setWidget(3, 1,editarSSUIData.getNotaAdicional());
    	
     	mail.setHTML(4, 0, "Cant. Equipos");
	    mail.setWidget(4, 1, editarSSUIData.getCantEquipos());
	    
		mail.setHTML(5, 0,  Sfa.constant().enviarA());
		mail.setWidget(5, 1,editarSSUIData.getEnviarA());
		
		mail.setWidget(6, 0,editarSSUIData.getEnviar() );
		mail.setHTML(6, 1, Sfa.constant().whiteSpace());
	    
	   
//	    ingresarCambio.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				addEstado();
//				
//			}
//	    });
	    
	    editarSSUIData.getNuevoEstado().addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				
				editarSSUIData.getComentarioAnalista().clear();
				if(editarSSUIData.getNuevoEstado().getSelectedItemId() != null){
					if(editarSSUIData.getComentarioAnalistaMensajePorEstado(editarSSUIData.getComentarioAnalistaMensaje(), new Long(editarSSUIData.getNuevoEstado().getSelectedItemId())) != null){						
						editarSSUIData.getComentarioAnalista().addAllItems(editarSSUIData.getComentarioAnalistaMensajePorEstado(editarSSUIData.getComentarioAnalistaMensaje(), new Long(editarSSUIData.getNuevoEstado().getSelectedItemId())));
					}					
				}
				editarSSUIData.getComentarioAnalista().setSelectedIndex(0);
			}
		});
       mail.setHTML(7, 0,Sfa.constant().whiteSpace());
	    
	   
	    cancelarCambio.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cancelarCambio();
				
			}
	    });
	    
	    mail.setWidget(7, 1, cancelarCambio);
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
		desHabilitarCampos();
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
			
		pass = getCodigoEstadoPass(editarSSUIData.getOpcionesEstado());
		fail = getCodigoEstadoFail(editarSSUIData.getOpcionesEstado());
			
		SolicitudServicioDto solicitud = editarSSUIData.getSolicitudServicio();
		cambiarEstadoSS.resizeRows(row);
		if(solicitud.getHistorialEstados().size() == 0){
			cleanGrid();
			opciones.clear();
			addOpcionesIgnorandoEnCarga(opciones, editarSSUIData.getOpcionesEstado());
			
		}else{
				for (int i = 0; i < solicitud.getHistorialEstados().size() ; i++) {
					cambiarEstadoSS.resizeRows(row + 1);
					if(solicitud.getHistorialEstados().get(i).getEstado() != null){
						cambiarEstadoSS.setHTML(row, 0, solicitud.getHistorialEstados().get(i).getEstado().getItemText());				
					}
					cambiarEstadoSS.setHTML(row, 1, solicitud.getHistorialEstados().get(i).getFecha().toString());
					//esto lo tuve q cambiar ver si es correcto  estefania iguacel
					cambiarEstadoSS.setHTML(row, 2,"");
					row++;
					if(i == solicitud.getHistorialEstados().size()-1){
						if(solicitud.getHistorialEstados().get(i).getEstado() != null){
							long code = solicitud.getHistorialEstados().get(i).getEstado().getCode();	
							opciones.clear();
							if(code == pass){
								opciones.add(fail);
							}
							else if(code == fail){
								opciones.add(pass);
							}
							else {
								opciones.add(pass);
								opciones.add(fail);
							}
						}
						editarSSUIData.getComentarioAnalista().setSelectedIndex(0);
					}				
				}
			}
		
			if(editarSSUIData.getNuevoEstado() != null){
				editarSSUIData.getNuevoEstado().clear();							
				editarSSUIData.getNuevoEstado().addAllItems(editarSSUIData.getOpcionesEstadoPorEstadoIds(editarSSUIData.getOpcionesEstado(), opciones));
			}
			
			if(editarSSUIData.getCantEquipos() != null){
				
				SolicitudRpcService.Util.getInstance().calcularCantEquipos(solicitud.getLineas(), new DefaultWaitCallback<Integer>() {
					
					@Override
					public void success(Integer result) {
						editarSSUIData.getCantEquipos().setText(result+"");			
					}
				});
				
			}
		}
	}

	public void cleanGrid(){
		cambiarEstadoSS.resizeRows(2);
		cambiarEstadoSS.setHTML(1, 0, " ");
		cambiarEstadoSS.setHTML(1, 1, " ");
		cambiarEstadoSS.setHTML(1, 2, " ");
	}
	
//	private void addEstado(){
//		ingresarCambio.setEnabled(false);
//		if(editarSSUIData.getNuevoEstado().getSelectedItemText() != null){
//			String descripcionEstado = editarSSUIData.getNuevoEstado().getSelectedItemText();
//			
//			EstadoSolicitudDto nuevoEstado = editarSSUIData.getEstadoPorEstadoText(editarSSUIData.getOpcionesEstado(), descripcionEstado);
//			EstadoPorSolicitudDto estadoPorSolicitudDto = new EstadoPorSolicitudDto();
//			estadoPorSolicitudDto.setEstado(nuevoEstado);
//			estadoPorSolicitudDto.setFecha(new Date());
//			
//			if(!editarSSUIData.getSolicitudServicio().getNumero().equals("")){
//				estadoPorSolicitudDto.setNumeroSolicitud(new Long(editarSSUIData.getSolicitudServicio().getId()));			
//			}
//			
//		//	String usuario = editarSSUIData.getSolicitudServicio().getUsuarioCreacion().getApellidoYNombre();
//			//ver esta persistencia
//			estadoPorSolicitudDto.setUsuario(editarSSUIData.getSolicitudServicio().getUsuarioCreacion().getId());
//			
//			editarSSUIData.getSolicitudServicio().addHistorialEstados(estadoPorSolicitudDto);
//			
////		SolicitudRpcService.Util.getInstance().saveEstadoPorSolicitudDto(estadoPorSolicitudDto, new DefaultWaitCallback<Boolean>() {
////
////			@Override
////			public void success(Boolean result) {
//			refresh();
//			editarSSUIData.getComentarioAnalista().clear();
////			}
////		});
//		}
//	}
	
	private void cancelarCambio(){
		desHabilitarCampos();
	}
	
	public void addOpcionesIgnorandoEnCarga(List<Long> opcionesACargar,List<EstadoSolicitudDto> opcionesTotal){
		for (int i = 0; i < opcionesTotal.size() ; i++) {
			if((!opcionesTotal.get(i).getDescripcion().equals("en carga"))&&(!opcionesTotal.get(i).getDescripcion().equals("En carga"))&&(!opcionesTotal.get(i).getDescripcion().equals("En Carga"))){
				opcionesACargar.add(opcionesTotal.get(i).getCode());
			}
		}
	}
	
	public long getCodigoEstadoFail(List<EstadoSolicitudDto> opcionesTotal){
		for (int i = 0; i < opcionesTotal.size() ; i++) {
			if((opcionesTotal.get(i).getDescripcion().equals("fail"))||(opcionesTotal.get(i).getDescripcion().equals("Fail"))||(opcionesTotal.get(i).getDescripcion().equals("FAIL"))){
				return opcionesTotal.get(i).getCode();
			}
		}
		return 0l;
	}
	
	public long getCodigoEstadoPass(List<EstadoSolicitudDto> opcionesTotal){
		for (int i = 0; i < opcionesTotal.size() ; i++) {
			if((opcionesTotal.get(i).getDescripcion().equals("pass"))||(opcionesTotal.get(i).getDescripcion().equals("Pass"))||(opcionesTotal.get(i).getDescripcion().equals("PASS"))){
				return opcionesTotal.get(i).getCode();
			}
		}
		return 0l;
	}
	
	public void  desHabilitarCampos() {
		editarSSUIData.getEnviarA().setEnabled(false);
		editarSSUIData.getNuevoEstado().setEnabled(false);
		editarSSUIData.getTitulo().setEnabled(false);
		editarSSUIData.getComentarioAnalista().setEnabled(false);
		editarSSUIData.getNotaAdicional().setEnabled(false);
		editarSSUIData.getEnviar().setEnabled(false);
		cancelarCambio.setEnabled(false);
	}
	
	
}