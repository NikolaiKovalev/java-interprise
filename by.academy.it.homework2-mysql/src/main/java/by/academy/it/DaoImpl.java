package by.academy.it;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoImpl implements Dao {

    private Connection connection;
    boolean isTestInstance;

    private void connect() throws SQLException {
        if (isTestInstance) {
            this.connection = MySqlDataSource.getTestConnection();
        } else {
            this.connection = MySqlDataSource.getConnection();
        }
    }

    public DaoImpl(boolean isTestInstance) throws SQLException {
        this.isTestInstance = isTestInstance;
        connect();
    }

    public DaoImpl() throws SQLException {
        this.isTestInstance = false;
        connect();
    }

    @Override
    public ReceiversDto getReceiver(int num) throws SQLException {
        if (connection.isClosed()) {
            connect();
        }
        PreparedStatement statement = connection.
                prepareStatement("select * from receivers where num=?");
        statement.setInt(1, num);
        ResultSet resultSet = statement.executeQuery();
        List<ReceiversDto> receiversDtos = new ArrayList<>();
        while (resultSet.next()) {
            ReceiversDto receiver = new ReceiversDto();
            receiver.setNum(resultSet.getInt(1));
            receiver.setName(resultSet.getString(2));
            receiversDtos.add(receiver);
        }
        statement.close();
        return receiversDtos.size() > 0 ? receiversDtos.get(0) : null;
    }


    @Override
    public List<ReceiversDto> getReceivers() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.
                executeQuery("select * from receivers");
        List<ReceiversDto> receiversDtos = new ArrayList<>();
        while (resultSet.next()) {
            ReceiversDto receiver = new ReceiversDto();
            receiver.setNum(resultSet.getInt(1));
            receiver.setName(resultSet.getString(2));
            receiversDtos.add(receiver);
        }
        statement.close();
        return receiversDtos;
    }

    @Override
    public ExpensesDto getExpense(int num) throws SQLException {
        if (connection.isClosed()) {
            connect();
        }
        PreparedStatement statement = connection.
                prepareStatement("select * from expenses where num=?");
        statement.setInt(1, num);
        ResultSet resultSet = statement.executeQuery();
        List<ExpensesDto> expensesDtos = new ArrayList<>();
        while (resultSet.next()) {
            ExpensesDto expenses = new ExpensesDto();
            expenses.setNum(resultSet.getInt(1));
            expenses.setPayday(resultSet.getDate(2));
            expenses.setReceiver(resultSet.getInt(3));
            expenses.setValue(resultSet.getDouble(4));
            expensesDtos.add(expenses);
        }
        statement.close();
        return expensesDtos.size() > 0 ? expensesDtos.get(0) : null;
    }

    @Override
    public List<ExpensesDto> getExpenses() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.
                executeQuery("select * from expenses");
        List<ExpensesDto> expensesDtos = new ArrayList<>();
        while (resultSet.next()) {
            ExpensesDto expenses = new ExpensesDto();
            expenses.setNum(resultSet.getInt(1));
            expenses.setPayday(resultSet.getDate(2));
            expenses.setReceiver(resultSet.getInt(3));
            expenses.setValue(resultSet.getDouble(4));
            expensesDtos.add(expenses);
        }
        statement.close();
        return expensesDtos;
    }

    @Override
    public int addReceiver(ReceiversDto receiver) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into receivers " +
                "values (?,?)");
        preparedStatement.setInt(1, receiver.getNum());
        preparedStatement.setString(2, receiver.getName());

        boolean result = preparedStatement.execute();
        preparedStatement.close();
        if (result) return receiver.getNum();
        else return -1;

    }

    @Override
    public int addExpense(ExpensesDto expense) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into expenses " +
                "values (?,?,?,?)");
        preparedStatement.setInt(1, expense.getNum());
        preparedStatement.setDate(2, (Date) expense.getPayday());
        preparedStatement.setInt(3, expense.getReceiver());
        preparedStatement.setDouble(4, expense.getValue());

        boolean result = preparedStatement.execute();
        preparedStatement.close();
        if (result) return expense.getNum();
        else return -1;
    }
}
