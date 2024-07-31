// ClientServlet.java
package com.tcs.sellorder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "client", urlPatterns = { "/client", "/client/api" })
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
        String token = (String) session.getAttribute("token");

        System.out.println("Session userId: " + userId);
        System.out.println("Session token: " + token);

        if (userId == null || token == null) {
            System.out.println("User is not logged in. Redirecting to login page.");
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=You need to login first.");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sell_order", "root", "manager");

            System.out.println("Database connection established.");
            
            // SQL query to join users and orders tables and fetch the required data
            String sql = "SELECT u.username, u.mobile, o.* " +
                         "FROM users u " +
                         "JOIN orders o ON u.id = o.user_id";

            PreparedStatement ps = con.prepareStatement(sql);
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
                // Set additional user details
                record.setUsername(rs.getString("username"));
                record.setMobile(rs.getString("mobile"));

                records.add(record);
            }

            // Check if the request is for the API endpoint
            String requestUri = request.getRequestURI();
            if (requestUri.endsWith("/client/api")) {
                response.setContentType("application/xml");
                response.setCharacterEncoding("UTF-8");

                PrintWriter out = response.getWriter();
                out.println("<SellRecords>");

                for (SellRecord record : records) {
                    out.println("<SellRecord>");
                    out.println("<Maincategory>" + record.getMaincategory() + "</Maincategory>");
                    out.println("<Subcategory>" + record.getSubcategory() + "</Subcategory>");
                    out.println("<Item>" + record.getItem() + "</Item>");
                    out.println("<Shade>" + record.getShade() + "</Shade>");
                    out.println("<WidthInch>" + record.getWidthInch() + "</WidthInch>");
                    out.println("<DropInch>" + record.getDropInch() + "</DropInch>");
                    out.println("<WidthMm>" + record.getWidthMm() + "</WidthMm>");
                    out.println("<DropMm>" + record.getDropMm() + "</DropMm>");
                    out.println("<Operation>" + record.getOperation() + "</Operation>");
                    out.println("<Area>" + record.getArea() + "</Area>");
                    out.println("<Quantity>" + record.getQuantity() + "</Quantity>");
                    out.println("<Rate>" + record.getRate() + "</Rate>");
                    out.println("<Amount>" + record.getAmount() + "</Amount>");
                    out.println("<Tax>" + record.getTax() + "</Tax>");
                    out.println("<TotalAmount>" + record.getTotalAmount() + "</TotalAmount>");
                    out.println("<Username>" + record.getUsername() + "</Username>");
                    out.println("<Mobile>" + record.getMobile() + "</Mobile>");
                    out.println("</SellRecord>");
                }

                out.println("</SellRecords>");
                out.flush();
                out.close();
            } 
            else {
                request.setAttribute("sellRecords", records);
                RequestDispatcher dispatcher = request.getRequestDispatcher("client.jsp");
                dispatcher.forward(request, response);
            }

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
