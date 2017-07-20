package com.sales.service.model;

public class WeeklySale {
	@Override
	public String toString() {
		return "WeeklySale [weekNo=" + weekNo + ", sales=" + sales + ", achievementPercentage=" + achievementPercentage
				+ "]";
	}
	
	public WeeklySale(){
		
	}
	
	public WeeklySale(String weekNo,String sales, String acheivementPercentage){
		this.weekNo = weekNo;
		this.sales = sales;
		this.achievementPercentage  = acheivementPercentage;
	}
	private String weekNo;
	private String sales;
	private String achievementPercentage;
	public String getWeekNo() {
		return weekNo;
	}
	public void setWeekNo(String weekNo) {
		this.weekNo = weekNo;
	}
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	public String getAchievementPercentage() {
		return achievementPercentage;
	}
	public void setAchievementPercentage(String achievementPercentage) {
		this.achievementPercentage = achievementPercentage;
	}
	
	
}
