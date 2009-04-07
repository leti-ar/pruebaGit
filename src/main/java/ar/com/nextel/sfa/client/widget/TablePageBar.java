package ar.com.nextel.sfa.client.widget;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
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
	private int cantResultados;
	private int cantResultadosVisibles;
	private int offset = 0;
	private int pagina = 1;
	private int cantPaginas = 1;
	private Command beforeClickCommand;

	public TablePageBar() {
		mainPanel = new FlowPanel();
		initWidget(mainPanel);

		first = new Button();
		prev = new Button();
		next = new Button();
		last = new Button();
		pagLabel = new InlineHTML("Pág " + pagina);
		cantLabel = new InlineHTML("de " + cantPaginas);
		mainPanel.add(first);
		mainPanel.add(prev);
		mainPanel.add(next);
		mainPanel.add(last);
		mainPanel.add(pagLabel);
		mainPanel.add(cantLabel);
		first.addStyleName("btn-first");
		prev.addStyleName("btn-prev");
		next.addStyleName("btn-next");
		last.addStyleName("btn-last");
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
		pagLabel.setText("Pág " + pagina);
	}

	public int getCantPaginas() {
		return cantPaginas;
	}

	public void setCantPaginas(int cantPaginas) {
		this.cantPaginas = cantPaginas;
	}

	public void setCantResultadosVisibles(int cantResultados) {
		this.cantResultadosVisibles = cantResultados;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public void setBeforeClickCommand(Command command) {
		this.beforeClickCommand = command;
	}

	public void addListener() {
		ClickListener listener = new ClickListener() {
			public void onClick(Widget sender) {
				if (sender == next) {
					// TODO: Esto queda comentado por si en algun momento traemos la cant de resultados
					// if(pagina == cantResultados / cantResultadosVisibles + 1){
					// return;
					// }
					setOffset(getOffset() + cantResultadosVisibles + 1);
					setPagina(getPagina() + 1);
				} else if (sender == first) {
					if (getPagina() == 1) {
						return;
					}
					setOffset(0);
					setPagina(1);
				} else if (sender == last) {
					// setOffset(cantResultados / cantResultadosVisibles * cantResultados);
					// setPagina(cantResultados / cantResultadosVisibles + 1);
				} else if (sender == prev) {
					if (getPagina() == 1) {
						return;
					}
					setOffset(getOffset() - cantResultadosVisibles - 1);
					setPagina(getPagina() - 1);
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

}
