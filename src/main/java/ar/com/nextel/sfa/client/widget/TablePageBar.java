package ar.com.nextel.sfa.client.widget;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.debug.DebugConstants;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * Barra para manejar el paginado en una tabla. No contiene la lógica del paginado, pero ermite cargarle un
 * comando que si lo haga.
 * 
 * @author jlgperez
 * 
 */
public class TablePageBar extends Composite {

	private FlowPanel mainPanel;
	private Button first;
	private Button prev;
	private Button next;
	private Button last;
	private InlineHTML pagLabel;
	private InlineHTML cantLabel;
	private HTML registrosMostrados;
	private int cantResultados;
	private int cantRegistrosTot;
	private int cantRegistrosParcI;
	private int cantRegistrosParcF;
	private int offset = 1;
	private int pagina = 1;
	private int cantPaginas = 1;
	private int cantResultadosPorPagina;
	private Command beforeClickCommand;

	public TablePageBar() {
		mainPanel = new FlowPanel();
		initWidget(mainPanel);

		first = new Button();
		prev = new Button();
		next = new Button();
		last = new Button();
		pagLabel = new InlineHTML("Pág " + pagina);
		cantLabel = new InlineHTML(" de " + cantPaginas);
		registrosMostrados = new HTML(Sfa.constant().registrosMostrados() + " " + cantRegistrosParcI + " - " + cantRegistrosParcF +" de " + cantRegistrosTot);
		mainPanel.add(registrosMostrados);
		mainPanel.add(first);
		mainPanel.add(prev);
		mainPanel.add(pagLabel);
		mainPanel.add(cantLabel);
		mainPanel.add(next);
		mainPanel.add(last);
		registrosMostrados.addStyleName("floatRight");
		first.addStyleName("btn-first");
		prev.addStyleName("btn-prev");
		next.addStyleName("btn-next");
		last.addStyleName("btn-last");
		// Seteo los debug Ids para c/u de los botones al mismo nombre que el estilo
		first.ensureDebugId(DebugConstants.TABLE_PAGE_BAR_FIRST);
		prev.ensureDebugId(DebugConstants.TABLE_PAGE_BAR_PREV);
		next.ensureDebugId(DebugConstants.TABLE_PAGE_BAR_NEXT);
		last.ensureDebugId(DebugConstants.TABLE_PAGE_BAR_LAST);

		pagLabel.addStyleName("ml10");

		this.addListener();
	}

	public void setLastVisible(boolean visible) {
		last.setVisible(visible);
		cantLabel.setVisible(visible);
	}

	public void setCantResultados(int cantResultados) {
		this.cantResultados = cantResultados;
	}

	public int getPagina() {
		return pagina;
	}

	
	public void setPagina(int pagina) {
		this.pagina = pagina;
		double calculoCantPaginas = ((double) (cantResultados / (double) cantResultadosPorPagina));
		this.cantPaginas =  (int) Math.ceil(calculoCantPaginas);
		pagLabel.setText("Pág " + pagina);
		cantLabel.setText(" de " + cantPaginas);
	}
	
	public void refrescaLabelRegistros(){
		registrosMostrados.setText(Sfa.constant().registrosMostrados() + " " + cantRegistrosParcI + " - " + cantRegistrosParcF +" de " + cantRegistrosTot);
	}
	
	public int getCantPaginas() {
		return cantPaginas;
	}

	public void setCantPaginas(int cantPaginas) {
		this.cantPaginas = cantPaginas;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public int getCantResultadosPorPagina() {
		return cantResultadosPorPagina;
	}

	public void setCantResultadosPorPagina(int cantResultadosPorPagina) {
		this.cantResultadosPorPagina = cantResultadosPorPagina;
	}

	public void setBeforeClickCommand(Command command) {
		this.beforeClickCommand = command;
	}

	public void addListener() {
		ClickListener listener = new ClickListener() {
			public void onClick(Widget sender) {
				if (sender == next) {
					last.setEnabled(true);
					next.setEnabled(true);
					first.setEnabled(true);
					prev.setEnabled(true);
					if (getPagina()==cantPaginas) {
						return;
					}
					setPagina(getPagina() + 1);
					setCantRegistrosTot(cantRegistrosTot);
					if (getPagina() != cantPaginas) {						
						setCantRegistrosParcI(cantRegistrosParcI + cantResultadosPorPagina);
						setCantRegistrosParcF(cantRegistrosParcF + cantResultadosPorPagina);
					} else {
						setPagina(getCantPaginas());
						setCantRegistrosParcF(cantRegistrosTot);
						int numResultados =  (cantRegistrosTot - (cantResultadosPorPagina * (cantPaginas-1))) - 1;
						setCantRegistrosParcI(cantRegistrosTot - numResultados);
					}	
					refrescaLabelRegistros();
				} else if (sender == first) {
					last.setEnabled(true);
					next.setEnabled(true);
					if (getPagina() == 1) {
						return;
					}
					setPagina(1);
					setCantRegistrosParcI(1);
					first.setEnabled(false);
					prev.setEnabled(false);
					if (cantResultadosPorPagina > cantRegistrosTot) {
						setCantRegistrosParcF(cantRegistrosTot);	
					} else {
						setCantRegistrosParcF(cantResultadosPorPagina);
					}					
					refrescaLabelRegistros();

				} else if (sender == last) {
					first.setEnabled(true);
					prev.setEnabled(true);
					setPagina(getCantPaginas());
					setCantRegistrosParcF(cantRegistrosTot);
					int numResultados =  (cantRegistrosTot - (cantResultadosPorPagina * (cantPaginas-1))) - 1;
					setCantRegistrosParcI(cantRegistrosTot - numResultados);
					refrescaLabelRegistros();
					last.setEnabled(false);
					next.setEnabled(false);
				} else if (sender == prev) {
					last.setEnabled(true);
					next.setEnabled(true);
					first.setEnabled(true);
					prev.setEnabled(true);
					if (getPagina() == 1) {
						return;
					} else if (getPagina()==cantPaginas) {
						setPagina(getPagina() - 1);
						setCantRegistrosTot(cantRegistrosTot);
						int calculo = cantResultadosPorPagina*(cantPaginas-1);
						setCantRegistrosParcI(calculo-(cantResultadosPorPagina-1));
						setCantRegistrosParcF(calculo);
						refrescaLabelRegistros();

					} else {
						setPagina(getPagina() - 1);
						setCantRegistrosTot(cantRegistrosTot);
						setCantRegistrosParcI(cantRegistrosParcI - cantResultadosPorPagina);
						setCantRegistrosParcF(cantRegistrosParcF - cantResultadosPorPagina);
						refrescaLabelRegistros();
					}
				}
				if (beforeClickCommand != null) {
					beforeClickCommand.execute();
				}
			}
		};

		first.addClickListener(listener);
		next.addClickListener(listener);
		last.addClickListener(listener);
		prev.addClickListener(listener);
	}
	

	public void newSearch(String buttonName) {
	}

	public Button getFirst() {
		return first;
	}

	public Button getPrev() {
		return prev;
	}

	public Button getNext() {
		return next;
	}

	public Button getLast() {
		return last;
	}

	public int getCantRegistrosTot() {
		return cantRegistrosTot;
	}
	public void setCantRegistrosTot(int cantRegistrosTot) {
		this.cantRegistrosTot = cantRegistrosTot;
	}

	public int getCantRegistrosParcI() {
		return cantRegistrosParcI;
	}

	public int getCantRegistrosParcF() {
		return cantRegistrosParcF;
	}

	public void setCantRegistrosParcI(int cantRegistrosParcI) {
		this.cantRegistrosParcI = cantRegistrosParcI;
	}

	public void setCantRegistrosParcF(int cantRegistrosParcF) {
		this.cantRegistrosParcF = cantRegistrosParcF;
	}

	public int getCantResultados() {
		return cantResultados;
	}
}
