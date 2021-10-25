package ru.akirakozov.sd.refactoring.html;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HtmlResponseBuilder {
    public void writeHtmlResponse(PrintWriter writer, ResultSet rs, String desc, boolean writeItems) throws SQLException {
        writer.println("<html><body>");
        if (!desc.isEmpty()) {
           writer.println("<h1>" + desc + "</h1>");
        }

        if (writeItems) {
            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                writer.println(name + "\t" + price + "</br>");
            }
        } else {
            if (rs.next()) {
                writer.println(rs.getInt(1));
            }
        }
    }
}
