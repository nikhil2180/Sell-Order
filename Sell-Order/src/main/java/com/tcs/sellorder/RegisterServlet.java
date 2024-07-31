package com.tcs.sellorder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "register", urlPatterns = { "/register" })
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegisterServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstname = request.getParameter("firstName");
        String lastname = request.getParameter("lastName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String mobile=request.getParameter("mobile");
        

        Connection con = null; 
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        HttpSession session=request.getSession();
        session.setAttribute("username", username);
        session.setAttribute("password", password);
        session.setAttribute("mobile", mobile); 
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sell_order", "root", "manager");

            ps = con.prepareStatement("SELECT * FROM users WHERE username=?");
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) 
            {
            		
                response.sendRedirect("register.jsp?error=User already registered. Please log in.");
            } 
            else 
            {
                ps = con.prepareStatement("INSERT INTO users (first_name, last_name, mobile, username, password) VALUES (?,?,?,?,?)");
                ps.setString(1, firstname);
                ps.setString(2, lastname);
                ps.setString(3, mobile);
                ps.setString(4, username);
                ps.setString(5, password);
                
                ps.executeUpdate();
                response.sendRedirect("login.jsp?success=Registration successful. Please log in.");
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            response.sendRedirect("register.jsp?error=Registration failed. Please try again.");
        } 
        finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
