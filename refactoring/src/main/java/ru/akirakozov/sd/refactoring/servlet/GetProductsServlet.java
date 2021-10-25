package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.DatabaseManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractServlet {

    @Override
    protected void doGetImpl(HttpServletRequest request, HttpServletResponse response, DatabaseManager databaseManager) {

        try {
            ResultSet rs = databaseManager.executeQuery("SELECT * FROM PRODUCT");
            response.getWriter().println("<html><body>");

            while (rs.next()) {
                String  name = rs.getString("name");
                int price  = rs.getInt("price");
                response.getWriter().println(name + "\t" + price + "</br>");
            }

            rs.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
