package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.DatabaseManager;
import ru.akirakozov.sd.refactoring.html.HtmlResponseBuilder;

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
            HtmlResponseBuilder htmlResponseBuilder = new HtmlResponseBuilder();

            htmlResponseBuilder.writeHtmlResponse(response.getWriter(), rs, "", true);

            rs.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
