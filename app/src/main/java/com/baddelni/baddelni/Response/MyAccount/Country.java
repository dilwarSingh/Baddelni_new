package com.baddelni.baddelni.Response.MyAccount;

import com.google.gson.annotations.SerializedName;

public class Country{

	@SerializedName("country_ar")
	private String countryAr;

	@SerializedName("country")
	private String country;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	public void setCountryAr(String countryAr){
		this.countryAr = countryAr;
	}

	public String getCountryAr(){
		return countryAr;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"Country{" + 
			"country_ar = '" + countryAr + '\'' + 
			",country = '" + country + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}