package com.sales.service.model;

import java.util.List;

public class Sales {

	public List<WeeklySale> getWeeklySales() {
		return weeklySales;
	}

	public void setWeeklySales(List<WeeklySale> weeklySales) {
		this.weeklySales = weeklySales;
	}

	@Override
	public String toString() {
		return "Sales [product=" + product + ", target=" + target + ", weeklySales=" + weeklySales + "]";
	}

	public Sales() {

	}

	public Sales(String product, String target, List<WeeklySale> weeklySales) {
		this.product = product;
		this.target = target;
		this.weeklySales = weeklySales;
	}

	private String product;
	private String target;
	private List<WeeklySale> weeklySales;

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
