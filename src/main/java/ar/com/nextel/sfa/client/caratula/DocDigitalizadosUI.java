package ar.com.nextel.sfa.client.caratula;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.DocDigitalizadosDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.LoadingModalDialog;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.nextel.sfa.client.widget.NextelTable;
import ar.com.nextel.sfa.client.widget.TablePageBar;
import ar.com.nextel.util.AppLogger;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.util.WindowUtils;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class DocDigitalizadosUI extends NextelDialog implements ClickHandler{

	private static DocDigitalizadosUI instance = null;
	
	private Label nroCaratulasDig;
	private NextelTable resultTableDocumentos;
	private TablePageBar tablePageBarDocDigitalizados;
	private SimplePanel resultTableWrapperDocDig;
	private FlowPanel flowPanelDocDig;
	private FormButtonsBar footerBar;
	private SimpleLink cerrar;
	
	private int cantResultadosPorPagina = 20;
	List<DocDigitalizadosDto> documentos;
	List<DocDigitalizadosDto> docDigActuales;

	public static DocDigitalizadosUI getInstance(){
		if(instance == null){
			instance = new DocDigitalizadosUI();
		}
		return instance;
	}
	
	private DocDigitalizadosUI() {
		super(Sfa.constant().docDigitalizados(), false, true);
		init();
	}
	
	private void init(){
		setWidth("500px");
		setHeight("400px");

		nroCaratulasDig = new Label();
		nroCaratulasDig.addStyleName("docDigitalizadosLabel");
		
		resultTableWrapperDocDig = new SimplePanel();
		resultTableWrapperDocDig.addStyleName("resultTableWrapper");
		resultTableWrapperDocDig.setHeight("300px");
		
		resultTableDocumentos = new NextelTable();
		resultTableWrapperDocDig.add(resultTableDocumentos);
		resultTableDocumentos.setHeight("150px");
		resultTableDocumentos.addClickHandler(this);
		
		tablePageBarDocDigitalizados = new TablePageBar();
		tablePageBarDocDigitalizados.addStyleName("mlr8");
		
		tablePageBarDocDigitalizados.setCantResultadosPorPagina(cantResultadosPorPagina);
		
		tablePageBarDocDigitalizados.setBeforeClickCommand(new Command() {
			public void execute() {
				setDocDigitalizados();
			}
		});
		
		
		flowPanelDocDig = new FlowPanel();
		flowPanelDocDig.add(resultTableWrapperDocDig);
		
		cerrar = new SimpleLink("Cerrar");
		cerrar.setStyleName("link");
		cerrar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				hide();
			}
		});
		
		footerBar = new FormButtonsBar();
		footerBar.addLink(cerrar);
		
		add(nroCaratulasDig);
		add(flowPanelDocDig);
		add(tablePageBarDocDigitalizados);
		add(footerBar);
		setVisible(true);
	}
	
	public void setDocDigitalizados() {
		tablePageBarDocDigitalizados.setCantRegistrosTot(documentos.size());
		docDigActuales = new ArrayList<DocDigitalizadosDto>();
		for (int i = (tablePageBarDocDigitalizados.getCantRegistrosParcI() - 1); i < tablePageBarDocDigitalizados
				.getCantRegistrosParcF(); i++) {
			docDigActuales.add(documentos.get(i));
		}
		initTableDocDigitalizados(resultTableDocumentos);
		loadTableDocDigitalizados(docDigActuales);
	}
	
	private void loadTableDocDigitalizados(List<DocDigitalizadosDto> docDigActuales) {
		resultTableDocumentos.clearContent();
		resultTableWrapperDocDig.setWidget(resultTableDocumentos);
		int rowIndex = 1;
		for (DocDigitalizadosDto docDigDto : docDigActuales) {
			resultTableDocumentos.setWidget(rowIndex, 0, IconFactory.imagenTIF());
			resultTableDocumentos.setHTML(rowIndex, 1, docDigDto.getDescripcion());
			
			if(docDigDto.getFecha() != null){
				resultTableDocumentos.setHTML(rowIndex, 2, DateTimeFormat.getShortDateFormat().format(docDigDto.getFecha()));
			}else{
				resultTableDocumentos.setHTML(rowIndex, 2, Sfa.constant().whiteSpace());
			}
			rowIndex++;
		}
		nroCaratulasDig.setText(Sfa.constant().nroDocDigitalizados() + documentos.size());

		setVisible(true);
		showAndCenter();
	}
	
	private void initTableDocDigitalizados(FlexTable table) {
		String[] widths = { "30px", "400px", "200px", };
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.getColumnFormatter().addStyleName(0, "alignCenter");
		table.getColumnFormatter().addStyleName(1, "alignCenter");
		table.getColumnFormatter().addStyleName(2, "alignCenter");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarDocDigResultTable");
		table.getRowFormatter().addStyleName(0, "header");
		table.setHTML(0, 0, Sfa.constant().whiteSpace());
		table.setHTML(0, 1, Sfa.constant().descripcion());
		table.setHTML(0, 2, Sfa.constant().fechaCreacion());
	}
	
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		Cell cell;
		if (sender == resultTableDocumentos) {
			cell = resultTableDocumentos.getCellForEvent(event);
			if (cell == null || cell.getRowIndex() <= 0) {
				return;
			}
			
			if (cell.getCellIndex() == 0) {
				int listPosition = tablePageBarDocDigitalizados.getCantRegistrosParcI() + cell.getRowIndex() - 2;
				final DocDigitalizadosDto docDig = documentos.get(listPosition);
				final String contextRoot = WindowUtils.getContextRoot();
				final String pahtAndNameFile = docDig.getServer() + docDig.getPaht() + docDig.getNombre();

				SolicitudRpcService.Util.getInstance().existDocDigitalizado(pahtAndNameFile, new DefaultWaitCallback<Boolean>() {
					
					public void success(Boolean result) {
						LoadingModalDialog.getInstance().hide();
						if(result){
							
								WindowUtils.redirect("/" + contextRoot + "/download/" + pahtAndNameFile
										+ "?module=cuentas&service=tif&name=" + pahtAndNameFile);
						}else {
							MessageDialog.getInstance().showAceptar(ErrorDialog.AVISO, Sfa.constant().ERR_FILE_NOT_FOUND(),
									MessageDialog.getCloseCommand());
						}
					}
				});
			}
		}
	}
	
	public void onClick(Widget sender) {
		DocDigitalizadosDto docDigitalizado = null;
		int offset = 0;
		if (resultTableDocumentos.getRowSelected() > 0) {
			if (resultTableDocumentos.getRowSelected() > 0) {
				offset = (tablePageBarDocDigitalizados.getPagina() - 1) * tablePageBarDocDigitalizados.getCantResultadosPorPagina();
				docDigitalizado = documentos.get(offset + (resultTableDocumentos.getRowSelected() - 1));
			}
		} else {
			MessageDialog.getInstance().showAceptar(ErrorDialog.AVISO,
					Sfa.constant().ERR_NO_DOCUMENTO_SELECTED(), MessageDialog.getCloseCommand());
		}
	}
	
	public void cargarDocDigitalizados(String customerCode){
		CuentaRpcService.Util.getInstance().getDocDigitalizados(customerCode,
				new DefaultWaitCallback<List<DocDigitalizadosDto>>() {
					
					public void success(List<DocDigitalizadosDto> result) {
						
						if(result == null || result.isEmpty()){
							MessageDialog.getInstance().showAceptar(Sfa.constant().busquedaDocDigitalizados(),
									Sfa.constant().ERR_DOC_DIGITALIZADOS_NO_ENCONTRADOS(),
									MessageDialog.getCloseCommand());
						}else{
							documentos = result;
							tablePageBarDocDigitalizados.setPagina(1);
							setDocDigitalizados();	
						}
					}
				});
	}
}
