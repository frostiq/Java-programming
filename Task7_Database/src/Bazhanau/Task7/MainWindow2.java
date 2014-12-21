package Bazhanau.Task7;

import Bazhanau.Task2.MainWindow;
import Bazhanau.Task2.Models.StudentModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainWindow2 extends MainWindow {
    public static final String INSERT = "INSERT INTO StudentsTable(Surname, [Group], Matan, GA, Programming) VALUES(?,?,?,?,?)";
    public static final String UPDATE = "UPDATE StudentsTable SET [Group] = ?, Matan = ?, GA = ?, Programming = ? WHERE Surname = ?";
    public static final String DELETE = "DELETE FROM StudentsTable WHERE Surname = ?;";
    public static final String SELECT = "SELECT * FROM StudentsTable;";
    private Connection connection = new MSSQLManager().getConnection();
    private JMenu menu = new JMenu("База дадзеных");
    private JMenuItem readMenuItem = new JMenuItem("Атрымаць дадзеныя з БД");
    private JMenuItem saveMenuItem = new JMenuItem("Захаваць дадзеныя ў БД");
    private JTextField affectedRowsField = new JTextField();

    public MainWindow2() {
        menuBar.add(menu);
        menu.add(readMenuItem);
        menu.add(saveMenuItem);
        readMenuItem.addActionListener(e -> super.setData(readData()));
        saveMenuItem.addActionListener(e -> saveData(super.getData()));
        add(affectedRowsField, BorderLayout.SOUTH);
        setVisible(false);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                try {
                    connection.close();
                } catch (SQLException e1) {
                    catcher.catchException(e1);
                }
            }
        });
    }

    public static void main(String[] args) {
        new MainWindow2();
    }

    private ArrayList<StudentModel> readData() {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT);
            ResultSet res = statement.executeQuery();

            ArrayList<StudentModel> data = new ArrayList<>();

            while (res.next()) {
                StudentModel temp = new StudentModel();
                temp.setSurname(res.getString("Surname"));
                temp.setGroup(res.getInt("Group"));
                temp.getMarks().set(0, res.getInt("Matan"));
                temp.getMarks().set(1, res.getInt("GA"));
                temp.getMarks().set(2, res.getInt("Programming"));
                data.add(temp);
            }

            return data;
        } catch (SQLException e) {
            catcher.catchException(e);
        }
        return null;
    }

    private void saveData(ArrayList<StudentModel> clientData) {
        ArrayList<StudentModel> dataFromDB = this.readData();
        int affectedRows = 0;
        try {
            for (StudentModel student : clientData) {
                PreparedStatement statement = dataFromDB.contains(student) ? prepareStudentUpdate(student) : prepareStudentInsert(student);
                affectedRows += statement.executeUpdate();
                dataFromDB.remove(student);
            }

            for (StudentModel student : dataFromDB) {
                affectedRows += prepareStudentDelete(student).executeUpdate();
            }

            updateAffectedRowCount(affectedRows);
        } catch (SQLException e) {
            catcher.catchException(e);
        }
    }

    private PreparedStatement prepareStudentUpdate(StudentModel student) throws SQLException {
        PreparedStatement res = connection.prepareStatement(UPDATE);
        res.setInt(1, student.getGroupId());
        for (int i = 0; i < 3; i++) {
            res.setInt(i + 2, student.getMarks().get(i));
        }
        res.setString(5, student.getSurname());
        return res;
    }

    private PreparedStatement prepareStudentInsert(StudentModel student) throws SQLException {
        PreparedStatement res = connection.prepareStatement(INSERT);
        res.setString(1, student.getSurname());
        res.setInt(2, student.getGroupId());
        for (int i = 0; i < 3; i++) {
            res.setInt(i + 3, student.getMarks().get(i));
        }
        return res;
    }

    private PreparedStatement prepareStudentDelete(StudentModel student) throws SQLException {
        PreparedStatement res = connection.prepareStatement(DELETE);
        res.setString(1, student.getSurname());
        return res;
    }

    private void updateAffectedRowCount(int k) {
        affectedRowsField.setText(k + " row(s) affected");
    }
}
