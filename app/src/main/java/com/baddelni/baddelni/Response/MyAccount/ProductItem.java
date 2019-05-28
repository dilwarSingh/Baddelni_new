package com.baddelni.baddelni.Response.MyAccount;

import com.baddelni.baddelni.util.FavoriteCode;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductItem {

    @SerializedName("main_image")
    private MainImage mainImage;

    @SerializedName("description")
    private String description;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("sub_categories")
    private List<SubCategoriesItem> subCategories;

    @SerializedName("category_id")
    private int categoryId;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("sub_images")
    private List<SubImagesItem> subImages;

    @SerializedName("fav")
    private FavoriteCode fav = FavoriteCode.NotFavorite;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("replacements")
    private List<ReplacementsItem> replacements;

    @SerializedName("category")
    private Category category;

    @SerializedName("user")
    private User user;

    @SerializedName("status")
    private int status;

    public MainImage getMainImage() {
        return mainImage;
    }

    public FavoriteCode getFav() {
        return fav;
    }

    public void setFav(FavoriteCode fav) {
        this.fav = fav;
    }

    public void setMainImage(MainImage mainImage) {
        this.mainImage = mainImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<SubCategoriesItem> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategoriesItem> subCategories) {
        this.subCategories = subCategories;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<SubImagesItem> getSubImages() {
        return subImages;
    }

    public void setSubImages(List<SubImagesItem> subImages) {
        this.subImages = subImages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ReplacementsItem> getReplacements() {
        return replacements;
    }

    public void setReplacements(List<ReplacementsItem> replacements) {
        this.replacements = replacements;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return
                "ProductItem{" +
                        "main_image = '" + mainImage + '\'' +
                        ",description = '" + description + '\'' +
                        ",created_at = '" + createdAt + '\'' +
                        ",sub_categories = '" + subCategories + '\'' +
                        ",category_id = '" + categoryId + '\'' +
                        ",updated_at = '" + updatedAt + '\'' +
                        ",user_id = '" + userId + '\'' +
                        ",sub_images = '" + subImages + '\'' +
                        ",name = '" + name + '\'' +
                        ",id = '" + id + '\'' +
                        ",replacements = '" + replacements + '\'' +
                        ",category = '" + category + '\'' +
                        ",user = '" + user + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}