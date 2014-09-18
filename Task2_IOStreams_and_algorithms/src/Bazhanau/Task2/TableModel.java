package Bazhanau.Task2;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;

public class TableModel extends AbstractTableModel {
    private ArrayList<StudentModel> students = new ArrayList<>();
    private String[] columnIdentifiers;

    private enum Columns {
        SURNAME, GROUP, MATAN, GA, PROGRAMMING, AVERAGE
    }

    public TableModel(String[] columnIdentifiers) {
        this.columnIdentifiers = columnIdentifiers;
    }

    @Override
    public String getColumnName(int column) {
        Object localObject = null;
        if ((column < this.columnIdentifiers.length) && (column >= 0)) {
            localObject = this.columnIdentifiers[column];
        }
        return localObject == null ? super.getColumnName(column) : localObject.toString();
    }

    @Override
    public int getRowCount() {
        return students.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StudentModel student = students.get(rowIndex);
        switch (Columns.values()[columnIndex]) {
            case SURNAME:
                return student.getSurname();
            case GROUP:
                return student.getGroup();
            case MATAN:
                return student.getMarks().get(0);
            case GA:
                return student.getMarks().get(1);
            case PROGRAMMING:
                return student.getMarks().get(2);
            case AVERAGE:
                return student.getAverage();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
        StudentModel student = students.get(rowIndex);
        String val = (String) aValue;
        switch (Columns.values()[columnIndex]) {
            case SURNAME:
                student.setSurname(val);
                break;
            case GROUP:
                student.setGroup(Integer.parseInt(val));
                break;
            case MATAN:
                student.getMarks().set(0, Integer.parseInt(val));
                changeRows();
                break;
            case GA:
                student.getMarks().set(1, Integer.parseInt(val));
                changeRows();
                break;
            case PROGRAMMING:
                student.getMarks().set(2, Integer.parseInt(val));
                changeRows();
                break;
        }
    }

    public void addRow() {
        students.add(new StudentModel());
        Collections.sort(students);
        fireTableRowsInserted(students.size() - 1, students.size() - 1);
    }

    public void deleteRow(int selectedRow) {
        students.remove(selectedRow);
        fireTableRowsDeleted(selectedRow, selectedRow);
    }

    private void changeRows() {
        Collections.sort(students);
        fireTableRowsUpdated(0, students.size() - 1);
    }
}
