package com.github.santanumishra0506;

public class Product {

	String name;
	String price;
	String in_stock;
	String id;
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getIn_stock() {
		return in_stock;
	}
	public void setIn_stock(String in_stock) {
		this.in_stock = in_stock;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Product [name=" + name + ", price=" + price + ", in_stock=" + in_stock + ", id=" + id + "]";
	}
	
	
	
}
