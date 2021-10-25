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
    @Override
    protected void doGetImpl(HttpServletRequest request, HttpServletResponse response, DatabaseManager databaseManager) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            try {
                ResultSet rs = databaseManager.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with max price: </h1>");

                while (rs.next()) {
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    response.getWriter().println(name + "\t" + price + "</br>");
                }
                response.getWriter().println("</body></html>");

                rs.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("min".equals(command)) {
            try {
                ResultSet rs = databaseManager.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with min price: </h1>");

                while (rs.next()) {
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    response.getWriter().println(name + "\t" + price + "</br>");
                }
                response.getWriter().println("</body></html>");

                rs.close();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("sum".equals(command)) {
            try {
                ResultSet rs = databaseManager.executeQuery("SELECT SUM(price) FROM PRODUCT");
                response.getWriter().println("<html><body>");
                response.getWriter().println("Summary price: ");

                if (rs.next()) {
                    response.getWriter().println(rs.getInt(1));
                }
                response.getWriter().println("</body></html>");

                rs.close();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            try {
                ResultSet rs = databaseManager.executeQuery("SELECT COUNT(*) FROM PRODUCT");
                response.getWriter().println("<html><body>");
                response.getWriter().println("Number of products: ");

                if (rs.next()) {
                    response.getWriter().println(rs.getInt(1));
                }
                response.getWriter().println("</body></html>");

                rs.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
