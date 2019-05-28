package com.baddelni.baddelni.Response.MyAccount;

import com.baddelni.baddelni.util.NetworkCode;
import com.google.gson.annotations.SerializedName;

public class MyAccount{

	@SerializedName("code")
	private NetworkCode code;

	@SerializedName("data")
	private Data data;

	@SerializedName("category_image_path")
	private String categoryImagePath;

	@SerializedName("product_image_path")
	private String productImagePath;

	@SerializedName("user_image_path")
	private String userImagePath;

	public void setCode(NetworkCode code){
		this.code = code;
	}

	public NetworkCode getCode(){
		return code;
	}

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setCategoryImagePath(String categoryImagePath){
		this.categoryImagePath = categoryImagePath;
	}

	public String getCategoryImagePath(){
		return categoryImagePath;
	}

	public void setProductImagePath(String productImagePath){
		this.productImagePath = productImagePath;
	}

	public String getProductImagePath(){
		return productImagePath;
	}

	public void setUserImagePath(String userImagePath){
		this.userImagePath = userImagePath;
	}

	public String getUserImagePath(){
		return userImagePath;
	}

	@Override
 	public String toString(){
		return 
			"MyAccount{" + 
			"code = '" + code + '\'' + 
			",data = '" + data + '\'' + 
			",category_image_path = '" + categoryImagePath + '\'' + 
			",product_image_path = '" + productImagePath + '\'' + 
			",user_image_path = '" + userImagePath + '\'' + 
			"}";
		}
}