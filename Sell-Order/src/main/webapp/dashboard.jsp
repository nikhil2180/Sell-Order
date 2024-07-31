<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sell Order</title>
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
    form {
        background: #fff;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);
        width: 100%;
        max-width: 500px;
        box-sizing: border-box;
    }
    label {
        display: block;
        margin-bottom: 5px;
        color: #333;
    }
    input[type="text"], input[type="number"], select {
        width: calc(100% - 20px);
        padding: 10px;
        margin: 10px 0;
        border: 1px solid #ddd;
        border-radius: 5px;
        box-sizing: border-box;
    }
    input[type="submit"], .btn {
        width: 100%;
        padding: 10px;
        background: rgb(0, 159, 0);
        color: #fff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        margin: 10px 0;
    }
    input[type="submit"]:hover, .btn:hover {
        background: #555;
    }
    .unit-fields {
        display: none;
    }
</style>
<script>
function fetchSubCategories(mainCategory) {
    console.log("Fetching sub-categories for: " + mainCategory);
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "subcategory?maincategory=" + mainCategory, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                console.log("Sub-categories fetched successfully.");
                var subCategories = JSON.parse(xhr.responseText);
                var subCategorySelect = document.getElementById("subcategory");
                subCategorySelect.innerHTML = "<option value=''>Select sub category</option>";
                subCategories.forEach(function(subCategory) {
                    var option = document.createElement("option");
                    option.value = subCategory;
                    option.text = subCategory;
                    subCategorySelect.appendChild(option);
                });
            } else {
                console.error("Failed to fetch sub-categories. Status: " + xhr.status);
            }
        }
    };
    xhr.send();
}

function fetchItems(subcategory) {
    console.log("Fetching items for: " + subcategory);
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "item?subcategory=" + subcategory, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                console.log("Items fetched successfully.");
                var items = JSON.parse(xhr.responseText);
                var itemSelect = document.getElementById("item");
                itemSelect.innerHTML = "<option value=''>Select item</option>";
                items.forEach(function(item) {
                    var option = document.createElement("option");
                    option.value = item;
                    option.text = item;
                    itemSelect.appendChild(option);
                });
            } else {
                console.error("Failed to fetch items. Status: " + xhr.status);
            }
        }
    };
    xhr.send();
}

function fetchShades(item) {
    console.log("Fetching shades for: " + item);
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "shade?item=" + item, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                console.log("Shades fetched successfully.");
                var shades = JSON.parse(xhr.responseText);
                var shadeSelect = document.getElementById("shade");
                shadeSelect.innerHTML = "<option value=''>Select shade</option>";
                shades.forEach(function(shade) {
                    var option = document.createElement("option");
                    option.value = shade;
                    option.text = shade;
                    shadeSelect.appendChild(option);
                });
            } else {
                console.error("Failed to fetch shades. Status: " + xhr.status);
            }
        }
    };
    xhr.send();
}

function toggleFields(unit) {
    if (unit === 'inch') {
        document.getElementById('inch-fields').style.display = 'block';
        document.getElementById('mm-fields').style.display = 'none';
    } else if (unit === 'mm') {
        document.getElementById('inch-fields').style.display = 'none';
        document.getElementById('mm-fields').style.display = 'block';
    }
}

function validateForm() {
    var mainCategory = document.getElementsByName("maincategory")[0].value;
    var subCategory = document.getElementsByName("subcategory")[0].value;
    var item = document.getElementsByName("items")[0].value;
    if (mainCategory === "" || subCategory === "" || item === "") {
        alert("Please select all required fields.");
        return false;
    }

    // Log form data for debugging
    console.log("Form Data:");
    console.log("Main Category: " + mainCategory);
    console.log("Sub Category: " + subCategory);
    console.log("Item: " + item);
    return true;
}
</script>
</head>
<body>
<div>
    <h1>Sell Order</h1>
    <form action="sellorder" method="post" onsubmit="return validateForm()">
        <label for="maincategory">Main Category:</label>
        <select name="maincategory" onchange="fetchSubCategories(this.value)"> 
            <option value="">Select main category</option>
            <option value="Window Blind">Window Blind</option>
            <option value="Motors">Motors</option>
            <option value="Curtain Tracks">Curtain Tracks</option>
            <option value="Wallpaper">Wallpaper</option>
        </select>

        <label for="subcategory">Sub Category:</label>
        <select id="subcategory" name="subcategory" onchange="fetchItems(this.value)">
            <option value="">Select sub category</option>
        </select>
        
        <label for="item">Name of item:</label>
        <select id="item" name="items" onchange="fetchShades(this.value)">
            <option value="">Select item</option> 
        </select>
        
        <label for="shade">Shade:</label>
        <select id="shade" name="shade">
            <option value="">Select shade</option>
        </select>

        <label for="unit">Select Unit:</label>
        <select name="unit" onchange="toggleFields(this.value)">
            <option value="">Select unit</option>
            <option value="inch">Inches</option>
            <option value="mm">Millimeters</option>
        </select>
        
        <div id="inch-fields" class="unit-fields">
            <label for="width_inch">Width (inch):</label>
            <input type="text" name="width_inch">
            <label for="drop_inch">Drop (inch):</label> 
            <input type="text" name="drop_inch">
        </div>
        <div id="mm-fields" class="unit-fields">
            <label for="width_mm">Width (mm):</label>
            <input type="text" name="width_mm">
            <label for="drop_mm">Drop (mm):</label>
            <input type="text" name="drop_mm">
        </div>

        <label for="operation">Operation:</label>
        <select name="operation">
            <option value="Left">Left</option>
            <option value="Right">Right</option>
            <option value="Center">Center</option>
        </select>

        <label for="area">Area (Sq. Ft):</label>
        <input type="text" name="area" placeholder="Enter area">
        
        <label for="quantity">Quantity:</label>
        <input type="text" name="quantity" placeholder="Enter Quantity">
        
        <label for="rate">Rate per GST (%):</label>
        <input type="text" name="rate">
        
        <label for="amount">Amount:</label>
        <input type="text" name="amount">
        
        <label for="tax">Tax:</label>
        <input type="text" name="tax">
        
        <label for="total_amount">Total Amount:</label>
        <input type="text" name="total_amount">
        
        <input type="submit" value="Submit">
    </form>
    
    <form action="preview" method="get">
        <button type="submit" name="action" value="download1" class="btn">Preview</button>
    </form>
</div>
</body>
</html>
