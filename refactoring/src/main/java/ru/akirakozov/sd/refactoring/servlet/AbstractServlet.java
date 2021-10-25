package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.DatabaseManager;
import ru.akirakozov.sd.refactoring.database.DatabaseManagerImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public abstract class AbstractServlet extends HttpServlet {


    protected abstract void doGetImpl(HttpServletRequest request, HttpServletResponse response, DatabaseManager databaseManager) throws IOException, SQLException;

    private void workWithDatabase (HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        DatabaseManager databaseManager = new DatabaseManagerImpl();
        doGetImpl(request, response, databaseManager);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            workWithDatabase(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
