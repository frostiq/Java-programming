package by.bazhanau.kr2;

import Bazhanau.Logging.ICatcher;
import Bazhanau.Task7.MSSQLManager;
import by.bazhanau.kr2.models.AggregateData;
import by.bazhanau.kr2.models.Deal;
import by.bazhanau.kr2.models.DealType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SqlManager implements AutoCloseable{
    private static final String SELECT_DEALS_BY_TYPE = "SELECT * FROM Deals WHERE DealType = ?";
    private static final String SELECT_DEAL_TYPES = "SELECT * FROM DealTypes";
    private static final String SELECT_DEAL_TYPE = "SELECT * FROM DealTypes WHERE Id = ?";
    private static final String SELECT_AGGREGATE_DATA = "SELECT COUNT(*), SUM(Amount) FROM Deals WHERE DealType = ?";
    private Connection connection;
    private ICatcher catcher;

    /**
     * Creates an instance
     */
    public SqlManager(ICatcher catcher) {
        connection = new MSSQLManager("JavaTask8").getConnection();
        this.catcher = catcher;
    }

    public ArrayList<Deal> getDeals(DealType dealType) {
        ArrayList<Deal> items = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_DEALS_BY_TYPE);
            statement.setString(1, Integer.toString(dealType.getId()));
            ResultSet res = statement.executeQuery();

            while (res.next()){
                Deal temp = new Deal();
                temp.setId(res.getInt("Id"));
                temp.setName(res.getString("Name"));
                temp.setDealType(dealType);
                temp.setDate(res.getDate("Date"));
                temp.setAmount(res.getInt("Amount"));
                items.add(temp);
            }
        } catch (Exception e) {
            catcher.catchException(e);
        }
        return items;
    }

    public AggregateData getAggregateData(DealType dealType) {
        AggregateData data = new AggregateData();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_AGGREGATE_DATA);
            statement.setString(1, Integer.toString(dealType.getId()));
            ResultSet res = statement.executeQuery();

            if (res.next()){
                data.setCount(res.getInt(1));
                data.setSum(res.getInt(2));
            }
        } catch (Exception e) {
            catcher.catchException(e);
        }
        return data;
    }

    public ArrayList<DealType> getDialTypes() {
        ArrayList<DealType> items = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_DEAL_TYPES);
            ResultSet res = statement.executeQuery();

            while (res.next()){
                DealType temp = new DealType();
                temp.setId(res.getInt("Id"));
                temp.setName(res.getString("Name"));
                items.add(temp);
            }
        } catch (Exception e) {
            catcher.catchException(e);
        }
        return items;
    }

    public DealType getDealType(int id){
        DealType dealType = new DealType();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_DEAL_TYPE);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                dealType.setId(res.getInt("Id"));
                dealType.setName(res.getString("Name"));
            }

        } catch (Exception e) {
            catcher.catchException(e);
        }

        return dealType;
    }

    /**
     * closes connection with servers
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        connection.close();
    }
}
