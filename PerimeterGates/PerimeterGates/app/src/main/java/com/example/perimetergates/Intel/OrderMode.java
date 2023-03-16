package com.example.perimetergates.Intel;

public class OrderMode {
    //order_id,mpesa,amount,ship,cost,quantity,unit,cust_id,name,phone,location,category,type,material,coating,dimension,set_date,mode,
//finance,remarks,blacksmith,dispatcher,driver,drive,customer,date
    String order_id = null;
    String mpesa = null;
    String amount = null;
    String ship = null;
    String cost = null;
    String quantity = null;
    String unit = null;
    String cust_id = null;
    String name = null;
    String phone = null;
    String location = null;
    String category = null;
    String type = null;
    String material = null;
    String coating = null;
    String dimension = null;
    String set_date = null;
    String mode = null;
    String finance = null;
    String remarks = null;
    String blacksmith = null;
    String dispatcher = null;
    String driver = null;
    String drive = null;
    String customer = null;
    String date = null;

    public OrderMode(String order_id, String mpesa, String amount, String ship, String cost, String quantity, String unit, String cust_id, String name, String phone, String location, String category, String type, String material, String coating, String dimension, String set_date, String mode, String finance, String remarks, String blacksmith, String dispatcher, String driver, String drive, String customer, String date) {
        this.order_id = order_id;
        this.mpesa = mpesa;
        this.amount = amount;
        this.ship = ship;
        this.cost = cost;
        this.quantity = quantity;
        this.unit = unit;
        this.cust_id = cust_id;
        this.name = name;
        this.phone = phone;
        this.location = location;
        this.category = category;
        this.type = type;
        this.material = material;
        this.coating = coating;
        this.dimension = dimension;
        this.set_date = set_date;
        this.mode = mode;
        this.finance = finance;
        this.remarks = remarks;
        this.blacksmith = blacksmith;
        this.dispatcher = dispatcher;
        this.driver = driver;
        this.drive = drive;
        this.customer = customer;
        this.date = date;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getMpesa() {
        return mpesa;
    }

    public void setMpesa(String mpesa) {
        this.mpesa = mpesa;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getShip() {
        return ship;
    }

    public void setShip(String ship) {
        this.ship = ship;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCoating() {
        return coating;
    }

    public void setCoating(String coating) {
        this.coating = coating;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getSet_date() {
        return set_date;
    }

    public void setSet_date(String set_date) {
        this.set_date = set_date;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getFinance() {
        return finance;
    }

    public void setFinance(String finance) {
        this.finance = finance;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBlacksmith() {
        return blacksmith;
    }

    public void setBlacksmith(String blacksmith) {
        this.blacksmith = blacksmith;
    }

    public String getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(String dispatcher) {
        this.dispatcher = dispatcher;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDrive() {
        return drive;
    }

    public void setDrive(String drive) {
        this.drive = drive;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "OrderMode{" +
                "order_id='" + order_id + '\'' +
                ", mpesa='" + mpesa + '\'' +
                ", amount='" + amount + '\'' +
                ", ship='" + ship + '\'' +
                ", cost='" + cost + '\'' +
                ", quantity='" + quantity + '\'' +
                ", unit='" + unit + '\'' +
                ", cust_id='" + cust_id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", location='" + location + '\'' +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", material='" + material + '\'' +
                ", coating='" + coating + '\'' +
                ", dimension='" + dimension + '\'' +
                ", set_date='" + set_date + '\'' +
                ", mode='" + mode + '\'' +
                ", finance='" + finance + '\'' +
                ", remarks='" + remarks + '\'' +
                ", blacksmith='" + blacksmith + '\'' +
                ", dispatcher='" + dispatcher + '\'' +
                ", driver='" + driver + '\'' +
                ", drive='" + drive + '\'' +
                ", customer='" + customer + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
