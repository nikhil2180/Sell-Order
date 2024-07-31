<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tcs.sellorder.SellRecord" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Order</title>
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
        .container {
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 800px;
            box-sizing: border-box;
        }
        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }
        form {
            display: flex;
            flex-direction: column;
        }
        label {
            margin: 10px 0 5px;
        }
        input, select {
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .btn {
            padding: 10px 20px;
            font-size: 16px;
            color: #fff;
            background-color: #007bff;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            cursor: pointer;
            margin-top: 10px;
        }
        .btn:hover {
            background-color: #0056b3;
        }
        .unit-fields {
            display: none;
        }
    </style>
    <script>
        function toggleFields(unit) {
            if (unit === 'inch') {
                document.getElementById('inch-fields').style.display = 'block';
                document.getElementById('mm-fields').style.display = 'none';
            } else if (unit === 'mm') {
                document.getElementById('inch-fields').style.display = 'none';
                document.getElementById('mm-fields').style.display = 'block';
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>Edit Order Details</h1>
        <%
            SellRecord record = (SellRecord) request.getAttribute("sellRecord");
        %>
        <form action="editorder" method="post">
        
             <input type="hidden" name="id" value="<%= record.getId() %>">
    		<input type="hidden" name="orderid" value="<%= record.getOrderid() %>">

            <label for="maincategory">Main Category:</label>
            <input type="text" id="maincategory" name="maincategory" value="<%= record.getMaincategory() %>">

            <label for="subcategory">Sub Category:</label>
            <input type="text" id="subcategory" name="subcategory" value="<%= record.getSubcategory() %>">

            <label for="items">Name of Item:</label>
            <input type="text" id="items" name="items" value="<%= record.getItem() %>">

            <label for="shade">Shade:</label>
            <input type="text" id="shade" name="shade" value="<%= record.getShade() %>">

            <label for="unit">Select Unit:</label>
            <select id="unit" name="unit" onchange="toggleFields(this.value)">
                <option value="">Select unit</option>
                <option value="inch" <%= "inch".equals(record.getUnit()) ? "selected" : "" %>>Inches</option>
                <option value="mm" <%= "mm".equals(record.getUnit()) ? "selected" : "" %>>Millimeters</option>
            </select>

            <div id="inch-fields" class="unit-fields" style="display: <%= "inch".equals(record.getUnit()) ? "block" : "none" %>;">
                <label for="width_inch">Width (inch):</label>
                <input type="text" id="width_inch" name="width_inch" value="<%= record.getWidthInch() %>">

                <label for="drop_inch">Drop (inch):</label>
                <input type="text" id="drop_inch" name="drop_inch" value="<%= record.getDropInch() %>">
            </div>
            <div id="mm-fields" class="unit-fields" style="display: <%= "mm".equals(record.getUnit()) ? "block" : "none" %>;">
                <label for="width_mm">Width (mm):</label>
                <input type="text" id="width_mm" name="width_mm" value="<%= record.getWidthMm() %>">

                <label for="drop_mm">Drop (mm):</label>
                <input type="text" id="drop_mm" name="drop_mm" value="<%= record.getDropMm() %>">
            </div>

            <label for="operation">Operation:</label>
            <select id="operation" name="operation">
                <option value="Left" <%= "Left".equals(record.getOperation()) ? "selected" : "" %>>Left</option>
                <option value="Right" <%= "Right".equals(record.getOperation()) ? "selected" : "" %>>Right</option>
                <option value="Center" <%= "Center".equals(record.getOperation()) ? "selected" : "" %>>Center</option>
            </select>

            <label for="area">Area (Sq. Ft):</label>
            <input type="text" id="area" name="area" value="<%= record.getArea() %>" placeholder="Enter area">

            <label for="quantity">Quantity:</label>
            <input type="text" id="quantity" name="quantity" value="<%= record.getQuantity() %>" placeholder="Enter Quantity">

            <label for="rate">Rate per GST (%):</label>
            <input type="text" id="rate" name="rate" value="<%= record.getRate() %>">

            <label for="amount">Amount:</label>
            <input type="text" id="amount" name="amount" value="<%= record.getAmount() %>">

            <label for="tax">Tax:</label>
            <input type="text" id="tax" name="tax" value="<%= record.getTax() %>">

            <label for="total_amount">Total Amount:</label>
            <input type="text" id="total_amount" name="total_amount" value="<%= record.getTotalAmount() %>">

            <button type="submit" name="action" class="btn">Update</button>
        </form>
    </div>
</body>
</html>
