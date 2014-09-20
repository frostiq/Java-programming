package Bazhanau.Task2.Models;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;

public class GroupsTableModel extends AbstractTableModel {
    private ArrayList<GroupModel> groups = new ArrayList<>();

    private String[] columnIdentifiers;

    public GroupsTableModel(String[] columnIdentifiers) {
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
        return groups.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        GroupModel groupModel = groups.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return groupModel.getId();
            case 1:
                double val = groupModel.getGroupAverage();
                return val >= 0 ? val : "";
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void update(ArrayList<StudentModel> students) {
        groups.clear();
        for (StudentModel student : students) {
            if (student.getGroupId() != StudentModel.DEFAULT_GROUP) {
                boolean contains = false;
                GroupModel group = null;
                for (int i = 0; i < groups.size(); i++) {
                    if (groups.get(i).getId() == student.getGroupId()) {
                        contains = true;
                        group = groups.get(i);
                        break;
                    }
                }

                if (!contains) {
                    groups.add(group = new GroupModel(student.getGroupId()));
                }

                group.addStudent(student);
                Collections.sort(groups);
            }
        }
        this.fireTableDataChanged();
    }
}
