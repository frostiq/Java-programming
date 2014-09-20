package Bazhanau.Task2.Listeners;

import Bazhanau.FileMainWindow;
import Bazhanau.Task2.Models.GroupsTableModel;
import Bazhanau.Task2.Models.StudentsTableModel;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class StudentsTableModelListener implements TableModelListener {
    private GroupsTableModel groupsTableModel;
    private StudentsTableModel studentsTableModel;
    private FileMainWindow _this;

    public void setGroupsTableModel(GroupsTableModel groupsTableModel) {
        this.groupsTableModel = groupsTableModel;
    }

    public void setFileMainWindow(FileMainWindow fileMainWindow) {
        this._this = fileMainWindow;
    }

    public void setStudentsTableModel(StudentsTableModel studentsTableModel) {
        this.studentsTableModel = studentsTableModel;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        groupsTableModel.update(studentsTableModel.getStudents());
        _this.setIsSaved(false);
    }
}
