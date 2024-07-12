package com.tcs.sellorder;

public class SellRecord {
    private String maincategory;
    private String subcategory;
    private String item;
    private String shade;
    private float widthInch;
    private float dropInch;
    private float widthMm;
    private float dropMm;
    private String operation;
    private int area;
    private int quantity;
    private float rate;
    private float amount;
    private float tax;
    private float totalAmount;

    // Getters and setters for each field
    public String getMaincategory() {
        return maincategory;
    }

    public void setMaincategory(String maincategory) {
        this.maincategory = maincategory;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getShade() {
        return shade;
    }

    public void setShade(String shade) {
        this.shade = shade;
    }

    public float getWidthInch() {
        return widthInch;
    }

    public void setWidthInch(float widthInch) {
        this.widthInch = widthInch;
    }

    public float getDropInch() {
        return dropInch;
    }

    public void setDropInch(float dropInch) {
        this.dropInch = dropInch;
    }

    public float getWidthMm() {
        return widthMm;
    }

    public void setWidthMm(float widthMm) {
        this.widthMm = widthMm;
    }

    public float getDropMm() {
        return dropMm;
    }

    public void setDropMm(float dropMm) {
        this.dropMm = dropMm;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }
}
