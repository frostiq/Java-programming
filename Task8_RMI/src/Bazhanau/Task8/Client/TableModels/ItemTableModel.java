package Bazhanau.Task8.Client.TableModels;

import Bazhanau.Logging.ICatcher;
import Bazhanau.Task8.IRmiServer;
import Bazhanau.Task8.Models.Item;
import Bazhanau.Task8.Models.Storage;

import javax.swing.table.AbstractTableModel;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ItemTableModel extends AbstractTableModel {
    ArrayList<Integer> ids;
    IRmiServer server;
    private String[] columnIdentifiers;
    private ICatcher catcher;

    public ItemTableModel(String[] columnIdentifiers, IRmiServer server, ICatcher catcher) {
        this.columnIdentifiers = columnIdentifiers;
        this.server = server;
        this.catcher = catcher;
    }

    @Override
    public int getRowCount() {

        return getIds().size();
    }

    @Override
    public int getColumnCount() {
        return Columns.values().length;
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
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != Columns.STORAGE_NAME.ordinal() && columnIndex != Columns.ID.ordinal();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Item item = server.getItem(getIds().get(rowIndex));
            switch (Columns.values()[columnIndex]) {
                case ID:
                    return item.getId();
                case NAME:
                    return item.getName();
                case PRICE:
                    return item.getPrice();
                case QUANTITY:
                    return item.getQuantity();
                case STORAGE_ID:
                    return item.getStorage().getId();
                case STORAGE_NAME:
                    return item.getStorage().getName();
            }
        } catch (Exception e) {
            catcher.catchException(e);
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        try {
            String val = (String) aValue;
            Item item = server.getItem(getIds().get(rowIndex));
            switch (Columns.values()[columnIndex]) {
                case NAME:
                    item.setName(val);
                    break;
                case PRICE:
                    item.setPrice(Integer.parseInt(val));
                    break;
                case QUANTITY:
                    item.setQuantity(Integer.parseInt(val));
                    break;
                case STORAGE_ID:
                    item.setStorage(new Storage(Integer.parseInt(val)));
                    fireTableCellUpdated(rowIndex, Columns.STORAGE_NAME.ordinal());
                    break;

            }

            server.updateItem(item);
            super.setValueAt(aValue, rowIndex, columnIndex);
        } catch (Exception e) {
            catcher.catchException(e);
        }
    }

    @Override
    public void fireTableDataChanged() {
        ids = null;
        super.fireTableDataChanged();
    }

    public void addItem() {
        try {
            server.createNewItem();
            fireTableDataChanged();
        } catch (RemoteException e) {
            catcher.catchException(e);
        }
    }

    public void deleteItem(int row) {
        try {
            server.removeItem(row);
            fireTableDataChanged();
        } catch (RemoteException e) {
            catcher.catchException(e);
        }
    }

    private ArrayList<Integer> getIds(){
        try {
            if (ids == null) {
                ids = server.getItemIds();
            }
        } catch (RemoteException e) {
            catcher.catchException(e);
        }
        return ids;
    }

    private enum Columns {
        ID, NAME, PRICE, QUANTITY, STORAGE_ID, STORAGE_NAME
    }
}
