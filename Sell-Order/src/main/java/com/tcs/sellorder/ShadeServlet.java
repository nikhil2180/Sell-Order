package com.tcs.sellorder;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "shade", urlPatterns = { "/shade" })
public class ShadeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subCategory = request.getParameter("item");

        // Dummy data for shades based on the sub-category
        String json;
        if ("Roller Blinds with Standard Mechanism".equals(subCategory)||"Roller Blinds with Standard Pelmet".equals(subCategory)||"Roller Blinds with Standard Headrail".equals(subCategory)||"Roller Blinds Fabric Only".equals(subCategory)) 
        {
            json = "[\"Shade 1\"]";
        } 
        else if ("Customized Roller Blinds with standard Mechanism".equals(subCategory)||"Customized Roller Blinds with standard Pelmet".equals(subCategory)) {
            json = "[\"Shade 2\"]";
        } 
        else if("Zebra Blinds with standard pelmet".equals(subCategory)||"Zebra Blind with premium mechanism and standard pelmet".equals(subCategory)||"Zebra Blinds Febric Only".equals(subCategory)) 
        {
            json = "[\"Shade 3]";
        }
        else if("Triple Blinds with Standard Pelmet".equals(subCategory)||"Triple Blinds with standard headrail".equals(subCategory)||"Triple Blinds Febric Only".equals(subCategory))
        {
        	json="[\"Shade 4]";
        }
        else if("Wooden Blinds with normal mono mechanism".equals(subCategory)||"Wooden Blinds with mono Mechanism".equals(subCategory))
        {
        	json="[\"Shade 5\"]";
        }
        else if("Industrial Blinds".equals(subCategory))
        {
        	json="[\"Shade 6\"]";
        }
        else if("Vertical Blinds".equals(subCategory))
        {
        	json="[\"Shade 7]";
        }
        else if("Almunium Venetian Blinds".equals(subCategory))
        {
        	json="[\"Shade 8\"]";
        }
        else if("Covid Protection Roll up Blinds".equals(subCategory))
        {
        	json="[\"Shade 9\"]";
        }
        else if("Covid Protection Verti-strips Blinds".equals(subCategory))
        {
        	json="[\"Shade 10\"]";
        }
        else if("Motor for Roller Blinds - Wintree".equals(subCategory)||"Motor for Roller Blinds - Somfy".equals(subCategory)||"Motor for Zebra Blind - Wintree".equals(subCategory)||"Motor for Zebra Blind - Somfy".equals(subCategory)||"Motor for Triple Blinds - Wintree".equals(subCategory)||"Motor for Triple Blinds - Somfy".equals(subCategory)||"Motor for Wooden Blinds - Wintree".equals(subCategory)||"Motor for Wooden Blinds - Somfy".equals(subCategory)||"Motor for Aluminium Venetian - Wintree".equals(subCategory)||"Motor for Aluminium Venetian - Somfy".equals(subCategory))
        {
        	json="[\"Shade 11\"]";
        }
        else if("Motor for Curtain Track - Wintree".equals(subCategory)||"Motor for Curtain Track - Somfy".equals(subCategory))
        {
        	json="[\"Shade 12\"]";
        }
        else if("Curtain track -Wintree".equals(subCategory)||"Curtain Track - Somfy".equals(subCategory))
        {
        	json="[\"Shade 13\"]";
        }
        else if("I track".equals(subCategory)||"M track".equals(subCategory))
        {
        	json="[\"Shade 14\"]";
        }
        else if("Book Name 1".equals(subCategory)||"Book Name 2".equals(subCategory)||"Book Name 3..... As on".equals(subCategory))
        {
        	json="[\"Shade 15\"]";
        }
        else if("PVC".equals(subCategory)||"Non Woven".equals(subCategory)||"3D....Etc".equals(subCategory))
        {
        	json="[\"Shade 16\"]";
        }
        else 
        {
        	json="[]";
        }
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
