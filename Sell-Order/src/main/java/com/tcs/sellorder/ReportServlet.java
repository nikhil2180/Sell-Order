package com.tcs.sellorder;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "report", urlPatterns = { "/report" })
public class ReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReportServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String date = request.getParameter("date");

        response.setContentType("text/xml");
        response.setHeader("Content-Disposition", "attachment; filename=\"report.xml\"");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sell_order", "root", "manager");

            if ("download".equals(action)) 
            {
                generateReport(response, con, date);
            } 
           

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void generateReport(HttpServletResponse response, Connection con, String date) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        PrintWriter out = response.getWriter();

        try {
            ps = con.prepareStatement("select u.username, u.mobile, o.* from users u join orders o on u.id=o.user_id where o.created_at >= ?");
            ps.setTimestamp(1, Timestamp.valueOf(date + " 00:00:00"));
            rs = ps.executeQuery();

            out.println("Username,Mobile,Main Category,Sub Category,Item,Shade,Width (inch),Drop (inch),Width (mm),Drop (mm),Operation,Area,Quantity,Rate,Amount,Tax,Total Amount");

            while (rs.next()) {
                String username = rs.getString("username");
                String mobile = rs.getString("mobile");
                String maincategory = rs.getString("maincategory");
                String subcategory = rs.getString("subcategory");
                String item = rs.getString("item");
                String shade = rs.getString("shade");
                float widthInch = rs.getFloat("width_inch");
                float dropInch = rs.getFloat("drop_inch");
                float widthmm = rs.getFloat("width_mm");
                float dropmm = rs.getFloat("drop_mm");
                String operation = rs.getString("operation");
                int area = rs.getInt("area");
                int quantity = rs.getInt("quantity");
                float rate = rs.getFloat("rate");
                float amount = rs.getFloat("amount");
                float tax = rs.getFloat("tax");
                float totalAmount = rs.getFloat("total_amount");

                out.printf("%s,%s,%s,%s,%s,%s,%.2f,%.2f,%.2f,%.2f,%s,%d,%d,%.2f,%.2f,%.2f,%.2f%n",
                        username, mobile, maincategory, subcategory, item, shade, widthInch, dropInch, widthmm, dropmm, operation, area, quantity, rate, amount, tax, totalAmount);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            if (ps != null) try { ps.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
