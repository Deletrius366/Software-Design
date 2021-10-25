package ru.akirakozov.sd.refactoring.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseManager {
    public ResultSet executeQuery(String sql) throws SQLException;

    public void executeUpdate(String sql) throws SQLException;
}
