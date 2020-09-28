package by.academy.it;

import java.sql.SQLException;
import java.util.List;

public interface Dao {
    ReceiversDto getReceiver(int num) throws SQLException;

    List<ReceiversDto> getReceivers() throws SQLException;

    ExpensesDto getExpense(int num) throws SQLException;

    List<ExpensesDto> getExpenses() throws SQLException;

    int addReceiver(ReceiversDto receiver) throws SQLException;

    int addExpense(ExpensesDto expense) throws SQLException;
}
