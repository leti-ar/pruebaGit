package ar.com.nextel.sfa.client.widget;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.debug.DebugConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
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
	private int cantRegistrosTot;
	private int cantRegistrosParcI;
	private int cantRegistrosParcF;
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
		cantLabel.addStyleName("mr10");
		registrosMostrados = new HTML(Sfa.constant().registrosMostrados() + " " + cantRegistrosParcI + " - "
				+ cantRegistrosParcF + " de " + cantRegistrosTot);
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

		this.addClickHandler();
	}

	public void setLastVisible(boolean visible) {
		last.setVisible(visible);
		cantLabel.setVisible(visible);
	}

	private void refresh() {
		double calculoCantPaginasOpEnCurso = ((double) cantRegistrosTot / (double) cantResultadosPorPagina);
		cantPaginas = (int) Math.ceil(calculoCantPaginasOpEnCurso);
		if (cantPaginas == 0) {
			cantPaginas = 1;
		}
		if (pagina > cantPaginas) {
			pagina = cantPaginas;
		}
		pagLabel.setHTML("Pág " + pagina);
		cantLabel.setHTML(" de " + cantPaginas);
		cantRegistrosParcI = (pagina - 1) * cantResultadosPorPagina + 1;
		cantRegistrosParcF = pagina * cantResultadosPorPagina;
		if (cantRegistrosParcF > cantRegistrosTot) {
			cantRegistrosParcF = cantRegistrosTot;
		}
		registrosMostrados.setText(Sfa.constant().registrosMostrados() + " " + cantRegistrosParcI + " - "
				+ cantRegistrosParcF + " de " + cantRegistrosTot);
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
		if (cantResultadosPorPagina > 0 && cantRegistrosTot > 0) {
			refresh();
		}
	}

	public int getCantPaginas() {
		return cantPaginas;
	}

	public void setCantPaginas(int cantPaginas) {
		this.cantPaginas = cantPaginas;
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

	public void addClickHandler() {
		ClickHandler handler = new ClickHandler() {
			public void onClick(ClickEvent clickEvent) {
				Widget sender = (Widget) clickEvent.getSource();
				if (sender == next || sender == last) {
					if (pagina == cantPaginas) {
						return;
					}
					setPagina(sender == next ? ++pagina : cantPaginas);
				} else if (sender == prev || sender == first) {
					if (pagina == 1) {
						return;
					}
					setPagina(sender == prev ? --pagina : 1);
				}
				last.setEnabled(pagina != cantPaginas);
				next.setEnabled(pagina != cantPaginas);
				first.setEnabled(pagina != 1);
				prev.setEnabled(pagina != 1);
				if (beforeClickCommand != null) {
					beforeClickCommand.execute();
				}
			}
		};

		first.addClickHandler(handler);
		next.addClickHandler(handler);
		last.addClickHandler(handler);
		prev.addClickHandler(handler);
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
		if (cantResultadosPorPagina > 0) {
			refresh();
		}
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

}
