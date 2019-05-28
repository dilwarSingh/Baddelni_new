package com.baddelni.baddelni.Response.MyAccount;

import com.google.gson.annotations.SerializedName;

public class SubCategoriesItem{

	@SerializedName("category_id")
	private int categoryId;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("sub_category")
	private String subCategory;

	@SerializedName("sub_category_ar")
	private String subCategoryAr;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("pivot")
	private Pivot pivot;

	@SerializedName("id")
	private int id;

	public void setCategoryId(int categoryId){
		this.categoryId = categoryId;
	}

	public int getCategoryId(){
		return categoryId;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setSubCategory(String subCategory){
		this.subCategory = subCategory;
	}

	public String getSubCategory(){
		return subCategory;
	}

	public void setSubCategoryAr(String subCategoryAr){
		this.subCategoryAr = subCategoryAr;
	}

	public String getSubCategoryAr(){
		return subCategoryAr;
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

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"SubCategoriesItem{" + 
			"category_id = '" + categoryId + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",sub_category = '" + subCategory + '\'' + 
			",sub_category_ar = '" + subCategoryAr + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",pivot = '" + pivot + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}