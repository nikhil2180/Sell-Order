package com.tcs.sellorder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "editorder", urlPatterns = { "/editorder" })
public class EditOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EditOrderServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sell_order", "root", "manager");

            String sql = "SELECT * FROM orders WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            SellRecord record = new SellRecord();
            if (rs.next()) {
                record.setId(rs.getInt("id"));
                record.setOrderid(rs.getInt("orderid"));
                // Set other fields
            }

            request.setAttribute("sellRecord", record);
            RequestDispatcher dispatcher = request.getRequestDispatcher("edit.jsp");
            dispatcher.forward(request, response);

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	int id = Integer.parseInt(request.getParameter("id"));
        String maincategory = request.getParameter("maincategory");
        String subcategory = request.getParameter("subcategory");
        String item = request.getParameter("items");
        String shade = request.getParameter("shade");
        String unit = request.getParameter("unit");
        String operation = request.getParameter("operation");
        int area = Integer.parseInt(request.getParameter("area"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        float rate = Float.parseFloat(request.getParameter("rate"));
        float amount = Float.parseFloat(request.getParameter("amount"));
        float tax = Float.parseFloat(request.getParameter("tax"));
        float totalAmount = Float.parseFloat(request.getParameter("total_amount"));

        float widthInch = 0;
        float dropInch = 0;
        float widthMm = 0;
        float dropMm = 0;

        if ("inch".equals(unit)) {
            widthInch = Float.parseFloat(request.getParameter("width_inch"));
            dropInch = Float.parseFloat(request.getParameter("drop_inch"));
        } else if ("mm".equals(unit)) {
            widthMm = Float.parseFloat(request.getParameter("width_mm"));
            dropMm = Float.parseFloat(request.getParameter("drop_mm"));
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sell_order", "root", "manager");

            String sql = "UPDATE orders SET maincategory=?, subcategory=?, item=?, shade=?, width_inch=?, drop_inch=?, width_mm=?, drop_mm=?, operation=?, area=?, quantity=?, rate=?, amount=?, tax=?, total_amount=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maincategory);
            ps.setString(2, subcategory);
            ps.setString(3, item);
            ps.setString(4, shade);
            ps.setFloat(5, widthInch);
            ps.setFloat(6, dropInch);
            ps.setFloat(7, widthMm);
            ps.setFloat(8, dropMm);
            ps.setString(9, operation);
            ps.setInt(10, area);
            ps.setInt(11, quantity);
            ps.setFloat(12, rate);
            ps.setFloat(13, amount);
            ps.setFloat(14, tax);
            ps.setFloat(15, totalAmount);
            ps.setInt(16, id);

            int updatedRows = ps.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Order updated successfully.");
            } else {
                System.out.println("No order found with the given ID.");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirect to the order list page or any other appropriate page
    }
}