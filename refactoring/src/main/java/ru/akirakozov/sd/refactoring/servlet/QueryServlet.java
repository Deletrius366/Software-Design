package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.DatabaseManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;

/**
 * @author akirakozov
 */
public class QueryServlet extends AbstractServlet {

    private void doQuery(HttpServletResponse response, DatabaseManager databaseManager, String query, String desc, boolean writeItems) {
        try {
            ResultSet rs = databaseManager.executeQuery(query);
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>" + desc + "</h1>");

            if (writeItems) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    response.getWriter().println(name + "\t" + price + "</br>");
                }
            } else {
                if (rs.next()) {
                    response.getWriter().println(rs.getInt(1));
                }
            }
            response.getWriter().println("</body></html>");

            rs.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGetImpl(HttpServletRequest request, HttpServletResponse response, DatabaseManager databaseManager) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            doQuery(response, databaseManager, "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", "Product with max price: ", true);
        } else if ("min".equals(command)) {
            doQuery(response, databaseManager, "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", "Product with min price: ", true);
        } else if ("sum".equals(command)) {
            doQuery(response, databaseManager, "SELECT SUM(price) FROM PRODUCT", "Summary price: ", false);
        } else if ("count".equals(command)) {
            doQuery(response, databaseManager, "SELECT COUNT(*) FROM PRODUCT", "Number of products: ", false);
        } else {
            response.getWriter().println("Unknown command: " + command);
        }
    }
}
