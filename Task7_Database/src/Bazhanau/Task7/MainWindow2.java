package Bazhanau.Task7;

import Bazhanau.Task2.MainWindow;
import Bazhanau.Task2.Models.StudentModel;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainWindow2 extends MainWindow {
    private Connection connection = new MSSQLManager().getConnection();
    private JMenu menu = new JMenu("База дадзеных");
    private JMenuItem readMenuItem = new JMenuItem("Атрымаць дадзеныя з БД");
    private JMenuItem saveMenuItem = new JMenuItem("Захаваць дадзеныя ў БД");

    public MainWindow2() {
        menuBar.add(menu);
        menu.add(readMenuItem);
        menu.add(saveMenuItem);
        readMenuItem.addActionListener(e -> readData());
        saveMenuItem.addActionListener(e -> saveData());

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

    private void readData() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from StudentsTable;");
            ResultSet res = statement.executeQuery();

            // data of the table
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

            super.setData(data);
        } catch (SQLException e) {
            catcher.catchException(e);
        }
    }

    private void saveData() {
        ArrayList<StudentModel> data = super.getData();
        try {
            for (StudentModel student : data) {
                PreparedStatement statement = connection.prepareStatement("");
                statement.setInt(0, student.getGroupId());
                for (int i = 0; i < student.getMarks().size(); i++) {
                    statement.setInt(i + 1, student.getMarks().get(0));
                }
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
