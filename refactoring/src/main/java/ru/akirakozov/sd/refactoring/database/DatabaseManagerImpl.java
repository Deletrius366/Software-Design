package ru.akirakozov.sd.refactoring.database;

import java.sql.*;

public class DatabaseManagerImpl implements DatabaseManager {

    Connection c;
    Statement stmt;

    public DatabaseManagerImpl() throws SQLException {
        c = DriverManager.getConnection("jdbc:sqlite:test.db");
        stmt = c.createStatement();
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException throwables) {
            c.close();
            throw new RuntimeException(throwables);
        }
    }

    public void executeUpdate(String sql) throws SQLException {
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException throwables) {
            c.close();
            throw new RuntimeException(throwables);
        }
    }
}
