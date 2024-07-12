package com.tcs.sellorder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "sellorder", urlPatterns = { "/sellorder" })
public class SellOrder extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SellOrder() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String maincategory = request.getParameter("maincategory");
        String subcategory = request.getParameter("subcategory");
        String item = request.getParameter("items");     // Corrected parameter name here
        String shade = request.getParameter("shade");
        String winStr = request.getParameter("width_inch");
        String dinStr = request.getParameter("drop_inch");
        String wimStr = request.getParameter("width_mm");
        String dimStr = request.getParameter("drop_mm");
        String operation = request.getParameter("operation");
        String areaStr = request.getParameter("area");
        String quantityStr = request.getParameter("quantity");
        String rateStr = request.getParameter("rate");
        String amountStr = request.getParameter("amount");
        String taxStr = request.getParameter("tax");
        String totalAmountStr = request.getParameter("total_amount");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sell_order", "root", "manager");

            PreparedStatement ps = con.prepareStatement("INSERT INTO orders (maincategory, subcategory, item, shade, width_inch, drop_inch, width_mm, drop_mm, operation, area, quantity, rate, amount, tax, total_amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            ps.setString(1, maincategory);
            ps.setString(2, subcategory);
            ps.setString(3, item);
            ps.setString(4, shade);
            ps.setFloat(5, winStr != null && !winStr.isEmpty() ? Float.parseFloat(winStr) : 0);
            ps.setFloat(6, dinStr != null && !dinStr.isEmpty() ? Float.parseFloat(dinStr) : 0);
            ps.setFloat(7, wimStr != null && !wimStr.isEmpty() ? Float.parseFloat(wimStr) : 0);
            ps.setFloat(8, dimStr != null && !dimStr.isEmpty() ? Float.parseFloat(dimStr) : 0);
            ps.setString(9, operation);
            ps.setInt(10, areaStr != null && !areaStr.isEmpty() ? Integer.parseInt(areaStr) : 0);
            ps.setInt(11, quantityStr != null && !quantityStr.isEmpty() ? Integer.parseInt(quantityStr) : 0);
            ps.setFloat(12, rateStr != null && !rateStr.isEmpty() ? Float.parseFloat(rateStr) : 0);
            ps.setFloat(13, amountStr != null && !amountStr.isEmpty() ? Float.parseFloat(amountStr) : 0);
            ps.setFloat(14, taxStr != null && !taxStr.isEmpty() ? Float.parseFloat(taxStr) : 0);
            ps.setFloat(15, totalAmountStr != null && !totalAmountStr.isEmpty() ? Float.parseFloat(totalAmountStr) : 0);

            int result = ps.executeUpdate();

            if (result > 0) {
                response.sendRedirect("dashboard.jsp?success=Order created successfully.");
            } else {
                response.sendRedirect("dashboard.jsp?error=Failed to create order.");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("dashboard.jsp?error=" + e.getMessage());
        }
    }
}
