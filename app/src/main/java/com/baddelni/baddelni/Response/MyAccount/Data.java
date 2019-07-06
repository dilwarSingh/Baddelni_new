package com.baddelni.baddelni.Response.MyAccount;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("image")
    private String image;

    @SerializedName("country")
    private Country country;

    @SerializedName("img")
    private String img;

    @SerializedName("product")
    private List<ProductItem> product;

    @SerializedName("role")
    private int role;

    @SerializedName("gender")
    private int gender;

    @SerializedName("active_code")
    private Object activeCode;

    @SerializedName("goggle_id")
    private Object goggleId;

    @SerializedName("active")
    private int active;

    @SerializedName("available_product")
    private int availableProduct;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("email_verified_at")
    private String emailVerifiedAt;

    @SerializedName("facebook_id")
    private Object facebookId;

    @SerializedName("available_time")
    private String availableTime;

    @SerializedName("count_requests")
    private int countRequests;

    @SerializedName("count_myrequest")
    private int count_myrequest;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("phone")
    private String phone;

    @SerializedName("name")
    private String name;

    @SerializedName("about")
    private String about;

    @SerializedName("id")
    private int id;

    @SerializedName("count_favourites")
    private int countFavourites;

    @SerializedName("category")
    private List<CategoryItem> category;

    @SerializedName("email")
    private String email;

    @SerializedName("country_id")
    private int countryId;



    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<ProductItem> getProduct() {
        return product;
    }

    public void setProduct(List<ProductItem> product) {
        this.product = product;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Object getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(Object activeCode) {
        this.activeCode = activeCode;
    }

    public Object getGoggleId() {
        return goggleId;
    }

    public void setGoggleId(Object goggleId) {
        this.goggleId = goggleId;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getAvailableProduct() {
        return availableProduct;
    }

    public void setAvailableProduct(int availableProduct) {
        this.availableProduct = availableProduct;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(String emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public Object getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(Object facebookId) {
        this.facebookId = facebookId;
    }

    public String getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(String availableTime) {
        this.availableTime = availableTime;
    }

    public int getCountRequests() {
        return countRequests;
    }

    public void setCountRequests(int countRequests) {
        this.countRequests = countRequests;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public int getCountFavourites() {
        return countFavourites;
    }

    public void setCountFavourites(int countFavourites) {
        this.countFavourites = countFavourites;
    }

    public List<CategoryItem> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryItem> category) {
        this.category = category;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getCount_myrequest() {
        return count_myrequest;
    }

    public void setCount_myrequest(int count_myrequest) {
        this.count_myrequest = count_myrequest;
    }

    @Override
    public String toString() {
        return
                "Data{" +
                        "image = '" + image + '\'' +
                        ",country = '" + country + '\'' +
                        ",img = '" + img + '\'' +
                        ",product = '" + product + '\'' +
                        ",role = '" + role + '\'' +
                        ",gender = '" + gender + '\'' +
                        ",active_code = '" + activeCode + '\'' +
                        ",goggle_id = '" + goggleId + '\'' +
                        ",active = '" + active + '\'' +
                        ",available_product = '" + availableProduct + '\'' +
                        ",created_at = '" + createdAt + '\'' +
                        ",email_verified_at = '" + emailVerifiedAt + '\'' +
                        ",facebook_id = '" + facebookId + '\'' +
                        ",available_time = '" + availableTime + '\'' +
                        ",count_requests = '" + countRequests + '\'' +
                        ",updated_at = '" + updatedAt + '\'' +
                        ",phone = '" + phone + '\'' +
                        ",name = '" + name + '\'' +
                        ",id = '" + id + '\'' +
                        ",count_favourites = '" + countFavourites + '\'' +
                        ",category = '" + category + '\'' +
                        ",email = '" + email + '\'' +
                        ",country_id = '" + countryId + '\'' +
                        "}";
    }
}