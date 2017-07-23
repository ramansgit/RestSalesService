package com.sales.service.model;

import java.util.List;

/**
 * sales pojo to carry sales information
 * @author ramans
 *
 */
public class Sales {

	/**
	 * weekly sales
	 * @return
	 */
	public List<WeeklySale> getWeeklySales() {
		return weeklySales;
	}

	/**
	 * sets weekly sales
	 * @param weeklySales
	 */
	public void setWeeklySales(List<WeeklySale> weeklySales) {
		this.weeklySales = weeklySales;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return "Sales [product=" + product + ", target=" + target + ", weeklySales=" + weeklySales + "]";
	}

	public Sales() {

	}

	/**
	 * 
	 * @param product
	 * @param target
	 * @param weeklySales
	 */
	public Sales(String product, String target, List<WeeklySale> weeklySales) {
		this.product = product;
		this.target = target;
		this.weeklySales = weeklySales;
	}

	/**
	 * product
	 */
	private String product;
	/**
	 * target
	 */
	private String target;
	/**
	 * weekly sales
	 */
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
