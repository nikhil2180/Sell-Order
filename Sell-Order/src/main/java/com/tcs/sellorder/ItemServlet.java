package com.tcs.sellorder;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@WebServlet(name = "item", urlPatterns = { "/item" })
public class ItemServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ItemServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subCategory = request.getParameter("subcategory");

        String json = "[]";
        if ("Roller Blinds".equals(subCategory)) {
            json = "[\"Roller Blinds with Standard Mechanism\", \"Roller Blinds with Standard Pelmet\", \"Roller Blinds with Standard Headrail\", \"Roller Blinds Fabric Only\"]";
        }
        else if("Roller Blinds Customized".equals(subCategory))
        {
        	json="[\"Customized Roller Blinds with standard Mechanism\", \"Customized Roller Blinds with standard Pelmet\"]";
        }
        else if("Zebra Blinds".equals(subCategory))
        {
        	json="[\"Zebra Blinds with standard pelmet\", \"Zebra Blind with premium mechanism and standard pelmet\", \"Zebra Blinds Febric Only\"]";
        }
        else if("Triple Blinds".equals(subCategory))
        {
        	json="[\"Triple Blinds with Standard Pelmet\", \"Triple Blinds with standard headrail\", \"Triple Blinds Febric Only\"]";
        }
        else if("Wooden Blinds".equals(subCategory))
        {
        	json ="[\"Wooden Blinds with normal mono mechanism\", \"Wooden Blinds with mono Mechanism\"]";
        }
        else if("Industrial Blinds".equals(subCategory))
        {
        	json="[\"Industrial Blinds\"]";
        }
        else if("Vertical Blinds".equals(subCategory))
        {
        	json="[\"Vertical Blinds\"]";
        }
        else if("Almunium Venetian Blinds".equals(subCategory))
        {
        	json="[\"Almunium Venetian Blinds\"]";
        }
        else if("COVID Protection Blinds".equals(subCategory))
        {
        	json="[\"Covid Protection Roll up Blinds\", \"Covid Protection Verti-strips Blinds\"]";
        }
        else if("Motor for Blinds".equals(subCategory))
        {
        	json="[\"Motor for Roller Blinds - Wintree\", \"Motor for Roller Blinds - Somfy\", \"Motor for Zebra Blind - Wintree\",\"Motor for Zebra Blind - Somfy\", \"Motor for Triple Blinds - Wintree\", \"Motor for Triple Blinds - Somfy\",\"Motor for Wooden Blinds - Wintree\", \"Motor for Wooden Blinds - Somfy\", \"Motor for Aluminium Venetian - Wintree\", \"Motor for Aluminium Venetian - Somfy\"]";        
        }
        else if("Motor for Curtain Tracks".equals(subCategory))
        {
        	json="[\"Motor for Curtain Track - Wintree\", \"Motor for Curtain Track - Somfy\"]";
        }
       
        else if("Curtain tracks operated with Motor".equals(subCategory))
        {
        	json="[\"Curtain track -Wintree\", \"Curtain Track - Somfy\"]";
        }
        else if("Curtain Track Manual".equals(subCategory))
        {
        	json="[\"I track\", \"M track\"]";
        }
        else if("Designer".equals(subCategory))
        {
        	json="[\"Book Name 1\",\"Book Name 2\", \"Book Name 3..... As on\"]";
        }
        else if("Customized".equals(subCategory))
        {
        	json="[\"PVC\", \"Non Woven\", \"3D....Etc\"]";
        }
        else {
            json = "[]";
        }

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle POST requests if needed
    }
}
