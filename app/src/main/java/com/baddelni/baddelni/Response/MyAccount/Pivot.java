package com.baddelni.baddelni.Response.MyAccount;

import com.google.gson.annotations.SerializedName;

public class Pivot{

	@SerializedName("category_id")
	private int categoryId;

	@SerializedName("user_id")
	private int userId;

	public void setCategoryId(int categoryId){
		this.categoryId = categoryId;
	}

	public int getCategoryId(){
		return categoryId;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	@Override
 	public String toString(){
		return 
			"Pivot{" + 
			"category_id = '" + categoryId + '\'' + 
			",user_id = '" + userId + '\'' + 
			"}";
		}
}