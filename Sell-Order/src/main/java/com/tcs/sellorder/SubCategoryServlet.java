package com.tcs.sellorder;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SubCategoryServlet", urlPatterns = { "/subcategory" })
public class SubCategoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mainCategory = request.getParameter("maincategory");

        // Dummy data for sub-categories based on the main category
        String json;
        if ("Window Blind".equals(mainCategory)) {
            json = "[\"Roller Blinds\", \"Roller Blinds Customized\", \"Zebra Blinds\", \"Triple Blinds\", \"Wooden Blinds\", \"Industrial Blinds\",\"Vertical Blinds\",\"Almunium Venetian Blinds\", \"COVID Protection Blinds\" ]";
        } 
        else if ("Motors".equals(mainCategory)) {
            json = "[\"Motor for Blinds\", \"Motor for Curtain Tracks\"]";
        } 
        else if ("Curtain Tracks".equals(mainCategory)) {
            json = "[\"Curtain tracks operated with Motor\", \"Curtain Track Manual\"]";
        } else if ("Wallpaper".equals(mainCategory)) {
            json = "[\"Designer\", \"Customized\"]";
        } else {
            json = "[]";
        }

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
