package com.dinein.model;

public class OrderItem {
	
	int orderNo;
	String menuId;
	String name;
	String imgUrl;
	int qty;
	double price;

	public OrderItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderItem(int orderNo, String menuId, String name, String imgUrl, int qty, double price) {
		super();
		this.orderNo = orderNo;
		this.menuId = menuId;
		this.name = name;
		this.imgUrl = imgUrl;
		this.qty = qty;
		this.price = price;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "OrderItem [orderNo=" + orderNo + ", menuId=" + menuId + ", name=" + name + ", imgUrl=" + imgUrl
				+ ", qty=" + qty + ", price=" + price + "]";
	}

	

}
