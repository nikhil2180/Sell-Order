<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.tcs.sellorder.SellRecord" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Order Details</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 20px;
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
        box-sizing: border-box;
    }
    h1 {
        text-align: center;
        color: #333;
        margin-bottom: 20px;
    }
    .container {
        background: #fff;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);
        width: 100%;
        max-width: 1200px;
        box-sizing: border-box;
        overflow-x: auto;
    }
    table {
        width: 100%;
        border-collapse: collapse;
        margin: 20px 0;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }
    table, th, td {
        border: 1px solid #ddd;
    }
    th, td {
        padding: 10px;
        text-align: left;
        white-space: nowrap;
    }
    th {
        background-color: #f2f2f2;
    }
    td {
        background-color: #fff;
    }
    tr:nth-child(even) td {
        background-color: #f9f9f9;
    }
    @media (max-width: 768px) {
        .container {
            padding: 10px;
        }
        th, td {
            padding: 8px;
            font-size: 14px;
        }
    }
</style>
</head>
<body>
<div class="container">
    <h1>Sell Order Details</h1>
    <form action="report" method="get">
        <label for="date">Select Date:</label>
        <input type="date" id="date" name="date" required>
        <button type="submit" name="action" value="download">Download Report</button>
    </form>
    <table>
        <tr>
            <th>Username</th>
            <th>Mobile</th>
            <th>Main Category</th>
            <th>Sub Category</th>
            <th>Item</th>
            <th>Shade</th>
            <th>Width (inch)</th>
            <th>Drop (inch)</th>
            <th>Width (mm)</th>
            <th>Drop (mm)</th>
            <th>Operation</th>
            <th>Area</th>
            <th>Quantity</th>
            <th>Rate</th>
            <th>Amount</th>
            <th>Tax</th>
            <th>Total Amount</th>
        </tr>
        <%
            List<SellRecord> records = (List<SellRecord>) request.getAttribute("sellRecords");
            if (records != null) {
                for (SellRecord record : records) {
        %>
        <tr>
            <td><%= record.getUsername() %></td>
            <td><%= record.getMobile() %></td>
            <td><%= record.getMaincategory() %></td>
            <td><%= record.getSubcategory() %></td>
            <td><%= record.getItem() %></td>
            <td><%= record.getShade() %></td>
            <td><%= record.getWidthInch() %></td>
            <td><%= record.getDropInch() %></td>
            <td><%= record.getWidthMm() %></td>
            <td><%= record.getDropMm() %></td>
            <td><%= record.getOperation() %></td>
            <td><%= record.getArea() %></td>
            <td><%= record.getQuantity() %></td>
            <td><%= record.getRate() %></td>
            <td><%= record.getAmount() %></td>
            <td><%= record.getTax() %></td>
            <td><%= record.getTotalAmount() %></td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>
</body>
</html>


