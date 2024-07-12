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
        max-width: 400px;
        box-sizing: border-box;
    }
    input[type="text"], input[type="number"], select {
        width: calc(100% - 20px);
        padding: 10px;
        margin: 10px 0;
        border: 1px solid #ddd;
        border-radius: 5px;
        box-sizing: border-box;
    }
    input[type="submit"] {
        width: 100%;
        padding: 10px;
        background: rgb(0, 159, 0);
        color: #fff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }
    input[type="submit"]:hover {
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
        Main Category: 
        <select name="maincategory" onchange="fetchSubCategories(this.value)"> 
            <option value="">Select main category</option>
            <option value="Window Blind">Window Blind</option>
            <option value="Motors">Motors</option>
            <option value="Curtain Tracks">Curtain Tracks</option>
            <option value="Wallpaper">Wallpaper</option>
        </select><br><br>

        Sub Category: 
        <select id="subcategory" name="subcategory" onchange="fetchItems(this.value)">
            <option value="">Select sub category</option>
        </select><br><br>
        
        Name of item: 
        <select id="item" name="items" onchange="fetchShades(this.value)">
            <option value="">Select item </option> 
        </select><br><br>
        
        Shade: 
        <select id="shade" name="shade">
            <option value="">Select shade</option>
        </select><br><br>

        Select Unit: 
        <select name="unit" onchange="toggleFields(this.value)">
            <option value="">Select unit</option>
            <option value="inch">Inches</option>
            <option value="mm">Millimeters</option>
        </select><br><br>
        <div id="inch-fields" class="unit-fields">
            Width (inch): <input type="text" name="width_inch"><br><br>
            Drop (inch): <input type="text" name="drop_inch"><br><br>
        </div>
        <div id="mm-fields" class="unit-fields">
            Width (mm): <input type="text" name="width_mm"><br><br>
            Drop (mm): <input type="text" name="drop_mm"><br><br>
        </div>
        Operation: 
        <select name="operation">
            <option value="Left">Left</option>
            <option value="Right">Right</option>
            <option value="Center">Center</option>
        </select><br><br>
        Area (Sq. Ft): <input type="text" name="area" placeholder="Enter area"><br><br>
        Quantity: <input type="text" name="quantity" placeholder="Enter Quantity"><br><br>
        Rate per GST (%): <input type="text" name="rate"><br><br>
        Amount: <input type="text" name="amount"><br><br>
        Tax: <input type="text" name="tax"><br><br>
        Total Amount: <input type="text" name="total_amount"><br><br>
        <input type="submit" value="Submit">
    </form>
</div>
</body>
</html>
