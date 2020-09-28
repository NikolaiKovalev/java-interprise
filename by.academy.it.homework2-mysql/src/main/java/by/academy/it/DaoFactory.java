package by.academy.it;

import java.security.InvalidParameterException;
import java.sql.SQLException;

public class DaoFactory {
    private static DaoImpl dao;


    public static Dao getDao(String database) throws SQLException {
        if ("homework2_mysql".equals(database)) {
            if (dao == null) {
                dao = new DaoImpl();
            }
            return dao;
        } else if ("homework2_mysql_test".equals(database)) {
            if (dao == null) {
                dao = new DaoImpl(true);
            }
            return dao;
        }
        throw new InvalidParameterException("No such database implemented " + database);
    }
}
