package ru.akirakozov.sd.refactoring.servlet;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.akirakozov.sd.refactoring.database.DatabaseManager;
import ru.akirakozov.sd.refactoring.database.DatabaseManagerImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServletTest {

    private final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);

    private static class Item {
        String name;
        int price;

        public Item(String name, int price) {
            this.name = name;
            this.price = price;
        }
    }

    @BeforeEach
    private void setup() throws SQLException, IOException {
        DatabaseManager databaseManager = new DatabaseManagerImpl();
        String sql1 = "DROP TABLE IF EXISTS PRODUCT";
        String sql2 = "CREATE TABLE PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)";

        databaseManager.executeUpdate(sql1);
        databaseManager.executeUpdate(sql2);
        Mockito.when(response.getWriter()).thenReturn(writer);
    }

    private void addTwoItems() throws IOException, ServletException {
        AddProductServlet addProductServlet = new AddProductServlet();

        Mockito.when(request.getParameter("name")).thenReturn("iphone6");
        Mockito.when(request.getParameter("price")).thenReturn("300");
        addProductServlet.doGet(request, response);

        Mockito.when(request.getParameter("name")).thenReturn("iphone7");
        Mockito.when(request.getParameter("price")).thenReturn("400");
        addProductServlet.doGet(request, response);
    }

    private int parseQueryResponse(StringWriter stringWriter) {
        String parsed = stringWriter.toString().split("<html><body>")[1].split(System.lineSeparator())[2];
        int retVal = 0;
        try {
            retVal = Integer.parseInt(parsed);
        } catch (NumberFormatException e) {
            retVal = Integer.parseInt(parsed.split("\t")[1].split("</br>")[0]);
        }
        return retVal;
    }

    private List<Item> parseGetProducts(StringWriter stringWriter) {
        List<Item> items = new ArrayList<>();
        String[] parsed = stringWriter.toString().split("<html><body>")[1].split(System.lineSeparator());
        for (String s : parsed) {
            if (s.isEmpty() || !Character.isLetter(s.charAt(0)))
                continue;
            String[] rawItem = s.split("</br>")[0].split("\t");
            items.add(new Item(rawItem[0], Integer.parseInt(rawItem[1])));
        }
        return items;
    }

    @Test
    public void QuerySumServletTest() throws IOException, ServletException {
        QueryServlet queryServlet = new QueryServlet();

        addTwoItems();

        Mockito.when(request.getParameter("command")).thenReturn("sum");
        queryServlet.doGet(request, response);
        Assertions.assertEquals(parseQueryResponse(stringWriter), 700);
    }

    @Test
    public void QueryMinServletTest() throws IOException, ServletException {
        QueryServlet queryServlet = new QueryServlet();

        addTwoItems();

        Mockito.when(request.getParameter("command")).thenReturn("min");
        queryServlet.doGet(request, response);
        Assertions.assertEquals(parseQueryResponse(stringWriter), 300);
    }

    @Test
    public void QueryMaxServletTest() throws IOException, ServletException {
        QueryServlet queryServlet = new QueryServlet();

        addTwoItems();

        Mockito.when(request.getParameter("command")).thenReturn("max");
        queryServlet.doGet(request, response);
        Assertions.assertEquals(parseQueryResponse(stringWriter), 400);
    }

    @Test
    public void QueryCountServletTest() throws IOException, ServletException {
        QueryServlet queryServlet = new QueryServlet();

        addTwoItems();

        Mockito.when(request.getParameter("command")).thenReturn("count");
        queryServlet.doGet(request, response);
        Assertions.assertEquals(parseQueryResponse(stringWriter), 2);
    }

    @Test
    public void GetProductServletTest() throws IOException, ServletException {
        GetProductsServlet servlet = new GetProductsServlet();

        addTwoItems();

        servlet.doGet(request, response);

        List<Item> items = parseGetProducts(stringWriter);

        Assertions.assertEquals(items.get(0).name, "iphone6");
        Assertions.assertEquals(items.get(0).price, 300);
        Assertions.assertEquals(items.get(1).name, "iphone7");
        Assertions.assertEquals(items.get(1).price, 400);
    }
}
