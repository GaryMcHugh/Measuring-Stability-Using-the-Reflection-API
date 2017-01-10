package ie.gmit.sw;

import javax.swing.table.*;
public class TypeSummaryTableModel extends AbstractTableModel{
	private static final long serialVersionUID = 777L;
	
	private String[] cols = {"Class", "InDegree", "OutDegree", "Stability"};
	private Object[][] data = {
		{"Class 1", "InDegree 1", "OutDegree 1","Stability 1"},
		{"Class 2", "InDegree 2", "OutDegree 2","Stability 2"},
		{"Class 3", "InDegree 3", "OutDegree 3","Stability 3"},
		{"Class 4", "InDegree 4", "OutDegree 4","Stability 4"}
	};
	
	//need this to set table data
    public void setTableData(Object[][] data){
	    this.data = data;
    }
	
	public int getColumnCount() {
        return cols.length;
    }
	
    public int getRowCount() {
        return data.length;
	}

    public String getColumnName(int col) {
    	return cols[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
	}
   
    public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
	}
}