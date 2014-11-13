package Bazhanau.Task2;

import Bazhanau.FileWindow.FileMainWindow;
import Bazhanau.Task2.Listeners.*;
import Bazhanau.Task2.Models.GroupsTableModel;
import Bazhanau.Task2.Models.StudentModel;
import Bazhanau.Task2.Models.StudentsTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class MainWindow extends FileMainWindow {
    private JPanel controlPanel = new JPanel();

    private String[] studentColumnNames = new String[]
            {"Прозвішча", "Група", "Мат. аналіз", "Геаметрыя і аглебра", "Праграмаванне", "Сярэдні бал"};

    private String[] groupsColumnNames = new String[]{"Група", "Сярэдні бал"};

    private GroupsTableModel groupsTableModel = new GroupsTableModel(groupsColumnNames);
    private JTable groupsTable = new JTable(groupsTableModel);
    private StudentsTableModelListener studentsTableModelListener = new StudentsTableModelListener();
    private StudentsTableModel studentsTableModel = new StudentsTableModel(studentColumnNames, studentsTableModelListener);
    private JTable studentsTable = new JTable(studentsTableModel);
    private JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

    private JButton addButton = new JButton("Дадаць студэнта");

    private JButton deleteButton = new JButton("Выдаліць абранага студэнта");

    public MainWindow() {
        super();
        getContentPane().setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);
        this.splitPane.add(new JScrollPane(this.studentsTable), JSplitPane.RIGHT);
        this.splitPane.add(new JScrollPane(this.groupsTable), JSplitPane.LEFT);
        this.splitPane.setDividerLocation(1.0);
        add(this.controlPanel, BorderLayout.NORTH);
        this.controlPanel.add(addButton);
        this.controlPanel.add(deleteButton);
        this.controlPanel.setLayout(new FlowLayout());
        this.studentsTableModelListener.setFileMainWindow(this);
        this.studentsTableModelListener.setGroupsTableModel(groupsTableModel);
        this.openMenuItem.addActionListener(new OpenButtonListener(this));
        this.saveMenuItem.addActionListener(new SaveButtonListener(this));
        addWindowListener(new MainWindowAdapter(this));
        this.addButton.addActionListener(new AddButtonListener(this));
        this.deleteButton.addActionListener(new DeleteButtonListener(this));
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                splitPane.setDividerLocation(0.3);
            }
        });
    }

    public static void main(String[] args) {
        new MainWindow();
    }

    @Override
    public boolean hasNoInfoToSave() {
        return studentsTableModel.getRowCount() == 0;
    }

    public int getSelectedRow() {
        return studentsTable.getSelectedRow();
    }

    public void addRow() {
        studentsTableModel.addRow();
    }

    public void deleteRow(int selectedRow) {
        studentsTableModel.deleteRow(selectedRow);
    }

    public ArrayList<StudentModel> getData() {
        return studentsTableModel.getStudents();
    }

    public void setData(ArrayList<StudentModel> data) {
        studentsTableModel.setStudents(data);
    }
}
