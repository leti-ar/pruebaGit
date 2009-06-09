package ar.com.nextel.sfa.client.ss;

import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.DetalleSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaResultDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.util.WindowUtils;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;

/**
 * Muestra la tabla correspondiente a las SS cerradas, resultado del buscador.
 * 
 * @author juliovesco
 * 
 */
public class BuscarSSCerradasResultUI extends FlowPanel {

	private FlexTable resultTable;
	private FlowPanel resultTableWrapper;
	private List<SolicitudServicioCerradaResultDto> solicitudesServicioCerradaResultDto;
	private Long totalRegistrosBusqueda;
	private BuscarSSTotalesResultUI buscarSSTotalesResultUI;
	private CambiosSSCerradasResultUI cambiosSSCerradasResultUI;
	private Long cantEquipos = new Long(0);
	private Double cantPataconex = new Double(0);
	private int cantEqFirmados = 0;
	private SimplePanel exportarExcel;
	private SolicitudServicioCerradaDto solicitudServicioCerradaDto;

	public Long getTotalRegistrosBusqueda() {
		return totalRegistrosBusqueda;
	}

	public void setTotalRegistrosBusqueda(Long totalRegistrosBusqueda) {
		this.totalRegistrosBusqueda = totalRegistrosBusqueda;
	}

	public BuscarSSCerradasResultUI() {
		super();
		addStyleName("gwt-BuscarCuentaResultPanel");
		resultTableWrapper = new FlowPanel();
		resultTableWrapper.addStyleName("resultTableWrapper");
		setVisible(false);
	}

	/**
	 * Metodo publico que contiene lo que se desea ejecutar la primera vez que se busca. (o sea, cuando se
	 * hace click al boton Buscar)
	 * 
	 * @param: cuentaSearchDto
	 * */
	public void searchSSCerradas(SolicitudServicioCerradaDto solicitudServicioSearchDto) {
		this.searchSSCerradas(solicitudServicioSearchDto, true);
	};

	private void searchSSCerradas(SolicitudServicioCerradaDto solicitudServicioCerradaDto, boolean firstTime) {
		this.solicitudServicioCerradaDto = solicitudServicioCerradaDto;
		SolicitudRpcService.Util.getInstance().searchSSCerrada(solicitudServicioCerradaDto,
				new DefaultWaitCallback<List<SolicitudServicioCerradaResultDto>>() {
			public void success(List<SolicitudServicioCerradaResultDto> result) {
				if (result != null) {
					if (result.size()==0) {
						MessageDialog.getInstance().showAceptar("No se encontraron datos con el " +
								"criterio utilizado", MessageDialog.getCloseCommand());
						cantEquipos = new Long(0);
						cantPataconex = new Double(0);
						cantEqFirmados = 0;		
					}					
					loadExcel();
					setSolicitudServicioDto(result);
					buscarSSTotalesResultUI.setValues(cantEquipos.toString(), cantPataconex.toString(), String.valueOf(cantEqFirmados));
					buscarSSTotalesResultUI.setVisible(true);					
				}
			}
		});
	}

	public void setSolicitudServicioDto(List<SolicitudServicioCerradaResultDto> solicitudServicioCerradaResultDto) {
		this.solicitudesServicioCerradaResultDto = solicitudServicioCerradaResultDto;
		add(loadTable());
		setVisible(true);
	}

	private void loadExcel() {
		if (exportarExcel != null) {
			exportarExcel.unsinkEvents(Event.getEventsSunk(exportarExcel.getElement()));
			exportarExcel.removeFromParent();
		}
		exportarExcel = new SimplePanel();
		exportarExcel.setHeight("23px");
		exportarExcel.setWidth("1089");
		HTML icon = IconFactory.excel();
		icon.addStyleName("exportarExcelSS");
		exportarExcel.add(icon);	
		resultTableWrapper.add(exportarExcel);
		icon.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				SolicitudRpcService.Util.getInstance().buildExcel(solicitudServicioCerradaDto, 
				new DefaultWaitCallback<String>() {
					public void success(String result) {
						if (result != null) {
							WindowUtils.redirect("/download/download?module=solicitudes&service=xls&name="+result+".xls");
						}
					}
				}); 
			}
		});
	}

	private Widget loadTable() {
		if (resultTable != null) {
			resultTable.unsinkEvents(Event.getEventsSunk(resultTable.getElement()));
			resultTable.removeFromParent();
		}
		resultTable = new FlexTable();
		resultTable.addTableListener(new Listener());
		initTable(resultTable);
		resultTableWrapper.add(resultTable);
		int row = 1;

		if (solicitudesServicioCerradaResultDto != null) {
			for (Iterator iter = solicitudesServicioCerradaResultDto.iterator(); iter.hasNext();) {
				SolicitudServicioCerradaResultDto solicitudServicioCerradaResultDto = (SolicitudServicioCerradaResultDto) iter
						.next();
				resultTable.setWidget(row, 0, IconFactory.word());
				resultTable.setHTML(row, 1, solicitudServicioCerradaResultDto.getNumero());
				resultTable.setHTML(row, 2, solicitudServicioCerradaResultDto.getNumeroCuenta());
				resultTable.setHTML(row, 3, solicitudServicioCerradaResultDto.getRazonSocialCuenta());
				resultTable.setHTML(row, 4, solicitudServicioCerradaResultDto.getCantidadEquiposPorCuenta()
						.toString());
				resultTable.setHTML(row, 5, solicitudServicioCerradaResultDto.getPataconex().toString());
				if (solicitudServicioCerradaResultDto.getFirmar().booleanValue() == true) {
					resultTable.setWidget(row, 6, IconFactory.tildeVerde());
				}
				if (solicitudServicioCerradaResultDto.getFirmar().booleanValue() == Boolean.TRUE) {
					cantEqFirmados++;
				}
				cantEquipos = cantEquipos + solicitudServicioCerradaResultDto.getCantidadEquipos();
				cantPataconex = cantPataconex + solicitudServicioCerradaResultDto.getPataconex();
				row++;
			}
		}

		return resultTableWrapper;
	}

	private void initTable(FlexTable table) {
		String[] widths = { "24px", "150px", "200px", "320px", "100px", "100px", "100px", };
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.getColumnFormatter().addStyleName(0, "alignCenter");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarCuentaResultTable");
		table.getRowFormatter().addStyleName(0, "header");
		table.setHTML(0, 0, Sfa.constant().whiteSpace());
		table.setHTML(0, 1, "N° SS");
		table.setHTML(0, 2, "N° Cuenta");
		table.setHTML(0, 3, "Razon Social");
		table.setHTML(0, 4, "Equipos");
		table.setHTML(0, 5, "Pataconex");
		table.setHTML(0, 6, "Firmas");
	}

	public void setBuscarSSTotalesResultUI(BuscarSSTotalesResultUI buscarSSTotalesResultUI) {
		this.buscarSSTotalesResultUI = buscarSSTotalesResultUI;
	}

	private class Listener implements TableListener {

		public void onCellClicked(SourcesTableEvents arg0, int arg1, int arg2) {
			String numeroSS = resultTable.getHTML(arg1, 1).toString();
			SolicitudServicioCerradaResultDto solicitud = buscarSS(numeroSS);
			if ((arg1 >= 1) && (arg2>=1)) {
				SolicitudRpcService.Util.getInstance().getDetalleSolicitudServicio(solicitud.getId(),
						new DefaultWaitCallback<DetalleSolicitudServicioDto>() {
							public void success(DetalleSolicitudServicioDto result) {
								cambiosSSCerradasResultUI.setSolicitudServicioCerradaDto(result);
								cambiosSSCerradasResultUI.setVisible(true);
							}
						});
			} else if ((arg1>=1) && (arg2==0)){
			
				if (solicitud.isCliente()) {
			        // Si es cliente usamos el codigo Vantive, sino el Id (ya que no podemos 
			        // guardar archivos con los caracteres de VANCUC
					WindowUtils.redirect("/download/download?module=solicitudes&service=rtf&name="+solicitud.getIdVantive().toString()+"-5-"+numeroSS+".rtf");
			        } else {
			        	WindowUtils.redirect("/download/download?module=solicitudes&service=rtf&name="+solicitud.getId().toString()+"-5-"+numeroSS+".rtf");
			        }
			}
		}
	}

	private SolicitudServicioCerradaResultDto buscarSS(String numeroSS) {
		for (Iterator iterator = solicitudesServicioCerradaResultDto.iterator(); iterator.hasNext();) {
			SolicitudServicioCerradaResultDto solicitudServicioDto = (SolicitudServicioCerradaResultDto) iterator
					.next();
			if (solicitudServicioDto.getNumero().equals(numeroSS)) {
				return solicitudServicioDto;
			}
		}
		return null;
	}

	public void setCambiosSSCerradasResultUI(CambiosSSCerradasResultUI cambiosSSCerradasResultUI) {
		this.cambiosSSCerradasResultUI = cambiosSSCerradasResultUI;
	}
	
}
