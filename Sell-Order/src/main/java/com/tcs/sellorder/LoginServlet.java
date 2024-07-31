package com.tcs.sellorder;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@WebServlet(name = "login", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final byte[] SECRET_KEY = generateKey();

    private static byte[] generateKey() {
        byte[] keyBytes = new byte[32]; // 256 bits for HS256
        new SecureRandom().nextBytes(keyBytes);     
        return keyBytes;
    }

    public LoginServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Typically, we do not use GET for login, but you can handle GET requests here if necessary
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sell_order", "root", "manager");

            String query = "SELECT id FROM users WHERE username=? AND password=?";
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");

                HttpSession session = request.getSession();
                session.setAttribute("userId", userId);
                session.setAttribute("username", username);

                String token = Jwts.builder()
                        .setSubject(username)
                        .claim("userId", userId)
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
                        .signWith(Keys.hmacShaKeyFor(SECRET_KEY)) // Default algorithm (HS256)
                        .compact();

                System.out.println(userId);
                System.out.println(token);
                session.setAttribute("token", token);

                if ("manager".equals(username) && "Testing@123".equals(password)) 
                {
                    response.sendRedirect("client");
                } 
                else 
                {
                    response.sendRedirect("dashboard.jsp");
                }
                
            } 
            else  
            {
                response.sendRedirect("register.jsp?error=Invalid credentials, please register.");
            }
        } 
        catch (Exception e)
        {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=An error occurred during login. Please try again.");
        } 
        finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
