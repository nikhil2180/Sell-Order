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

@WebServlet(name = "preview", urlPatterns = { "/preview" })
public class PreviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public PreviewServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId"); // Retrieve as Integer

        if (userId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is missing in session.");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sell_order", "root", "manager");

            if ("download1".equals(action)) {
                String sql = "SELECT u.username, u.mobile, o.maincategory, o.subcategory, o.item, o.shade, o.width_inch, o.drop_inch, o.width_mm, o.drop_mm, o.operation, o.area, o.quantity, o.rate, o.amount, o.tax, o.total_amount " +
                             "FROM orders o JOIN users u ON o.user_id = u.id WHERE o.user_id = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, userId); // Set userId as an Integer

                ResultSet rs = ps.executeQuery();

                List<SellRecord> records = new ArrayList<>();
                while (rs.next()) {
                    SellRecord record = new SellRecord();
                    record.setUsername(rs.getString("username"));
                    record.setMobile(rs.getString("mobile"));
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

                request.setAttribute("sellRecords", records);
                RequestDispatcher dispatcher = request.getRequestDispatcher("preview.jsp");
                dispatcher.forward(request, response);

                rs.close();
                ps.close();
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
