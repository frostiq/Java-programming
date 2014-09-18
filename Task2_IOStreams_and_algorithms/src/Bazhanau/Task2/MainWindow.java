package Bazhanau.Task2;

import Bazhanau.FileMainWindow;
import Bazhanau.Task2.Listeners.*;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends FileMainWindow {
    private JPanel controlPanel = new JPanel();
    private String[] columnNames = new String[]
            {"Прозвішча", "Група", "Мат. аналіз", "Геаметрыя і аглебра", "Праграмаванне", "Сярэдні бал"};
    private TableModel tableModel = new TableModel(columnNames);
    private JTable table = new JTable(tableModel);
    private JButton addButton = new JButton("Дадаць студэнта");
    private JButton deleteButton = new JButton("Выдаліць абранага студэнта");

    public MainWindow() {
        getContentPane().setLayout(new BorderLayout());
        add(new JScrollPane(this.table));
        add(this.controlPanel, "North");
        this.controlPanel.add(addButton);
        this.controlPanel.add(deleteButton);
        this.controlPanel.setLayout(new FlowLayout());

        this.openMenuItem.addActionListener(new OpenButtonListener(this));
        this.saveMenuItem.addActionListener(new SaveButtonListener(this));
        addWindowListener(new MainWindowAdapter(this));
        this.addButton.addActionListener(new AddButtonListener(this));
        this.deleteButton.addActionListener(new DeleteButtonListener(this));
    }

    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    @Override
    public boolean hasNoInfoToSave() {
        return tableModel.getRowCount() == 0;
    }

    public static void main(String[] args) {
        new MainWindow();
    }

    public void addRow() {
        tableModel.addRow();
    }

    public void deleteRow(int selectedRow) {
        tableModel.deleteRow(selectedRow);
    }
}
