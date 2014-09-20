package Bazhanau.Task2.Models;

import Bazhanau.Task2.Listeners.StudentsTableModelListener;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StudentsTableModel extends AbstractTableModel {
    private ArrayList<StudentModel> students = new ArrayList<>();
    private String[] columnIdentifiers;
    private StudentsComparator comparator = new StudentsComparator();

    private enum Columns {
        SURNAME, GROUP, MATAN, GA, PROGRAMMING, AVERAGE
    }

    public StudentsTableModel(String[] columnIdentifiers, StudentsTableModelListener listener) {
        this.columnIdentifiers = columnIdentifiers;
        listener.setStudentsTableModel(this);
        this.addTableModelListener(listener);
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
        this.insertRows();
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
                val = student.getGroupId();
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
                    break;
                case GA:
                    student.getMarks().set(1, Integer.parseInt(val));
                    break;
                case PROGRAMMING:
                    student.getMarks().set(2, Integer.parseInt(val));
                    break;
            }
            changeRows();
        } catch (NumberFormatException e) {
        }
    }

    public void addRow() {
        students.add(new StudentModel());
        Collections.sort(students, comparator);
        fireTableRowsInserted(students.size() - 1, students.size() - 1);
    }

    public void deleteRow(int selectedRow) {
        if (selectedRow >= 0) {
            students.remove(selectedRow);
            Collections.sort(students, comparator);
            fireTableRowsDeleted(selectedRow, selectedRow);
        }
    }

    private void changeRows() {
        Collections.sort(students, comparator);
        fireTableRowsUpdated(0, students.size() - 1);
    }

    private void insertRows() {
        Collections.sort(students, comparator);
        fireTableRowsInserted(0, students.size() - 1);
    }

    private class StudentsComparator implements Comparator<StudentModel> {
        @Override
        public int compare(StudentModel o1, StudentModel o2) {
            if (o1.getGroupId() == o2.getGroupId()) {
                return o1.compareTo(o2);
            } else {
                return Integer.compare(o1.getGroupId(), o2.getGroupId());
            }
        }
    }
}
