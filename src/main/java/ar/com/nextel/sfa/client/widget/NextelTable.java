package ar.com.nextel.sfa.client.widget;

import ar.com.snoop.gwt.commons.client.widget.table.RowFlexTable;
import ar.com.snoop.gwt.commons.client.widget.table.RowListener;

import com.google.gwt.user.client.ui.Widget;

public class NextelTable extends RowFlexTable implements RowListener {
	private int dataStarRow;
	private String[] rowStyles = { "TableRow1", "TableRow2" };
	private String headerStyle = "TableHeader";
	private String selectedStyle;
	private int rowSelected = 0;


	public NextelTable(boolean showPointer) {
		this(1);
		selectedStyle = showPointer ? "TableRowSelected": "NoPointerTableRowSelected";
	}
	
	public NextelTable() {
		this(1);
		selectedStyle = "TableRowSelected"; 
	}

	public NextelTable(int dataStarRow) {
		super();
		addStyleName("gwt-NextelTable");
		this.dataStarRow = dataStarRow;
		addRowListener(this);
		getRowFormatter().addStyleName(0, headerStyle);
		//addRowClickListener();
	}

	public void setHTML(int row, int column, String html) {
		int rows = getRowCount();
		super.setHTML(row, column, html);
		if (row >= rows) {
			for (int i = rows; i < (row + 1); i++) {
				getRowFormatter().addStyleName(i, rowStyles[i % rowStyles.length]);
			}
		}
	}

	public void setWidget(int row, int column, Widget widget) {
		int rows = getRowCount();
		super.setWidget(row, column, widget);
		if (row >= rows) {
			for (int i = rows; i < (row + 1); i++) {
				getRowFormatter().addStyleName(i, rowStyles[i % rowStyles.length]);
			}
		}
	}

	public void onRowEnter(Widget sender, int row) {
		if (row >= dataStarRow) {
			getRowFormatter().addStyleName(row, selectedStyle);
		}
	}

	public void onRowLeave(Widget sender, int row) {
		if (row >= dataStarRow) {
			getRowFormatter().removeStyleName(row, selectedStyle);
		}
	}

	public void onRowClick(Widget sender, int row) {
		if (row >= dataStarRow) {
			getRowFormatter().addStyleName(row, "selectedRow");
			if (rowSelected != 0) {
				getRowFormatter().removeStyleName(rowSelected,"selectedRow");
			}
			rowSelected = row;
		}
	}
	
	public int insertRow(int beforeRow) {
		int index = super.insertRow(beforeRow);
		// Le resto 1 porque ya inserte al menos una
		if (beforeRow != getRowCount() - 1) {
			refreshRowStyles(index);
		}
		return index;
	}

	public void removeRow(int row) {
		super.removeRow(row);
		if (row < getRowCount()) {
			refreshRowStyles(row);
		}
	}
	
	/** Recarga los estilos de las filas para mantener la coherencia al insertar o borrar filas del medio */
	public void refreshRowStyles(int from) {
		for (int i = from; i < getRowCount(); i++) {
			RowFormatter formatter = getRowFormatter();
			for (int j = 0; j < rowStyles.length; j++) {
				formatter.removeStyleName(i, rowStyles[j]);
			}
			formatter.addStyleName(i, rowStyles[i % rowStyles.length]);
		}
	}
	public int getRowSelected() {
		return rowSelected;
	}

	public void setRowSelected(int rowSelected) {
		this.rowSelected = rowSelected;
	}
}