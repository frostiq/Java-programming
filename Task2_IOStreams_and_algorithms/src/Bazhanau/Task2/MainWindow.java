package Bazhanau.Task2;

import Bazhanau.FileService.FileService;
import Bazhanau.FileWindow.FileMainWindow;
import Bazhanau.FileWindow.IFileHandler;
import Bazhanau.Task2.Listeners.AddButtonListener;
import Bazhanau.Task2.Listeners.DeleteButtonListener;
import Bazhanau.Task2.Listeners.StudentsTableModelListener;
import Bazhanau.Task2.Models.GroupsTableModel;
import Bazhanau.Task2.Models.StudentModel;
import Bazhanau.Task2.Models.StudentsTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainWindow extends FileMainWindow {
    protected final String[] studentColumnNames = new String[]
            {"Прозвішча", "Група", "Мат. аналіз", "Геаметрыя і аглебра", "Праграмаванне", "Сярэдні бал"};
    private final StudentsTableModelListener studentsTableModelListener = new StudentsTableModelListener();
    protected StudentsTableModel studentsTableModel = new StudentsTableModel(studentColumnNames, studentsTableModelListener);
    private JTable studentsTable = new JTable(studentsTableModel);
    private JPanel controlPanel = new JPanel();
    private String[] groupsColumnNames = new String[]{"Група", "Сярэдні бал"};
    private GroupsTableModel groupsTableModel = new GroupsTableModel(groupsColumnNames);
    private JTable groupsTable = new JTable(groupsTableModel);
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
    public boolean hasInfoToSave() {
        return studentsTableModel.getRowCount() != 0;
    }

    @Override
    public IFileHandler getFileHandler() {
        return new FileHandler();
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

    protected ArrayList<StudentModel> getData() {
        return studentsTableModel.getStudents();
    }

    protected void setData(ArrayList<StudentModel> data) {
        studentsTableModel.setStudents(data);
    }

    private class FileHandler implements IFileHandler {
        private FileService fileService = new FileService();

        @Override
        public void open(File file) {
            try {
                setData((ArrayList<StudentModel>) fileService.readObject(file));
            } catch (IOException | ClassCastException | ClassNotFoundException e) {
                catchException(e);
            }
        }

        @Override
        public void save(File file) {
            try {
                fileService.writeObject(file, getData());
            } catch (IOException e) {
                catchException(e);
            }
        }
    }
}
