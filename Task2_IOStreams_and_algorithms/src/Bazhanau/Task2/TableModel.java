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

    public ArrayList<StudentModel> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<StudentModel> students) {
        this.students = students;
        Collections.sort(students);
        fireTableRowsInserted(0, students.size());
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
        int val;
        switch (Columns.values()[columnIndex]) {
            case SURNAME:
                return student.getSurname();
            case GROUP:
                val = student.getGroup();
                return val >= 0 ? val : "";
            case MATAN:
                val = student.getMarks().get(0);
                return val >= 0 ? val : "";
            case GA:
                val = student.getMarks().get(1);
                return val >= 0 ? val : "";
            case PROGRAMMING:
                val = student.getMarks().get(2);
                return val >= 0 ? val : "";
            case AVERAGE:
                double average = student.getAverage();
                return average >= 0 ? average : "";
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
        StudentModel student = students.get(rowIndex);
        String val = (String) aValue;
        try {
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
        } catch (NumberFormatException e) {
        }
    }

    public void addRow() {
        students.add(new StudentModel());
        Collections.sort(students);
        fireTableRowsInserted(students.size() - 1, students.size() - 1);
    }

    public void deleteRow(int selectedRow) {
        if (selectedRow >= 0) {
            students.remove(selectedRow);
            fireTableRowsDeleted(selectedRow, selectedRow);
        }
    }

    private void changeRows() {
        Collections.sort(students);
        fireTableRowsUpdated(0, students.size() - 1);
    }
}
