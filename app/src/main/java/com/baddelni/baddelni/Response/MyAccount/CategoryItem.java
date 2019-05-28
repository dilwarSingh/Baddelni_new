package com.baddelni.baddelni.Response.MyAccount;

import com.google.gson.annotations.SerializedName;

public class CategoryItem{

	@SerializedName("image")
	private String image;

	@SerializedName("img")
	private String img;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("pivot")
	private Pivot pivot;

	@SerializedName("category_ar")
	private String categoryAr;

	@SerializedName("id")
	private int id;

	@SerializedName("category")
	private String category;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
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

	public void setPivot(Pivot pivot){
		this.pivot = pivot;
	}

	public Pivot getPivot(){
		return pivot;
	}

	public void setCategoryAr(String categoryAr){
		this.categoryAr = categoryAr;
	}

	public String getCategoryAr(){
		return categoryAr;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public String getCategory(){
		return category;
	}

	@Override
 	public String toString(){
		return 
			"CategoryItem{" + 
			"image = '" + image + '\'' + 
			",img = '" + img + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",pivot = '" + pivot + '\'' + 
			",category_ar = '" + categoryAr + '\'' + 
			",id = '" + id + '\'' + 
			",category = '" + category + '\'' + 
			"}";
		}
}