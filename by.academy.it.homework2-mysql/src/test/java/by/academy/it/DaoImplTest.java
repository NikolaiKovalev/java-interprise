package by.academy.it;

import org.dbunit.DatabaseUnitException;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;

public class DaoImplTest {

    DaoImpl dao;
    private IDatabaseConnection connection;
    Connection connect;

    @Before
    public void setUp() throws Exception {
        try {
            dao = (DaoImpl) DaoFactory.getDao("homework2_mysql_test");
            connection = new MySqlConnection(MySqlDataSource.getTestConnection(), "homework2_test");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
        dao = null;
    }

    @Test
    public void getReceiver() throws DatabaseUnitException, SQLException, MalformedURLException {
        //Given
        IDataSet dataSet = new FlatXmlDataSetBuilder()
                .build(new File("src/test/resources/by/academy/it/homework2-mysql/DaoImplTestDatabase.xml"));
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        //When
        ReceiversDto receiver = dao.getReceiver(5);
        //Then
        assertNotNull(receiver);
        assertEquals("evroopt", receiver.getName());
        DatabaseOperation.DELETE.execute(connection, dataSet);
    }

    @Test
    public void getReceivers() throws MalformedURLException, DatabaseUnitException, SQLException {
        //Given
        IDataSet dataSet = new FlatXmlDataSetBuilder()
                .build(new File("src/test/resources/by/academy/it/homework2-mysql/DaoImplTestDatabase.xml"));
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        //When
        List<ReceiversDto> receivers = dao.getReceivers();
        //Then
        assertNotNull(receivers);
        assertEquals(receivers.size(), 3);
        DatabaseOperation.DELETE.execute(connection, dataSet);
    }

    @Test
    public void getExpense() throws MalformedURLException, DatabaseUnitException, SQLException {
        //Given
        IDataSet dataSet = new FlatXmlDataSetBuilder()
                .build(new File("src/test/resources/by/academy/it/homework2-mysql/DaoImplTestDatabase.xml"));
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        //When
        ExpensesDto expense = dao.getExpense(2);
        //Then
        assertNotNull(expense);
        assertEquals(117.5, expense.getValue(), 0.5);
        assertEquals(2, expense.getReceiver());
        DatabaseOperation.DELETE.execute(connection, dataSet);
    }

    @Test
    public void getExpenses() throws MalformedURLException, DatabaseUnitException, SQLException {
        //Given
        IDataSet dataSet = new FlatXmlDataSetBuilder()
                .build(new File("src/test/resources/by/academy/it/homework2-mysql/DaoImplTestDatabase.xml"));
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        //When
        List<ExpensesDto> expenses = dao.getExpenses();
        //Then
        assertNotNull(expenses);
        assertEquals(expenses.size(), 3);
        DatabaseOperation.DELETE.execute(connection, dataSet);
    }

    @Test
    public void addReceiver() throws SQLException {
        //Given
        ReceiversDto receiver = new ReceiversDto();
        //When
        receiver.setNum(7);
        receiver.setName("bigz");
        dao.addReceiver(receiver);
        //Then
        assertEquals(receiver.getName(), "bigz");
        assertEquals(receiver.getNum(), 7);

        PreparedStatement preparedStatement = connection
                .getConnection()
                .prepareStatement(
                        "delete from homework2_test.receivers where num=7");
        preparedStatement.execute();
        preparedStatement.close();

    }

    @Test
    public void addExpense() throws SQLException {
        //Given
        ExpensesDto expense = new ExpensesDto();
        //When
        expense.setNum(7);
        expense.setReceiver(2);
        expense.setValue(118);
        expense.setPayday(new Date(2020 - 02 - 02));
        dao.addExpense(expense);
        //Then
        assertEquals(2, expense.getReceiver());
        assertEquals(7, expense.getNum());
        assertEquals(118, expense.getValue(), 0);

        PreparedStatement preparedStatement = connection
                .getConnection()
                .prepareStatement(
                        "delete from homework2_test.expenses where num=7");
        preparedStatement.execute();
        preparedStatement.close();
    }
}