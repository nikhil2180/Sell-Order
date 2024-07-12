package com.tcs.sellorder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcs.sellorder.SellRecord;

@WebServlet(name = "client", urlPatterns = { "/client" })
public class ClientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ClientServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ClientServlet doGet method started.");

        List<SellRecord> records = new ArrayList<>();

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        String username = (String) session.getAttribute("username");

        System.out.println("Session userId: " + userId);
        System.out.println("Session username: " + username);

        if (userId == null) {
            System.out.println("User is not logged in. Redirecting to login page.");
            response.sendRedirect("login.jsp?error=You need to login first.");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sell_order", "root", "manager");

            System.out.println("Database connection established.");

            PreparedStatement ps = con.prepareStatement("SELECT maincategory, subcategory, item, shade, width_inch, drop_inch, width_mm, drop_mm, operation, area, quantity, rate, amount, tax, total_amount FROM orders");
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SellRecord record = new SellRecord();
                record.setMaincategory(rs.getString("maincategory"));
                record.setSubcategory(rs.getString("subcategory"));
                record.setItem(rs.getString("item"));
                record.setShade(rs.getString("shade"));
                record.setWidthInch(rs.getFloat("width_inch"));
                record.setDropInch(rs.getFloat("drop_inch"));
                record.setWidthMm(rs.getFloat("width_mm"));
                record.setDropMm(rs.getFloat("drop_mm"));
                record.setOperation(rs.getString("operation"));
                record.setArea(rs.getInt("area"));
                record.setQuantity(rs.getInt("quantity"));
                record.setRate(rs.getFloat("rate"));
                record.setAmount(rs.getFloat("amount"));
                record.setTax(rs.getFloat("tax"));
                record.setTotalAmount(rs.getFloat("total_amount"));

                records.add(record);
            }

            // Debug: Print the number of records retrieved
            System.out.println("Number of records retrieved: " + records.size());
            for (SellRecord record : records) {
                System.out.println(record);
            }

            request.setAttribute("sellRecords", records);
            RequestDispatcher dispatcher = request.getRequestDispatcher("client.jsp");
            dispatcher.forward(request, response);

            con.close();
            System.out.println("Database connection closed.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("ClientServlet doGet method ended.");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
