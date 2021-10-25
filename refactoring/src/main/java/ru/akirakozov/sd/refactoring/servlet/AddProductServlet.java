package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.DatabaseManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author akirakozov
 */
public class AddProductServlet extends AbstractServlet {

    @Override
    protected void doGetImpl(HttpServletRequest request, HttpServletResponse response, DatabaseManager databaseManager) throws IOException, SQLException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        String sql = "INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";

        databaseManager.executeUpdate(sql);

        response.getWriter().println("OK");
    }
}
